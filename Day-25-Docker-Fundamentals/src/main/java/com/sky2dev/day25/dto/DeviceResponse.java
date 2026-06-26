package com.sky2dev.day25.dto;

import com.sky2dev.day25.entity.Device;
import com.sky2dev.day25.entity.DeviceStatus;
import com.sky2dev.day25.entity.DeviceType;
import java.time.LocalDateTime;

public record DeviceResponse(
        Long id,
        String name,
        String ipAddress,
        DeviceType deviceType,
        DeviceStatus status,
        String region,
        LocalDateTime lastSeen
) {
    public static DeviceResponse from(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getName(),
                device.getIpAddress(),
                device.getDeviceType(),
                device.getStatus(),
                device.getRegion(),
                device.getLastSeen());
    }
}
