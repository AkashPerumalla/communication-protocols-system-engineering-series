package com.sky2dev.day26.controller;

import com.sky2dev.day26.constants.MarkerConstants;
import com.sky2dev.day26.dto.ApiResponse;
import com.sky2dev.day26.dto.PlatformStatusResponse;
import com.sky2dev.day26.service.PlatformStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformStatusService platformStatusService;

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<PlatformStatusResponse>> getPlatformStatus() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.KUBERNETES_READY,
                MarkerConstants.APPLICATION_HEALTHY,
                platformStatusService.getPlatformStatus()));
    }
}
