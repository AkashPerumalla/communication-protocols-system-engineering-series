package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.LinkBudgetCalculationRequest;
import com.sky2dev.day19.dto.LinkBudgetDto;
import com.sky2dev.day19.model.LinkBudget;
import com.sky2dev.day19.repository.LinkBudgetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkBudgetService {

    private static final double BOLTZMANN_DBW_PER_HZ_K = -228.6;

    private final LinkBudgetRepository linkBudgetRepository;

    public List<LinkBudgetDto> getAllBudgets() {
        return linkBudgetRepository.findAll().stream().map(LinkBudgetDto::from).toList();
    }

    public LinkBudgetDto calculateAndPersist(LinkBudgetCalculationRequest request) {
        double eirp = request.txPowerDbw() + request.txAntennaGainDbi() - request.implementationLossDb();
        double pathLoss = 92.45 + (20.0 * Math.log10(request.frequencyGhz())) + (20.0 * Math.log10(request.slantRangeKm()));
        double antennaGain = request.txAntennaGainDbi();
        double carrierPower = eirp - pathLoss + antennaGain;
        double cnRatio = carrierPower - BOLTZMANN_DBW_PER_HZ_K - (10.0 * Math.log10(request.noiseTemperatureK())) + request.gOverTDbPerK();
        double linkMargin = cnRatio - request.requiredCnDb();

        LinkBudget budget = LinkBudget.builder()
                .linkName(request.linkName())
                .eirp(round(eirp))
                .antennaGain(round(antennaGain))
                .pathLoss(round(pathLoss))
                .gOverT(round(request.gOverTDbPerK()))
                .noiseTemperature(round(request.noiseTemperatureK()))
                .carrierPower(round(carrierPower))
                .cnRatio(round(cnRatio))
                .linkMargin(round(linkMargin))
                .build();

        return LinkBudgetDto.from(linkBudgetRepository.save(budget));
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
