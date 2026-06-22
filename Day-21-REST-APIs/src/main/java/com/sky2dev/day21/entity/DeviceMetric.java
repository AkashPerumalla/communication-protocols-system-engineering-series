package com.sky2dev.day21.entity;

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
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "device_metrics", indexes = {
        @Index(name = "idx_metric_device_time", columnList = "device_id,timestamp"),
        @Index(name = "idx_metric_timestamp", columnList = "timestamp")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private double cpuUsage;

    @Column(nullable = false)
    private double memoryUsage;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double signalStrength;

    @Column(nullable = false)
    private Instant timestamp;
}
