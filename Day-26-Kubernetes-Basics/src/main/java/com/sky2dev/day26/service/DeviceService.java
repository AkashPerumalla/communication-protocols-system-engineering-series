package com.sky2dev.day26.service;

import com.sky2dev.day26.dto.DeviceRequest;
import com.sky2dev.day26.dto.DeviceResponse;
import com.sky2dev.day26.entity.Device;
import com.sky2dev.day26.exception.ResourceNotFoundException;
import com.sky2dev.day26.mapper.DeviceMapper;
import com.sky2dev.day26.repository.DeviceRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Transactional(readOnly = true)
    public List<DeviceResponse> getAllDevices() {
        return deviceRepository.findAllByOrderByNameAsc().stream()
                .map(deviceMapper::toResponse)
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
                .clusterZone(request.region() + "-zone-a")
                .firmwareVersion("fw-26.1")
                .lastSeen(now)
                .createdAt(now)
                .updatedAt(now)
                .build();

        return deviceMapper.toResponse(deviceRepository.save(device));
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
        device.setClusterZone(request.region() + "-zone-a");
        device.setUpdatedAt(LocalDateTime.now());

        return deviceMapper.toResponse(deviceRepository.save(device));
    }

    @Transactional
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device not found with id: " + id);
        }
        deviceRepository.deleteById(id);
    }
}
