package com.sky2dev.day21.service;

import com.sky2dev.day21.dto.ApiAuditLogResponse;
import com.sky2dev.day21.entity.ApiAuditLog;
import com.sky2dev.day21.mapper.ApiAuditLogMapper;
import com.sky2dev.day21.repository.ApiAuditLogRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final ApiAuditLogRepository apiAuditLogRepository;
    private final ApiAuditLogMapper apiAuditLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logApiCall(String endpoint, String method, int responseCode, long executionTime) {
        try {
            apiAuditLogRepository.save(ApiAuditLog.builder()
                    .endpoint(endpoint)
                    .method(method)
                    .responseCode(responseCode)
                    .executionTime(executionTime)
                    .timestamp(Instant.now())
                    .build());
        } catch (Exception ex) {
            log.warn("Failed to persist audit log for {} {}", method, endpoint, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ApiAuditLogResponse> getAuditLogs(int limit) {
        return apiAuditLogRepository.findAllByOrderByTimestampDesc(PageRequest.of(0, Math.min(Math.max(limit, 1), 200)))
                .stream()
                .map(apiAuditLogMapper::toResponse)
                .toList();
    }
}
