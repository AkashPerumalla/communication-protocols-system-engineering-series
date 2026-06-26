package com.sky2dev.day25.service;

import com.sky2dev.day25.dto.ReportResponse;
import com.sky2dev.day25.repository.ReportRecordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRecordRepository reportRecordRepository;

    @Transactional(readOnly = true)
    public List<ReportResponse> getReports() {
        return reportRecordRepository.findTop20ByOrderByGeneratedAtDesc().stream()
                .map(report -> new ReportResponse(
                        report.getId(),
                        report.getReportName(),
                        report.getReportType(),
                        report.getPeriodStart(),
                        report.getPeriodEnd(),
                        report.getGeneratedAt(),
                        report.getSummary(),
                        report.getGeneratedBy()))
                .toList();
    }
}
