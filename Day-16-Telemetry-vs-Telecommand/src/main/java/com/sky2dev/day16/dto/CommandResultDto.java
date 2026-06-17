package com.sky2dev.day16.dto;

import java.time.Instant;

public record CommandResultDto(Long id,
                               Long deviceId,
                               String deviceCode,
                               String commandType,
                               String status,
                               String marker,
                               String message,
                               String previousState,
                               String resultingState,
                               Instant requestedAt,
                               Instant completedAt) {
}
