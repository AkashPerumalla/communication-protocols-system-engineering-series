package com.sky2dev.day23.repository;

import com.sky2dev.day23.entity.Device;
import com.sky2dev.day23.entity.DeviceStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByStatus(DeviceStatus status);
}
