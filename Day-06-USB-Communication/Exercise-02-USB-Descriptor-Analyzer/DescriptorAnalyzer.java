package Exercise02;

import java.util.logging.Logger;

public class DescriptorAnalyzer {
    private static final Logger logger = Logger.getLogger(DescriptorAnalyzer.class.getName());

    public static void main(String[] args) {
        // Example device descriptor (18 bytes). Values chosen for demonstration.
        byte[] sample = new byte[] {
                0x12, 0x01, 0x00, 0x02, // bLength, bDescriptorType, bcdUSB
                0x00, // bDeviceClass
                0x00, // bDeviceSubClass
                0x00, // bDeviceProtocol
                0x40, // bMaxPacketSize0
                (byte)0x34, (byte)0x12, // idVendor (0x1234)
                (byte)0xCD, (byte)0xAB, // idProduct (0xABCD)
                0x00, 0x01, // bcdDevice
                0x01, // iManufacturer
                0x02, // iProduct
                0x03, // iSerialNumber
                0x01  // bNumConfigurations
        };
        System.out.println(DescriptorParser.parseDeviceDescriptor(sample));
        System.out.println("Raw Hex: " + DescriptorParser.hexToString(sample));
        logger.info("Descriptor analysis complete.");
    }
}
