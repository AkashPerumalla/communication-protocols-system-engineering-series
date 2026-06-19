package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.ControlCommandDto;
import com.sky2dev.day18.dto.ControlRequest;
import com.sky2dev.day18.model.CommandType;
import com.sky2dev.day18.service.DeviceControlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/control")
@RequiredArgsConstructor
public class ControlController {

    private final DeviceControlService deviceControlService;

    @PostMapping("/reboot/{id}")
    public ApiResponse<ControlCommandDto> reboot(@PathVariable Long id, @Valid @RequestBody ControlRequest request) {
        return execute(id, CommandType.REBOOT_DEVICE, request);
    }

    @PostMapping("/reset-interface/{id}")
    public ApiResponse<ControlCommandDto> resetInterface(@PathVariable Long id, @Valid @RequestBody ControlRequest request) {
        return execute(id, CommandType.RESET_INTERFACE, request);
    }

    @PostMapping("/enable-port/{id}")
    public ApiResponse<ControlCommandDto> enablePort(@PathVariable Long id, @Valid @RequestBody ControlRequest request) {
        return execute(id, CommandType.ENABLE_PORT, request);
    }

    @PostMapping("/disable-port/{id}")
    public ApiResponse<ControlCommandDto> disablePort(@PathVariable Long id, @Valid @RequestBody ControlRequest request) {
        return execute(id, CommandType.DISABLE_PORT, request);
    }

    @PostMapping("/change-config/{id}")
    public ApiResponse<ControlCommandDto> changeConfig(@PathVariable Long id, @Valid @RequestBody ControlRequest request) {
        return execute(id, CommandType.CHANGE_CONFIGURATION, request);
    }

    private ApiResponse<ControlCommandDto> execute(Long id, CommandType commandType, ControlRequest request) {
        return new ApiResponse<>(
                "CONTROL EXECUTED",
                "Control command executed with audit trail",
                deviceControlService.executeCommand(id, commandType, request.commandPayload(), request.executedBy())
        );
    }
}
