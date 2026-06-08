import java.util.ArrayList;
import java.util.List;

public class MonitoringStation {
    private final List<PLCSimulator> plcs = new ArrayList<>();

    public void addPLC(PLCSimulator p) { plcs.add(p); }

    public void poll() {
        System.out.println("--- Industrial Monitoring Station ---");
        for (PLCSimulator p : plcs) {
            PLCSimulator.Telemetry t = p.sample();
            System.out.printf("%s: temp=%.2fC pressure=%.2fbar motor=%s alarm=%s\n", t.id, t.temperature, t.pressure, t.motorRunning?"ON":"OFF", t.alarm);
            if (t.alarm) System.out.println("  -> ALERT: take action!");
        }
    }

    public static void main(String[] args) {
        MonitoringStation ms = new MonitoringStation();
        ms.addPLC(new PLCSimulator("PLC-1"));
        ms.addPLC(new PLCSimulator("PLC-2"));
        ms.poll();
    }
}
