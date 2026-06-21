package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.ApiResponse;
import com.sky2dev.day20.dto.ReportDto;
import com.sky2dev.day20.dto.ReportGenerateRequest;
import com.sky2dev.day20.service.ReportingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping
    public ApiResponse<List<ReportDto>> listReports() {
        return new ApiResponse<>("REPORT GENERATED", "Reports listed", reportingService.listReports());
    }

    @PostMapping("/generate")
    public ApiResponse<ReportDto> generate(@Valid @RequestBody ReportGenerateRequest request) {
        return new ApiResponse<>("REPORT GENERATED", "Report generation completed", reportingService.generateReport(request.reportType()));
    }
}
