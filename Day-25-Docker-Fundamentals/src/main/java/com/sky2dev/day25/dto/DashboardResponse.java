package com.sky2dev.day25.dto;

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
        long totalReports,
        Map<String, Double> avgMetricByType
) {
}
