package com.sky2dev.day26.dto;

import com.sky2dev.day26.entity.DeploymentEnvironment;
import java.time.LocalDateTime;

public record DeploymentStatusResponse(
        Long id,
        String deploymentName,
        String namespaceName,
        DeploymentEnvironment environment,
        Integer desiredReplicas,
        Integer availableReplicas,
        Integer updatedReplicas,
        String strategy,
        String imageTag,
        String stableImageTag,
        String rolloutState,
        String version,
        String lastAction,
        boolean stable,
        LocalDateTime lastUpdated
) {
}
