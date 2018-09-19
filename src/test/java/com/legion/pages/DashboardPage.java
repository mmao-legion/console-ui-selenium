package com.legion.pages;

/**
 * Yanming
 */
public interface DashboardPage {
    public SchedulePage goToToday() throws Exception;
    public boolean isToday() throws Exception;
    public void verifyDashboardPageLoadedProperly() throws Exception;
}
