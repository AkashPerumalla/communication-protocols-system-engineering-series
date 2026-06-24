package com.sky2dev.day23.service;

import com.sky2dev.day23.dto.AlarmResponse;
import com.sky2dev.day23.entity.AlarmEvent;
import com.sky2dev.day23.entity.AlarmSeverity;
import com.sky2dev.day23.repository.AlarmEventRepository;
import com.sky2dev.day23.simulation.TelemetrySample;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

    private final AlarmEventRepository alarmEventRepository;

    public Optional<AlarmEvent> evaluateTelemetry(TelemetrySample sample) {
        boolean alarmCondition = sample.cpuUsage() >= 85.0 || sample.temperature() >= 75.0;
        if (!alarmCondition) {
            return Optional.empty();
        }

        AlarmSeverity severity = sample.cpuUsage() >= 90.0 || sample.temperature() >= 80.0
                ? AlarmSeverity.CRITICAL
                : AlarmSeverity.HIGH;

        AlarmEvent event = AlarmEvent.builder()
                .deviceId(sample.deviceId())
                .severity(severity)
                .message("Threshold violation detected for device " + sample.deviceId())
                .acknowledged(false)
                .timestamp(Instant.now())
                .build();
        return Optional.of(alarmEventRepository.save(event));
    }

    @Transactional(readOnly = true)
    public List<AlarmResponse> getAlarms() {
        return alarmEventRepository.findTop50ByOrderByTimestampDesc().stream().map(AlarmResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public long countActiveAlarms() {
        return alarmEventRepository.findByAcknowledgedFalse().size();
    }
}
