package com.sky2dev.day18.dto;

import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.model.DeviceType;
import com.sky2dev.day18.model.HubDevice;
import java.time.Instant;

public record HubDeviceDto(
        Long id,
        String hostname,
        DeviceType deviceType,
        String ipAddress,
        DeviceStatus status,
        String location,
        String vendor,
        String firmwareVersion,
        Instant lastSeen
) {
    public static HubDeviceDto from(HubDevice device) {
        return new HubDeviceDto(
                device.getId(),
                device.getHostname(),
                device.getDeviceType(),
                device.getIpAddress(),
                device.getStatus(),
                device.getLocation(),
                device.getVendor(),
                device.getFirmwareVersion(),
                device.getLastSeen()
        );
    }
}
