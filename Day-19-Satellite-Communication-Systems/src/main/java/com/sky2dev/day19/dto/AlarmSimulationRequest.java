package com.sky2dev.day19.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlarmSimulationRequest(
        @NotBlank String linkName,
        @NotNull @DecimalMin("0.0") Double cnDb,
        @NotNull @DecimalMin("0.0") Double ebNo,
        @NotNull @DecimalMin("0.0") Double ber,
        @NotNull Double rxPowerDbm,
        @NotNull String lockStatus
) {
}
