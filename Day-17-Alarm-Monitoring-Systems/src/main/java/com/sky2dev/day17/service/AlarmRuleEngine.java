package com.sky2dev.day17.service;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmRule;
import com.sky2dev.day17.model.AlarmSeverity;
import com.sky2dev.day17.model.AlarmState;
import com.sky2dev.day17.model.AlarmType;
import com.sky2dev.day17.model.MetricRecord;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlarmRuleEngine {

    public List<Alarm> evaluate(MetricRecord metric, List<AlarmRule> rules) {
        List<Alarm> alarms = new ArrayList<>();
        for (AlarmRule rule : rules) {
            Double metricValue = resolveMetricValue(metric, rule.getMetricName());
            if (metricValue == null) {
                continue;
            }
            if (isTriggered(metricValue, rule.getComparator(), rule.getThresholdValue())) {
                alarms.add(createAlarm(metric, rule.getAlarmType(), rule.getSeverity(), rule.getDescription()));
            }
        }
        if (Boolean.FALSE.equals(metric.getDeviceReachable())) {
            alarms.add(createAlarm(metric, AlarmType.DEVICE_UNREACHABLE, AlarmSeverity.CRITICAL, "Device unreachable"));
        }
        if (Boolean.FALSE.equals(metric.getApplicationHealthy())) {
            alarms.add(createAlarm(metric, AlarmType.APPLICATION_DOWN, AlarmSeverity.MAJOR, "Application down"));
        }
        return alarms;
    }

    private Double resolveMetricValue(MetricRecord metric, String metricName) {
        return switch (metricName) {
            case "cpuUsage" -> metric.getCpuUsage();
            case "temperatureC" -> metric.getTemperatureC();
            case "memoryUsage" -> metric.getMemoryUsage();
            case "signalStrengthDbm" -> metric.getSignalStrengthDbm();
            case "ber" -> metric.getBer();
            case "ebno" -> metric.getEbno();
            default -> null;
        };
    }

    private boolean isTriggered(Double value, String comparator, Double threshold) {
        return switch (comparator) {
            case ">" -> value > threshold;
            case ">=" -> value >= threshold;
            case "<" -> value < threshold;
            case "<=" -> value <= threshold;
            case "==" -> value.equals(threshold);
            default -> false;
        };
    }

    private Alarm createAlarm(MetricRecord metric, AlarmType alarmType, AlarmSeverity severity, String message) {
        Instant now = metric.getRecordedAt();
        return Alarm.builder()
                .device(metric.getDevice())
                .metricRecord(metric)
                .alarmType(alarmType)
                .severity(severity)
                .state(AlarmState.DETECTED)
                .message(message)
                .correlationKey(metric.getDevice().getCategory())
                .detectedAt(now)
                .openedAt(now)
                .build();
    }
}
