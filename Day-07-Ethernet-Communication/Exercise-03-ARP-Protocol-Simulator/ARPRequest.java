public class ARPRequest {
    public final String senderIp;
    public final String senderMac;
    public final String targetIp;

    public ARPRequest(String senderIp, String senderMac, String targetIp) {
        this.senderIp = senderIp;
        this.senderMac = senderMac;
        this.targetIp = targetIp;
    }

    @Override
    public String toString() {
        return String.format("ARP Request: Who has %s? Tell %s (%s)", targetIp, senderIp, senderMac);
    }
}
