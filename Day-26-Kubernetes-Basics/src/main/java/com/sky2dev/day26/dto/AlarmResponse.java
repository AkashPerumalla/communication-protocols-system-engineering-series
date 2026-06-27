package com.sky2dev.day26.dto;

import com.sky2dev.day26.entity.AlarmSeverity;
import com.sky2dev.day26.entity.AlarmStatus;
import java.time.LocalDateTime;

public record AlarmResponse(
        Long id,
        String deviceName,
        String code,
        String description,
        AlarmSeverity severity,
        AlarmStatus status,
        String sourceMetric,
        Double thresholdValue,
        Double observedValue,
        LocalDateTime raisedAt
) {
}
