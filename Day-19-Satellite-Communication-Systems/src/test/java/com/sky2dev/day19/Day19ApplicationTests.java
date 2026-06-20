package com.sky2dev.day19;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sky2dev.day19.repository.EarthStationRepository;
import com.sky2dev.day19.repository.LinkMetricRepository;
import com.sky2dev.day19.repository.SatComAlarmRepository;
import com.sky2dev.day19.repository.SatelliteLinkRepository;
import com.sky2dev.day19.repository.SatelliteRepository;
import com.sky2dev.day19.repository.TransponderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "spring.task.scheduling.enabled=false")
@AutoConfigureMockMvc
class Day19ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SatelliteRepository satelliteRepository;

    @Autowired
    private EarthStationRepository earthStationRepository;

    @Autowired
    private TransponderRepository transponderRepository;

    @Autowired
    private SatelliteLinkRepository satelliteLinkRepository;

    @Autowired
    private LinkMetricRepository linkMetricRepository;

    @Autowired
    private SatComAlarmRepository satComAlarmRepository;

    @Test
    void deterministicSeedDataShouldLoad() {
        assertThat(satelliteRepository.count()).isEqualTo(3);
        assertThat(earthStationRepository.count()).isEqualTo(6);
        assertThat(transponderRepository.count()).isEqualTo(3);
        assertThat(satelliteLinkRepository.count()).isEqualTo(5);
        assertThat(linkMetricRepository.count()).isEqualTo(5);
        assertThat(satComAlarmRepository.count()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void shouldExposeSatelliteNetworkMarker() throws Exception {
        mockMvc.perform(get("/api/satellites"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SATELLITE NETWORK ACTIVE")));
    }

    @Test
    void shouldExposeDashboardMarker() throws Exception {
        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SATCOM NOC DASHBOARD")));
    }

    @Test
    void shouldCalculateLinkBudget() throws Exception {
        String payload = """
                {
                  "linkName": "Test-Link",
                  "txPowerDbw": 18.5,
                  "txAntennaGainDbi": 43.2,
                  "implementationLossDb": 1.2,
                  "slantRangeKm": 38500,
                  "frequencyGhz": 14.0,
                  "gOverTDbPerK": 15.5,
                  "noiseTemperatureK": 320,
                  "requiredCnDb": 10.5
                }
                """;

        mockMvc.perform(post("/api/link-budget/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("LINK BUDGET CALCULATED")));
    }

    @Test
    void shouldSimulateAlarmAndTroubleshoot() throws Exception {
        String alarmPayload = """
                {
                  "linkName": "Hub-to-Site-5",
                  "cnDb": 7.0,
                  "ebNo": 4.8,
                  "ber": 0.00032,
                  "rxPowerDbm": -76.0,
                  "lockStatus": "LOST"
                }
                """;

        mockMvc.perform(post("/api/alarms/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(alarmPayload))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SATCOM ALARM GENERATED")));

        String troublePayload = """
                {
                  "linkName": "Hub-to-Site-5",
                  "cnDb": 7.0,
                  "ebNo": 4.8,
                  "ber": 0.00032,
                  "rxPowerDbm": -76.0,
                  "lockStatus": "LOST"
                }
                """;

        mockMvc.perform(post("/api/troubleshooting/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(troublePayload))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ROOT CAUSE IDENTIFIED")));
    }
}
