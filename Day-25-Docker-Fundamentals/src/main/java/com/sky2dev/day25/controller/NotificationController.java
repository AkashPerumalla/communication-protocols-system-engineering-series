package com.sky2dev.day25.controller;

import com.sky2dev.day25.dto.ApiResponse;
import com.sky2dev.day25.dto.NotificationResponse;
import com.sky2dev.day25.service.NotificationService;
import com.sky2dev.day25.util.MarkerConstants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotifications() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.NOTIFICATION_SENT,
                "Notifications fetched successfully",
                notificationService.getNotifications()));
    }
}
