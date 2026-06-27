package com.sky2dev.day26.dto;

import java.time.LocalDateTime;

public record ClusterHealthResponse(
        Long id,
        String clusterName,
        String namespaceName,
        Integer nodeCount,
        Integer readyNodeCount,
        Integer totalPods,
        Integer runningPods,
        Integer pendingPods,
        Integer failedPods,
        Integer serviceCount,
        String configMapStatus,
        String secretStatus,
        String pvcStatus,
        String overallStatus,
        Double apiServerLatencyMs,
        LocalDateTime observedAt
) {
}
