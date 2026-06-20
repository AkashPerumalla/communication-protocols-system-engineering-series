package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.TroubleTicketDto;
import com.sky2dev.day19.dto.TroubleshootingAnalyzeRequest;
import com.sky2dev.day19.model.TroubleTicket;
import com.sky2dev.day19.model.TroubleTicketStatus;
import com.sky2dev.day19.repository.SatComAlarmRepository;
import com.sky2dev.day19.repository.TroubleTicketRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TroubleshootingService {

    private final TroubleTicketRepository troubleTicketRepository;
    private final SatComAlarmRepository alarmRepository;

    public List<TroubleTicketDto> listTickets() {
        return troubleTicketRepository.findAll().stream().map(TroubleTicketDto::from).toList();
    }

    public TroubleTicketDto analyze(TroubleshootingAnalyzeRequest request) {
        String rootCause = "Unidentified impairment";
        String correctiveAction = "Run full RF and modem diagnostics";

        if (request.rxPowerDbm() < -72.0 && request.ber() > 1e-4) {
            rootCause = "Possible Antenna Misalignment";
            correctiveAction = "Perform antenna peaking and verify pointing offsets";
        } else if (request.cnDb() < 9.0 && request.ebNo() < 6.0 && request.ber() > 5e-5) {
            rootCause = "Rain Fade";
            correctiveAction = "Enable adaptive coding and modulation, increase uplink power if margin permits";
        } else if ("LOST".equalsIgnoreCase(request.lockStatus())) {
            rootCause = "Carrier Lock Lost";
            correctiveAction = "Check LNB, BUC, modem IF chain, and reference clock";
        }

        var alarm = alarmRepository.findAll().stream()
                .filter(a -> a.getSource().equalsIgnoreCase(request.linkName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No alarm found for link: " + request.linkName()));

        TroubleTicket ticket = TroubleTicket.builder()
                .alarm(alarm)
                .rootCause(rootCause)
                .correctiveAction(correctiveAction)
                .status(TroubleTicketStatus.INVESTIGATING)
                .build();

        return TroubleTicketDto.from(troubleTicketRepository.save(ticket));
    }
}
