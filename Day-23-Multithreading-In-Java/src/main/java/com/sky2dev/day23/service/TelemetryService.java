package com.sky2dev.day23.service;

import com.sky2dev.day23.dto.TelemetryResponse;
import com.sky2dev.day23.entity.TelemetryRecord;
import com.sky2dev.day23.repository.TelemetryRecordRepository;
import com.sky2dev.day23.simulation.TelemetrySample;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TelemetryService {

    private final TelemetryRecordRepository telemetryRecordRepository;

    public TelemetryRecord saveSample(TelemetrySample sample) {
        TelemetryRecord record = TelemetryRecord.builder()
                .deviceId(sample.deviceId())
                .cpuUsage(sample.cpuUsage())
                .memoryUsage(sample.memoryUsage())
                .temperature(sample.temperature())
                .signalStrength(sample.signalStrength())
                .timestamp(sample.timestamp())
                .build();
        return telemetryRecordRepository.save(record);
    }

    @Transactional(readOnly = true)
    public List<TelemetryResponse> getTelemetry() {
        return telemetryRecordRepository.findTop50ByOrderByTimestampDesc().stream()
                .map(TelemetryResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public long countTelemetry() {
        return telemetryRecordRepository.count();
    }
}
