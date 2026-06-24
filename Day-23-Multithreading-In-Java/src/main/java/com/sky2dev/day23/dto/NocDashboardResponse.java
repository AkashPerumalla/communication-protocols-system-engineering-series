package com.sky2dev.day23.dto;

import java.util.Map;

public record NocDashboardResponse(
        String marker,
        long totalDevices,
        long activeAlarms,
        long telemetryRecords,
        long notificationsSent,
        Map<String, Object> metrics
) {
}
