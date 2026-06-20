package com.sky2dev.day19.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "earth_stations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarthStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    @Enumerated(EnumType.STRING)
    private StationType type;

    private Double latitude;
    private Double longitude;
    private Double antennaSize;

    @Enumerated(EnumType.STRING)
    private StationStatus status;
}
