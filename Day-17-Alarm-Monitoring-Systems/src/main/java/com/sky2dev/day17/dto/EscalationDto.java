package com.sky2dev.day17.dto;

import com.sky2dev.day17.model.EscalationPolicy;

public record EscalationDto(Long id, String name, Integer minutesToEscalate, String minimumSeverity, String targetTeam) {
    public static EscalationDto from(EscalationPolicy policy) {
        return new EscalationDto(policy.getId(), policy.getName(), policy.getMinutesToEscalate(), policy.getMinimumSeverity().name(), policy.getTargetTeam());
    }
}
