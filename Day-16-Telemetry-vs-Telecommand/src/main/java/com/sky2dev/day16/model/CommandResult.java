package com.sky2dev.day16.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "command_results")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private ManagedDevice device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "telecommand_id")
    private Telecommand telecommand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TelecommandType commandType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommandStatus status;

    @Column(nullable = false)
    private String marker;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String previousState;

    @Column(nullable = false)
    private String resultingState;

    @Column(nullable = false)
    private Instant requestedAt;

    @Column(nullable = false)
    private Instant completedAt;
}
