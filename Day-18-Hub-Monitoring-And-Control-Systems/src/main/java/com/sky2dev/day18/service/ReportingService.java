package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.ReportDto;
import com.sky2dev.day18.model.Report;
import com.sky2dev.day18.model.ReportType;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import com.sky2dev.day18.repository.ReportRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final ReportRepository reportRepository;
    private final HubDeviceRepository hubDeviceRepository;
    private final AlarmRepository alarmRepository;

    @Transactional(readOnly = true)
    public List<ReportDto> getReports() {
        return reportRepository.findTop10ByOrderByGeneratedTimeDesc().stream()
                .map(ReportDto::from)
                .toList();
    }

    @Transactional
    public List<ReportDto> generateReports(String generatedBy) {
        long totalDevices = hubDeviceRepository.count();
        long activeAlarms = alarmRepository.countByStatusNot(com.sky2dev.day18.model.AlarmStatus.CLOSED);

        List<Report> reports = new ArrayList<>();
        reports.add(report(ReportType.DAILY, generatedBy,
                "Daily hub report: devices=" + totalDevices + ", activeAlarms=" + activeAlarms));
        reports.add(report(ReportType.WEEKLY, generatedBy,
                "Weekly hub report: recurring alerts reviewed for NOC shift handover"));
        reports.add(report(ReportType.ALARM_SUMMARY, generatedBy,
                "Alarm summary generated for WARNING/MAJOR/CRITICAL distribution"));
        reports.add(report(ReportType.AVAILABILITY, generatedBy,
                "Availability report generated for hub devices and control plane"));
        reports.add(report(ReportType.PERFORMANCE, generatedBy,
                "Performance report generated with top problem devices by health score"));

        return reportRepository.saveAll(reports).stream().map(ReportDto::from).toList();
    }

    private Report report(ReportType type, String generatedBy, String summary) {
        return Report.builder()
                .reportType(type)
                .generatedBy(generatedBy)
                .generatedTime(Instant.now())
                .reportSummary(summary)
                .build();
    }
}
