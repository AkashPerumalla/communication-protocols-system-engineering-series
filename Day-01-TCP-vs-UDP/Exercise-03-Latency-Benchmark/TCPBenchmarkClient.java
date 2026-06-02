import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPBenchmarkClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9000;
    private static final int REQUESTS = 10;

    public static void main(String[] args) {
        List<Long> latencies = new ArrayList<>();

        System.out.println("TCP Latency Benchmark");
        System.out.println("Sending " + REQUESTS + " requests...\n");

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            input.readLine();
            output.println("benchmark-user");
            input.readLine();

            for (int index = 1; index <= REQUESTS; index++) {
                String payload = "TCP-Ping-" + index;

                long start = System.nanoTime();
                output.println(payload);
                String response = input.readLine();
                long end = System.nanoTime();

                if (response == null) {
                    break;
                }

                latencies.add((end - start) / 1_000_000);
            }

            printResults("TCP", latencies);

            output.println("/exit");
        } catch (IOException exception) {
            System.err.println("TCP benchmark error: " + exception.getMessage());
        }
    }

    private static void printResults(String label, List<Long> latencies) {
        if (latencies.isEmpty()) {
            System.out.println(label + " Results");
            System.out.println("No samples collected.");
            return;
        }

        long total = 0;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for (long latency : latencies) {
            total += latency;
            min = Math.min(min, latency);
            max = Math.max(max, latency);
        }

        long average = total / latencies.size();

        System.out.println(label + " Results");
        System.out.println("Average: " + average + " ms");
        System.out.println("Min: " + min + " ms");
        System.out.println("Max: " + max + " ms");
    }
}
