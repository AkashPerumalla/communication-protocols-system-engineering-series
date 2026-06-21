package com.sky2dev.day20.controller;

import com.sky2dev.day20.dto.ApiResponse;
import com.sky2dev.day20.dto.SnmpMetricDto;
import com.sky2dev.day20.service.SNMPMonitoringService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/snmp")
@RequiredArgsConstructor
public class SNMPController {

    private final SNMPMonitoringService snmpMonitoringService;

    @GetMapping
    public ApiResponse<List<SnmpMetricDto>> snmpMetrics() {
        return new ApiResponse<>("SNMP MONITORING ACTIVE", "SNMP OID collection completed", snmpMonitoringService.collectSnmpMetrics());
    }
}
