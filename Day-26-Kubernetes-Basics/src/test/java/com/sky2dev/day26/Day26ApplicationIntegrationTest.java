package com.sky2dev.day26;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sky2dev.day26.repository.AlarmEventRepository;
import com.sky2dev.day26.repository.DeploymentStatusRepository;
import com.sky2dev.day26.repository.DeviceRepository;
import com.sky2dev.day26.repository.NotificationEventRepository;
import com.sky2dev.day26.repository.TelemetryRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "app.scheduling.enabled=false")
@AutoConfigureMockMvc
class Day26ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TelemetryRecordRepository telemetryRecordRepository;

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    @Autowired
    private NotificationEventRepository notificationEventRepository;

    @Autowired
        private DeploymentStatusRepository deploymentStatusRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void deterministicSeedCountsShouldMatch() {
        org.junit.jupiter.api.Assertions.assertEquals(10, deviceRepository.count());
        org.junit.jupiter.api.Assertions.assertEquals(100, telemetryRecordRepository.count());
        org.junit.jupiter.api.Assertions.assertEquals(20, alarmEventRepository.count());
        org.junit.jupiter.api.Assertions.assertEquals(20, notificationEventRepository.count());
                org.junit.jupiter.api.Assertions.assertEquals(5, deploymentStatusRepository.count());
    }

    @Test
    void shouldExposeCoreApisWithMarkers() throws Exception {
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("POD_RUNNING"))
                .andExpect(jsonPath("$.data", hasSize(10)));

        mockMvc.perform(get("/api/telemetry"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("CONFIGMAP_LOADED"));

        mockMvc.perform(get("/api/alarms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("APPLICATION_HEALTHY"));

        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("SERVICE_EXPOSED"))
                .andExpect(jsonPath("$.data.clusterStatus").exists());

        mockMvc.perform(get("/api/deployments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("DEPLOYMENT_CREATED"));

        mockMvc.perform(get("/api/cluster/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("CLUSTER_READY"));
    }

    @Test
    void shouldExecuteDeploymentLifecycleApis() throws Exception {
        mockMvc.perform(post("/api/deployment/scale/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("DEPLOYMENT_CREATED"))
                .andExpect(jsonPath("$.data.desiredReplicas").value(4));

        mockMvc.perform(post("/api/deployment/rolling-update"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("ROLLING_UPDATE_STARTED"))
                .andExpect(jsonPath("$.data.rolloutState").value("ROLLING_UPDATE_IN_PROGRESS"));

        mockMvc.perform(post("/api/deployment/rollback"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("ROLLBACK_COMPLETED"));
    }

    @Test
    void shouldExposePlatformStatus() throws Exception {
        mockMvc.perform(get("/api/platform/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("KUBERNETES_READY"))
                .andExpect(jsonPath("$.message", containsString("APPLICATION_HEALTHY")))
                .andExpect(jsonPath("$.data.status").value("UP"));
    }
}
