package com.sky2dev.day21.repository;

import com.sky2dev.day21.entity.Device;
import com.sky2dev.day21.entity.DeviceMetric;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceMetricRepository extends JpaRepository<DeviceMetric, Long> {

    void deleteByDeviceId(Long deviceId);

    List<DeviceMetric> findByDeviceIdOrderByTimestampDesc(Long deviceId);

    List<DeviceMetric> findByDeviceOrderByTimestampDesc(Device device);

    List<DeviceMetric> findAllByOrderByTimestampDesc(Pageable pageable);

    @Query("select avg(m.cpuUsage) from DeviceMetric m")
    Double averageCpuUsage();

    @Query("select avg(m.memoryUsage) from DeviceMetric m")
    Double averageMemoryUsage();
}
