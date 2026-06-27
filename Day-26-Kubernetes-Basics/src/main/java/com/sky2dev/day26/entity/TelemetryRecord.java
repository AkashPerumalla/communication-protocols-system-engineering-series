package com.sky2dev.day26.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "telemetry_records", indexes = {
    @Index(name = "idx_telemetry_collected_at", columnList = "collectedAt"),
    @Index(name = "idx_telemetry_metric_name", columnList = "metricName")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false, length = 64)
    private String metricName;

    @Column(nullable = false)
    private Double metricValue;

    @Column(nullable = false, length = 16)
    private String unit;

    @Column(nullable = false)
    private Integer qualityScore;

    @Column(nullable = false, length = 32)
    private String ingestionSource;

    @Column(nullable = false)
    private LocalDateTime collectedAt;
}
