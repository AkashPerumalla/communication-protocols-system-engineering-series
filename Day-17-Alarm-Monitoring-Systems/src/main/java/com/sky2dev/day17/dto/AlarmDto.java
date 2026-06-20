package com.sky2dev.day17.dto;

import com.sky2dev.day17.model.Alarm;
import java.time.Instant;

public record AlarmDto(Long id, Long deviceId, String deviceName, String alarmType, String severity, String state, String message, String correlationKey, String rootCauseName, boolean acknowledged, boolean escalated, Instant detectedAt, Instant openedAt, Instant acknowledgedAt, Instant escalatedAt, Instant resolvedAt, Instant closedAt) {
    public static AlarmDto from(Alarm alarm) {
        return new AlarmDto(alarm.getId(), alarm.getDevice().getId(), alarm.getDevice().getName(), alarm.getAlarmType().name(), alarm.getSeverity().name(), alarm.getState().name(), alarm.getMessage(), alarm.getCorrelationKey(), alarm.getRootCauseName(), alarm.isAcknowledged(), alarm.isEscalated(), alarm.getDetectedAt(), alarm.getOpenedAt(), alarm.getAcknowledgedAt(), alarm.getEscalatedAt(), alarm.getResolvedAt(), alarm.getClosedAt());
    }
}
