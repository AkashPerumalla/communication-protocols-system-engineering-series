package com.sky2dev.day26.service;

import com.sky2dev.day26.dto.NotificationResponse;
import com.sky2dev.day26.entity.AlarmEvent;
import com.sky2dev.day26.entity.AlarmStatus;
import com.sky2dev.day26.entity.NotificationChannel;
import com.sky2dev.day26.entity.NotificationEvent;
import com.sky2dev.day26.entity.NotificationStatus;
import com.sky2dev.day26.mapper.NotificationMapper;
import com.sky2dev.day26.repository.AlarmEventRepository;
import com.sky2dev.day26.repository.NotificationEventRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationEventRepository notificationEventRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationMapper notificationMapper;

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotifications() {
        return notificationEventRepository.findTop20ByOrderBySentAtDesc().stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Transactional
    public void dispatchPendingNotifications() {
        List<AlarmEvent> openAlarms = alarmEventRepository.findByStatusOrderByRaisedAtDesc(AlarmStatus.OPEN);
        int created = 0;
        for (AlarmEvent alarm : openAlarms) {
            if (created >= 3) {
                break;
            }
            notificationEventRepository.save(NotificationEvent.builder()
                    .alarm(alarm)
                    .device(alarm.getDevice())
                    .channel(created % 2 == 0 ? NotificationChannel.NOC_CONSOLE : NotificationChannel.EMAIL)
                    .recipient(created % 2 == 0 ? "noc-console" : "noc-escalation@sky2dev.com")
                    .message("Alarm " + alarm.getCode() + " requires deployment-aware triage")
                    .status(NotificationStatus.SENT)
                    .deliveryReference("notif-" + alarm.getCode() + "-" + created)
                    .sentAt(LocalDateTime.now().withNano(0))
                    .build());
            created++;
        }
    }
}
