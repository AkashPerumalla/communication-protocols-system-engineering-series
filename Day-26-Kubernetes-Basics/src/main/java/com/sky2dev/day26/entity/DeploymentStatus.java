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
@Table(name = "deployment_statuses", indexes = {
        @Index(name = "idx_deployment_environment", columnList = "environment"),
        @Index(name = "idx_deployment_rollout_state", columnList = "rolloutState")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String deploymentName;

    @Column(nullable = false, length = 64)
    private String namespaceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DeploymentEnvironment environment;

    @Column(nullable = false)
    private Integer desiredReplicas;

    @Column(nullable = false)
    private Integer availableReplicas;

    @Column(nullable = false)
    private Integer updatedReplicas;

    @Column(nullable = false, length = 32)
    private String strategy;

    @Column(nullable = false, length = 64)
    private String imageTag;

    @Column(nullable = false, length = 64)
    private String stableImageTag;

    @Column(nullable = false, length = 64)
    private String rolloutState;

    @Column(nullable = false, length = 32)
    private String version;

    @Column(nullable = false, length = 64)
    private String lastAction;

    @Column(nullable = false)
    private boolean stable;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}
