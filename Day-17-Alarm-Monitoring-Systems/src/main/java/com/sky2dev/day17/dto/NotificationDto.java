package com.sky2dev.day17.dto;

import com.sky2dev.day17.model.NotificationDelivery;
import java.time.Instant;

public record NotificationDto(Long id, Long alarmId, String channelType, String recipient, Instant deliveredAt, String status) {
    public static NotificationDto from(NotificationDelivery delivery) {
        return new NotificationDto(delivery.getId(), delivery.getAlarm().getId(), delivery.getChannelType().name(), delivery.getRecipient(), delivery.getDeliveredAt(), delivery.getStatus());
    }
}
