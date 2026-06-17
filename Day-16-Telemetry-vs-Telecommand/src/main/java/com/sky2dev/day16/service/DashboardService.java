package com.sky2dev.day16.service;

import com.sky2dev.day16.dto.AlarmDto;
import com.sky2dev.day16.dto.CommandResultDto;
import com.sky2dev.day16.dto.DashboardDto;
import com.sky2dev.day16.dto.DeviceDto;
import com.sky2dev.day16.dto.TelemetryDto;
import com.sky2dev.day16.model.Alarm;
import com.sky2dev.day16.model.CommandResult;
import com.sky2dev.day16.model.TelemetryRecord;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ManagedDeviceService managedDeviceService;
    private final TelemetryGeneratorService telemetryGeneratorService;
    private final AlarmGenerationService alarmGenerationService;
    private final AuditTrailService auditTrailService;

    @Transactional(readOnly = true)
    public DashboardDto buildDashboard() {
        List<DeviceDto> devices = managedDeviceService.listDevices().stream().map(ManagedDeviceService::toDto).toList();
        List<TelemetryDto> telemetry = telemetryGeneratorService.recentTelemetry().stream().map(this::toTelemetryDto).toList();
        List<AlarmDto> activeAlarms = alarmGenerationService.activeAlarms().stream().map(this::toAlarmDto).toList();
        List<CommandResultDto> recentCommands = auditTrailService.recentCommands().stream().map(this::toCommandDto).toList();
        List<CommandResultDto> recoveryActions = recentCommands.stream().filter(command -> command.marker().equals("REMOTE RECOVERY") || command.marker().equals("AUTO CORRECTION") || command.marker().equals("SATCOM COMMAND")).toList();

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("deviceCount", devices.size());
        summary.put("telemetryCount", telemetry.size());
        summary.put("activeAlarmCount", activeAlarms.size());
        summary.put("commandCount", recentCommands.size());
        summary.put("recoveryActionCount", recoveryActions.size());
        summary.put("automationMarker", "AUTO CORRECTION");

        return new DashboardDto(devices, telemetry, activeAlarms, recentCommands, recoveryActions, summary);
    }

    private TelemetryDto toTelemetryDto(TelemetryRecord record) {
        return new TelemetryDto(record.getId(), record.getDevice().getId(), record.getDevice().getDeviceCode(), record.getCollectedAt(), record.getCpu(), record.getMemory(), record.getTemperature(), record.getPower(), record.getInterfaceStatus(), record.getRfPower(), record.getBer(), record.getCarrierLock(), record.getFrequencyMHz(), record.getSymbolRateKsps(), record.getEbNo(), record.getBucStatus(), record.getLnbStatus(), record.getModemStatus(), record.getUplinkPower(), record.getDownlinkPower(), record.getSourceScenario());
    }

    private AlarmDto toAlarmDto(Alarm alarm) {
        return new AlarmDto(alarm.getId(), alarm.getDevice().getId(), alarm.getDevice().getDeviceCode(), alarm.getSeverity().name(), alarm.getStatus().name(), alarm.getMetricName(), alarm.getMetricValue(), alarm.getThresholdValue(), alarm.getMessage(), alarm.getTriggeredAt(), alarm.getClearedAt(), alarm.getRecoveryNote());
    }

    private CommandResultDto toCommandDto(CommandResult result) {
        return new CommandResultDto(result.getId(), result.getDevice().getId(), result.getDevice().getDeviceCode(), result.getCommandType().name(), result.getStatus().name(), result.getMarker(), result.getMessage(), result.getPreviousState(), result.getResultingState(), result.getRequestedAt(), result.getCompletedAt());
    }
}
