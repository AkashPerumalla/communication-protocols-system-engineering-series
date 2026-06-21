package com.sky2dev.day20.dto;

import java.time.Instant;

public record ReportDto(String reportType, String summary, Instant generatedAt) {
}
