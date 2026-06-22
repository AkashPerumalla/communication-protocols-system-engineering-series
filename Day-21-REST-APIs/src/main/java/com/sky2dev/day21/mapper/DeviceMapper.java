package com.sky2dev.day21.mapper;

import com.sky2dev.day21.dto.DeviceRequest;
import com.sky2dev.day21.dto.DeviceResponse;
import com.sky2dev.day21.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public Device toEntity(DeviceRequest request) {
        return Device.builder()
                .hostname(request.hostname())
                .ipAddress(request.ipAddress())
                .deviceType(request.deviceType())
                .vendor(request.vendor())
                .model(request.model())
                .firmwareVersion(request.firmwareVersion())
                .location(request.location())
                .status(request.status())
                .build();
    }

    public void updateEntity(Device device, DeviceRequest request) {
        device.setHostname(request.hostname());
        device.setIpAddress(request.ipAddress());
        device.setDeviceType(request.deviceType());
        device.setVendor(request.vendor());
        device.setModel(request.model());
        device.setFirmwareVersion(request.firmwareVersion());
        device.setLocation(request.location());
        device.setStatus(request.status());
    }

    public DeviceResponse toResponse(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getHostname(),
                device.getIpAddress(),
                device.getDeviceType(),
                device.getVendor(),
                device.getModel(),
                device.getFirmwareVersion(),
                device.getLocation(),
                device.getStatus(),
                device.getCreatedAt(),
                device.getUpdatedAt());
    }
}
