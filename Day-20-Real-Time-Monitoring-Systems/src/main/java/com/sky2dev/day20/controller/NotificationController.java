package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.ApiResponse;
import com.sky2dev.day20.dto.NotificationEventDto;
import com.sky2dev.day20.dto.NotificationSendRequest;
import com.sky2dev.day20.service.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ApiResponse<List<NotificationEventDto>> listNotifications() {
        return new ApiResponse<>("NOTIFICATION SENT", "Notifications fetched", notificationService.listNotifications());
    }

    @PostMapping("/send")
    public ApiResponse<NotificationEventDto> send(@Valid @RequestBody NotificationSendRequest request) {
        return new ApiResponse<>("NOTIFICATION SENT", "Notification dispatched", notificationService.sendNotification(request));
    }
}
