public class PacketCaptureAnalyzer {
    public static void main(String[] args) {
        System.out.println("--- Packet Capture Analyzer Demo ---\n");
        analyzeArpSample();
        analyzeIcmpSample();
        analyzeTcpSample();
        analyzeUdpSample();
        analyzeHttpSample();
        analyzeDnsSample();
    }

    private static void analyzeArpSample() {
        System.out.println("[ARP] Frame: Who has 192.168.1.1? Tell 192.168.1.100");
        System.out.println("  -> eth.src: 11:22:33:44:55:66");
        System.out.println("  -> eth.dst: ff:ff:ff:ff:ff:ff (broadcast)");
        System.out.println("  -> arp.opcode: request (1)");
        System.out.println();
    }

    private static void analyzeIcmpSample() {
        System.out.println("[ICMP] Echo Request: 192.168.1.100 -> 192.168.1.1");
        System.out.println("  -> icmp.type: 8 (echo request)");
        System.out.println("  -> ip.ttl: 64");
        System.out.println();
    }

    private static void analyzeTcpSample() {
        System.out.println("[TCP] 3-way handshake sample between client 10.0.0.5:34567 and server 10.0.0.1:80");
        System.out.println("  -> SYN(seq=1000)");
        System.out.println("  -> SYN,ACK(seq=2000, ack=1001)");
        System.out.println("  -> ACK(seq=1001, ack=2001)");
        System.out.println();
    }

    private static void analyzeUdpSample() {
        System.out.println("[UDP] DNS query sample: src 10.0.0.5:54321 -> 8.8.8.8:53");
        System.out.println("  -> length: 78");
        System.out.println();
    }

    private static void analyzeHttpSample() {
        System.out.println("[HTTP] GET /index.html HTTP/1.1 from 10.0.0.5:34567 to 10.0.0.1:80");
        System.out.println("  -> headers: Host, User-Agent, Accept");
        System.out.println("  -> response: 200 OK, Content-Type: text/html\n");
    }

    private static void analyzeDnsSample() {
        System.out.println("[DNS] Query: example.com A record -> Response: 93.184.216.34");
        System.out.println();
    }
}
