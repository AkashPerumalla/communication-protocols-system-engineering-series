package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.ReportDto;
import com.sky2dev.day18.service.ReportingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportingService reportingService;

    @GetMapping
    public ApiResponse<List<ReportDto>> reports() {
        List<ReportDto> generated = reportingService.generateReports("noc-analytics-service");
        return new ApiResponse<>(
                "REPORT GENERATED",
                "Daily, weekly, alarm summary, availability, and performance reports generated",
                generated
        );
    }
}
