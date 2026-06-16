package com.sky2dev.day15.alert;

import com.sky2dev.day15.entity.Alert;
import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.repository.AlertRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertRuleEngine alertRuleEngine;

    @Transactional
    public void reconcile(Device device, String sourceType, List<AlertCandidate> candidates) {
        for (AlertCandidate candidate : candidates) {
            List<Alert> existingAlerts = alertRepository.findAllByDeviceAndRuleNameAndActiveTrue(device, candidate.ruleName());
            if (existingAlerts.isEmpty()) {
                alertRepository.save(Alert.builder()
                        .device(device)
                        .ruleName(candidate.ruleName())
                        .severity(candidate.severity())
                        .message(candidate.message())
                        .active(true)
                        .acknowledged(false)
                        .createdAt(Instant.now())
                        .sourceType(sourceType)
                        .build());
                continue;
            }
            for (Alert existing : existingAlerts) {
                existing.setMessage(candidate.message());
                existing.setSeverity(candidate.severity());
                existing.setSourceType(sourceType);
                alertRepository.save(existing);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Alert> findAllAlerts() {
        return alertRepository.findTop100ByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Alert> findActiveAlerts() {
        return alertRepository.findByActiveTrueOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public long countActiveAlerts() {
        return alertRepository.countByActiveTrue();
    }

    @Transactional(readOnly = true)
    public long countCriticalAlerts() {
        return alertRepository.countBySeverityAndActiveTrue(com.sky2dev.day15.entity.AlertSeverity.CRITICAL);
    }
}
