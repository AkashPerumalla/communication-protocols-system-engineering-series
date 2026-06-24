package com.sky2dev.day23.service;

import com.sky2dev.day23.dto.DeviceResponse;
import com.sky2dev.day23.entity.Device;
import com.sky2dev.day23.entity.DeviceStatus;
import com.sky2dev.day23.repository.DeviceRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final TaskExecutionLogService taskExecutionLogService;

    @Transactional(readOnly = true)
    public List<DeviceResponse> getDevices() {
        return deviceRepository.findAll().stream().map(DeviceResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public long countDevices() {
        return deviceRepository.count();
    }

    public void runHealthCheckSweep() {
        Instant start = Instant.now();
        try {
            List<Device> devices = deviceRepository.findAll();
            for (Device device : devices) {
                DeviceStatus status = (device.getId() % 4 == 0) ? DeviceStatus.DEGRADED : DeviceStatus.ONLINE;
                if (device.getId() % 9 == 0) {
                    status = DeviceStatus.MAINTENANCE;
                }
                device.setStatus(status);
            }
            deviceRepository.saveAll(devices);
            taskExecutionLogService.logSuccess("DeviceHealthTask", start);
        } catch (RuntimeException ex) {
            taskExecutionLogService.logFailure("DeviceHealthTask", start);
            throw ex;
        }
    }
}
