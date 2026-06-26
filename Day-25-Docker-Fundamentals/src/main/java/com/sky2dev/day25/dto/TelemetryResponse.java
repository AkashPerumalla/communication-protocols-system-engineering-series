package com.sky2dev.day25.dto;

import java.time.LocalDateTime;

public record TelemetryResponse(
        Long id,
        String deviceName,
        String metricName,
        Double metricValue,
        String unit,
        Integer qualityScore,
        LocalDateTime collectedAt
) {
}
