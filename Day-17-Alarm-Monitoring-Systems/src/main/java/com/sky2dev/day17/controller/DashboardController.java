package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.DashboardDto;
import com.sky2dev.day17.service.AlarmManagementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final AlarmManagementService alarmManagementService;

    public DashboardController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @GetMapping
    public ApiResponse<DashboardDto> getDashboard() {
        return new ApiResponse<>("NOC ALARM DASHBOARD", "NOC summary", alarmManagementService.getDashboard());
    }
}
