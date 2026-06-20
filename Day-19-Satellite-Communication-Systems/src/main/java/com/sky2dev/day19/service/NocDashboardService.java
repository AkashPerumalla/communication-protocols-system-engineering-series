package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.NocDashboardDto;
import com.sky2dev.day19.model.AlarmSeverity;
import com.sky2dev.day19.model.AlarmStatus;
import com.sky2dev.day19.model.LinkStatus;
import com.sky2dev.day19.repository.LinkMetricRepository;
import com.sky2dev.day19.repository.SatComAlarmRepository;
import com.sky2dev.day19.repository.SatelliteLinkRepository;
import java.util.DoubleSummaryStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NocDashboardService {

    private final SatelliteLinkRepository linkRepository;
    private final SatComAlarmRepository alarmRepository;
    private final LinkMetricRepository metricRepository;

    public NocDashboardDto buildDashboard() {
        long totalLinks = linkRepository.count();
        long activeLinks = linkRepository.findAll().stream()
                .filter(link -> link.getStatus() == LinkStatus.UP || link.getStatus() == LinkStatus.DEGRADED)
                .count();

        long activeAlarms = alarmRepository.findByStatus(AlarmStatus.OPEN).size();
        long criticalAlarms = alarmRepository.findBySeverity(AlarmSeverity.CRITICAL).stream()
                .filter(a -> a.getStatus() == AlarmStatus.OPEN)
                .count();

        var metrics = metricRepository.findAll();
        DoubleSummaryStatistics cnStats = metrics.stream().mapToDouble(m -> m.getCnDb() == null ? 0.0 : m.getCnDb()).summaryStatistics();
        DoubleSummaryStatistics ebNoStats = metrics.stream().mapToDouble(m -> m.getEbNo() == null ? 0.0 : m.getEbNo()).summaryStatistics();
        DoubleSummaryStatistics berStats = metrics.stream().mapToDouble(m -> m.getBer() == null ? 0.0 : m.getBer()).summaryStatistics();

        double availability = totalLinks == 0 ? 0.0 : (activeLinks * 100.0) / totalLinks;

        return new NocDashboardDto(
                activeLinks,
                activeAlarms,
                criticalAlarms,
                round(availability),
                round(cnStats.getAverage()),
                round(ebNoStats.getAverage()),
                round(berStats.getAverage())
        );
    }

    private double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
