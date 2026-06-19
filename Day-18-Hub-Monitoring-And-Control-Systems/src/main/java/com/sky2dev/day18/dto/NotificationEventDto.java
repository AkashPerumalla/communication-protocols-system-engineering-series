package com.sky2dev.day18.dto;

import com.sky2dev.day18.model.DeliveryStatus;
import com.sky2dev.day18.model.NotificationChannel;
import com.sky2dev.day18.model.NotificationEvent;
import java.time.Instant;

public record NotificationEventDto(
        Long id,
        Long alarmId,
        String alarmMessage,
        NotificationChannel channel,
        DeliveryStatus deliveryStatus,
        Instant sentTime
) {
    public static NotificationEventDto from(NotificationEvent event) {
        return new NotificationEventDto(
                event.getId(),
                event.getAlarm().getId(),
                event.getAlarm().getMessage(),
                event.getChannel(),
                event.getDeliveryStatus(),
                event.getSentTime()
        );
    }
}
