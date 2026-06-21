package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.MetricRecordDto;
import com.sky2dev.day20.entity.MetricRecord;
import com.sky2dev.day20.entity.MonitoringAgent;
import com.sky2dev.day20.repository.MetricRecordRepository;
import com.sky2dev.day20.repository.MonitoringAgentRepository;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsCollectionService {

    private final MetricRecordRepository metricRecordRepository;
    private final MonitoringAgentRepository monitoringAgentRepository;
    private final AtomicLong tick = new AtomicLong(0);
    private final Map<String, Long> uptimeByAgent = new HashMap<>();

    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    public void scheduledCollect() {
        collectMetrics();
    }

    public List<MetricRecordDto> collectMetrics() {
        long localTick = tick.incrementAndGet();
        List<MonitoringAgent> agents = monitoringAgentRepository.findAll();
        for (int index = 0; index < agents.size(); index++) {
            MonitoringAgent agent = agents.get(index);
            long uptime = uptimeByAgent.getOrDefault(agent.getAgentId(), 3600L + index * 900);
            uptime += 5;
            uptimeByAgent.put(agent.getAgentId(), uptime);

            double cpu = bounded(20 + index * 10 + (localTick % 9) * 3.5, 5, 98);
            double memory = bounded(35 + index * 8 + (localTick % 7) * 2.8, 10, 98);
            double disk = bounded(30 + index * 12 + (localTick % 5) * 1.9, 12, 99);
            double networkIn = bounded(80 + index * 75 + (localTick % 11) * 14.0, 20, 1200);
            double networkOut = bounded(50 + index * 55 + (localTick % 11) * 11.5, 10, 1000);
            int processCount = (int) bounded(90 + index * 15 + (localTick % 13) * 2, 60, 380);

            MetricRecord record = MetricRecord.builder()
                    .agentId(agent.getAgentId())
                    .timestamp(Instant.now())
                    .cpuUsage(cpu)
                    .memoryUsage(memory)
                    .diskUsage(disk)
                    .networkIn(networkIn)
                    .networkOut(networkOut)
                    .processCount(processCount)
                    .uptimeSeconds(uptime)
                    .build();
            metricRecordRepository.save(record);
        }
        return listRecentMetrics();
    }

    public List<MetricRecordDto> listRecentMetrics() {
        return metricRecordRepository.findTop100ByOrderByTimestampDesc().stream().map(MetricRecordDto::from).toList();
    }

    public Map<String, MetricRecord> latestByAgent() {
        Map<String, MetricRecord> latest = new HashMap<>();
        List<MonitoringAgent> agents = monitoringAgentRepository.findAll();
        for (MonitoringAgent agent : agents) {
            metricRecordRepository.findTop1ByAgentIdOrderByTimestampDesc(agent.getAgentId())
                    .ifPresent(metric -> latest.put(agent.getAgentId(), metric));
        }
        return latest;
    }

    private double bounded(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
