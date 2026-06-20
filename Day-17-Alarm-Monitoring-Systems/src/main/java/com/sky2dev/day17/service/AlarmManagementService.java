package com.sky2dev.day17.service;

import com.sky2dev.day17.dto.AlarmDto;
import com.sky2dev.day17.dto.DeviceDto;
import com.sky2dev.day17.dto.DashboardDto;
import com.sky2dev.day17.dto.EscalationDto;
import com.sky2dev.day17.dto.MetricDto;
import com.sky2dev.day17.dto.NotificationDto;
import com.sky2dev.day17.dto.RootCauseDto;
import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmCorrelation;
import com.sky2dev.day17.model.AlarmRule;
import com.sky2dev.day17.model.EscalationPolicy;
import com.sky2dev.day17.model.ManagedDevice;
import com.sky2dev.day17.model.MetricRecord;
import com.sky2dev.day17.model.NotificationDelivery;
import com.sky2dev.day17.model.RootCause;
import com.sky2dev.day17.repository.AlarmCorrelationRepository;
import com.sky2dev.day17.repository.AlarmRepository;
import com.sky2dev.day17.repository.AlarmRuleRepository;
import com.sky2dev.day17.repository.EscalationPolicyRepository;
import com.sky2dev.day17.repository.ManagedDeviceRepository;
import com.sky2dev.day17.repository.MetricRecordRepository;
import com.sky2dev.day17.repository.NotificationDeliveryRepository;
import com.sky2dev.day17.repository.RootCauseRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlarmManagementService {

    private final ManagedDeviceRepository managedDeviceRepository;
    private final MetricRecordRepository metricRecordRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmRuleRepository alarmRuleRepository;
    private final AlarmRuleEngine alarmRuleEngine;
    private final AlarmLifecycleService alarmLifecycleService;
    private final NotificationService notificationService;
    private final EscalationService escalationService;
    private final AlarmCorrelationRepository alarmCorrelationRepository;
    private final RootCauseRepository rootCauseRepository;
    private final TelemetryGeneratorService telemetryGeneratorService;

    public AlarmManagementService(ManagedDeviceRepository managedDeviceRepository,
                                  MetricRecordRepository metricRecordRepository,
                                  AlarmRepository alarmRepository,
                                  AlarmRuleRepository alarmRuleRepository,
                                  AlarmRuleEngine alarmRuleEngine,
                                  AlarmLifecycleService alarmLifecycleService,
                                  NotificationService notificationService,
                                  EscalationService escalationService,
                                  AlarmCorrelationRepository alarmCorrelationRepository,
                                  RootCauseRepository rootCauseRepository,
                                  TelemetryGeneratorService telemetryGeneratorService) {
        this.managedDeviceRepository = managedDeviceRepository;
        this.metricRecordRepository = metricRecordRepository;
        this.alarmRepository = alarmRepository;
        this.alarmRuleRepository = alarmRuleRepository;
        this.alarmRuleEngine = alarmRuleEngine;
        this.alarmLifecycleService = alarmLifecycleService;
        this.notificationService = notificationService;
        this.escalationService = escalationService;
        this.alarmCorrelationRepository = alarmCorrelationRepository;
        this.rootCauseRepository = rootCauseRepository;
        this.telemetryGeneratorService = telemetryGeneratorService;
    }

    public List<DeviceDto> getDevices() {
        return managedDeviceRepository.findAll().stream().map(DeviceDto::from).toList();
    }

    public List<MetricDto> getMetrics() {
        return metricRecordRepository.findTop20ByOrderByRecordedAtDesc().stream().map(MetricDto::from).toList();
    }

    public List<AlarmDto> getAlarms() {
        return alarmRepository.findAll().stream().map(AlarmDto::from).toList();
    }

    public List<AlarmDto> getOpenAlarms() {
        return alarmRepository.findByState(com.sky2dev.day17.model.AlarmState.OPEN).stream().map(AlarmDto::from).toList();
    }

    public List<AlarmDto> getCriticalAlarms() {
        return alarmRepository.findBySeverity(com.sky2dev.day17.model.AlarmSeverity.CRITICAL).stream().map(AlarmDto::from).toList();
    }

    public List<AlarmDto> getUnacknowledgedAlarms() {
        return alarmRepository.findByAcknowledgedFalse().stream().map(AlarmDto::from).toList();
    }

    public AlarmDto acknowledge(Long alarmId) {
        return AlarmDto.from(alarmLifecycleService.acknowledge(alarmId));
    }

    public AlarmDto escalate(Long alarmId) {
        return AlarmDto.from(alarmLifecycleService.escalate(alarmId));
    }

    public AlarmDto resolve(Long alarmId) {
        return AlarmDto.from(alarmLifecycleService.resolve(alarmId));
    }

    public AlarmDto close(Long alarmId) {
        return AlarmDto.from(alarmLifecycleService.close(alarmId));
    }

    public List<EscalationDto> getEscalations() {
        return escalationService.findAll().stream().map(EscalationDto::from).toList();
    }

    public List<NotificationDto> getNotifications() {
        return notificationService.findAll().stream().map(NotificationDto::from).toList();
    }

    public List<RootCauseDto> getRootCauses() {
        return rootCauseRepository.findAll().stream().map(RootCauseDto::from).toList();
    }

    public DashboardDto getDashboard() {
        return new DashboardDto(
                (int) managedDeviceRepository.count(),
                (int) metricRecordRepository.count(),
                alarmRepository.findByState(com.sky2dev.day17.model.AlarmState.OPEN).size(),
                alarmRepository.findBySeverity(com.sky2dev.day17.model.AlarmSeverity.CRITICAL).size(),
                alarmRepository.findByAcknowledgedFalse().size(),
                (int) alarmRepository.findAll().stream().filter(com.sky2dev.day17.model.Alarm::isEscalated).count(),
                (int) alarmCorrelationRepository.count(),
                (int) rootCauseRepository.count(),
                List.of("THRESHOLD DETECTED", "ALARM GENERATED", "SEVERITY ASSIGNED", "ALARM ACKNOWLEDGED", "ALARM ESCALATED", "ALARM RESOLVED", "ALARM CLOSED", "ROOT CAUSE IDENTIFIED", "ALARM CORRELATED", "NOC ALARM DASHBOARD")
        );
    }

    public List<Alarm> rebuildAlarmsFromMetrics() {
        List<MetricRecord> metrics = telemetryGeneratorService.generateMetrics(managedDeviceRepository.findAll());
        metricRecordRepository.saveAll(metrics);
        List<AlarmRule> rules = alarmRuleRepository.findAll();
        List<Alarm> alarms = metrics.stream().flatMap(metric -> alarmRuleEngine.evaluate(metric, rules).stream()).toList();
        List<Alarm> saved = alarmRepository.saveAll(alarms);
        saved.forEach(notificationService::notifyChannels);
        return saved;
    }
}
