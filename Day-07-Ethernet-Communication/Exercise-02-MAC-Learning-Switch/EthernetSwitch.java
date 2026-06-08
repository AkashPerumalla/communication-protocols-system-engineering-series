import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EthernetSwitch {
    private static final Logger LOGGER = Logger.getLogger(EthernetSwitch.class.getName());
    private final Map<String, Integer> macTable = new HashMap<>();
    private final int ports;

    public EthernetSwitch(int ports) {
        this.ports = ports;
    }

    public void receiveFrame(String srcMac, String dstMac, int inPort, byte[] frame) {
        // Learn source MAC
        macTable.put(srcMac, inPort);
        LOGGER.info(() -> "Learned " + srcMac + " -> port " + inPort);

        Integer outPort = macTable.get(dstMac);
        if (outPort != null && outPort != inPort) {
            // forward to specific port
            LOGGER.info(() -> "Forwarding frame from " + srcMac + " to " + dstMac + " on port " + outPort);
            // In a full simulation we'd call connected port's receive; here we print
        } else if (outPort != null && outPort == inPort) {
            LOGGER.info(() -> "Destination on same port; dropping frame");
        } else {
            // Flood
            LOGGER.info(() -> "Unknown destination " + dstMac + "; flooding to all ports except " + inPort);
            for (int p = 1; p <= ports; p++) {
                if (p == inPort) continue;
                final int portToFlood = p;
                LOGGER.info(() -> "Flooding to port " + portToFlood);
            }
        }
    }

    public void printMacTable() {
        System.out.println("--- MAC Table ---");
        macTable.forEach((mac, port) -> System.out.println(mac + " -> port " + port));
    }

    // Demo main
    public static void main(String[] args) throws Exception {
        EthernetSwitch sw = new EthernetSwitch(4);
        // Host A on port 1
        String macA = "AA:AA:AA:AA:AA:AA";
        String macB = "BB:BB:BB:BB:BB:BB";

        System.out.println("HostA (" + macA + ") sends to HostB (" + macB + ")");
        sw.receiveFrame(macA, macB, 1, new byte[42]);
        // Host B replies
        System.out.println("HostB replies to HostA");
        sw.receiveFrame(macB, macA, 2, new byte[54]);

        // Now HostA sends again; switch should forward directly
        System.out.println("HostA sends to HostB again");
        sw.receiveFrame(macA, macB, 1, new byte[60]);

        sw.printMacTable();
    }
}
