package com.sky2dev.day18.repository;

import com.sky2dev.day18.model.Alarm;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.AlarmStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    long countBySeverityAndStatusNot(AlarmSeverity severity, AlarmStatus status);

    List<Alarm> findByStatusNotOrderByCreatedAtDesc(AlarmStatus status);

    List<Alarm> findTop10ByOrderByCreatedAtDesc();

    List<Alarm> findAllByOrderByCreatedAtDesc();

    long countByStatusNot(AlarmStatus status);
}
