package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.AlertEvaluateRequest;
import com.sky2dev.day20.dto.AlertEventDto;
import com.sky2dev.day20.dto.ApiResponse;
import com.sky2dev.day20.service.AlertEngine;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertEngine alertEngine;

    @GetMapping
    public ApiResponse<List<AlertEventDto>> listAlerts() {
        return new ApiResponse<>("ALERT GENERATED", "Alert history fetched", alertEngine.listAlerts());
    }

    @GetMapping("/active")
    public ApiResponse<List<AlertEventDto>> activeAlerts() {
        return new ApiResponse<>("ALERT GENERATED", "Active alerts fetched", alertEngine.listActiveAlerts());
    }

    @PostMapping("/evaluate")
    public ApiResponse<List<AlertEventDto>> evaluate(@RequestBody(required = false) AlertEvaluateRequest request) {
        String agentId = request == null ? null : request.agentId();
        return new ApiResponse<>("ALERT GENERATED", "Alert evaluation completed", alertEngine.evaluateThresholds(agentId));
    }
}
