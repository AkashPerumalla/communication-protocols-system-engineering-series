package com.sky2dev.day24.repository;

import com.sky2dev.day24.entity.ArchivedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivedRecordRepository extends JpaRepository<ArchivedRecord, Long> {
}
