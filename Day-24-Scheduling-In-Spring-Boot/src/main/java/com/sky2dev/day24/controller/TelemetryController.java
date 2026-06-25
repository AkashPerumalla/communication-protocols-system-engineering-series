package com.sky2dev.day24.controller;

import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.entity.TelemetryRecord;
import com.sky2dev.day24.repository.TelemetryRecordRepository;
import com.sky2dev.day24.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryRecordRepository telemetryRecordRepository;

    @GetMapping
    public ApiResponse<List<TelemetryRecord>> getTelemetry() {
        return ApiResponse.success(MarkerConstants.TELEMETRY_COLLECTED, "Telemetry stream snapshot fetched", telemetryRecordRepository.findAll());
    }
}
