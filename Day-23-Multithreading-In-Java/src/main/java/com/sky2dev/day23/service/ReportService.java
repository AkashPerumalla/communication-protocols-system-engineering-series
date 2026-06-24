package com.sky2dev.day23.service;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final DashboardService dashboardService;
    private final TaskExecutionLogService taskExecutionLogService;

    public void generatePeriodicReport() {
        Instant start = Instant.now();
        try {
            dashboardService.fetchDashboardMetrics();
            taskExecutionLogService.logSuccess("ReportGenerationTask", start);
        } catch (RuntimeException ex) {
            taskExecutionLogService.logFailure("ReportGenerationTask", start);
            throw ex;
        }
    }

    public void runBackgroundMaintenance() {
        Instant start = Instant.now();
        try {
            dashboardService.fetchDashboardMetrics();
            taskExecutionLogService.logSuccess("BackgroundMaintenanceJob", start);
        } catch (RuntimeException ex) {
            taskExecutionLogService.logFailure("BackgroundMaintenanceJob", start);
            throw ex;
        }
    }
}
