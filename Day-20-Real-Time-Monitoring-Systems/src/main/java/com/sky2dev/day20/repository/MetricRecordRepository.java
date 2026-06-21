package com.sky2dev.day20.repository;

import com.sky2dev.day20.entity.MetricRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRecordRepository extends JpaRepository<MetricRecord, Long> {

    List<MetricRecord> findTop100ByOrderByTimestampDesc();

    Optional<MetricRecord> findTop1ByAgentIdOrderByTimestampDesc(String agentId);

    List<MetricRecord> findTop20ByAgentIdOrderByTimestampDesc(String agentId);
}
