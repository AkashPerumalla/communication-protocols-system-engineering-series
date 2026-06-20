package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.FrequencyPlanDto;
import com.sky2dev.day19.dto.FrequencyPlanGenerateRequest;
import com.sky2dev.day19.model.FrequencyPlan;
import com.sky2dev.day19.model.Transponder;
import com.sky2dev.day19.repository.FrequencyPlanRepository;
import com.sky2dev.day19.repository.TransponderRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FrequencyPlanningService {

    private final FrequencyPlanRepository frequencyPlanRepository;
    private final TransponderRepository transponderRepository;

    public List<FrequencyPlanDto> listPlans() {
        return frequencyPlanRepository.findAll().stream().map(FrequencyPlanDto::from).toList();
    }

    public List<FrequencyPlanDto> allocateChannels() {
        return listPlans();
    }

    public boolean validateGuardBands() {
        List<FrequencyPlan> ordered = frequencyPlanRepository.findAll().stream()
                .sorted(Comparator.comparing(FrequencyPlan::getUplinkFrequency))
                .toList();

        for (int i = 1; i < ordered.size(); i++) {
            FrequencyPlan prev = ordered.get(i - 1);
            FrequencyPlan curr = ordered.get(i);
            double prevEdge = prev.getUplinkFrequency() + prev.getBandwidth();
            double guardGap = curr.getUplinkFrequency() - prevEdge;
            if (guardGap < Math.min(prev.getGuardBand(), curr.getGuardBand())) {
                return false;
            }
        }
        return true;
    }

    public List<FrequencyPlanDto> generateFrequencyPlan(FrequencyPlanGenerateRequest request) {
        Transponder transponder = transponderRepository.findAll().stream()
                .filter(tp -> tp.getSatellite().getSatelliteName().equalsIgnoreCase(request.transponderSatelliteName()))
                .filter(tp -> tp.getTransponderNumber().equals(request.transponderNumber()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Transponder not found for requested satellite and number"));

        List<FrequencyPlanDto> generated = new ArrayList<>();
        for (int i = 0; i < request.channelCount(); i++) {
            double offset = i * (request.channelBandwidth() + request.guardBand());
            FrequencyPlan plan = FrequencyPlan.builder()
                    .transponder(transponder)
                    .channelName(request.channelPrefix() + "-" + (i + 1))
                    .uplinkFrequency(round(request.startUplinkFrequency() + offset))
                    .downlinkFrequency(round(request.startDownlinkFrequency() + offset))
                    .bandwidth(request.channelBandwidth())
                    .guardBand(request.guardBand())
                    .build();
            generated.add(FrequencyPlanDto.from(frequencyPlanRepository.save(plan)));
        }
        return generated;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
