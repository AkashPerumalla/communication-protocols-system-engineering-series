package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.PolarizationType;
import com.sky2dev.day19.model.Transponder;
import com.sky2dev.day19.model.TransponderStatus;

public record TransponderDto(
        Long id,
        Long satelliteId,
        String satelliteName,
        Integer transponderNumber,
        Double bandwidthMhz,
        Double uplinkFrequency,
        Double downlinkFrequency,
        PolarizationType polarization,
        TransponderStatus status
) {
    public static TransponderDto from(Transponder transponder) {
        return new TransponderDto(
                transponder.getId(),
                transponder.getSatellite().getId(),
                transponder.getSatellite().getSatelliteName(),
                transponder.getTransponderNumber(),
                transponder.getBandwidthMhz(),
                transponder.getUplinkFrequency(),
                transponder.getDownlinkFrequency(),
                transponder.getPolarization(),
                transponder.getStatus()
        );
    }
}
