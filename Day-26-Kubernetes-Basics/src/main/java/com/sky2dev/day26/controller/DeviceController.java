package com.sky2dev.day26.controller;

import com.sky2dev.day26.dto.ApiResponse;
import com.sky2dev.day26.dto.DeviceRequest;
import com.sky2dev.day26.dto.DeviceResponse;
import com.sky2dev.day26.service.DeviceService;
import com.sky2dev.day26.constants.MarkerConstants;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            MarkerConstants.POD_RUNNING,
                "Devices fetched successfully",
                deviceService.getAllDevices()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DeviceResponse>> createDevice(@Valid @RequestBody DeviceRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
            MarkerConstants.DEPLOYMENT_CREATED,
                "Device created successfully",
                deviceService.createDevice(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceResponse>> updateDevice(
            @PathVariable Long id,
            @Valid @RequestBody DeviceRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
            MarkerConstants.DEPLOYMENT_CREATED,
                "Device updated successfully",
                deviceService.updateDevice(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok(ApiResponse.success(
            MarkerConstants.DEPLOYMENT_CREATED,
                "Device deleted successfully",
                null));
    }
}
