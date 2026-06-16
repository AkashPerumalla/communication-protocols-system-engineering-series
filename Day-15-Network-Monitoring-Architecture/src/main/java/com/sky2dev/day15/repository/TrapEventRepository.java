package com.sky2dev.day15.repository;

import com.sky2dev.day15.entity.TrapEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrapEventRepository extends JpaRepository<TrapEvent, Long> {

    List<TrapEvent> findTop100ByOrderByTriggeredAtDesc();
}
