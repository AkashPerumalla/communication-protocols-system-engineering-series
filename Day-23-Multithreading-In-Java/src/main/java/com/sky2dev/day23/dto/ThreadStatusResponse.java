package com.sky2dev.day23.dto;

import java.util.List;

public record ThreadStatusResponse(
        String marker,
        List<String> observedStates,
        String details
) {
}
