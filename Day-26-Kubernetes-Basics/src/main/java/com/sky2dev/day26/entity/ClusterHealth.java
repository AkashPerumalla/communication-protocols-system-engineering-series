package com.sky2dev.day26.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cluster_health")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String clusterName;

    @Column(nullable = false, length = 64)
    private String namespaceName;

    @Column(nullable = false)
    private Integer nodeCount;

    @Column(nullable = false)
    private Integer readyNodeCount;

    @Column(nullable = false)
    private Integer totalPods;

    @Column(nullable = false)
    private Integer runningPods;

    @Column(nullable = false)
    private Integer pendingPods;

    @Column(nullable = false)
    private Integer failedPods;

    @Column(nullable = false)
    private Integer serviceCount;

    @Column(nullable = false, length = 32)
    private String configMapStatus;

    @Column(nullable = false, length = 32)
    private String secretStatus;

    @Column(nullable = false, length = 32)
    private String pvcStatus;

    @Column(nullable = false, length = 32)
    private String overallStatus;

    @Column(nullable = false)
    private Double apiServerLatencyMs;

    @Column(nullable = false)
    private LocalDateTime observedAt;
}
