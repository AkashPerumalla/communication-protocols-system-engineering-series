package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.ApiResponse;
import com.sky2dev.day20.dto.MetricRecordDto;
import com.sky2dev.day20.service.MetricsCollectionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsCollectionService metricsCollectionService;

    @GetMapping
    public ApiResponse<List<MetricRecordDto>> listMetrics() {
        return new ApiResponse<>("METRICS COLLECTED", "Latest metrics retrieved", metricsCollectionService.listRecentMetrics());
    }
}
