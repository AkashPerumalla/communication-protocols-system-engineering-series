package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.AlarmRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Long> {
}
