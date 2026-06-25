package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.Device;
import com.sky2dev.day24.entity.DeviceStatus;
import com.sky2dev.day24.entity.TelemetryRecord;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.repository.TelemetryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceHealthService {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;

    @Transactional
    public List<Device> evaluateDeviceHealth() {
        List<Device> devices = deviceRepository.findAll();
        Instant now = Instant.now();

        for (Device device : devices) {
            List<TelemetryRecord> recent = telemetryRecordRepository.findTop20ByDeviceIdOrderByTimestampDesc(device.getId());
            if (recent.isEmpty()) {
                device.setStatus(DeviceStatus.OFFLINE);
                continue;
            }

            TelemetryRecord latest = recent.get(0);
            boolean stale = Duration.between(device.getLastSeen(), now).toSeconds() > 120;
            if (stale) {
                device.setStatus(DeviceStatus.OFFLINE);
            } else if (latest.getCpuUsage() > 80 || latest.getMemoryUsage() > 85 || latest.getTemperature() > 70 || latest.getSignalStrength() < -80) {
                device.setStatus(DeviceStatus.WARNING);
            } else {
                device.setStatus(DeviceStatus.ONLINE);
            }
        }

        return deviceRepository.saveAll(devices);
    }
}
