package com.sky2dev.day17.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ManagedDevice device;

    @ManyToOne
    private MetricRecord metricRecord;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    private AlarmSeverity severity;

    @Enumerated(EnumType.STRING)
    private AlarmState state;

    private String message;
    private String correlationKey;
    private String rootCauseName;
    private boolean acknowledged;
    private boolean escalated;
    private Instant detectedAt;
    private Instant openedAt;
    private Instant acknowledgedAt;
    private Instant escalatedAt;
    private Instant resolvedAt;
    private Instant closedAt;
}
