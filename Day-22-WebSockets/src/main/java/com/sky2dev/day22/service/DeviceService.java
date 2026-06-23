package com.sky2dev.day22.service;

import com.sky2dev.day22.dto.DeviceResponse;
import com.sky2dev.day22.repository.DeviceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public List<DeviceResponse> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(device -> new DeviceResponse(device.getId(), device.getHostname(), device.getIpAddress(),
                        device.getType(), device.getStatus()))
                .toList();
    }
}
