public class ARPReply {
    public final String senderIp;
    public final String senderMac;
    public final String targetIp;
    public final String targetMac;

    public ARPReply(String senderIp, String senderMac, String targetIp, String targetMac) {
        this.senderIp = senderIp;
        this.senderMac = senderMac;
        this.targetIp = targetIp;
        this.targetMac = targetMac;
    }

    @Override
    public String toString() {
        return String.format("ARP Reply: %s is at %s (reply to %s)", senderIp, senderMac, targetIp);
    }
}
