package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.Device;
import com.sky2dev.day24.entity.TelemetryRecord;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.repository.TelemetryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class TelemetryCollectionService {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AtomicLong tick = new AtomicLong(0);

    @Transactional
    public List<TelemetryRecord> collectMetrics() {
        long cycle = tick.incrementAndGet();
        Instant now = Instant.now();

        List<Device> devices = deviceRepository.findAll();
        List<TelemetryRecord> records = new ArrayList<>();

        for (Device device : devices) {
            TelemetryRecord record = buildDeterministicRecord(device, cycle, now);
            records.add(record);
            device.setLastSeen(now);
        }

        deviceRepository.saveAll(devices);
        return telemetryRecordRepository.saveAll(records);
    }

    private TelemetryRecord buildDeterministicRecord(Device device, long cycle, Instant now) {
        int profile = Math.floorMod((int) (device.getId() + cycle), 3);

        int cpu = profile == 0 ? 35 : profile == 1 ? 75 : 95;
        int memory = profile == 0 ? 45 : profile == 1 ? 80 : 98;
        int temperature = profile == 0 ? 40 : profile == 1 ? 65 : 85;
        int signal = profile == 0 ? -55 : profile == 1 ? -75 : -90;

        return TelemetryRecord.builder()
                .deviceId(device.getId())
                .cpuUsage(cpu)
                .memoryUsage(memory)
                .temperature(temperature)
                .signalStrength(signal)
                .timestamp(now)
                .build();
    }
}
