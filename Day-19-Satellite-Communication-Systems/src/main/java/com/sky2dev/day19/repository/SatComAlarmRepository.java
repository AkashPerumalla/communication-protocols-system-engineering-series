package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.AlarmSeverity;
import com.sky2dev.day19.model.SatComAlarm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatComAlarmRepository extends JpaRepository<SatComAlarm, Long> {
    List<SatComAlarm> findBySeverity(AlarmSeverity severity);
    List<SatComAlarm> findByStatus(com.sky2dev.day19.model.AlarmStatus status);
}
