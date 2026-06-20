package com.sky2dev.day17.config;

import com.sky2dev.day17.model.Alarm;
import com.sky2dev.day17.model.AlarmRule;
import com.sky2dev.day17.model.AlarmSeverity;
import com.sky2dev.day17.model.AlarmState;
import com.sky2dev.day17.model.AlarmType;
import com.sky2dev.day17.model.EscalationPolicy;
import com.sky2dev.day17.model.ManagedDevice;
import com.sky2dev.day17.model.NotificationChannel;
import com.sky2dev.day17.model.NotificationChannelType;
import com.sky2dev.day17.model.Operator;
import com.sky2dev.day17.repository.AlarmRepository;
import com.sky2dev.day17.repository.AlarmRuleRepository;
import com.sky2dev.day17.repository.EscalationPolicyRepository;
import com.sky2dev.day17.repository.ManagedDeviceRepository;
import com.sky2dev.day17.repository.NotificationChannelRepository;
import com.sky2dev.day17.repository.OperatorRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seed(ManagedDeviceRepository deviceRepository,
                           AlarmRuleRepository alarmRuleRepository,
                           EscalationPolicyRepository escalationPolicyRepository,
                           NotificationChannelRepository notificationChannelRepository,
                           OperatorRepository operatorRepository,
                           AlarmRepository alarmRepository,
                           com.sky2dev.day17.service.AlarmManagementService alarmManagementService,
                           com.sky2dev.day17.service.AlarmCorrelationService alarmCorrelationService,
                           com.sky2dev.day17.service.RootCauseAnalysisService rootCauseAnalysisService) {
        return args -> {
            if (deviceRepository.count() == 0) {
                deviceRepository.saveAll(List.of(
                        ManagedDevice.builder().name("Core-Router-01").category("Telecom").location("NOC-A").ipAddress("10.10.0.11").baselineSeverity(AlarmSeverity.MAJOR).telecom(true).satcom(false).build(),
                        ManagedDevice.builder().name("Backhaul-Switch-01").category("Telecom").location("NOC-B").ipAddress("10.10.0.12").baselineSeverity(AlarmSeverity.WARNING).telecom(true).satcom(false).build(),
                        ManagedDevice.builder().name("Power-Unit-01").category("Infrastructure").location("DataCenter-1").ipAddress("10.20.0.21").baselineSeverity(AlarmSeverity.CRITICAL).telecom(false).satcom(false).build(),
                        ManagedDevice.builder().name("App-Server-01").category("Application").location("DataCenter-1").ipAddress("10.20.0.22").baselineSeverity(AlarmSeverity.MAJOR).telecom(false).satcom(false).build(),
                        ManagedDevice.builder().name("Hub-Modem-01").category("SatCom").location("Hub-Station").ipAddress("10.30.0.31").baselineSeverity(AlarmSeverity.CRITICAL).telecom(false).satcom(true).build(),
                        ManagedDevice.builder().name("Remote-Terminal-01").category("SatCom").location("Remote-Site").ipAddress("10.30.0.32").baselineSeverity(AlarmSeverity.MAJOR).telecom(false).satcom(true).build()
                ));
            }

            if (alarmRuleRepository.count() == 0) {
                alarmRuleRepository.saveAll(List.of(
                        AlarmRule.builder().alarmType(AlarmType.HIGH_CPU_USAGE).severity(AlarmSeverity.MAJOR).metricName("cpuUsage").thresholdValue(80.0).comparator(">").description("CPU above 80 percent").build(),
                        AlarmRule.builder().alarmType(AlarmType.HIGH_CPU_USAGE).severity(AlarmSeverity.CRITICAL).metricName("cpuUsage").thresholdValue(95.0).comparator(">").description("CPU above 95 percent").build(),
                        AlarmRule.builder().alarmType(AlarmType.HIGH_TEMPERATURE).severity(AlarmSeverity.CRITICAL).metricName("temperatureC").thresholdValue(70.0).comparator(">").description("Temperature above 70C").build(),
                        AlarmRule.builder().alarmType(AlarmType.SIGNAL_STRENGTH_LOW).severity(AlarmSeverity.MAJOR).metricName("signalStrengthDbm").thresholdValue(-80.0).comparator("<").description("Signal below -80 dBm").build(),
                        AlarmRule.builder().alarmType(AlarmType.BER_HIGH).severity(AlarmSeverity.CRITICAL).metricName("ber").thresholdValue(0.01).comparator(">").description("BER above threshold").build()
                ));
            }

            if (escalationPolicyRepository.count() == 0) {
                escalationPolicyRepository.saveAll(List.of(
                        EscalationPolicy.builder().name("Major alarm after 15 minutes").minutesToEscalate(15).minimumSeverity(AlarmSeverity.MAJOR).targetTeam("NOC Tier 2").build(),
                        EscalationPolicy.builder().name("Critical alarm after 5 minutes").minutesToEscalate(5).minimumSeverity(AlarmSeverity.CRITICAL).targetTeam("NOC Duty Manager").build()
                ));
            }

            if (notificationChannelRepository.count() == 0) {
                notificationChannelRepository.saveAll(List.of(
                        NotificationChannel.builder().channelType(NotificationChannelType.EMAIL).endpoint("noc@example.com").enabled(true).build(),
                        NotificationChannel.builder().channelType(NotificationChannelType.SMS).endpoint("+15555550101").enabled(true).build(),
                        NotificationChannel.builder().channelType(NotificationChannelType.WEBHOOK).endpoint("https://noc.local/webhook").enabled(true).build(),
                        NotificationChannel.builder().channelType(NotificationChannelType.DASHBOARD).endpoint("NOC Alarm Console").enabled(true).build(),
                        NotificationChannel.builder().channelType(NotificationChannelType.TICKETING).endpoint("INC-QUEUE").enabled(true).build()
                ));
            }

            if (operatorRepository.count() == 0) {
                operatorRepository.saveAll(List.of(
                        Operator.builder().name("Priya Nair").team("NOC Tier 1").role("Operator").contact("priya.nair@example.com").build(),
                        Operator.builder().name("Daniel Chen").team("NOC Tier 2").role("Senior Operator").contact("daniel.chen@example.com").build(),
                        Operator.builder().name("Maya Alvarez").team("Duty Management").role("Duty Manager").contact("maya.alvarez@example.com").build()
                ));
            }

            List<com.sky2dev.day17.model.Alarm> generatedAlarms = alarmManagementService.rebuildAlarmsFromMetrics();
            if (!generatedAlarms.isEmpty()) {
                alarmCorrelationService.correlate(generatedAlarms);
                rootCauseAnalysisService.analyze(generatedAlarms);
            }
        };
    }
}
