package com.sky2dev.day15.service;

import com.sky2dev.day15.alert.AlertRuleEngine;
import com.sky2dev.day15.alert.AlertService;
import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.DeviceStatus;
import com.sky2dev.day15.entity.MonitoringMetric;
import com.sky2dev.day15.monitoring.MonitoringStatsCalculator;
import com.sky2dev.day15.repository.DeviceRepository;
import com.sky2dev.day15.repository.MonitoringMetricRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DevicePollingService {

    private static final Logger log = LoggerFactory.getLogger(DevicePollingService.class);

    private final DeviceRepository deviceRepository;
    private final MonitoringMetricRepository monitoringMetricRepository;
    private final DeviceSimulatorService deviceSimulatorService;
    private final TelecomMonitoringService telecomMonitoringService;
    private final SatComMonitoringService satComMonitoringService;
    private final AlertRuleEngine alertRuleEngine;
    private final AlertService alertService;
    private final MonitoringStatsCalculator monitoringStatsCalculator;
    private final DashboardAggregator dashboardAggregator;

    @Transactional
    public List<MonitoringMetric> pollAllDevices() {
        List<Device> devices = deviceRepository.findAll().stream()
                .sorted(Comparator.comparing(Device::getHostname))
                .toList();
        List<MonitoringMetric> metrics = new ArrayList<>();
        for (Device device : devices) {
            MonitoringMetric metric = deviceSimulatorService.simulate(device);
            telecomMonitoringService.enrich(device, metric);
            satComMonitoringService.enrich(device, metric);
            metric.setCapturedAt(Instant.now());
            metric.setDevice(device);
            MonitoringMetric savedMetric = monitoringMetricRepository.save(metric);
            metrics.add(savedMetric);

            device.setCpuUsage(savedMetric.getCpuUsage());
            device.setMemoryUsage(savedMetric.getMemoryUsage());
            device.setUptime(savedMetric.getUptime());
            device.setStatus(savedMetric.getStatus());
            device.setTemperature(savedMetric.getTemperature());
            device.setInterfaceStatus(savedMetric.getInterfaceStatus());
            device.setLastUpdated(savedMetric.getCapturedAt());
            deviceRepository.save(device);

            alertService.reconcile(device, "POLLING", alertRuleEngine.evaluate(savedMetric));
        }
        dashboardAggregator.captureSnapshot();
        log.debug("Polled {} devices and stored {} metrics", devices.size(), metrics.size());
        return metrics;
    }

    @Transactional(readOnly = true)
    public List<MonitoringMetric> findRecentMetrics() {
        return monitoringMetricRepository.findTop100ByOrderByCapturedAtDesc();
    }

    @Transactional(readOnly = true)
    public MonitoringMetric findLatestMetric(Device device) {
        return monitoringMetricRepository.findTopByDeviceOrderByCapturedAtDesc(device)
                .orElseGet(() -> MonitoringMetric.builder()
                        .device(device)
                        .capturedAt(Instant.now())
                        .cpuUsage(device.getCpuUsage())
                        .memoryUsage(device.getMemoryUsage())
                        .uptime(device.getUptime())
                        .status(device.getStatus())
                        .temperature(device.getTemperature())
                        .interfaceStatus(device.getInterfaceStatus())
                        .source(com.sky2dev.day15.entity.MetricSource.POLLING)
                        .build());
    }
}
