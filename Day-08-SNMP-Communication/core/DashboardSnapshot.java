public final class DashboardSnapshot {
    private final int devicesOnline;
    private final int devicesOffline;
    private final int criticalAlarms;
    private final int majorAlarms;
    private final int minorAlarms;
    private final int warningAlarms;
    private final int averageCpu;
    private final int averageMemory;

    public DashboardSnapshot(int devicesOnline, int devicesOffline, int criticalAlarms, int majorAlarms, int minorAlarms, int warningAlarms, int averageCpu, int averageMemory) {
        this.devicesOnline = devicesOnline;
        this.devicesOffline = devicesOffline;
        this.criticalAlarms = criticalAlarms;
        this.majorAlarms = majorAlarms;
        this.minorAlarms = minorAlarms;
        this.warningAlarms = warningAlarms;
        this.averageCpu = averageCpu;
        this.averageMemory = averageMemory;
    }

    public int devicesOnline() {
        return devicesOnline;
    }

    public int devicesOffline() {
        return devicesOffline;
    }

    public int criticalAlarms() {
        return criticalAlarms;
    }

    public int majorAlarms() {
        return majorAlarms;
    }

    public int minorAlarms() {
        return minorAlarms;
    }

    public int warningAlarms() {
        return warningAlarms;
    }

    public int averageCpu() {
        return averageCpu;
    }

    public int averageMemory() {
        return averageMemory;
    }
}
