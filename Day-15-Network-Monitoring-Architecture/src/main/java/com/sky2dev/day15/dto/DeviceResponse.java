package com.sky2dev.day15.dto;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.DeviceCategory;
import com.sky2dev.day15.entity.DeviceStatus;
import com.sky2dev.day15.entity.InterfaceStatus;
import java.time.Instant;

public record DeviceResponse(
        Long id,
        String hostname,
        String ipAddress,
        DeviceCategory category,
        Double cpuUsage,
        Double memoryUsage,
        Long uptime,
        DeviceStatus status,
        Double temperature,
        InterfaceStatus interfaceStatus,
        Instant lastUpdated) {

    public static DeviceResponse from(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getHostname(),
                device.getIpAddress(),
                device.getCategory(),
                device.getCpuUsage(),
                device.getMemoryUsage(),
                device.getUptime(),
                device.getStatus(),
                device.getTemperature(),
                device.getInterfaceStatus(),
                device.getLastUpdated());
    }
}
