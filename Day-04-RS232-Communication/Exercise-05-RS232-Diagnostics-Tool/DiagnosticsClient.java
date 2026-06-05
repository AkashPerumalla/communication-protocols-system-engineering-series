import java.io.*;
import java.net.*;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

/**
 * Runs a simple diagnostics suite against DiagnosticsServer and prints statistics.
 */
public class DiagnosticsClient {
    private static final Logger LOGGER = Logger.getLogger(DiagnosticsClient.class.getName());
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7005;

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        int packetsSent = 0;
        int packetsReceived = 0;
        long totalRtt = 0;

        try (Socket s = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            // send 5 PINGs
            for (int i = 0; i < 5; i++) {
                packetsSent++;
                Instant start = Instant.now();
                out.println("PING");
                String resp = in.readLine();
                if (resp != null) {
                    packetsReceived++;
                    long rtt = Duration.between(start, Instant.now()).toMillis();
                    totalRtt += rtt;
                    System.out.println("PING -> " + resp + " (RTT=" + rtt + "ms)");
                }
            }

            // send ECHO
            packetsSent++;
            Instant st = Instant.now();
            out.println("ECHO HelloDiagnostics");
            String r = in.readLine();
            if (r != null) { packetsReceived++; long rtt = Duration.between(st, Instant.now()).toMillis(); totalRtt += rtt; System.out.println("ECHO -> " + r + " (RTT=" + rtt + "ms)"); }

            // send DELAY 200
            packetsSent++;
            Instant st2 = Instant.now();
            out.println("DELAY 200");
            String rr = in.readLine();
            if (rr != null) { packetsReceived++; long rtt = Duration.between(st2, Instant.now()).toMillis(); totalRtt += rtt; System.out.println("DELAY -> " + rr + " (RTT=" + rtt + "ms)"); }

            System.out.println("\n--- Diagnostics Summary ---");
            System.out.println("Packets Sent: " + packetsSent);
            System.out.println("Packets Received: " + packetsReceived);
            System.out.println("Success Rate: " + (packetsReceived * 100 / Math.max(1, packetsSent)) + "%");
            System.out.println("Average RTT (ms): " + (packetsReceived == 0 ? 0 : totalRtt / packetsReceived));

        } catch (IOException e) {
            LOGGER.warning("Connection error: " + e.getMessage());
        }
    }
}
