package com.sky2dev.day26;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day26.entity.DeviceStatus;
import com.sky2dev.day26.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(com.sky2dev.day26.seed.SeedDataInitializer.class)
class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void shouldQueryDeviceStatusCounts() {
        assertThat(deviceRepository.findAllByOrderByNameAsc()).hasSize(10);
        assertThat(deviceRepository.countByStatus(DeviceStatus.ONLINE)).isGreaterThan(0);
        assertThat(deviceRepository.findByName("Router-01")).isPresent();
    }
}
