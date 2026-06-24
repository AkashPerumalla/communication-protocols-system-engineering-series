package com.sky2dev.day23;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day23.dto.LinkBudgetResponse;
import com.sky2dev.day23.dto.NocDashboardResponse;
import com.sky2dev.day23.service.ThreadingDemoService;
import com.sky2dev.day23.util.MarkerConstants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.task.scheduling.enabled=false",
                "app.scheduling.enabled=false"
        }
)
class Day23ApplicationTests {

    @Autowired
    private ThreadingDemoService threadingDemoService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    @Qualifier("monitoringExecutor")
    private ExecutorService monitoringExecutor;

    @Autowired
    @Qualifier("alarmExecutor")
    private ExecutorService alarmExecutor;

    @Autowired
    @Qualifier("notificationExecutor")
    private ExecutorService notificationExecutor;

    @Autowired
    @Qualifier("scheduledExecutor")
    private ScheduledExecutorService scheduledExecutor;

    @Test
    void contextLoads() {
        assertThat(threadingDemoService).isNotNull();
    }

    @Test
    void threadPoolsCreated() {
        assertThat(monitoringExecutor).isNotNull();
        assertThat(alarmExecutor).isNotNull();
        assertThat(notificationExecutor).isNotNull();
        assertThat(scheduledExecutor).isNotNull();
    }

    @Test
    void callableWorks() throws Exception {
        LinkBudgetResponse response = threadingDemoService.callableDemo();
        assertThat(response.marker()).isEqualTo(MarkerConstants.CALLABLE_EXECUTED);
        assertThat(response.eirp()).isGreaterThan(0);
        assertThat(response.cn()).isGreaterThan(0);
        assertThat(response.linkMargin()).isGreaterThan(0);
    }

    @Test
    void completableFutureWorks() {
        NocDashboardResponse response = threadingDemoService.completableFutureDemo();
        assertThat(response.marker()).isEqualTo(MarkerConstants.COMPLETABLE_FUTURE_COMPLETED);
        assertThat(response.totalDevices()).isEqualTo(10);
        assertThat(response.metrics()).isNotEmpty();
    }

    @Test
    void restApisReturnMarkers() {
        assertThat(getBody("/api/devices")).contains(MarkerConstants.THREAD_POOL_ACTIVE);
        assertThat(getBody("/api/telemetry")).contains(MarkerConstants.TELEMETRY_COLLECTED);
        assertThat(getBody("/api/alarms")).contains(MarkerConstants.ALARM_GENERATED);
        assertThat(getBody("/api/dashboard")).contains(MarkerConstants.MULTITHREADING_ACTIVE);
        assertThat(getBody("/api/threads/status")).contains(MarkerConstants.THREAD_CREATED);
        assertThat(getBody("/api/threads/statistics")).contains(MarkerConstants.THREAD_POOL_ACTIVE);
        assertThat(getBody("/api/threads/performance")).contains(MarkerConstants.THREAD_SYNCHRONIZED);
        assertThat(getBody("/api/threads/callable-demo")).contains(MarkerConstants.CALLABLE_EXECUTED);
        assertThat(getBody("/api/threads/completable-future-demo")).contains(MarkerConstants.COMPLETABLE_FUTURE_COMPLETED);
    }

    private String getBody(String uri) {
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        return response.getBody();
    }
}
