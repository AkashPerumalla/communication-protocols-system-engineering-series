package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.DeviceHealthDto;
import com.sky2dev.day18.dto.HubDeviceDto;
import com.sky2dev.day18.service.HubMonitoringService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final HubMonitoringService hubMonitoringService;

    @GetMapping
    public ApiResponse<List<HubDeviceDto>> getDevices() {
        return new ApiResponse<>(
                "HUB MONITORING ACTIVE",
                "Hub devices discovered successfully",
                hubMonitoringService.discoverDevices()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<HubDeviceDto> getDevice(@PathVariable Long id) {
        return new ApiResponse<>(
                "HUB MONITORING ACTIVE",
                "Hub device loaded successfully",
                hubMonitoringService.getDevice(id)
        );
    }

    @GetMapping("/health")
    public ApiResponse<List<DeviceHealthDto>> deviceHealth() {
        return new ApiResponse<>(
                "HUB MONITORING ACTIVE",
                "Device health computed for hub operations",
                hubMonitoringService.getDeviceHealth()
        );
    }
}
