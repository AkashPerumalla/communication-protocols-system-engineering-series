package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.ControlCommandDto;
import com.sky2dev.day18.model.Alarm;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.AlarmStatus;
import com.sky2dev.day18.model.AlarmType;
import com.sky2dev.day18.model.CommandType;
import com.sky2dev.day18.model.DeviceMetric;
import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.model.HubDevice;
import com.sky2dev.day18.model.InterfaceStatus;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.DeviceMetricRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutomationService {

    private final HubDeviceRepository hubDeviceRepository;
    private final DeviceMetricRepository deviceMetricRepository;
    private final DeviceControlService deviceControlService;
    private final AlarmRepository alarmRepository;

    @Transactional
    public List<String> recoverDevice(Long deviceId, String triggeredBy) {
        HubDevice device = hubDeviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + deviceId));

        DeviceMetric latestMetric = deviceMetricRepository.findTopByDeviceIdOrderByTimestampDesc(deviceId);
        List<String> actions = new ArrayList<>();

        if (latestMetric != null && latestMetric.getInterfaceStatus() == InterfaceStatus.DOWN) {
            ControlCommandDto cmd = deviceControlService.executeCommand(
                    deviceId,
                    CommandType.RESET_INTERFACE,
                    "auto-reset-by-automation",
                    triggeredBy
            );
            actions.add("Interface Down -> Auto Reset Interface (commandId=" + cmd.id() + ")");
        }

        if (device.getDeviceType() == com.sky2dev.day18.model.DeviceType.MODEM && device.getStatus() == DeviceStatus.OFFLINE) {
            ControlCommandDto cmd = deviceControlService.executeCommand(
                    deviceId,
                    CommandType.REBOOT_DEVICE,
                    "auto-restart-modem",
                    triggeredBy
            );
            actions.add("Modem Offline -> Auto Restart Modem (commandId=" + cmd.id() + ")");
        }

        if (latestMetric != null && latestMetric.getTemperature() != null && latestMetric.getTemperature().doubleValue() > 80) {
            alarmRepository.save(Alarm.builder()
                    .device(device)
                    .severity(AlarmSeverity.CRITICAL)
                    .alarmType(AlarmType.HIGH_TEMPERATURE)
                    .message("Auto action: critical temperature alarm generated")
                    .status(AlarmStatus.OPEN)
                    .acknowledged(false)
                    .createdAt(Instant.now())
                    .build());
            actions.add("High Temperature -> Generate Critical Alarm");
        }

        if (latestMetric != null && latestMetric.getSignalStrength() != null && latestMetric.getSignalStrength().doubleValue() < -90) {
            deviceControlService.executeCommand(
                    deviceId,
                    CommandType.CHANGE_CONFIGURATION,
                    "recovery-profile=low-signal-boost",
                    triggeredBy
            );
            actions.add("Low Signal -> Trigger Recovery Action");
        }

        if (actions.isEmpty()) {
            actions.add("No recovery action needed. Device telemetry is within safe envelope.");
        } else {
            device.setStatus(DeviceStatus.RECOVERING);
            device.setStatus(DeviceStatus.ONLINE);
            device.setLastSeen(Instant.now());
            hubDeviceRepository.save(device);
        }

        return actions;
    }
}
