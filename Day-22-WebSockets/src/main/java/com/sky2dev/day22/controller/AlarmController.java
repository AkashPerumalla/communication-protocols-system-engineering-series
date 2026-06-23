package com.sky2dev.day22.controller;

import com.sky2dev.day22.dto.AlarmResponse;
import com.sky2dev.day22.dto.ApiResponse;
import com.sky2dev.day22.service.AlarmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ApiResponse<List<AlarmResponse>> getAlarms() {
        return ApiResponse.success("ALARM BROADCASTED", "Alarm events retrieved", alarmService.getAlarms());
    }

    @PostMapping("/{id}/acknowledge")
    public ApiResponse<AlarmResponse> acknowledge(@PathVariable Long id) {
        return ApiResponse.success("ALARM ACKNOWLEDGED", "Alarm acknowledged successfully", alarmService.acknowledge(id));
    }
}
