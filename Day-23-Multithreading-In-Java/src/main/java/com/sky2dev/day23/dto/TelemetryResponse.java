package com.sky2dev.day23.dto;

import com.sky2dev.day23.entity.TelemetryRecord;
import java.time.Instant;

public record TelemetryResponse(
        Long id,
        Long deviceId,
        double cpuUsage,
        double memoryUsage,
        double temperature,
        double signalStrength,
        Instant timestamp
) {
    public static TelemetryResponse from(TelemetryRecord record) {
        return new TelemetryResponse(
                record.getId(),
                record.getDeviceId(),
                record.getCpuUsage(),
                record.getMemoryUsage(),
                record.getTemperature(),
                record.getSignalStrength(),
                record.getTimestamp()
        );
    }
}
