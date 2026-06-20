package com.sky2dev.day19.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FrequencyPlanGenerateRequest(
        @NotBlank String transponderSatelliteName,
        @NotNull Integer transponderNumber,
        @NotBlank String channelPrefix,
        @NotNull @DecimalMin("1.0") Double startUplinkFrequency,
        @NotNull @DecimalMin("1.0") Double startDownlinkFrequency,
        @NotNull @DecimalMin("0.1") Double channelBandwidth,
        @NotNull @DecimalMin("0.01") Double guardBand,
        @NotNull Integer channelCount
) {
}
