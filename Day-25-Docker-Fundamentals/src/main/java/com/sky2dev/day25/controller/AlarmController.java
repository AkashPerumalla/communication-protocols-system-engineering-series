package com.sky2dev.day25.controller;

import com.sky2dev.day25.dto.AlarmResponse;
import com.sky2dev.day25.dto.ApiResponse;
import com.sky2dev.day25.service.AlarmService;
import com.sky2dev.day25.util.MarkerConstants;
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
                MarkerConstants.ALARM_GENERATED,
                "Alarm events fetched successfully",
                alarmService.getAlarms()));
    }
}
