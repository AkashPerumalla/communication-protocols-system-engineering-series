package com.sky2dev.day24.controller;

import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.entity.Device;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @GetMapping
    public ApiResponse<List<Device>> getDevices() {
        return ApiResponse.success(MarkerConstants.SCHEDULER_ACTIVE, "Device inventory fetched", deviceRepository.findAll());
    }
}
