package com.sky2dev.day23.dto;

import com.sky2dev.day23.entity.AlarmEvent;
import com.sky2dev.day23.entity.AlarmSeverity;
import java.time.Instant;

public record AlarmResponse(
        Long id,
        Long deviceId,
        AlarmSeverity severity,
        String message,
        boolean acknowledged,
        Instant timestamp
) {
    public static AlarmResponse from(AlarmEvent event) {
        return new AlarmResponse(
                event.getId(),
                event.getDeviceId(),
                event.getSeverity(),
                event.getMessage(),
                event.isAcknowledged(),
                event.getTimestamp()
        );
    }
}
