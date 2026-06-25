package com.sky2dev.day24;

import com.sky2dev.day24.controller.PlatformController;
import com.sky2dev.day24.controller.TaskController;
import com.sky2dev.day24.dto.ApiResponse;
import com.sky2dev.day24.entity.ArchivedRecord;
import com.sky2dev.day24.entity.Device;
import com.sky2dev.day24.entity.DeviceStatus;
import com.sky2dev.day24.entity.ReportRecord;
import com.sky2dev.day24.entity.TelemetryRecord;
import com.sky2dev.day24.repository.AlarmEventRepository;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.repository.NotificationEventRepository;
import com.sky2dev.day24.repository.ReportRecordRepository;
import com.sky2dev.day24.repository.TaskExecutionLogRepository;
import com.sky2dev.day24.repository.TelemetryRecordRepository;
import com.sky2dev.day24.scheduler.AlarmScheduler;
import com.sky2dev.day24.scheduler.ArchiveScheduler;
import com.sky2dev.day24.scheduler.DashboardScheduler;
import com.sky2dev.day24.scheduler.HealthCheckScheduler;
import com.sky2dev.day24.scheduler.MaintenanceScheduler;
import com.sky2dev.day24.scheduler.NotificationScheduler;
import com.sky2dev.day24.scheduler.RecoveryScheduler;
import com.sky2dev.day24.scheduler.ReportScheduler;
import com.sky2dev.day24.scheduler.TelemetryScheduler;
import com.sky2dev.day24.service.AlarmEvaluationService;
import com.sky2dev.day24.service.ArchiveService;
import com.sky2dev.day24.service.RecoveryService;
import com.sky2dev.day24.service.ReportService;
import com.sky2dev.day24.service.TelemetryCollectionService;
import com.sky2dev.day24.util.MarkerConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "app.scheduling.enabled=false")
@Transactional
class Day24ApplicationTests {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TelemetryRecordRepository telemetryRecordRepository;

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    @Autowired
    private NotificationEventRepository notificationEventRepository;

    @Autowired
    private ReportRecordRepository reportRecordRepository;

    @Autowired
    private TaskExecutionLogRepository taskExecutionLogRepository;

    @Autowired
    private TelemetryCollectionService telemetryCollectionService;

    @Autowired
    private AlarmEvaluationService alarmEvaluationService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private RecoveryService recoveryService;

    @Autowired
    private PlatformController platformController;

    @Autowired
    private TaskController taskController;

    @Autowired
    private TelemetryScheduler telemetryScheduler;

    @Autowired
    private HealthCheckScheduler healthCheckScheduler;

    @Autowired
    private AlarmScheduler alarmScheduler;

    @Autowired
    private DashboardScheduler dashboardScheduler;

    @Autowired
    private NotificationScheduler notificationScheduler;

    @Autowired
    private ReportScheduler reportScheduler;

    @Autowired
    private ArchiveScheduler archiveScheduler;

    @Autowired
    private MaintenanceScheduler maintenanceScheduler;

    @Autowired
    private RecoveryScheduler recoveryScheduler;

    @Test
    void contextLoads() {
        assertThat(platformController).isNotNull();
    }

    @Test
    void seedCountsShouldMatchRequirements() {
        assertThat(deviceRepository.count()).isEqualTo(10);
        assertThat(telemetryRecordRepository.count()).isEqualTo(100);
        assertThat(alarmEventRepository.count()).isEqualTo(15);
        assertThat(notificationEventRepository.count()).isEqualTo(20);
        assertThat(reportRecordRepository.count()).isEqualTo(10);
        assertThat(taskExecutionLogRepository.count()).isEqualTo(50);
    }

    @Test
    void schedulerBeansShouldExist() {
        assertThat(telemetryScheduler).isNotNull();
        assertThat(healthCheckScheduler).isNotNull();
        assertThat(alarmScheduler).isNotNull();
        assertThat(dashboardScheduler).isNotNull();
        assertThat(notificationScheduler).isNotNull();
        assertThat(reportScheduler).isNotNull();
        assertThat(archiveScheduler).isNotNull();
        assertThat(maintenanceScheduler).isNotNull();
        assertThat(recoveryScheduler).isNotNull();
    }

    @Test
    void telemetryGenerationShouldProduceDeterministicProfiles() {
        List<TelemetryRecord> records = telemetryCollectionService.collectMetrics();
        assertThat(records).isNotEmpty();
        assertThat(records)
                .allMatch(r -> r.getCpuUsage() == 35 || r.getCpuUsage() == 75 || r.getCpuUsage() == 95)
                .allMatch(r -> r.getMemoryUsage() == 45 || r.getMemoryUsage() == 80 || r.getMemoryUsage() == 98)
                .allMatch(r -> r.getTemperature() == 40 || r.getTemperature() == 65 || r.getTemperature() == 85)
                .allMatch(r -> r.getSignalStrength() == -55 || r.getSignalStrength() == -75 || r.getSignalStrength() == -90);
    }

    @Test
    void alarmGenerationShouldCreateThresholdAlarms() {
        assertThat(alarmEvaluationService.evaluateAlarms()).isNotEmpty();
    }

    @Test
    void reportGenerationShouldPersistRecords() {
        ReportRecord daily = reportService.generateDailyReport();
        ReportRecord performance = reportService.generatePerformanceReport();
        assertThat(daily.getId()).isNotNull();
        assertThat(performance.getId()).isNotNull();
    }

    @Test
    void archiveExecutionShouldCreateArchiveRecord() {
        ArchivedRecord archivedRecord = archiveService.archiveOldTelemetry();
        assertThat(archivedRecord.getSourceType()).isEqualTo("TELEMETRY");
    }

    @Test
    void recoveryExecutionShouldBringOfflineDevicesOnline() {
        Device device = deviceRepository.findAll().stream()
                .findFirst()
                .orElseThrow();
        device.setStatus(DeviceStatus.OFFLINE);
        deviceRepository.save(device);

        List<Device> recovered = recoveryService.autoRecoverDevices();
        assertThat(recovered).isNotEmpty();
        assertThat(recovered).allMatch(d -> d.getStatus() == DeviceStatus.ONLINE);
    }

    @Test
    void markerValidationShouldMatchContract() {
        ApiResponse<?> platform = platformController.status();
        ApiResponse<?> telemetry = taskController.runTelemetryTask();
        ApiResponse<?> alarms = taskController.runAlarmTask();

        assertThat(platform.marker()).isEqualTo(MarkerConstants.SCHEDULER_ACTIVE);
        assertThat(telemetry.marker()).isEqualTo(MarkerConstants.TELEMETRY_COLLECTED);
        assertThat(alarms.marker()).isEqualTo(MarkerConstants.ALARM_GENERATED);
    }
}
