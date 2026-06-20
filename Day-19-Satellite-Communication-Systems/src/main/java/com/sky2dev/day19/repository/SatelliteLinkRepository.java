package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.SatelliteLink;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteLinkRepository extends JpaRepository<SatelliteLink, Long> {
    Optional<SatelliteLink> findByLinkName(String linkName);
}
