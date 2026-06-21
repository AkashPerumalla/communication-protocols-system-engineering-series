package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.SnmpMetricDto;
import com.sky2dev.day20.entity.MetricRecord;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SNMPMonitoringService {

    private final MetricsCollectionService metricsCollectionService;

    public List<SnmpMetricDto> collectSnmpMetrics() {
        Map<String, MetricRecord> latest = metricsCollectionService.latestByAgent();
        List<SnmpMetricDto> result = new ArrayList<>();
        Instant now = Instant.now();

        for (Map.Entry<String, MetricRecord> entry : latest.entrySet()) {
            MetricRecord m = entry.getValue();
            String agentId = entry.getKey();
            result.add(new SnmpMetricDto(agentId, ".1.3.6.1.2.1.1.3.0", "systemUptime", m.getUptimeSeconds(), now));
            result.add(new SnmpMetricDto(agentId, ".1.3.6.1.4.1.2021.11.9.0", "cpuUsage", m.getCpuUsage(), now));
            result.add(new SnmpMetricDto(agentId, ".1.3.6.1.4.1.2021.4.6.0", "memoryUsage", m.getMemoryUsage(), now));
            result.add(new SnmpMetricDto(agentId, ".1.3.6.1.2.1.2.2.1.10", "interfaceIn", m.getNetworkIn(), now));
            result.add(new SnmpMetricDto(agentId, ".1.3.6.1.2.1.2.2.1.16", "interfaceOut", m.getNetworkOut(), now));
        }
        return result;
    }
}
