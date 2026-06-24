package com.sky2dev.day23.threading;

import com.sky2dev.day23.simulation.TelemetrySample;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class TelemetryCollectorTask implements Runnable {

    private final BlockingQueue<TelemetrySample> queue;
    private final AtomicBoolean running;
    private final AtomicLong sequence;

    public TelemetryCollectorTask(BlockingQueue<TelemetrySample> queue, AtomicBoolean running, AtomicLong sequence) {
        this.queue = queue;
        this.running = running;
        this.sequence = sequence;
    }

    @Override
    public void run() {
        while (running.get() && !Thread.currentThread().isInterrupted()) {
            long seq = sequence.incrementAndGet();
            long deviceId = ((seq - 1) % 10) + 1;
            TelemetrySample sample = new TelemetrySample(
                    deviceId,
                    25.0 + (seq % 60),
                    30.0 + (seq % 50),
                    40.0 + (seq % 45),
                    -65.0 + (seq % 20),
                    Instant.now()
            );
            try {
                queue.offer(sample, 500, TimeUnit.MILLISECONDS);
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
