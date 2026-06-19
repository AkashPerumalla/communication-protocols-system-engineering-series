package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.AcknowledgeRequest;
import com.sky2dev.day18.dto.AlarmDto;
import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.service.AlarmEngine;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmEngine alarmEngine;

    @GetMapping
    public ApiResponse<List<AlarmDto>> getAlarms() {
        alarmEngine.runThresholdEvaluation();
        return new ApiResponse<>(
                "ALARM GENERATED",
                "Threshold evaluation completed and alarms updated",
                alarmEngine.listAlarms()
        );
    }

    @PostMapping("/{id}/acknowledge")
    public ApiResponse<AlarmDto> acknowledge(@PathVariable Long id, @Valid @RequestBody AcknowledgeRequest request) {
        return new ApiResponse<>(
                "ALARM ACKNOWLEDGED",
                "Alarm acknowledged by operator",
                alarmEngine.acknowledge(id, request.acknowledgedBy())
        );
    }
}
