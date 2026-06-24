package com.sky2dev.day23.config;

import com.sky2dev.day23.entity.AlarmEvent;
import com.sky2dev.day23.entity.AlarmSeverity;
import com.sky2dev.day23.entity.Device;
import com.sky2dev.day23.entity.DeviceStatus;
import com.sky2dev.day23.entity.NotificationChannel;
import com.sky2dev.day23.entity.NotificationEvent;
import com.sky2dev.day23.entity.NotificationStatus;
import com.sky2dev.day23.entity.TaskExecutionLog;
import com.sky2dev.day23.entity.TaskExecutionStatus;
import com.sky2dev.day23.entity.TelemetryRecord;
import com.sky2dev.day23.repository.AlarmEventRepository;
import com.sky2dev.day23.repository.DeviceRepository;
import com.sky2dev.day23.repository.NotificationEventRepository;
import com.sky2dev.day23.repository.TaskExecutionLogRepository;
import com.sky2dev.day23.repository.TelemetryRecordRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SeedDataConfig {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final TaskExecutionLogRepository taskExecutionLogRepository;

    @Bean
    CommandLineRunner seedDay23Data() {
        return args -> {
            if (deviceRepository.count() > 0) {
                return;
            }

            List<Device> devices = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                devices.add(Device.builder()
                        .name("NOC-Device-" + i)
                        .type((i % 2 == 0) ? "SATCOM_MODEM" : "TELECOM_ROUTER")
                        .status((i % 3 == 0) ? DeviceStatus.DEGRADED : DeviceStatus.ONLINE)
                        .ipAddress("10.23.0." + i)
                        .createdAt(Instant.parse("2026-01-01T00:00:00Z").plusSeconds(i * 60L))
                        .build());
            }
            List<Device> savedDevices = deviceRepository.saveAll(devices);

            List<TelemetryRecord> telemetry = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                long deviceId = savedDevices.get((i - 1) % savedDevices.size()).getId();
                telemetry.add(TelemetryRecord.builder()
                        .deviceId(deviceId)
                        .cpuUsage(40 + (i % 45))
                        .memoryUsage(35 + (i % 50))
                        .temperature(45 + (i % 30))
                        .signalStrength(-70 + (i % 18))
                        .timestamp(Instant.parse("2026-01-01T01:00:00Z").plusSeconds(i * 30L))
                        .build());
            }
            telemetryRecordRepository.saveAll(telemetry);

            List<AlarmEvent> alarms = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                long deviceId = savedDevices.get((i - 1) % savedDevices.size()).getId();
                alarms.add(AlarmEvent.builder()
                        .deviceId(deviceId)
                        .severity((i % 4 == 0) ? AlarmSeverity.CRITICAL : AlarmSeverity.HIGH)
                        .message("Seed alarm event " + i + " for device " + deviceId)
                        .acknowledged(i % 5 == 0)
                        .timestamp(Instant.parse("2026-01-01T02:00:00Z").plusSeconds(i * 45L))
                        .build());
            }
            alarmEventRepository.saveAll(alarms);

            List<NotificationEvent> notifications = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                NotificationChannel channel = (i % 3 == 0) ? NotificationChannel.WEBHOOK
                        : (i % 2 == 0) ? NotificationChannel.SMS : NotificationChannel.EMAIL;
                notifications.add(NotificationEvent.builder()
                        .channel(channel)
                        .recipient("recipient-" + i + "@sky2dev.com")
                        .status(NotificationStatus.SENT)
                        .timestamp(Instant.parse("2026-01-01T03:00:00Z").plusSeconds(i * 50L))
                        .build());
            }
            notificationEventRepository.saveAll(notifications);

            List<TaskExecutionLog> logs = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                Instant start = Instant.parse("2026-01-01T04:00:00Z").plusSeconds(i * 20L);
                logs.add(TaskExecutionLog.builder()
                        .taskName("SeedTask-" + i)
                        .threadName("Seed-Thread-" + ((i % 5) + 1))
                        .startTime(start)
                        .endTime(start.plusMillis(120 + i))
                        .duration(120 + i)
                        .status(TaskExecutionStatus.SUCCESS)
                        .build());
            }
            taskExecutionLogRepository.saveAll(logs);
        };
    }
}
