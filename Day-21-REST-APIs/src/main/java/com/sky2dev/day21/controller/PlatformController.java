package com.sky2dev.day21.controller;

import com.sky2dev.day21.dto.ApiResponse;
import com.sky2dev.day21.dto.PlatformStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
@Tag(name = "Platform", description = "Platform health and service exposure endpoints")
public class PlatformController {

    @GetMapping
    @Operation(summary = "Get API platform status")
    public ApiResponse<PlatformStatusResponse> getPlatformStatus() {
        return ApiResponse.success(
                "API PLATFORM ACTIVE",
                "Device management platform is online and secured with HTTP Basic authentication",
                new PlatformStatusResponse("Device Management REST API Platform", "1.0.0", "lab", "HTTP Basic with roles"));
    }
}
