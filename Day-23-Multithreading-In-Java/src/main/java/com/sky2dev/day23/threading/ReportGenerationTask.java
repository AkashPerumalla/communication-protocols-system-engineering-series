package com.sky2dev.day23.threading;

import com.sky2dev.day23.service.ReportService;

public class ReportGenerationTask implements Runnable {

    private final ReportService reportService;

    public ReportGenerationTask(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public void run() {
        reportService.generatePeriodicReport();
    }
}
