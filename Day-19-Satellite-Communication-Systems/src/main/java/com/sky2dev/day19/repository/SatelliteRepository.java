package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.Satellite;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteRepository extends JpaRepository<Satellite, Long> {
    Optional<Satellite> findBySatelliteName(String satelliteName);
}
