package com.sky2dev.day26.service;

import com.sky2dev.day26.dto.PlatformStatusResponse;
import com.sky2dev.day26.repository.AlarmEventRepository;
import com.sky2dev.day26.repository.DeviceRepository;
import com.sky2dev.day26.repository.NotificationEventRepository;
import com.sky2dev.day26.repository.TelemetryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlatformStatusService {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final DeploymentService deploymentService;
    private final ClusterHealthService clusterHealthService;
    private final Environment environment;

    @Value("${app.cluster.name}")
    private String clusterName;

    @Value("${app.cluster.namespace}")
    private String namespace;

    @Transactional(readOnly = true)
    public PlatformStatusResponse getPlatformStatus() {
        String[] profiles = environment.getActiveProfiles();
        String activeProfile = profiles.length == 0 ? "default" : profiles[0];
        var clusterHealth = clusterHealthService.getCurrentHealth();
        return new PlatformStatusResponse(
                "Day-26 Kubernetes Device Monitoring Platform",
                activeProfile,
                "UP",
                "Spring Boot 3.3.x / Java 17",
                clusterName,
                namespace,
                activeProfile.equalsIgnoreCase("kubernetes") ? "MYSQL" : "H2",
                deviceRepository.count(),
                telemetryRecordRepository.count(),
                alarmEventRepository.count(),
                notificationEventRepository.count(),
                deploymentService.getDeployments().size(),
                clusterHealth.overallStatus(),
                "/actuator/prometheus");
    }
}
