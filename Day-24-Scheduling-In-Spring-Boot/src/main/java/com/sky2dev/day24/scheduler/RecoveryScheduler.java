package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.RecoveryService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecoveryScheduler {

    private final TaskExecutionService taskExecutionService;
    private final RecoveryService recoveryService;

    @Scheduled(fixedRateString = "${scheduler.recovery-rate}")
    public void autoRecover() {
        taskExecutionService.executeTracked("RecoveryScheduler.autoRecover", recoveryService::autoRecoverDevices);
    }
}
