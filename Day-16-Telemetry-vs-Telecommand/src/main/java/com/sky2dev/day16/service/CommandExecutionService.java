package com.sky2dev.day16.service;

import com.sky2dev.day16.model.Alarm;
import com.sky2dev.day16.model.CommandResult;
import com.sky2dev.day16.model.CommandStatus;
import com.sky2dev.day16.model.DeviceState;
import com.sky2dev.day16.model.DeviceType;
import com.sky2dev.day16.model.ManagedDevice;
import com.sky2dev.day16.model.Telecommand;
import com.sky2dev.day16.model.TelecommandType;
import com.sky2dev.day16.repository.AlarmRepository;
import com.sky2dev.day16.repository.ManagedDeviceRepository;
import com.sky2dev.day16.repository.TelecommandRepository;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommandExecutionService {

    private final ManagedDeviceRepository managedDeviceRepository;
    private final TelecommandRepository telecommandRepository;
    private final AuditTrailService auditTrailService;
    private final TelemetryGeneratorService telemetryGeneratorService;
    private final AlarmGenerationService alarmGenerationService;
    private final AlarmRepository alarmRepository;

    @Transactional
    public CommandResult restartDevice(Long deviceId) {
        return execute(deviceId, TelecommandType.RESTART_DEVICE, "COMMAND EXECUTED", "Restart the device and return it to service.", device -> {
            device.setState(DeviceState.RESTARTING);
            device.setModemHealthy(true);
        }, device -> device.setState(DeviceState.ACTIVE));
    }

    @Transactional
    public CommandResult enableInterface(Long deviceId) {
        return execute(deviceId, TelecommandType.ENABLE_INTERFACE, "STATE UPDATED", "Enable the device interface.", device -> require(device, true, false, false, false), device -> {
            device.setInterfaceEnabled(true);
            device.setState(DeviceState.ACTIVE);
        });
    }

    @Transactional
    public CommandResult disableInterface(Long deviceId) {
        return execute(deviceId, TelecommandType.DISABLE_INTERFACE, "STATE UPDATED", "Disable the device interface.", device -> require(device, true, false, false, false), device -> {
            device.setInterfaceEnabled(false);
            device.setState(DeviceState.DEGRADED);
        });
    }

    @Transactional
    public CommandResult changeFrequency(Long deviceId) {
        return execute(deviceId, TelecommandType.CHANGE_FREQUENCY, "TELECOM CONTROL", "Tune the telecom or SatCom carrier frequency.", device -> require(device, false, false, false, true), device -> device.setCurrentFrequencyMHz(device.getCurrentFrequencyMHz() + 8));
    }

    @Transactional
    public CommandResult resetModem(Long deviceId) {
        return execute(deviceId, TelecommandType.RESET_MODEM, "SATCOM COMMAND", "Reset the modem and recover the link.", device -> require(device, false, false, true, false), device -> {
            device.setModemHealthy(true);
            device.setCarrierEnabled(true);
            device.setInterfaceEnabled(true);
            device.setState(DeviceState.RECOVERING);
        }, device -> device.setState(DeviceState.ACTIVE));
    }

    @Transactional
    public CommandResult enableCarrier(Long deviceId) {
        return execute(deviceId, TelecommandType.ENABLE_CARRIER, "TELECOM CONTROL", "Enable the carrier.", device -> require(device, false, true, false, false), device -> device.setCarrierEnabled(true));
    }

    @Transactional
    public CommandResult disableCarrier(Long deviceId) {
        return execute(deviceId, TelecommandType.DISABLE_CARRIER, "TELECOM CONTROL", "Disable the carrier.", device -> require(device, false, true, false, false), device -> device.setCarrierEnabled(false));
    }

    @Transactional
    public CommandResult switchToBackupLink(Long deviceId) {
        return execute(deviceId, TelecommandType.SWITCH_TO_BACKUP_LINK, "REMOTE RECOVERY", "Move service to the backup link.", device -> require(device, false, true, true, true), device -> {
            device.setBackupLinkActive(true);
            device.setCarrierEnabled(true);
            device.setState(DeviceState.RECOVERING);
        }, device -> device.setState(DeviceState.ACTIVE));
    }

    @Transactional
    public CommandResult syncConfiguration(Long deviceId) {
        return execute(deviceId, TelecommandType.SYNC_CONFIGURATION, "STATE UPDATED", "Synchronize configuration with the control center.", device -> {
        }, device -> device.setState(DeviceState.ACTIVE));
    }

    @Transactional
    public CommandResult clearAlarm(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alarm not found"));
        alarmGenerationService.clearAlarm(alarm, "Cleared after remote recovery");
        ManagedDevice device = alarm.getDevice();
        CommandResult result = buildResult(device, TelecommandType.CLEAR_ALARM, "REMOTE RECOVERY", "Alarm cleared and service restored.", device.getState(), DeviceState.ACTIVE, null);
        device.setState(DeviceState.ACTIVE);
        managedDeviceRepository.save(device);
        return auditTrailService.record(result);
    }

    @Transactional(readOnly = true)
    public List<CommandResult> recentCommands() {
        return auditTrailService.recentCommands();
    }

    private CommandResult execute(Long deviceId, TelecommandType commandType, String marker, String details, Consumer<ManagedDevice> validation, Consumer<ManagedDevice> primaryMutation) {
        return execute(deviceId, commandType, marker, details, validation, primaryMutation, null);
    }

    private CommandResult execute(Long deviceId, TelecommandType commandType, String marker, String details, Consumer<ManagedDevice> validation, Consumer<ManagedDevice> primaryMutation, Consumer<ManagedDevice> postMutation) {
        ManagedDevice device = managedDeviceRepository.findById(deviceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
        validation.accept(device);
        Telecommand telecommand = telecommandRepository.save(Telecommand.builder().device(device).commandType(commandType).status(CommandStatus.VALIDATED).requestedAt(Instant.now()).marker(marker).details(details).build());
        DeviceState previousState = device.getState();
        primaryMutation.accept(device);
        if (postMutation != null) {
            postMutation.accept(device);
        }
        managedDeviceRepository.save(device);
        if (commandType != TelecommandType.CLEAR_ALARM) {
            telemetryGeneratorService.capture(device);
        }
        telecommand.setStatus(CommandStatus.EXECUTED);
        telecommand.setExecutedAt(Instant.now());
        telecommandRepository.save(telecommand);
        CommandResult result = buildResult(device, commandType, marker, details, previousState, device.getState(), telecommand);
        return auditTrailService.record(result);
    }

    private CommandResult buildResult(ManagedDevice device, TelecommandType commandType, String marker, String message, DeviceState previousState, DeviceState resultingState, Telecommand telecommand) {
        return CommandResult.builder().device(device).telecommand(telecommand).commandType(commandType).status(CommandStatus.EXECUTED).marker(marker).message(message).previousState(previousState.name()).resultingState(resultingState.name()).requestedAt(telecommand != null ? telecommand.getRequestedAt() : Instant.now()).completedAt(Instant.now()).build();
    }

    private void require(ManagedDevice device, boolean interfaceControl, boolean carrierControl, boolean modemReset, boolean frequencyControl) {
        DeviceType type = device.getDeviceType();
        if (interfaceControl && !type.supportsInterfaceControl()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Interface control is not supported for this device");
        }
        if (carrierControl && !type.supportsCarrierControl()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Carrier control is not supported for this device");
        }
        if (modemReset && !type.supportsModemReset()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Modem reset is not supported for this device");
        }
        if (frequencyControl && !type.supportsFrequencyControl()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Frequency control is not supported for this device");
        }
    }
}
