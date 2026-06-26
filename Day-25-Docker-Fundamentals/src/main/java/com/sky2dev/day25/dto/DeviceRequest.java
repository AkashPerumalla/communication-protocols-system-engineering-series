package com.sky2dev.day25.dto;

import com.sky2dev.day25.entity.DeviceStatus;
import com.sky2dev.day25.entity.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DeviceRequest(
        @NotBlank(message = "Device name is required")
        @Size(max = 64, message = "Device name must be <= 64 characters")
        String name,

        @NotBlank(message = "IP address is required")
        @Pattern(
                regexp = "^((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)$",
                message = "Invalid IPv4 address")
        String ipAddress,

        @NotNull(message = "Device type is required")
        DeviceType deviceType,

        @NotNull(message = "Device status is required")
        DeviceStatus status,

        @NotBlank(message = "Region is required")
        @Size(max = 64, message = "Region must be <= 64 characters")
        String region
) {
}
