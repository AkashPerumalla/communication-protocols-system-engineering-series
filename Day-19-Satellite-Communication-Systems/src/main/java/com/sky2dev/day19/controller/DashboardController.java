package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.NocDashboardDto;
import com.sky2dev.day19.service.NocDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final NocDashboardService nocDashboardService;

    @GetMapping
    public ApiResponse<NocDashboardDto> dashboard() {
        return new ApiResponse<>("SATCOM NOC DASHBOARD", "SatCom dashboard generated", nocDashboardService.buildDashboard());
    }
}
