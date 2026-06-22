package com.sky2dev.day21.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_audit_logs", indexes = {
        @Index(name = "idx_audit_timestamp", columnList = "timestamp"),
        @Index(name = "idx_audit_endpoint", columnList = "endpoint")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String endpoint;

    @Column(nullable = false, length = 10)
    private String method;

    @Column(name = "response_code", nullable = false)
    private int responseCode;

    @Column(name = "execution_time", nullable = false)
    private long executionTime;

    @Column(nullable = false)
    private Instant timestamp;

    @PrePersist
    void onPersist() {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}
