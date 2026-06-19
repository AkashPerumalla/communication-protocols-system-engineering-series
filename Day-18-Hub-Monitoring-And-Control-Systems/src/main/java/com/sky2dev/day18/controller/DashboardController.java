package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.DashboardDto;
import com.sky2dev.day18.service.DashboardService;
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
    public ApiResponse<DashboardDto> dashboard() {
        return new ApiResponse<>(
                "DASHBOARD UPDATED",
                "Realtime hub dashboard snapshot generated",
                dashboardService.getDashboard()
        );
    }
}
