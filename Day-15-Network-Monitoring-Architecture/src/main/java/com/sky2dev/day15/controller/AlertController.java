package com.sky2dev.day15.controller;

import com.sky2dev.day15.alert.AlertService;
import com.sky2dev.day15.dto.AlertResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public List<AlertResponse> getAlerts() {
        return alertService.findAllAlerts().stream().map(AlertResponse::from).toList();
    }
}
