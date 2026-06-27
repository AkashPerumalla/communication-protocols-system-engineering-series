package com.sky2dev.day26.mapper;

import com.sky2dev.day26.dto.AlarmResponse;
import com.sky2dev.day26.entity.AlarmEvent;
import org.springframework.stereotype.Component;

@Component
public class AlarmMapper {

    public AlarmResponse toResponse(AlarmEvent alarmEvent) {
        return new AlarmResponse(
                alarmEvent.getId(),
                alarmEvent.getDevice().getName(),
                alarmEvent.getCode(),
                alarmEvent.getDescription(),
                alarmEvent.getSeverity(),
                alarmEvent.getStatus(),
                alarmEvent.getSourceMetric(),
                alarmEvent.getThresholdValue(),
                alarmEvent.getObservedValue(),
                alarmEvent.getRaisedAt());
    }
}
