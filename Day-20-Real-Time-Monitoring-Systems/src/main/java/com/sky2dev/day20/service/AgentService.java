package com.sky2dev.day20.service;

import com.sky2dev.day20.dto.AgentHeartbeatRequest;
import com.sky2dev.day20.dto.AgentRegistrationRequest;
import com.sky2dev.day20.dto.MonitoringAgentDto;
import com.sky2dev.day20.entity.AgentStatus;
import com.sky2dev.day20.entity.MonitoringAgent;
import com.sky2dev.day20.repository.MonitoringAgentRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final MonitoringAgentRepository agentRepository;

    public MonitoringAgentDto registerAgent(AgentRegistrationRequest request) {
        MonitoringAgent agent = agentRepository.findByAgentId(request.agentId())
                .map(existing -> {
                    existing.setHostname(request.hostname());
                    existing.setIpAddress(request.ipAddress());
                    existing.setLocation(request.location());
                    existing.setStatus(AgentStatus.ONLINE);
                    existing.setLastSeen(Instant.now());
                    return existing;
                })
                .orElseGet(() -> MonitoringAgent.builder()
                        .agentId(request.agentId())
                        .hostname(request.hostname())
                        .ipAddress(request.ipAddress())
                        .location(request.location())
                        .status(AgentStatus.ONLINE)
                        .lastSeen(Instant.now())
                        .build());

        return MonitoringAgentDto.from(agentRepository.save(agent));
    }

    public MonitoringAgentDto heartbeat(AgentHeartbeatRequest request) {
        MonitoringAgent agent = agentRepository.findByAgentId(request.agentId())
                .orElseThrow(() -> new IllegalArgumentException("Agent not found: " + request.agentId()));
        agent.setLastSeen(Instant.now());
        agent.setStatus(AgentStatus.ONLINE);
        return MonitoringAgentDto.from(agentRepository.save(agent));
    }

    public List<MonitoringAgentDto> discoverAgents() {
        return agentRepository.findAll().stream().map(MonitoringAgentDto::from).toList();
    }

    public void refreshAgentHealthStatus() {
        Instant cutoff = Instant.now().minusSeconds(15);
        List<MonitoringAgent> agents = agentRepository.findAll();
        for (MonitoringAgent agent : agents) {
            agent.setStatus(agent.getLastSeen().isBefore(cutoff) ? AgentStatus.OFFLINE : AgentStatus.ONLINE);
        }
        agentRepository.saveAll(agents);
    }
}
