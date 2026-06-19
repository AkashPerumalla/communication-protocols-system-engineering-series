package com.sky2dev.day18.config;

import com.sky2dev.day18.model.Alarm;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.AlarmStatus;
import com.sky2dev.day18.model.AlarmType;
import com.sky2dev.day18.model.CommandType;
import com.sky2dev.day18.model.ControlCommand;
import com.sky2dev.day18.model.DeliveryStatus;
import com.sky2dev.day18.model.DeviceMetric;
import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.model.DeviceType;
import com.sky2dev.day18.model.ExecutionStatus;
import com.sky2dev.day18.model.HubDevice;
import com.sky2dev.day18.model.InterfaceStatus;
import com.sky2dev.day18.model.NotificationChannel;
import com.sky2dev.day18.model.NotificationEvent;
import com.sky2dev.day18.model.Report;
import com.sky2dev.day18.model.ReportType;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.ControlCommandRepository;
import com.sky2dev.day18.repository.DeviceMetricRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import com.sky2dev.day18.repository.NotificationEventRepository;
import com.sky2dev.day18.repository.ReportRepository;
import java.math.BigDecimal;
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

    @Bean
    CommandLineRunner seedData(
            HubDeviceRepository hubDeviceRepository,
            DeviceMetricRepository deviceMetricRepository,
            AlarmRepository alarmRepository,
            NotificationEventRepository notificationEventRepository,
            ControlCommandRepository controlCommandRepository,
            ReportRepository reportRepository
    ) {
        return args -> {
            if (hubDeviceRepository.count() > 0) {
                return;
            }

            Instant base = Instant.parse("2026-06-19T00:00:00Z");

            List<HubDevice> devices = hubDeviceRepository.saveAll(List.of(
                    device("Router-01", DeviceType.ROUTER, "10.18.0.1", DeviceStatus.ONLINE, "Teleport-Rack-A1", "Cisco", "IOS-XE-17.09.01", base.plusSeconds(60)),
                    device("Router-02", DeviceType.ROUTER, "10.18.0.2", DeviceStatus.DEGRADED, "Teleport-Rack-A2", "Juniper", "Junos-22.3R1", base.plusSeconds(120)),
                    device("Switch-01", DeviceType.SWITCH, "10.18.0.10", DeviceStatus.ONLINE, "NOC-Core-Switching", "Arista", "EOS-4.31.2F", base.plusSeconds(180)),
                    device("Satellite-Modem-01", DeviceType.MODEM, "10.18.1.11", DeviceStatus.ONLINE, "Hub-Modem-Bay-1", "iDirect", "9.4.1", base.plusSeconds(240)),
                    device("Satellite-Modem-02", DeviceType.MODEM, "10.18.1.12", DeviceStatus.OFFLINE, "Hub-Modem-Bay-2", "Comtech", "CDM-760-2.8.7", base.plusSeconds(300)),
                    device("BUC-01", DeviceType.BUC, "10.18.2.20", DeviceStatus.ONLINE, "Antenna-Chain-TX", "Advantech", "BUC-1.2.0", base.plusSeconds(360)),
                    device("LNB-01", DeviceType.LNB, "10.18.2.21", DeviceStatus.ONLINE, "Antenna-Chain-RX", "Norsat", "LNB-3.1.4", base.plusSeconds(420)),
                    device("UPS-01", DeviceType.UPS, "10.18.3.30", DeviceStatus.DEGRADED, "Power-Room-1", "APC", "UPS-11.0.5", base.plusSeconds(480)),
                    device("Temperature-Sensor-01", DeviceType.SENSOR, "10.18.4.40", DeviceStatus.ONLINE, "RF-Chamber-Temp-Sensor", "Honeywell", "TEMP-2.6", base.plusSeconds(540)),
                    device("Power-Meter-01", DeviceType.POWER_SYSTEM, "10.18.5.50", DeviceStatus.ONLINE, "Power-Distribution-Panel", "Schneider", "PM-7.4.0", base.plusSeconds(600))
            ));

            List<DeviceMetric> metrics = new ArrayList<>();
            for (int i = 0; i < devices.size(); i++) {
                HubDevice d = devices.get(i);
                metrics.add(metric(d, base.plusSeconds(900 + i * 60),
                        bd(30 + i * 4), bd(45 + i * 3), bd(35 + i), bd(-58 - i * 3), bd(82 - i * 4),
                        i == 4 ? InterfaceStatus.DOWN : InterfaceStatus.UP,
                        bd(8.7 - i * 0.4), bd(0.000001 + i * 0.000001), i != 4,
                        bd(14250 + i * 2), bd(30000),
                        bd(37 - i), bd(48 + i), i % 2 == 0 ? "ACTIVE" : "STANDBY",
                        bd(60 + i), bd(0.8 + i * 0.02), "LOCKED",
                        bd(94 - i * 5), bd(229 - i), bd(228 - i),
                        bd(8 + i * 0.3), bd(230 + i * 0.4), bd(1800 + i * 15)));

                metrics.add(metric(d, base.plusSeconds(1800 + i * 60),
                        bd(35 + i * 4), bd(50 + i * 3), bd(38 + i), bd(-61 - i * 3), bd(78 - i * 4),
                        i == 1 || i == 7 ? InterfaceStatus.DEGRADED : InterfaceStatus.UP,
                        bd(8.3 - i * 0.4), bd(0.000002 + i * 0.000001), i != 4,
                        bd(14251 + i * 2), bd(30000),
                        bd(36 - i), bd(49 + i), i % 3 == 0 ? "ACTIVE" : "NORMAL",
                        bd(61 + i), bd(0.85 + i * 0.02), "LOCKED",
                        bd(91 - i * 5), bd(228 - i), bd(227 - i),
                        bd(8.2 + i * 0.3), bd(229 + i * 0.4), bd(1780 + i * 15)));

                metrics.add(metric(d, base.plusSeconds(2700 + i * 60),
                        bd(40 + i * 4), bd(55 + i * 3), bd(41 + i * 2), bd(-64 - i * 3), bd(74 - i * 4),
                        i == 4 ? InterfaceStatus.DOWN : InterfaceStatus.UP,
                        bd(7.9 - i * 0.4), bd(0.000003 + i * 0.000001), i != 4,
                        bd(14252 + i * 2), bd(30000),
                        bd(35 - i), bd(50 + i), i % 4 == 0 ? "ACTIVE" : "NORMAL",
                        bd(62 + i), bd(0.9 + i * 0.02), "LOCKED",
                        bd(88 - i * 5), bd(227 - i), bd(226 - i),
                        bd(8.4 + i * 0.3), bd(228 + i * 0.4), bd(1760 + i * 15)));
            }
            deviceMetricRepository.saveAll(metrics);

            List<Alarm> alarms = alarmRepository.saveAll(List.of(
                    alarm(devices.get(4), AlarmSeverity.CRITICAL, AlarmType.DEVICE_OFFLINE, "Satellite-Modem-02 offline and unreachable", AlarmStatus.OPEN, false, base.plusSeconds(3000)),
                    alarm(devices.get(4), AlarmSeverity.CRITICAL, AlarmType.INTERFACE_DOWN, "Modem downstream interface down", AlarmStatus.OPEN, false, base.plusSeconds(3060)),
                    alarm(devices.get(7), AlarmSeverity.MAJOR, AlarmType.POWER_ANOMALY, "UPS battery dropped below safe threshold", AlarmStatus.OPEN, false, base.plusSeconds(3120)),
                    alarm(devices.get(1), AlarmSeverity.MAJOR, AlarmType.HIGH_CPU, "Router-02 sustained high CPU for 5 minutes", AlarmStatus.OPEN, false, base.plusSeconds(3180)),
                    alarm(devices.get(3), AlarmSeverity.WARNING, AlarmType.LOW_SIGNAL, "Satellite-Modem-01 low EbNo trend", AlarmStatus.ACKNOWLEDGED, true, base.plusSeconds(3240)),
                    alarm(devices.get(5), AlarmSeverity.WARNING, AlarmType.HIGH_TEMPERATURE, "BUC thermal envelope warning", AlarmStatus.OPEN, false, base.plusSeconds(3300)),
                    alarm(devices.get(9), AlarmSeverity.WARNING, AlarmType.PERFORMANCE_DEGRADATION, "Power meter reporting latency increase", AlarmStatus.OPEN, false, base.plusSeconds(3360)),
                    alarm(devices.get(2), AlarmSeverity.MAJOR, AlarmType.INTERFACE_DOWN, "Switch uplink flap detected", AlarmStatus.RESOLVED, true, base.plusSeconds(3420)),
                    alarm(devices.get(0), AlarmSeverity.WARNING, AlarmType.HIGH_CPU, "Router-01 CPU burst due to route convergence", AlarmStatus.OPEN, false, base.plusSeconds(3480)),
                    alarm(devices.get(6), AlarmSeverity.WARNING, AlarmType.LOW_SIGNAL, "LNB gain drift warning", AlarmStatus.OPEN, false, base.plusSeconds(3540)),
                    alarm(devices.get(8), AlarmSeverity.MAJOR, AlarmType.HIGH_TEMPERATURE, "RF chamber temperature rising", AlarmStatus.OPEN, false, base.plusSeconds(3600)),
                    alarm(devices.get(3), AlarmSeverity.CRITICAL, AlarmType.LOW_SIGNAL, "Carrier lock instability detected", AlarmStatus.OPEN, false, base.plusSeconds(3660))
            ));

            List<NotificationEvent> notifications = new ArrayList<>();
            NotificationChannel[] channels = NotificationChannel.values();
            for (int i = 0; i < 20; i++) {
                Alarm alarm = alarms.get(i % alarms.size());
                NotificationChannel channel = channels[i % channels.length];
                notifications.add(NotificationEvent.builder()
                        .alarm(alarm)
                        .channel(channel)
                        .deliveryStatus(i % 9 == 0 ? DeliveryStatus.FAILED : DeliveryStatus.SENT)
                        .sentTime(base.plusSeconds(4000 + i * 30))
                        .build());
            }
            notificationEventRepository.saveAll(notifications);

            List<ControlCommand> commands = controlCommandRepository.saveAll(List.of(
                    command(devices.get(4), CommandType.REBOOT_DEVICE, "manual recovery start", ExecutionStatus.SUCCESS, "noc-operator-1", base.plusSeconds(4200)),
                    command(devices.get(2), CommandType.RESET_INTERFACE, "clear uplink errors", ExecutionStatus.SUCCESS, "noc-operator-2", base.plusSeconds(4260)),
                    command(devices.get(1), CommandType.CHANGE_CONFIGURATION, "apply qos profile", ExecutionStatus.SUCCESS, "automation-engine", base.plusSeconds(4320)),
                    command(devices.get(5), CommandType.DISABLE_PORT, "maintenance isolation", ExecutionStatus.SUCCESS, "noc-operator-1", base.plusSeconds(4380)),
                    command(devices.get(5), CommandType.ENABLE_PORT, "return to service", ExecutionStatus.SUCCESS, "noc-operator-1", base.plusSeconds(4440)),
                    command(devices.get(7), CommandType.CHANGE_CONFIGURATION, "battery optimization mode", ExecutionStatus.SUCCESS, "power-automation", base.plusSeconds(4500)),
                    command(devices.get(3), CommandType.RESET_INTERFACE, "carrier recovery", ExecutionStatus.FAILED, "noc-operator-3", base.plusSeconds(4560)),
                    command(devices.get(0), CommandType.CHANGE_CONFIGURATION, "bgp dampening tune", ExecutionStatus.SUCCESS, "noc-operator-2", base.plusSeconds(4620)),
                    command(devices.get(9), CommandType.REBOOT_DEVICE, "meter firmware sync", ExecutionStatus.SUCCESS, "field-engineer", base.plusSeconds(4680)),
                    command(devices.get(4), CommandType.CHANGE_CONFIGURATION, "modcod fallback profile", ExecutionStatus.FAILED, "automation-engine", base.plusSeconds(4740))
            ));

            reportRepository.saveAll(List.of(
                    report(ReportType.DAILY, "noc-shift-a", "Daily report: 10 devices monitored, 12 alarms assessed", base.plusSeconds(5000)),
                    report(ReportType.WEEKLY, "noc-manager", "Weekly report: modem and power chain incidents trended", base.plusSeconds(5060)),
                    report(ReportType.ALARM_SUMMARY, "alarm-engine", "Alarm summary: critical=3 major=4 warning=5", base.plusSeconds(5120)),
                    report(ReportType.AVAILABILITY, "availability-engine", "Availability report: hub availability at 80.00%", base.plusSeconds(5180)),
                    report(ReportType.PERFORMANCE, "performance-engine", "Top problem devices: Satellite-Modem-02, UPS-01, Router-02", base.plusSeconds(5240))
            ));

            if (commands.isEmpty()) {
                throw new IllegalStateException("Seed commands failed unexpectedly");
            }
        };
    }

    private HubDevice device(
            String hostname,
            DeviceType type,
            String ip,
            DeviceStatus status,
            String location,
            String vendor,
            String firmware,
            Instant lastSeen
    ) {
        return HubDevice.builder()
                .hostname(hostname)
                .deviceType(type)
                .ipAddress(ip)
                .status(status)
                .location(location)
                .vendor(vendor)
                .firmwareVersion(firmware)
                .lastSeen(lastSeen)
                .build();
    }

    private DeviceMetric metric(
            HubDevice device,
            Instant timestamp,
            BigDecimal cpu,
            BigDecimal memory,
            BigDecimal temperature,
            BigDecimal signal,
            BigDecimal powerLevel,
            InterfaceStatus interfaceStatus,
            BigDecimal ebNo,
            BigDecimal ber,
            boolean carrierLock,
            BigDecimal frequency,
            BigDecimal symbolRate,
            BigDecimal bucOutputPower,
            BigDecimal bucTemperature,
            String bucStatus,
            BigDecimal lnbGain,
            BigDecimal lnbNoiseFigure,
            String lnbStatus,
            BigDecimal upsBattery,
            BigDecimal upsInput,
            BigDecimal upsOutput,
            BigDecimal current,
            BigDecimal voltage,
            BigDecimal consumption
    ) {
        return DeviceMetric.builder()
                .device(device)
                .cpuUsage(cpu)
                .memoryUsage(memory)
                .temperature(temperature)
                .signalStrength(signal)
                .powerLevel(powerLevel)
                .interfaceStatus(interfaceStatus)
                .timestamp(timestamp)
                .ebNo(ebNo)
                .ber(ber)
                .carrierLock(carrierLock)
                .frequencyMhz(frequency)
                .symbolRateKsps(symbolRate)
                .bucOutputPower(bucOutputPower)
                .bucTemperature(bucTemperature)
                .bucStatus(bucStatus)
                .lnbGain(lnbGain)
                .lnbNoiseFigure(lnbNoiseFigure)
                .lnbStatus(lnbStatus)
                .upsBatteryPercent(upsBattery)
                .upsInputVoltage(upsInput)
                .upsOutputVoltage(upsOutput)
                .powerCurrent(current)
                .powerVoltage(voltage)
                .powerConsumption(consumption)
                .build();
    }

    private Alarm alarm(
            HubDevice device,
            AlarmSeverity severity,
            AlarmType type,
            String message,
            AlarmStatus status,
            boolean acknowledged,
            Instant createdAt
    ) {
        return Alarm.builder()
                .device(device)
                .severity(severity)
                .alarmType(type)
                .message(message)
                .status(status)
                .acknowledged(acknowledged)
                .createdAt(createdAt)
                .build();
    }

    private ControlCommand command(
            HubDevice device,
            CommandType type,
            String payload,
            ExecutionStatus status,
            String executedBy,
            Instant time
    ) {
        return ControlCommand.builder()
                .device(device)
                .commandType(type)
                .commandPayload(payload)
                .executionStatus(status)
                .executionTime(time)
                .executedBy(executedBy)
                .build();
    }

    private Report report(ReportType type, String generatedBy, String summary, Instant time) {
        return Report.builder()
                .reportType(type)
                .generatedBy(generatedBy)
                .generatedTime(time)
                .reportSummary(summary)
                .build();
    }

    private BigDecimal bd(double value) {
        return BigDecimal.valueOf(value);
    }
}
