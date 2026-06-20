package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.AlarmDto;
import com.sky2dev.day17.service.AlarmManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmManagementService alarmManagementService;

    public AlarmController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @GetMapping
    public ApiResponse<List<AlarmDto>> getAlarms() {
        return new ApiResponse<>("ALARM GENERATED", "All alarms", alarmManagementService.getAlarms());
    }

    @GetMapping("/open")
    public ApiResponse<List<AlarmDto>> getOpenAlarms() {
        return new ApiResponse<>("ALARM GENERATED", "Open alarms", alarmManagementService.getOpenAlarms());
    }

    @GetMapping("/critical")
    public ApiResponse<List<AlarmDto>> getCriticalAlarms() {
        return new ApiResponse<>("SEVERITY ASSIGNED", "Critical alarms", alarmManagementService.getCriticalAlarms());
    }

    @GetMapping("/unacknowledged")
    public ApiResponse<List<AlarmDto>> getUnacknowledgedAlarms() {
        return new ApiResponse<>("ALARM ACKNOWLEDGED", "Unacknowledged alarms", alarmManagementService.getUnacknowledgedAlarms());
    }

    @PostMapping("/{id}/acknowledge")
    public ApiResponse<AlarmDto> acknowledge(@PathVariable Long id) {
        return new ApiResponse<>("ALARM ACKNOWLEDGED", "Alarm acknowledged", alarmManagementService.acknowledge(id));
    }

    @PostMapping("/{id}/escalate")
    public ApiResponse<AlarmDto> escalate(@PathVariable Long id) {
        return new ApiResponse<>("ALARM ESCALATED", "Alarm escalated", alarmManagementService.escalate(id));
    }

    @PostMapping("/{id}/resolve")
    public ApiResponse<AlarmDto> resolve(@PathVariable Long id) {
        return new ApiResponse<>("ALARM RESOLVED", "Alarm resolved", alarmManagementService.resolve(id));
    }

    @PostMapping("/{id}/close")
    public ApiResponse<AlarmDto> close(@PathVariable Long id) {
        return new ApiResponse<>("ALARM CLOSED", "Alarm closed", alarmManagementService.close(id));
    }
}
