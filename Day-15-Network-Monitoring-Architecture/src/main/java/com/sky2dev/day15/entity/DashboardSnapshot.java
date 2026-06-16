package com.sky2dev.day15.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "dashboard_snapshots")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant generatedAt;

    @Column(nullable = false)
    private Long totalDevices;

    @Column(nullable = false)
    private Long onlineDevices;

    @Column(nullable = false)
    private Long offlineDevices;

    @Column(nullable = false)
    private Long activeAlerts;

    @Column(nullable = false)
    private Long criticalAlerts;

    @Column(nullable = false)
    private Double averageCpu;

    @Column(nullable = false)
    private Double averageMemory;
}
