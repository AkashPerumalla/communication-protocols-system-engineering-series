package com.sky2dev.day26.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alarm_events", indexes = {
    @Index(name = "idx_alarm_status", columnList = "status"),
    @Index(name = "idx_alarm_severity", columnList = "severity")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false, length = 64)
    private String code;

    @Column(nullable = false, length = 256)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AlarmSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AlarmStatus status;

    @Column(nullable = false, length = 64)
    private String sourceMetric;

    @Column(nullable = false)
    private Double thresholdValue;

    @Column(nullable = false)
    private Double observedValue;

    @Column(nullable = false)
    private LocalDateTime raisedAt;

    private LocalDateTime acknowledgedAt;

    private LocalDateTime resolvedAt;
}
