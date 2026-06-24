package com.sky2dev.day23.dto;

public record ThreadStatisticsResponse(
        String marker,
        int telemetryCount,
        int alarmCount,
        int notificationCount,
        String mode
) {
}
