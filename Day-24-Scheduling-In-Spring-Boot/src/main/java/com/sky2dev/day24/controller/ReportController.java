package com.sky2dev.day24.controller;

import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.entity.ReportRecord;
import com.sky2dev.day24.repository.ReportRecordRepository;
import com.sky2dev.day24.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRecordRepository reportRecordRepository;

    @GetMapping
    public ApiResponse<List<ReportRecord>> getReports() {
        return ApiResponse.success(MarkerConstants.REPORT_GENERATED, "Reports fetched", reportRecordRepository.findAll());
    }
}
