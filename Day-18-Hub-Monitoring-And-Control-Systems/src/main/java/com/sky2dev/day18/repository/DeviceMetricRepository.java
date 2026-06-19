package com.sky2dev.day18.repository;

import com.sky2dev.day18.model.DeviceMetric;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceMetricRepository extends JpaRepository<DeviceMetric, Long> {

    List<DeviceMetric> findTop20ByOrderByTimestampDesc();

    List<DeviceMetric> findByDeviceIdOrderByTimestampDesc(Long deviceId);

    DeviceMetric findTopByDeviceIdOrderByTimestampDesc(Long deviceId);
}
