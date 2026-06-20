package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.AlarmAcknowledgement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmAcknowledgementRepository extends JpaRepository<AlarmAcknowledgement, Long> {
}
