package com.sky2dev.day26.service;

import com.sky2dev.day26.dto.TelemetryResponse;
import com.sky2dev.day26.entity.Device;
import com.sky2dev.day26.entity.TelemetryRecord;
import com.sky2dev.day26.mapper.TelemetryMapper;
import com.sky2dev.day26.repository.DeviceRepository;
import com.sky2dev.day26.repository.TelemetryRecordRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRecordRepository telemetryRecordRepository;
    private final DeviceRepository deviceRepository;
    private final TelemetryMapper telemetryMapper;

    @Transactional(readOnly = true)
    public List<TelemetryResponse> getTelemetry() {
        return telemetryRecordRepository.findTop100ByOrderByCollectedAtDesc().stream()
                .map(telemetryMapper::toResponse)
                .toList();
    }

    @Transactional
    public void refreshTelemetryWindow() {
        List<Device> devices = deviceRepository.findAllByOrderByNameAsc();
        long existing = telemetryRecordRepository.count();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        for (int index = 0; index < devices.size(); index++) {
            Device device = devices.get(index);
            double nextValue = 35.0 + ((existing + index) % 30) * 1.5;
            telemetryRecordRepository.save(TelemetryRecord.builder()
                    .device(device)
                    .metricName(index % 2 == 0 ? "latency_ms" : "snr_db")
                    .metricValue(nextValue)
                    .unit(index % 2 == 0 ? "ms" : "dB")
                    .qualityScore((int) (92 - ((existing + index) % 15)))
                    .ingestionSource("platform-scheduler")
                    .collectedAt(now.plusSeconds(index * 15L))
                    .build());
        }
    }
}
