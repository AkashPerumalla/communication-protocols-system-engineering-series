package com.sky2dev.day15;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.task.scheduling.enabled=false")
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.task.scheduling.enabled=false")
class DashboardControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void dashboardShouldExposeAggregates() throws Exception {
        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalDevices").value(8))
                .andExpect(jsonPath("$.onlineDevices").value(7))
                .andExpect(jsonPath("$.offlineDevices").value(1))
                .andExpect(jsonPath("$.activeAlerts").value(3))
                .andExpect(jsonPath("$.criticalAlerts").value(1))
                .andExpect(jsonPath("$.averageCpu").value(greaterThan(0.0)));
    }
}
