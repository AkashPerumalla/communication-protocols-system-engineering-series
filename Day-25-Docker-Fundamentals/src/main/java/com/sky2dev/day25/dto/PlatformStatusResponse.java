package com.sky2dev.day25.dto;

public record PlatformStatusResponse(
        String application,
        String profile,
        String status,
        String runtime,
        String dockerReadiness,
        String databaseMode,
        long devices,
        long telemetry,
        long alarms,
        long notifications,
        long reports
) {
}
