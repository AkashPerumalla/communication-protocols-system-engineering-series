package com.sky2dev.day17.controller;

import com.sky2dev.day17.dto.ApiResponse;
import com.sky2dev.day17.dto.DeviceDto;
import com.sky2dev.day17.service.AlarmManagementService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final AlarmManagementService alarmManagementService;

    public DeviceController(AlarmManagementService alarmManagementService) {
        this.alarmManagementService = alarmManagementService;
    }

    @GetMapping
    public ApiResponse<List<DeviceDto>> getDevices() {
        return new ApiResponse<>("THRESHOLD DETECTED", "Managed device inventory", alarmManagementService.getDevices());
    }
}
