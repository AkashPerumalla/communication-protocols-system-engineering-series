package com.sky2dev.day22.service;

import com.sky2dev.day22.dto.ApiResponse;
import com.sky2dev.day22.monitoring.ConnectionMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketBroadcastService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ConnectionMetricsService metricsService;

    public void publish(String topic, String marker, String message, Object payload) {
        messagingTemplate.convertAndSend(topic, ApiResponse.success(marker, message, payload));
        metricsService.onMessageSent();
        metricsService.onBroadcast();
    }
}
