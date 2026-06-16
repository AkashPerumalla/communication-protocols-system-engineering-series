package com.sky2dev.day15.service;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.DeviceCategory;
import com.sky2dev.day15.entity.MetricSource;
import com.sky2dev.day15.entity.MonitoringMetric;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class TelecomMonitoringService {

    public void enrich(Device device, MonitoringMetric metric) {
        if (device.getCategory() != DeviceCategory.HUB && !"hub-01".equals(device.getHostname().toLowerCase(Locale.ROOT))) {
            return;
        }
        metric.setRfPower(-42.0);
        metric.setBer(1.2E-6);
        metric.setCarrierLock("LOCKED");
        metric.setFrequencyGhz(14.250);
        metric.setSymbolRateMsps(45.0);
        metric.setSource(MetricSource.TELECOM);
    }
}
