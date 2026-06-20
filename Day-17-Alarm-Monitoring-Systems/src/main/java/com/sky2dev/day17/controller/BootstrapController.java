package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.AlarmDto;
import com.sky2dev.day17.service.AlarmManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bootstrap")
public class BootstrapController {

    private final AlarmManagementService alarmManagementService;

    public BootstrapController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @PostMapping("/rebuild-alarms")
    public ApiResponse<List<AlarmDto>> rebuild() {
        return new ApiResponse<>("ALARM GENERATED", "Alarm rebuild complete", alarmManagementService.rebuildAlarmsFromMetrics().stream().map(AlarmDto::from).toList());
    }
}
