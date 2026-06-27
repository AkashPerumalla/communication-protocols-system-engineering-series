package com.sky2dev.day26.controller;

import com.sky2dev.day26.dto.ApiResponse;
import com.sky2dev.day26.dto.NotificationResponse;
import com.sky2dev.day26.service.NotificationService;
import com.sky2dev.day26.constants.MarkerConstants;
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
            MarkerConstants.SECRET_LOADED,
                "Notifications fetched successfully",
                notificationService.getNotifications()));
    }
}
