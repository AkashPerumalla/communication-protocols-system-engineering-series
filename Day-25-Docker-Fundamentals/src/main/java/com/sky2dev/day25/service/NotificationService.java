package com.sky2dev.day25.service;

import com.sky2dev.day25.dto.NotificationResponse;
import com.sky2dev.day25.repository.NotificationEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationEventRepository notificationEventRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotifications() {
        return notificationEventRepository.findTop20ByOrderBySentAtDesc().stream()
                .map(notification -> new NotificationResponse(
                        notification.getId(),
                        notification.getDevice().getName(),
                        notification.getChannel(),
                        notification.getRecipient(),
                        notification.getMessage(),
                        notification.getStatus(),
                        notification.getSentAt()))
                .toList();
    }
}
