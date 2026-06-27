package com.sky2dev.day26.mapper;

import com.sky2dev.day26.dto.DeploymentStatusResponse;
import com.sky2dev.day26.entity.DeploymentStatus;
import org.springframework.stereotype.Component;

@Component
public class DeploymentStatusMapper {

    public DeploymentStatusResponse toResponse(DeploymentStatus deploymentStatus) {
        return new DeploymentStatusResponse(
                deploymentStatus.getId(),
                deploymentStatus.getDeploymentName(),
                deploymentStatus.getNamespaceName(),
                deploymentStatus.getEnvironment(),
                deploymentStatus.getDesiredReplicas(),
                deploymentStatus.getAvailableReplicas(),
                deploymentStatus.getUpdatedReplicas(),
                deploymentStatus.getStrategy(),
                deploymentStatus.getImageTag(),
                deploymentStatus.getStableImageTag(),
                deploymentStatus.getRolloutState(),
                deploymentStatus.getVersion(),
                deploymentStatus.getLastAction(),
                deploymentStatus.isStable(),
                deploymentStatus.getLastUpdated());
    }
}
