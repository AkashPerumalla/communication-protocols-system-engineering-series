package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.AlarmEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {
}
