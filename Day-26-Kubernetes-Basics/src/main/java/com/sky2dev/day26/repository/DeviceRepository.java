package com.sky2dev.day26.repository;

import com.sky2dev.day26.entity.Device;
import com.sky2dev.day26.entity.DeviceStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByOrderByNameAsc();
    List<Device> findByStatus(DeviceStatus status);
    Optional<Device> findByName(String name);
    boolean existsByIpAddress(String ipAddress);
    long countByStatus(DeviceStatus status);
}
