package com.sky2dev.day20.dto;

import jakarta.validation.constraints.NotBlank;

public record AgentRegistrationRequest(
        @NotBlank String agentId,
        @NotBlank String hostname,
        @NotBlank String ipAddress,
        @NotBlank String location) {
}
