package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.LinkStatus;
import com.sky2dev.day19.model.SatelliteLink;

public record SatelliteLinkDto(
        Long id,
        String linkName,
        String sourceStation,
        String destinationStation,
        String satelliteName,
        LinkStatus status
) {
    public static SatelliteLinkDto from(SatelliteLink link) {
        return new SatelliteLinkDto(
                link.getId(),
                link.getLinkName(),
                link.getSourceStation().getStationName(),
                link.getDestinationStation().getStationName(),
                link.getSatellite().getSatelliteName(),
                link.getStatus()
        );
    }
}
