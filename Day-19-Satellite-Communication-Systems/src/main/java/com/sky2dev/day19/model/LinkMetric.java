package com.sky2dev.day19.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "link_metrics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id", nullable = false)
    private SatelliteLink link;

    private Instant timestamp;
    private Double cnDb;
    private Double ebNo;
    private Double ber;
    private Double rxPowerDbm;
    private Double txPowerDbm;

    @Enumerated(EnumType.STRING)
    private LockStatus lockStatus;

    @Enumerated(EnumType.STRING)
    private ModulationType modulation;

    private Double symbolRate;
    private Double throughputMbps;
    private Double latencyMs;
}
