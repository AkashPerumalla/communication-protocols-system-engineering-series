package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.LinkBudget;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkBudgetRepository extends JpaRepository<LinkBudget, Long> {
    Optional<LinkBudget> findByLinkName(String linkName);
}
