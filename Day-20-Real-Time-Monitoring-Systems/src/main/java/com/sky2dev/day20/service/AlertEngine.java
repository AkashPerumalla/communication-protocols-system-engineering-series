package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.AlertEventDto;
import com.sky2dev.day20.entity.AlertEvent;
import com.sky2dev.day20.entity.AlertRule;
import com.sky2dev.day20.entity.AlertStatus;
import com.sky2dev.day20.entity.MetricRecord;
import com.sky2dev.day20.repository.AlertEventRepository;
import com.sky2dev.day20.repository.AlertRuleRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertEngine {

    private final AlertRuleRepository alertRuleRepository;
    private final AlertEventRepository alertEventRepository;
    private final MetricsCollectionService metricsCollectionService;

    @Scheduled(fixedDelay = 7000, initialDelay = 3000)
    public void scheduledEvaluate() {
        evaluateThresholds(null);
    }

    public List<AlertEventDto> evaluateThresholds(String agentFilter) {
        Map<String, MetricRecord> latestMetrics = metricsCollectionService.latestByAgent();
        List<AlertEventDto> created = new ArrayList<>();

        for (AlertRule rule : alertRuleRepository.findByEnabledTrue()) {
            for (Map.Entry<String, MetricRecord> entry : latestMetrics.entrySet()) {
                if (agentFilter != null && !agentFilter.equals(entry.getKey())) {
                    continue;
                }

                MetricRecord metric = entry.getValue();
                double metricValue = extractMetricValue(metric, rule.getMetricName());
                if (rule.getComparator().evaluate(metricValue, rule.getThreshold())) {
                    boolean activeExists = alertEventRepository
                            .findTop1ByAlertIdAndMetricNameAndStatusOrderByTimestampDesc(
                                    rule.getId(), rule.getMetricName(), AlertStatus.ACTIVE)
                            .isPresent();
                    if (!activeExists) {
                        AlertEvent event = AlertEvent.builder()
                                .alertId(rule.getId())
                                .metricName(rule.getMetricName())
                                .metricValue(metricValue)
                                .threshold(rule.getThreshold())
                                .severity(rule.getSeverity())
                                .message("Threshold breach for " + rule.getMetricName() + " on " + entry.getKey())
                                .timestamp(Instant.now())
                                .status(AlertStatus.ACTIVE)
                                .build();
                        created.add(AlertEventDto.from(alertEventRepository.save(event)));
                    }
                }
            }
        }

        return created;
    }

    public List<AlertEventDto> listAlerts() {
        return alertEventRepository.findAll().stream().map(AlertEventDto::from).toList();
    }

    public List<AlertEventDto> listActiveAlerts() {
        return alertEventRepository.findByStatusOrderByTimestampDesc(AlertStatus.ACTIVE).stream()
                .map(AlertEventDto::from)
                .toList();
    }

    private double extractMetricValue(MetricRecord metric, String metricName) {
        return switch (metricName) {
            case "cpuUsage" -> metric.getCpuUsage();
            case "memoryUsage" -> metric.getMemoryUsage();
            case "diskUsage" -> metric.getDiskUsage();
            case "networkIn" -> metric.getNetworkIn();
            case "networkOut" -> metric.getNetworkOut();
            case "processCount" -> metric.getProcessCount();
            default -> 0.0;
        };
    }
}
