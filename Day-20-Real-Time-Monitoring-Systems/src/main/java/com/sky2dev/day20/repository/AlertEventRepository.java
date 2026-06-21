package com.sky2dev.day20.repository;

import com.sky2dev.day20.entity.AlertEvent;
import com.sky2dev.day20.entity.AlertSeverity;
import com.sky2dev.day20.entity.AlertStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertEventRepository extends JpaRepository<AlertEvent, Long> {

    List<AlertEvent> findByStatusOrderByTimestampDesc(AlertStatus status);

    long countByStatus(AlertStatus status);

    long countBySeverityAndStatus(AlertSeverity severity, AlertStatus status);

    Optional<AlertEvent> findTop1ByAlertIdAndMetricNameAndStatusOrderByTimestampDesc(
            Long alertId, String metricName, AlertStatus status);
}
