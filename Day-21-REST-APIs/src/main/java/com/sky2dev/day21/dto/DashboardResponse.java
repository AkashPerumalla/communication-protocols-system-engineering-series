package com.sky2dev.day21.dto;

public record DashboardResponse(
        long totalDevices,
        long onlineDevices,
        long offlineDevices,
        long warningDevices,
        long maintenanceDevices,
        long routers,
        long switches,
        long modems,
        long hubs,
        long sensors,
        double averageCpuUsage,
        double averageMemoryUsage
) {
}
