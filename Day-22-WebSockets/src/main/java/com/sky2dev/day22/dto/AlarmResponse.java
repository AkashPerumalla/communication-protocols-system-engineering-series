package com.sky2dev.day22.dto;

import com.sky2dev.day22.entity.AlarmSeverity;
import java.time.Instant;

public record AlarmResponse(
        Long id,
        Long deviceId,
        AlarmSeverity severity,
        String message,
        boolean acknowledged,
        Instant timestamp
) {
}
