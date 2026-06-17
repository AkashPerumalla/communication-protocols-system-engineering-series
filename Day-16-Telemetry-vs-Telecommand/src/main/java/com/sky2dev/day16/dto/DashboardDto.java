package com.sky2dev.day16.dto;

import java.util.List;
import java.util.Map;

public record DashboardDto(List<DeviceDto> devices,
                           List<TelemetryDto> telemetry,
                           List<AlarmDto> activeAlarms,
                           List<CommandResultDto> recentCommands,
                           List<CommandResultDto> recoveryActions,
                           Map<String, Object> summary) {
}
