package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.RecoveryRequest;
import com.sky2dev.day18.service.AutomationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;

    @PostMapping("/recover/{deviceId}")
    public ApiResponse<List<String>> recover(@PathVariable Long deviceId, @Valid @RequestBody RecoveryRequest request) {
        return new ApiResponse<>(
                "AUTO RECOVERY COMPLETE",
                "Automation and recovery workflows executed",
                automationService.recoverDevice(deviceId, request.triggeredBy())
        );
    }
}
