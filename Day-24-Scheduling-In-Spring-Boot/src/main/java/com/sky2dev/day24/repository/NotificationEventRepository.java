package com.sky2dev.day24.repository;

import com.sky2dev.day24.entity.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {

    long countByStatus(String status);
}
