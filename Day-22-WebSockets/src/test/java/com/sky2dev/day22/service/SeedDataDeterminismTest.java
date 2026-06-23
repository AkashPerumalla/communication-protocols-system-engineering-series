package com.sky2dev.day22.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day22.repository.AlarmEventRepository;
import com.sky2dev.day22.repository.ConnectedClientRepository;
import com.sky2dev.day22.repository.DeviceRepository;
import com.sky2dev.day22.repository.TelemetryMetricRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(properties = {
        "spring.task.scheduling.enabled=false",
        "app.scheduling.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:day22-seed;MODE=PostgreSQL;DB_CLOSE_DELAY=0;DB_CLOSE_ON_EXIT=FALSE",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class SeedDataDeterminismTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TelemetryMetricRepository telemetryMetricRepository;

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    @Autowired
    private ConnectedClientRepository connectedClientRepository;

    @Test
    void shouldLoadDeterministicSeedCounts() {
        assertThat(deviceRepository.count()).isEqualTo(10);
        assertThat(telemetryMetricRepository.count()).isEqualTo(100);
        assertThat(alarmEventRepository.count()).isEqualTo(10);
        assertThat(connectedClientRepository.count()).isEqualTo(5);
    }
}
