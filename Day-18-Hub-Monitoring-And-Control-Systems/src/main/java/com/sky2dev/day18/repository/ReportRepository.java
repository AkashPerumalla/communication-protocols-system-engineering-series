package com.sky2dev.day18.repository;

import com.sky2dev.day18.model.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findTop10ByOrderByGeneratedTimeDesc();
}
