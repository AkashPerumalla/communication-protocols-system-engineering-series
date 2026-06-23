package com.sky2dev.day22.service;

import com.sky2dev.day22.dto.AlarmResponse;
import com.sky2dev.day22.entity.AlarmEvent;
import com.sky2dev.day22.entity.AlarmSeverity;
import com.sky2dev.day22.entity.Device;
import com.sky2dev.day22.entity.DeviceStatus;
import com.sky2dev.day22.entity.TelemetryMetric;
import com.sky2dev.day22.exception.EntityNotFoundException;
import com.sky2dev.day22.repository.AlarmEventRepository;
import com.sky2dev.day22.repository.DeviceRepository;
import com.sky2dev.day22.repository.TelemetryMetricRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmEventRepository alarmEventRepository;
    private final DeviceRepository deviceRepository;
    private final TelemetryMetricRepository telemetryMetricRepository;

    public List<AlarmResponse> getAlarms() {
        return alarmEventRepository.findTop200ByOrderByTimestampDesc().stream().map(this::toResponse).toList();
    }

    public List<AlarmResponse> evaluateAndGenerateAlarms() {
        List<AlarmEvent> events = new ArrayList<>();

        for (Device device : deviceRepository.findAll()) {
            AlarmEvent event = evaluateDevice(device);
            if (event != null) {
                events.add(event);
            }
        }

        if (events.isEmpty()) {
            return List.of();
        }
        return alarmEventRepository.saveAll(events).stream().map(this::toResponse).toList();
    }

    public AlarmResponse acknowledge(Long id) {
        AlarmEvent alarm = alarmEventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alarm not found: " + id));
        alarm.setAcknowledged(true);
        return toResponse(alarmEventRepository.save(alarm));
    }

    private AlarmEvent evaluateDevice(Device device) {
        if (device.getStatus() == DeviceStatus.OFFLINE) {
            return buildAlarm(device.getId(), AlarmSeverity.CRITICAL, "Device offline alarm");
        }

        TelemetryMetric latest = telemetryMetricRepository.findTopByDeviceIdOrderByTimestampDesc(device.getId()).orElse(null);
        if (latest == null) {
            return null;
        }

        if (latest.getCpuUsage() > 95 || latest.getMemoryUsage() > 92 || latest.getTemperature() > 85
                || latest.getSignalStrength() < -90) {
            return buildAlarm(device.getId(), AlarmSeverity.CRITICAL, "Critical threshold breach detected");
        }

        if (latest.getCpuUsage() > 90 || latest.getMemoryUsage() > 89 || latest.getTemperature() > 78
                || latest.getSignalStrength() < -85) {
            return buildAlarm(device.getId(), AlarmSeverity.MAJOR, "Major threshold breach detected");
        }

        if (latest.getCpuUsage() > 80 || latest.getMemoryUsage() > 85 || latest.getTemperature() > 70
                || latest.getSignalStrength() < -80) {
            return buildAlarm(device.getId(), AlarmSeverity.WARNING, "Warning threshold breach detected");
        }

        return null;
    }

    private AlarmEvent buildAlarm(Long deviceId, AlarmSeverity severity, String message) {
        return AlarmEvent.builder()
                .deviceId(deviceId)
                .severity(severity)
                .message(message)
                .acknowledged(false)
                .timestamp(Instant.now())
                .build();
    }

    private AlarmResponse toResponse(AlarmEvent alarmEvent) {
        return new AlarmResponse(alarmEvent.getId(), alarmEvent.getDeviceId(), alarmEvent.getSeverity(),
                alarmEvent.getMessage(), alarmEvent.isAcknowledged(), alarmEvent.getTimestamp());
    }
}
