package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.LinkMetric;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkMetricRepository extends JpaRepository<LinkMetric, Long> {
    List<LinkMetric> findByLink_IdOrderByTimestampDesc(Long linkId);
}
