package com.sky2dev.day19.dto;

import com.sky2dev.day19.model.TroubleTicket;
import com.sky2dev.day19.model.TroubleTicketStatus;

public record TroubleTicketDto(
        Long id,
        Long alarmId,
        String rootCause,
        String correctiveAction,
        TroubleTicketStatus status
) {
    public static TroubleTicketDto from(TroubleTicket ticket) {
        return new TroubleTicketDto(
                ticket.getId(),
                ticket.getAlarm().getId(),
                ticket.getRootCause(),
                ticket.getCorrectiveAction(),
                ticket.getStatus()
        );
    }
}
