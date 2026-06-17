package com.sky2dev.day16.dto;

import java.time.Instant;

public record AlarmDto(Long id,
                       Long deviceId,
                       String deviceCode,
                       String severity,
                       String status,
                       String metricName,
                       double metricValue,
                       double thresholdValue,
                       String message,
                       Instant triggeredAt,
                       Instant clearedAt,
                       String recoveryNote) {
}
