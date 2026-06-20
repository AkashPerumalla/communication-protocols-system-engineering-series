package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.VsatTopologyDto;
import com.sky2dev.day19.model.EarthStation;
import com.sky2dev.day19.model.StationType;
import com.sky2dev.day19.repository.EarthStationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VSATNetworkService {

    private final EarthStationRepository stationRepository;

    public VsatTopologyDto generateTopology() {
        EarthStation hub = stationRepository.findAll().stream()
                .filter(st -> st.getType() == StationType.HUB)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("HUB station not found"));

        List<String> spokes = stationRepository.findAll().stream()
                .filter(st -> st.getType() == StationType.VSAT)
                .map(EarthStation::getStationName)
                .sorted()
                .toList();

        return new VsatTopologyDto(hub.getStationName(), spokes, spokes.size() + 1, "HUB_SPOKE");
    }
}
