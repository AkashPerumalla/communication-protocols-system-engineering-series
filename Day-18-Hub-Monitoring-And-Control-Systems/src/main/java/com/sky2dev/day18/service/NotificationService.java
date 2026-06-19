package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.NotificationEventDto;
import com.sky2dev.day18.model.Alarm;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.DeliveryStatus;
import com.sky2dev.day18.model.NotificationChannel;
import com.sky2dev.day18.model.NotificationEvent;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.NotificationEventRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final AlarmRepository alarmRepository;
    private final NotificationEventRepository notificationEventRepository;

    @Transactional(readOnly = true)
    public List<NotificationEventDto> getNotifications() {
        return notificationEventRepository.findTop20ByOrderBySentTimeDesc().stream()
                .map(NotificationEventDto::from)
                .toList();
    }

    @Transactional
    public List<NotificationEventDto> processAutomaticNotifications() {
        List<NotificationEvent> created = new ArrayList<>();
        List<Alarm> recent = alarmRepository.findTop10ByOrderByCreatedAtDesc();
        for (Alarm alarm : recent) {
            if (alarm.getSeverity() != AlarmSeverity.CRITICAL) {
                continue;
            }
            if (!notificationEventRepository.findByAlarmId(alarm.getId()).isEmpty()) {
                continue;
            }
            created.addAll(dispatch(alarm));
        }
        return created.stream().map(NotificationEventDto::from).toList();
    }

    @Transactional
    public List<NotificationEvent> dispatch(Alarm alarm) {
        List<NotificationChannel> channels = List.of(
                NotificationChannel.EMAIL,
                NotificationChannel.SMS,
                NotificationChannel.WEBHOOK,
                NotificationChannel.SLACK,
                NotificationChannel.TEAMS,
                NotificationChannel.ITSM
        );

        List<NotificationEvent> events = new ArrayList<>();
        for (NotificationChannel channel : channels) {
            DeliveryStatus status = shouldFailDelivery(alarm, channel) ? DeliveryStatus.FAILED : DeliveryStatus.SENT;
            events.add(NotificationEvent.builder()
                    .alarm(alarm)
                    .channel(channel)
                    .deliveryStatus(status)
                    .sentTime(Instant.now())
                    .build());
        }
        return notificationEventRepository.saveAll(events);
    }

    private boolean shouldFailDelivery(Alarm alarm, NotificationChannel channel) {
        return alarm.getMessage() != null
                && alarm.getMessage().contains("control")
                && channel == NotificationChannel.ITSM;
    }
}
