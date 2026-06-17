package com.sky2dev.day16.service;

import com.sky2dev.day16.model.CommandResult;
import com.sky2dev.day16.repository.CommandResultRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditTrailService {

    private final CommandResultRepository commandResultRepository;

    @Transactional
    public CommandResult record(CommandResult commandResult) {
        return commandResultRepository.save(commandResult);
    }

    @Transactional(readOnly = true)
    public List<CommandResult> recentCommands() {
        return commandResultRepository.findTop50ByOrderByCompletedAtDesc();
    }
}
