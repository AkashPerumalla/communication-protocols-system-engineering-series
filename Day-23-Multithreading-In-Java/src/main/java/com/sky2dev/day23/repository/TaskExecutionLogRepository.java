package com.sky2dev.day23.repository;

import com.sky2dev.day23.entity.TaskExecutionLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskExecutionLogRepository extends JpaRepository<TaskExecutionLog, Long> {
    List<TaskExecutionLog> findTop100ByOrderByStartTimeDesc();
}
