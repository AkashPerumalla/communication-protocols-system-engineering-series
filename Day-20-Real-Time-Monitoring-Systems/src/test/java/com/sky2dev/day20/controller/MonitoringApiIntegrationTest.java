package com.sky2dev.day20.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day20.dto.AgentHeartbeatRequest;
import com.sky2dev.day20.dto.AgentRegistrationRequest;
import com.sky2dev.day20.dto.NotificationSendRequest;
import com.sky2dev.day20.dto.ReportGenerateRequest;
import com.sky2dev.day20.entity.NotificationChannel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.task.scheduling.enabled=false")
class MonitoringApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldExposeAllRequiredMarkers() {
        assertThat(restTemplate.getForObject("/api/agents", String.class)).contains("AGENT REGISTERED");
        assertThat(restTemplate.getForObject("/api/metrics", String.class)).contains("METRICS COLLECTED");
        assertThat(restTemplate.getForObject("/api/alerts", String.class)).contains("ALERT GENERATED");
        assertThat(restTemplate.getForObject("/api/dashboard", String.class)).contains("DASHBOARD UPDATED");
        assertThat(restTemplate.getForObject("/api/snmp", String.class)).contains("SNMP MONITORING ACTIVE");
        assertThat(restTemplate.getForObject("/api/stream/status", String.class)).contains("REALTIME STREAM ACTIVE");
    }

    @Test
    void shouldHandlePostWorkflows() {
        ResponseEntity<String> register = restTemplate.exchange(
                "/api/agents/register",
                HttpMethod.POST,
                new HttpEntity<>(new AgentRegistrationRequest("agent-99", "edge-node-99", "10.0.99.99", "Lab")),
                String.class);
        assertThat(register.getBody()).contains("AGENT REGISTERED");

        ResponseEntity<String> heartbeat = restTemplate.exchange(
                "/api/agents/heartbeat",
                HttpMethod.POST,
                new HttpEntity<>(new AgentHeartbeatRequest("agent-99")),
                String.class);
        assertThat(heartbeat.getBody()).contains("AGENT REGISTERED");

        ResponseEntity<String> notification = restTemplate.exchange(
                "/api/notifications/send",
                HttpMethod.POST,
                new HttpEntity<>(new NotificationSendRequest(NotificationChannel.SLACK, "noc-team", "test alert")),
                String.class);
        assertThat(notification.getBody()).contains("NOTIFICATION SENT");

        ResponseEntity<String> report = restTemplate.exchange(
                "/api/reports/generate",
                HttpMethod.POST,
                new HttpEntity<>(new ReportGenerateRequest("Alert Summary Report")),
                String.class);
        assertThat(report.getBody()).contains("REPORT GENERATED");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        ResponseEntity<String> evaluate = restTemplate.exchange(
                "/api/alerts/evaluate",
                HttpMethod.POST,
                new HttpEntity<>("{}", headers),
                String.class);
        assertThat(evaluate.getBody()).contains("ALERT GENERATED");
    }
}
