package com.sky2dev.day17.service;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmSeverity;
import com.sky2dev.day17.model.AlarmState;
import com.sky2dev.day17.model.EscalationPolicy;
import com.sky2dev.day17.repository.EscalationPolicyRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EscalationService {

    private final EscalationPolicyRepository escalationPolicyRepository;

    public EscalationService(EscalationPolicyRepository escalationPolicyRepository) {
        this.escalationPolicyRepository = escalationPolicyRepository;
    }

    public List<EscalationPolicy> findAll() {
        return escalationPolicyRepository.findAll();
    }

    public boolean shouldEscalate(Alarm alarm) {
        return alarm.getSeverity().ordinal() >= AlarmSeverity.MAJOR.ordinal()
                && alarm.getState() != AlarmState.RESOLVED
                && alarm.getState() != AlarmState.CLOSED;
    }
}
