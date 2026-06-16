package com.sky2dev.day15.service;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.repository.DeviceRepository;
import com.sky2dev.day15.simulator.TrapGenerator;
import com.sky2dev.day15.simulator.TrapProcessor;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartupBootstrapService {

    private final DeviceRepository deviceRepository;
    private final DevicePollingService devicePollingService;
    private final TrapGenerator trapGenerator;
    private final TrapProcessor trapProcessor;

    @EventListener(ApplicationReadyEvent.class)
    public void bootstrapMonitoringData() {
        devicePollingService.pollAllDevices();
        List<Device> devices = deviceRepository.findAll().stream()
                .sorted(Comparator.comparing(Device::getHostname))
                .toList();
        var bootstrapTrap = trapGenerator.generateBootstrapTrap(devices);
        if (bootstrapTrap != null) {
            trapProcessor.processSynchronously(bootstrapTrap);
        }
    }
}
