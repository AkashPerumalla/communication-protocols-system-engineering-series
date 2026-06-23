package com.sky2dev.day22.websocket;

import com.sky2dev.day22.monitoring.ConnectionMetricsService;
import com.sky2dev.day22.service.ConnectedClientService;
import com.sky2dev.day22.service.WebSocketBroadcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketSessionEventListener {

    private final ConnectionMetricsService connectionMetricsService;
    private final ConnectedClientService connectedClientService;
    private final WebSocketBroadcastService broadcastService;

    @EventListener
    public void onConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        connectionMetricsService.onConnect(sessionId);
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        connectionMetricsService.onDisconnect(sessionId);
        connectedClientService.disconnect(sessionId);
        broadcastService.publish("/topic/clients", "CLIENT DISCONNECTED", "Client disconnected", sessionId);
    }
}
