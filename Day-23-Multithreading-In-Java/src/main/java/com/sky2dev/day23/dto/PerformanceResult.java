package com.sky2dev.day23.dto;

public record PerformanceResult(
        String strategy,
        int expected,
        int actual,
        long durationMs,
        boolean accurate
) {
}
