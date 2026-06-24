package com.sky2dev.day23.simulation;

import java.time.Instant;

public record TelemetrySample(
        long deviceId,
        double cpuUsage,
        double memoryUsage,
        double temperature,
        double signalStrength,
        Instant timestamp
) {
}
