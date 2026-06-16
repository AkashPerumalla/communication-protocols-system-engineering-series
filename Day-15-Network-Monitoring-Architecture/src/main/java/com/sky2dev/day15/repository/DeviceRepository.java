package com.sky2dev.day15.repository;

import com.sky2dev.day15.entity.Device;
import com.sky2dev.day15.entity.DeviceStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByHostname(String hostname);

    List<Device> findByStatus(DeviceStatus status);
}
