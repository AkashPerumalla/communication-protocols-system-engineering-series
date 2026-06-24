package com.sky2dev.day23.dto;

import java.util.List;

public record ThreadPerformanceResponse(
        String marker,
        List<PerformanceResult> results
) {
}
