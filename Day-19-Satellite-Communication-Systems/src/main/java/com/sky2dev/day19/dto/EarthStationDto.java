package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.EarthStation;
import com.sky2dev.day19.model.StationStatus;
import com.sky2dev.day19.model.StationType;

public record EarthStationDto(
        Long id,
        String stationName,
        StationType type,
        Double latitude,
        Double longitude,
        Double antennaSize,
        StationStatus status
) {
    public static EarthStationDto from(EarthStation station) {
        return new EarthStationDto(
                station.getId(),
                station.getStationName(),
                station.getType(),
                station.getLatitude(),
                station.getLongitude(),
                station.getAntennaSize(),
                station.getStatus()
        );
    }
}
