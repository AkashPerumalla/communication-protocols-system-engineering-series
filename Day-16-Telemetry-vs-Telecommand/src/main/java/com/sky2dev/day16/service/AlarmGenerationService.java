package com.sky2dev.day16.service;

import com.sky2dev.day16.model.Alarm;
import com.sky2dev.day16.model.AlarmSeverity;
import com.sky2dev.day16.model.AlarmStatus;
import com.sky2dev.day16.model.DeviceType;
import com.sky2dev.day16.model.TelemetryRecord;
import com.sky2dev.day16.repository.AlarmRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmGenerationService {

    private final AlarmRepository alarmRepository;

    @Transactional
    public void evaluateTelemetry(TelemetryRecord telemetryRecord) {
        if (telemetryRecord.getBer() != null && telemetryRecord.getBer() > 0.0001d) {
            raiseAlarm(telemetryRecord, "BER", telemetryRecord.getBer(), 0.0001d, AlarmSeverity.CRITICAL, "Bit error rate above recovery threshold");
        }
        if (telemetryRecord.getTemperature() > 78.0d) {
            raiseAlarm(telemetryRecord, "TEMPERATURE", telemetryRecord.getTemperature(), 78.0d, AlarmSeverity.MAJOR, "Device temperature above safe limit");
        }
        if (telemetryRecord.getPower() < 45.0d) {
            raiseAlarm(telemetryRecord, "POWER", telemetryRecord.getPower(), 45.0d, AlarmSeverity.MAJOR, "Power budget below operating floor");
        }
        if (telemetryRecord.getCarrierLock() != null && !telemetryRecord.getCarrierLock()) {
            raiseAlarm(telemetryRecord, "CARRIER_LOCK", 0.0d, 1.0d, AlarmSeverity.CRITICAL, "Carrier lock lost on satellite link");
        }
        if (telemetryRecord.getDevice().getDeviceType() == DeviceType.SENSOR_01 && "DOWN".equals(telemetryRecord.getInterfaceStatus())) {
            raiseAlarm(telemetryRecord, "INTERFACE", 0.0d, 1.0d, AlarmSeverity.MINOR, "Sensor interface is down");
        }
    }

    @Transactional
    public Alarm clearAlarm(Alarm alarm, String recoveryNote) {
        alarm.setStatus(AlarmStatus.CLEARED);
        alarm.setClearedAt(Instant.now());
        alarm.setRecoveryNote(recoveryNote);
        return alarmRepository.save(alarm);
    }

    @Transactional(readOnly = true)
    public List<Alarm> activeAlarms() {
        return alarmRepository.findByStatusOrderByTriggeredAtDesc(AlarmStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<Alarm> allAlarms() {
        return alarmRepository.findTop50ByOrderByTriggeredAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Alarm> activeAlarmForDeviceMetric(Long deviceId, String metricName) {
        return alarmRepository.findFirstByDeviceIdAndMetricNameAndStatus(deviceId, metricName, AlarmStatus.ACTIVE);
    }

    private void raiseAlarm(TelemetryRecord telemetryRecord, String metricName, double value, double threshold, AlarmSeverity severity, String message) {
        alarmRepository.findFirstByDeviceIdAndMetricNameAndStatus(telemetryRecord.getDevice().getId(), metricName, AlarmStatus.ACTIVE)
                .orElseGet(() -> alarmRepository.save(Alarm.builder()
                        .device(telemetryRecord.getDevice())
                        .severity(severity)
                        .status(AlarmStatus.ACTIVE)
                        .metricName(metricName)
                        .metricValue(value)
                        .thresholdValue(threshold)
                        .message(message)
                        .triggeredAt(Instant.now())
                        .build()));
    }
}
