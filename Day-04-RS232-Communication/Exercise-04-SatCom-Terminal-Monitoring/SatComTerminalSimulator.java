import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * SatCom terminal simulator that sends periodic telemetry to connected monitoring clients.
 */
public class SatComTerminalSimulator {
    private static final Logger LOGGER = Logger.getLogger(SatComTerminalSimulator.class.getName());
    private static final int DEFAULT_PORT = 7004;
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException e) { }
        }
        try (ServerSocket server = new ServerSocket(port)) {
            LOGGER.info("SatComTerminalSimulator listening on " + port);
            while (true) {
                Socket client = server.accept();
                LOGGER.info("Monitor connected: " + client.getRemoteSocketAddress());
                new Thread(() -> streamTelemetry(client)).start();
            }
        } catch (IOException e) {
            LOGGER.severe("Server error: " + e.getMessage());
        }
    }

    private static void streamTelemetry(Socket client) {
        AtomicInteger counter = new AtomicInteger(0);
        Random rnd = new Random();
        try (client; PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
            while (!client.isClosed()) {
                int id = 100 + rnd.nextInt(900);
                int temp = 20 + rnd.nextInt(20);
                String power = rnd.nextBoolean() ? "ON" : "OFF";
                String status = "NOMINAL";
                String alarm = "NONE";
                if (rnd.nextInt(200) == 0) { alarm = "TEMP_HIGH"; status = "DEGRADED"; }
                int pkt = counter.incrementAndGet();
                String packet = String.format("TERMINAL_ID=%d;TEMP=%d;POWER=%s;STATUS=%s;ALARM=%s;PKT=%d;TS=%s",
                        id, temp, power, status, alarm, pkt, LocalDateTime.now().format(TF));
                out.println(packet);
                Thread.sleep(1000 + rnd.nextInt(500));
            }
        } catch (IOException e) {
            LOGGER.info("Monitor disconnected");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
