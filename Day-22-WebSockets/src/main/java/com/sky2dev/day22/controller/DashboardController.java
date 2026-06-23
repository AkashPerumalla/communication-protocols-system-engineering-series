package com.sky2dev.day22.controller;

import com.sky2dev.day22.dto.ApiResponse;
import com.sky2dev.day22.dto.DashboardResponse;
import com.sky2dev.day22.service.DashboardService;
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
    public ApiResponse<DashboardResponse> getDashboard() {
        return ApiResponse.success("DASHBOARD UPDATED", "Dashboard metrics retrieved", dashboardService.getDashboard());
    }
}
