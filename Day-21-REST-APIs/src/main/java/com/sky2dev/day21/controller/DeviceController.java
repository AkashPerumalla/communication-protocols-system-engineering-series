package com.sky2dev.day21.controller;

import com.sky2dev.day21.dto.ApiResponse;
import com.sky2dev.day21.dto.DeviceRequest;
import com.sky2dev.day21.dto.DeviceResponse;
import com.sky2dev.day21.dto.PageResponse;
import com.sky2dev.day21.entity.DeviceStatus;
import com.sky2dev.day21.entity.DeviceType;
import com.sky2dev.day21.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Tag(name = "Devices", description = "Managed device inventory and lifecycle APIs")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    @Operation(summary = "List managed devices")
    public ApiResponse<PageResponse<DeviceResponse>> getAllDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "hostname") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ApiResponse.success("DEVICE RETRIEVED", "Managed device inventory retrieved", deviceService.getAllDevices(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by id")
    public ApiResponse<DeviceResponse> getDevice(@PathVariable Long id) {
        return ApiResponse.success("DEVICE RETRIEVED", "Managed device details retrieved", deviceService.getDevice(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a managed device")
    public ApiResponse<DeviceResponse> createDevice(@Valid @RequestBody DeviceRequest request) {
        return ApiResponse.success("DEVICE CREATED", "VALIDATION PASSED. Device created successfully", deviceService.createDevice(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a managed device")
    public ApiResponse<DeviceResponse> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceRequest request) {
        return ApiResponse.success("DEVICE UPDATED", "VALIDATION PASSED. Device updated successfully", deviceService.updateDevice(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a managed device")
    public ApiResponse<Map<String, Object>> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ApiResponse.success("DEVICE DELETED", "Managed device deleted successfully", Map.of("deletedId", id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search devices using keyword and optional filters")
    public ApiResponse<PageResponse<DeviceResponse>> searchDevices(
            @Parameter(description = "Keyword across hostname, IP, vendor, model, and location") @RequestParam(required = false, name = "q") String keyword,
            @RequestParam(required = false) DeviceStatus status,
            @RequestParam(required = false) DeviceType type,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "hostname") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ApiResponse.success("DEVICE SEARCH COMPLETE", "Search results generated for managed device inventory", deviceService.searchDevices(keyword, status, type, location, page, size, sortBy, direction));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Find devices by status")
    public ApiResponse<PageResponse<DeviceResponse>> findByStatus(
            @PathVariable DeviceStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "hostname") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ApiResponse.success("DEVICE RETRIEVED", "Devices filtered by status", deviceService.findByStatus(status, page, size, sortBy, direction));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Find devices by type")
    public ApiResponse<PageResponse<DeviceResponse>> findByType(
            @PathVariable DeviceType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "hostname") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ApiResponse.success("DEVICE RETRIEVED", "Devices filtered by type", deviceService.findByType(type, page, size, sortBy, direction));
    }

    @GetMapping("/location/{location}")
    @Operation(summary = "Find devices by location")
    public ApiResponse<PageResponse<DeviceResponse>> findByLocation(
            @PathVariable String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "hostname") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ApiResponse.success("DEVICE RETRIEVED", "Devices filtered by location", deviceService.findByLocation(location, page, size, sortBy, direction));
    }
}
