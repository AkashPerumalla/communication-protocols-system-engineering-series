package com.sky2dev.day22.websocket;

import com.sky2dev.day22.dto.AlarmAcknowledgeRequest;
import com.sky2dev.day22.dto.AlarmResponse;
import com.sky2dev.day22.dto.ApiResponse;
import com.sky2dev.day22.dto.ClientConnectRequest;
import com.sky2dev.day22.dto.ConnectedClientResponse;
import com.sky2dev.day22.dto.DashboardResponse;
import com.sky2dev.day22.service.AlarmService;
import com.sky2dev.day22.service.ConnectedClientService;
import com.sky2dev.day22.service.DashboardService;
import com.sky2dev.day22.service.WebSocketBroadcastService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MonitoringWebSocketController {

    private final ConnectedClientService connectedClientService;
    private final DashboardService dashboardService;
    private final AlarmService alarmService;
    private final WebSocketBroadcastService broadcastService;

    @MessageMapping("/connect")
    public void registerClient(@Valid ClientConnectRequest request,
            @Header(name = "simpSessionId", required = false) String sessionId) {
        String effectiveSessionId = sessionId == null ? "unknown-session" : sessionId;
        ConnectedClientResponse response = connectedClientService.connect(request.username(), effectiveSessionId);
        broadcastService.publish("/topic/clients", "CLIENT CONNECTED", "Client registered on monitoring bus", response);
        broadcastService.publish("/topic/dashboard", "WEBSOCKET CONNECTED", "WebSocket client connected", dashboardService.getDashboard());
    }

    @MessageMapping("/request-dashboard")
    public void requestDashboard() {
        DashboardResponse response = dashboardService.getDashboard();
        broadcastService.publish("/topic/dashboard", "DASHBOARD UPDATED", "Dashboard snapshot pushed", response);
    }

    @MessageMapping("/acknowledge")
    public void acknowledgeAlarm(@Valid AlarmAcknowledgeRequest request) {
        AlarmResponse acknowledged = alarmService.acknowledge(request.alarmId());
        broadcastService.publish("/topic/alarms", "ALARM ACKNOWLEDGED", "Alarm acknowledged by operator", acknowledged);
    }

    @org.springframework.messaging.handler.annotation.MessageExceptionHandler
    public ApiResponse<Void> handleWsException(Exception ex) {
        return ApiResponse.failure("WEBSOCKET MESSAGE ERROR", ex.getMessage(), null);
    }
}
