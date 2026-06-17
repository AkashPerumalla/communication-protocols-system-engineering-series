package com.sky2dev.day16.dto;

import java.util.List;

public record AutomationResultDto(String marker, String message, List<CommandResultDto> recoveryActions, List<AlarmDto> clearedAlarms) {
}
