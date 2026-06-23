package com.sky2dev.day22.service;

import com.sky2dev.day22.dto.TelemetryResponse;
import com.sky2dev.day22.entity.Device;
import com.sky2dev.day22.entity.TelemetryMetric;
import com.sky2dev.day22.repository.DeviceRepository;
import com.sky2dev.day22.repository.TelemetryMetricRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryMetricRepository telemetryMetricRepository;
    private final DeviceRepository deviceRepository;
    private final AtomicLong tickCounter = new AtomicLong();

    public List<TelemetryResponse> getTelemetry() {
        return telemetryMetricRepository.findTop200ByOrderByTimestampDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TelemetryResponse> generateTelemetryBatch() {
        List<Device> devices = deviceRepository.findAll();
        long tick = tickCounter.incrementAndGet();
        List<TelemetryMetric> generated = new ArrayList<>();

        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            double cpu = bounded(30 + ((tick + i * 7) % 70), 10, 99);
            double memory = bounded(35 + ((tick * 2 + i * 5) % 62), 15, 99);
            double temperature = bounded(35 + ((tick + i * 3) % 50), 25, 95);
            double signal = bounded(-95 + ((tick + i * 4) % 45), -100, -45);

            TelemetryMetric metric = TelemetryMetric.builder()
                    .deviceId(device.getId())
                    .cpuUsage(round(cpu))
                    .memoryUsage(round(memory))
                    .temperature(round(temperature))
                    .signalStrength(round(signal))
                    .timestamp(Instant.now())
                    .build();
            generated.add(metric);
        }

        return telemetryMetricRepository.saveAll(generated).stream().map(this::toResponse).toList();
    }

    private double bounded(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private TelemetryResponse toResponse(TelemetryMetric metric) {
        return new TelemetryResponse(metric.getId(), metric.getDeviceId(), metric.getCpuUsage(), metric.getMemoryUsage(),
                metric.getTemperature(), metric.getSignalStrength(), metric.getTimestamp());
    }
}
