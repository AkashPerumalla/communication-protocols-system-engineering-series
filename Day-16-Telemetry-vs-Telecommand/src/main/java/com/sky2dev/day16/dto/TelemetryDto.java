package com.sky2dev.day16.dto;

import java.time.Instant;

public record TelemetryDto(Long id,
                           Long deviceId,
                           String deviceCode,
                           Instant collectedAt,
                           double cpu,
                           double memory,
                           double temperature,
                           double power,
                           String interfaceStatus,
                           Double rfPower,
                           Double ber,
                           Boolean carrierLock,
                           Long frequencyMHz,
                           Long symbolRateKsps,
                           Double ebNo,
                           String bucStatus,
                           String lnbStatus,
                           String modemStatus,
                           Double uplinkPower,
                           Double downlinkPower,
                           String sourceScenario) {
}
