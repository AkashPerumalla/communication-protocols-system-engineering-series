package com.sky2dev.day22.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.task.scheduling.enabled=false",
                "app.scheduling.enabled=false"
        })
class MonitoringApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldExposeRequiredRestMarkers() {
        assertThat(restTemplate.getForObject("/api/platform", String.class)).contains("REALTIME MONITORING ACTIVE");
        assertThat(restTemplate.getForObject("/api/devices", String.class)).contains("REALTIME MONITORING ACTIVE");
        assertThat(restTemplate.getForObject("/api/telemetry", String.class)).contains("TELEMETRY STREAM ACTIVE");
        assertThat(restTemplate.getForObject("/api/alarms", String.class)).contains("ALARM BROADCASTED");
        assertThat(restTemplate.getForObject("/api/dashboard", String.class)).contains("DASHBOARD UPDATED");
        assertThat(restTemplate.getForObject("/api/clients", String.class)).contains("CLIENT CONNECTED");
    }

    @Test
    void shouldAcknowledgeAlarmByRestApi() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/alarms/1/acknowledge",
                HttpMethod.POST,
                null,
                String.class);
        assertThat(response.getBody()).contains("ALARM ACKNOWLEDGED");
    }
}
