package com.sky2dev.day21.controller;

import com.sky2dev.day21.dto.ApiResponse;
import com.sky2dev.day21.dto.DashboardResponse;
import com.sky2dev.day21.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "NOC dashboard and operational summary APIs")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Generate the NOC dashboard")
    public ApiResponse<DashboardResponse> getDashboard() {
        return ApiResponse.success("DASHBOARD GENERATED", "NOC dashboard summary generated", dashboardService.getDashboard());
    }
}
