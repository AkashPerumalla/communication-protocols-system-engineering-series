package com.sky2dev.day20.dto;

import com.sky2dev.day20.entity.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationSendRequest(
        @NotNull NotificationChannel channel,
        @NotBlank String recipient,
        @NotBlank String message) {
}
