package com.sky2dev.day23.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(name = "alarm_events", indexes = {
        @Index(name = "idx_alarm_device", columnList = "deviceId"),
        @Index(name = "idx_alarm_severity", columnList = "severity"),
        @Index(name = "idx_alarm_ts", columnList = "timestamp")
})
public class AlarmEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmSeverity severity;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean acknowledged;

    @Column(nullable = false)
    private Instant timestamp;
}
