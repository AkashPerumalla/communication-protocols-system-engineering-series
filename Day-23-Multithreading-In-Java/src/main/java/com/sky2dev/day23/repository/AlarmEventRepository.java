package com.sky2dev.day23.repository;

import com.sky2dev.day23.entity.AlarmEvent;
import com.sky2dev.day23.entity.AlarmSeverity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {
    List<AlarmEvent> findTop50ByOrderByTimestampDesc();

    List<AlarmEvent> findByAcknowledgedFalse();

    long countBySeverity(AlarmSeverity severity);
}
