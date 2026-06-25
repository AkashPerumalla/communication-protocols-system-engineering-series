package com.sky2dev.day24.controller;

import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.dto.DashboardDto;
import com.sky2dev.day24.service.DashboardService;
import com.sky2dev.day24.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ApiResponse<DashboardDto> getDashboard() {
        return ApiResponse.success(MarkerConstants.DASHBOARD_UPDATED, "Dashboard refreshed", dashboardService.updateDashboard());
    }
}
