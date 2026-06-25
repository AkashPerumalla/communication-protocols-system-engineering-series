package com.sky2dev.day24.dto;

public record TaskStatisticsDto(
        long runningTasks,
        long successfulTasks,
        long failedTasks,
        long totalExecutions
) {
}
