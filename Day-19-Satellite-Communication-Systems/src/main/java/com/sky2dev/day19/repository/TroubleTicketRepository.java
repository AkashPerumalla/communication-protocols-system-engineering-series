package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.TroubleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TroubleTicketRepository extends JpaRepository<TroubleTicket, Long> {
}
