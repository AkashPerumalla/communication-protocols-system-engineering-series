package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.Satellite;
import com.sky2dev.day19.model.SatelliteStatus;

public record SatelliteDto(
        Long id,
        String satelliteName,
        String orbitalSlot,
        String coverageRegion,
        Integer transponderCount,
        SatelliteStatus status
) {
    public static SatelliteDto from(Satellite satellite) {
        return new SatelliteDto(
                satellite.getId(),
                satellite.getSatelliteName(),
                satellite.getOrbitalSlot(),
                satellite.getCoverageRegion(),
                satellite.getTransponderCount(),
                satellite.getStatus()
        );
    }
}
