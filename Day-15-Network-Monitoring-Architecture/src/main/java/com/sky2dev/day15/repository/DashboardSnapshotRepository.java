package com.sky2dev.day15.repository;

import com.sky2dev.day15.entity.DashboardSnapshot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardSnapshotRepository extends JpaRepository<DashboardSnapshot, Long> {

    Optional<DashboardSnapshot> findTopByOrderByGeneratedAtDesc();
}
