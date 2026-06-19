package com.sky2dev.day18.dto;

import jakarta.validation.constraints.NotBlank;

public record RecoveryRequest(
        @NotBlank(message = "triggeredBy is required")
        String triggeredBy
) {
}
