package com.sky2dev.day25.service;

import com.sky2dev.day25.dto.DashboardResponse;
import com.sky2dev.day25.entity.AlarmStatus;
import com.sky2dev.day25.entity.Device;
import com.sky2dev.day25.entity.DeviceStatus;
import com.sky2dev.day25.entity.NotificationStatus;
import com.sky2dev.day25.repository.AlarmEventRepository;
import com.sky2dev.day25.repository.DeviceRepository;
import com.sky2dev.day25.repository.NotificationEventRepository;
import com.sky2dev.day25.repository.ReportRecordRepository;
import com.sky2dev.day25.repository.TelemetryRecordRepository;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final ReportRecordRepository reportRecordRepository;

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {
        var devices = deviceRepository.findAll();
        long totalDevices = devices.size();
        long onlineDevices = devices.stream().filter(d -> d.getStatus() == DeviceStatus.ONLINE).count();
        long degradedDevices = devices.stream().filter(d -> d.getStatus() == DeviceStatus.DEGRADED).count();
        long offlineDevices = devices.stream().filter(d -> d.getStatus() == DeviceStatus.OFFLINE).count();
        long maintenanceDevices = devices.stream().filter(d -> d.getStatus() == DeviceStatus.MAINTENANCE).count();

        long openAlarms = alarmEventRepository.findAll().stream()
                .filter(a -> a.getStatus() == AlarmStatus.OPEN).count();
        long acknowledgedAlarms = alarmEventRepository.findAll().stream()
                .filter(a -> a.getStatus() == AlarmStatus.ACKNOWLEDGED).count();
        long resolvedAlarms = alarmEventRepository.findAll().stream()
                .filter(a -> a.getStatus() == AlarmStatus.RESOLVED).count();

        Map<String, Double> avgMetricByType = telemetryRecordRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        record -> record.getDevice().getDeviceType().name(),
                        Collectors.averagingDouble(record -> record.getMetricValue() == null ? 0.0 : record.getMetricValue())));

        return new DashboardResponse(
                totalDevices,
                onlineDevices,
                degradedDevices,
                offlineDevices,
                maintenanceDevices,
                telemetryRecordRepository.count(),
                openAlarms,
                acknowledgedAlarms,
                resolvedAlarms,
                notificationEventRepository.countByStatus(NotificationStatus.SENT),
                reportRecordRepository.count(),
                avgMetricByType);
    }
}
