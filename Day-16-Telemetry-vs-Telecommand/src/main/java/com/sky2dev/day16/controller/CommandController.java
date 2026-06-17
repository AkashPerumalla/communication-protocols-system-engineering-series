package com.sky2dev.day16.controller;

import com.sky2dev.day16.dto.ApiResponse;
import com.sky2dev.day16.dto.CommandResultDto;
import com.sky2dev.day16.service.CommandExecutionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/commands")
public class CommandController {

    private final CommandExecutionService commandExecutionService;

    public CommandController(CommandExecutionService commandExecutionService) {
        this.commandExecutionService = commandExecutionService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ApiResponse<List<CommandResultDto>> listCommands() {
        return new ApiResponse<>("NOC CONTROL DASHBOARD", "Recent command audit trail", commandExecutionService.recentCommands().stream().map(result -> new CommandResultDto(result.getId(), result.getDevice().getId(), result.getDevice().getDeviceCode(), result.getCommandType().name(), result.getStatus().name(), result.getMarker(), result.getMessage(), result.getPreviousState(), result.getResultingState(), result.getRequestedAt(), result.getCompletedAt())).toList());
    }

    @PostMapping("/restart/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> restartDevice(@PathVariable Long deviceId) {
        return new ApiResponse<>("COMMAND EXECUTED", "Device restart command executed", toDto(commandExecutionService.restartDevice(deviceId)));
    }

    @PostMapping("/enable-interface/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> enableInterface(@PathVariable Long deviceId) {
        return new ApiResponse<>("STATE UPDATED", "Interface enabled", toDto(commandExecutionService.enableInterface(deviceId)));
    }

    @PostMapping("/disable-interface/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> disableInterface(@PathVariable Long deviceId) {
        return new ApiResponse<>("STATE UPDATED", "Interface disabled", toDto(commandExecutionService.disableInterface(deviceId)));
    }

    @PostMapping("/reset-modem/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> resetModem(@PathVariable Long deviceId) {
        return new ApiResponse<>("SATCOM COMMAND", "Modem reset command executed", toDto(commandExecutionService.resetModem(deviceId)));
    }

    @PostMapping("/change-frequency/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> changeFrequency(@PathVariable Long deviceId) {
        return new ApiResponse<>("TELECOM CONTROL", "Frequency changed", toDto(commandExecutionService.changeFrequency(deviceId)));
    }

    @PostMapping("/enable-carrier/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> enableCarrier(@PathVariable Long deviceId) {
        return new ApiResponse<>("TELECOM CONTROL", "Carrier enabled", toDto(commandExecutionService.enableCarrier(deviceId)));
    }

    @PostMapping("/disable-carrier/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> disableCarrier(@PathVariable Long deviceId) {
        return new ApiResponse<>("TELECOM CONTROL", "Carrier disabled", toDto(commandExecutionService.disableCarrier(deviceId)));
    }

    @PostMapping("/switch-backup/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> switchBackupLink(@PathVariable Long deviceId) {
        return new ApiResponse<>("REMOTE RECOVERY", "Backup link activated", toDto(commandExecutionService.switchToBackupLink(deviceId)));
    }

    @PostMapping("/sync/{deviceId}")
    @Transactional
    public ApiResponse<CommandResultDto> syncConfiguration(@PathVariable Long deviceId) {
        return new ApiResponse<>("STATE UPDATED", "Configuration synchronized", toDto(commandExecutionService.syncConfiguration(deviceId)));
    }

    @PostMapping("/clear-alarm/{alarmId}")
    @Transactional
    public ApiResponse<CommandResultDto> clearAlarm(@PathVariable Long alarmId) {
        return new ApiResponse<>("REMOTE RECOVERY", "Alarm cleared", toDto(commandExecutionService.clearAlarm(alarmId)));
    }

    private CommandResultDto toDto(com.sky2dev.day16.model.CommandResult result) {
        return new CommandResultDto(result.getId(), result.getDevice().getId(), result.getDevice().getDeviceCode(), result.getCommandType().name(), result.getStatus().name(), result.getMarker(), result.getMessage(), result.getPreviousState(), result.getResultingState(), result.getRequestedAt(), result.getCompletedAt());
    }
}
