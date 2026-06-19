package com.sky2dev.day18.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hub_devices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostname;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    private String location;
    private String vendor;
    private String firmwareVersion;
    private Instant lastSeen;

    @PrePersist
    void onCreate() {
        if (lastSeen == null) {
            lastSeen = Instant.now();
        }
    }
}
