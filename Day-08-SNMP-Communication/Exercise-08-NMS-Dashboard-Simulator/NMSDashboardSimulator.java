import java.util.List;

public final class NMSDashboardSimulator {
    public static void main(String[] args) throws InterruptedException {
        ConsoleDashboardRenderer renderer = new ConsoleDashboardRenderer();
        List<DashboardSnapshot> snapshots = DemoDataFactory.dashboardSnapshots();

        for (int cycle = 0; cycle < snapshots.size(); cycle++) {
            System.out.println("REFRESH CYCLE " + (cycle + 1));
            System.out.println(renderer.render(snapshots.get(cycle)));
            if (cycle + 1 < snapshots.size()) {
                Thread.sleep(50L);
            }
        }
    }
}
