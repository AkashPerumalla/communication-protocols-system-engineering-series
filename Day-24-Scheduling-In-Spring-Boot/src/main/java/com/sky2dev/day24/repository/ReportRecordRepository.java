package com.sky2dev.day24.repository;

import com.sky2dev.day24.entity.ReportRecord;
import com.sky2dev.day24.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRecordRepository extends JpaRepository<ReportRecord, Long> {

    long countByReportType(ReportType reportType);
}
