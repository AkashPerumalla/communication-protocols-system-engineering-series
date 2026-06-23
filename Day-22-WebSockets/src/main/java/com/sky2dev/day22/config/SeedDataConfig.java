package com.sky2dev.day22.config;

import com.sky2dev.day22.entity.AlarmEvent;
import com.sky2dev.day22.entity.AlarmSeverity;
import com.sky2dev.day22.entity.ConnectedClient;
import com.sky2dev.day22.entity.Device;
import com.sky2dev.day22.entity.DeviceStatus;
import com.sky2dev.day22.entity.DeviceType;
import com.sky2dev.day22.entity.TelemetryMetric;
import com.sky2dev.day22.repository.AlarmEventRepository;
import com.sky2dev.day22.repository.ConnectedClientRepository;
import com.sky2dev.day22.repository.DeviceRepository;
import com.sky2dev.day22.repository.TelemetryMetricRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seedData(DeviceRepository deviceRepository,
            TelemetryMetricRepository telemetryMetricRepository,
            AlarmEventRepository alarmEventRepository,
            ConnectedClientRepository connectedClientRepository) {
        return args -> {
            if (deviceRepository.count() > 0) {
                return;
            }

            Instant baseTime = Instant.parse("2026-06-22T10:00:00Z");

            List<Device> devices = List.of(
                    Device.builder().hostname("Router-01").ipAddress("10.22.0.1").type(DeviceType.ROUTER).status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Switch-01").ipAddress("10.22.0.2").type(DeviceType.SWITCH).status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Hub-01").ipAddress("10.22.0.3").type(DeviceType.HUB).status(DeviceStatus.DEGRADED).build(),
                    Device.builder().hostname("Modem-01").ipAddress("10.22.0.4").type(DeviceType.MODEM).status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("BUC-01").ipAddress("10.22.0.5").type(DeviceType.BUC).status(DeviceStatus.OFFLINE).build(),
                    Device.builder().hostname("LNB-01").ipAddress("10.22.0.6").type(DeviceType.LNB).status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Server-01").ipAddress("10.22.0.7").type(DeviceType.SERVER).status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Server-02").ipAddress("10.22.0.8").type(DeviceType.SERVER).status(DeviceStatus.MAINTENANCE).build(),
                    Device.builder().hostname("Sensor-01").ipAddress("10.22.0.9").type(DeviceType.SENSOR).status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Sensor-02").ipAddress("10.22.0.10").type(DeviceType.SENSOR).status(DeviceStatus.DEGRADED).build());

            List<Device> savedDevices = deviceRepository.saveAll(devices);

            List<TelemetryMetric> metrics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Device device = savedDevices.get(i % savedDevices.size());
                double cpu = Math.min(99, 35 + (i % 50));
                double memory = Math.min(99, 40 + ((i * 3) % 55));
                double temperature = Math.min(95, 32 + ((i * 2) % 50));
                double signal = -95 + (i % 40);
                metrics.add(TelemetryMetric.builder()
                        .deviceId(device.getId())
                        .cpuUsage(round(cpu))
                        .memoryUsage(round(memory))
                        .temperature(round(temperature))
                        .signalStrength(round(signal))
                        .timestamp(baseTime.plusSeconds(i * 30L))
                        .build());
            }
            telemetryMetricRepository.saveAll(metrics);

            List<AlarmEvent> alarms = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                AlarmSeverity severity = (i % 3 == 0) ? AlarmSeverity.CRITICAL : (i % 3 == 1) ? AlarmSeverity.MAJOR
                        : AlarmSeverity.WARNING;
                alarms.add(AlarmEvent.builder()
                        .deviceId(savedDevices.get(i).getId())
                        .severity(severity)
                        .message("Seed alarm #" + (i + 1) + " for " + savedDevices.get(i).getHostname())
                        .acknowledged(false)
                        .timestamp(baseTime.plusSeconds(5000 + i * 60L))
                        .build());
            }
            alarmEventRepository.saveAll(alarms);

            List<ConnectedClient> clients = List.of(
                    ConnectedClient.builder().username("noc-operator-01").sessionId("seed-session-01").connectedAt(baseTime.plusSeconds(60)).active(true).build(),
                    ConnectedClient.builder().username("noc-operator-02").sessionId("seed-session-02").connectedAt(baseTime.plusSeconds(120)).active(true).build(),
                    ConnectedClient.builder().username("field-engineer-01").sessionId("seed-session-03").connectedAt(baseTime.plusSeconds(180)).active(true).build(),
                    ConnectedClient.builder().username("satcom-supervisor").sessionId("seed-session-04").connectedAt(baseTime.plusSeconds(240)).active(true).build(),
                    ConnectedClient.builder().username("telemetry-analyst").sessionId("seed-session-05").connectedAt(baseTime.plusSeconds(300)).active(true).build());
            connectedClientRepository.saveAll(clients);
        };
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
