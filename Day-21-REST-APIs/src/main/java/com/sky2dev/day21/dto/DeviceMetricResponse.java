package com.sky2dev.day21.dto;

import java.time.Instant;

public record DeviceMetricResponse(
        Long id,
        Long deviceId,
        String hostname,
        double cpuUsage,
        double memoryUsage,
        double temperature,
        double signalStrength,
        Instant timestamp
) {
}
