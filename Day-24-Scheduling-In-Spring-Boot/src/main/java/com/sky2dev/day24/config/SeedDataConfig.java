package com.sky2dev.day24.config;

import com.sky2dev.day24.entity.AlarmEvent;
import com.sky2dev.day24.entity.AlarmSeverity;
import com.sky2dev.day24.entity.ArchivedRecord;
import com.sky2dev.day24.entity.Device;
import com.sky2dev.day24.entity.DeviceStatus;
import com.sky2dev.day24.entity.DeviceType;
import com.sky2dev.day24.entity.NotificationChannel;
import com.sky2dev.day24.entity.NotificationEvent;
import com.sky2dev.day24.entity.ReportRecord;
import com.sky2dev.day24.entity.ReportType;
import com.sky2dev.day24.entity.TaskExecutionLog;
import com.sky2dev.day24.entity.TaskExecutionStatus;
import com.sky2dev.day24.entity.TelemetryRecord;
import com.sky2dev.day24.repository.AlarmEventRepository;
import com.sky2dev.day24.repository.ArchivedRecordRepository;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.repository.NotificationEventRepository;
import com.sky2dev.day24.repository.ReportRecordRepository;
import com.sky2dev.day24.repository.TaskExecutionLogRepository;
import com.sky2dev.day24.repository.TelemetryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SeedDataConfig {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final ReportRecordRepository reportRecordRepository;
    private final TaskExecutionLogRepository taskExecutionLogRepository;
    private final ArchivedRecordRepository archivedRecordRepository;

    @Bean
    CommandLineRunner seedDay24Data() {
        return args -> {
            if (deviceRepository.count() > 0) {
                return;
            }

            List<Device> devices = seedDevices();
            seedTelemetry(devices);
            seedAlarms(devices);
            seedNotifications();
            seedReports();
            seedExecutionLogs();
            seedArchives();
        };
    }

    private List<Device> seedDevices() {
        List<Device> devices = List.of(
                buildDevice("Router-01", DeviceType.ROUTER, "10.24.0.1", DeviceStatus.ONLINE),
                buildDevice("Router-02", DeviceType.ROUTER, "10.24.0.2", DeviceStatus.ONLINE),
                buildDevice("Switch-01", DeviceType.SWITCH, "10.24.1.1", DeviceStatus.ONLINE),
                buildDevice("Switch-02", DeviceType.SWITCH, "10.24.1.2", DeviceStatus.WARNING),
                buildDevice("Satellite-Modem-01", DeviceType.MODEM, "10.24.2.1", DeviceStatus.WARNING),
                buildDevice("Satellite-Modem-02", DeviceType.MODEM, "10.24.2.2", DeviceStatus.OFFLINE),
                buildDevice("BUC-01", DeviceType.BUC, "10.24.3.1", DeviceStatus.ONLINE),
                buildDevice("LNB-01", DeviceType.LNB, "10.24.3.2", DeviceStatus.ONLINE),
                buildDevice("Hub-01", DeviceType.HUB, "10.24.4.1", DeviceStatus.MAINTENANCE),
                buildDevice("Sensor-01", DeviceType.SENSOR, "10.24.5.1", DeviceStatus.ONLINE)
        );
        return deviceRepository.saveAll(devices);
    }

    private Device buildDevice(String name, DeviceType type, String ip, DeviceStatus status) {
        Instant now = Instant.now();
        return Device.builder()
                .deviceName(name)
                .deviceType(type)
                .ipAddress(ip)
                .status(status)
                .lastSeen(now)
                .createdAt(now)
                .build();
    }

    private void seedTelemetry(List<Device> devices) {
        List<TelemetryRecord> records = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Device device = devices.get(i % devices.size());
            int profile = i % 3;
            records.add(TelemetryRecord.builder()
                    .deviceId(device.getId())
                    .cpuUsage(profile == 0 ? 35 : profile == 1 ? 75 : 95)
                    .memoryUsage(profile == 0 ? 45 : profile == 1 ? 80 : 98)
                    .temperature(profile == 0 ? 40 : profile == 1 ? 65 : 85)
                    .signalStrength(profile == 0 ? -55 : profile == 1 ? -75 : -90)
                    .timestamp(Instant.now().minusSeconds(100 - i))
                    .build());
        }
        telemetryRecordRepository.saveAll(records);
    }

    private void seedAlarms(List<Device> devices) {
        List<AlarmEvent> alarms = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            alarms.add(AlarmEvent.builder()
                    .deviceId(devices.get(i % devices.size()).getId())
                    .severity(i % 3 == 0 ? AlarmSeverity.CRITICAL : i % 3 == 1 ? AlarmSeverity.MAJOR : AlarmSeverity.WARNING)
                    .message("Seeded alarm " + (i + 1))
                    .acknowledged(i % 4 == 0)
                    .timestamp(Instant.now().minusSeconds(i * 20L))
                    .build());
        }
        alarmEventRepository.saveAll(alarms);
    }

    private void seedNotifications() {
        List<NotificationEvent> events = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            events.add(NotificationEvent.builder()
                    .channel(NotificationChannel.values()[i % NotificationChannel.values().length])
                    .message("Seed notification " + (i + 1))
                    .status("SENT")
                    .timestamp(Instant.now().minusSeconds(i * 15L))
                    .build());
        }
        notificationEventRepository.saveAll(events);
    }

    private void seedReports() {
        List<ReportRecord> reports = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            reports.add(ReportRecord.builder()
                    .reportType(ReportType.values()[i % ReportType.values().length])
                    .generatedAt(Instant.now().minusSeconds(i * 120L))
                    .summary("Seed report summary " + (i + 1))
                    .build());
        }
        reportRecordRepository.saveAll(reports);
    }

    private void seedExecutionLogs() {
        List<TaskExecutionLog> logs = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Instant start = Instant.now().minusSeconds(4000L - i * 10L);
            Instant end = start.plusMillis(500 + i);
            TaskExecutionStatus status = i % 10 == 0 ? TaskExecutionStatus.FAILED : TaskExecutionStatus.SUCCESS;
            logs.add(TaskExecutionLog.builder()
                    .taskName("SeedTask-" + (i + 1))
                    .startTime(start)
                    .endTime(end)
                    .status(status)
                    .executionDurationMs(500 + i)
                    .message(status == TaskExecutionStatus.SUCCESS ? "Completed" : "Failure simulated")
                    .build());
        }
        taskExecutionLogRepository.saveAll(logs);
    }

    private void seedArchives() {
        archivedRecordRepository.save(ArchivedRecord.builder()
                .sourceType("TELEMETRY")
                .archivedAt(Instant.now().minusSeconds(300))
                .recordCount(25)
                .build());
    }
}
