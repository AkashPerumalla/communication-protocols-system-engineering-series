package com.sky2dev.day19.service;

import com.sky2dev.day19.dto.AlarmSimulationRequest;
import com.sky2dev.day19.dto.SatComAlarmDto;
import com.sky2dev.day19.model.AlarmSeverity;
import com.sky2dev.day19.model.AlarmStatus;
import com.sky2dev.day19.model.LockStatus;
import com.sky2dev.day19.model.SatComAlarm;
import com.sky2dev.day19.repository.SatComAlarmRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final SatComAlarmRepository alarmRepository;

    public List<SatComAlarmDto> listAlarms() {
        return alarmRepository.findAll().stream().map(SatComAlarmDto::from).toList();
    }

    public List<SatComAlarmDto> simulate(AlarmSimulationRequest request) {
        List<SatComAlarmDto> created = new ArrayList<>();

        if (request.cnDb() < 9.0) {
            created.add(createAlarm(AlarmSeverity.CRITICAL, "LOW_CN", request.linkName(), "C/N below threshold: " + request.cnDb()));
        }
        if (request.ber() > 1e-4) {
            created.add(createAlarm(AlarmSeverity.MAJOR, "HIGH_BER", request.linkName(), "BER above threshold: " + request.ber()));
        }
        if (request.ebNo() < 6.0) {
            created.add(createAlarm(AlarmSeverity.MAJOR, "LOW_EBNO", request.linkName(), "Eb/No below threshold: " + request.ebNo()));
        }
        if (request.rxPowerDbm() < -72.0) {
            created.add(createAlarm(AlarmSeverity.WARNING, "LOW_RX_POWER", request.linkName(), "Rx Power low: " + request.rxPowerDbm() + " dBm"));
        }
        if (LockStatus.valueOf(request.lockStatus()) == LockStatus.LOST) {
            created.add(createAlarm(AlarmSeverity.CRITICAL, "LOCK_LOST", request.linkName(), "Carrier lock lost on modem"));
        }

        if (created.isEmpty()) {
            created.add(createAlarm(AlarmSeverity.WARNING, "NO_FAULT", request.linkName(), "No threshold breach. Simulation generated baseline alarm."));
        }

        return created;
    }

    private SatComAlarmDto createAlarm(AlarmSeverity severity, String alarmType, String source, String message) {
        SatComAlarm alarm = SatComAlarm.builder()
                .severity(severity)
                .alarmType(alarmType)
                .source(source)
                .message(message)
                .status(AlarmStatus.OPEN)
                .timestamp(Instant.now())
                .build();
        return SatComAlarmDto.from(alarmRepository.save(alarm));
    }
}
