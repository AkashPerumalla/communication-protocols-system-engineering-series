import java.util.zip.CRC32;

public class EthernetFrameAnalyzer {
    public static void main(String[] args) {
        // Example frame components
        byte[] preamble = new byte[] {0x55,0x55,0x55,0x55,0x55,0x55,0x55};
        byte sfd = (byte)0xD5;
        byte[] dst = new byte[] {0x11,0x22,0x33,0x44,0x55,0x66};
        byte[] src = new byte[] {(byte)0xAA,(byte)0xBB,(byte)0xCC,(byte)0xDD,(byte)0xEE,(byte)0xFF};
        byte[] etherType = new byte[] {0x08, 0x00}; // IPv4
        byte[] payload = "Hello Ethernet".getBytes();

        // construct frame bytes: preamble + sfd + dst + src + etherType + payload + crc32
        int frameLen = preamble.length + 1 + dst.length + src.length + etherType.length + payload.length + 4;
        byte[] frame = new byte[frameLen];
        int idx = 0;
        System.arraycopy(preamble, 0, frame, idx, preamble.length); idx += preamble.length;
        frame[idx++] = sfd;
        System.arraycopy(dst, 0, frame, idx, dst.length); idx += dst.length;
        System.arraycopy(src, 0, frame, idx, src.length); idx += src.length;
        System.arraycopy(etherType, 0, frame, idx, etherType.length); idx += etherType.length;
        System.arraycopy(payload, 0, frame, idx, payload.length); idx += payload.length;

        // compute CRC32 of everything except the CRC field itself (simplified)
        CRC32 crc = new CRC32();
        crc.update(frame, 0, idx);
        long crcVal = crc.getValue();
        frame[idx++] = (byte)((crcVal >> 24) & 0xFF);
        frame[idx++] = (byte)((crcVal >> 16) & 0xFF);
        frame[idx++] = (byte)((crcVal >> 8) & 0xFF);
        frame[idx++] = (byte)(crcVal & 0xFF);

        // parse and print
        EthernetFrame ef = EthernetFrame.parseFromBytes(frame);
        System.out.println("--- Ethernet Frame Analysis ---");
        System.out.println(ef.summary());
        System.out.println("Payload interpreted as ASCII: " + new String(ef.payload));
        System.out.println("(Note: CRC shown is simplified CRC32 for demo purposes)");
    }
}
