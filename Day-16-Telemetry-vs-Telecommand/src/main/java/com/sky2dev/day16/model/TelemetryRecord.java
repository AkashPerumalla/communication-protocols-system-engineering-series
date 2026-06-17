package com.sky2dev.day16.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "telemetry_records")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private ManagedDevice device;

    @Column(nullable = false)
    private Instant collectedAt;

    @Column(nullable = false)
    private double cpu;

    @Column(nullable = false)
    private double memory;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double power;

    @Column(nullable = false)
    private String interfaceStatus;

    private Double rfPower;

    private Double ber;

    private Boolean carrierLock;

    private Long frequencyMHz;

    private Long symbolRateKsps;

    private Double ebNo;

    private String bucStatus;

    private String lnbStatus;

    private String modemStatus;

    private Double uplinkPower;

    private Double downlinkPower;

    @Column(nullable = false)
    private String sourceScenario;
}
