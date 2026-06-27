package com.sky2dev.day26.repository;

import com.sky2dev.day26.entity.DeploymentEnvironment;
import com.sky2dev.day26.entity.DeploymentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeploymentStatusRepository extends JpaRepository<DeploymentStatus, Long> {
    List<DeploymentStatus> findAllByOrderByDeploymentNameAsc();
    List<DeploymentStatus> findByEnvironmentOrderByDeploymentNameAsc(DeploymentEnvironment environment);
    Optional<DeploymentStatus> findByDeploymentName(String deploymentName);
    long countByRolloutState(String rolloutState);
}
