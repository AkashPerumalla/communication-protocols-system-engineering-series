package com.sky2dev.day18.repository;

import com.sky2dev.day18.model.DeliveryStatus;
import com.sky2dev.day18.model.NotificationEvent;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {

    List<NotificationEvent> findTop20ByOrderBySentTimeDesc();

    List<NotificationEvent> findByAlarmId(Long alarmId);

    long countBySentTimeAfter(Instant timestamp);

    long countByDeliveryStatus(DeliveryStatus deliveryStatus);
}
