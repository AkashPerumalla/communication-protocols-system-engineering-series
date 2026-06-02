import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UDPBenchmarkClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9001;
    private static final int REQUESTS = 10;

    public static void main(String[] args) {
        List<Long> latencies = new ArrayList<>();

        System.out.println("UDP Latency Benchmark");
        System.out.println("Sending " + REQUESTS + " datagrams...\n");

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(1000);
            InetAddress destination = InetAddress.getByName(HOST);

            for (int index = 1; index <= REQUESTS; index++) {
                String payload = "UDP-Ping-" + index;
                byte[] requestData = payload.getBytes(StandardCharsets.UTF_8);
                DatagramPacket request = new DatagramPacket(requestData, requestData.length, destination, PORT);

                byte[] responseBuffer = new byte[128];
                DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);

                long start = System.nanoTime();
                socket.send(request);

                try {
                    socket.receive(response);
                } catch (Exception timeout) {
                    // The server should reply, but if a packet is lost we still record the elapsed time.
                }

                long end = System.nanoTime();
                latencies.add((end - start) / 1_000_000);
            }

            printResults("UDP", latencies);
        } catch (Exception exception) {
            System.err.println("UDP benchmark error: " + exception.getMessage());
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
