package com.sky2dev.day20.dto;

import com.sky2dev.day20.entity.MetricRecord;
import java.time.Instant;

public record MetricRecordDto(
        Long id,
        String agentId,
        Instant timestamp,
        double cpuUsage,
        double memoryUsage,
        double diskUsage,
        double networkIn,
        double networkOut,
        int processCount,
        long uptimeSeconds) {

    public static MetricRecordDto from(MetricRecord metric) {
        return new MetricRecordDto(
                metric.getId(),
                metric.getAgentId(),
                metric.getTimestamp(),
                metric.getCpuUsage(),
                metric.getMemoryUsage(),
                metric.getDiskUsage(),
                metric.getNetworkIn(),
                metric.getNetworkOut(),
                metric.getProcessCount(),
                metric.getUptimeSeconds());
    }
}
