package com.sky2dev.day25.repository;

import com.sky2dev.day25.entity.TelemetryRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryRecordRepository extends JpaRepository<TelemetryRecord, Long> {
    List<TelemetryRecord> findTop20ByOrderByCollectedAtDesc();
}
