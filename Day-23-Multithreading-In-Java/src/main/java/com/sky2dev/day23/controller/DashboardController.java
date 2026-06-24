package com.sky2dev.day23.controller;

import com.sky2dev.day23.dto.ApiResponse;
import com.sky2dev.day23.dto.NocDashboardResponse;
import com.sky2dev.day23.service.DashboardService;
import com.sky2dev.day23.util.MarkerConstants;
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
    public ResponseEntity<ApiResponse<NocDashboardResponse>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.MULTITHREADING_ACTIVE,
                "NOC dashboard reflects concurrent telemetry, alarm and notification processing",
                dashboardService.buildDashboardSnapshot()
        ));
    }
}
