package com.sky2dev.day17.service;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmState;
import com.sky2dev.day17.repository.AlarmRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class AlarmLifecycleService {

    private final AlarmRepository alarmRepository;

    public AlarmLifecycleService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public Alarm acknowledge(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow();
        alarm.setState(AlarmState.ACKNOWLEDGED);
        alarm.setAcknowledged(true);
        alarm.setAcknowledgedAt(Instant.parse("2026-06-18T10:06:00Z"));
        return alarmRepository.save(alarm);
    }

    public Alarm resolve(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow();
        alarm.setState(AlarmState.RESOLVED);
        alarm.setResolvedAt(Instant.parse("2026-06-18T10:15:00Z"));
        return alarmRepository.save(alarm);
    }

    public Alarm close(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow();
        alarm.setState(AlarmState.CLOSED);
        alarm.setClosedAt(Instant.parse("2026-06-18T10:20:00Z"));
        return alarmRepository.save(alarm);
    }

    public Alarm escalate(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow();
        alarm.setState(AlarmState.ESCALATED);
        alarm.setEscalated(true);
        alarm.setEscalatedAt(Instant.parse("2026-06-18T10:08:00Z"));
        return alarmRepository.save(alarm);
    }
}
