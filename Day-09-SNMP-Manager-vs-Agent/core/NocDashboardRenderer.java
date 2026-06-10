public final class NocDashboardRenderer {
    private NocDashboardRenderer() {
    }

    public static void print(DashboardSnapshot snapshot) {
        System.out.println("==================================");
        System.out.println("NETWORK OPERATIONS CENTER");
        System.out.println("==================================");
        System.out.println("Devices Online: " + snapshot.devicesOnline());
        System.out.println("Devices Offline: " + snapshot.devicesOffline());
        System.out.println("Critical Alarms: " + snapshot.criticalAlarms());
        System.out.println("Major Alarms: " + snapshot.majorAlarms());
        System.out.println("Average CPU: " + snapshot.averageCpu() + "%");
        System.out.println("Average Memory: " + snapshot.averageMemory() + "%");
        System.out.println("==================================");
    }
}
