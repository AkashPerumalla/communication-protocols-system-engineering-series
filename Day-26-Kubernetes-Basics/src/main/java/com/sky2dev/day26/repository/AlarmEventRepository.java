package com.sky2dev.day26.repository;

import com.sky2dev.day26.entity.AlarmEvent;
import com.sky2dev.day26.entity.AlarmSeverity;
import com.sky2dev.day26.entity.AlarmStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {
    List<AlarmEvent> findTop20ByOrderByRaisedAtDesc();
    List<AlarmEvent> findByStatusOrderByRaisedAtDesc(AlarmStatus status);
    long countBySeverity(AlarmSeverity severity);
    long countByStatus(AlarmStatus status);
}
