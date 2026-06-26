package com.sky2dev.day25.controller;

import com.sky2dev.day25.dto.ApiResponse;
import com.sky2dev.day25.dto.TelemetryResponse;
import com.sky2dev.day25.service.TelemetryService;
import com.sky2dev.day25.util.MarkerConstants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TelemetryResponse>>> getTelemetry() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.TELEMETRY_COLLECTED,
                "Telemetry records fetched successfully",
                telemetryService.getTelemetry()));
    }
}
