package com.sky2dev.day21.controller;

import com.sky2dev.day21.dto.ApiResponse;
import com.sky2dev.day21.dto.DeviceMetricResponse;
import com.sky2dev.day21.service.MetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
@Tag(name = "Metrics", description = "Device telemetry and health APIs")
public class MetricController {

    private final MetricService metricService;

    @GetMapping
    @Operation(summary = "Get recent device metrics")
    public ApiResponse<List<DeviceMetricResponse>> getMetrics(@RequestParam(defaultValue = "20") int limit) {
        return ApiResponse.success("METRICS RETRIEVED", "Recent device metrics retrieved", metricService.getMetrics(limit));
    }

    @GetMapping("/device/{id}")
    @Operation(summary = "Get metrics by device id")
    public ApiResponse<List<DeviceMetricResponse>> getMetricsByDevice(@PathVariable Long id) {
        return ApiResponse.success("METRICS RETRIEVED", "Device-specific metrics retrieved", metricService.getMetricsByDevice(id));
    }
}
