package com.sky2dev.day17.dto;

import com.sky2dev.day17.model.ManagedDevice;

public record DeviceDto(Long id, String name, String category, String location, String ipAddress, String baselineSeverity) {
    public static DeviceDto from(ManagedDevice device) {
        return new DeviceDto(device.getId(), device.getName(), device.getCategory(), device.getLocation(), device.getIpAddress(), device.getBaselineSeverity().name());
    }
}
