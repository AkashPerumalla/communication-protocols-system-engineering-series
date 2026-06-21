package com.sky2dev.day20.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metric_records", indexes = {
        @Index(name = "idx_metric_agent_time", columnList = "agentId,timestamp"),
        @Index(name = "idx_metric_timestamp", columnList = "timestamp")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String agentId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private double cpuUsage;

    @Column(nullable = false)
    private double memoryUsage;

    @Column(nullable = false)
    private double diskUsage;

    @Column(nullable = false)
    private double networkIn;

    @Column(nullable = false)
    private double networkOut;

    @Column(nullable = false)
    private int processCount;

    @Column(nullable = false)
    private long uptimeSeconds;
}
