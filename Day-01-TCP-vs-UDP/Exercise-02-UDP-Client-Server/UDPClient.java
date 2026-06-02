import java.net.*;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();

        String message = "Hello UDP Server";

        DatagramPacket packet =
                new DatagramPacket(
                        message.getBytes(),
                        message.length(),
                        InetAddress.getByName("localhost"),
                        9001);

        socket.send(packet);

        byte[] buffer = new byte[1024];

        DatagramPacket response =
                new DatagramPacket(buffer, buffer.length);

        socket.receive(response);

        System.out.println(
                new String(
                        response.getData(),
                        0,
                        response.getLength()));

        socket.close();
    }
}
