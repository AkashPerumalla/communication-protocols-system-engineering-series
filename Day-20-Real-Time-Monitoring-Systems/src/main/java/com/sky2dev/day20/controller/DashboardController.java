package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.ApiResponse;
import com.sky2dev.day20.dto.DashboardSnapshotDto;
import com.sky2dev.day20.service.DashboardService;
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
    public ApiResponse<DashboardSnapshotDto> dashboard() {
        return new ApiResponse<>("DASHBOARD UPDATED", "Dashboard snapshot ready", dashboardService.latestSnapshot());
    }
}
