package com.sky2dev.day16;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day16.model.Alarm;
import com.sky2dev.day16.model.CommandResult;
import com.sky2dev.day16.model.ManagedDevice;
import com.sky2dev.day16.model.TelemetryRecord;
import com.sky2dev.day16.repository.AlarmRepository;
import com.sky2dev.day16.repository.CommandResultRepository;
import com.sky2dev.day16.repository.ManagedDeviceRepository;
import com.sky2dev.day16.repository.TelemetryRecordRepository;
import com.sky2dev.day16.service.CommandExecutionService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Day16ApplicationTests {

    @Autowired
    private ManagedDeviceRepository managedDeviceRepository;

    @Autowired
    private TelemetryRecordRepository telemetryRecordRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private CommandResultRepository commandResultRepository;

    @Autowired
    private CommandExecutionService commandExecutionService;

    @Test
    void seedsDevicesTelemetryAndAutomationArtifacts() {
        List<ManagedDevice> devices = managedDeviceRepository.findAll();
        List<TelemetryRecord> telemetry = telemetryRecordRepository.findAll();
        List<Alarm> alarms = alarmRepository.findAll();
        List<CommandResult> commands = commandResultRepository.findAll();

        assertThat(devices).hasSize(8);
        assertThat(telemetry).isNotEmpty();
        assertThat(alarms).isNotEmpty();
        assertThat(commands).isNotEmpty();
    }

    @Test
    void restartCommandCreatesAuditTrail() {
        ManagedDevice device = managedDeviceRepository.findByDeviceCode("Router-01").orElseThrow();
        CommandResult result = commandExecutionService.restartDevice(device.getId());

        assertThat(result.getMarker()).isEqualTo("COMMAND EXECUTED");
        assertThat(result.getResultingState()).isEqualTo("ACTIVE");
    }
}
