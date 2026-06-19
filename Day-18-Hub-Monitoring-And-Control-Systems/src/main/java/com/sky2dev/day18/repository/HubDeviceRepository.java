package com.sky2dev.day18.repository;

import com.sky2dev.day18.model.DeviceStatus;
import com.sky2dev.day18.model.HubDevice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubDeviceRepository extends JpaRepository<HubDevice, Long> {

    List<HubDevice> findByStatus(DeviceStatus status);
}
