package com.sky2dev.day20.repository;

import com.sky2dev.day20.entity.AgentStatus;
import com.sky2dev.day20.entity.MonitoringAgent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoringAgentRepository extends JpaRepository<MonitoringAgent, Long> {

    Optional<MonitoringAgent> findByAgentId(String agentId);

    int countByStatus(AgentStatus status);
}
