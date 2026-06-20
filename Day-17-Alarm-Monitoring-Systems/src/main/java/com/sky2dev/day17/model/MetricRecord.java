package com.sky2dev.day17.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ManagedDevice device;

    private Instant recordedAt;
    private Double cpuUsage;
    private Double temperatureC;
    private Double memoryUsage;
    private Double signalStrengthDbm;
    private Double ber;
    private Double ebno;
    private Boolean deviceReachable;
    private Boolean applicationHealthy;
}
