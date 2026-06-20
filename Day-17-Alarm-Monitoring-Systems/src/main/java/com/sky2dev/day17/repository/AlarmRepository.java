package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmSeverity;
import com.sky2dev.day17.model.AlarmState;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByState(AlarmState state);
    List<Alarm> findBySeverity(AlarmSeverity severity);
    List<Alarm> findByAcknowledgedFalse();
}
