package com.sky2dev.day18.dto;

import com.sky2dev.day18.model.Report;
import com.sky2dev.day18.model.ReportType;
import java.time.Instant;

public record ReportDto(
        Long id,
        ReportType reportType,
        String generatedBy,
        Instant generatedTime,
        String reportSummary
) {
    public static ReportDto from(Report report) {
        return new ReportDto(
                report.getId(),
                report.getReportType(),
                report.getGeneratedBy(),
                report.getGeneratedTime(),
                report.getReportSummary()
        );
    }
}
