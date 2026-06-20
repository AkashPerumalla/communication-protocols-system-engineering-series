package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.EarthStationDto;
import com.sky2dev.day19.dto.SatelliteDto;
import com.sky2dev.day19.dto.SatelliteLinkDto;
import com.sky2dev.day19.dto.TransponderDto;
import com.sky2dev.day19.repository.EarthStationRepository;
import com.sky2dev.day19.repository.SatelliteLinkRepository;
import com.sky2dev.day19.repository.SatelliteRepository;
import com.sky2dev.day19.repository.TransponderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SatelliteNetworkService {

    private final SatelliteRepository satelliteRepository;
    private final TransponderRepository transponderRepository;
    private final EarthStationRepository earthStationRepository;
    private final SatelliteLinkRepository satelliteLinkRepository;

    public List<SatelliteDto> listSatellites() {
        return satelliteRepository.findAll().stream().map(SatelliteDto::from).toList();
    }

    public List<TransponderDto> listTransponders() {
        return transponderRepository.findAll().stream().map(TransponderDto::from).toList();
    }

    public List<EarthStationDto> listEarthStations() {
        return earthStationRepository.findAll().stream().map(EarthStationDto::from).toList();
    }

    public List<SatelliteLinkDto> listLinks() {
        return satelliteLinkRepository.findAll().stream().map(SatelliteLinkDto::from).toList();
    }
}
