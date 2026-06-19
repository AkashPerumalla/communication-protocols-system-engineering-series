package com.sky2dev.day18.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDto(
        long totalDevices,
        long onlineDevices,
        long offlineDevices,
        long criticalAlarms,
        long majorAlarms,
        long warnings,
        BigDecimal deviceAvailability,
        List<ControlCommandDto> recentCommands
) {
}
