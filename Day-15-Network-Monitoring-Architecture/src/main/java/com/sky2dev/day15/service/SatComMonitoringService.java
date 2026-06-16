package com.sky2dev.day15.service;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.DeviceCategory;
import com.sky2dev.day15.entity.MetricSource;
import com.sky2dev.day15.entity.MonitoringMetric;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class SatComMonitoringService {

    public void enrich(Device device, MonitoringMetric metric) {
        if (device.getCategory() != DeviceCategory.MODEM && !"modem-01".equals(device.getHostname().toLowerCase(Locale.ROOT))) {
            return;
        }
        metric.setEbNo(7.8);
        metric.setModemStatus("ONLINE");
        metric.setBucStatus("READY");
        metric.setLnbStatus("READY");
        metric.setUplinkPower(3.2);
        metric.setDownlinkPower(-18.5);
        metric.setSource(MetricSource.SATCOM);
    }
}
