package com.sky2dev.day22.repository;

import com.sky2dev.day22.entity.TelemetryMetric;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TelemetryMetricRepository extends JpaRepository<TelemetryMetric, Long> {

    List<TelemetryMetric> findTop200ByOrderByTimestampDesc();

    Optional<TelemetryMetric> findTopByDeviceIdOrderByTimestampDesc(Long deviceId);

    @Query("select coalesce(avg(t.cpuUsage), 0) from TelemetryMetric t")
    Double avgCpuUsage();

    @Query("select coalesce(avg(t.memoryUsage), 0) from TelemetryMetric t")
    Double avgMemoryUsage();

    @Query("select coalesce(avg(t.signalStrength), 0) from TelemetryMetric t")
    Double avgSignalStrength();
}
