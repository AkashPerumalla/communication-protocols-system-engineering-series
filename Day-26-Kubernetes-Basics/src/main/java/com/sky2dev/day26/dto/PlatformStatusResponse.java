package com.sky2dev.day26.dto;

public record PlatformStatusResponse(
        String application,
        String environment,
        String status,
        String runtime,
        String clusterName,
        String namespace,
        String databaseMode,
        long devices,
        long telemetry,
        long alarms,
        long notifications,
        long deployments,
        String clusterStatus,
        String metricsEndpoint
) {
}
