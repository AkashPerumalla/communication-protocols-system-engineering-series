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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "satellite_links")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SatelliteLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String linkName;

    @ManyToOne
    @JoinColumn(name = "source_station_id", nullable = false)
    private EarthStation sourceStation;

    @ManyToOne
    @JoinColumn(name = "destination_station_id", nullable = false)
    private EarthStation destinationStation;

    @ManyToOne
    @JoinColumn(name = "satellite_id", nullable = false)
    private Satellite satellite;

    @Enumerated(EnumType.STRING)
    private LinkStatus status;
}
