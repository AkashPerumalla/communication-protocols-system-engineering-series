package com.sky2dev.day26;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "app.scheduling.enabled=false")
class HealthEndpointIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldExposeActuatorHealthAndInfo() {
        ResponseEntity<String> health = restTemplate.getForEntity("http://localhost:" + port + "/actuator/health", String.class);
        ResponseEntity<String> info = restTemplate.getForEntity("http://localhost:" + port + "/actuator/info", String.class);

        assertThat(health.getBody()).contains("UP");
        assertThat(info.getBody()).contains("Day-26 Kubernetes Device Monitoring Platform");
    }
}
