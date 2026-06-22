package com.sky2dev.day21.service;

import com.sky2dev.day21.dto.DeviceMetricResponse;
import com.sky2dev.day21.entity.Device;
import com.sky2dev.day21.entity.DeviceMetric;
import com.sky2dev.day21.entity.DeviceType;
import com.sky2dev.day21.mapper.DeviceMetricMapper;
import com.sky2dev.day21.repository.DeviceMetricRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MetricService {

    private final DeviceMetricRepository deviceMetricRepository;
    private final DeviceService deviceService;
    private final DeviceMetricMapper deviceMetricMapper;

    @Transactional(readOnly = true)
    public List<DeviceMetricResponse> getMetrics(int limit) {
        return deviceMetricRepository.findAllByOrderByTimestampDesc(PageRequest.of(0, Math.min(Math.max(limit, 1), 200)))
                .stream()
                .map(deviceMetricMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DeviceMetricResponse> getMetricsByDevice(Long deviceId) {
        deviceService.findEntity(deviceId);
        return deviceMetricRepository.findByDeviceIdOrderByTimestampDesc(deviceId)
                .stream()
                .map(deviceMetricMapper::toResponse)
                .toList();
    }

    public List<DeviceMetric> generateMetrics(List<Device> devices) {
        List<DeviceMetric> metrics = new ArrayList<>();
        Instant base = Instant.parse("2026-01-21T00:00:00Z");
        for (int i = 0; i < 50; i++) {
            Device device = devices.get(i % devices.size());
            metrics.add(DeviceMetric.builder()
                    .device(device)
                    .cpuUsage(cpuFor(device.getDeviceType(), i))
                    .memoryUsage(memoryFor(device.getDeviceType(), i))
                    .temperature(temperatureFor(device.getDeviceType(), i))
                    .signalStrength(signalFor(device.getDeviceType(), i))
                    .timestamp(base.plusSeconds(i * 300L))
                    .build());
        }
        return metrics;
    }

    public void generateMetrics() {
        List<Device> devices = deviceService.getAllManagedEntities();
        if (devices.isEmpty() || deviceMetricRepository.count() > 0) {
            return;
        }
        deviceMetricRepository.saveAll(generateMetrics(devices));
    }

    @Transactional(readOnly = true)
    public double averageCpuUsage() {
        return safeAverage(deviceMetricRepository.averageCpuUsage());
    }

    @Transactional(readOnly = true)
    public double averageMemoryUsage() {
        return safeAverage(deviceMetricRepository.averageMemoryUsage());
    }

    private double safeAverage(Double value) {
        return value == null ? 0.0 : Math.round(value * 100.0) / 100.0;
    }

    private double cpuFor(DeviceType type, int index) {
        return switch (type) {
            case ROUTER -> 38 + (index % 9);
            case SWITCH -> 28 + (index % 7);
            case MODEM -> 44 + (index % 11);
            case HUB -> 53 + (index % 10);
            case BUC -> 32 + (index % 8);
            case LNB -> 21 + (index % 6);
            case IOT_GATEWAY -> 36 + (index % 12);
            case SENSOR -> 18 + (index % 5);
        };
    }

    private double memoryFor(DeviceType type, int index) {
        return switch (type) {
            case ROUTER -> 52 + (index % 12);
            case SWITCH -> 42 + (index % 8);
            case MODEM -> 58 + (index % 9);
            case HUB -> 65 + (index % 11);
            case BUC -> 40 + (index % 7);
            case LNB -> 34 + (index % 6);
            case IOT_GATEWAY -> 55 + (index % 10);
            case SENSOR -> 26 + (index % 5);
        };
    }

    private double temperatureFor(DeviceType type, int index) {
        return switch (type) {
            case ROUTER -> 40 + (index % 5);
            case SWITCH -> 37 + (index % 4);
            case MODEM -> 48 + (index % 6);
            case HUB -> 42 + (index % 5);
            case BUC -> 54 + (index % 7);
            case LNB -> 18 + (index % 3);
            case IOT_GATEWAY -> 39 + (index % 5);
            case SENSOR -> 24 + (index % 4);
        };
    }

    private double signalFor(DeviceType type, int index) {
        return switch (type) {
            case ROUTER, SWITCH, IOT_GATEWAY -> -45 + (index % 6);
            case MODEM, HUB -> -58 + (index % 7);
            case BUC -> -41 + (index % 4);
            case LNB -> -33 + (index % 3);
            case SENSOR -> -62 + (index % 5);
        };
    }
}
