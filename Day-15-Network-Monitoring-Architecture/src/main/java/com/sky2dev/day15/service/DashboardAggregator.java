package com.sky2dev.day15.service;

import com.sky2dev.day15.dashboard.DashboardSummary;
import com.sky2dev.day15.entity.AlertSeverity;
import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.MonitoringMetric;
import com.sky2dev.day15.monitoring.MonitoringStatsCalculator;
import com.sky2dev.day15.repository.AlertRepository;
import com.sky2dev.day15.repository.DashboardSnapshotRepository;
import com.sky2dev.day15.repository.DeviceRepository;
import com.sky2dev.day15.repository.MonitoringMetricRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardAggregator {

    private final DeviceRepository deviceRepository;
    private final MonitoringMetricRepository monitoringMetricRepository;
    private final AlertRepository alertRepository;
    private final DashboardSnapshotRepository dashboardSnapshotRepository;
    private final MonitoringStatsCalculator monitoringStatsCalculator;

    @Transactional
    public DashboardSummary captureSnapshot() {
        List<Device> devices = deviceRepository.findAll();
        List<MonitoringMetric> latestMetrics = devices.stream()
                .map(this::latestMetric)
                .toList();
        long totalDevices = devices.size();
        long onlineDevices = monitoringStatsCalculator.onlineDevices(devices);
        long offlineDevices = monitoringStatsCalculator.offlineDevices(devices);
        long activeAlerts = alertRepository.countByActiveTrue();
        long criticalAlerts = alertRepository.countBySeverityAndActiveTrue(AlertSeverity.CRITICAL);
        double averageCpu = monitoringStatsCalculator.averageCpu(latestMetrics);
        double averageMemory = monitoringStatsCalculator.averageMemory(latestMetrics);
        DashboardSummary summary = new DashboardSummary(
                Instant.now(),
                totalDevices,
                onlineDevices,
                offlineDevices,
                activeAlerts,
                criticalAlerts,
                averageCpu,
                averageMemory);
        dashboardSnapshotRepository.save(com.sky2dev.day15.entity.DashboardSnapshot.builder()
                .generatedAt(summary.generatedAt())
                .totalDevices(summary.totalDevices())
                .onlineDevices(summary.onlineDevices())
                .offlineDevices(summary.offlineDevices())
                .activeAlerts(summary.activeAlerts())
                .criticalAlerts(summary.criticalAlerts())
                .averageCpu(summary.averageCpu())
                .averageMemory(summary.averageMemory())
                .build());
        return summary;
    }

    private MonitoringMetric latestMetric(Device device) {
        return monitoringMetricRepository.findTopByDeviceOrderByCapturedAtDesc(device)
                .orElseGet(() -> MonitoringMetric.builder()
                        .device(device)
                        .cpuUsage(device.getCpuUsage())
                        .memoryUsage(device.getMemoryUsage())
                        .uptime(device.getUptime())
                        .status(device.getStatus())
                        .temperature(device.getTemperature())
                        .interfaceStatus(device.getInterfaceStatus())
                        .capturedAt(Instant.now())
                        .source(com.sky2dev.day15.entity.MetricSource.POLLING)
                        .build());
    }
}
