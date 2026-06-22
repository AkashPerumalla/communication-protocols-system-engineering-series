package com.sky2dev.day21.dto;

import com.sky2dev.day21.entity.DeviceStatus;
import com.sky2dev.day21.entity.DeviceType;
import com.sky2dev.day21.validation.ValidationPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DeviceRequest(
        @NotBlank(message = "hostname is required")
        String hostname,
        @NotBlank(message = "ipAddress is required")
        @Pattern(regexp = ValidationPatterns.IPV4, message = "ipAddress must be a valid IPv4 address")
        String ipAddress,
        @NotNull(message = "deviceType is required")
        DeviceType deviceType,
        @NotBlank(message = "vendor is required")
        String vendor,
        @NotBlank(message = "model is required")
        String model,
        @NotBlank(message = "firmwareVersion is required")
        String firmwareVersion,
        @NotBlank(message = "location is required")
        String location,
        @NotNull(message = "status is required")
        DeviceStatus status
) {
}
