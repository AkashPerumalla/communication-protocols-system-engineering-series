package com.sky2dev.day15.controller;

import com.sky2dev.day15.dto.DashboardResponse;
import com.sky2dev.day15.service.DashboardAggregator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardAggregator dashboardAggregator;

    @GetMapping
    public DashboardResponse getDashboard() {
        return DashboardResponse.from(dashboardAggregator.captureSnapshot());
    }
}
