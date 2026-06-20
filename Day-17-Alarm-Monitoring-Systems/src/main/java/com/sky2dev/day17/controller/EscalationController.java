package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.EscalationDto;
import com.sky2dev.day17.service.AlarmManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/escalations")
public class EscalationController {

    private final AlarmManagementService alarmManagementService;

    public EscalationController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @GetMapping
    public ApiResponse<List<EscalationDto>> getEscalations() {
        return new ApiResponse<>("ALARM ESCALATED", "Escalation policies", alarmManagementService.getEscalations());
    }
}
