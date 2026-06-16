package com.sky2dev.day15.dto;

import com.sky2dev.day15.entity.Alert;
import com.sky2dev.day15.entity.AlertSeverity;
import java.time.Instant;

public record AlertResponse(
        Long id,
        Long deviceId,
        String hostname,
        String ruleName,
        AlertSeverity severity,
        String message,
        boolean active,
        boolean acknowledged,
        Instant createdAt,
        Instant resolvedAt,
        String sourceType) {

    public static AlertResponse from(Alert alert) {
        return new AlertResponse(
                alert.getId(),
                alert.getDevice() == null ? null : alert.getDevice().getId(),
                alert.getDevice() == null ? null : alert.getDevice().getHostname(),
                alert.getRuleName(),
                alert.getSeverity(),
                alert.getMessage(),
                alert.isActive(),
                alert.isAcknowledged(),
                alert.getCreatedAt(),
                alert.getResolvedAt(),
                alert.getSourceType());
    }
}
