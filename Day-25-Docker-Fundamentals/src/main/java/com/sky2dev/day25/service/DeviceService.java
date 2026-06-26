package com.sky2dev.day25.service;

import com.sky2dev.day25.dto.DeviceRequest;
import com.sky2dev.day25.dto.DeviceResponse;
import com.sky2dev.day25.entity.Device;
import com.sky2dev.day25.exception.ResourceNotFoundException;
import com.sky2dev.day25.repository.DeviceRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Transactional(readOnly = true)
    public List<DeviceResponse> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(DeviceResponse::from)
                .toList();
    }

    @Transactional
    public DeviceResponse createDevice(DeviceRequest request) {
        if (deviceRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("Device name already exists: " + request.name());
        }
        if (deviceRepository.existsByIpAddress(request.ipAddress())) {
            throw new IllegalArgumentException("IP address already exists: " + request.ipAddress());
        }

        LocalDateTime now = LocalDateTime.now();
        Device device = Device.builder()
                .name(request.name())
                .ipAddress(request.ipAddress())
                .deviceType(request.deviceType())
            .status(request.status())
                .region(request.region())
                .lastSeen(now)
                .createdAt(now)
                .updatedAt(now)
                .build();

        return DeviceResponse.from(deviceRepository.save(device));
    }

    @Transactional
    public DeviceResponse updateDevice(Long id, DeviceRequest request) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));

        device.setName(request.name());
        device.setIpAddress(request.ipAddress());
        device.setDeviceType(request.deviceType());
        device.setStatus(request.status());
        device.setRegion(request.region());
        device.setUpdatedAt(LocalDateTime.now());

        return DeviceResponse.from(deviceRepository.save(device));
    }

    @Transactional
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device not found with id: " + id);
        }
        deviceRepository.deleteById(id);
    }
}
