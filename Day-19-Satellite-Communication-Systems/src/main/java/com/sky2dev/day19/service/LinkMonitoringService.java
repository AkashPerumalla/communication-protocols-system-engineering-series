package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.LinkMetricDto;
import com.sky2dev.day19.dto.MonitoringFindingDto;
import com.sky2dev.day19.model.LinkMetric;
import com.sky2dev.day19.model.LockStatus;
import com.sky2dev.day19.repository.LinkMetricRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkMonitoringService {

    private final LinkMetricRepository linkMetricRepository;

    public List<LinkMetricDto> listMetrics() {
        return linkMetricRepository.findAll().stream().map(LinkMetricDto::from).toList();
    }

    public List<MonitoringFindingDto> analyzeMetrics() {
        List<LinkMetric> metrics = linkMetricRepository.findAll();
        List<MonitoringFindingDto> findings = new ArrayList<>();

        for (LinkMetric metric : metrics) {
            String linkName = metric.getLink().getLinkName();
            if (metric.getCnDb() < 9.0) {
                findings.add(new MonitoringFindingDto(linkName, "C/N DEGRADATION", "Carrier to Noise below nominal threshold", "CRITICAL"));
            }
            if (metric.getBer() > 1e-4) {
                findings.add(new MonitoringFindingDto(linkName, "BER INCREASE", "Bit error rate is above operational limit", "MAJOR"));
            }
            if (metric.getEbNo() < 6.0) {
                findings.add(new MonitoringFindingDto(linkName, "EB/NO DEGRADATION", "Energy per bit to noise ratio is low", "MAJOR"));
            }
            if (metric.getRxPowerDbm() < -72.0) {
                findings.add(new MonitoringFindingDto(linkName, "RX POWER DROP", "Receive power indicates attenuation or misalignment", "WARNING"));
            }
            if (metric.getLockStatus() == LockStatus.LOST) {
                findings.add(new MonitoringFindingDto(linkName, "LOSS OF LOCK", "Demodulator lock lost on downlink carrier", "CRITICAL"));
            }
        }

        return findings;
    }
}
