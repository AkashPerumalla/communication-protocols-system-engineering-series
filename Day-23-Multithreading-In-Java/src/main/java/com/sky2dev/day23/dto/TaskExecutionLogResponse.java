package com.sky2dev.day23.dto;

import com.sky2dev.day23.entity.TaskExecutionLog;
import com.sky2dev.day23.entity.TaskExecutionStatus;
import java.time.Instant;

public record TaskExecutionLogResponse(
        Long id,
        String taskName,
        String threadName,
        Instant startTime,
        Instant endTime,
        long duration,
        TaskExecutionStatus status
) {
    public static TaskExecutionLogResponse from(TaskExecutionLog log) {
        return new TaskExecutionLogResponse(
                log.getId(),
                log.getTaskName(),
                log.getThreadName(),
                log.getStartTime(),
                log.getEndTime(),
                log.getDuration(),
                log.getStatus()
        );
    }
}
