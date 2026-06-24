package com.sky2dev.day23.controller;

import com.sky2dev.day23.dto.ApiResponse;
import com.sky2dev.day23.dto.DeviceResponse;
import com.sky2dev.day23.service.DeviceService;
import com.sky2dev.day23.util.MarkerConstants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DeviceResponse>>> getDevices() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.THREAD_POOL_ACTIVE,
                "Concurrent device inventory retrieved",
                deviceService.getDevices()
        ));
    }
}
