package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.MetricDto;
import com.sky2dev.day17.service.AlarmManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

    private final AlarmManagementService alarmManagementService;

    public MetricController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @GetMapping
    public ApiResponse<List<MetricDto>> getMetrics() {
        return new ApiResponse<>("THRESHOLD DETECTED", "Metric samples ready for rule evaluation", alarmManagementService.getMetrics());
    }
}
