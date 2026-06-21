package com.sky2dev.day20.dto;

import com.sky2dev.day20.entity.DashboardSnapshot;
import java.time.Instant;

public record DashboardSnapshotDto(
        Long id,
        Instant timestamp,
        int activeAgents,
        long activeAlerts,
        long criticalAlerts,
        double averageCpu,
        double averageMemory,
        double averageDisk) {

    public static DashboardSnapshotDto from(DashboardSnapshot snapshot) {
        return new DashboardSnapshotDto(
                snapshot.getId(),
                snapshot.getTimestamp(),
                snapshot.getActiveAgents(),
                snapshot.getActiveAlerts(),
                snapshot.getCriticalAlerts(),
                snapshot.getAverageCpu(),
                snapshot.getAverageMemory(),
                snapshot.getAverageDisk());
    }
}
