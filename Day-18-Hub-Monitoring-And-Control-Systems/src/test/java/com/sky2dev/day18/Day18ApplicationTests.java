package com.sky2dev.day18;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day18.repository.AlarmRepository;
import com.sky2dev.day18.repository.ControlCommandRepository;
import com.sky2dev.day18.repository.DeviceMetricRepository;
import com.sky2dev.day18.repository.HubDeviceRepository;
import com.sky2dev.day18.repository.NotificationEventRepository;
import com.sky2dev.day18.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Day18ApplicationTests {

    @Autowired
    private HubDeviceRepository hubDeviceRepository;

    @Autowired
    private DeviceMetricRepository deviceMetricRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private NotificationEventRepository notificationEventRepository;

    @Autowired
    private ControlCommandRepository controlCommandRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoadDeterministicSeedData() {
        assertThat(hubDeviceRepository.count()).isEqualTo(10);
        assertThat(deviceMetricRepository.count()).isEqualTo(30);
        assertThat(alarmRepository.count()).isEqualTo(12);
        assertThat(notificationEventRepository.count()).isEqualTo(20);
        assertThat(controlCommandRepository.count()).isEqualTo(10);
        assertThat(reportRepository.count()).isEqualTo(5);
    }

    @Test
    void shouldExposeDashboardMarker() throws Exception {
        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("DASHBOARD UPDATED")));
    }

    @Test
    void shouldExposeNocMarker() throws Exception {
        mockMvc.perform(get("/api/noc"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("NOC CONTROL CENTER")));
    }
}
