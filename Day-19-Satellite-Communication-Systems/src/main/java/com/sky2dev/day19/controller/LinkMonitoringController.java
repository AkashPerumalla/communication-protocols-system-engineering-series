package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.LinkMetricDto;
import com.sky2dev.day19.dto.MonitoringFindingDto;
import com.sky2dev.day19.service.LinkMonitoringService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LinkMonitoringController {

    private final LinkMonitoringService monitoringService;

    @GetMapping("/metrics")
    public ApiResponse<List<LinkMetricDto>> metrics() {
        return new ApiResponse<>("LINK MONITORING ACTIVE", "Link metrics listed", monitoringService.listMetrics());
    }

    @GetMapping("/monitoring/findings")
    public ApiResponse<List<MonitoringFindingDto>> findings() {
        return new ApiResponse<>("LINK MONITORING ACTIVE", "Monitoring findings generated", monitoringService.analyzeMetrics());
    }
}
