package com.sky2dev.day22.controller;

import com.sky2dev.day22.dto.ApiResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
public class PlatformController {

    @GetMapping
    public ApiResponse<Map<String, Object>> platform() {
        return ApiResponse.success("REALTIME MONITORING ACTIVE", "Platform is operational", Map.of(
                "websocketEndpoint", "/ws-monitoring",
                "topics", new String[]{"/topic/telemetry", "/topic/alarms", "/topic/dashboard", "/topic/clients"},
                "appDestinations", new String[]{"/app/connect", "/app/acknowledge", "/app/request-dashboard"}));
    }
}
