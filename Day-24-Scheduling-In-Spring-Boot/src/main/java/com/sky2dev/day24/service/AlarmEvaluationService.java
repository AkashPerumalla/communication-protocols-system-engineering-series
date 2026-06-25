package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.AlarmEvent;
import com.sky2dev.day24.entity.AlarmSeverity;
import com.sky2dev.day24.entity.TelemetryRecord;
import com.sky2dev.day24.repository.AlarmEventRepository;
import com.sky2dev.day24.repository.TelemetryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmEvaluationService {

    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;

    @Transactional
    public List<AlarmEvent> evaluateAlarms() {
        List<TelemetryRecord> telemetry = telemetryRecordRepository.findAll();
        List<AlarmEvent> alarms = new ArrayList<>();

        for (TelemetryRecord record : telemetry) {
            StringBuilder message = new StringBuilder();
            if (record.getCpuUsage() > 80) {
                message.append("CPU high ");
            }
            if (record.getMemoryUsage() > 85) {
                message.append("Memory high ");
            }
            if (record.getTemperature() > 70) {
                message.append("Temperature high ");
            }
            if (record.getSignalStrength() < -80) {
                message.append("Signal weak ");
            }

            if (message.length() == 0) {
                continue;
            }

            AlarmSeverity severity = determineSeverity(record);
            alarms.add(AlarmEvent.builder()
                    .deviceId(record.getDeviceId())
                    .severity(severity)
                    .message(message.toString().trim())
                    .acknowledged(false)
                    .timestamp(Instant.now())
                    .build());
        }

        if (alarms.isEmpty()) {
            return List.of();
        }
        return alarmEventRepository.saveAll(alarms);
    }

    private AlarmSeverity determineSeverity(TelemetryRecord record) {
        if (record.getCpuUsage() >= 95 || record.getMemoryUsage() >= 95 || record.getTemperature() >= 85 || record.getSignalStrength() <= -90) {
            return AlarmSeverity.CRITICAL;
        }
        if (record.getCpuUsage() > 85 || record.getMemoryUsage() > 90 || record.getTemperature() > 75 || record.getSignalStrength() < -85) {
            return AlarmSeverity.MAJOR;
        }
        return AlarmSeverity.WARNING;
    }
}
