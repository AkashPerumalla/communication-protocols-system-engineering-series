package com.sky2dev.day24.controller;

import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.dto.PlatformStatusDto;
import com.sky2dev.day24.util.MarkerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/platform")
public class PlatformController {

    @Value("${scheduler.telemetry-rate}")
    private String telemetryRate;

    @Value("${scheduler.health-rate}")
    private String healthRate;

    @Value("${scheduler.alarm-rate}")
    private String alarmRate;

    @Value("${scheduler.dashboard-rate}")
    private String dashboardRate;

    @GetMapping("/status")
    public ApiResponse<PlatformStatusDto> status() {
        PlatformStatusDto payload = new PlatformStatusDto(
                "Day24 Scheduling Operations Platform",
                "UP",
                "RUNNING",
                Map.of(
                        "telemetryRate", telemetryRate,
                        "healthRate", healthRate,
                        "alarmRate", alarmRate,
                        "dashboardRate", dashboardRate
                )
        );
        return ApiResponse.success(MarkerConstants.SCHEDULER_ACTIVE, "Platform status fetched", payload);
    }
}
