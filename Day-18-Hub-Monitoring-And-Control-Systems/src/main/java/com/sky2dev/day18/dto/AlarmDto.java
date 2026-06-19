package com.sky2dev.day18.dto;

import com.sky2dev.day18.model.Alarm;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.AlarmStatus;
import com.sky2dev.day18.model.AlarmType;
import java.time.Instant;

public record AlarmDto(
        Long id,
        Long deviceId,
        String deviceName,
        AlarmSeverity severity,
        AlarmType alarmType,
        String message,
        AlarmStatus status,
        boolean acknowledged,
        Instant createdAt
) {
    public static AlarmDto from(Alarm alarm) {
        return new AlarmDto(
                alarm.getId(),
                alarm.getDevice().getId(),
                alarm.getDevice().getHostname(),
                alarm.getSeverity(),
                alarm.getAlarmType(),
                alarm.getMessage(),
                alarm.getStatus(),
                alarm.isAcknowledged(),
                alarm.getCreatedAt()
        );
    }
}
