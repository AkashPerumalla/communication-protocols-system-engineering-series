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
@Table(name = "transponders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transponder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "satellite_id", nullable = false)
    private Satellite satellite;

    private Integer transponderNumber;
    private Double bandwidthMhz;
    private Double uplinkFrequency;
    private Double downlinkFrequency;

    @Enumerated(EnumType.STRING)
    private PolarizationType polarization;

    @Enumerated(EnumType.STRING)
    private TransponderStatus status;
}
