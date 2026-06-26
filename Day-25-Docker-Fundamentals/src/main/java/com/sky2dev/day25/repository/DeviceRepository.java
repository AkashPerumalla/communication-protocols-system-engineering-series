package com.sky2dev.day25.repository;

import com.sky2dev.day25.entity.Device;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByName(String name);
    boolean existsByIpAddress(String ipAddress);
}
