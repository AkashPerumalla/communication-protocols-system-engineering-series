package com.sky2dev.day17.service;

import com.sky2dev.day17.model.ManagedDevice;
import com.sky2dev.day17.model.MetricRecord;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class TelemetryGeneratorService {

    private final AtomicInteger sequence = new AtomicInteger();

    public List<MetricRecord> generateMetrics(List<ManagedDevice> devices) {
        List<MetricRecord> metrics = new ArrayList<>();
        for (ManagedDevice device : devices) {
            int offset = sequence.incrementAndGet();
            metrics.add(MetricRecord.builder()
                    .device(device)
                    .recordedAt(Instant.parse("2026-06-18T10:00:00Z").plusSeconds(offset))
                    .cpuUsage(device.getName().equals("Core-Router-01") ? 96.0 : 72.0)
                    .temperatureC(device.isSatcom() ? 76.0 : 64.0)
                    .memoryUsage(device.getName().contains("DB") ? 94.0 : 68.0)
                    .signalStrengthDbm(device.isSatcom() ? -84.0 : -70.0)
                    .ber(device.isSatcom() ? 0.031 : 0.002)
                    .ebno(device.isSatcom() ? 5.2 : 9.5)
                    .deviceReachable(!device.getName().equals("Power-Unit-01"))
                    .applicationHealthy(!device.getName().equals("App-Server-01"))
                    .build());
        }
        return metrics;
    }
}
