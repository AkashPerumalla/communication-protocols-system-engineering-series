package com.sky2dev.day25.service;

import com.sky2dev.day25.dto.TelemetryResponse;
import com.sky2dev.day25.repository.TelemetryRecordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRecordRepository telemetryRecordRepository;

    @Transactional(readOnly = true)
    public List<TelemetryResponse> getTelemetry() {
        return telemetryRecordRepository.findTop20ByOrderByCollectedAtDesc().stream()
                .map(record -> new TelemetryResponse(
                        record.getId(),
                        record.getDevice().getName(),
                        record.getMetricName(),
                        record.getMetricValue(),
                        record.getUnit(),
                        record.getQualityScore(),
                        record.getCollectedAt()))
                .toList();
    }
}
