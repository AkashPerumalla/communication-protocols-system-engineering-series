package com.sky2dev.day15.service;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.DeviceStatus;
import com.sky2dev.day15.entity.InterfaceStatus;
import com.sky2dev.day15.entity.MetricSource;
import com.sky2dev.day15.entity.MonitoringMetric;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class DeviceSimulatorService {

    private final AtomicLong sampleSequence = new AtomicLong();

    public MonitoringMetric simulate(Device device) {
        long sequence = sampleSequence.incrementAndGet();
        double cpuOffset = offset(device.getHostname(), sequence, 4.0);
        double memoryOffset = offset(device.getHostname() + "-memory", sequence, 3.5);
        double tempOffset = offset(device.getHostname() + "-temp", sequence, 1.7);

        double cpuUsage = clamp(device.getCpuUsage() + cpuOffset, 0.0, 100.0);
        double memoryUsage = clamp(device.getMemoryUsage() + memoryOffset, 0.0, 100.0);
        double temperature = clamp(device.getTemperature() + tempOffset, 18.0, 95.0);
        long uptime = device.getUptime() + 10L;
        DeviceStatus status = device.getStatus();
        InterfaceStatus interfaceStatus = device.getInterfaceStatus();

        if (status == DeviceStatus.ONLINE && cpuUsage > 94.0) {
            status = DeviceStatus.DEGRADED;
        }
        if (status == DeviceStatus.ONLINE && temperature > 73.0) {
            status = DeviceStatus.DEGRADED;
        }
        if (status == DeviceStatus.OFFLINE) {
            cpuUsage = 0.0;
            memoryUsage = 0.0;
            interfaceStatus = InterfaceStatus.DOWN;
        }

        return MonitoringMetric.builder()
                .device(device)
                .cpuUsage(round(cpuUsage))
                .memoryUsage(round(memoryUsage))
                .uptime(uptime)
                .status(status)
                .temperature(round(temperature))
                .interfaceStatus(interfaceStatus)
                .source(MetricSource.POLLING)
                .build();
    }

    private double offset(String seed, long sequence, double amplitude) {
        long normalized = Math.abs((seed + sequence).hashCode());
        double fraction = (normalized % 1000) / 1000.0;
        return (fraction - 0.5) * 2.0 * amplitude;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
