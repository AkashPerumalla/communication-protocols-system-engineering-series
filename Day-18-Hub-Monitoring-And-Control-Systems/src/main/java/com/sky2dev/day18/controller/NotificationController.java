package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.NotificationEventDto;
import com.sky2dev.day18.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ApiResponse<List<NotificationEventDto>> notifications() {
        List<NotificationEventDto> auto = notificationService.processAutomaticNotifications();
        List<NotificationEventDto> result = auto.isEmpty() ? notificationService.getNotifications() : auto;
        return new ApiResponse<>(
                "NOTIFICATION SENT",
                "Notification workflow processed for critical and failure events",
                result
        );
    }
}
