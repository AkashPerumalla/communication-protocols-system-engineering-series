package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.EarthStation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EarthStationRepository extends JpaRepository<EarthStation, Long> {
    Optional<EarthStation> findByStationName(String stationName);
}
