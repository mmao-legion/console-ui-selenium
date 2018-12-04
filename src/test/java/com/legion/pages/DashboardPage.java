package com.legion.pages;

/**
 * Yanming
 */
public interface DashboardPage {
    public SchedulePage goToToday() throws Exception;
    public boolean isToday() throws Exception;
    public SchedulePage goToTodayForNewUI() throws Exception;
    public void verifyDashboardPageLoadedProperly() throws Exception;
    public Boolean isDashboardPageLoaded() throws Exception;
    public void navigateToDashboard() throws Exception;
    public void verifySuccessfulNavToDashboardnLoading() throws Exception;
}
