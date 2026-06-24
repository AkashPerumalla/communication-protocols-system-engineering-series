package com.sky2dev.day23.threading;

import com.sky2dev.day23.service.NotificationService;

public class NotificationTask implements Runnable {

    private final NotificationService notificationService;
    private final Long alarmId;

    public NotificationTask(NotificationService notificationService, Long alarmId) {
        this.notificationService = notificationService;
        this.alarmId = alarmId;
    }

    @Override
    public void run() {
        notificationService.createNotificationForAlarm(alarmId);
    }
}
