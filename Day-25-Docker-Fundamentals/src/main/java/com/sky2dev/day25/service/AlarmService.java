package com.sky2dev.day25.service;

import com.sky2dev.day25.dto.AlarmResponse;
import com.sky2dev.day25.repository.AlarmEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmEventRepository alarmEventRepository;

    @Transactional(readOnly = true)
    public List<AlarmResponse> getAlarms() {
        return alarmEventRepository.findTop20ByOrderByRaisedAtDesc().stream()
                .map(alarm -> new AlarmResponse(
                        alarm.getId(),
                        alarm.getDevice().getName(),
                        alarm.getCode(),
                        alarm.getDescription(),
                        alarm.getSeverity(),
                        alarm.getStatus(),
                        alarm.getRaisedAt()))
                .toList();
    }
}
