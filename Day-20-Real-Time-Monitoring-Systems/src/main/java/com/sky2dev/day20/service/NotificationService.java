package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.NotificationEventDto;
import com.sky2dev.day20.dto.NotificationSendRequest;
import com.sky2dev.day20.entity.NotificationEvent;
import com.sky2dev.day20.entity.NotificationStatus;
import com.sky2dev.day20.repository.NotificationEventRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationEventRepository notificationEventRepository;

    public NotificationEventDto sendNotification(NotificationSendRequest request) {
        NotificationEvent event = NotificationEvent.builder()
                .channel(request.channel())
                .recipient(request.recipient())
                .message(request.message())
                .status(NotificationStatus.SENT)
                .timestamp(Instant.now())
                .build();
        return NotificationEventDto.from(notificationEventRepository.save(event));
    }

    public List<NotificationEventDto> listNotifications() {
        return notificationEventRepository.findAll().stream().map(NotificationEventDto::from).toList();
    }
}
