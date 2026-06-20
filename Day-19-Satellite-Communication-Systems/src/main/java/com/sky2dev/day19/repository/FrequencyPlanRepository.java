package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.FrequencyPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrequencyPlanRepository extends JpaRepository<FrequencyPlan, Long> {
    List<FrequencyPlan> findByTransponder_IdOrderByUplinkFrequencyAsc(Long transponderId);
}
