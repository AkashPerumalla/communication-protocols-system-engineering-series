package com.sky2dev.day19.repository;

import com.sky2dev.day19.model.Transponder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransponderRepository extends JpaRepository<Transponder, Long> {
    List<Transponder> findBySatellite_SatelliteName(String satelliteName);
}
