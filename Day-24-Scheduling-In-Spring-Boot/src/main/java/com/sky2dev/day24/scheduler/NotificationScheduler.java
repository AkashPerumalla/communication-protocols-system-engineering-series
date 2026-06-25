package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.NotificationService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final TaskExecutionService taskExecutionService;
    private final NotificationService notificationService;

    @Scheduled(fixedRateString = "${scheduler.notification-rate}")
    public void dispatchNotifications() {
        taskExecutionService.executeTracked("NotificationScheduler.dispatchNotifications", notificationService::sendNotifications);
    }
}
