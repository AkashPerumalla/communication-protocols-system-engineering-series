package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.TroubleTicketDto;
import com.sky2dev.day19.dto.TroubleshootingAnalyzeRequest;
import com.sky2dev.day19.service.TroubleshootingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/troubleshooting")
@RequiredArgsConstructor
public class TroubleshootingController {

    private final TroubleshootingService troubleshootingService;

    @GetMapping
    public ApiResponse<List<TroubleTicketDto>> list() {
        return new ApiResponse<>("ROOT CAUSE IDENTIFIED", "Trouble tickets listed", troubleshootingService.listTickets());
    }

    @PostMapping("/analyze")
    public ApiResponse<TroubleTicketDto> analyze(@Valid @RequestBody TroubleshootingAnalyzeRequest request) {
        return new ApiResponse<>("ROOT CAUSE IDENTIFIED", "Troubleshooting analysis completed", troubleshootingService.analyze(request));
    }
}
