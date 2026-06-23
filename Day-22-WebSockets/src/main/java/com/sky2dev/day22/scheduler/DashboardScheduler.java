package com.sky2dev.day22.scheduler;

import com.sky2dev.day22.service.DashboardService;
import com.sky2dev.day22.service.WebSocketBroadcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "app.scheduling.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class DashboardScheduler {

    private final DashboardService dashboardService;
    private final WebSocketBroadcastService broadcastService;

    @Scheduled(fixedRate = 15000)
    public void updateDashboard() {
        broadcastService.publish("/topic/dashboard", "DASHBOARD UPDATED", "Dashboard refresh pushed to clients",
                dashboardService.getDashboard());
    }
}
