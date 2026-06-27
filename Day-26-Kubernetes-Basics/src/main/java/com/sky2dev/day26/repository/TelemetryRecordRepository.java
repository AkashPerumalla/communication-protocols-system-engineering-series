package com.sky2dev.day26.repository;

import com.sky2dev.day26.entity.TelemetryRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryRecordRepository extends JpaRepository<TelemetryRecord, Long> {
    List<TelemetryRecord> findTop100ByOrderByCollectedAtDesc();
    Optional<TelemetryRecord> findTop1ByDeviceIdOrderByCollectedAtDesc(Long deviceId);
}
