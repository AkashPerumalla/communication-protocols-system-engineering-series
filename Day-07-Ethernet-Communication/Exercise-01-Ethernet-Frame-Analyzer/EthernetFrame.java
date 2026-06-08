import java.util.Arrays;
import java.util.zip.CRC32;

public class EthernetFrame {
    public final byte[] preamble; // usually 7 bytes of 0x55
    public final byte sfd; // start frame delimiter 0xD5
    public final String destinationMac;
    public final String sourceMac;
    public final int etherType;
    public final byte[] payload;
    public final long crc32; // simplified CRC32 for illustration

    public EthernetFrame(byte[] preamble, byte sfd, String destinationMac, String sourceMac, int etherType, byte[] payload, long crc32) {
        this.preamble = preamble == null ? new byte[0] : preamble;
        this.sfd = sfd;
        this.destinationMac = destinationMac;
        this.sourceMac = sourceMac;
        this.etherType = etherType;
        this.payload = payload == null ? new byte[0] : payload;
        this.crc32 = crc32;
    }

    public static String bytesToMac(byte[] b, int offset) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            if (i > 0) sb.append(":");
            sb.append(String.format("%02X", b[offset + i]));
        }
        return sb.toString();
    }

    public static long computeCRC32(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data, 0, data.length);
        return crc.getValue();
    }

    public static EthernetFrame parseFromBytes(byte[] frameBytes) {
        int idx = 0;
        byte[] preamble = new byte[0];
        byte sfd = 0x00;
        // detect preamble (0x55 repeated) + SFD 0xD5
        if (frameBytes.length > 8) {
            boolean hasPreamble = true;
            for (int i = 0; i < 7; i++) {
                if (frameBytes[i] != 0x55) { hasPreamble = false; break; }
            }
            if (hasPreamble && frameBytes[7] == (byte)0xD5) {
                preamble = Arrays.copyOfRange(frameBytes, 0, 7);
                sfd = frameBytes[7];
                idx = 8;
            }
        }

        String dst = bytesToMac(frameBytes, idx);
        idx += 6;
        String src = bytesToMac(frameBytes, idx);
        idx += 6;

        int etherType = ((frameBytes[idx] & 0xFF) << 8) | (frameBytes[idx + 1] & 0xFF);
        idx += 2;

        // payload is everything until last 4 bytes (CRC)
        if (frameBytes.length < idx + 4) {
            return new EthernetFrame(preamble, sfd, dst, src, etherType, new byte[0], 0);
        }
        byte[] payload = Arrays.copyOfRange(frameBytes, idx, frameBytes.length - 4);
        byte[] crcBytes = Arrays.copyOfRange(frameBytes, frameBytes.length - 4, frameBytes.length);
        long crc = ((crcBytes[0] & 0xFFL) << 24) | ((crcBytes[1] & 0xFFL) << 16) | ((crcBytes[2] & 0xFFL) << 8) | (crcBytes[3] & 0xFFL);

        return new EthernetFrame(preamble, sfd, dst, src, etherType, payload, crc);
    }

    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Preamble: ");
        if (preamble.length > 0) sb.append("present"); else sb.append("absent");
        sb.append('\n');
        sb.append("Destination MAC: ").append(destinationMac).append('\n');
        sb.append("Source MAC: ").append(sourceMac).append('\n');
        sb.append(String.format("EtherType: 0x%04X", etherType)).append('\n');
        sb.append("Payload (len): ").append(payload.length).append('\n');
        sb.append(String.format("CRC32 (frame): 0x%08X", crc32)).append('\n');
        if (payload.length <= 64) {
            sb.append("Payload (hex): ");
            for (byte b : payload) sb.append(String.format("%02X ", b));
            sb.append('\n');
        }
        return sb.toString();
    }
}
