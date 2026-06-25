package com.sky2dev.day24.service;

import com.sky2dev.day24.dto.DashboardDto;
import com.sky2dev.day24.entity.AlarmSeverity;
import com.sky2dev.day24.entity.DeviceStatus;
import com.sky2dev.day24.entity.TaskExecutionStatus;
import com.sky2dev.day24.repository.AlarmEventRepository;
import com.sky2dev.day24.repository.ArchivedRecordRepository;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.repository.NotificationEventRepository;
import com.sky2dev.day24.repository.ReportRecordRepository;
import com.sky2dev.day24.repository.TaskExecutionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DeviceRepository deviceRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final ReportRecordRepository reportRecordRepository;
    private final ArchivedRecordRepository archivedRecordRepository;
    private final TaskExecutionLogRepository taskExecutionLogRepository;

    public DashboardDto updateDashboard() {
        long totalDevices = deviceRepository.count();
        long online = deviceRepository.countByStatus(DeviceStatus.ONLINE);
        long offline = deviceRepository.countByStatus(DeviceStatus.OFFLINE);
        long warning = deviceRepository.countByStatus(DeviceStatus.WARNING);
        long activeAlarms = alarmEventRepository.countByAcknowledgedFalse();
        long critical = alarmEventRepository.countBySeverityAndAcknowledgedFalse(AlarmSeverity.CRITICAL);
        long notificationsSent = notificationEventRepository.countByStatus("SENT");
        long reportsGenerated = reportRecordRepository.count();
        long archived = archivedRecordRepository.count();
        long failed = taskExecutionLogRepository.countByStatus(TaskExecutionStatus.FAILED);

        return new DashboardDto(
                totalDevices,
                online,
                offline,
                warning,
                activeAlarms,
                critical,
                notificationsSent,
                reportsGenerated,
                archived,
                failed > 0 ? "DEGRADED" : "HEALTHY"
        );
    }
}
