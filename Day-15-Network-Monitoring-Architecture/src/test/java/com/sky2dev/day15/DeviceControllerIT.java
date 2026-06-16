package com.sky2dev.day15;

import com.sky2dev.day15.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.task.scheduling.enabled=false")
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.task.scheduling.enabled=false")
class DeviceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void shouldReturnAllDevices() throws Exception {
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    void shouldReturnSingleDevice() throws Exception {
        Long deviceId = deviceRepository.findAll().get(0).getId();
        mockMvc.perform(get("/api/devices/{id}", deviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deviceId.intValue()));
    }
}
