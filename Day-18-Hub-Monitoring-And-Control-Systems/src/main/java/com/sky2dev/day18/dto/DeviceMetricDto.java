package com.sky2dev.day18.dto;

import com.sky2dev.day18.model.DeviceMetric;
import com.sky2dev.day18.model.InterfaceStatus;
import java.math.BigDecimal;
import java.time.Instant;

public record DeviceMetricDto(
        Long id,
        Long deviceId,
        String deviceName,
        BigDecimal cpuUsage,
        BigDecimal memoryUsage,
        BigDecimal temperature,
        BigDecimal signalStrength,
        BigDecimal powerLevel,
        InterfaceStatus interfaceStatus,
        Instant timestamp,
        BigDecimal ebNo,
        BigDecimal ber,
        Boolean carrierLock,
        BigDecimal frequencyMhz,
        BigDecimal symbolRateKsps,
        BigDecimal bucOutputPower,
        BigDecimal bucTemperature,
        String bucStatus,
        BigDecimal lnbGain,
        BigDecimal lnbNoiseFigure,
        String lnbStatus,
        BigDecimal upsBatteryPercent,
        BigDecimal upsInputVoltage,
        BigDecimal upsOutputVoltage,
        BigDecimal powerCurrent,
        BigDecimal powerVoltage,
        BigDecimal powerConsumption
) {
    public static DeviceMetricDto from(DeviceMetric metric) {
        return new DeviceMetricDto(
                metric.getId(),
                metric.getDevice().getId(),
                metric.getDevice().getHostname(),
                metric.getCpuUsage(),
                metric.getMemoryUsage(),
                metric.getTemperature(),
                metric.getSignalStrength(),
                metric.getPowerLevel(),
                metric.getInterfaceStatus(),
                metric.getTimestamp(),
                metric.getEbNo(),
                metric.getBer(),
                metric.getCarrierLock(),
                metric.getFrequencyMhz(),
                metric.getSymbolRateKsps(),
                metric.getBucOutputPower(),
                metric.getBucTemperature(),
                metric.getBucStatus(),
                metric.getLnbGain(),
                metric.getLnbNoiseFigure(),
                metric.getLnbStatus(),
                metric.getUpsBatteryPercent(),
                metric.getUpsInputVoltage(),
                metric.getUpsOutputVoltage(),
                metric.getPowerCurrent(),
                metric.getPowerVoltage(),
                metric.getPowerConsumption()
        );
    }
}
