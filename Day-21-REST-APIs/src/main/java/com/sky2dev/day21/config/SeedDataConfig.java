package com.sky2dev.day21.config;

import com.sky2dev.day21.entity.Device;
import com.sky2dev.day21.entity.DeviceMetric;
import com.sky2dev.day21.entity.DeviceStatus;
import com.sky2dev.day21.entity.DeviceType;
import com.sky2dev.day21.repository.DeviceMetricRepository;
import com.sky2dev.day21.repository.DeviceRepository;
import com.sky2dev.day21.service.MetricService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SeedDataConfig {

    private final DeviceRepository deviceRepository;
    private final DeviceMetricRepository deviceMetricRepository;
    private final MetricService metricService;

    @Bean
    CommandLineRunner seedDay21Data() {
        return args -> {
            if (deviceRepository.count() > 0) {
                return;
            }

            List<Device> devices = deviceRepository.saveAll(List.of(
                    Device.builder().hostname("Router-01").ipAddress("10.21.0.11").deviceType(DeviceType.ROUTER).vendor("Cisco").model("ASR-920").firmwareVersion("17.09.03").location("NOC-Core-A").status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Router-02").ipAddress("10.21.0.12").deviceType(DeviceType.ROUTER).vendor("Juniper").model("MX204").firmwareVersion("23.2R1").location("NOC-Core-B").status(DeviceStatus.WARNING).build(),
                    Device.builder().hostname("Switch-01").ipAddress("10.21.1.21").deviceType(DeviceType.SWITCH).vendor("Arista").model("7050SX3").firmwareVersion("4.30.1F").location("Access-Pod-1").status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Switch-02").ipAddress("10.21.1.22").deviceType(DeviceType.SWITCH).vendor("Cisco").model("Catalyst-9300").firmwareVersion("17.12.1").location("Access-Pod-2").status(DeviceStatus.MAINTENANCE).build(),
                    Device.builder().hostname("Satellite-Modem-01").ipAddress("10.21.2.31").deviceType(DeviceType.MODEM).vendor("iDirect").model("Evolution-X7").firmwareVersion("8.4.2").location("Teleport-East").status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Satellite-Modem-02").ipAddress("10.21.2.32").deviceType(DeviceType.MODEM).vendor("Hughes").model("JUPITER-HT2600").firmwareVersion("4.6.1").location("Teleport-West").status(DeviceStatus.OFFLINE).build(),
                    Device.builder().hostname("Hub-01").ipAddress("10.21.3.41").deviceType(DeviceType.HUB).vendor("Comtech").model("CDM-760").firmwareVersion("2.11.0").location("Primary-Hub").status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("BUC-01").ipAddress("10.21.3.51").deviceType(DeviceType.BUC).vendor("CPI").model("B5KX").firmwareVersion("1.8.7").location("Primary-Hub").status(DeviceStatus.WARNING).build(),
                    Device.builder().hostname("LNB-01").ipAddress("10.21.3.61").deviceType(DeviceType.LNB).vendor("Norsat").model("3200HF").firmwareVersion("3.0.4").location("Teleport-East").status(DeviceStatus.ONLINE).build(),
                    Device.builder().hostname("Sensor-01").ipAddress("10.21.4.71").deviceType(DeviceType.SENSOR).vendor("Advantech").model("WISE-4610").firmwareVersion("5.2.0").location("Remote-Site-7").status(DeviceStatus.OFFLINE).build()));

            List<DeviceMetric> metrics = metricService.generateMetrics(devices);
            deviceMetricRepository.saveAll(metrics);
        };
    }
}
