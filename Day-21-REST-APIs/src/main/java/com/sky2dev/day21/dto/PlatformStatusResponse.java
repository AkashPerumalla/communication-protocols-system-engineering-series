package com.sky2dev.day21.dto;

public record PlatformStatusResponse(
        String platform,
        String version,
        String environment,
        String securityModel
) {
}
