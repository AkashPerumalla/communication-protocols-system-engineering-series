package com.sky2dev.day26.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "devices", indexes = {
    @Index(name = "idx_device_status", columnList = "status"),
    @Index(name = "idx_device_type", columnList = "deviceType"),
    @Index(name = "idx_device_region", columnList = "region")
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

    @Column(nullable = false, unique = true, length = 64)
    private String name;

    @Column(nullable = false, unique = true, length = 64)
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DeviceStatus status;

    @Column(nullable = false, length = 64)
    private String region;

    @Column(nullable = false, length = 64)
    private String clusterZone;

    @Column(nullable = false, length = 32)
    private String firmwareVersion;

    @Column(nullable = false)
    private LocalDateTime lastSeen;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
