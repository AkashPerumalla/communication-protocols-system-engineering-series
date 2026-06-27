package com.sky2dev.day26.controller;

import com.sky2dev.day26.constants.MarkerConstants;
import com.sky2dev.day26.dto.ApiResponse;
import com.sky2dev.day26.dto.DeploymentStatusResponse;
import com.sky2dev.day26.service.DeploymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeploymentController {

    private final DeploymentService deploymentService;

    @GetMapping("/api/deployments")
    public ResponseEntity<ApiResponse<List<DeploymentStatusResponse>>> getDeployments() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.DEPLOYMENT_CREATED,
                "Deployment state snapshot generated",
                deploymentService.getDeployments()));
    }

    @PostMapping("/api/deployment/scale/{replicas}")
    public ResponseEntity<ApiResponse<DeploymentStatusResponse>> scaleDeployment(@PathVariable int replicas) {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.DEPLOYMENT_CREATED,
                "Deployment scale action executed",
                deploymentService.scalePrimaryDeployment(replicas)));
    }

    @PostMapping("/api/deployment/rolling-update")
    public ResponseEntity<ApiResponse<DeploymentStatusResponse>> rollingUpdate() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.ROLLING_UPDATE_STARTED,
                "Rolling update action executed",
                deploymentService.startRollingUpdate()));
    }

    @PostMapping("/api/deployment/rollback")
    public ResponseEntity<ApiResponse<DeploymentStatusResponse>> rollbackDeployment() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.ROLLBACK_COMPLETED,
                "Rollback action executed",
                deploymentService.rollbackPrimaryDeployment()));
    }
}
