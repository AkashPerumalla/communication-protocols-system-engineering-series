package com.sky2dev.day19.dto;

public record MonitoringFindingDto(
        String linkName,
        String category,
        String detail,
        String severity
) {
}
