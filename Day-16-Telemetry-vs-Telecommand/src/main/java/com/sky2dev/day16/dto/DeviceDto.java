package com.sky2dev.day16.dto;

import java.time.Instant;

public record DeviceDto(Long id,
                        String deviceCode,
                        String displayName,
                        String deviceType,
                        String category,
                        String state,
                        boolean interfaceEnabled,
                        boolean carrierEnabled,
                        boolean modemHealthy,
                        boolean backupLinkActive,
                        long currentFrequencyMHz,
                        long currentSymbolRateKsps,
                        double currentCpu,
                        double currentMemory,
                        double currentTemperature,
                        double currentPower,
                        String currentInterfaceStatus,
                        String currentModemStatus,
                        Instant createdAt,
                        Instant updatedAt) {
}
