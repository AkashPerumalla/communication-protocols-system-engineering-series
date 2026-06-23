package com.sky2dev.day22.repository;

import com.sky2dev.day22.entity.ConnectedClient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectedClientRepository extends JpaRepository<ConnectedClient, Long> {

    long countByActiveTrue();

    Optional<ConnectedClient> findBySessionId(String sessionId);

    List<ConnectedClient> findAllByActiveTrue();
}
