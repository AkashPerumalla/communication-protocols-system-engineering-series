package com.sky2dev.day15.dto;

import com.sky2dev.day15.entity.AlertSeverity;
import com.sky2dev.day15.entity.TrapEvent;
import com.sky2dev.day15.entity.TrapType;
import java.time.Instant;

public record TrapEventResponse(
        Long id,
        Long deviceId,
        String hostname,
        TrapType trapType,
        AlertSeverity severity,
        String message,
        Instant triggeredAt,
        boolean processed,
        Instant processedAt) {

    public static TrapEventResponse from(TrapEvent event) {
        return new TrapEventResponse(
                event.getId(),
                event.getDevice().getId(),
                event.getDevice().getHostname(),
                event.getTrapType(),
                event.getSeverity(),
                event.getMessage(),
                event.getTriggeredAt(),
                event.isProcessed(),
                event.getProcessedAt());
    }
}
