package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.DeviceHealthDto;
import com.sky2dev.day18.dto.DeviceMetricDto;
import com.sky2dev.day18.dto.HubDeviceDto;
import com.sky2dev.day18.model.DeviceMetric;
import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.model.InterfaceStatus;
import com.sky2dev.day18.repository.DeviceMetricRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubMonitoringService {

    private final HubDeviceRepository hubDeviceRepository;
    private final DeviceMetricRepository deviceMetricRepository;

    @Transactional(readOnly = true)
    public List<HubDeviceDto> discoverDevices() {
        return hubDeviceRepository.findAll().stream()
                .map(HubDeviceDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public HubDeviceDto getDevice(Long id) {
        return hubDeviceRepository.findById(id)
                .map(HubDeviceDto::from)
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<DeviceMetricDto> collectMetrics() {
        return deviceMetricRepository.findTop20ByOrderByTimestampDesc().stream()
                .map(DeviceMetricDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DeviceHealthDto> getDeviceHealth() {
        return hubDeviceRepository.findAll().stream()
                .map(device -> {
                    DeviceMetric metric = deviceMetricRepository.findTopByDeviceIdOrderByTimestampDesc(device.getId());
                    int score = baseScoreByStatus(device.getStatus());
                    if (metric != null) {
                        if (metric.getCpuUsage() != null && metric.getCpuUsage().doubleValue() > 85) {
                            score -= 15;
                        }
                        if (metric.getTemperature() != null && metric.getTemperature().doubleValue() > 70) {
                            score -= 20;
                        }
                        if (metric.getSignalStrength() != null && metric.getSignalStrength().doubleValue() < -80) {
                            score -= 20;
                        }
                        if (metric.getInterfaceStatus() == InterfaceStatus.DOWN) {
                            score -= 25;
                        }
                    }
                    int healthScore = Math.max(0, Math.min(100, score));
                    return new DeviceHealthDto(
                            device.getId(),
                            device.getHostname(),
                            device.getStatus(),
                            healthScore,
                            healthSummary(healthScore)
                    );
                })
                .toList();
    }

    private int baseScoreByStatus(DeviceStatus status) {
        return switch (status) {
            case ONLINE -> 95;
            case DEGRADED -> 70;
            case RECOVERING -> 60;
            case MAINTENANCE -> 55;
            case OFFLINE -> 20;
        };
    }

    private String healthSummary(int score) {
        if (score >= 85) {
            return "Stable";
        }
        if (score >= 65) {
            return "Degraded";
        }
        return "At Risk";
    }
}
