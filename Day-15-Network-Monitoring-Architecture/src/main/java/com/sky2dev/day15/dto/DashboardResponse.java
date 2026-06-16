package com.sky2dev.day15.dto;

import com.sky2dev.day15.dashboard.DashboardSummary;
import java.time.Instant;

public record DashboardResponse(
        Instant generatedAt,
        long totalDevices,
        long onlineDevices,
        long offlineDevices,
        long activeAlerts,
        long criticalAlerts,
        double averageCpu,
        double averageMemory) {

    public static DashboardResponse from(DashboardSummary summary) {
        return new DashboardResponse(
                summary.generatedAt(),
                summary.totalDevices(),
                summary.onlineDevices(),
                summary.offlineDevices(),
                summary.activeAlerts(),
                summary.criticalAlerts(),
                summary.averageCpu(),
                summary.averageMemory());
    }
}
