package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.AgentHeartbeatRequest;
import com.sky2dev.day20.dto.AgentRegistrationRequest;
import com.sky2dev.day20.dto.ApiResponse;
import com.sky2dev.day20.dto.MonitoringAgentDto;
import com.sky2dev.day20.service.AgentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @GetMapping
    public ApiResponse<List<MonitoringAgentDto>> listAgents() {
        return new ApiResponse<>("AGENT REGISTERED", "Agent discovery completed", agentService.discoverAgents());
    }

    @PostMapping("/register")
    public ApiResponse<MonitoringAgentDto> register(@Valid @RequestBody AgentRegistrationRequest request) {
        return new ApiResponse<>("AGENT REGISTERED", "Agent registration successful", agentService.registerAgent(request));
    }

    @PostMapping("/heartbeat")
    public ApiResponse<MonitoringAgentDto> heartbeat(@Valid @RequestBody AgentHeartbeatRequest request) {
        return new ApiResponse<>("AGENT REGISTERED", "Agent heartbeat processed", agentService.heartbeat(request));
    }
}
