package Exercise07;

public class AndroidUsbHostExample {
    public static void main(String[] args) {
        System.out.println("Android USB Host Example (host-side)\n");
        System.out.println("This file contains example snippets and guidance for using UsbManager, UsbDevice, UsbInterface, and UsbEndpoint.");
        System.out.println("On Android you obtain an instance via context.getSystemService(UsbManager.class) and then query connected devices.");
        System.out.println("Permission flow, BroadcastReceiver registration, and UsbDeviceConnection.openDevice() are described in the README for Exercise-07.");
    }
}
