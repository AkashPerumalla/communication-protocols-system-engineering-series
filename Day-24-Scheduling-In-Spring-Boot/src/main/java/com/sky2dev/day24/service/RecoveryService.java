package com.sky2dev.day24.service;

import com.sky2dev.day24.entity.Device;
import com.sky2dev.day24.entity.DeviceStatus;
import com.sky2dev.day24.entity.NotificationChannel;
import com.sky2dev.day24.entity.NotificationEvent;
import com.sky2dev.day24.repository.DeviceRepository;
import com.sky2dev.day24.repository.NotificationEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecoveryService {

    private final DeviceRepository deviceRepository;
    private final NotificationEventRepository notificationEventRepository;

    @Transactional
    public List<Device> autoRecoverDevices() {
        List<Device> devices = deviceRepository.findAll();
        List<Device> recovered = new ArrayList<>();

        for (Device device : devices) {
            if (device.getStatus() != DeviceStatus.OFFLINE) {
                continue;
            }

            device.setStatus(DeviceStatus.ONLINE);
            device.setLastSeen(Instant.now());
            recovered.add(device);

            notificationEventRepository.save(NotificationEvent.builder()
                    .channel(NotificationChannel.SLACK)
                    .message("AUTO RECOVERY COMPLETE for " + device.getDeviceName())
                    .status("SENT")
                    .timestamp(Instant.now())
                    .build());
        }

        if (!recovered.isEmpty()) {
            deviceRepository.saveAll(recovered);
        }
        return recovered;
    }
}
