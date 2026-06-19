package com.sky2dev.day18.repository;

import com.sky2dev.day18.model.ControlCommand;
import com.sky2dev.day18.model.ExecutionStatus;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ControlCommandRepository extends JpaRepository<ControlCommand, Long> {

    List<ControlCommand> findTop10ByOrderByExecutionTimeDesc();

    long countByExecutionTimeAfter(Instant timestamp);

    long countByExecutionStatus(ExecutionStatus status);
}
