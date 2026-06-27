package com.sky2dev.day26.service;

import com.sky2dev.day26.dto.ClusterHealthResponse;
import com.sky2dev.day26.entity.ClusterHealth;
import com.sky2dev.day26.entity.DeviceStatus;
import com.sky2dev.day26.mapper.ClusterHealthMapper;
import com.sky2dev.day26.repository.ClusterHealthRepository;
import com.sky2dev.day26.repository.DeviceRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClusterHealthService {

    private final ClusterHealthRepository clusterHealthRepository;
    private final DeviceRepository deviceRepository;
    private final DeploymentService deploymentService;
    private final ClusterHealthMapper clusterHealthMapper;

    @Value("${app.cluster.name}")
    private String clusterName;

    @Value("${app.cluster.namespace}")
    private String namespace;

    @Value("${app.cluster.node-count}")
    private int nodeCount;

    @Transactional(readOnly = true)
    public ClusterHealthResponse getCurrentHealth() {
        ClusterHealth current = clusterHealthRepository.findTop1ByOrderByObservedAtDesc()
                .orElseGet(this::buildInitialHealth);
        return clusterHealthMapper.toResponse(current);
    }

    @Transactional
    public ClusterHealthResponse recomputeClusterHealth() {
        int desiredPods = (int) deploymentService.getDeploymentEntities().stream()
                .mapToLong(deployment -> deployment.getDesiredReplicas())
                .sum();
        int runningPods = (int) deploymentService.getDeploymentEntities().stream()
                .mapToLong(deployment -> deployment.getAvailableReplicas())
                .sum();
        int pendingPods = Math.max(0, desiredPods - runningPods);
        int failedPods = (int) deviceRepository.countByStatus(DeviceStatus.OFFLINE);
        int readyNodes = pendingPods > 2 ? Math.max(1, nodeCount - 1) : nodeCount;

        ClusterHealth latest = clusterHealthRepository.findTop1ByOrderByObservedAtDesc()
                .orElseGet(this::buildInitialHealth);
        latest.setClusterName(clusterName);
        latest.setNamespaceName(namespace);
        latest.setNodeCount(nodeCount);
        latest.setReadyNodeCount(readyNodes);
        latest.setTotalPods(desiredPods);
        latest.setRunningPods(runningPods);
        latest.setPendingPods(pendingPods);
        latest.setFailedPods(failedPods);
        latest.setServiceCount(4);
        latest.setConfigMapStatus("LOADED");
        latest.setSecretStatus("LOADED");
        latest.setPvcStatus("BOUND");
        latest.setOverallStatus(pendingPods > 0 || failedPods > 0 ? "DEGRADED" : "HEALTHY");
        latest.setApiServerLatencyMs(8.0 + pendingPods * 4.0 + failedPods * 2.0);
        latest.setObservedAt(LocalDateTime.now().withNano(0));
        return clusterHealthMapper.toResponse(clusterHealthRepository.save(latest));
    }

    private ClusterHealth buildInitialHealth() {
        return ClusterHealth.builder()
                .clusterName(clusterName)
                .namespaceName(namespace)
                .nodeCount(nodeCount)
                .readyNodeCount(nodeCount)
                .totalPods(0)
                .runningPods(0)
                .pendingPods(0)
                .failedPods(0)
                .serviceCount(0)
                .configMapStatus("LOADED")
                .secretStatus("LOADED")
                .pvcStatus("BOUND")
                .overallStatus("HEALTHY")
                .apiServerLatencyMs(7.5)
                .observedAt(LocalDateTime.now().withNano(0))
                .build();
    }
}
