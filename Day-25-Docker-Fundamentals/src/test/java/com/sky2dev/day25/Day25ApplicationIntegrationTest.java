package com.sky2dev.day25;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sky2dev.day25.repository.AlarmEventRepository;
import com.sky2dev.day25.repository.DeviceRepository;
import com.sky2dev.day25.repository.NotificationEventRepository;
import com.sky2dev.day25.repository.ReportRecordRepository;
import com.sky2dev.day25.repository.TelemetryRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class Day25ApplicationIntegrationTest {

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
    private ReportRecordRepository reportRecordRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void deterministicSeedCountsShouldMatch() {
        org.junit.jupiter.api.Assertions.assertEquals(10, deviceRepository.count());
        org.junit.jupiter.api.Assertions.assertEquals(100, telemetryRecordRepository.count());
        org.junit.jupiter.api.Assertions.assertEquals(20, alarmEventRepository.count());
        org.junit.jupiter.api.Assertions.assertEquals(20, notificationEventRepository.count());
        org.junit.jupiter.api.Assertions.assertEquals(10, reportRecordRepository.count());
    }

    @Test
    void shouldExposeCoreApisWithMarkers() throws Exception {
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("APPLICATION RUNNING"))
                .andExpect(jsonPath("$.data", hasSize(10)));

        mockMvc.perform(get("/api/telemetry"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("TELEMETRY COLLECTED"));

        mockMvc.perform(get("/api/alarms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("ALARM GENERATED"));

        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("DASHBOARD UPDATED"))
                .andExpect(jsonPath("$.data.notificationMarker").value("NOTIFICATION SENT"));

        mockMvc.perform(get("/api/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("REPORT GENERATED"));
    }

    @Test
    void shouldCreateAndUpdateDevice() throws Exception {
        String createBody = """
                {
                  "name":"Router-03",
                  "ipAddress":"10.20.1.3",
                  "deviceType":"ROUTER",
                  "status":"ONLINE",
                  "region":"Hyderabad"
                }
                """;

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("DEVICE CREATED"))
                .andExpect(jsonPath("$.data.name").value("Router-03"));

        Long id = deviceRepository.findByName("Router-03").orElseThrow().getId();

        String updateBody = """
                {
                  "name":"Router-03",
                  "ipAddress":"10.20.1.33",
                  "deviceType":"ROUTER",
                  "status":"DEGRADED",
                  "region":"Hyderabad"
                }
                """;

        mockMvc.perform(put("/api/devices/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("DEVICE CREATED"))
                .andExpect(jsonPath("$.data.ipAddress").value("10.20.1.33"));
    }

    @Test
    void shouldExposePlatformStatus() throws Exception {
        mockMvc.perform(get("/api/platform/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker").value("DOCKER READY"))
                .andExpect(jsonPath("$.message", containsString("APPLICATION RUNNING")))
                .andExpect(jsonPath("$.data.status").value("UP"));
    }
}
