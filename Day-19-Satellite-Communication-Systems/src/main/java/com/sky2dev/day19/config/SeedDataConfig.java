package com.sky2dev.day19.config;

import com.sky2dev.day19.model.AlarmSeverity;
import com.sky2dev.day19.model.AlarmStatus;
import com.sky2dev.day19.model.EarthStation;
import com.sky2dev.day19.model.FrequencyPlan;
import com.sky2dev.day19.model.LinkBudget;
import com.sky2dev.day19.model.LinkMetric;
import com.sky2dev.day19.model.LinkStatus;
import com.sky2dev.day19.model.LockStatus;
import com.sky2dev.day19.model.ModulationType;
import com.sky2dev.day19.model.PolarizationType;
import com.sky2dev.day19.model.SatComAlarm;
import com.sky2dev.day19.model.Satellite;
import com.sky2dev.day19.model.SatelliteLink;
import com.sky2dev.day19.model.SatelliteStatus;
import com.sky2dev.day19.model.StationStatus;
import com.sky2dev.day19.model.StationType;
import com.sky2dev.day19.model.Transponder;
import com.sky2dev.day19.model.TransponderStatus;
import com.sky2dev.day19.model.TroubleTicket;
import com.sky2dev.day19.model.TroubleTicketStatus;
import com.sky2dev.day19.repository.EarthStationRepository;
import com.sky2dev.day19.repository.FrequencyPlanRepository;
import com.sky2dev.day19.repository.LinkBudgetRepository;
import com.sky2dev.day19.repository.LinkMetricRepository;
import com.sky2dev.day19.repository.SatComAlarmRepository;
import com.sky2dev.day19.repository.SatelliteLinkRepository;
import com.sky2dev.day19.repository.SatelliteRepository;
import com.sky2dev.day19.repository.TransponderRepository;
import com.sky2dev.day19.repository.TroubleTicketRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SeedDataConfig {

    @Bean
    CommandLineRunner seedData(
            SatelliteRepository satelliteRepository,
            TransponderRepository transponderRepository,
            EarthStationRepository earthStationRepository,
            SatelliteLinkRepository satelliteLinkRepository,
            LinkMetricRepository linkMetricRepository,
            FrequencyPlanRepository frequencyPlanRepository,
            LinkBudgetRepository linkBudgetRepository,
            SatComAlarmRepository satComAlarmRepository,
            TroubleTicketRepository troubleTicketRepository
    ) {
        return args -> {
            if (satelliteRepository.count() > 0) {
                return;
            }

            Satellite insat = satelliteRepository.save(Satellite.builder()
                    .satelliteName("INSAT-4A")
                    .orbitalSlot("83E")
                    .coverageRegion("India and South Asia")
                    .transponderCount(12)
                    .status(SatelliteStatus.ACTIVE)
                    .build());
            Satellite gsat = satelliteRepository.save(Satellite.builder()
                    .satelliteName("GSAT-30")
                    .orbitalSlot("83E")
                    .coverageRegion("South Asia and Indian Ocean")
                    .transponderCount(24)
                    .status(SatelliteStatus.ACTIVE)
                    .build());
            Satellite intelsat = satelliteRepository.save(Satellite.builder()
                    .satelliteName("INTELSAT-20")
                    .orbitalSlot("68.5E")
                    .coverageRegion("Asia Pacific and Africa")
                    .transponderCount(32)
                    .status(SatelliteStatus.DEGRADED)
                    .build());

            EarthStation hub = earthStationRepository.save(station("Hub-Station", StationType.HUB, 17.3850, 78.4867, 9.0, StationStatus.ONLINE));
            EarthStation vsat1 = earthStationRepository.save(station("VSAT-Site-1", StationType.VSAT, 12.9716, 77.5946, 1.8, StationStatus.ONLINE));
            EarthStation vsat2 = earthStationRepository.save(station("VSAT-Site-2", StationType.VSAT, 13.0827, 80.2707, 1.8, StationStatus.ONLINE));
            EarthStation vsat3 = earthStationRepository.save(station("VSAT-Site-3", StationType.VSAT, 19.0760, 72.8777, 2.4, StationStatus.DEGRADED));
            EarthStation vsat4 = earthStationRepository.save(station("VSAT-Site-4", StationType.VSAT, 22.5726, 88.3639, 1.2, StationStatus.ONLINE));
            EarthStation vsat5 = earthStationRepository.save(station("VSAT-Site-5", StationType.VSAT, 28.6139, 77.2090, 1.2, StationStatus.MAINTENANCE));

            Transponder tp1 = transponderRepository.save(transponder(insat, 1, 36.0, 6.175, 4.025, PolarizationType.H, TransponderStatus.ALLOCATED));
            Transponder tp2 = transponderRepository.save(transponder(gsat, 2, 36.0, 6.210, 4.060, PolarizationType.V, TransponderStatus.AVAILABLE));
            Transponder tp3 = transponderRepository.save(transponder(intelsat, 3, 36.0, 6.250, 4.100, PolarizationType.H, TransponderStatus.CONGESTED));

            SatelliteLink l1 = satelliteLinkRepository.save(link("Hub-to-Site-1", hub, vsat1, insat, LinkStatus.UP));
            SatelliteLink l2 = satelliteLinkRepository.save(link("Hub-to-Site-2", hub, vsat2, gsat, LinkStatus.UP));
            SatelliteLink l3 = satelliteLinkRepository.save(link("Hub-to-Site-3", hub, vsat3, intelsat, LinkStatus.DEGRADED));
            SatelliteLink l4 = satelliteLinkRepository.save(link("Hub-to-Site-4", hub, vsat4, gsat, LinkStatus.UP));
            SatelliteLink l5 = satelliteLinkRepository.save(link("Hub-to-Site-5", hub, vsat5, intelsat, LinkStatus.DOWN));

            Instant base = Instant.parse("2026-06-20T00:00:00Z");
            linkMetricRepository.saveAll(List.of(
                    metric(l1, base.plusSeconds(60), 16.0, 12.0, 1e-7, -52.0, 9.0, LockStatus.LOCKED, ModulationType.QPSK, 5.0, 8.5, 540.0),
                    metric(l2, base.plusSeconds(120), 14.2, 10.6, 8e-7, -57.0, 10.2, LockStatus.LOCKED, ModulationType._8PSK, 6.0, 10.2, 620.0),
                    metric(l3, base.plusSeconds(180), 9.1, 6.1, 8.3e-5, -71.2, 11.5, LockStatus.INTERMITTENT, ModulationType._8PSK, 7.0, 6.3, 780.0),
                    metric(l4, base.plusSeconds(240), 15.3, 11.5, 1.9e-7, -55.8, 9.4, LockStatus.LOCKED, ModulationType._16APSK, 8.0, 12.9, 590.0),
                    metric(l5, base.plusSeconds(300), 7.0, 4.8, 3.2e-4, -76.0, 12.8, LockStatus.LOST, ModulationType.QPSK, 4.0, 2.4, 1250.0)
            ));

            frequencyPlanRepository.saveAll(List.of(
                    FrequencyPlan.builder().transponder(tp1).channelName("INSAT-CH-1").uplinkFrequency(6.175).downlinkFrequency(4.025).bandwidth(4.5).guardBand(0.25).build(),
                    FrequencyPlan.builder().transponder(tp1).channelName("INSAT-CH-2").uplinkFrequency(6.430).downlinkFrequency(4.280).bandwidth(4.5).guardBand(0.25).build(),
                    FrequencyPlan.builder().transponder(tp2).channelName("GSAT-CH-1").uplinkFrequency(6.210).downlinkFrequency(4.060).bandwidth(8.0).guardBand(0.30).build(),
                    FrequencyPlan.builder().transponder(tp3).channelName("INTELSAT-CH-1").uplinkFrequency(6.250).downlinkFrequency(4.100).bandwidth(9.0).guardBand(0.35).build()
            ));

            linkBudgetRepository.saveAll(List.of(
                    budget("Hub-to-Site-1", 45.0, 42.0, 205.5, 17.5, 290.0, -118.5, 16.2, 3.1),
                    budget("Hub-to-Site-2", 44.2, 41.5, 206.1, 16.8, 320.0, -121.3, 14.1, 1.6),
                    budget("Hub-to-Site-3", 42.8, 40.0, 207.8, 14.2, 380.0, -126.7, 9.0, -2.0),
                    budget("Hub-to-Site-4", 45.5, 42.4, 205.3, 17.2, 300.0, -119.2, 15.7, 2.8),
                    budget("Hub-to-Site-5", 41.9, 39.8, 208.6, 13.7, 410.0, -128.9, 7.2, -3.1)
            ));

            SatComAlarm a1 = satComAlarmRepository.save(SatComAlarm.builder()
                    .severity(AlarmSeverity.MAJOR)
                    .alarmType("HIGH_BER")
                    .source("Hub-to-Site-3")
                    .message("BER increasing under rain attenuation profile")
                    .status(AlarmStatus.OPEN)
                    .timestamp(base.plusSeconds(400))
                    .build());
            SatComAlarm a2 = satComAlarmRepository.save(SatComAlarm.builder()
                    .severity(AlarmSeverity.CRITICAL)
                    .alarmType("LOCK_LOST")
                    .source("Hub-to-Site-5")
                    .message("Carrier lock lost on remote modem")
                    .status(AlarmStatus.OPEN)
                    .timestamp(base.plusSeconds(500))
                    .build());

            troubleTicketRepository.saveAll(List.of(
                    TroubleTicket.builder()
                            .alarm(a1)
                            .rootCause("Rain Fade")
                            .correctiveAction("Enable ACM and increase uplink power by 2 dB")
                            .status(TroubleTicketStatus.INVESTIGATING)
                            .build(),
                    TroubleTicket.builder()
                            .alarm(a2)
                            .rootCause("Carrier Lock Lost")
                            .correctiveAction("Inspect LNB, BUC and modem IF cable path")
                            .status(TroubleTicketStatus.OPEN)
                            .build()
            ));
        };
    }

    private EarthStation station(String name, StationType type, double lat, double lon, double antenna, StationStatus status) {
        return EarthStation.builder()
                .stationName(name)
                .type(type)
                .latitude(lat)
                .longitude(lon)
                .antennaSize(antenna)
                .status(status)
                .build();
    }

    private Transponder transponder(
            Satellite satellite,
            int number,
            double bandwidth,
            double uplink,
            double downlink,
            PolarizationType polarization,
            TransponderStatus status
    ) {
        return Transponder.builder()
                .satellite(satellite)
                .transponderNumber(number)
                .bandwidthMhz(bandwidth)
                .uplinkFrequency(uplink)
                .downlinkFrequency(downlink)
                .polarization(polarization)
                .status(status)
                .build();
    }

    private SatelliteLink link(String name, EarthStation source, EarthStation destination, Satellite satellite, LinkStatus status) {
        return SatelliteLink.builder()
                .linkName(name)
                .sourceStation(source)
                .destinationStation(destination)
                .satellite(satellite)
                .status(status)
                .build();
    }

    private LinkMetric metric(
            SatelliteLink link,
            Instant ts,
            double cn,
            double ebNo,
            double ber,
            double rx,
            double tx,
            LockStatus lock,
            ModulationType modulation,
            double symbolRate,
            double throughput,
            double latency
    ) {
        return LinkMetric.builder()
                .link(link)
                .timestamp(ts)
                .cnDb(cn)
                .ebNo(ebNo)
                .ber(ber)
                .rxPowerDbm(rx)
                .txPowerDbm(tx)
                .lockStatus(lock)
                .modulation(modulation)
                .symbolRate(symbolRate)
                .throughputMbps(throughput)
                .latencyMs(latency)
                .build();
    }

    private LinkBudget budget(
            String linkName,
            double eirp,
            double antennaGain,
            double pathLoss,
            double gOverT,
            double noiseTemp,
            double carrierPower,
            double cn,
            double margin
    ) {
        return LinkBudget.builder()
                .linkName(linkName)
                .eirp(eirp)
                .antennaGain(antennaGain)
                .pathLoss(pathLoss)
                .gOverT(gOverT)
                .noiseTemperature(noiseTemp)
                .carrierPower(carrierPower)
                .cnRatio(cn)
                .linkMargin(margin)
                .build();
    }
}
