package com.sky2dev.day17.dto;

import java.util.List;

public record DashboardDto(int totalDevices, int totalMetrics, int openAlarms, int criticalAlarms, int unacknowledgedAlarms, int escalatedAlarms, int correlatedIncidents, int rootCauses, List<String> markers) {
}
