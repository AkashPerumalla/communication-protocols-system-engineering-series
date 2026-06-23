package com.sky2dev.day22.scheduler;

import com.sky2dev.day22.service.TelemetryService;
import com.sky2dev.day22.service.WebSocketBroadcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "app.scheduling.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class TelemetryScheduler {

    private final TelemetryService telemetryService;
    private final WebSocketBroadcastService broadcastService;

    @Scheduled(fixedRate = 5000)
    public void generateTelemetry() {
        broadcastService.publish(
                "/topic/telemetry",
                "TELEMETRY STREAM ACTIVE",
                "Telemetry metrics generated and streamed",
                telemetryService.generateTelemetryBatch());
    }
}
