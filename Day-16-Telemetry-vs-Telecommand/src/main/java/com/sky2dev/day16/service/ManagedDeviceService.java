package com.sky2dev.day16.service;

import com.sky2dev.day16.dto.DeviceDto;
import com.sky2dev.day16.model.ManagedDevice;
import com.sky2dev.day16.repository.ManagedDeviceRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ManagedDeviceService {

    private final ManagedDeviceRepository managedDeviceRepository;

    @Transactional(readOnly = true)
    public List<ManagedDevice> listDevices() {
        return managedDeviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ManagedDevice getDevice(Long id) {
        return managedDeviceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
    }

    public static DeviceDto toDto(ManagedDevice device) {
        return new DeviceDto(device.getId(), device.getDeviceCode(), device.getDisplayName(), device.getDeviceType().name(), device.getDeviceType().getCategory(), device.getState().name(), device.isInterfaceEnabled(), device.isCarrierEnabled(), device.isModemHealthy(), device.isBackupLinkActive(), device.getCurrentFrequencyMHz(), device.getCurrentSymbolRateKsps(), device.getCurrentCpu(), device.getCurrentMemory(), device.getCurrentTemperature(), device.getCurrentPower(), device.getCurrentInterfaceStatus(), device.getCurrentModemStatus(), device.getCreatedAt(), device.getUpdatedAt());
    }
}
