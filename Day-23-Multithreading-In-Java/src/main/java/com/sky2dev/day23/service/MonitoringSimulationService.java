package com.sky2dev.day23.service;

import com.sky2dev.day23.simulation.TelemetrySample;
import com.sky2dev.day23.threading.AlarmProcessorTask;
import com.sky2dev.day23.threading.DashboardUpdateTask;
import com.sky2dev.day23.threading.DeviceHealthTask;
import com.sky2dev.day23.threading.ReportGenerationTask;
import com.sky2dev.day23.threading.TelemetryCollectorTask;
import jakarta.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringSimulationService {

    @Qualifier("monitoringExecutor")
    private final ExecutorService monitoringExecutor;

    @Qualifier("alarmExecutor")
    private final ExecutorService alarmExecutor;

    @Qualifier("scheduledExecutor")
    private final ScheduledExecutorService scheduledExecutor;

    private final BlockingQueue<TelemetrySample> telemetryQueue;
    private final MonitoringPipelineService pipelineService;
    private final DashboardService dashboardService;
    private final ReportService reportService;
    private final DeviceService deviceService;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicLong sequence = new AtomicLong(0);
    private final List<Future<?>> workerFutures = new ArrayList<>();
    private final List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

    public synchronized boolean startSimulation() {
        if (running.get()) {
            return false;
        }

        running.set(true);
        workerFutures.add(monitoringExecutor.submit(new TelemetryCollectorTask(telemetryQueue, running, sequence)));
        workerFutures.add(alarmExecutor.submit(new AlarmProcessorTask(telemetryQueue, running, pipelineService)));
        workerFutures.add(alarmExecutor.submit(new AlarmProcessorTask(telemetryQueue, running, pipelineService)));

        scheduledFutures.add(scheduledExecutor.scheduleAtFixedRate(
                new DashboardUpdateTask(dashboardService), 1, 4, TimeUnit.SECONDS));
        scheduledFutures.add(scheduledExecutor.scheduleAtFixedRate(
                new ReportGenerationTask(reportService), 2, 10, TimeUnit.SECONDS));
        scheduledFutures.add(scheduledExecutor.scheduleAtFixedRate(
                new DeviceHealthTask(deviceService), 3, 8, TimeUnit.SECONDS));

        return true;
    }

    public synchronized boolean stopSimulation() {
        if (!running.get()) {
            return false;
        }

        running.set(false);
        for (Future<?> future : workerFutures) {
            future.cancel(true);
        }
        for (ScheduledFuture<?> future : scheduledFutures) {
            future.cancel(true);
        }
        workerFutures.clear();
        scheduledFutures.clear();
        telemetryQueue.clear();
        return true;
    }

    public boolean isRunning() {
        return running.get();
    }

    public void runMaintenanceTick() {
        if (running.get()) {
            reportService.runBackgroundMaintenance();
        }
    }

    @PreDestroy
    public void shutdown() {
        stopSimulation();
    }
}
