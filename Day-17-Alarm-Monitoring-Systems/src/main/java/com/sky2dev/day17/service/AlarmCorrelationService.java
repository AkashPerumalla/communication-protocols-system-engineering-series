package com.sky2dev.day17.service;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmCorrelation;
import com.sky2dev.day17.repository.AlarmCorrelationRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AlarmCorrelationService {

    private final AlarmCorrelationRepository alarmCorrelationRepository;

    public AlarmCorrelationService(AlarmCorrelationRepository alarmCorrelationRepository) {
        this.alarmCorrelationRepository = alarmCorrelationRepository;
    }

    public List<AlarmCorrelation> correlate(List<Alarm> alarms) {
        Map<String, Long> counts = new LinkedHashMap<>();
        for (Alarm alarm : alarms) {
            counts.merge(alarm.getCorrelationKey(), 1L, Long::sum);
        }
        return alarmCorrelationRepository.saveAll(counts.entrySet().stream()
                .map(entry -> AlarmCorrelation.builder()
                        .correlationKey(entry.getKey())
                        .incidentName(entry.getKey() + " correlated incident")
                        .relatedAlarmCount(entry.getValue().intValue())
                        .build())
                .toList());
    }

    public List<AlarmCorrelation> findAll() {
        return alarmCorrelationRepository.findAll();
    }
}
