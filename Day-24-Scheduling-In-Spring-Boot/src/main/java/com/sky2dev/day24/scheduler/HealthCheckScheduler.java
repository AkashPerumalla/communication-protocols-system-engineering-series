package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.DeviceHealthService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HealthCheckScheduler {

    private final TaskExecutionService taskExecutionService;
    private final DeviceHealthService deviceHealthService;

    @Scheduled(fixedRateString = "${scheduler.health-rate}")
    public void evaluateHealth() {
        taskExecutionService.executeTracked("HealthCheckScheduler.evaluateHealth", deviceHealthService::evaluateDeviceHealth);
    }
}
