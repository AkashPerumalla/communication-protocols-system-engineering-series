package com.sky2dev.day26.mapper;

import com.sky2dev.day26.dto.DeviceResponse;
import com.sky2dev.day26.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public DeviceResponse toResponse(Device device) {
        return DeviceResponse.from(device);
    }
}
