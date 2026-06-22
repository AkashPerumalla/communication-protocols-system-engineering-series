package com.sky2dev.day21.mapper;

import com.sky2dev.day21.dto.DeviceMetricResponse;
import com.sky2dev.day21.entity.DeviceMetric;
import org.springframework.stereotype.Component;

@Component
public class DeviceMetricMapper {

    public DeviceMetricResponse toResponse(DeviceMetric metric) {
        return new DeviceMetricResponse(
                metric.getId(),
                metric.getDevice().getId(),
                metric.getDevice().getHostname(),
                metric.getCpuUsage(),
                metric.getMemoryUsage(),
                metric.getTemperature(),
                metric.getSignalStrength(),
                metric.getTimestamp());
    }
}
