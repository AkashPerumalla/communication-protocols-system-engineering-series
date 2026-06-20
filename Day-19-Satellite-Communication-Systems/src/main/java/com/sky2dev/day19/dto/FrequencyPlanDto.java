package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.FrequencyPlan;

public record FrequencyPlanDto(
        Long id,
        Long transponderId,
        String channelName,
        Double uplinkFrequency,
        Double downlinkFrequency,
        Double bandwidth,
        Double guardBand
) {
    public static FrequencyPlanDto from(FrequencyPlan plan) {
        return new FrequencyPlanDto(
                plan.getId(),
                plan.getTransponder().getId(),
                plan.getChannelName(),
                plan.getUplinkFrequency(),
                plan.getDownlinkFrequency(),
                plan.getBandwidth(),
                plan.getGuardBand()
        );
    }
}
