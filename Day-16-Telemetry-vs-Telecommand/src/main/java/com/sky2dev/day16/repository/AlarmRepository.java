package com.sky2dev.day16.repository;

import com.sky2dev.day16.model.Alarm;
import com.sky2dev.day16.model.AlarmStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @EntityGraph(attributePaths = "device")
    List<Alarm> findByStatusOrderByTriggeredAtDesc(AlarmStatus status);

    @EntityGraph(attributePaths = "device")
    List<Alarm> findTop50ByOrderByTriggeredAtDesc();

    @EntityGraph(attributePaths = "device")
    Optional<Alarm> findFirstByDeviceIdAndMetricNameAndStatus(Long deviceId, String metricName, AlarmStatus status);
}
