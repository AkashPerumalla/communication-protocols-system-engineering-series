package com.sky2dev.day23.service;

import com.sky2dev.day23.entity.AlarmEvent;
import com.sky2dev.day23.simulation.TelemetrySample;
import com.sky2dev.day23.threading.MonitoringStatistics;
import com.sky2dev.day23.threading.NotificationTask;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringPipelineService {

    private final TelemetryService telemetryService;
    private final AlarmService alarmService;
    private final NotificationService notificationService;
    private final MonitoringStatistics monitoringStatistics;
    private final TaskExecutionLogService taskExecutionLogService;

    @Qualifier("notificationExecutor")
    private final ExecutorService notificationExecutor;

    public void processSample(TelemetrySample sample) {
        Instant start = Instant.now();
        try {
            telemetryService.saveSample(sample);
            monitoringStatistics.incrementUnsafeTelemetry();

            Optional<AlarmEvent> alarm = alarmService.evaluateTelemetry(sample);
            if (alarm.isPresent()) {
                monitoringStatistics.incrementSynchronizedAlarm();
                notificationExecutor.submit(new NotificationTask(notificationService, alarm.get().getId()));
            }
            taskExecutionLogService.logSuccess("AlarmProcessorTask", start);
        } catch (RuntimeException ex) {
            taskExecutionLogService.logFailure("AlarmProcessorTask", start);
            throw ex;
        }
    }
}
