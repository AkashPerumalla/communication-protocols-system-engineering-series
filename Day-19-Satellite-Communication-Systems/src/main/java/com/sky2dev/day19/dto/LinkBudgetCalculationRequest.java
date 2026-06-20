package com.sky2dev.day19.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LinkBudgetCalculationRequest(
        @NotBlank String linkName,
        @NotNull @DecimalMin("1.0") @DecimalMax("80.0") Double txPowerDbw,
        @NotNull @DecimalMin("0.0") @DecimalMax("90.0") Double txAntennaGainDbi,
        @NotNull @DecimalMin("0.0") @DecimalMax("10.0") Double implementationLossDb,
        @NotNull @DecimalMin("1.0") @DecimalMax("50000.0") Double slantRangeKm,
        @NotNull @DecimalMin("1.0") @DecimalMax("60.0") Double frequencyGhz,
        @NotNull @DecimalMin("-30.0") @DecimalMax("30.0") Double gOverTDbPerK,
        @NotNull @DecimalMin("10.0") @DecimalMax("5000.0") Double noiseTemperatureK,
        @NotNull @DecimalMin("0.1") @DecimalMax("1000.0") Double requiredCnDb
) {
}
