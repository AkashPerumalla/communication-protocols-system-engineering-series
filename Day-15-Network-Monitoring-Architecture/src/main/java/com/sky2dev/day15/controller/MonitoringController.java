package com.sky2dev.day15.controller;

import com.sky2dev.day15.dto.MonitoringMetricResponse;
import com.sky2dev.day15.service.DevicePollingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/metrics")
public class MonitoringController {

    private final DevicePollingService devicePollingService;

    @GetMapping
    public List<MonitoringMetricResponse> getMetrics() {
        return devicePollingService.findRecentMetrics().stream()
                .map(MonitoringMetricResponse::from)
                .toList();
    }
}
