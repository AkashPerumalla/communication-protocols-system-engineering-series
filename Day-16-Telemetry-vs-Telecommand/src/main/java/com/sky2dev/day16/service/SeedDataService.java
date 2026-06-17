package com.sky2dev.day16.service;

import com.sky2dev.day16.model.DeviceState;
import com.sky2dev.day16.model.DeviceType;
import com.sky2dev.day16.model.ManagedDevice;
import com.sky2dev.day16.repository.ManagedDeviceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeedDataService implements ApplicationRunner {

    private final ManagedDeviceRepository managedDeviceRepository;
    private final TelemetryGeneratorService telemetryGeneratorService;

    @Override
    public void run(ApplicationArguments args) {
        seed();
    }

    @Transactional
    public void seed() {
        if (managedDeviceRepository.count() > 0) {
            return;
        }

        List<ManagedDevice> devices = new ArrayList<>();
        devices.add(buildDevice("Router-01", DeviceType.ROUTER_01, true, true, true, false, 1800, 27500, DeviceState.ACTIVE));
        devices.add(buildDevice("Switch-01", DeviceType.SWITCH_01, true, false, true, false, 0, 0, DeviceState.ACTIVE));
        devices.add(buildDevice("Hub-01", DeviceType.HUB_01, true, false, true, false, 0, 0, DeviceState.ACTIVE));
        devices.add(buildDevice("Modem-01", DeviceType.MODEM_01, true, true, false, false, 12500, 45000, DeviceState.DEGRADED));
        devices.add(buildDevice("BUC-01", DeviceType.BUC_01, false, true, true, false, 14250, 36000, DeviceState.ACTIVE));
        devices.add(buildDevice("LNB-01", DeviceType.LNB_01, false, true, true, false, 10950, 27500, DeviceState.ACTIVE));
        devices.add(buildDevice("PLC-01", DeviceType.PLC_01, true, false, true, false, 0, 0, DeviceState.ACTIVE));
        devices.add(buildDevice("Sensor-01", DeviceType.SENSOR_01, false, false, true, false, 0, 0, DeviceState.ACTIVE));

        managedDeviceRepository.saveAll(devices);
        devices.forEach(telemetryGeneratorService::capture);
    }

    private ManagedDevice buildDevice(String deviceCode, DeviceType type, boolean interfaceEnabled, boolean carrierEnabled, boolean modemHealthy, boolean backupLinkActive, long frequency, long symbolRate, DeviceState state) {
        return ManagedDevice.builder().deviceCode(deviceCode).displayName(type.getDisplayName()).deviceType(type).state(state).interfaceEnabled(interfaceEnabled).carrierEnabled(carrierEnabled).modemHealthy(modemHealthy).backupLinkActive(backupLinkActive).currentFrequencyMHz(frequency).currentSymbolRateKsps(symbolRate).currentCpu(0.0d).currentMemory(0.0d).currentTemperature(0.0d).currentPower(0.0d).currentInterfaceStatus(interfaceEnabled ? "UP" : "DOWN").currentModemStatus(modemHealthy ? "READY" : "FAULT").build();
    }
}
