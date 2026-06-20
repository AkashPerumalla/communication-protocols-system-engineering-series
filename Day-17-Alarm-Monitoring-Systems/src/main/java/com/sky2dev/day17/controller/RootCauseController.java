package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.RootCauseDto;
import com.sky2dev.day17.service.AlarmManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rca")
public class RootCauseController {

    private final AlarmManagementService alarmManagementService;

    public RootCauseController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @GetMapping
    public ApiResponse<List<RootCauseDto>> getRootCauses() {
        return new ApiResponse<>("ROOT CAUSE IDENTIFIED", "RCA results", alarmManagementService.getRootCauses());
    }
}
