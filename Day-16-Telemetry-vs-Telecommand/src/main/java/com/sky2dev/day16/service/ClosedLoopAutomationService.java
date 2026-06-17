package com.sky2dev.day16.service;

import com.sky2dev.day16.dto.AlarmDto;
import com.sky2dev.day16.dto.AutomationResultDto;
import com.sky2dev.day16.dto.CommandResultDto;
import com.sky2dev.day16.model.Alarm;
import com.sky2dev.day16.model.CommandResult;
import com.sky2dev.day16.model.DeviceType;
import com.sky2dev.day16.model.ManagedDevice;
import com.sky2dev.day16.repository.ManagedDeviceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClosedLoopAutomationService {

    private final AlarmGenerationService alarmGenerationService;
    private final CommandExecutionService commandExecutionService;
    private final ManagedDeviceRepository managedDeviceRepository;
    private final TelemetryGeneratorService telemetryGeneratorService;

    @Transactional
    public AutomationResultDto runClosedLoopAutomation() {
        List<CommandResultDto> actions = new ArrayList<>();
        List<AlarmDto> clearedAlarms = new ArrayList<>();

        for (Alarm alarm : alarmGenerationService.activeAlarms()) {
            ManagedDevice device = alarm.getDevice();
            if ("BER".equals(alarm.getMetricName()) && device.getDeviceType().supportsModemReset()) {
                CommandResult result = commandExecutionService.resetModem(device.getId());
                actions.add(toCommandDto(result));
                Alarm cleared = alarmGenerationService.clearAlarm(alarm, "Recovered automatically after modem reset");
                clearedAlarms.add(toAlarmDto(cleared));
            } else if (!device.isBackupLinkActive() && (device.getDeviceType() == DeviceType.MODEM_01 || device.getDeviceType() == DeviceType.BUC_01)) {
                CommandResult result = commandExecutionService.switchToBackupLink(device.getId());
                actions.add(toCommandDto(result));
            }
        }

        if (actions.isEmpty()) {
            ManagedDevice modem = managedDeviceRepository.findAll().stream().filter(device -> device.getDeviceType().supportsModemReset()).findFirst().orElseThrow();
            CommandResult result = commandExecutionService.resetModem(modem.getId());
            actions.add(toCommandDto(result));
        }

        return new AutomationResultDto("AUTO CORRECTION", "Closed-loop automation executed recovery actions.", actions, clearedAlarms);
    }

    private CommandResultDto toCommandDto(CommandResult result) {
        return new CommandResultDto(result.getId(), result.getDevice().getId(), result.getDevice().getDeviceCode(), result.getCommandType().name(), result.getStatus().name(), result.getMarker(), result.getMessage(), result.getPreviousState(), result.getResultingState(), result.getRequestedAt(), result.getCompletedAt());
    }

    private AlarmDto toAlarmDto(Alarm alarm) {
        return new AlarmDto(alarm.getId(), alarm.getDevice().getId(), alarm.getDevice().getDeviceCode(), alarm.getSeverity().name(), alarm.getStatus().name(), alarm.getMetricName(), alarm.getMetricValue(), alarm.getThresholdValue(), alarm.getMessage(), alarm.getTriggeredAt(), alarm.getClearedAt(), alarm.getRecoveryNote());
    }
}
