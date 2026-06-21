package com.sky2dev.day20.dto;

import com.sky2dev.day20.entity.AlertEvent;
import java.time.Instant;

public record AlertEventDto(
        Long id,
        Long alertId,
        String metricName,
        double metricValue,
        double threshold,
        String severity,
        String message,
        Instant timestamp,
        String status) {

    public static AlertEventDto from(AlertEvent event) {
        return new AlertEventDto(
                event.getId(),
                event.getAlertId(),
                event.getMetricName(),
                event.getMetricValue(),
                event.getThreshold(),
                event.getSeverity().name(),
                event.getMessage(),
                event.getTimestamp(),
                event.getStatus().name());
    }
}
