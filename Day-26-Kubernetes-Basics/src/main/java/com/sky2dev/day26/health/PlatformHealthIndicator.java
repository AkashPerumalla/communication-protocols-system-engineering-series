package com.sky2dev.day26.health;

import com.sky2dev.day26.dto.PlatformStatusResponse;
import com.sky2dev.day26.service.PlatformStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlatformHealthIndicator implements HealthIndicator {

    private final PlatformStatusService platformStatusService;

    @Override
    public Health health() {
        PlatformStatusResponse status = platformStatusService.getPlatformStatus();
        return Health.up()
                .withDetail("clusterName", status.clusterName())
                .withDetail("namespace", status.namespace())
                .withDetail("environment", status.environment())
                .withDetail("databaseMode", status.databaseMode())
                .withDetail("deployments", status.deployments())
                .withDetail("clusterStatus", status.clusterStatus())
                .build();
    }
}
