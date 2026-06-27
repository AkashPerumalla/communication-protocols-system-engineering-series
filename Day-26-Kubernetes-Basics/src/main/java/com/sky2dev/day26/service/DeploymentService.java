package com.sky2dev.day26.service;

import com.sky2dev.day26.dto.DeploymentStatusResponse;
import com.sky2dev.day26.entity.DeploymentEnvironment;
import com.sky2dev.day26.entity.DeploymentStatus;
import com.sky2dev.day26.exception.ResourceNotFoundException;
import com.sky2dev.day26.mapper.DeploymentStatusMapper;
import com.sky2dev.day26.repository.DeploymentStatusRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeploymentService {

    private final DeploymentStatusRepository deploymentStatusRepository;
    private final DeploymentStatusMapper deploymentStatusMapper;

    @Value("${app.deployment.primary-name}")
    private String primaryDeploymentName;

    @Transactional(readOnly = true)
    public List<DeploymentStatusResponse> getDeployments() {
        return deploymentStatusRepository.findAllByOrderByDeploymentNameAsc().stream()
                .map(deploymentStatusMapper::toResponse)
                .toList();
    }

    @Transactional
    public DeploymentStatusResponse scalePrimaryDeployment(int replicas) {
        if (replicas < 1 || replicas > 10) {
            throw new IllegalArgumentException("Replica count must be between 1 and 10");
        }
        DeploymentStatus deployment = getPrimaryDeployment();
        deployment.setDesiredReplicas(replicas);
        deployment.setAvailableReplicas(Math.min(deployment.getAvailableReplicas(), replicas));
        deployment.setUpdatedReplicas(Math.min(deployment.getUpdatedReplicas(), replicas));
        deployment.setRolloutState("SCALING");
        deployment.setLastAction("Manual scale to " + replicas + " replicas");
        deployment.setStable(false);
        deployment.setLastUpdated(LocalDateTime.now().withNano(0));
        return deploymentStatusMapper.toResponse(deploymentStatusRepository.save(deployment));
    }

    @Transactional
    public DeploymentStatusResponse startRollingUpdate() {
        DeploymentStatus deployment = getPrimaryDeployment();
        String currentTag = deployment.getImageTag();
        int nextMinor = Integer.parseInt(currentTag.substring(currentTag.lastIndexOf('.') + 1)) + 1;
        deployment.setStableImageTag(currentTag);
        deployment.setImageTag("1.0." + nextMinor);
        deployment.setVersion("v" + (nextMinor + 1));
        deployment.setUpdatedReplicas(0);
        deployment.setRolloutState("ROLLING_UPDATE_IN_PROGRESS");
        deployment.setLastAction("Rolling update started for " + deployment.getDeploymentName());
        deployment.setStable(false);
        deployment.setLastUpdated(LocalDateTime.now().withNano(0));
        return deploymentStatusMapper.toResponse(deploymentStatusRepository.save(deployment));
    }

    @Transactional
    public DeploymentStatusResponse rollbackPrimaryDeployment() {
        DeploymentStatus deployment = getPrimaryDeployment();
        String rollbackTarget = deployment.getStableImageTag();
        deployment.setImageTag(rollbackTarget);
        deployment.setAvailableReplicas(deployment.getDesiredReplicas());
        deployment.setUpdatedReplicas(deployment.getDesiredReplicas());
        deployment.setRolloutState("ROLLED_BACK");
        deployment.setLastAction("Rollback completed to image " + rollbackTarget);
        deployment.setStable(true);
        deployment.setLastUpdated(LocalDateTime.now().withNano(0));
        return deploymentStatusMapper.toResponse(deploymentStatusRepository.save(deployment));
    }

    @Transactional
    public void advanceDeploymentSimulation() {
        List<DeploymentStatus> deployments = deploymentStatusRepository.findAllByOrderByDeploymentNameAsc();
        for (DeploymentStatus deployment : deployments) {
            if ("ROLLING_UPDATE_IN_PROGRESS".equals(deployment.getRolloutState()) || "SCALING".equals(deployment.getRolloutState())) {
                int nextUpdated = Math.min(deployment.getDesiredReplicas(), deployment.getUpdatedReplicas() + 1);
                deployment.setUpdatedReplicas(nextUpdated);
                deployment.setAvailableReplicas(nextUpdated);
                if (nextUpdated == deployment.getDesiredReplicas()) {
                    deployment.setRolloutState("RUNNING");
                    deployment.setStable(true);
                }
                deployment.setLastUpdated(LocalDateTime.now().withNano(0));
                deploymentStatusRepository.save(deployment);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<DeploymentStatus> getDeploymentEntities() {
        return deploymentStatusRepository.findAllByOrderByDeploymentNameAsc();
    }

    @Transactional(readOnly = true)
    public List<DeploymentStatus> getProductionDeployments() {
        return deploymentStatusRepository.findByEnvironmentOrderByDeploymentNameAsc(DeploymentEnvironment.PRODUCTION);
    }

    private DeploymentStatus getPrimaryDeployment() {
        return deploymentStatusRepository.findByDeploymentName(primaryDeploymentName)
                .orElseThrow(() -> new ResourceNotFoundException("Primary deployment not found: " + primaryDeploymentName));
    }
}
