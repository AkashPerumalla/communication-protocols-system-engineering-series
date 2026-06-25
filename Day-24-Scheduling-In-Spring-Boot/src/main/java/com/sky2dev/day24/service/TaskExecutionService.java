package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.TaskExecutionLog;
import com.sky2dev.day24.entity.TaskExecutionStatus;
import com.sky2dev.day24.repository.TaskExecutionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class TaskExecutionService {

    private final TaskExecutionLogRepository taskExecutionLogRepository;

    public <T> T executeTracked(String taskName, Supplier<T> action) {
        Instant startTime = Instant.now();
        TaskExecutionLog log = TaskExecutionLog.builder()
                .taskName(taskName)
                .startTime(startTime)
                .status(TaskExecutionStatus.RUNNING)
                .message("Task started")
                .executionDurationMs(0L)
                .build();

        log = taskExecutionLogRepository.save(log);

        try {
            T result = action.get();
            finalizeLog(log, TaskExecutionStatus.SUCCESS, "Task completed successfully", startTime);
            return result;
        } catch (Exception ex) {
            finalizeLog(log, TaskExecutionStatus.FAILED, ex.getMessage(), startTime);
            throw ex;
        }
    }

    public void executeTracked(String taskName, Runnable action) {
        executeTracked(taskName, () -> {
            action.run();
            return null;
        });
    }

    private void finalizeLog(TaskExecutionLog log,
                             TaskExecutionStatus status,
                             String message,
                             Instant startTime) {
        Instant endTime = Instant.now();
        long duration = endTime.toEpochMilli() - startTime.toEpochMilli();
        log.setStatus(status);
        log.setEndTime(endTime);
        log.setExecutionDurationMs(Math.max(duration, 0));
        log.setMessage(message);
        taskExecutionLogRepository.save(log);
    }
}
