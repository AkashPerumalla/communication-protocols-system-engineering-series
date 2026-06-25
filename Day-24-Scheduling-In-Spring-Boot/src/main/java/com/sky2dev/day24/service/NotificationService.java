package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.AlarmEvent;
import com.sky2dev.day24.entity.NotificationChannel;
import com.sky2dev.day24.entity.NotificationEvent;
import com.sky2dev.day24.repository.AlarmEventRepository;
import com.sky2dev.day24.repository.NotificationEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;

    @Transactional
    public List<NotificationEvent> sendNotifications() {
        List<AlarmEvent> active = alarmEventRepository.findTop50ByOrderByTimestampDesc();
        List<NotificationEvent> events = new ArrayList<>();

        for (AlarmEvent alarm : active) {
            if (alarm.isAcknowledged()) {
                continue;
            }
            NotificationChannel channel = NotificationChannel.values()[(int) (alarm.getId() % NotificationChannel.values().length)];
            events.add(NotificationEvent.builder()
                    .channel(channel)
                    .message("Alarm notification for device " + alarm.getDeviceId() + " severity=" + alarm.getSeverity())
                    .status("SENT")
                    .timestamp(Instant.now())
                    .build());
        }

        if (events.isEmpty()) {
            return List.of();
        }
        return notificationEventRepository.saveAll(events);
    }
}
