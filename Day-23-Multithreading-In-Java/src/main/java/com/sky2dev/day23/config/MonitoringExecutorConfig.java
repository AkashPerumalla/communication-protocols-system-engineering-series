package com.sky2dev.day23.config;

import com.sky2dev.day23.simulation.TelemetrySample;
import com.sky2dev.day23.threading.NamedThreadFactory;
import com.sky2dev.day23.threading.MonitoringStatistics;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringExecutorConfig {

    @Bean(name = "monitoringExecutor", destroyMethod = "shutdown")
    public ExecutorService monitoringExecutor() {
        return Executors.newFixedThreadPool(5, new NamedThreadFactory("Telemetry-Thread-"));
    }

    @Bean(name = "alarmExecutor", destroyMethod = "shutdown")
    public ExecutorService alarmExecutor() {
        return Executors.newFixedThreadPool(3, new NamedThreadFactory("Alarm-Thread-"));
    }

    @Bean(name = "notificationExecutor", destroyMethod = "shutdown")
    public ExecutorService notificationExecutor() {
        return Executors.newFixedThreadPool(3, new NamedThreadFactory("Notification-Thread-"));
    }

    @Bean(name = "scheduledExecutor", destroyMethod = "shutdown")
    public ScheduledExecutorService scheduledExecutor() {
        return Executors.newScheduledThreadPool(2, new NamedThreadFactory("Dashboard-Thread-"));
    }

    @Bean
    public BlockingQueue<TelemetrySample> telemetryQueue() {
        return new LinkedBlockingQueue<>(1024);
    }

    @Bean
    public MonitoringStatistics monitoringStatistics() {
        return new MonitoringStatistics();
    }
}
