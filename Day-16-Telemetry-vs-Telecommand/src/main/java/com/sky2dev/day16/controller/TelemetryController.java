package com.sky2dev.day16.controller;

import com.sky2dev.day16.dto.ApiResponse;
import com.sky2dev.day16.dto.TelemetryDto;
import com.sky2dev.day16.service.TelemetryGeneratorService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryGeneratorService telemetryGeneratorService;

    public TelemetryController(TelemetryGeneratorService telemetryGeneratorService) {
        this.telemetryGeneratorService = telemetryGeneratorService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ApiResponse<List<TelemetryDto>> listTelemetry() {
        return new ApiResponse<>("TELEMETRY RECEIVED", "Recent telemetry records", telemetryGeneratorService.recentTelemetry().stream().map(record -> new TelemetryDto(record.getId(), record.getDevice().getId(), record.getDevice().getDeviceCode(), record.getCollectedAt(), record.getCpu(), record.getMemory(), record.getTemperature(), record.getPower(), record.getInterfaceStatus(), record.getRfPower(), record.getBer(), record.getCarrierLock(), record.getFrequencyMHz(), record.getSymbolRateKsps(), record.getEbNo(), record.getBucStatus(), record.getLnbStatus(), record.getModemStatus(), record.getUplinkPower(), record.getDownlinkPower(), record.getSourceScenario())).toList());
    }
}
