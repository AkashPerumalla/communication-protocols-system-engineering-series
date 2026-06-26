package com.sky2dev.day25.repository;

import com.sky2dev.day25.entity.AlarmEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {
    List<AlarmEvent> findTop20ByOrderByRaisedAtDesc();
}
