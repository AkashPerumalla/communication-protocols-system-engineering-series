package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.ArchiveService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArchiveScheduler {

    private final TaskExecutionService taskExecutionService;
    private final ArchiveService archiveService;

    @Scheduled(cron = "${scheduler.archive-cron}")
    public void archiveTelemetry() {
        taskExecutionService.executeTracked("ArchiveScheduler.archiveTelemetry", archiveService::archiveOldTelemetry);
    }
}
