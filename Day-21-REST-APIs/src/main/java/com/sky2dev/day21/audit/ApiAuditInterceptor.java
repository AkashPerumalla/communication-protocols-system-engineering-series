package com.sky2dev.day21.audit;

import com.sky2dev.day21.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ApiAuditInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "apiAuditStartTime";

    private final AuditService auditService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!request.getRequestURI().startsWith("/api")) {
            return;
        }
        Object startTime = request.getAttribute(START_TIME);
        long executionTime = startTime instanceof Long value ? System.currentTimeMillis() - value : 0L;
        auditService.logApiCall(request.getRequestURI(), request.getMethod(), response.getStatus(), executionTime);
    }
}
