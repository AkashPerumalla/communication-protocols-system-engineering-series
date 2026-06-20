package com.sky2dev.day19.model;

import jakarta.persistence.Entity;
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
@Table(name = "frequency_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrequencyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transponder_id", nullable = false)
    private Transponder transponder;

    private String channelName;
    private Double uplinkFrequency;
    private Double downlinkFrequency;
    private Double bandwidth;
    private Double guardBand;
}
