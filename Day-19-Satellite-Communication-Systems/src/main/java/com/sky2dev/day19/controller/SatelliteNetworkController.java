package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.EarthStationDto;
import com.sky2dev.day19.dto.SatelliteDto;
import com.sky2dev.day19.dto.SatelliteLinkDto;
import com.sky2dev.day19.dto.TransponderDto;
import com.sky2dev.day19.service.SatelliteNetworkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SatelliteNetworkController {

    private final SatelliteNetworkService satelliteNetworkService;

    @GetMapping("/satellites")
    public ApiResponse<List<SatelliteDto>> satellites() {
        return new ApiResponse<>("SATELLITE NETWORK ACTIVE", "Satellites listed", satelliteNetworkService.listSatellites());
    }

    @GetMapping("/transponders")
    public ApiResponse<List<TransponderDto>> transponders() {
        return new ApiResponse<>("SATELLITE NETWORK ACTIVE", "Transponders listed", satelliteNetworkService.listTransponders());
    }

    @GetMapping("/stations")
    public ApiResponse<List<EarthStationDto>> stations() {
        return new ApiResponse<>("SATELLITE NETWORK ACTIVE", "Earth stations listed", satelliteNetworkService.listEarthStations());
    }

    @GetMapping("/links")
    public ApiResponse<List<SatelliteLinkDto>> links() {
        return new ApiResponse<>("SATELLITE NETWORK ACTIVE", "Links listed", satelliteNetworkService.listLinks());
    }
}
