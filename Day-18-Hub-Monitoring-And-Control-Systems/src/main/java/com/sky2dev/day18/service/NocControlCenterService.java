package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.DashboardDto;
import com.sky2dev.day18.dto.NocOverviewDto;
import com.sky2dev.day18.model.AlarmStatus;
import com.sky2dev.day18.model.DeliveryStatus;
import com.sky2dev.day18.model.ExecutionStatus;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.ControlCommandRepository;
import com.sky2dev.day18.repository.NotificationEventRepository;
import com.sky2dev.day18.repository.ReportRepository;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NocControlCenterService {

    private final DashboardService dashboardService;
    private final AlarmRepository alarmRepository;
    private final ControlCommandRepository controlCommandRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public NocOverviewDto getNocOverview() {
        DashboardDto dashboard = dashboardService.getDashboard();
        Instant since = Instant.now().minusSeconds(24 * 60 * 60);

        long activeAlarms = alarmRepository.countByStatusNot(AlarmStatus.CLOSED);
        long controlsToday = controlCommandRepository.countByExecutionTimeAfter(since);
        long notificationsToday = notificationEventRepository.countBySentTimeAfter(since);
        long reportsAvailable = reportRepository.count();

        int health = calculateHealthScore(
                dashboard.deviceAvailability(),
                activeAlarms,
                controlCommandRepository.countByExecutionStatus(ExecutionStatus.FAILED),
                notificationEventRepository.countByDeliveryStatus(DeliveryStatus.FAILED)
        );

        return new NocOverviewDto(
                dashboard,
                activeAlarms,
                controlsToday,
                notificationsToday,
                reportsAvailable,
                dashboard.deviceAvailability(),
                health
        );
    }

    private int calculateHealthScore(
            BigDecimal availability,
            long activeAlarms,
            long failedControls,
            long failedNotifications
    ) {
        int base = availability.intValue();
        int penalty = (int) Math.min(40, activeAlarms * 2 + failedControls * 3 + failedNotifications * 2);
        return Math.max(0, Math.min(100, base - penalty));
    }
}
