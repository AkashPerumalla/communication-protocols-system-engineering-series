package com.sky2dev.day22.service;

import com.sky2dev.day22.dto.DashboardResponse;
import com.sky2dev.day22.entity.AlarmSeverity;
import com.sky2dev.day22.entity.DeviceStatus;
import com.sky2dev.day22.monitoring.ConnectionMetricsService;
import com.sky2dev.day22.repository.AlarmEventRepository;
import com.sky2dev.day22.repository.ConnectedClientRepository;
import com.sky2dev.day22.repository.DeviceRepository;
import com.sky2dev.day22.repository.TelemetryMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DeviceRepository deviceRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final TelemetryMetricRepository telemetryMetricRepository;
    private final ConnectedClientRepository connectedClientRepository;
    private final ConnectionMetricsService connectionMetricsService;

    public DashboardResponse getDashboard() {
        long totalDevices = deviceRepository.count();
        long onlineDevices = deviceRepository.countByStatus(DeviceStatus.ONLINE);
        long offlineDevices = deviceRepository.countByStatus(DeviceStatus.OFFLINE);
        long activeAlarms = alarmEventRepository.countByAcknowledgedFalse();
        long criticalAlarms = alarmEventRepository.countByAcknowledgedFalseAndSeverity(AlarmSeverity.CRITICAL);

        double avgCpu = telemetryMetricRepository.avgCpuUsage();
        double avgMemory = telemetryMetricRepository.avgMemoryUsage();
        double avgSignal = telemetryMetricRepository.avgSignalStrength();

        long connectedClients = connectedClientRepository.countByActiveTrue();

        return new DashboardResponse(
                totalDevices,
                onlineDevices,
                offlineDevices,
                activeAlarms,
                criticalAlarms,
                round(avgCpu),
                round(avgMemory),
                round(avgSignal),
                connectedClients,
                connectionMetricsService.getActiveSessions(),
                connectionMetricsService.getTotalConnections(),
                connectionMetricsService.getMessagesSent(),
                connectionMetricsService.getMessagesReceived(),
                connectionMetricsService.getBroadcastCount());
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
