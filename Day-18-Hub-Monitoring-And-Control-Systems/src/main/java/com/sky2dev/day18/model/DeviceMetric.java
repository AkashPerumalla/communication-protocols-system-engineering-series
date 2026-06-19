package com.sky2dev.day18.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "device_metrics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private HubDevice device;

    private BigDecimal cpuUsage;
    private BigDecimal memoryUsage;
    private BigDecimal temperature;
    private BigDecimal signalStrength;
    private BigDecimal powerLevel;

    @Enumerated(EnumType.STRING)
    private InterfaceStatus interfaceStatus;

    private Instant timestamp;

    private BigDecimal ebNo;
    private BigDecimal ber;
    private Boolean carrierLock;
    private BigDecimal frequencyMhz;
    private BigDecimal symbolRateKsps;

    private BigDecimal bucOutputPower;
    private BigDecimal bucTemperature;
    private String bucStatus;

    private BigDecimal lnbGain;
    private BigDecimal lnbNoiseFigure;
    private String lnbStatus;

    private BigDecimal upsBatteryPercent;
    private BigDecimal upsInputVoltage;
    private BigDecimal upsOutputVoltage;

    private BigDecimal powerCurrent;
    private BigDecimal powerVoltage;
    private BigDecimal powerConsumption;

    @PrePersist
    void onCreate() {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}
