package com.sky2dev.day16.service;

import com.sky2dev.day16.model.DeviceState;
import com.sky2dev.day16.model.DeviceType;
import com.sky2dev.day16.model.ManagedDevice;
import com.sky2dev.day16.model.TelemetryRecord;
import com.sky2dev.day16.repository.ManagedDeviceRepository;
import com.sky2dev.day16.repository.TelemetryRecordRepository;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TelemetryGeneratorService {

    private final ManagedDeviceRepository managedDeviceRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlarmGenerationService alarmGenerationService;
    private final AtomicLong sequence = new AtomicLong(1);

    @Transactional
    public List<TelemetryRecord> captureAll() {
        return managedDeviceRepository.findAll().stream().map(this::capture).toList();
    }

    @Transactional
    public TelemetryRecord capture(ManagedDevice device) {
        long tick = sequence.getAndIncrement();
        boolean degraded = device.getState() == DeviceState.DEGRADED || device.getState() == DeviceState.RECOVERING;
        double cpu = baseMetric(18, tick, 70);
        double memory = baseMetric(32, tick, 80);
        double temperature = baseMetric(35, tick / 2.0, 79);
        double power = baseMetric(96, tick / 4.0, 82);
        String interfaceStatus = device.isInterfaceEnabled() ? "UP" : "DOWN";
        Double rfPower = null;
        Double ber = null;
        Boolean carrierLock = null;
        Long frequency = null;
        Long symbolRate = null;
        Double ebNo = null;
        String bucStatus = null;
        String lnbStatus = null;
        String modemStatus = null;
        Double uplinkPower = null;
        Double downlinkPower = null;

        if (device.getDeviceType() == DeviceType.MODEM_01 || device.getDeviceType() == DeviceType.BUC_01 || device.getDeviceType() == DeviceType.LNB_01) {
            frequency = device.getCurrentFrequencyMHz();
            symbolRate = device.getCurrentSymbolRateKsps();
            rfPower = degraded ? 31.8 : 47.2;
            ber = degraded ? 0.0009d : 0.0000007d;
            carrierLock = !degraded && device.isCarrierEnabled();
            ebNo = degraded ? 3.2d : 9.6d;
            bucStatus = device.getDeviceType() == DeviceType.BUC_01 ? (device.isCarrierEnabled() ? "TRANSMITTING" : "IDLE") : null;
            lnbStatus = device.getDeviceType() == DeviceType.LNB_01 ? (carrierLock ? "LOCKED" : "SEARCHING") : null;
            modemStatus = device.isModemHealthy() ? "READY" : "FAULT";
            uplinkPower = device.getDeviceType() != DeviceType.LNB_01 ? (degraded ? 8.8d : 14.5d) : null;
            downlinkPower = device.getDeviceType() != DeviceType.BUC_01 ? (degraded ? 9.2d : 15.3d) : null;
            if (device.getState() == DeviceState.RECOVERING) {
                ber = 0.0000002d;
                carrierLock = true;
                ebNo = 10.4d;
                modemStatus = "READY";
                rfPower = 49.1d;
            }
        }

        TelemetryRecord record = TelemetryRecord.builder()
                .device(device)
                .collectedAt(Instant.now())
                .cpu(cpu)
                .memory(memory)
                .temperature(temperature)
                .power(power)
                .interfaceStatus(interfaceStatus)
                .rfPower(rfPower)
                .ber(ber)
                .carrierLock(carrierLock)
                .frequencyMHz(frequency)
                .symbolRateKsps(symbolRate)
                .ebNo(ebNo)
                .bucStatus(bucStatus)
                .lnbStatus(lnbStatus)
                .modemStatus(modemStatus)
                .uplinkPower(uplinkPower)
                .downlinkPower(downlinkPower)
                .sourceScenario("DETERMINISTIC_SIMULATION")
                .build();

        telemetryRecordRepository.save(record);
        device.setCurrentCpu(cpu);
        device.setCurrentMemory(memory);
        device.setCurrentTemperature(temperature);
        device.setCurrentPower(power);
        device.setCurrentInterfaceStatus(interfaceStatus);
        if (modemStatus != null) {
            device.setCurrentModemStatus(modemStatus);
        }
        managedDeviceRepository.save(device);
        alarmGenerationService.evaluateTelemetry(record);
        return record;
    }

    @Transactional(readOnly = true)
    public List<TelemetryRecord> recentTelemetry() {
        return telemetryRecordRepository.findTop50ByOrderByCollectedAtDesc();
    }

    private double baseMetric(double base, double modifier, double ceiling) {
        return Math.min(base + (modifier % 9.0d), ceiling);
    }
}
