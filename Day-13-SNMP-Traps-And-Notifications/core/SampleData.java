import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SampleData {
    private static final Instant BASE_TIME = Instant.parse("2026-06-15T08:00:00Z");

    private SampleData() {
    }

    public static List<DeviceProfile> deviceProfiles() {
        return List.of(
                new DeviceProfile("Router-01", "10.10.0.1", "NOC-East", "Edge Router"),
                new DeviceProfile("Switch-01", "10.10.0.2", "NOC-East", "Aggregation Switch"),
                new DeviceProfile("Modem-01", "10.10.0.3", "Teleport-A", "Telecom Modem"),
                new DeviceProfile("Hub-01", "10.10.0.4", "Teleport-A", "Satellite Hub"),
                new DeviceProfile("BUC-01", "10.10.0.5", "Teleport-A", "RF Outdoor Unit")
        );
    }

    public static List<TrapEvent> trapBasics() {
        DeviceProfile router = deviceProfiles().get(0);
        TrapAgent agent = new TrapAgent(router);
        return List.of(
                agent.generate(TrapType.COLD_START, BASE_TIME, "Router boot completed after planned maintenance", orderedMap("bootReason", "planned-maintenance")),
                agent.generate(TrapType.LINK_DOWN, BASE_TIME.plusSeconds(30), "Uplink Gi0/1 lost carrier", orderedMap("interface", "Gi0/1", "peer", "Switch-01")),
                agent.generate(TrapType.HIGH_TEMPERATURE, BASE_TIME.plusSeconds(60), "Chassis temperature crossed threshold", orderedMap("temperatureC", "87.5", "thresholdC", "80.0"))
        );
    }

    public static TrapEvent receiverTrap() {
        DeviceProfile switchDevice = deviceProfiles().get(1);
        return new TrapAgent(switchDevice).generate(
                TrapType.AUTHENTICATION_FAILURE,
                BASE_TIME.plusSeconds(90),
                "SNMP authentication failure from management station",
                orderedMap("community", "restricted", "source", "192.168.10.20"));
    }

    public static TrapEvent processingTrap() {
        DeviceProfile modem = deviceProfiles().get(2);
        return new TrapAgent(modem).generate(
                TrapType.MODEM_FAILURE,
                BASE_TIME.plusSeconds(120),
                "Modem stopped responding to keepalive probes",
                orderedMap("keepalive", "missed-3", "state", "offline"));
    }

    public static List<TrapEvent> alarmTraps() {
        DeviceProfile switchDevice = deviceProfiles().get(1);
        TrapAgent agent = new TrapAgent(switchDevice);
        return List.of(
                agent.generate(TrapType.HIGH_CPU_UTILIZATION, BASE_TIME.plusSeconds(150), "Control plane CPU above threshold", orderedMap("cpuPercent", "94.1")),
                agent.generate(TrapType.HIGH_MEMORY_UTILIZATION, BASE_TIME.plusSeconds(180), "Buffer memory above threshold", orderedMap("memoryPercent", "89.7"))
        );
    }

    public static TelecomAlarmModel telecomAlarmModel() {
        return new TelecomAlarmModel(1.2e-6, -42.0, "LOST", 14.250, 45.0);
    }

    public static TrapEvent telecomTrap() {
        DeviceProfile modem = deviceProfiles().get(2);
        return new TrapAgent(modem).generate(
                TrapType.BER_THRESHOLD_EXCEEDED,
                BASE_TIME.plusSeconds(210),
                "BER crossed telecom service threshold",
                orderedMap("ber", "1.2E-6", "carrierLock", "LOST", "rfPowerDbm", "-42", "frequencyGhz", "14.250", "symbolRateMsps", "45"));
    }

    public static SatComAlarmModel satComAlarmModel() {
        return new SatComAlarmModel(4.2, "FAILED", "FAILED", "OFFLINE", "DEGRADED");
    }

    public static TrapEvent satComTrap() {
        DeviceProfile hub = deviceProfiles().get(3);
        return new TrapAgent(hub).generate(
                TrapType.BUC_FAILURE,
                BASE_TIME.plusSeconds(240),
                "BUC and LNB failure detected on satellite terminal",
                orderedMap("ebNoDb", "4.2", "bucStatus", "FAILED", "lnbStatus", "FAILED", "modemStatus", "OFFLINE", "terminalState", "DEGRADED"));
    }

    public static List<TrapEvent> correlationTraps() {
        DeviceProfile hub = deviceProfiles().get(3);
        TrapAgent agent = new TrapAgent(hub);
        return List.of(
                agent.generate(TrapType.POWER_FAILURE, BASE_TIME.plusSeconds(270), "Primary power feed failed", orderedMap("feed", "A", "voltage", "0V")),
                agent.generate(TrapType.DEVICE_UNREACHABLE, BASE_TIME.plusSeconds(300), "Polling timed out after power loss", orderedMap("poller", "NMS-01", "timeoutMs", "5000"))
        );
    }

    public static List<TrapEvent> nocTraps() {
        List<TrapEvent> traps = new ArrayList<>();
        traps.add(trapFor(deviceProfiles().get(0), TrapType.COLD_START, BASE_TIME.plusSeconds(10), "Router restarted during patch window", orderedMap("window", "patch-2026-06")));
        traps.add(trapFor(deviceProfiles().get(1), TrapType.LINK_DOWN, BASE_TIME.plusSeconds(20), "Switch uplink down to distribution", orderedMap("interface", "Gi1/0/24")));
        traps.add(trapFor(deviceProfiles().get(2), TrapType.BER_THRESHOLD_EXCEEDED, BASE_TIME.plusSeconds(210), "Modem BER alarm active", orderedMap("ber", "1.2E-6")));
        traps.add(trapFor(deviceProfiles().get(3), TrapType.BUC_FAILURE, BASE_TIME.plusSeconds(240), "BUC failure at teleport", orderedMap("site", "Teleport-A")));
        traps.add(trapFor(deviceProfiles().get(4), TrapType.LNB_FAILURE, BASE_TIME.plusSeconds(250), "LNB failure at teleport", orderedMap("site", "Teleport-A")));
        return List.copyOf(traps);
    }

    public static List<Alarm> nocAlarms() {
        EventRepository repository = new EventRepository();
        AlarmManager alarmManager = new AlarmManager(repository);
        List<Alarm> alarms = new ArrayList<>();
        alarms.add(alarmManager.createAlarm(trapFor(deviceProfiles().get(0), TrapType.COLD_START, BASE_TIME.plusSeconds(10), "Router restarted during patch window", orderedMap("window", "patch-2026-06"))));
        alarms.add(alarmManager.createAlarm(trapFor(deviceProfiles().get(1), TrapType.LINK_DOWN, BASE_TIME.plusSeconds(20), "Switch uplink down to distribution", orderedMap("interface", "Gi1/0/24"))));
        alarms.add(alarmManager.createAlarm(trapFor(deviceProfiles().get(2), TrapType.BER_THRESHOLD_EXCEEDED, BASE_TIME.plusSeconds(210), "Modem BER alarm active", orderedMap("ber", "1.2E-6"))));
        alarms.add(alarmManager.createAlarm(trapFor(deviceProfiles().get(3), TrapType.BUC_FAILURE, BASE_TIME.plusSeconds(240), "BUC failure at teleport", orderedMap("site", "Teleport-A"))));
        alarms.add(alarmManager.createAlarm(trapFor(deviceProfiles().get(4), TrapType.LNB_FAILURE, BASE_TIME.plusSeconds(250), "LNB failure at teleport", orderedMap("site", "Teleport-A"))));
        return List.copyOf(alarms);
    }

    private static TrapEvent trapFor(DeviceProfile profile, TrapType trapType, Instant timestamp, String description, Map<String, String> attributes) {
        return new TrapAgent(profile).generate(trapType, timestamp, description, attributes);
    }

    private static Map<String, String> orderedMap(String... pairs) {
        ValidationUtils.ensure(pairs.length % 2 == 0, "Attributes must be supplied as key/value pairs");
        Map<String, String> map = new LinkedHashMap<>();
        for (int index = 0; index < pairs.length; index += 2) {
            map.put(pairs[index], pairs[index + 1]);
        }
        return map;
    }
}
