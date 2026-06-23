package com.sky2dev.day22.repository;

import com.sky2dev.day22.entity.Device;
import com.sky2dev.day22.entity.DeviceStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    long countByStatus(DeviceStatus status);

    List<Device> findAllByStatus(DeviceStatus status);
}
