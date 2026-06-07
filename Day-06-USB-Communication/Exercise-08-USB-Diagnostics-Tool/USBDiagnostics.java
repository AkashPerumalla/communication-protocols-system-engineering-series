package Exercise08;

public class USBDiagnostics {
    public static void main(String[] args) {
        System.out.println("USB Diagnostics — quick health check for Day-06 simulators");
        String host = "127.0.0.1";
        int[] ports = new int[] {8001, 8003, 8004, 8005, 8006};
        boolean allOk = true;
        for (int p : ports) {
            boolean ok = USBHealthMonitor.checkPort(host, p, 300);
            System.out.printf("Port %d: %s\n", p, ok ? "OPEN" : "CLOSED");
            allOk = allOk && ok;
        }
        System.out.println(allOk ? "Enumeration Success: some simulators are reachable." : "Diagnostics: some simulators are not running.");
    }
}
