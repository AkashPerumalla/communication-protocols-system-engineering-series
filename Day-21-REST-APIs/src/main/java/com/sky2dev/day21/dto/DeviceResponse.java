package com.sky2dev.day21.dto;

import com.sky2dev.day21.entity.DeviceStatus;
import com.sky2dev.day21.entity.DeviceType;
import java.time.Instant;

public record DeviceResponse(
        Long id,
        String hostname,
        String ipAddress,
        DeviceType deviceType,
        String vendor,
        String model,
        String firmwareVersion,
        String location,
        DeviceStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
