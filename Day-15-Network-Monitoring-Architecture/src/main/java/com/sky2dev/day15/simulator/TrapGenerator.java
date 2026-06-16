package com.sky2dev.day15.simulator;

import com.sky2dev.day15.entity.AlertSeverity;
import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.TrapEvent;
import com.sky2dev.day15.entity.TrapType;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class TrapGenerator {

    private final AtomicLong sequence = new AtomicLong();

    public TrapEvent generateRandomTrap(List<Device> devices) {
        if (devices.isEmpty()) {
            return null;
        }
        long currentSequence = sequence.incrementAndGet();
        Device device = devices.get((int) (currentSequence % devices.size()));
        TrapType trapType = switch ((int) (currentSequence % 5)) {
            case 0 -> TrapType.INTERFACE_DOWN;
            case 1 -> TrapType.HIGH_TEMPERATURE;
            case 2 -> TrapType.DEVICE_RESTART;
            case 3 -> TrapType.HIGH_CPU;
            default -> TrapType.HIGH_MEMORY;
        };
        return buildTrap(device, trapType, switch (trapType) {
            case INTERFACE_DOWN, HIGH_TEMPERATURE, HIGH_CPU, HIGH_MEMORY -> AlertSeverity.WARNING;
            case DEVICE_RESTART -> AlertSeverity.INFO;
        }, "Generated trap " + trapType + " for " + device.getHostname());
    }

    public TrapEvent generateBootstrapTrap(List<Device> devices) {
        Device device = devices.stream()
                .sorted(Comparator.comparing(Device::getHostname))
                .findFirst()
                .orElse(null);
        if (device == null) {
            return null;
        }
        return buildTrap(device, TrapType.DEVICE_RESTART, AlertSeverity.INFO, device.getHostname() + " restarted during bootstrap");
    }

    private TrapEvent buildTrap(Device device, TrapType trapType, AlertSeverity severity, String message) {
        return TrapEvent.builder()
                .device(device)
                .trapType(trapType)
                .severity(severity)
                .message(message)
                .triggeredAt(Instant.now())
                .processed(false)
                .build();
    }
}
