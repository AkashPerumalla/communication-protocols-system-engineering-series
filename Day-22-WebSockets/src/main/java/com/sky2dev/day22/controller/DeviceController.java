package com.sky2dev.day22.controller;

import com.sky2dev.day22.dto.ApiResponse;
import com.sky2dev.day22.dto.DeviceResponse;
import com.sky2dev.day22.service.DeviceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ApiResponse<List<DeviceResponse>> getDevices() {
        return ApiResponse.success("REALTIME MONITORING ACTIVE", "Devices retrieved", deviceService.getAllDevices());
    }
}
