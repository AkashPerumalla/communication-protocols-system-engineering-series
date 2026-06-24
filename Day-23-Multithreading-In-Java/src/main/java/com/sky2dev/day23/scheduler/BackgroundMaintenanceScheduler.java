package com.sky2dev.day23.scheduler;

import com.sky2dev.day23.service.MonitoringSimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.scheduling", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BackgroundMaintenanceScheduler {

    private final MonitoringSimulationService monitoringSimulationService;

    @Scheduled(fixedDelay = 15000, initialDelay = 10000)
    public void executeMaintenance() {
        monitoringSimulationService.runMaintenanceTick();
    }
}
