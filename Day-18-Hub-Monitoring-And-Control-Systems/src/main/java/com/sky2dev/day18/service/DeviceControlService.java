package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.ControlCommandDto;
import com.sky2dev.day18.model.Alarm;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.AlarmStatus;
import com.sky2dev.day18.model.AlarmType;
import com.sky2dev.day18.model.CommandType;
import com.sky2dev.day18.model.ControlCommand;
import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.model.ExecutionStatus;
import com.sky2dev.day18.model.HubDevice;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.ControlCommandRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceControlService {

    private final HubDeviceRepository hubDeviceRepository;
    private final ControlCommandRepository controlCommandRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    public ControlCommandDto executeCommand(Long deviceId, CommandType commandType, String payload, String executedBy) {
        HubDevice device = hubDeviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + deviceId));

        boolean failRequested = payload != null && payload.toLowerCase().contains("force-fail");
        boolean success = !failRequested;

        if (success) {
            applySuccessfulStateTransition(device, commandType, payload);
        } else {
            device.setStatus(DeviceStatus.DEGRADED);
            Alarm controlFailureAlarm = Alarm.builder()
                    .device(device)
                    .severity(AlarmSeverity.CRITICAL)
                    .alarmType(AlarmType.CONTROL_FAILURE)
                    .message("Control operation failed for command=" + commandType)
                    .status(AlarmStatus.OPEN)
                    .acknowledged(false)
                    .createdAt(Instant.now())
                    .build();
            alarmRepository.save(controlFailureAlarm);
        }

        hubDeviceRepository.save(device);

        ControlCommand command = ControlCommand.builder()
                .device(device)
                .commandType(commandType)
                .commandPayload(payload)
                .executionStatus(success ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED)
                .executionTime(Instant.now())
                .executedBy(executedBy)
                .build();

        return ControlCommandDto.from(controlCommandRepository.save(command));
    }

    @Transactional(readOnly = true)
    public List<ControlCommandDto> getRecentCommands() {
        return controlCommandRepository.findTop10ByOrderByExecutionTimeDesc().stream()
                .map(ControlCommandDto::from)
                .toList();
    }

    private void applySuccessfulStateTransition(HubDevice device, CommandType commandType, String payload) {
        switch (commandType) {
            case REBOOT_DEVICE -> {
                device.setStatus(DeviceStatus.RECOVERING);
                device.setStatus(DeviceStatus.ONLINE);
            }
            case RESET_INTERFACE, ENABLE_PORT -> device.setStatus(DeviceStatus.ONLINE);
            case DISABLE_PORT -> device.setStatus(DeviceStatus.DEGRADED);
            case CHANGE_CONFIGURATION -> {
                device.setStatus(DeviceStatus.MAINTENANCE);
                if (payload != null && !payload.isBlank()) {
                    device.setFirmwareVersion(device.getFirmwareVersion() + "|" + payload);
                }
                device.setStatus(DeviceStatus.ONLINE);
            }
        }
        device.setLastSeen(Instant.now());
    }
}
