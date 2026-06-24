package com.sky2dev.day23.service;

import com.sky2dev.day23.dto.NocDashboardResponse;
import com.sky2dev.day23.repository.AlarmEventRepository;
import com.sky2dev.day23.repository.DeviceRepository;
import com.sky2dev.day23.repository.NotificationEventRepository;
import com.sky2dev.day23.repository.TelemetryRecordRepository;
import com.sky2dev.day23.util.MarkerConstants;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final DeviceRepository deviceRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final TaskExecutionLogService taskExecutionLogService;

    public NocDashboardResponse buildDashboardSnapshot() {
        Instant start = Instant.now();
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("snapshotTime", Instant.now().toString());
        metrics.put("openAlarmCount", alarmEventRepository.findByAcknowledgedFalse().size());
        metrics.put("recentTelemetryWindow", telemetryRecordRepository.findTop50ByOrderByTimestampDesc().size());

        NocDashboardResponse response = new NocDashboardResponse(
                MarkerConstants.MULTITHREADING_ACTIVE,
                deviceRepository.count(),
                alarmEventRepository.findByAcknowledgedFalse().size(),
                telemetryRecordRepository.count(),
                notificationEventRepository.count(),
                metrics
        );
        taskExecutionLogService.logSuccess("DashboardUpdateTask", start);
        return response;
    }

    public Map<String, Object> fetchDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("deviceCount", deviceRepository.count());
        metrics.put("activeAlarms", alarmEventRepository.findByAcknowledgedFalse().size());
        metrics.put("telemetryCount", telemetryRecordRepository.count());
        metrics.put("notificationCount", notificationEventRepository.count());
        return metrics;
    }
}
