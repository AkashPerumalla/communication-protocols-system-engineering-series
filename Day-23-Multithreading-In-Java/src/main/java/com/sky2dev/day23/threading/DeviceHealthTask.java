package com.sky2dev.day23.threading;

import com.sky2dev.day23.service.DeviceService;

public class DeviceHealthTask implements Runnable {

    private final DeviceService deviceService;

    public DeviceHealthTask(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void run() {
        deviceService.runHealthCheckSweep();
    }
}
