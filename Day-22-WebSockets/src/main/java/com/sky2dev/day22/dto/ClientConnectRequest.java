package com.sky2dev.day22.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientConnectRequest(
        @NotBlank(message = "username is required")
        String username
) {
}
