package com.sky2dev.day17.repository;

import com.sky2dev.day17.model.ManagedDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagedDeviceRepository extends JpaRepository<ManagedDevice, Long> {
}
