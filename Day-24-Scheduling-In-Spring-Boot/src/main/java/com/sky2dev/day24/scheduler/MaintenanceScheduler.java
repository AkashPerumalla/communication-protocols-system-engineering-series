package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.MaintenanceService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaintenanceScheduler {

    private final TaskExecutionService taskExecutionService;
    private final MaintenanceService maintenanceService;

    @Scheduled(fixedDelayString = "${scheduler.maintenance-delay}")
    public void performMaintenance() {
        taskExecutionService.executeTracked("MaintenanceScheduler.performMaintenance", () -> {
            long deleted = maintenanceService.cleanupLogs();
            String dbResult = maintenanceService.databaseMaintenance();
            return "Deleted logs=" + deleted + ", dbMaintenance=" + dbResult;
        });
    }
}
