package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.DeviceMetricDto;
import com.sky2dev.day18.service.HubMonitoringService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricController {

    private final HubMonitoringService hubMonitoringService;

    @GetMapping
    public ApiResponse<List<DeviceMetricDto>> getMetrics() {
        return new ApiResponse<>(
                "HUB MONITORING ACTIVE",
                "Latest metrics collected from hub devices",
                hubMonitoringService.collectMetrics()
        );
    }
}
