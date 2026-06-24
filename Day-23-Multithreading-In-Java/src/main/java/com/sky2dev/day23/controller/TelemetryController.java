package com.sky2dev.day23.controller;

import com.sky2dev.day23.dto.ApiResponse;
import com.sky2dev.day23.dto.TelemetryResponse;
import com.sky2dev.day23.service.TelemetryService;
import com.sky2dev.day23.util.MarkerConstants;
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
                "Telemetry stream processed by producer-consumer workers",
                telemetryService.getTelemetry()
        ));
    }
}
