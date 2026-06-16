package com.sky2dev.day15.repository;

import com.sky2dev.day15.entity.Alert;
import com.sky2dev.day15.entity.AlertSeverity;
import com.sky2dev.day15.entity.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findTop100ByOrderByCreatedAtDesc();

    long countByActiveTrue();

    long countBySeverityAndActiveTrue(AlertSeverity severity);

    List<Alert> findAllByDeviceAndRuleNameAndActiveTrue(Device device, String ruleName);

    List<Alert> findByActiveTrueOrderByCreatedAtDesc();
}
