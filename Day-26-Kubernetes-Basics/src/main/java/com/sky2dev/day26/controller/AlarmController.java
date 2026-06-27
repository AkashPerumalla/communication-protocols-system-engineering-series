package com.sky2dev.day26.controller;

import com.sky2dev.day26.dto.AlarmResponse;
import com.sky2dev.day26.dto.ApiResponse;
import com.sky2dev.day26.service.AlarmService;
import com.sky2dev.day26.constants.MarkerConstants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AlarmResponse>>> getAlarms() {
        return ResponseEntity.ok(ApiResponse.success(
            MarkerConstants.APPLICATION_HEALTHY,
                "Alarm events fetched successfully",
                alarmService.getAlarms()));
    }
}
