package com.sky2dev.day19.dto;

public record NocDashboardDto(
        Long activeLinks,
        Long activeAlarms,
        Long criticalAlarms,
        Double linkAvailability,
        Double averageCn,
        Double averageEbNo,
        Double averageBer
) {
}
