package com.sky2dev.day19.controller;

import com.sky2dev.day19.dto.ApiResponse;
import com.sky2dev.day19.dto.VsatTopologyDto;
import com.sky2dev.day19.service.VSATNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vsat")
@RequiredArgsConstructor
public class VsatController {

    private final VSATNetworkService vsatNetworkService;

    @GetMapping
    public ApiResponse<VsatTopologyDto> topology() {
        return new ApiResponse<>("VSAT NETWORK CREATED", "VSAT topology generated", vsatNetworkService.generateTopology());
    }
}
