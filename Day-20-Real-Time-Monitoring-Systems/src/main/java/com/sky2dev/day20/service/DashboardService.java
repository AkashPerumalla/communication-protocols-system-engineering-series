package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.DashboardSnapshotDto;
import com.sky2dev.day20.entity.AgentStatus;
import com.sky2dev.day20.entity.AlertSeverity;
import com.sky2dev.day20.entity.AlertStatus;
import com.sky2dev.day20.entity.DashboardSnapshot;
import com.sky2dev.day20.entity.MetricRecord;
import com.sky2dev.day20.repository.AlertEventRepository;
import com.sky2dev.day20.repository.DashboardSnapshotRepository;
import com.sky2dev.day20.repository.MonitoringAgentRepository;
import java.time.Instant;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MonitoringAgentRepository monitoringAgentRepository;
    private final AlertEventRepository alertEventRepository;
    private final DashboardSnapshotRepository dashboardSnapshotRepository;
    private final MetricsCollectionService metricsCollectionService;

    @Scheduled(fixedDelay = 6000, initialDelay = 3500)
    public void scheduledRefresh() {
        buildSnapshot();
    }

    public DashboardSnapshotDto buildSnapshot() {
        Map<String, MetricRecord> latest = metricsCollectionService.latestByAgent();
        double avgCpu = latest.values().stream().mapToDouble(MetricRecord::getCpuUsage).average().orElse(0.0);
        double avgMem = latest.values().stream().mapToDouble(MetricRecord::getMemoryUsage).average().orElse(0.0);
        double avgDisk = latest.values().stream().mapToDouble(MetricRecord::getDiskUsage).average().orElse(0.0);

        DashboardSnapshot snapshot = DashboardSnapshot.builder()
                .timestamp(Instant.now())
                .activeAgents(monitoringAgentRepository.countByStatus(AgentStatus.ONLINE))
                .activeAlerts(alertEventRepository.countByStatus(AlertStatus.ACTIVE))
                .criticalAlerts(alertEventRepository.countBySeverityAndStatus(AlertSeverity.CRITICAL, AlertStatus.ACTIVE))
                .averageCpu(avgCpu)
                .averageMemory(avgMem)
                .averageDisk(avgDisk)
                .build();

        return DashboardSnapshotDto.from(dashboardSnapshotRepository.save(snapshot));
    }

    public DashboardSnapshotDto latestSnapshot() {
        return dashboardSnapshotRepository.findTop1ByOrderByTimestampDesc()
                .map(DashboardSnapshotDto::from)
                .orElseGet(this::buildSnapshot);
    }
}
