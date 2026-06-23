package com.sky2dev.day22.service;

import com.sky2dev.day22.dto.ConnectedClientResponse;
import com.sky2dev.day22.entity.ConnectedClient;
import com.sky2dev.day22.repository.ConnectedClientRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectedClientService {

    private final ConnectedClientRepository connectedClientRepository;

    public ConnectedClientResponse connect(String username, String sessionId) {
        ConnectedClient client = connectedClientRepository.findBySessionId(sessionId)
                .orElse(ConnectedClient.builder()
                        .username(username)
                        .sessionId(sessionId)
                        .connectedAt(Instant.now())
                        .active(true)
                        .build());
        client.setUsername(username);
        client.setActive(true);
        client.setConnectedAt(Instant.now());
        ConnectedClient saved = connectedClientRepository.save(client);
        return toResponse(saved);
    }

    public void disconnect(String sessionId) {
        connectedClientRepository.findBySessionId(sessionId).ifPresent(client -> {
            client.setActive(false);
            connectedClientRepository.save(client);
        });
    }

    public List<ConnectedClientResponse> getActiveClients() {
        return connectedClientRepository.findAllByActiveTrue().stream().map(this::toResponse).toList();
    }

    private ConnectedClientResponse toResponse(ConnectedClient client) {
        return new ConnectedClientResponse(client.getId(), client.getUsername(), client.getSessionId(),
                client.getConnectedAt(), client.isActive());
    }
}
