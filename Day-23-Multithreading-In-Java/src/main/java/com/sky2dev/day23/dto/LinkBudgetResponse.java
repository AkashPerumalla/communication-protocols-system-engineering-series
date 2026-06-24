package com.sky2dev.day23.dto;

public record LinkBudgetResponse(
        String marker,
        double eirp,
        double cn,
        double linkMargin
) {
}
