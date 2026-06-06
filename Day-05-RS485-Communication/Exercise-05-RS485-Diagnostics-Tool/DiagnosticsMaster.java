import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class DiagnosticsMaster {
    private static final Logger LOGGER = Logger.getLogger(DiagnosticsMaster.class.getName());
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        int[] ports = {8002, 8003, 8004, 8009}; // includes devices and diagnostics node
        Map<Integer, Long> latencies = new HashMap<>();
        int success = 0, attempts = 0;
        for (int p : ports) {
            attempts++;
            long t = ping(p);
            if (t >= 0) {
                latencies.put(p, t);
                success++;
            }
        }
        System.out.println("Devices tested: " + attempts + ", success: " + success);
        latencies.forEach((port, lat) -> System.out.println("Port " + port + " avg latency (us): " + lat));
    }

    private static long ping(int port) {
        final int tries = 3;
        long total = 0;
        int ok = 0;
        for (int i = 0; i < tries; i++) {
            try (Socket s = new Socket()) {
                s.connect(new InetSocketAddress(HOST, port), 500);
                try (PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
                    long start = System.nanoTime();
                    out.println("PING");
                    String r = in.readLine();
                    long delta = System.nanoTime() - start;
                    if (r != null && r.contains("PONG")) {
                        total += delta;
                        ok++;
                    }
                }
            } catch (IOException e) {
                // timeout or unreachable
            }
        }
        if (ok == 0) return -1;
        return (total / ok) / 1000; // microseconds
    }
}
