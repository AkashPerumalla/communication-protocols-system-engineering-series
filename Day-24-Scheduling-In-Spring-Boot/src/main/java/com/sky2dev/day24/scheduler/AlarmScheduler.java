package com.sky2dev.day24.scheduler;

import com.sky2dev.day24.service.AlarmEvaluationService;
import com.sky2dev.day24.service.TaskExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmScheduler {

    private final TaskExecutionService taskExecutionService;
    private final AlarmEvaluationService alarmEvaluationService;

    @Scheduled(fixedRateString = "${scheduler.alarm-rate}")
    public void evaluateAlarms() {
        taskExecutionService.executeTracked("AlarmScheduler.evaluateAlarms", alarmEvaluationService::evaluateAlarms);
    }
}
