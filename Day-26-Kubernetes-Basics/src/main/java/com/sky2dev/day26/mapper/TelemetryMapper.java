package com.sky2dev.day26.mapper;

import com.sky2dev.day26.dto.TelemetryResponse;
import com.sky2dev.day26.entity.TelemetryRecord;
import org.springframework.stereotype.Component;

@Component
public class TelemetryMapper {

    public TelemetryResponse toResponse(TelemetryRecord record) {
        return new TelemetryResponse(
                record.getId(),
                record.getDevice().getName(),
                record.getMetricName(),
                record.getMetricValue(),
                record.getUnit(),
                record.getQualityScore(),
                record.getIngestionSource(),
                record.getCollectedAt());
    }
}
