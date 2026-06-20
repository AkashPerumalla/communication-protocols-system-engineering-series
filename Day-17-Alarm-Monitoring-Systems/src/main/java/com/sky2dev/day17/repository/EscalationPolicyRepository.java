package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.EscalationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalationPolicyRepository extends JpaRepository<EscalationPolicy, Long> {
}
