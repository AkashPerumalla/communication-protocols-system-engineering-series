package com.sky2dev.day25.repository;

import com.sky2dev.day25.entity.NotificationEvent;
import com.sky2dev.day25.entity.NotificationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {
    List<NotificationEvent> findTop20ByOrderBySentAtDesc();
    long countByStatus(NotificationStatus status);
}
