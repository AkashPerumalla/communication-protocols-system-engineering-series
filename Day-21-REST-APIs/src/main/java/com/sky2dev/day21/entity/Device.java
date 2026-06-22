package com.sky2dev.day21.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "devices", indexes = {
        @Index(name = "idx_device_hostname", columnList = "hostname"),
        @Index(name = "idx_device_ip", columnList = "ip_address", unique = true),
        @Index(name = "idx_device_status", columnList = "status"),
        @Index(name = "idx_device_type", columnList = "device_type"),
        @Index(name = "idx_device_location", columnList = "location")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String hostname;

    @Column(name = "ip_address", nullable = false, unique = true, length = 15)
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false, length = 30)
    private DeviceType deviceType;

    @Column(nullable = false, length = 80)
    private String vendor;

    @Column(nullable = false, length = 80)
    private String model;

    @Column(name = "firmware_version", nullable = false, length = 50)
    private String firmwareVersion;

    @Column(nullable = false, length = 120)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DeviceStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
