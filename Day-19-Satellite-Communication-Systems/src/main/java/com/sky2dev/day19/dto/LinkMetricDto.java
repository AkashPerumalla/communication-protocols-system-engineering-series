package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.LinkMetric;
import com.sky2dev.day19.model.LockStatus;
import com.sky2dev.day19.model.ModulationType;
import java.time.Instant;

public record LinkMetricDto(
        Long id,
        Long linkId,
        String linkName,
        Instant timestamp,
        Double cnDb,
        Double ebNo,
        Double ber,
        Double rxPowerDbm,
        Double txPowerDbm,
        LockStatus lockStatus,
        ModulationType modulation,
        Double symbolRate,
        Double throughputMbps,
        Double latencyMs
) {
    public static LinkMetricDto from(LinkMetric metric) {
        return new LinkMetricDto(
                metric.getId(),
                metric.getLink().getId(),
                metric.getLink().getLinkName(),
                metric.getTimestamp(),
                metric.getCnDb(),
                metric.getEbNo(),
                metric.getBer(),
                metric.getRxPowerDbm(),
                metric.getTxPowerDbm(),
                metric.getLockStatus(),
                metric.getModulation(),
                metric.getSymbolRate(),
                metric.getThroughputMbps(),
                metric.getLatencyMs()
        );
    }
}
