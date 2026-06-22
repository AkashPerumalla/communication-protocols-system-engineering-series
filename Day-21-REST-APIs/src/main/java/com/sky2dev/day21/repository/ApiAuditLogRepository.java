package com.sky2dev.day21.repository;

import com.sky2dev.day21.entity.ApiAuditLog;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiAuditLogRepository extends JpaRepository<ApiAuditLog, Long> {

    List<ApiAuditLog> findAllByOrderByTimestampDesc(Pageable pageable);
}
