package com.sky2dev.day15.dashboard;

import java.time.Instant;

public record DashboardSummary(
        Instant generatedAt,
        long totalDevices,
        long onlineDevices,
        long offlineDevices,
        long activeAlerts,
        long criticalAlerts,
        double averageCpu,
        double averageMemory) {
}
