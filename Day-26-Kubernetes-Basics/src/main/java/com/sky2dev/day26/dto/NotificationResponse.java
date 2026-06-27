package com.sky2dev.day26.dto;

import com.sky2dev.day26.entity.NotificationChannel;
import com.sky2dev.day26.entity.NotificationStatus;
import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        String deviceName,
        NotificationChannel channel,
        String recipient,
        String message,
        NotificationStatus status,
        String deliveryReference,
        LocalDateTime sentAt
) {
}
