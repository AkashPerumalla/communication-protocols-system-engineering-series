package com.sky2dev.day15.controller;

import com.sky2dev.day15.dto.DeviceResponse;
import com.sky2dev.day15.service.DeviceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public List<DeviceResponse> getDevices() {
        return deviceService.findAllDevices().stream().map(DeviceResponse::from).toList();
    }

    @GetMapping("/{id}")
    public DeviceResponse getDevice(@PathVariable Long id) {
        return DeviceResponse.from(deviceService.getDevice(id));
    }
}
