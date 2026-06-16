package com.sky2dev.day15.scheduler;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.repository.DeviceRepository;
import com.sky2dev.day15.simulator.TrapGenerator;
import com.sky2dev.day15.simulator.TrapProcessor;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrapScheduler {

    private static final Logger log = LoggerFactory.getLogger(TrapScheduler.class);

    private final DeviceRepository deviceRepository;
    private final TrapGenerator trapGenerator;
    private final TrapProcessor trapProcessor;

    @Scheduled(fixedDelay = 30000, initialDelay = 30000)
    public void generateTrap() {
        List<Device> devices = deviceRepository.findAll().stream()
                .sorted(Comparator.comparing(Device::getHostname))
                .toList();
        var trapEvent = trapGenerator.generateRandomTrap(devices);
        if (trapEvent != null) {
            log.debug("Generated trap {} for {}", trapEvent.getTrapType(), trapEvent.getDevice().getHostname());
            trapProcessor.process(trapEvent);
        }
    }
}
