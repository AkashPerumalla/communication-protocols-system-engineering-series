package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.ReportService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final TaskExecutionService taskExecutionService;
    private final ReportService reportService;

    @Scheduled(cron = "${scheduler.report-daily-cron}")
    public void generateDaily() {
        taskExecutionService.executeTracked("ReportScheduler.generateDaily", reportService::generateDailyReport);
    }

    @Scheduled(cron = "${scheduler.report-performance-cron}")
    public void generatePerformance() {
        taskExecutionService.executeTracked("ReportScheduler.generatePerformance", reportService::generatePerformanceReport);
    }
}
