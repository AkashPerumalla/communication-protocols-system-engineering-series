package com.sky2dev.day15.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "monitoring_metrics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private Instant capturedAt;

    @Column(nullable = false)
    private Double cpuUsage;

    @Column(nullable = false)
    private Double memoryUsage;

    @Column(nullable = false)
    private Long uptime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;

    @Column(nullable = false)
    private Double temperature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterfaceStatus interfaceStatus;

    private Double rfPower;

    private Double ber;

    private String carrierLock;

    private Double frequencyGhz;

    private Double symbolRateMsps;

    private Double ebNo;

    private String modemStatus;

    private String bucStatus;

    private String lnbStatus;

    private Double uplinkPower;

    private Double downlinkPower;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricSource source;
}
