package com.sky2dev.day15.dto;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.DeviceStatus;
import com.sky2dev.day15.entity.InterfaceStatus;
import com.sky2dev.day15.entity.MetricSource;
import com.sky2dev.day15.entity.MonitoringMetric;
import java.time.Instant;

public record MonitoringMetricResponse(
        Long id,
        Long deviceId,
        String hostname,
        Instant capturedAt,
        Double cpuUsage,
        Double memoryUsage,
        Long uptime,
        DeviceStatus status,
        Double temperature,
        InterfaceStatus interfaceStatus,
        Double rfPower,
        Double ber,
        String carrierLock,
        Double frequencyGhz,
        Double symbolRateMsps,
        Double ebNo,
        String modemStatus,
        String bucStatus,
        String lnbStatus,
        Double uplinkPower,
        Double downlinkPower,
        MetricSource source) {

    public static MonitoringMetricResponse from(MonitoringMetric metric) {
        Device device = metric.getDevice();
        return new MonitoringMetricResponse(
                metric.getId(),
                device.getId(),
                device.getHostname(),
                metric.getCapturedAt(),
                metric.getCpuUsage(),
                metric.getMemoryUsage(),
                metric.getUptime(),
                metric.getStatus(),
                metric.getTemperature(),
                metric.getInterfaceStatus(),
                metric.getRfPower(),
                metric.getBer(),
                metric.getCarrierLock(),
                metric.getFrequencyGhz(),
                metric.getSymbolRateMsps(),
                metric.getEbNo(),
                metric.getModemStatus(),
                metric.getBucStatus(),
                metric.getLnbStatus(),
                metric.getUplinkPower(),
                metric.getDownlinkPower(),
                metric.getSource());
    }
}
