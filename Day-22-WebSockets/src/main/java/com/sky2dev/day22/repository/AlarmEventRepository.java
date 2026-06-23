package com.sky2dev.day22.repository;

import com.sky2dev.day22.entity.AlarmEvent;
import com.sky2dev.day22.entity.AlarmSeverity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {

    long countByAcknowledgedFalse();

    long countByAcknowledgedFalseAndSeverity(AlarmSeverity severity);

    List<AlarmEvent> findTop200ByOrderByTimestampDesc();
}
