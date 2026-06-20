package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
