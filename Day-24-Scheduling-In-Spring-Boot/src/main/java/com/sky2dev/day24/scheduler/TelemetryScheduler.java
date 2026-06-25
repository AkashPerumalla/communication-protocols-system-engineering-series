package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.TaskExecutionService;
import com.sky2dev.day24.service.TelemetryCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelemetryScheduler {

    private final TaskExecutionService taskExecutionService;
    private final TelemetryCollectionService telemetryCollectionService;

    @Scheduled(fixedRateString = "${scheduler.telemetry-rate}")
    public void collectTelemetry() {
        taskExecutionService.executeTracked("TelemetryScheduler.collectTelemetry", telemetryCollectionService::collectMetrics);
    }
}
