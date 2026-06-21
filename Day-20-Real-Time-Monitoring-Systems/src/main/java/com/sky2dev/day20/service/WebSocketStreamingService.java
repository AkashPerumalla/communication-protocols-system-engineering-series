package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.DashboardSnapshotDto;
import com.sky2dev.day20.dto.MetricRecordDto;
import com.sky2dev.day20.dto.AlertEventDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketStreamingService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MetricsCollectionService metricsCollectionService;
    private final AlertEngine alertEngine;
    private final DashboardService dashboardService;

    @Scheduled(fixedDelay = 5000, initialDelay = 4000)
    public void broadcastRealtimeUpdates() {
        List<MetricRecordDto> metrics = metricsCollectionService.listRecentMetrics();
        List<AlertEventDto> alerts = alertEngine.listActiveAlerts();
        DashboardSnapshotDto dashboard = dashboardService.latestSnapshot();

        messagingTemplate.convertAndSend("/topic/metrics", metrics);
        messagingTemplate.convertAndSend("/topic/alerts", alerts);
        messagingTemplate.convertAndSend("/topic/dashboard", dashboard);
        log.info("REALTIME STREAM ACTIVE");
    }
}
