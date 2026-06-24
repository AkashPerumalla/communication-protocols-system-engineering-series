package com.sky2dev.day23.repository;

import com.sky2dev.day23.entity.NotificationEvent;
import com.sky2dev.day23.entity.NotificationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {
    List<NotificationEvent> findTop50ByOrderByTimestampDesc();

    long countByStatus(NotificationStatus status);
}
