package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.ApiResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

    @GetMapping("/status")
    public ApiResponse<Map<String, Object>> status() {
        return new ApiResponse<>(
                "REALTIME STREAM ACTIVE",
                "WebSocket stream endpoint configured",
                Map.of(
                        "endpoint", "/ws-monitoring",
                        "topics", java.util.List.of("/topic/metrics", "/topic/alerts", "/topic/dashboard")));
    }
}
