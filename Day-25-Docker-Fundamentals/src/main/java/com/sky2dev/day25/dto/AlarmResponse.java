package com.sky2dev.day25.dto;

import com.sky2dev.day25.entity.AlarmSeverity;
import com.sky2dev.day25.entity.AlarmStatus;
import java.time.LocalDateTime;

public record AlarmResponse(
        Long id,
        String deviceName,
        String code,
        String description,
        AlarmSeverity severity,
        AlarmStatus status,
        LocalDateTime raisedAt
) {
}
