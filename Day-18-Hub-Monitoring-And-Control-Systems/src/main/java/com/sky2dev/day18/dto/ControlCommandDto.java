package com.sky2dev.day18.dto;

import com.sky2dev.day18.model.CommandType;
import com.sky2dev.day18.model.ControlCommand;
import com.sky2dev.day18.model.ExecutionStatus;
import java.time.Instant;

public record ControlCommandDto(
        Long id,
        Long deviceId,
        String deviceName,
        CommandType commandType,
        String commandPayload,
        ExecutionStatus executionStatus,
        Instant executionTime,
        String executedBy
) {
    public static ControlCommandDto from(ControlCommand command) {
        return new ControlCommandDto(
                command.getId(),
                command.getDevice().getId(),
                command.getDevice().getHostname(),
                command.getCommandType(),
                command.getCommandPayload(),
                command.getExecutionStatus(),
                command.getExecutionTime(),
                command.getExecutedBy()
        );
    }
}
