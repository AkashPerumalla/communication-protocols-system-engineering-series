package com.sky2dev.day26.mapper;

import com.sky2dev.day26.dto.NotificationResponse;
import com.sky2dev.day26.entity.NotificationEvent;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(NotificationEvent notificationEvent) {
        return new NotificationResponse(
                notificationEvent.getId(),
                notificationEvent.getDevice().getName(),
                notificationEvent.getChannel(),
                notificationEvent.getRecipient(),
                notificationEvent.getMessage(),
                notificationEvent.getStatus(),
                notificationEvent.getDeliveryReference(),
                notificationEvent.getSentAt());
    }
}
