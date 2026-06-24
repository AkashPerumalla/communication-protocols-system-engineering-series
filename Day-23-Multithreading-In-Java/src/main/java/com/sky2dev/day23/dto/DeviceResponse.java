package com.sky2dev.day23.dto;

import com.sky2dev.day23.entity.Device;
import com.sky2dev.day23.entity.DeviceStatus;
import java.time.Instant;

public record DeviceResponse(
        Long id,
        String name,
        String type,
        DeviceStatus status,
        String ipAddress,
        Instant createdAt
) {
    public static DeviceResponse from(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getName(),
                device.getType(),
                device.getStatus(),
                device.getIpAddress(),
                device.getCreatedAt()
        );
    }
}
