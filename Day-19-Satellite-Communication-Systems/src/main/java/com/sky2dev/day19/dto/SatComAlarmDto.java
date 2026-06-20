package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.AlarmSeverity;
import com.sky2dev.day19.model.AlarmStatus;
import com.sky2dev.day19.model.SatComAlarm;
import java.time.Instant;

public record SatComAlarmDto(
        Long id,
        AlarmSeverity severity,
        String alarmType,
        String source,
        String message,
        AlarmStatus status,
        Instant timestamp
) {
    public static SatComAlarmDto from(SatComAlarm alarm) {
        return new SatComAlarmDto(
                alarm.getId(),
                alarm.getSeverity(),
                alarm.getAlarmType(),
                alarm.getSource(),
                alarm.getMessage(),
                alarm.getStatus(),
                alarm.getTimestamp()
        );
    }
}
