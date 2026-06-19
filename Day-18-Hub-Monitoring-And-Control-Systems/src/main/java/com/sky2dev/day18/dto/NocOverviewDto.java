package com.sky2dev.day18.dto;

import java.math.BigDecimal;

public record NocOverviewDto(
        DashboardDto networkOverview,
        long activeAlarms,
        long controlsToday,
        long notificationsToday,
        long reportsAvailable,
        BigDecimal hubAvailability,
        int nocHealthScore
) {
}
