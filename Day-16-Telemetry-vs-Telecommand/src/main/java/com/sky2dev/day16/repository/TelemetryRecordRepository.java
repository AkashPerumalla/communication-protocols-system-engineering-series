package com.sky2dev.day16.repository;

import com.sky2dev.day16.model.TelemetryRecord;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryRecordRepository extends JpaRepository<TelemetryRecord, Long> {
    @EntityGraph(attributePaths = "device")
    List<TelemetryRecord> findTop50ByOrderByCollectedAtDesc();

    @EntityGraph(attributePaths = "device")
    List<TelemetryRecord> findTop10ByDeviceIdOrderByCollectedAtDesc(Long deviceId);
}
