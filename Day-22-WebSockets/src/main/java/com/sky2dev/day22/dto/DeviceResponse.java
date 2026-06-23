package com.sky2dev.day22.dto;

import com.sky2dev.day22.entity.DeviceStatus;
import com.sky2dev.day22.entity.DeviceType;

public record DeviceResponse(
        Long id,
        String hostname,
        String ipAddress,
        DeviceType type,
        DeviceStatus status
) {
}
