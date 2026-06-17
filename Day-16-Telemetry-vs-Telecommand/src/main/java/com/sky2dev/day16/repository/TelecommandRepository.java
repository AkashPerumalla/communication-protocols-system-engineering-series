package com.sky2dev.day16.repository;

import com.sky2dev.day16.model.Telecommand;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelecommandRepository extends JpaRepository<Telecommand, Long> {
    @EntityGraph(attributePaths = "device")
    List<Telecommand> findTop50ByOrderByRequestedAtDesc();
}
