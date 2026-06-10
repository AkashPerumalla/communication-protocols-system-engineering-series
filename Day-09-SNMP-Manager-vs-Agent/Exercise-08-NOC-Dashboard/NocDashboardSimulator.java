public class NocDashboardSimulator {
    public static void main(String[] args) throws InterruptedException {
        DashboardSnapshot snapshot = SnmpFixtures.dashboardSnapshot();
        for (int cycle = 1; cycle <= 2; cycle++) {
            System.out.println("Refresh Cycle: " + cycle);
            NocDashboardRenderer.print(snapshot);
            if (cycle < 2) {
                System.out.println("Refreshing dashboard...\n");
                Thread.sleep(50L);
            }
        }
    }
}
