package com.sky2dev.day20.dto;

import com.sky2dev.day20.entity.MonitoringAgent;
import java.time.Instant;

public record MonitoringAgentDto(
        Long id,
        String agentId,
        String hostname,
        String ipAddress,
        String location,
        String status,
        Instant lastSeen) {

    public static MonitoringAgentDto from(MonitoringAgent agent) {
        return new MonitoringAgentDto(
                agent.getId(),
                agent.getAgentId(),
                agent.getHostname(),
                agent.getIpAddress(),
                agent.getLocation(),
                agent.getStatus().name(),
                agent.getLastSeen());
    }
}
