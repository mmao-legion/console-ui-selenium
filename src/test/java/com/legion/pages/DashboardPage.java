package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;

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
	public ArrayList<HashMap<String, Float>> getDashboardForeCastDataForAllLocation();
	public HashMap<String, Float> getTodaysForcastData() throws Exception;
    public void clickOnProfileIconOnDashboard() throws Exception;
    public void clickOnTimeOffLink() throws Exception;
}
