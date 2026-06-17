package com.sky2dev.day16.repository;

import com.sky2dev.day16.model.ManagedDevice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagedDeviceRepository extends JpaRepository<ManagedDevice, Long> {
    Optional<ManagedDevice> findByDeviceCode(String deviceCode);
}
