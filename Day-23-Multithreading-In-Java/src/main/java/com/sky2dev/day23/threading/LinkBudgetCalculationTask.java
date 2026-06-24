package com.sky2dev.day23.threading;

import com.sky2dev.day23.dto.LinkBudgetResponse;
import com.sky2dev.day23.util.MarkerConstants;
import java.util.concurrent.Callable;

public class LinkBudgetCalculationTask implements Callable<LinkBudgetResponse> {

    @Override
    public LinkBudgetResponse call() throws Exception {
        Thread.sleep(200);
        double eirp = 51.2;
        double cn = 14.8;
        double linkMargin = 3.5;
        return new LinkBudgetResponse(MarkerConstants.CALLABLE_EXECUTED, eirp, cn, linkMargin);
    }
}
