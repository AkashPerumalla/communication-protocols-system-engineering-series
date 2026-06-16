package com.sky2dev.day15.alert;

import com.sky2dev.day15.entity.DeviceStatus;
import com.sky2dev.day15.entity.MonitoringMetric;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AlertRuleEngine {

    public List<AlertCandidate> evaluate(MonitoringMetric metric) {
        List<AlertCandidate> candidates = new ArrayList<>();
        if (metric.getStatus() == DeviceStatus.OFFLINE) {
            candidates.add(new AlertCandidate(
                    "DEVICE_OFFLINE",
                    com.sky2dev.day15.entity.AlertSeverity.CRITICAL,
                    metric.getDevice().getHostname() + " is offline"));
        }
        if (metric.getCpuUsage() > 95.0) {
            candidates.add(new AlertCandidate(
                    "HIGH_CPU",
                    com.sky2dev.day15.entity.AlertSeverity.CRITICAL,
                    metric.getDevice().getHostname() + " CPU is above 95%"));
        } else if (metric.getCpuUsage() > 85.0) {
            candidates.add(new AlertCandidate(
                    "HIGH_CPU",
                    com.sky2dev.day15.entity.AlertSeverity.WARNING,
                    metric.getDevice().getHostname() + " CPU is above 85%"));
        }
        if (metric.getMemoryUsage() > 95.0) {
            candidates.add(new AlertCandidate(
                    "HIGH_MEMORY",
                    com.sky2dev.day15.entity.AlertSeverity.CRITICAL,
                    metric.getDevice().getHostname() + " memory is above 95%"));
        } else if (metric.getMemoryUsage() > 90.0) {
            candidates.add(new AlertCandidate(
                    "HIGH_MEMORY",
                    com.sky2dev.day15.entity.AlertSeverity.WARNING,
                    metric.getDevice().getHostname() + " memory is above 90%"));
        }
        if (metric.getTemperature() > 75.0) {
            candidates.add(new AlertCandidate(
                    "HIGH_TEMPERATURE",
                    com.sky2dev.day15.entity.AlertSeverity.CRITICAL,
                    metric.getDevice().getHostname() + " temperature is above 75C"));
        } else if (metric.getTemperature() > 70.0) {
            candidates.add(new AlertCandidate(
                    "HIGH_TEMPERATURE",
                    com.sky2dev.day15.entity.AlertSeverity.WARNING,
                    metric.getDevice().getHostname() + " temperature is above 70C"));
        }
        return candidates;
    }
}
