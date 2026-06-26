package com.sky2dev.day25.controller;

import com.sky2dev.day25.dto.ApiResponse;
import com.sky2dev.day25.dto.DashboardResponse;
import com.sky2dev.day25.service.DashboardService;
import com.sky2dev.day25.util.MarkerConstants;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        DashboardResponse dashboard = dashboardService.getDashboard();
        Map<String, Object> payload = Map.of(
                "summary", dashboard,
                "notificationMarker", MarkerConstants.NOTIFICATION_SENT);
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.DASHBOARD_UPDATED,
                "Dashboard snapshot generated",
                payload));
    }
}
