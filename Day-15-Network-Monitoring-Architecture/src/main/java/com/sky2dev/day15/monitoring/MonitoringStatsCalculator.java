package com.sky2dev.day15.monitoring;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.MonitoringMetric;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MonitoringStatsCalculator {

    public double averageCpu(List<MonitoringMetric> metrics) {
        return roundAverage(metrics.stream().mapToDouble(MonitoringMetric::getCpuUsage).average().orElse(0.0));
    }

    public double averageMemory(List<MonitoringMetric> metrics) {
        return roundAverage(metrics.stream().mapToDouble(MonitoringMetric::getMemoryUsage).average().orElse(0.0));
    }

    public long onlineDevices(List<Device> devices) {
        return devices.stream().filter(device -> device.getStatus() == com.sky2dev.day15.entity.DeviceStatus.ONLINE).count();
    }

    public long offlineDevices(List<Device> devices) {
        return devices.stream().filter(device -> device.getStatus() == com.sky2dev.day15.entity.DeviceStatus.OFFLINE).count();
    }

    private double roundAverage(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
