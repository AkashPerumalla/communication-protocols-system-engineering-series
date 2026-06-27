package com.sky2dev.day26.dto;

import java.time.LocalDateTime;

public record TelemetryResponse(
        Long id,
        String deviceName,
        String metricName,
        Double metricValue,
        String unit,
        Integer qualityScore,
        String ingestionSource,
        LocalDateTime collectedAt
) {
}
