package com.sky2dev.day18.service;

import com.sky2dev.day18.dto.ControlCommandDto;
import com.sky2dev.day18.dto.DashboardDto;
import com.sky2dev.day18.model.AlarmSeverity;
import com.sky2dev.day18.model.AlarmStatus;
import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.ControlCommandRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final HubDeviceRepository hubDeviceRepository;
    private final AlarmRepository alarmRepository;
    private final ControlCommandRepository controlCommandRepository;

    @Transactional(readOnly = true)
    public DashboardDto getDashboard() {
        long total = hubDeviceRepository.count();
        long online = hubDeviceRepository.findByStatus(DeviceStatus.ONLINE).size();
        long offline = hubDeviceRepository.findByStatus(DeviceStatus.OFFLINE).size();
        long critical = alarmRepository.countBySeverityAndStatusNot(AlarmSeverity.CRITICAL, AlarmStatus.CLOSED);
        long major = alarmRepository.countBySeverityAndStatusNot(AlarmSeverity.MAJOR, AlarmStatus.CLOSED);
        long warning = alarmRepository.countBySeverityAndStatusNot(AlarmSeverity.WARNING, AlarmStatus.CLOSED);

        BigDecimal availability = total == 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf((online * 100.0) / total).setScale(2, RoundingMode.HALF_UP);

        List<ControlCommandDto> recentCommands = controlCommandRepository.findTop10ByOrderByExecutionTimeDesc().stream()
                .map(ControlCommandDto::from)
                .toList();

        return new DashboardDto(total, online, offline, critical, major, warning, availability, recentCommands);
    }
}
