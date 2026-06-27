package com.sky2dev.day26.dto;

import com.sky2dev.day26.entity.Device;
import com.sky2dev.day26.entity.DeviceStatus;
import com.sky2dev.day26.entity.DeviceType;
import java.time.LocalDateTime;

public record DeviceResponse(
        Long id,
        String name,
        String ipAddress,
        DeviceType deviceType,
        DeviceStatus status,
        String region,
    String clusterZone,
    String firmwareVersion,
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
        device.getClusterZone(),
        device.getFirmwareVersion(),
                device.getLastSeen());
    }
}
