package com.sky2dev.day22.dto;

import java.time.Instant;

public record TelemetryResponse(
        Long id,
        Long deviceId,
        Double cpuUsage,
        Double memoryUsage,
        Double temperature,
        Double signalStrength,
        Instant timestamp
) {
}
