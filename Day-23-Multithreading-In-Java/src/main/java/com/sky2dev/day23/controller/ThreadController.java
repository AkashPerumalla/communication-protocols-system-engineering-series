package com.sky2dev.day23.controller;

import com.sky2dev.day23.dto.ApiResponse;
import com.sky2dev.day23.dto.LinkBudgetResponse;
import com.sky2dev.day23.dto.NocDashboardResponse;
import com.sky2dev.day23.dto.ThreadPerformanceResponse;
import com.sky2dev.day23.dto.ThreadStatisticsResponse;
import com.sky2dev.day23.dto.ThreadStatusResponse;
import com.sky2dev.day23.service.MonitoringSimulationService;
import com.sky2dev.day23.service.ThreadingDemoService;
import com.sky2dev.day23.util.MarkerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/threads")
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadingDemoService threadingDemoService;
    private final MonitoringSimulationService monitoringSimulationService;

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<ThreadStatusResponse>> threadStatus() throws InterruptedException {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.THREAD_CREATED,
                "Thread lifecycle transitions observed",
                threadingDemoService.inspectThreadLifecycle()
        ));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<ThreadStatisticsResponse>> statistics() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.THREAD_POOL_ACTIVE,
                "Shared statistics resource exposed",
                threadingDemoService.getStatistics()
        ));
    }

    @GetMapping("/performance")
    public ResponseEntity<ApiResponse<ThreadPerformanceResponse>> performance() throws InterruptedException {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.THREAD_SYNCHRONIZED,
                "Unsafe vs synchronized vs atomic comparison completed",
                threadingDemoService.compareSynchronizationPerformance()
        ));
    }

    @GetMapping("/callable-demo")
    public ResponseEntity<ApiResponse<LinkBudgetResponse>> callableDemo() throws Exception {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.CALLABLE_EXECUTED,
                "Callable and Future executed for link budget computation",
                threadingDemoService.callableDemo()
        ));
    }

    @GetMapping("/completable-future-demo")
    public ResponseEntity<ApiResponse<NocDashboardResponse>> completableFutureDemo() {
        return ResponseEntity.ok(ApiResponse.success(
                MarkerConstants.COMPLETABLE_FUTURE_COMPLETED,
                "CompletableFuture allOf aggregation completed",
                threadingDemoService.completableFutureDemo()
        ));
    }

    @PostMapping("/start-simulation")
    public ResponseEntity<ApiResponse<String>> startSimulation() {
        boolean started = monitoringSimulationService.startSimulation();
        String marker = started ? MarkerConstants.TASK_EXECUTED : MarkerConstants.THREAD_POOL_ACTIVE;
        String message = started ? "Simulation started with producer-consumer and scheduled task pools"
                : "Simulation already running";
        return ResponseEntity.ok(ApiResponse.success(marker, message, started ? "STARTED" : "ALREADY_RUNNING"));
    }

    @PostMapping("/stop-simulation")
    public ResponseEntity<ApiResponse<String>> stopSimulation() {
        boolean stopped = monitoringSimulationService.stopSimulation();
        String marker = stopped ? MarkerConstants.NOTIFICATION_SENT : MarkerConstants.THREAD_POOL_ACTIVE;
        String message = stopped ? "Simulation stopped gracefully and worker threads interrupted"
                : "Simulation already stopped";
        return ResponseEntity.ok(ApiResponse.success(marker, message, stopped ? "STOPPED" : "ALREADY_STOPPED"));
    }
}
