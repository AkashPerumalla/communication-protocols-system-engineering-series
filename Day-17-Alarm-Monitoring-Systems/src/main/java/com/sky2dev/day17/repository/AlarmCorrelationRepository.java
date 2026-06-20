package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.AlarmCorrelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmCorrelationRepository extends JpaRepository<AlarmCorrelation, Long> {
}
