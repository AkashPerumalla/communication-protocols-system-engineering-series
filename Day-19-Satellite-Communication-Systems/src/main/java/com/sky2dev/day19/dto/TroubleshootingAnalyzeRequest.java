package com.sky2dev.day19.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TroubleshootingAnalyzeRequest(
        @NotBlank String linkName,
        @NotNull Double cnDb,
        @NotNull Double ebNo,
        @NotNull Double ber,
        @NotNull Double rxPowerDbm,
        @NotNull String lockStatus
) {
}
