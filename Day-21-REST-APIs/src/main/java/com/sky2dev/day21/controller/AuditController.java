package com.sky2dev.day21.controller;

import com.sky2dev.day21.dto.ApiAuditLogResponse;
import com.sky2dev.day21.dto.ApiResponse;
import com.sky2dev.day21.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@Tag(name = "Audit", description = "API audit trail and compliance endpoints")
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    @Operation(summary = "Get recent audit log entries")
    public ApiResponse<List<ApiAuditLogResponse>> getAuditLogs(@RequestParam(defaultValue = "50") int limit) {
        return ApiResponse.success("AUDIT LOG GENERATED", "API audit trail generated", auditService.getAuditLogs(limit));
    }
}
