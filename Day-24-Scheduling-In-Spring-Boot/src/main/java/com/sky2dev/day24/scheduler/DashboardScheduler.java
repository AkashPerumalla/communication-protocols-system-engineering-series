package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.DashboardService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardScheduler {

    private final TaskExecutionService taskExecutionService;
    private final DashboardService dashboardService;

    @Scheduled(fixedRateString = "${scheduler.dashboard-rate}")
    public void refreshDashboard() {
        taskExecutionService.executeTracked("DashboardScheduler.refreshDashboard", dashboardService::updateDashboard);
    }
}
