package com.sky2dev.day17.dto;

import com.sky2dev.day17.model.RootCause;

public record RootCauseDto(Long id, String causeName, String explanation, Integer affectedAlarmCount) {
    public static RootCauseDto from(RootCause rootCause) {
        return new RootCauseDto(rootCause.getId(), rootCause.getCauseName(), rootCause.getExplanation(), rootCause.getAffectedAlarmCount());
    }
}
