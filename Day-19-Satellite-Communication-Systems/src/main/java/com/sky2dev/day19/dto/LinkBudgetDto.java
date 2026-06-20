package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.LinkBudget;

public record LinkBudgetDto(
        Long id,
        String linkName,
        Double eirp,
        Double antennaGain,
        Double pathLoss,
        Double gOverT,
        Double noiseTemperature,
        Double carrierPower,
        Double cnRatio,
        Double linkMargin
) {
    public static LinkBudgetDto from(LinkBudget budget) {
        return new LinkBudgetDto(
                budget.getId(),
                budget.getLinkName(),
                budget.getEirp(),
                budget.getAntennaGain(),
                budget.getPathLoss(),
                budget.getGOverT(),
                budget.getNoiseTemperature(),
                budget.getCarrierPower(),
                budget.getCnRatio(),
                budget.getLinkMargin()
        );
    }
}
