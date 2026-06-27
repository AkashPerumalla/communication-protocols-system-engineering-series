package com.sky2dev.day26.scheduler;

import com.sky2dev.day26.service.AlarmService;
import com.sky2dev.day26.service.ClusterHealthService;
import com.sky2dev.day26.service.DeploymentService;
import com.sky2dev.day26.service.NotificationService;
import com.sky2dev.day26.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class PlatformSimulationScheduler {

    private final TelemetryService telemetryService;
    private final AlarmService alarmService;
    private final NotificationService notificationService;
    private final DeploymentService deploymentService;
    private final ClusterHealthService clusterHealthService;

    @Scheduled(fixedDelayString = "${app.scheduler.telemetry-delay-ms}", initialDelay = 15000)
    public void refreshTelemetry() {
        telemetryService.refreshTelemetryWindow();
    }

    @Scheduled(fixedDelayString = "${app.scheduler.alarm-delay-ms}", initialDelay = 30000)
    public void evaluateAlarms() {
        alarmService.evaluateTelemetryAlarms();
    }

    @Scheduled(fixedDelayString = "${app.scheduler.notification-delay-ms}", initialDelay = 45000)
    public void dispatchNotifications() {
        notificationService.dispatchPendingNotifications();
    }

    @Scheduled(fixedDelayString = "${app.scheduler.deployment-delay-ms}", initialDelay = 20000)
    public void advanceDeploymentRollouts() {
        deploymentService.advanceDeploymentSimulation();
    }

    @Scheduled(fixedDelayString = "${app.scheduler.cluster-delay-ms}", initialDelay = 25000)
    public void refreshClusterHealth() {
        clusterHealthService.recomputeClusterHealth();
    }
}
