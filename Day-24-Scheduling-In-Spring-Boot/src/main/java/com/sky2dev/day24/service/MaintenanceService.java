package com.sky2dev.day24.service;

import com.sky2dev.day24.repository.TaskExecutionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final TaskExecutionLogRepository taskExecutionLogRepository;

    @Transactional
    public long cleanupLogs() {
        Instant threshold = Instant.now().minusSeconds(1800);
        return taskExecutionLogRepository.deleteByEndTimeBefore(threshold);
    }

    public String databaseMaintenance() {
        return "Vacuum/analyze simulated for H2 runtime";
    }
}
