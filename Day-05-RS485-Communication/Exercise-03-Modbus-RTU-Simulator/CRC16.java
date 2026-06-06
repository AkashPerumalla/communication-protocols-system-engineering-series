public class CRC16 {
    // Modbus CRC16 (little-endian output: low byte, high byte)
    public static byte[] modbusCRC(byte[] data, int offset, int len) {
        int crc = 0xFFFF;
        for (int i = offset; i < offset + len; i++) {
            crc ^= (data[i] & 0xFF);
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x0001) != 0) crc = (crc >>> 1) ^ 0xA001;
                else crc = (crc >>> 1);
            }
        }
        byte lo = (byte) (crc & 0xFF);
        byte hi = (byte) ((crc >> 8) & 0xFF);
        return new byte[] {lo, hi};
    }
}
