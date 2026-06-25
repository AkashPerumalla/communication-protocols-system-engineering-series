package com.sky2dev.day24.repository;

import com.sky2dev.day24.entity.AlarmEvent;
import com.sky2dev.day24.entity.AlarmSeverity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {

    long countByAcknowledgedFalse();

    long countBySeverityAndAcknowledgedFalse(AlarmSeverity severity);

    List<AlarmEvent> findTop50ByOrderByTimestampDesc();
}
