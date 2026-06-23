package com.sky2dev.day22.scheduler;

import com.sky2dev.day22.dto.AlarmResponse;
import com.sky2dev.day22.service.AlarmService;
import com.sky2dev.day22.service.WebSocketBroadcastService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "app.scheduling.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class AlarmScheduler {

    private final AlarmService alarmService;
    private final WebSocketBroadcastService broadcastService;

    @Scheduled(fixedRate = 10000)
    public void evaluateAlarms() {
        List<AlarmResponse> generated = alarmService.evaluateAndGenerateAlarms();
        if (!generated.isEmpty()) {
            broadcastService.publish("/topic/alarms", "ALARM BROADCASTED", "Alarm events broadcasted", generated);
        }
    }
}
