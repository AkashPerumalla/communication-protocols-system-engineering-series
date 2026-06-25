package com.sky2dev.day24.dto;

import java.util.Map;

public record PlatformStatusDto(
        String service,
        String status,
        String schedulerState,
        Map<String, String> configuredSchedules
) {
}
