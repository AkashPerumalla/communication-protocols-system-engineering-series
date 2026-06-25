package com.sky2dev.day24.controller;

import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.entity.AlarmEvent;
import com.sky2dev.day24.repository.AlarmEventRepository;
import com.sky2dev.day24.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmEventRepository alarmEventRepository;

    @GetMapping
    public ApiResponse<List<AlarmEvent>> getAlarms() {
        return ApiResponse.success(MarkerConstants.ALARM_GENERATED, "Alarm list fetched", alarmEventRepository.findAll());
    }
}
