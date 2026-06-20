package com.sky2dev.day17.dto;

import com.sky2dev.day17.model.MetricRecord;
import java.time.Instant;

public record MetricDto(Long id, Long deviceId, Instant recordedAt, Double cpuUsage, Double temperatureC, Double memoryUsage, Double signalStrengthDbm, Double ber, Double ebno, Boolean deviceReachable, Boolean applicationHealthy) {
    public static MetricDto from(MetricRecord metric) {
        return new MetricDto(metric.getId(), metric.getDevice().getId(), metric.getRecordedAt(), metric.getCpuUsage(), metric.getTemperatureC(), metric.getMemoryUsage(), metric.getSignalStrengthDbm(), metric.getBer(), metric.getEbno(), metric.getDeviceReachable(), metric.getApplicationHealthy());
    }
}
