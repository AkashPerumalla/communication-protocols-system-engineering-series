package com.sky2dev.day26.service;

import com.sky2dev.day26.dto.AlarmResponse;
import com.sky2dev.day26.entity.AlarmEvent;
import com.sky2dev.day26.entity.AlarmSeverity;
import com.sky2dev.day26.entity.AlarmStatus;
import com.sky2dev.day26.entity.Device;
import com.sky2dev.day26.entity.DeviceStatus;
import com.sky2dev.day26.entity.TelemetryRecord;
import com.sky2dev.day26.mapper.AlarmMapper;
import com.sky2dev.day26.repository.AlarmEventRepository;
import com.sky2dev.day26.repository.DeviceRepository;
import com.sky2dev.day26.repository.TelemetryRecordRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmEventRepository alarmEventRepository;
    private final DeviceRepository deviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmMapper alarmMapper;

    @Transactional(readOnly = true)
    public List<AlarmResponse> getAlarms() {
        return alarmEventRepository.findTop20ByOrderByRaisedAtDesc().stream()
                .map(alarmMapper::toResponse)
                .toList();
    }

    @Transactional
    public void evaluateTelemetryAlarms() {
        List<Device> devices = deviceRepository.findAllByOrderByNameAsc();
        LocalDateTime now = LocalDateTime.now().withNano(0);

        for (Device device : devices) {
            TelemetryRecord latest = telemetryRecordRepository.findTop1ByDeviceIdOrderByCollectedAtDesc(device.getId()).orElse(null);
            if (latest == null) {
                continue;
            }

            if (device.getStatus() == DeviceStatus.OFFLINE || latest.getMetricValue() >= 70.0 || latest.getQualityScore() <= 78) {
                alarmEventRepository.save(AlarmEvent.builder()
                        .device(device)
                        .code("K8S-ALM-" + String.format("%03d", alarmEventRepository.findAll().size() + 1))
                        .description("Device telemetry breached SLO threshold for platform operations")
                        .severity(latest.getMetricValue() >= 85.0 ? AlarmSeverity.CRITICAL : AlarmSeverity.MAJOR)
                        .status(AlarmStatus.OPEN)
                        .sourceMetric(latest.getMetricName())
                        .thresholdValue(70.0)
                        .observedValue(latest.getMetricValue())
                        .raisedAt(now)
                        .acknowledgedAt(null)
                        .resolvedAt(null)
                        .build());
            }
        }
    }
}
