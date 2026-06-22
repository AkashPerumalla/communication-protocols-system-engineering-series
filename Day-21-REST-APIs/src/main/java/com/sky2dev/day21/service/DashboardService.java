package com.sky2dev.day21.service;

import com.sky2dev.day21.dto.DashboardResponse;
import com.sky2dev.day21.entity.DeviceStatus;
import com.sky2dev.day21.entity.DeviceType;
import com.sky2dev.day21.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final DeviceRepository deviceRepository;
    private final MetricService metricService;

    public DashboardResponse getDashboard() {
        return new DashboardResponse(
                deviceRepository.count(),
                deviceRepository.countByStatus(DeviceStatus.ONLINE),
                deviceRepository.countByStatus(DeviceStatus.OFFLINE),
                deviceRepository.countByStatus(DeviceStatus.WARNING),
                deviceRepository.countByStatus(DeviceStatus.MAINTENANCE),
                deviceRepository.countByDeviceType(DeviceType.ROUTER),
                deviceRepository.countByDeviceType(DeviceType.SWITCH),
                deviceRepository.countByDeviceType(DeviceType.MODEM),
                deviceRepository.countByDeviceType(DeviceType.HUB),
                deviceRepository.countByDeviceType(DeviceType.SENSOR),
                metricService.averageCpuUsage(),
                metricService.averageMemoryUsage());
    }
}
