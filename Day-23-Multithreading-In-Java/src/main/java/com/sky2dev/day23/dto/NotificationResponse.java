package com.sky2dev.day23.dto;

import com.sky2dev.day23.entity.NotificationChannel;
import com.sky2dev.day23.entity.NotificationEvent;
import com.sky2dev.day23.entity.NotificationStatus;
import java.time.Instant;

public record NotificationResponse(
        Long id,
        NotificationChannel channel,
        String recipient,
        NotificationStatus status,
        Instant timestamp
) {
    public static NotificationResponse from(NotificationEvent event) {
        return new NotificationResponse(
                event.getId(),
                event.getChannel(),
                event.getRecipient(),
                event.getStatus(),
                event.getTimestamp()
        );
    }
}
