package com.sky2dev.day17.service;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.NotificationChannel;
import com.sky2dev.day17.model.NotificationChannelType;
import com.sky2dev.day17.model.NotificationDelivery;
import com.sky2dev.day17.repository.NotificationDeliveryRepository;
import com.sky2dev.day17.repository.NotificationChannelRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationDeliveryRepository notificationDeliveryRepository;
    private final NotificationChannelRepository notificationChannelRepository;

    public NotificationService(NotificationDeliveryRepository notificationDeliveryRepository, NotificationChannelRepository notificationChannelRepository) {
        this.notificationDeliveryRepository = notificationDeliveryRepository;
        this.notificationChannelRepository = notificationChannelRepository;
    }

    public List<NotificationDelivery> notifyChannels(Alarm alarm) {
        List<NotificationDelivery> deliveries = new ArrayList<>();
        for (NotificationChannel channel : notificationChannelRepository.findAll()) {
            if (!channel.isEnabled()) {
                continue;
            }
            deliveries.add(NotificationDelivery.builder()
                    .alarm(alarm)
                    .channelType(channel.getChannelType())
                    .recipient(channel.getEndpoint())
                    .deliveredAt(Instant.parse("2026-06-18T10:05:00Z"))
                    .status("DELIVERED")
                    .build());
        }
        return notificationDeliveryRepository.saveAll(deliveries);
    }

    public List<NotificationDelivery> findAll() {
        return notificationDeliveryRepository.findAll();
    }
}
