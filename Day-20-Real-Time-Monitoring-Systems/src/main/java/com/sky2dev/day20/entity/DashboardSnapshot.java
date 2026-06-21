package com.sky2dev.day20.entity;

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
    private Instant timestamp;

    @Column(nullable = false)
    private int activeAgents;

    @Column(nullable = false)
    private long activeAlerts;

    @Column(nullable = false)
    private long criticalAlerts;

    @Column(nullable = false)
    private double averageCpu;

    @Column(nullable = false)
    private double averageMemory;

    @Column(nullable = false)
    private double averageDisk;
}
