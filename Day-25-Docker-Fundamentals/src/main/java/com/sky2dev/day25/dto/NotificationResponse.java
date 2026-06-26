package com.sky2dev.day25.dto;

import com.sky2dev.day25.entity.NotificationChannel;
import com.sky2dev.day25.entity.NotificationStatus;
import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        String deviceName,
        NotificationChannel channel,
        String recipient,
        String message,
        NotificationStatus status,
        LocalDateTime sentAt
) {
}
