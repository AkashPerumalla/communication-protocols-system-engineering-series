package com.sky2dev.day17.service;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmType;
import com.sky2dev.day17.model.RootCause;
import com.sky2dev.day17.repository.RootCauseRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RootCauseAnalysisService {

    private final RootCauseRepository rootCauseRepository;

    public RootCauseAnalysisService(RootCauseRepository rootCauseRepository) {
        this.rootCauseRepository = rootCauseRepository;
    }

    public List<RootCause> analyze(List<Alarm> alarms) {
        List<RootCause> results = new ArrayList<>();
        boolean powerRelated = alarms.stream().anyMatch(alarm -> alarm.getAlarmType() == AlarmType.DEVICE_UNREACHABLE
            || alarm.getAlarmType() == AlarmType.INTERFACE_DOWN
            || alarm.getAlarmType() == AlarmType.POWER_SUPPLY_FAILURE);
        if (powerRelated) {
            results.add(RootCause.builder()
                .causeName("Power Failure")
                .explanation("Power failure can trigger device unreachable, interface down, and signal loss alarms.")
                .affectedAlarmCount(alarms.size())
                .build());
        }
        if (results.isEmpty() && !alarms.isEmpty()) {
            results.add(RootCause.builder()
                    .causeName("Application Down")
                    .explanation("Application failure is the most likely root cause in the current alarm set.")
                    .affectedAlarmCount(alarms.size())
                    .build());
        }
        return rootCauseRepository.saveAll(results);
    }

    public List<RootCause> findAll() {
        return rootCauseRepository.findAll().stream()
                .sorted(Comparator.comparing(RootCause::getId))
                .toList();
    }
}
