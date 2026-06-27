package com.sky2dev.day26.seed;

import com.sky2dev.day26.entity.AlarmEvent;
import com.sky2dev.day26.entity.AlarmSeverity;
import com.sky2dev.day26.entity.AlarmStatus;
import com.sky2dev.day26.entity.ClusterHealth;
import com.sky2dev.day26.entity.DeploymentEnvironment;
import com.sky2dev.day26.entity.DeploymentStatus;
import com.sky2dev.day26.entity.Device;
import com.sky2dev.day26.entity.DeviceStatus;
import com.sky2dev.day26.entity.DeviceType;
import com.sky2dev.day26.entity.NotificationChannel;
import com.sky2dev.day26.entity.NotificationEvent;
import com.sky2dev.day26.entity.NotificationStatus;
import com.sky2dev.day26.entity.TelemetryRecord;
import com.sky2dev.day26.repository.AlarmEventRepository;
import com.sky2dev.day26.repository.ClusterHealthRepository;
import com.sky2dev.day26.repository.DeploymentStatusRepository;
import com.sky2dev.day26.repository.DeviceRepository;
import com.sky2dev.day26.repository.NotificationEventRepository;
import com.sky2dev.day26.repository.TelemetryRecordRepository;
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
public class SeedDataInitializer implements CommandLineRunner {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final DeploymentStatusRepository deploymentStatusRepository;
    private final ClusterHealthRepository clusterHealthRepository;

    @Override
    public void run(String... args) {
        if (deviceRepository.count() > 0) {
            return;
        }

        LocalDateTime base = LocalDateTime.of(2026, 6, 27, 9, 0);
        List<Device> devices = seedDevices(base);
        seedTelemetry(devices, base);
        List<AlarmEvent> alarms = seedAlarms(devices, base);
        seedNotifications(devices, alarms, base);
        seedDeployments(base);
        seedClusterHealth(base);

        log.info("Day-26 seed completed: devices={}, telemetry={}, alarms={}, notifications={}, deployments={}",
                deviceRepository.count(),
                telemetryRecordRepository.count(),
                alarmEventRepository.count(),
                notificationEventRepository.count(),
                deploymentStatusRepository.count());
    }

    private List<Device> seedDevices(LocalDateTime base) {
        List<Device> devices = List.of(
                buildDevice("Router-01", "10.26.1.1", DeviceType.ROUTER, DeviceStatus.ONLINE, "NOC-Core", "noc-core-a", base),
                buildDevice("Router-02", "10.26.1.2", DeviceType.ROUTER, DeviceStatus.DEGRADED, "NOC-Edge", "noc-edge-a", base.plusMinutes(1)),
                buildDevice("Switch-01", "10.26.2.1", DeviceType.SWITCH, DeviceStatus.ONLINE, "NOC-Core", "noc-core-b", base.plusMinutes(2)),
                buildDevice("Switch-02", "10.26.2.2", DeviceType.SWITCH, DeviceStatus.ONLINE, "NOC-Edge", "noc-edge-b", base.plusMinutes(3)),
                buildDevice("Satellite-Modem-01", "10.26.3.1", DeviceType.SATELLITE_MODEM, DeviceStatus.ONLINE, "SatCom-West", "sat-west-a", base.plusMinutes(4)),
                buildDevice("Satellite-Modem-02", "10.26.3.2", DeviceType.SATELLITE_MODEM, DeviceStatus.DEGRADED, "SatCom-East", "sat-east-a", base.plusMinutes(5)),
                buildDevice("Hub-01", "10.26.4.1", DeviceType.HUB, DeviceStatus.ONLINE, "Hub-Primary", "hub-pri-a", base.plusMinutes(6)),
                buildDevice("BUC-01", "10.26.5.1", DeviceType.BUC, DeviceStatus.MAINTENANCE, "Uplink-Site", "uplink-a", base.plusMinutes(7)),
                buildDevice("Gateway-01", "10.26.6.1", DeviceType.IOT_GATEWAY, DeviceStatus.ONLINE, "IoT-Field", "iot-a", base.plusMinutes(8)),
                buildDevice("Sensor-01", "10.26.7.1", DeviceType.SENSOR, DeviceStatus.OFFLINE, "Field-Station", "field-a", base.plusMinutes(9)));
        return deviceRepository.saveAll(devices);
    }

    private Device buildDevice(String name, String ip, DeviceType type, DeviceStatus status, String region, String clusterZone,
            LocalDateTime timestamp) {
        return Device.builder()
                .name(name)
                .ipAddress(ip)
                .deviceType(type)
                .status(status)
                .region(region)
                .clusterZone(clusterZone)
                .firmwareVersion("fw-26.1")
                .lastSeen(timestamp)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }

    private void seedTelemetry(List<Device> devices, LocalDateTime base) {
        List<TelemetryRecord> telemetryRecords = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            Device device = devices.get(index % devices.size());
            telemetryRecords.add(TelemetryRecord.builder()
                    .device(device)
                    .metricName(index % 2 == 0 ? "latency_ms" : "snr_db")
                    .metricValue(30.0 + (index % 25) * 1.8)
                    .unit(index % 2 == 0 ? "ms" : "dB")
                    .qualityScore(82 + (index % 14))
                    .ingestionSource(index % 3 == 0 ? "telegraf-sidecar" : "spring-agent")
                    .collectedAt(base.plusSeconds(index * 45L))
                    .build());
        }
        telemetryRecordRepository.saveAll(telemetryRecords);
    }

    private List<AlarmEvent> seedAlarms(List<Device> devices, LocalDateTime base) {
        List<AlarmEvent> alarms = new ArrayList<>();
        AlarmSeverity[] severities = AlarmSeverity.values();
        AlarmStatus[] statuses = AlarmStatus.values();

        for (int index = 0; index < 20; index++) {
            AlarmStatus status = statuses[index % statuses.length];
            LocalDateTime raised = base.plusMinutes(index * 4L);
            alarms.add(AlarmEvent.builder()
                    .device(devices.get(index % devices.size()))
                    .code("K8S-ALM-" + String.format("%03d", index + 1))
                    .description("Platform telemetry breached service threshold")
                    .severity(severities[index % severities.length])
                    .status(status)
                    .sourceMetric(index % 2 == 0 ? "latency_ms" : "snr_db")
                    .thresholdValue(70.0)
                    .observedValue(72.0 + index)
                    .raisedAt(raised)
                    .acknowledgedAt(status == AlarmStatus.ACKNOWLEDGED || status == AlarmStatus.RESOLVED ? raised.plusMinutes(3) : null)
                    .resolvedAt(status == AlarmStatus.RESOLVED ? raised.plusMinutes(8) : null)
                    .build());
        }

        return alarmEventRepository.saveAll(alarms);
    }

    private void seedNotifications(List<Device> devices, List<AlarmEvent> alarms, LocalDateTime base) {
        List<NotificationEvent> notifications = new ArrayList<>();
        NotificationChannel[] channels = NotificationChannel.values();
        NotificationStatus[] statuses = NotificationStatus.values();

        for (int index = 0; index < 20; index++) {
            notifications.add(NotificationEvent.builder()
                    .alarm(alarms.get(index % alarms.size()))
                    .device(devices.get(index % devices.size()))
                    .channel(channels[index % channels.length])
                    .recipient(index % 2 == 0 ? "noc-team@sky2dev.com" : "satcom-ops@sky2dev.com")
                    .message("Notification for deployment-aware alarm " + alarms.get(index % alarms.size()).getCode())
                    .status(statuses[index % statuses.length])
                    .deliveryReference("seed-notif-" + String.format("%03d", index + 1))
                    .sentAt(base.plusMinutes(index))
                    .build());
        }

        notificationEventRepository.saveAll(notifications);
    }

    private void seedDeployments(LocalDateTime base) {
        List<DeploymentStatus> deployments = List.of(
                buildDeployment("device-monitoring-platform", DeploymentEnvironment.PRODUCTION, 3, 3, 3, "1.0.0", "1.0.0", "RUNNING", "v1", true, base),
                buildDeployment("telemetry-ingestor", DeploymentEnvironment.PRODUCTION, 2, 2, 2, "1.0.0", "1.0.0", "RUNNING", "v1", true, base.plusMinutes(2)),
                buildDeployment("alarm-evaluator", DeploymentEnvironment.STAGING, 2, 2, 2, "1.0.1", "1.0.1", "RUNNING", "v2", true, base.plusMinutes(4)),
                buildDeployment("notification-router", DeploymentEnvironment.EDGE, 1, 1, 1, "1.0.0", "1.0.0", "RUNNING", "v1", true, base.plusMinutes(6)),
                buildDeployment("cluster-health-watcher", DeploymentEnvironment.DISASTER_RECOVERY, 1, 1, 1, "1.0.0", "1.0.0", "RUNNING", "v1", true, base.plusMinutes(8)));
        deploymentStatusRepository.saveAll(deployments);
    }

    private DeploymentStatus buildDeployment(String name, DeploymentEnvironment environment, int desiredReplicas,
            int availableReplicas, int updatedReplicas, String imageTag, String stableImageTag, String rolloutState,
            String version, boolean stable, LocalDateTime timestamp) {
        return DeploymentStatus.builder()
                .deploymentName(name)
                .namespaceName("day26-platform")
                .environment(environment)
                .desiredReplicas(desiredReplicas)
                .availableReplicas(availableReplicas)
                .updatedReplicas(updatedReplicas)
                .strategy("RollingUpdate")
                .imageTag(imageTag)
                .stableImageTag(stableImageTag)
                .rolloutState(rolloutState)
                .version(version)
                .lastAction("Seeded deployment baseline")
                .stable(stable)
                .lastUpdated(timestamp)
                .build();
    }

    private void seedClusterHealth(LocalDateTime base) {
        clusterHealthRepository.save(ClusterHealth.builder()
                .clusterName("docker-desktop")
                .namespaceName("day26-platform")
                .nodeCount(3)
                .readyNodeCount(3)
                .totalPods(9)
                .runningPods(9)
                .pendingPods(0)
                .failedPods(1)
                .serviceCount(4)
                .configMapStatus("LOADED")
                .secretStatus("LOADED")
                .pvcStatus("BOUND")
                .overallStatus("DEGRADED")
                .apiServerLatencyMs(9.5)
                .observedAt(base.plusMinutes(10))
                .build());
    }
}
