package com.sky2dev.day24.repository;

import com.sky2dev.day24.entity.TelemetryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TelemetryRecordRepository extends JpaRepository<TelemetryRecord, Long> {

    List<TelemetryRecord> findByTimestampBefore(Instant threshold);

    List<TelemetryRecord> findTop20ByDeviceIdOrderByTimestampDesc(Long deviceId);
}
