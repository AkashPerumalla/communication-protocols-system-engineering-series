package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.AlarmSimulationRequest;
import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.SatComAlarmDto;
import com.sky2dev.day19.service.AlarmService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ApiResponse<List<SatComAlarmDto>> list() {
        return new ApiResponse<>("SATCOM ALARM GENERATED", "SatCom alarms listed", alarmService.listAlarms());
    }

    @PostMapping("/simulate")
    public ApiResponse<List<SatComAlarmDto>> simulate(@Valid @RequestBody AlarmSimulationRequest request) {
        return new ApiResponse<>("SATCOM ALARM GENERATED", "Alarm simulation completed", alarmService.simulate(request));
    }
}
