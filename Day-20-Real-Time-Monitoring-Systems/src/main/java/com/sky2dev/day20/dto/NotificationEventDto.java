package com.sky2dev.day20.dto;

import com.sky2dev.day20.entity.NotificationEvent;
import java.time.Instant;

public record NotificationEventDto(
        Long id,
        String channel,
        String recipient,
        String message,
        String status,
        Instant timestamp) {

    public static NotificationEventDto from(NotificationEvent event) {
        return new NotificationEventDto(
                event.getId(),
                event.getChannel().name(),
                event.getRecipient(),
                event.getMessage(),
                event.getStatus().name(),
                event.getTimestamp());
    }
}
