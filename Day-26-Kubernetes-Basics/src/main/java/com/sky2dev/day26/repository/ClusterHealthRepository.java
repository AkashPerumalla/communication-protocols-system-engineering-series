package com.sky2dev.day26.repository;

import com.sky2dev.day26.entity.ClusterHealth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterHealthRepository extends JpaRepository<ClusterHealth, Long> {
    Optional<ClusterHealth> findTop1ByOrderByObservedAtDesc();
}
