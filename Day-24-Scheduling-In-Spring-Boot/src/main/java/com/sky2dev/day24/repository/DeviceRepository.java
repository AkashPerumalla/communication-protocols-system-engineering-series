package com.sky2dev.day24.repository;

import com.sky2dev.day24.entity.Device;
import com.sky2dev.day24.entity.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    long countByStatus(DeviceStatus status);
}
