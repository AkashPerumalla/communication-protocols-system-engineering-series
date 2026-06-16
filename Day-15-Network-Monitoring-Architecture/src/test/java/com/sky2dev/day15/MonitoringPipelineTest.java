package com.sky2dev.day15;

import com.sky2dev.day15.repository.AlertRepository;
import com.sky2dev.day15.repository.DeviceRepository;
import com.sky2dev.day15.repository.MonitoringMetricRepository;
import com.sky2dev.day15.service.DevicePollingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.task.scheduling.enabled=false")
@TestPropertySource(properties = "spring.task.scheduling.enabled=false")
class MonitoringPipelineTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private MonitoringMetricRepository monitoringMetricRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private DevicePollingService devicePollingService;

    @Test
    void bootstrapShouldSeedMonitoringData() {
        assertThat(deviceRepository.count()).isEqualTo(8);
        assertThat(monitoringMetricRepository.count()).isGreaterThanOrEqualTo(8);
        assertThat(alertRepository.countByActiveTrue()).isGreaterThanOrEqualTo(3);
    }

    @Test
    void pollingShouldStoreAdditionalMetrics() {
        long before = monitoringMetricRepository.count();
        devicePollingService.pollAllDevices();
        long after = monitoringMetricRepository.count();
        assertThat(after).isEqualTo(before + 8);
    }
}
