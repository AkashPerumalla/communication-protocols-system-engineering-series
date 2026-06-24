package com.sky2dev.day23.repository;

import com.sky2dev.day23.entity.TelemetryRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryRecordRepository extends JpaRepository<TelemetryRecord, Long> {
    List<TelemetryRecord> findTop50ByOrderByTimestampDesc();

    List<TelemetryRecord> findTop10ByDeviceIdOrderByTimestampDesc(Long deviceId);
}
