import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9001;

    public static void main(String[] args) {
        System.out.println("UDP Packet Sender");
        System.out.println("Enter packet names like Packet-1, Packet-2, Packet-3.");
        System.out.println("Type /exit to finish.\n");

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress destination = InetAddress.getByName(HOST);

            while (true) {
                System.out.print("Packet payload: ");
                String payload = scanner.nextLine();

                if ("/exit".equalsIgnoreCase(payload.trim())) {
                    break;
                }

                byte[] data = payload.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, destination, PORT);
                socket.send(packet);

                byte[] responseBuffer = new byte[128];
                DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(response);

                String acknowledgement = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);
                System.out.println("Server Response: " + acknowledgement);
                System.out.println("Sent " + data.length + " bytes to " + HOST + ":" + PORT);
            }
        } catch (Exception exception) {
            System.err.println("UDP client error: " + exception.getMessage());
        }
    }
}
