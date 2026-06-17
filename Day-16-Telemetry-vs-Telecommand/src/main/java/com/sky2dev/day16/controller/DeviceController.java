package com.sky2dev.day16.controller;

import com.sky2dev.day16.dto.ApiResponse;
import com.sky2dev.day16.dto.DeviceDto;
import com.sky2dev.day16.service.ManagedDeviceService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final ManagedDeviceService managedDeviceService;

    public DeviceController(ManagedDeviceService managedDeviceService) {
        this.managedDeviceService = managedDeviceService;
    }

    @GetMapping
    public ApiResponse<List<DeviceDto>> listDevices() {
        return new ApiResponse<>("NOC CONTROL DASHBOARD", "Managed device inventory", managedDeviceService.listDevices().stream().map(ManagedDeviceService::toDto).toList());
    }

    @GetMapping("/{deviceId}")
    public ApiResponse<DeviceDto> getDevice(@PathVariable Long deviceId) {
        return new ApiResponse<>("STATE UPDATED", "Single device view", ManagedDeviceService.toDto(managedDeviceService.getDevice(deviceId)));
    }
}
