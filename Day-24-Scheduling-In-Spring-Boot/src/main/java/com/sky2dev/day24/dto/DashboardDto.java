package com.sky2dev.day24.dto;

public record DashboardDto(
        long totalDevices,
        long onlineDevices,
        long offlineDevices,
        long warningDevices,
        long activeAlarms,
        long criticalAlarms,
        long notificationsSent,
        long reportsGenerated,
        long archivedRecords,
        String schedulerHealth
) {
}
