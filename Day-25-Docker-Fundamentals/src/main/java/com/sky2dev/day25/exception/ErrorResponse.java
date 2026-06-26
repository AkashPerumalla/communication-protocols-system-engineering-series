package com.sky2dev.day25.exception;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        String marker,
        String message,
        List<String> details,
        Instant timestamp
) {
}
