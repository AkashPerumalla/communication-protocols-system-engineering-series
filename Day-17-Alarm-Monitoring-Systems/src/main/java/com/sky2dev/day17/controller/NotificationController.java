package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.NotificationDto;
import com.sky2dev.day17.service.AlarmManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final AlarmManagementService alarmManagementService;

    public NotificationController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @GetMapping
    public ApiResponse<List<NotificationDto>> getNotifications() {
        return new ApiResponse<>("ALARM ESCALATED", "Notification history", alarmManagementService.getNotifications());
    }
}
