import java.util.Random;

public class DeviceSimulator {
    private final String name;
    private final String ip;
    private final Random rnd = new Random();

    public DeviceSimulator(String name, String ip) {
        this.name = name; this.ip = ip;
    }

    public DeviceMetrics sample() {
        int bandwidthKbps = 100 + rnd.nextInt(900); // 100-1000 kbps
        int pkts = 1000 + rnd.nextInt(5000);
        int errors = rnd.nextInt(5);
        String status = errors > 2 ? "DEGRADED" : "OK";
        return new DeviceMetrics(name, ip, bandwidthKbps, pkts, errors, status);
    }

    public static class DeviceMetrics {
        public final String name, ip;
        public final int bandwidthKbps, packetCount, errors;
        public final String status;

        public DeviceMetrics(String name, String ip, int b, int p, int e, String s) {
            this.name = name; this.ip = ip; this.bandwidthKbps = b; this.packetCount = p; this.errors = e; this.status = s;
        }
    }

    public static void main(String[] args) {
        DeviceSimulator ds = new DeviceSimulator("Switch-1","10.0.0.1");
        DeviceMetrics m = ds.sample();
        System.out.println("Sample metrics: " + m.name + " " + m.ip + " bw=" + m.bandwidthKbps + " kbps pkts=" + m.packetCount + " errors=" + m.errors + " status=" + m.status);
    }
}
