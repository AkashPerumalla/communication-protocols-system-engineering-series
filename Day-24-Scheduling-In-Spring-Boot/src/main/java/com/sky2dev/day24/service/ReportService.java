package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.ReportRecord;
import com.sky2dev.day24.entity.ReportType;
import com.sky2dev.day24.repository.AlarmEventRepository;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.repository.ReportRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRecordRepository reportRecordRepository;
    private final DeviceRepository deviceRepository;
    private final AlarmEventRepository alarmEventRepository;

    @Transactional
    public ReportRecord generateDailyReport() {
        long totalDevices = deviceRepository.count();
        long activeAlarms = alarmEventRepository.countByAcknowledgedFalse();
        return reportRecordRepository.save(ReportRecord.builder()
                .reportType(ReportType.DAILY)
                .generatedAt(Instant.now())
                .summary("Daily ops summary: totalDevices=" + totalDevices + ", activeAlarms=" + activeAlarms)
                .build());
    }

    @Transactional
    public ReportRecord generatePerformanceReport() {
        long critical = alarmEventRepository.countBySeverityAndAcknowledgedFalse(com.sky2dev.day24.entity.AlarmSeverity.CRITICAL);
        return reportRecordRepository.save(ReportRecord.builder()
                .reportType(ReportType.PERFORMANCE)
                .generatedAt(Instant.now())
                .summary("Performance report: criticalAlarms=" + critical + ", generatedAt=" + Instant.now())
                .build());
    }
}
