import java.util.List;

public final class SecureNocMonitoringDemo {
    private SecureNocMonitoringDemo() {
    }

    public static void main(String[] args) {
        NocDashboardService dashboardService = new NocDashboardService();
        List<NocDashboardService.DashboardRow> rows = dashboardService.buildDashboard(SampleData.secureNocSessions());

        System.out.println(dashboardService.renderDashboard(rows));
        System.out.println("SECURE NOC");
    }
}
