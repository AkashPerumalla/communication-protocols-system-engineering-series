package com.sky2dev.day21.dto;

import java.time.Instant;

public record ApiAuditLogResponse(
        Long id,
        String endpoint,
        String method,
        int responseCode,
        long executionTime,
        Instant timestamp
) {
}
