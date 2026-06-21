package com.sky2dev.day20.repository;

import com.sky2dev.day20.entity.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {
}
