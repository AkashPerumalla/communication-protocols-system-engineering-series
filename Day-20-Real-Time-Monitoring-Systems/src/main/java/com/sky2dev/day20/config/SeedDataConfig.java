package com.sky2dev.day20.config;

import com.sky2dev.day20.entity.AgentStatus;
import com.sky2dev.day20.entity.AlertEvent;
import com.sky2dev.day20.entity.AlertRule;
import com.sky2dev.day20.entity.AlertSeverity;
import com.sky2dev.day20.entity.AlertStatus;
import com.sky2dev.day20.entity.ComparatorType;
import com.sky2dev.day20.entity.DashboardSnapshot;
import com.sky2dev.day20.entity.MetricRecord;
import com.sky2dev.day20.entity.MonitoringAgent;
import com.sky2dev.day20.entity.NotificationChannel;
import com.sky2dev.day20.entity.NotificationEvent;
import com.sky2dev.day20.entity.NotificationStatus;
import com.sky2dev.day20.repository.AlertEventRepository;
import com.sky2dev.day20.repository.AlertRuleRepository;
import com.sky2dev.day20.repository.DashboardSnapshotRepository;
import com.sky2dev.day20.repository.MetricRecordRepository;
import com.sky2dev.day20.repository.MonitoringAgentRepository;
import com.sky2dev.day20.repository.NotificationEventRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SeedDataConfig {

    private final MonitoringAgentRepository monitoringAgentRepository;
    private final MetricRecordRepository metricRecordRepository;
    private final AlertRuleRepository alertRuleRepository;
    private final AlertEventRepository alertEventRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final DashboardSnapshotRepository dashboardSnapshotRepository;

    @Bean
    CommandLineRunner seedDay20Data() {
        return args -> {
            if (monitoringAgentRepository.count() > 0) {
                return;
            }

            List<MonitoringAgent> agents = List.of(
                    MonitoringAgent.builder().agentId("agent-01").hostname("app-server-01").ipAddress("10.0.10.11").location("NOC-A").status(AgentStatus.ONLINE).lastSeen(Instant.now()).build(),
                    MonitoringAgent.builder().agentId("agent-02").hostname("db-server-01").ipAddress("10.0.10.21").location("NOC-A").status(AgentStatus.ONLINE).lastSeen(Instant.now()).build(),
                    MonitoringAgent.builder().agentId("agent-03").hostname("api-server-01").ipAddress("10.0.10.31").location("NOC-B").status(AgentStatus.DEGRADED).lastSeen(Instant.now()).build(),
                    MonitoringAgent.builder().agentId("agent-04").hostname("cache-server-01").ipAddress("10.0.10.41").location("NOC-B").status(AgentStatus.ONLINE).lastSeen(Instant.now()).build(),
                    MonitoringAgent.builder().agentId("agent-05").hostname("monitoring-node-01").ipAddress("10.0.10.51").location("Core-DC").status(AgentStatus.ONLINE).lastSeen(Instant.now()).build());
            monitoringAgentRepository.saveAll(agents);

            List<AlertRule> rules = List.of(
                    AlertRule.builder().metricName("cpuUsage").comparator(ComparatorType.GT).threshold(80).severity(AlertSeverity.HIGH).enabled(true).build(),
                    AlertRule.builder().metricName("memoryUsage").comparator(ComparatorType.GT).threshold(75).severity(AlertSeverity.MEDIUM).enabled(true).build(),
                    AlertRule.builder().metricName("diskUsage").comparator(ComparatorType.GT).threshold(90).severity(AlertSeverity.CRITICAL).enabled(true).build(),
                    AlertRule.builder().metricName("networkIn").comparator(ComparatorType.GT).threshold(500).severity(AlertSeverity.HIGH).enabled(true).build());
            alertRuleRepository.saveAll(rules);

            List<MetricRecord> metrics = new ArrayList<>();
            double[] cpuPattern = {23, 61, 87, 42, 79};
            double[] memPattern = {45, 74, 91, 58, 69};
            double[] diskPattern = {35, 67, 95, 50, 84};
            for (int i = 0; i < 50; i++) {
                int idx = i % agents.size();
                metrics.add(MetricRecord.builder()
                        .agentId(agents.get(idx).getAgentId())
                        .timestamp(Instant.now().minusSeconds((50L - i) * 5L))
                        .cpuUsage(cpuPattern[idx] + (i % 3))
                        .memoryUsage(memPattern[idx] + (i % 2))
                        .diskUsage(diskPattern[idx] + (i % 2))
                        .networkIn(120 + idx * 130 + (i % 5) * 25)
                        .networkOut(100 + idx * 110 + (i % 4) * 22)
                        .processCount(120 + idx * 15 + (i % 6))
                        .uptimeSeconds(7200 + i * 10L)
                        .build());
            }
            metricRecordRepository.saveAll(metrics);

            List<AlertEvent> alerts = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                alerts.add(AlertEvent.builder()
                        .alertId((long) ((i % 4) + 1))
                        .metricName(i % 2 == 0 ? "cpuUsage" : "diskUsage")
                        .metricValue(i % 2 == 0 ? 82 + i : 91 + i)
                        .threshold(i % 2 == 0 ? 80 : 90)
                        .severity(i < 4 ? AlertSeverity.MEDIUM : (i < 7 ? AlertSeverity.HIGH : AlertSeverity.CRITICAL))
                        .message("Seeded alert event #" + (i + 1))
                        .timestamp(Instant.now().minusSeconds(i * 60L))
                        .status(i < 8 ? AlertStatus.ACTIVE : AlertStatus.ACKNOWLEDGED)
                        .build());
            }
            alertEventRepository.saveAll(alerts);

            List<NotificationEvent> notifications = new ArrayList<>();
            NotificationChannel[] channels = NotificationChannel.values();
            for (int i = 0; i < 20; i++) {
                notifications.add(NotificationEvent.builder()
                        .channel(channels[i % channels.length])
                        .recipient("ops-team-" + ((i % 4) + 1) + "@noc.example")
                        .message("Seed notification " + (i + 1))
                        .status(NotificationStatus.SENT)
                        .timestamp(Instant.now().minusSeconds(i * 45L))
                        .build());
            }
            notificationEventRepository.saveAll(notifications);

            dashboardSnapshotRepository.save(DashboardSnapshot.builder()
                    .timestamp(Instant.now())
                    .activeAgents(4)
                    .activeAlerts(8)
                    .criticalAlerts(3)
                    .averageCpu(58.4)
                    .averageMemory(66.6)
                    .averageDisk(70.2)
                    .build());
        };
    }
}
