package com.sky2dev.day26.controller;

import com.sky2dev.day26.dto.ApiResponse;
import com.sky2dev.day26.dto.DashboardResponse;
import com.sky2dev.day26.service.DashboardService;
import com.sky2dev.day26.constants.MarkerConstants;
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
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.SERVICE_EXPOSED,
                "Dashboard snapshot generated",
                dashboardService.getDashboard()));
    }
}
