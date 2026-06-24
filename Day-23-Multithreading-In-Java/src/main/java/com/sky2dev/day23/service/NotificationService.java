package com.sky2dev.day23.service;

import com.sky2dev.day23.dto.NotificationResponse;
import com.sky2dev.day23.entity.NotificationChannel;
import com.sky2dev.day23.entity.NotificationEvent;
import com.sky2dev.day23.entity.NotificationStatus;
import com.sky2dev.day23.repository.NotificationEventRepository;
import com.sky2dev.day23.threading.MonitoringStatistics;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationEventRepository notificationEventRepository;
    private final MonitoringStatistics monitoringStatistics;

    public NotificationEvent createNotificationForAlarm(Long alarmId) {
        NotificationChannel channel = (alarmId % 3 == 0) ? NotificationChannel.WEBHOOK
                : (alarmId % 2 == 0) ? NotificationChannel.SMS : NotificationChannel.EMAIL;

        NotificationEvent event = NotificationEvent.builder()
                .channel(channel)
                .recipient("ops-team@sky2dev.com")
                .status(NotificationStatus.SENT)
                .timestamp(Instant.now())
                .build();

        monitoringStatistics.incrementAtomicNotification();
        return notificationEventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotifications() {
        return notificationEventRepository.findTop50ByOrderByTimestampDesc().stream()
                .map(NotificationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public long countSentNotifications() {
        return notificationEventRepository.countByStatus(NotificationStatus.SENT);
    }
}
