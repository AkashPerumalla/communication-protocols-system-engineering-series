package com.sky2dev.day18.dto;

import jakarta.validation.constraints.NotBlank;

public record AcknowledgeRequest(
        @NotBlank(message = "acknowledgedBy is required")
        String acknowledgedBy
) {
}
