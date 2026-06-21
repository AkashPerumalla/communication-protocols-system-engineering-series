package com.sky2dev.day20.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day20.repository.AlertEventRepository;
import com.sky2dev.day20.repository.MetricRecordRepository;
import com.sky2dev.day20.repository.MonitoringAgentRepository;
import com.sky2dev.day20.repository.NotificationEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.task.scheduling.enabled=false")
class SeedDataDeterminismTest {

    @Autowired
    private MonitoringAgentRepository agentRepository;

    @Autowired
    private MetricRecordRepository metricRecordRepository;

    @Autowired
    private AlertEventRepository alertEventRepository;

    @Autowired
    private NotificationEventRepository notificationEventRepository;

    @Test
    void shouldLoadDeterministicSeedCounts() {
        assertThat(agentRepository.count()).isEqualTo(5);
        assertThat(metricRecordRepository.count()).isEqualTo(50);
        assertThat(alertEventRepository.count()).isEqualTo(10);
        assertThat(notificationEventRepository.count()).isEqualTo(20);
    }
}
