import java.net.*;

public class UDPServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(6000);

        byte[] buffer = new byte[1024];

        DatagramPacket packet =
                new DatagramPacket(buffer, buffer.length);

        socket.receive(packet);

        String message =
                new String(packet.getData(), 0, packet.getLength());

        System.out.println("Client: " + message);

        String response = "Hello UDP Client";

        DatagramPacket responsePacket =
                new DatagramPacket(
                        response.getBytes(),
                        response.length(),
                        packet.getAddress(),
                        packet.getPort());

        socket.send(responsePacket);

        socket.close();
    }
}
