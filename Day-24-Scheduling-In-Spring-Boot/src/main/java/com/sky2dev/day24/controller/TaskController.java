package com.sky2dev.day24.controller;

import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.dto.TaskStatisticsDto;
import com.sky2dev.day24.entity.ArchivedRecord;
import com.sky2dev.day24.entity.ReportRecord;
import com.sky2dev.day24.entity.TaskExecutionLog;
import com.sky2dev.day24.entity.TaskExecutionStatus;
import com.sky2dev.day24.repository.TaskExecutionLogRepository;
import com.sky2dev.day24.service.AlarmEvaluationService;
import com.sky2dev.day24.service.ArchiveService;
import com.sky2dev.day24.service.RecoveryService;
import com.sky2dev.day24.service.ReportService;
import com.sky2dev.day24.service.TaskExecutionService;
import com.sky2dev.day24.service.TelemetryCollectionService;
import com.sky2dev.day24.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskExecutionLogRepository taskExecutionLogRepository;
    private final TaskExecutionService taskExecutionService;
    private final TelemetryCollectionService telemetryCollectionService;
    private final AlarmEvaluationService alarmEvaluationService;
    private final ReportService reportService;
    private final ArchiveService archiveService;
    private final RecoveryService recoveryService;

    @GetMapping("/executions")
    public ApiResponse<List<TaskExecutionLog>> getExecutions() {
        return ApiResponse.success(MarkerConstants.SCHEDULER_ACTIVE, "Task execution history fetched",
                taskExecutionLogRepository.findTop200ByOrderByStartTimeDesc());
    }

    @GetMapping("/statistics")
    public ApiResponse<TaskStatisticsDto> getStatistics() {
        long running = taskExecutionLogRepository.countByStatus(TaskExecutionStatus.RUNNING);
        long success = taskExecutionLogRepository.countByStatus(TaskExecutionStatus.SUCCESS);
        long failed = taskExecutionLogRepository.countByStatus(TaskExecutionStatus.FAILED);
        long total = taskExecutionLogRepository.count();
        return ApiResponse.success(MarkerConstants.SCHEDULER_ACTIVE, "Task statistics fetched",
                new TaskStatisticsDto(running, success, failed, total));
    }

    @PostMapping("/run/telemetry")
    public ApiResponse<Integer> runTelemetryTask() {
        List<?> output = taskExecutionService.executeTracked("Manual.telemetry", telemetryCollectionService::collectMetrics);
        return ApiResponse.success(MarkerConstants.TELEMETRY_COLLECTED, "Manual telemetry task executed", output.size());
    }

    @PostMapping("/run/alarm")
    public ApiResponse<Integer> runAlarmTask() {
        List<?> output = taskExecutionService.executeTracked("Manual.alarm", alarmEvaluationService::evaluateAlarms);
        return ApiResponse.success(MarkerConstants.ALARM_GENERATED, "Manual alarm task executed", output.size());
    }

    @PostMapping("/run/report")
    public ApiResponse<ReportRecord> runReportTask() {
        ReportRecord output = taskExecutionService.executeTracked("Manual.report", reportService::generatePerformanceReport);
        return ApiResponse.success(MarkerConstants.REPORT_GENERATED, "Manual report task executed", output);
    }

    @PostMapping("/run/archive")
    public ApiResponse<ArchivedRecord> runArchiveTask() {
        ArchivedRecord output = taskExecutionService.executeTracked("Manual.archive", archiveService::archiveOldTelemetry);
        return ApiResponse.success(MarkerConstants.DATA_ARCHIVED, "Manual archive task executed", output);
    }

    @PostMapping("/run/recovery")
    public ApiResponse<Integer> runRecoveryTask() {
        List<?> output = taskExecutionService.executeTracked("Manual.recovery", recoveryService::autoRecoverDevices);
        return ApiResponse.success(MarkerConstants.AUTO_RECOVERY_COMPLETE, "Manual recovery task executed", output.size());
    }
}
