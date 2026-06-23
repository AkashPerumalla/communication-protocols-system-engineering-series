package com.sky2dev.day22.dto;

import jakarta.validation.constraints.NotNull;

public record AlarmAcknowledgeRequest(
        @NotNull(message = "alarmId is required")
        Long alarmId,
        String acknowledgedBy
) {
}
