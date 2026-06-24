package com.sky2dev.day23.threading;

import com.sky2dev.day23.service.DashboardService;

public class DashboardUpdateTask implements Runnable {

    private final DashboardService dashboardService;

    public DashboardUpdateTask(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public void run() {
        dashboardService.buildDashboardSnapshot();
    }
}
