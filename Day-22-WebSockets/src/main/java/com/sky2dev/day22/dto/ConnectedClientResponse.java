package com.sky2dev.day22.dto;

import java.time.Instant;

public record ConnectedClientResponse(
        Long id,
        String username,
        String sessionId,
        Instant connectedAt,
        boolean active
) {
}
