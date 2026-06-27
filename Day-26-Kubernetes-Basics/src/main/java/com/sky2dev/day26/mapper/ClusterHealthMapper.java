package com.sky2dev.day26.mapper;

import com.sky2dev.day26.dto.ClusterHealthResponse;
import com.sky2dev.day26.entity.ClusterHealth;
import org.springframework.stereotype.Component;

@Component
public class ClusterHealthMapper {

    public ClusterHealthResponse toResponse(ClusterHealth clusterHealth) {
        return new ClusterHealthResponse(
                clusterHealth.getId(),
                clusterHealth.getClusterName(),
                clusterHealth.getNamespaceName(),
                clusterHealth.getNodeCount(),
                clusterHealth.getReadyNodeCount(),
                clusterHealth.getTotalPods(),
                clusterHealth.getRunningPods(),
                clusterHealth.getPendingPods(),
                clusterHealth.getFailedPods(),
                clusterHealth.getServiceCount(),
                clusterHealth.getConfigMapStatus(),
                clusterHealth.getSecretStatus(),
                clusterHealth.getPvcStatus(),
                clusterHealth.getOverallStatus(),
                clusterHealth.getApiServerLatencyMs(),
                clusterHealth.getObservedAt());
    }
}
