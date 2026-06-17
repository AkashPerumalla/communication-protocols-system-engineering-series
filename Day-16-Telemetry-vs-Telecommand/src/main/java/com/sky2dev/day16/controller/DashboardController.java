package com.sky2dev.day16.controller;

import com.sky2dev.day16.dto.ApiResponse;
import com.sky2dev.day16.dto.DashboardDto;
import com.sky2dev.day16.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ApiResponse<DashboardDto> dashboard() {
        return new ApiResponse<>("NOC CONTROL DASHBOARD", "Current command-and-telemetry operating picture", dashboardService.buildDashboard());
    }
}
