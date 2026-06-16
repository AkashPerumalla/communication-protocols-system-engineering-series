package com.sky2dev.day15.service;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.exception.DeviceNotFoundException;
import com.sky2dev.day15.repository.DeviceRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Transactional(readOnly = true)
    public List<Device> findAllDevices() {
        return deviceRepository.findAll().stream()
                .sorted(Comparator.comparing(Device::getHostname))
                .toList();
    }

    @Transactional(readOnly = true)
    public Device getDevice(Long id) {
        return deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Device> findOnlineDevices() {
        return deviceRepository.findByStatus(com.sky2dev.day15.entity.DeviceStatus.ONLINE);
    }
}
