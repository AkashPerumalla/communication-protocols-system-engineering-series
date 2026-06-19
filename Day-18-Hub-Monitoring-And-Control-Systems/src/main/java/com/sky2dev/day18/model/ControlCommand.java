package com.sky2dev.day18.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "control_commands")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private HubDevice device;

    @Enumerated(EnumType.STRING)
    private CommandType commandType;

    private String commandPayload;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus executionStatus;

    private Instant executionTime;
    private String executedBy;

    @PrePersist
    void onCreate() {
        if (executionTime == null) {
            executionTime = Instant.now();
        }
    }
}
