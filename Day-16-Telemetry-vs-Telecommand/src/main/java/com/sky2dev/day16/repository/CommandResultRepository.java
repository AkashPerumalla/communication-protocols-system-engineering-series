package com.sky2dev.day16.repository;

import com.sky2dev.day16.model.CommandResult;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandResultRepository extends JpaRepository<CommandResult, Long> {
    @EntityGraph(attributePaths = {"device", "telecommand"})
    List<CommandResult> findTop50ByOrderByCompletedAtDesc();
}
