package com.sky2dev.day26;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.sky2dev.day26.entity.DeploymentEnvironment;
import com.sky2dev.day26.entity.DeploymentStatus;
import com.sky2dev.day26.mapper.DeploymentStatusMapper;
import com.sky2dev.day26.repository.DeploymentStatusRepository;
import com.sky2dev.day26.service.DeploymentService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DeploymentServiceTest {

    @Mock
    private DeploymentStatusRepository deploymentStatusRepository;

    private DeploymentStatusMapper deploymentStatusMapper;

    @InjectMocks
    private DeploymentService deploymentService;

    @BeforeEach
    void setUp() {
        deploymentStatusMapper = new DeploymentStatusMapper();
        deploymentService = new DeploymentService(deploymentStatusRepository, deploymentStatusMapper);
        ReflectionTestUtils.setField(deploymentService, "primaryDeploymentName", "device-monitoring-platform");
    }

    @Test
    void shouldScalePrimaryDeployment() {
        DeploymentStatus deployment = DeploymentStatus.builder()
                .deploymentName("device-monitoring-platform")
                .namespaceName("day26-platform")
                .environment(DeploymentEnvironment.PRODUCTION)
                .desiredReplicas(3)
                .availableReplicas(3)
                .updatedReplicas(3)
                .strategy("RollingUpdate")
                .imageTag("1.0.0")
                .stableImageTag("1.0.0")
                .rolloutState("RUNNING")
                .version("v1")
                .lastAction("Seed")
                .stable(true)
                .lastUpdated(LocalDateTime.of(2026, 6, 27, 9, 0))
                .build();
        when(deploymentStatusRepository.findByDeploymentName("device-monitoring-platform")).thenReturn(Optional.of(deployment));
        when(deploymentStatusRepository.save(deployment)).thenReturn(deployment);

        var response = deploymentService.scalePrimaryDeployment(4);

        assertThat(response.desiredReplicas()).isEqualTo(4);
        assertThat(response.rolloutState()).isEqualTo("SCALING");
    }
}
