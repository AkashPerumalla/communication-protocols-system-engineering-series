package com.sky2dev.day16.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "managed_devices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagedDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceCode;

    @Column(nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceState state;

    @Column(nullable = false)
    private boolean interfaceEnabled;

    @Column(nullable = false)
    private boolean carrierEnabled;

    @Column(nullable = false)
    private boolean modemHealthy;

    @Column(nullable = false)
    private boolean backupLinkActive;

    @Column(nullable = false)
    private long currentFrequencyMHz;

    @Column(nullable = false)
    private long currentSymbolRateKsps;

    @Column(nullable = false)
    private double currentCpu;

    @Column(nullable = false)
    private double currentMemory;

    @Column(nullable = false)
    private double currentTemperature;

    @Column(nullable = false)
    private double currentPower;

    @Column(nullable = false)
    private String currentInterfaceStatus;

    @Column(nullable = false)
    private String currentModemStatus;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
