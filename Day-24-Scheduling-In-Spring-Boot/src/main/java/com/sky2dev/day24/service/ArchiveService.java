package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.ArchivedRecord;
import com.sky2dev.day24.entity.TelemetryRecord;
import com.sky2dev.day24.repository.ArchivedRecordRepository;
import com.sky2dev.day24.repository.TelemetryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchiveService {

    private final TelemetryRecordRepository telemetryRecordRepository;
    private final ArchivedRecordRepository archivedRecordRepository;

    @Transactional
    public ArchivedRecord archiveOldTelemetry() {
        Instant threshold = Instant.now().minusSeconds(120);
        List<TelemetryRecord> oldRecords = telemetryRecordRepository.findByTimestampBefore(threshold);
        if (!oldRecords.isEmpty()) {
            telemetryRecordRepository.deleteAll(oldRecords);
        }

        return archivedRecordRepository.save(ArchivedRecord.builder()
                .sourceType("TELEMETRY")
                .archivedAt(Instant.now())
                .recordCount(oldRecords.size())
                .build());
    }
}
