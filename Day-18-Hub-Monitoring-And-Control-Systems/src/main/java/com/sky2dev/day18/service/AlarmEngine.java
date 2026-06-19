package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.AlarmDto;
import com.sky2dev.day18.model.Alarm;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.AlarmStatus;
import com.sky2dev.day18.model.AlarmType;
import com.sky2dev.day18.model.DeviceMetric;
import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.model.InterfaceStatus;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.DeviceMetricRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmEngine {

    private final HubDeviceRepository hubDeviceRepository;
    private final DeviceMetricRepository deviceMetricRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    public List<AlarmDto> runThresholdEvaluation() {
        List<Alarm> alarms = new ArrayList<>();
        hubDeviceRepository.findAll().forEach(device -> {
            DeviceMetric metric = deviceMetricRepository.findTopByDeviceIdOrderByTimestampDesc(device.getId());
            if (metric == null) {
                return;
            }
            evaluateSignal(device.getId(), metric, alarms);
            evaluateTemperature(metric, alarms);
            evaluatePower(metric, alarms);
            evaluateCpu(metric, alarms);
            evaluateInterface(metric, alarms);
            if (device.getStatus() == DeviceStatus.OFFLINE) {
                alarms.add(createAlarm(metric, AlarmSeverity.CRITICAL, AlarmType.DEVICE_OFFLINE,
                        "Device is offline in hub operations"));
            }
        });

        return alarmRepository.saveAll(alarms).stream().map(AlarmDto::from).toList();
    }

    @Transactional
    public AlarmDto acknowledge(Long alarmId, String acknowledgedBy) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("Alarm not found: " + alarmId));
        alarm.setAcknowledged(true);
        alarm.setStatus(AlarmStatus.ACKNOWLEDGED);
        alarm.setMessage(alarm.getMessage() + " | acknowledgedBy=" + acknowledgedBy);
        return AlarmDto.from(alarmRepository.save(alarm));
    }

    @Transactional(readOnly = true)
    public List<AlarmDto> listAlarms() {
        return alarmRepository.findAllByOrderByCreatedAtDesc().stream().map(AlarmDto::from).toList();
    }

    private void evaluateSignal(Long deviceId, DeviceMetric metric, List<Alarm> alarms) {
        BigDecimal signal = metric.getSignalStrength();
        if (signal == null) {
            return;
        }
        double value = signal.doubleValue();
        if (value < -95) {
            alarms.add(createAlarm(metric, AlarmSeverity.CRITICAL, AlarmType.LOW_SIGNAL,
                    "Critical low signal on deviceId=" + deviceId + " value=" + value + " dBm"));
        } else if (value < -85) {
            alarms.add(createAlarm(metric, AlarmSeverity.MAJOR, AlarmType.LOW_SIGNAL,
                    "Major low signal on deviceId=" + deviceId + " value=" + value + " dBm"));
        } else if (value < -75) {
            alarms.add(createAlarm(metric, AlarmSeverity.WARNING, AlarmType.LOW_SIGNAL,
                    "Warning low signal on deviceId=" + deviceId + " value=" + value + " dBm"));
        }
    }

    private void evaluateTemperature(DeviceMetric metric, List<Alarm> alarms) {
        if (metric.getTemperature() == null) {
            return;
        }
        double value = metric.getTemperature().doubleValue();
        if (value > 80) {
            alarms.add(createAlarm(metric, AlarmSeverity.CRITICAL, AlarmType.HIGH_TEMPERATURE,
                    "Critical equipment temperature: " + value + " C"));
        } else if (value > 70) {
            alarms.add(createAlarm(metric, AlarmSeverity.MAJOR, AlarmType.HIGH_TEMPERATURE,
                    "Major equipment temperature: " + value + " C"));
        } else if (value > 60) {
            alarms.add(createAlarm(metric, AlarmSeverity.WARNING, AlarmType.HIGH_TEMPERATURE,
                    "Warning equipment temperature: " + value + " C"));
        }
    }

    private void evaluatePower(DeviceMetric metric, List<Alarm> alarms) {
        if (metric.getPowerLevel() == null) {
            return;
        }
        double value = metric.getPowerLevel().doubleValue();
        if (value < 20) {
            alarms.add(createAlarm(metric, AlarmSeverity.CRITICAL, AlarmType.POWER_ANOMALY,
                    "Critical power level: " + value + "%"));
        } else if (value < 30) {
            alarms.add(createAlarm(metric, AlarmSeverity.MAJOR, AlarmType.POWER_ANOMALY,
                    "Major power level: " + value + "%"));
        } else if (value < 40) {
            alarms.add(createAlarm(metric, AlarmSeverity.WARNING, AlarmType.POWER_ANOMALY,
                    "Warning power level: " + value + "%"));
        }
    }

    private void evaluateCpu(DeviceMetric metric, List<Alarm> alarms) {
        if (metric.getCpuUsage() == null) {
            return;
        }
        double value = metric.getCpuUsage().doubleValue();
        if (value > 92) {
            alarms.add(createAlarm(metric, AlarmSeverity.CRITICAL, AlarmType.HIGH_CPU,
                    "Critical CPU usage: " + value + "%"));
        } else if (value > 85) {
            alarms.add(createAlarm(metric, AlarmSeverity.MAJOR, AlarmType.HIGH_CPU,
                    "Major CPU usage: " + value + "%"));
        } else if (value > 75) {
            alarms.add(createAlarm(metric, AlarmSeverity.WARNING, AlarmType.HIGH_CPU,
                    "Warning CPU usage: " + value + "%"));
        }
    }

    private void evaluateInterface(DeviceMetric metric, List<Alarm> alarms) {
        if (metric.getInterfaceStatus() == InterfaceStatus.DOWN) {
            alarms.add(createAlarm(metric, AlarmSeverity.MAJOR, AlarmType.INTERFACE_DOWN,
                    "Interface down detected at hub interface layer"));
        }
    }

    private Alarm createAlarm(DeviceMetric metric, AlarmSeverity severity, AlarmType type, String message) {
        return Alarm.builder()
                .device(metric.getDevice())
                .severity(severity)
                .alarmType(type)
                .message(message)
                .status(AlarmStatus.OPEN)
                .acknowledged(false)
                .createdAt(Instant.now())
                .build();
    }
}
