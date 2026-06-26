package com.sky2dev.day25.config;

import com.sky2dev.day25.entity.AlarmEvent;
import com.sky2dev.day25.entity.AlarmSeverity;
import com.sky2dev.day25.entity.AlarmStatus;
import com.sky2dev.day25.entity.Device;
import com.sky2dev.day25.entity.DeviceStatus;
import com.sky2dev.day25.entity.DeviceType;
import com.sky2dev.day25.entity.NotificationChannel;
import com.sky2dev.day25.entity.NotificationEvent;
import com.sky2dev.day25.entity.NotificationStatus;
import com.sky2dev.day25.entity.ReportRecord;
import com.sky2dev.day25.entity.ReportType;
import com.sky2dev.day25.entity.TelemetryRecord;
import com.sky2dev.day25.repository.AlarmEventRepository;
import com.sky2dev.day25.repository.DeviceRepository;
import com.sky2dev.day25.repository.NotificationEventRepository;
import com.sky2dev.day25.repository.ReportRecordRepository;
import com.sky2dev.day25.repository.TelemetryRecordRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final ReportRecordRepository reportRecordRepository;

    @Override
    public void run(String... args) {
        if (deviceRepository.count() > 0) {
            return;
        }

        LocalDateTime base = LocalDateTime.of(2026, 1, 1, 10, 0);

        List<Device> devices = seedDevices(base);
        seedTelemetry(devices, base);
        List<AlarmEvent> alarms = seedAlarms(devices, base);
        seedNotifications(devices, alarms, base);
        seedReports(base);

        log.info("Deterministic seed completed: devices={}, telemetry={}, alarms={}, notifications={}, reports={}",
                deviceRepository.count(),
                telemetryRecordRepository.count(),
                alarmEventRepository.count(),
                notificationEventRepository.count(),
                reportRecordRepository.count());
    }

    private List<Device> seedDevices(LocalDateTime base) {
        List<Device> devices = List.of(
                buildDevice("Router-01", "10.10.1.1", DeviceType.ROUTER, DeviceStatus.ONLINE, "NOC-Core", base),
                buildDevice("Router-02", "10.10.1.2", DeviceType.ROUTER, DeviceStatus.DEGRADED, "NOC-Edge", base.plusMinutes(1)),
                buildDevice("Switch-01", "10.10.2.1", DeviceType.SWITCH, DeviceStatus.ONLINE, "NOC-Core", base.plusMinutes(2)),
                buildDevice("Switch-02", "10.10.2.2", DeviceType.SWITCH, DeviceStatus.ONLINE, "NOC-Edge", base.plusMinutes(3)),
                buildDevice("Satellite-Modem-01", "10.10.3.1", DeviceType.SATELLITE_MODEM, DeviceStatus.ONLINE, "SatCom-West", base.plusMinutes(4)),
                buildDevice("Satellite-Modem-02", "10.10.3.2", DeviceType.SATELLITE_MODEM, DeviceStatus.DEGRADED, "SatCom-East", base.plusMinutes(5)),
                buildDevice("Hub-01", "10.10.4.1", DeviceType.HUB, DeviceStatus.ONLINE, "Hub-Primary", base.plusMinutes(6)),
                buildDevice("BUC-01", "10.10.5.1", DeviceType.BUC, DeviceStatus.MAINTENANCE, "Uplink-Site", base.plusMinutes(7)),
                buildDevice("LNB-01", "10.10.6.1", DeviceType.LNB, DeviceStatus.ONLINE, "Downlink-Site", base.plusMinutes(8)),
                buildDevice("Sensor-01", "10.10.7.1", DeviceType.SENSOR, DeviceStatus.OFFLINE, "Field-Station", base.plusMinutes(9)));
        return deviceRepository.saveAll(devices);
    }

    private Device buildDevice(String name, String ip, DeviceType type, DeviceStatus status, String region, LocalDateTime timestamp) {
        return Device.builder()
                .name(name)
                .ipAddress(ip)
                .deviceType(type)
                .status(status)
                .region(region)
                .lastSeen(timestamp)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }

    private void seedTelemetry(List<Device> devices, LocalDateTime base) {
        List<TelemetryRecord> telemetryRecords = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Device device = devices.get(i % devices.size());
            double value = 30.0 + (i % 25) * 1.7;
            telemetryRecords.add(TelemetryRecord.builder()
                    .device(device)
                    .metricName((i % 2 == 0) ? "latency_ms" : "snr_db")
                    .metricValue(value)
                    .unit((i % 2 == 0) ? "ms" : "dB")
                    .qualityScore(80 + (i % 20))
                    .collectedAt(base.plusSeconds(i * 30L))
                    .build());
        }
        telemetryRecordRepository.saveAll(telemetryRecords);
    }

    private List<AlarmEvent> seedAlarms(List<Device> devices, LocalDateTime base) {
        List<AlarmEvent> alarms = new ArrayList<>();
        AlarmSeverity[] severities = AlarmSeverity.values();
        AlarmStatus[] statuses = AlarmStatus.values();

        for (int i = 0; i < 20; i++) {
            AlarmStatus status = statuses[i % statuses.length];
            LocalDateTime raised = base.plusMinutes(i * 3L);
            alarms.add(AlarmEvent.builder()
                    .device(devices.get(i % devices.size()))
                    .code("ALM-" + String.format("%03d", i + 1))
                    .description("Telemetry threshold breach detected on monitored interface")
                    .severity(severities[i % severities.length])
                    .status(status)
                    .raisedAt(raised)
                    .acknowledgedAt(status == AlarmStatus.ACKNOWLEDGED || status == AlarmStatus.RESOLVED ? raised.plusMinutes(2) : null)
                    .resolvedAt(status == AlarmStatus.RESOLVED ? raised.plusMinutes(7) : null)
                    .build());
        }

        return alarmEventRepository.saveAll(alarms);
    }

    private void seedNotifications(List<Device> devices, List<AlarmEvent> alarms, LocalDateTime base) {
        List<NotificationEvent> notifications = new ArrayList<>();
        NotificationChannel[] channels = NotificationChannel.values();
        NotificationStatus[] statuses = NotificationStatus.values();

        for (int i = 0; i < 20; i++) {
            notifications.add(NotificationEvent.builder()
                    .alarm(alarms.get(i % alarms.size()))
                    .device(devices.get(i % devices.size()))
                    .channel(channels[i % channels.length])
                    .recipient("noc-team-" + ((i % 4) + 1) + "@sky2dev.com")
                    .message("Notification for alarm " + alarms.get(i % alarms.size()).getCode())
                    .status(statuses[i % statuses.length])
                    .sentAt(base.plusMinutes(i))
                    .build());
        }

        notificationEventRepository.saveAll(notifications);
    }

    private void seedReports(LocalDateTime base) {
        List<ReportRecord> reports = new ArrayList<>();
        ReportType[] types = ReportType.values();

        for (int i = 0; i < 10; i++) {
            LocalDateTime start = base.minusHours(24L + (i * 2L));
            LocalDateTime end = start.plusHours(2);
            reports.add(ReportRecord.builder()
                    .reportName("NOC-Report-" + String.format("%02d", i + 1))
                    .reportType(types[i % types.length])
                    .periodStart(start)
                    .periodEnd(end)
                    .generatedAt(base.plusMinutes(i * 10L))
                    .summary("Automated report for telecom operations window " + (i + 1))
                    .generatedBy("docker-platform")
                    .build());
        }

        reportRecordRepository.saveAll(reports);
    }
}
