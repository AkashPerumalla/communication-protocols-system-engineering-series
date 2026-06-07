package Exercise02;

import java.util.Arrays;

public class DescriptorParser {
    // Minimal parser for a USB Device Descriptor (first 18 bytes)
    public static String parseDeviceDescriptor(byte[] data) {
        if (data == null || data.length < 18) return "Invalid device descriptor (too short)";
        int bLength = data[0] & 0xFF;
        int bDescriptorType = data[1] & 0xFF;
        int bcdUSB = ((data[3] & 0xFF) << 8) | (data[2] & 0xFF);
        int idVendor = ((data[9] & 0xFF) << 8) | (data[8] & 0xFF);
        int idProduct = ((data[11] & 0xFF) << 8) | (data[10] & 0xFF);
        int bDeviceClass = data[4] & 0xFF;
        return String.format("Device Descriptor: bLength=%d type=%d USB=0x%04X VID=0x%04X PID=0x%04X class=0x%02X",
                bLength, bDescriptorType, bcdUSB, idVendor, idProduct, bDeviceClass);
    }

    public static String hexToString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) sb.append(String.format("%02X ", b));
        return sb.toString().trim();
    }
}
