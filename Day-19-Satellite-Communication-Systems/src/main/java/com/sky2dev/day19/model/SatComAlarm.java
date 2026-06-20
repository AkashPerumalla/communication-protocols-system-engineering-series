package com.sky2dev.day19.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "satcom_alarms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SatComAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmSeverity severity;

    private String alarmType;
    private String source;
    private String message;

    @Enumerated(EnumType.STRING)
    private AlarmStatus status;

    private Instant timestamp;
}
