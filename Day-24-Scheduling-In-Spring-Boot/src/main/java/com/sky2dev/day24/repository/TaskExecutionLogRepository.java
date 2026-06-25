package com.sky2dev.day24.repository;

import com.sky2dev.day24.entity.TaskExecutionLog;
import com.sky2dev.day24.entity.TaskExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TaskExecutionLogRepository extends JpaRepository<TaskExecutionLog, Long> {

    long countByStatus(TaskExecutionStatus status);

    long deleteByEndTimeBefore(Instant threshold);

    List<TaskExecutionLog> findTop200ByOrderByStartTimeDesc();
}
