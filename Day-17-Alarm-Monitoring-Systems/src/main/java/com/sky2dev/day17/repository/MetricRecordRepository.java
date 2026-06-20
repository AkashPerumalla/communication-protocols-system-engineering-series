package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.MetricRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRecordRepository extends JpaRepository<MetricRecord, Long> {
    List<MetricRecord> findTop20ByOrderByRecordedAtDesc();
}
