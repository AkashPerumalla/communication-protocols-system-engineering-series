package com.sky2dev.day18.controller;

import com.sky2dev.day18.dto.ApiResponse;
import com.sky2dev.day18.dto.NocOverviewDto;
import com.sky2dev.day18.service.NocControlCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/noc")
@RequiredArgsConstructor
public class NocController {

    private final NocControlCenterService nocControlCenterService;

    @GetMapping
    public ApiResponse<NocOverviewDto> nocOverview() {
        return new ApiResponse<>(
                "NOC CONTROL CENTER",
                "Integrated NOC control center overview generated",
                nocControlCenterService.getNocOverview()
        );
    }
}
