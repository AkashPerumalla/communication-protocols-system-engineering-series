package com.sky2dev.day23.threading;

import java.util.concurrent.atomic.AtomicInteger;

public class MonitoringStatistics {

    private int unsafeTelemetryCount;
    private int synchronizedAlarmCount;
    private final AtomicInteger atomicNotificationCount = new AtomicInteger(0);

    public void incrementUnsafeTelemetry() {
        int current = unsafeTelemetryCount;
        Thread.yield();
        unsafeTelemetryCount = current + 1;
    }

    public synchronized void incrementSynchronizedAlarm() {
        synchronizedAlarmCount++;
    }

    public void incrementAtomicNotification() {
        atomicNotificationCount.incrementAndGet();
    }

    public synchronized void resetAll() {
        unsafeTelemetryCount = 0;
        synchronizedAlarmCount = 0;
        atomicNotificationCount.set(0);
    }

    public int getUnsafeTelemetryCount() {
        return unsafeTelemetryCount;
    }

    public synchronized int getSynchronizedAlarmCount() {
        return synchronizedAlarmCount;
    }

    public int getAtomicNotificationCount() {
        return atomicNotificationCount.get();
    }
}
