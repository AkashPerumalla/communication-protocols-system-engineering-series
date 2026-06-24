package com.sky2dev.day23.service;

import com.sky2dev.day23.entity.TaskExecutionLog;
import com.sky2dev.day23.entity.TaskExecutionStatus;
import com.sky2dev.day23.repository.TaskExecutionLogRepository;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskExecutionLogService {

    private final TaskExecutionLogRepository taskExecutionLogRepository;

    @Transactional
    public void logSuccess(String taskName, Instant startTime) {
        Instant endTime = Instant.now();
        taskExecutionLogRepository.save(TaskExecutionLog.builder()
                .taskName(taskName)
                .threadName(Thread.currentThread().getName())
                .startTime(startTime)
                .endTime(endTime)
                .duration(Duration.between(startTime, endTime).toMillis())
                .status(TaskExecutionStatus.SUCCESS)
                .build());
    }

    @Transactional
    public void logFailure(String taskName, Instant startTime) {
        Instant endTime = Instant.now();
        taskExecutionLogRepository.save(TaskExecutionLog.builder()
                .taskName(taskName)
                .threadName(Thread.currentThread().getName())
                .startTime(startTime)
                .endTime(endTime)
                .duration(Duration.between(startTime, endTime).toMillis())
                .status(TaskExecutionStatus.FAILED)
                .build());
    }
}
