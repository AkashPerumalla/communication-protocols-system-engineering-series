package com.sky2dev.day22.controller;

import com.sky2dev.day22.dto.ApiResponse;
import com.sky2dev.day22.dto.TelemetryResponse;
import com.sky2dev.day22.service.TelemetryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @GetMapping
    public ApiResponse<List<TelemetryResponse>> getTelemetry() {
        return ApiResponse.success("TELEMETRY STREAM ACTIVE", "Telemetry stream data retrieved",
                telemetryService.getTelemetry());
    }
}
