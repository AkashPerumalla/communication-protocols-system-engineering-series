package com.sky2dev.day26;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day26.repository.AlarmEventRepository;
import com.sky2dev.day26.repository.DeploymentStatusRepository;
import com.sky2dev.day26.repository.DeviceRepository;
import com.sky2dev.day26.repository.NotificationEventRepository;
import com.sky2dev.day26.repository.TelemetryRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "app.scheduling.enabled=false")
class SeedDataDeterminismTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TelemetryRecordRepository telemetryRecordRepository;

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    @Autowired
    private NotificationEventRepository notificationEventRepository;

    @Autowired
    private DeploymentStatusRepository deploymentStatusRepository;

    @Test
    void shouldLoadDeterministicCounts() {
        assertThat(deviceRepository.count()).isEqualTo(10);
        assertThat(telemetryRecordRepository.count()).isEqualTo(100);
        assertThat(alarmEventRepository.count()).isEqualTo(20);
        assertThat(notificationEventRepository.count()).isEqualTo(20);
        assertThat(deploymentStatusRepository.count()).isEqualTo(5);
    }
}
