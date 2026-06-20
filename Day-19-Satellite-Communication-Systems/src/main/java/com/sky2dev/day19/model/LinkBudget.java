package com.sky2dev.day19.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "link_budgets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String linkName;
    private Double eirp;
    private Double antennaGain;
    private Double pathLoss;
    private Double gOverT;
    private Double noiseTemperature;
    private Double carrierPower;
    private Double cnRatio;
    private Double linkMargin;
}
