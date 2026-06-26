package com.sky2dev.day25.repository;

import com.sky2dev.day25.entity.ReportRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRecordRepository extends JpaRepository<ReportRecord, Long> {
    List<ReportRecord> findTop20ByOrderByGeneratedAtDesc();
}
