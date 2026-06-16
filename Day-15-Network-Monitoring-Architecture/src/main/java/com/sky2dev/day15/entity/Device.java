package com.sky2dev.day15.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "devices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String hostname;

    @Column(nullable = false, unique = true)
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceCategory category;

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

    @Column(nullable = false)
    private Instant lastUpdated;
}
