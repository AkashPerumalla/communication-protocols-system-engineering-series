import java.util.Random;

public class PLCSimulator {
    private final String id;
    private final Random rnd = new Random();

    public PLCSimulator(String id) { this.id = id; }

    public Telemetry sample() {
        double temperature = 20.0 + rnd.nextDouble() * 15.0; // 20-35 C
        double pressure = 1.0 + rnd.nextDouble() * 4.0; // 1-5 bar
        boolean motorRunning = rnd.nextBoolean();
        boolean alarm = temperature > 30.0 || pressure > 4.5 || !motorRunning;
        return new Telemetry(id, temperature, pressure, motorRunning, alarm);
    }

    public static class Telemetry {
        public final String id;
        public final double temperature, pressure;
        public final boolean motorRunning, alarm;
        public Telemetry(String id,double t,double p,boolean m,boolean a){this.id=id;this.temperature=t;this.pressure=p;this.motorRunning=m;this.alarm=a;}
    }

    public static void main(String[] args) {
        PLCSimulator plc = new PLCSimulator("PLC-1");
        Telemetry t = plc.sample();
        System.out.printf("PLC %s: temp=%.2fC pressure=%.2fbar motor=%s alarm=%s\n", t.id, t.temperature, t.pressure, t.motorRunning?"ON":"OFF", t.alarm);
    }
}
