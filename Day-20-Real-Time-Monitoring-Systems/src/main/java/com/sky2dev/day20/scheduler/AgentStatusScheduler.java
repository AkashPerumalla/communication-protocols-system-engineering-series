package com.sky2dev.day20.scheduler;

import com.sky2dev.day20.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgentStatusScheduler {

    private final AgentService agentService;

    @Scheduled(fixedDelay = 8000, initialDelay = 5000)
    public void refreshAgentStatus() {
        agentService.refreshAgentHealthStatus();
    }
}
