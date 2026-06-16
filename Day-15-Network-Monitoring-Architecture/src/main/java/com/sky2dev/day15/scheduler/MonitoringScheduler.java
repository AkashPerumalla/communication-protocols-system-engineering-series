package com.sky2dev.day15.scheduler;

import com.sky2dev.day15.service.DevicePollingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringScheduler {

    private static final Logger log = LoggerFactory.getLogger(MonitoringScheduler.class);

    private final DevicePollingService devicePollingService;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void pollDevices() {
        log.debug("Running scheduled device polling cycle");
        devicePollingService.pollAllDevices();
    }
}
