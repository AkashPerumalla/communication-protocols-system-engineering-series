package com.sky2dev.day15.repository;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.MonitoringMetric;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoringMetricRepository extends JpaRepository<MonitoringMetric, Long> {

    List<MonitoringMetric> findTop100ByOrderByCapturedAtDesc();

    Optional<MonitoringMetric> findTopByDeviceOrderByCapturedAtDesc(Device device);

    List<MonitoringMetric> findByDeviceOrderByCapturedAtDesc(Device device);
}
