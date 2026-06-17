package com.sky2dev.day16.controller;

import com.sky2dev.day16.dto.AlarmDto;
import com.sky2dev.day16.dto.ApiResponse;
import com.sky2dev.day16.service.AlarmGenerationService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmGenerationService alarmGenerationService;

    public AlarmController(AlarmGenerationService alarmGenerationService) {
        this.alarmGenerationService = alarmGenerationService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ApiResponse<List<AlarmDto>> listAlarms() {
        return new ApiResponse<>("AUTO CORRECTION", "Alarm inventory", alarmGenerationService.allAlarms().stream().map(alarm -> new AlarmDto(alarm.getId(), alarm.getDevice().getId(), alarm.getDevice().getDeviceCode(), alarm.getSeverity().name(), alarm.getStatus().name(), alarm.getMetricName(), alarm.getMetricValue(), alarm.getThresholdValue(), alarm.getMessage(), alarm.getTriggeredAt(), alarm.getClearedAt(), alarm.getRecoveryNote())).toList());
    }

    @PostMapping("/clear/{alarmId}")
    @Transactional
    public ApiResponse<AlarmDto> clearAlarm(@PathVariable Long alarmId) {
        var activeAlarm = alarmGenerationService.allAlarms().stream().filter(alarm -> alarm.getId().equals(alarmId)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));
        var cleared = alarmGenerationService.clearAlarm(activeAlarm, "Cleared via alarm API");
        return new ApiResponse<>("REMOTE RECOVERY", "Alarm cleared", new AlarmDto(cleared.getId(), cleared.getDevice().getId(), cleared.getDevice().getDeviceCode(), cleared.getSeverity().name(), cleared.getStatus().name(), cleared.getMetricName(), cleared.getMetricValue(), cleared.getThresholdValue(), cleared.getMessage(), cleared.getTriggeredAt(), cleared.getClearedAt(), cleared.getRecoveryNote()));
    }
}
