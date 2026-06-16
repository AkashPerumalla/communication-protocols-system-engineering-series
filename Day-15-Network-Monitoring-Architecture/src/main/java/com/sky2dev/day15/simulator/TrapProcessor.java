package com.sky2dev.day15.simulator;

import com.sky2dev.day15.alert.AlertCandidate;
import com.sky2dev.day15.alert.AlertService;
import com.sky2dev.day15.entity.AlertSeverity;
import com.sky2dev.day15.entity.TrapEvent;
import com.sky2dev.day15.entity.TrapType;
import com.sky2dev.day15.repository.TrapEventRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrapProcessor {

    private final TrapEventRepository trapEventRepository;
    private final AlertService alertService;

    @Async("monitoringTaskExecutor")
    @Transactional
    public void process(TrapEvent trapEvent) {
        processSynchronously(trapEvent);
    }

    @Transactional
    public void processSynchronously(TrapEvent trapEvent) {
        TrapEvent stored = trapEventRepository.save(trapEvent);
        if (stored.getTrapType() != TrapType.DEVICE_RESTART) {
            alertService.reconcile(stored.getDevice(), "TRAP", toAlerts(stored));
        }
        stored.setProcessed(true);
        stored.setProcessedAt(Instant.now());
        trapEventRepository.save(stored);
    }

    @Transactional(readOnly = true)
    public List<TrapEvent> findRecentEvents() {
        return trapEventRepository.findTop100ByOrderByTriggeredAtDesc();
    }

    private List<AlertCandidate> toAlerts(TrapEvent trapEvent) {
        return switch (trapEvent.getTrapType()) {
            case INTERFACE_DOWN -> List.of(new AlertCandidate(
                    "TRAP_INTERFACE_DOWN",
                    AlertSeverity.CRITICAL,
                    trapEvent.getMessage()));
            case HIGH_TEMPERATURE -> List.of(new AlertCandidate(
                    "TRAP_HIGH_TEMPERATURE",
                    AlertSeverity.CRITICAL,
                    trapEvent.getMessage()));
            case HIGH_CPU -> List.of(new AlertCandidate(
                    "TRAP_HIGH_CPU",
                    AlertSeverity.WARNING,
                    trapEvent.getMessage()));
            case HIGH_MEMORY -> List.of(new AlertCandidate(
                    "TRAP_HIGH_MEMORY",
                    AlertSeverity.WARNING,
                    trapEvent.getMessage()));
            case DEVICE_RESTART -> List.of();
        };
    }
}
