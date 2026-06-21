package com.sky2dev.day20.repository;

import com.sky2dev.day20.entity.AlertRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRuleRepository extends JpaRepository<AlertRule, Long> {

    List<AlertRule> findByEnabledTrue();
}
