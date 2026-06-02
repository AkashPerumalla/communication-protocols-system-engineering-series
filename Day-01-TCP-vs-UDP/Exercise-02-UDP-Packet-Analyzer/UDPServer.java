import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class UDPServer {

    private static final int PORT = 9001;
    private static final int BUFFER_SIZE = 2048;
    private static final int SOCKET_TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        int packetCount = 0;

        System.out.println("UDP Packet Analyzer started on port " + PORT);
        System.out.println("Waiting for packets...\n");

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            socket.setSoTimeout(SOCKET_TIMEOUT_MS);

            while (true) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(packet);
                    packetCount++;

                    String payload = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                    int packetSize = packet.getLength();

                    System.out.println("Packet Received");
                    System.out.println("Source:");
                    System.out.println(packet.getAddress().getHostAddress());
                    System.out.println();
                    System.out.println("Port:");
                    System.out.println(packet.getPort());
                    System.out.println();
                    System.out.println("Payload:");
                    System.out.println(payload);
                    System.out.println();
                    System.out.println("Packet Size:");
                    System.out.println(packetSize + " bytes");
                    System.out.println();
                    System.out.println("Total Packets:");
                    System.out.println(packetCount);
                    System.out.println();

                    String acknowledgement = "ACK:" + payload;
                    byte[] responseData = acknowledgement.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket response = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
                    socket.send(response);
                } catch (SocketTimeoutException timeoutException) {
                    System.out.println("No packets received in the last " + SOCKET_TIMEOUT_MS + " ms.");
                    System.out.println("Total Packets:");
                    System.out.println(packetCount);
                    System.out.println();
                }
            }
        } catch (Exception exception) {
            System.err.println("UDP server error: " + exception.getMessage());
        }
    }
}
