package com.sky2dev.day25.dto;

import com.sky2dev.day25.entity.ReportType;
import java.time.LocalDateTime;

public record ReportResponse(
        Long id,
        String reportName,
        ReportType reportType,
        LocalDateTime periodStart,
        LocalDateTime periodEnd,
        LocalDateTime generatedAt,
        String summary,
        String generatedBy
) {
}
