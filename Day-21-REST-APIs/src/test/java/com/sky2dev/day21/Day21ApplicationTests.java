package com.sky2dev.day21;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class Day21ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetDevices() throws Exception {
        mockMvc.perform(get("/api/devices").with(httpBasic("viewer", "viewer123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.marker", is("DEVICE RETRIEVED")))
                .andExpect(jsonPath("$.data.totalElements", greaterThan(0)));
    }

    @Test
    void shouldGetDeviceById() throws Exception {
        mockMvc.perform(get("/api/devices/1").with(httpBasic("viewer", "viewer123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker", is("DEVICE RETRIEVED")))
                .andExpect(jsonPath("$.data.hostname", is("Router-01")));
    }

    @Test
    void shouldCreateDevice() throws Exception {
        mockMvc.perform(post("/api/devices")
                        .with(httpBasic("operator", "operator123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "hostname": "Gateway-01",
                                  "ipAddress": "10.21.5.81",
                                  "deviceType": "IOT_GATEWAY",
                                  "vendor": "Teltonika",
                                  "model": "RUTX11",
                                  "firmwareVersion": "7.06.2",
                                  "location": "Remote-Site-12",
                                  "status": "ONLINE"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marker", is("DEVICE CREATED")))
                .andExpect(jsonPath("$.message", is("VALIDATION PASSED. Device created successfully")))
                .andExpect(jsonPath("$.data.hostname", is("Gateway-01")));
    }

    @Test
    void shouldUpdateDevice() throws Exception {
        mockMvc.perform(put("/api/devices/1")
                        .with(httpBasic("operator", "operator123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "hostname": "Router-01",
                                  "ipAddress": "10.21.0.11",
                                  "deviceType": "ROUTER",
                                  "vendor": "Cisco",
                                  "model": "ASR-920",
                                  "firmwareVersion": "17.09.04",
                                  "location": "NOC-Core-A",
                                  "status": "MAINTENANCE"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker", is("DEVICE UPDATED")))
                .andExpect(jsonPath("$.data.status", is("MAINTENANCE")));
    }

    @Test
    void shouldDeleteDeviceAsAdmin() throws Exception {
        mockMvc.perform(delete("/api/devices/10").with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker", is("DEVICE DELETED")))
                .andExpect(jsonPath("$.data.deletedId", is(10)));
    }

    @Test
    void shouldGenerateDashboard() throws Exception {
        mockMvc.perform(get("/api/dashboard").with(httpBasic("viewer", "viewer123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marker", is("DASHBOARD GENERATED")))
                .andExpect(jsonPath("$.data.totalDevices", is(10)))
                .andExpect(jsonPath("$.data.onlineDevices", is(5)));
    }
}
