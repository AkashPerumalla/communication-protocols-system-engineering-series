package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long> {
}
