package com.sky2dev.day25.controller;

import com.sky2dev.day25.dto.ApiResponse;
import com.sky2dev.day25.dto.PlatformStatusResponse;
import com.sky2dev.day25.repository.AlarmEventRepository;
import com.sky2dev.day25.repository.DeviceRepository;
import com.sky2dev.day25.repository.NotificationEventRepository;
import com.sky2dev.day25.repository.ReportRecordRepository;
import com.sky2dev.day25.repository.TelemetryRecordRepository;
import com.sky2dev.day25.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
@RequiredArgsConstructor
public class PlatformController {

    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final ReportRecordRepository reportRecordRepository;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<PlatformStatusResponse>> getPlatformStatus() {
        PlatformStatusResponse data = new PlatformStatusResponse(
                "Day-25 Device Monitoring Platform",
                activeProfile,
                "UP",
                "Spring Boot 3.3.x / Java 17",
                "Container-ready with externalized configuration",
                "docker".equalsIgnoreCase(activeProfile) ? "MYSQL" : "H2",
                deviceRepository.count(),
                telemetryRecordRepository.count(),
                alarmEventRepository.count(),
                notificationEventRepository.count(),
                reportRecordRepository.count());

        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.DOCKER_READY,
                MarkerConstants.APPLICATION_RUNNING,
                data));
    }
}
