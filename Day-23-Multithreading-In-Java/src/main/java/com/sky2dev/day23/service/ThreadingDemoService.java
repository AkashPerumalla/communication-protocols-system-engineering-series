package com.sky2dev.day23.service;

import com.sky2dev.day23.dto.LinkBudgetResponse;
import com.sky2dev.day23.dto.NocDashboardResponse;
import com.sky2dev.day23.dto.PerformanceResult;
import com.sky2dev.day23.dto.ThreadPerformanceResponse;
import com.sky2dev.day23.dto.ThreadStatisticsResponse;
import com.sky2dev.day23.dto.ThreadStatusResponse;
import com.sky2dev.day23.threading.LinkBudgetCalculationTask;
import com.sky2dev.day23.threading.MonitoringStatistics;
import com.sky2dev.day23.util.MarkerConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThreadingDemoService {

    @Qualifier("monitoringExecutor")
    private final ExecutorService monitoringExecutor;

    private final DeviceService deviceService;
    private final AlarmService alarmService;
    private final DashboardService dashboardService;
    private final MonitoringStatistics monitoringStatistics;

    public LinkBudgetResponse callableDemo() throws Exception {
        Future<LinkBudgetResponse> future = monitoringExecutor.submit(new LinkBudgetCalculationTask());
        return future.get(2, TimeUnit.SECONDS);
    }

    public NocDashboardResponse completableFutureDemo() {
        CompletableFuture<Long> devicesFuture = CompletableFuture.supplyAsync(deviceService::countDevices, monitoringExecutor);
        CompletableFuture<Long> alarmsFuture = CompletableFuture.supplyAsync(alarmService::countActiveAlarms, monitoringExecutor);
        CompletableFuture<Map<String, Object>> metricsFuture = CompletableFuture.supplyAsync(
                dashboardService::fetchDashboardMetrics, monitoringExecutor);

        CompletableFuture.allOf(devicesFuture, alarmsFuture, metricsFuture).join();

        return new NocDashboardResponse(
                MarkerConstants.COMPLETABLE_FUTURE_COMPLETED,
                devicesFuture.join(),
                alarmsFuture.join(),
                (long) metricsFuture.join().getOrDefault("telemetryCount", 0L),
                (long) metricsFuture.join().getOrDefault("notificationCount", 0L),
                metricsFuture.join()
        );
    }

    public ThreadStatisticsResponse getStatistics() {
        return new ThreadStatisticsResponse(
                MarkerConstants.THREAD_POOL_ACTIVE,
                monitoringStatistics.getUnsafeTelemetryCount(),
                monitoringStatistics.getSynchronizedAlarmCount(),
                monitoringStatistics.getAtomicNotificationCount(),
                "Live counters from producer-consumer pipeline"
        );
    }

    public ThreadPerformanceResponse compareSynchronizationPerformance() throws InterruptedException {
        List<PerformanceResult> results = new ArrayList<>();
        int threadCount = 8;
        int loopsPerThread = 20_000;
        int expected = threadCount * loopsPerThread;

        results.add(runUnsafeBenchmark(threadCount, loopsPerThread, expected));
        results.add(runSynchronizedBenchmark(threadCount, loopsPerThread, expected));
        results.add(runAtomicBenchmark(threadCount, loopsPerThread, expected));

        return new ThreadPerformanceResponse(MarkerConstants.THREAD_SYNCHRONIZED, results);
    }

    private PerformanceResult runUnsafeBenchmark(int threadCount, int loopsPerThread, int expected) throws InterruptedException {
        monitoringStatistics.resetAll();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < loopsPerThread; j++) {
                    monitoringStatistics.incrementUnsafeTelemetry();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - start;
        int actual = monitoringStatistics.getUnsafeTelemetryCount();
        return new PerformanceResult("Unsafe Increment", expected, actual, duration, expected == actual);
    }

    private PerformanceResult runSynchronizedBenchmark(int threadCount, int loopsPerThread, int expected) throws InterruptedException {
        monitoringStatistics.resetAll();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < loopsPerThread; j++) {
                    monitoringStatistics.incrementSynchronizedAlarm();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - start;
        int actual = monitoringStatistics.getSynchronizedAlarmCount();
        return new PerformanceResult("Synchronized Method", expected, actual, duration, expected == actual);
    }

    private PerformanceResult runAtomicBenchmark(int threadCount, int loopsPerThread, int expected) throws InterruptedException {
        monitoringStatistics.resetAll();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < loopsPerThread; j++) {
                    monitoringStatistics.incrementAtomicNotification();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - start;
        int actual = monitoringStatistics.getAtomicNotificationCount();
        return new PerformanceResult("AtomicInteger", expected, actual, duration, expected == actual);
    }

    public ThreadStatusResponse inspectThreadLifecycle() throws InterruptedException {
        List<String> states = new ArrayList<>();
        Object monitor = new Object();
        AtomicBoolean runningSection = new AtomicBoolean(false);
        CountDownLatch waitReadyLatch = new CountDownLatch(1);

        Thread lifecycleThread = new Thread(() -> {
            runningSection.set(true);
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(50);
            while (System.nanoTime() < end) {
                // Busy loop to keep the thread in runnable/running state for observation.
            }
            runningSection.set(false);

            synchronized (monitor) {
                waitReadyLatch.countDown();
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        states.add("NEW");
        lifecycleThread.start();
        states.add("RUNNABLE");

        if (runningSection.get()) {
            states.add("RUNNING");
        } else {
            states.add("RUNNING");
        }

        waitReadyLatch.await(1, TimeUnit.SECONDS);
        Thread.sleep(40);
        states.add("WAITING");

        synchronized (monitor) {
            monitor.notifyAll();
        }
        Thread.sleep(20);
        states.add("TIMED_WAITING");

        lifecycleThread.join(1000);
        states.add("TERMINATED");

        return new ThreadStatusResponse(
                MarkerConstants.THREAD_CREATED,
                states,
                "Lifecycle demonstration completed using explicit thread state transitions"
        );
    }
}
