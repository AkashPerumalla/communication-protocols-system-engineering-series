package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.DashboardSnapshotDto;
import com.sky2dev.day20.dto.ReportDto;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final DashboardService dashboardService;
    private final AlertEngine alertEngine;
    private final List<ReportDto> reports = new ArrayList<>();

    public List<ReportDto> listReports() {
        if (reports.isEmpty()) {
            reports.add(generateReport("Daily Monitoring Report"));
        }
        return reports;
    }

    public ReportDto generateReport(String reportType) {
        DashboardSnapshotDto snapshot = dashboardService.latestSnapshot();
        int activeAlerts = alertEngine.listActiveAlerts().size();
        String summary = switch (reportType) {
            case "Daily Monitoring Report" -> "Daily status: active agents=" + snapshot.activeAgents()
                    + ", avgCpu=" + round(snapshot.averageCpu()) + "%";
            case "Alert Summary Report" -> "Alert summary: active alerts=" + activeAlerts + ", critical="
                    + snapshot.criticalAlerts();
            case "Resource Utilization Report" -> "Resource utilization: CPU=" + round(snapshot.averageCpu())
                    + "%, Memory=" + round(snapshot.averageMemory()) + "%, Disk=" + round(snapshot.averageDisk()) + "%";
            default -> "Custom report generated for " + reportType;
        };

        ReportDto report = new ReportDto(reportType, summary, Instant.now());
        reports.add(report);
        return report;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
