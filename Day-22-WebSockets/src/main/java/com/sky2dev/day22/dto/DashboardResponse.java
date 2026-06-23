package com.sky2dev.day22.dto;

public record DashboardResponse(
        long totalDevices,
        long onlineDevices,
        long offlineDevices,
        long activeAlarms,
        long criticalAlarms,
        double avgCpuUsage,
        double avgMemoryUsage,
        double avgSignalStrength,
        long connectedClients,
        long activeSessions,
        long totalConnections,
        long messagesSent,
        long messagesReceived,
        long broadcastCount
) {
}
