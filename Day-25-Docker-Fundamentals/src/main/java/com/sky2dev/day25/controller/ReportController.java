package com.sky2dev.day25.controller;

import com.sky2dev.day25.dto.ApiResponse;
import com.sky2dev.day25.dto.ReportResponse;
import com.sky2dev.day25.service.ReportService;
import com.sky2dev.day25.util.MarkerConstants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getReports() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.REPORT_GENERATED,
                "Reports fetched successfully",
                reportService.getReports()));
    }
}
