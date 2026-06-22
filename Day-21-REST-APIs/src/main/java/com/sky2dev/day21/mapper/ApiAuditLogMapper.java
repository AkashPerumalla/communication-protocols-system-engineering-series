package com.sky2dev.day21.mapper;

import com.sky2dev.day21.dto.ApiAuditLogResponse;
import com.sky2dev.day21.entity.ApiAuditLog;
import org.springframework.stereotype.Component;

@Component
public class ApiAuditLogMapper {

    public ApiAuditLogResponse toResponse(ApiAuditLog auditLog) {
        return new ApiAuditLogResponse(
                auditLog.getId(),
                auditLog.getEndpoint(),
                auditLog.getMethod(),
                auditLog.getResponseCode(),
                auditLog.getExecutionTime(),
                auditLog.getTimestamp());
    }
}
