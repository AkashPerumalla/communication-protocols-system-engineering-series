import java.util.List;

public final class NocMibDashboard {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibConsole.printBanner("DAY 10 - EXERCISE 08 - NOC MIB DASHBOARD", "NOC DASHBOARD");
        printDashboard(repository.dashboardRows());
    }

    private static void printDashboard(List<String[]> rows) {
        MibConsole.printRows(
                "Device                 | Status | CPU | Memory | Temperature | Alarm State",
                "-----------------------+--------+-----+--------+-------------+------------",
                rows);
    }
}
