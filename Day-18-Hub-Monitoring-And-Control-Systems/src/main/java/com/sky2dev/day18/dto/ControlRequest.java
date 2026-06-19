package com.sky2dev.day18.dto;

import jakarta.validation.constraints.NotBlank;

public record ControlRequest(
        @NotBlank(message = "commandPayload is required")
        String commandPayload,
        @NotBlank(message = "executedBy is required")
        String executedBy
) {
}
