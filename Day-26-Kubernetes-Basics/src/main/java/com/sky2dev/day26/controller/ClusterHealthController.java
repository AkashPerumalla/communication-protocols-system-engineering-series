package com.sky2dev.day26.controller;

import com.sky2dev.day26.constants.MarkerConstants;
import com.sky2dev.day26.dto.ApiResponse;
import com.sky2dev.day26.dto.ClusterHealthResponse;
import com.sky2dev.day26.service.ClusterHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cluster")
@RequiredArgsConstructor
public class ClusterHealthController {

    private final ClusterHealthService clusterHealthService;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<ClusterHealthResponse>> getClusterHealth() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.CLUSTER_READY,
                "Cluster health snapshot generated",
                clusterHealthService.getCurrentHealth()));
    }
}
