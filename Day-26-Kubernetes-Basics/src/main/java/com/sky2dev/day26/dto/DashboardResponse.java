package com.sky2dev.day26.dto;

import java.util.Map;

public record DashboardResponse(
        long totalDevices,
        long onlineDevices,
        long degradedDevices,
        long offlineDevices,
        long maintenanceDevices,
        long totalTelemetryRecords,
        long openAlarms,
        long acknowledgedAlarms,
        long resolvedAlarms,
        long notificationsSent,
        long queuedNotifications,
        long healthyDeployments,
        long activeRollouts,
        long totalDesiredReplicas,
        long totalAvailableReplicas,
        String clusterStatus,
        Map<String, Double> avgMetricByType
) {
}
