package com.sky2dev.day23.threading;

import com.sky2dev.day23.service.MonitoringPipelineService;
import com.sky2dev.day23.simulation.TelemetrySample;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlarmProcessorTask implements Runnable {

    private final BlockingQueue<TelemetrySample> queue;
    private final AtomicBoolean running;
    private final MonitoringPipelineService pipelineService;

    public AlarmProcessorTask(
            BlockingQueue<TelemetrySample> queue,
            AtomicBoolean running,
            MonitoringPipelineService pipelineService
    ) {
        this.queue = queue;
        this.running = running;
        this.pipelineService = pipelineService;
    }

    @Override
    public void run() {
        while (running.get() && !Thread.currentThread().isInterrupted()) {
            try {
                TelemetrySample sample = queue.poll(1, TimeUnit.SECONDS);
                if (sample != null) {
                    pipelineService.processSample(sample);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
