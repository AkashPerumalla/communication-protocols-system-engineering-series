package com.sky2dev.day18.dto;

import com.sky2dev.day18.model.DeviceStatus;

public record DeviceHealthDto(
        Long deviceId,
        String hostname,
        DeviceStatus status,
        int healthScore,
        String summary
) {
}
