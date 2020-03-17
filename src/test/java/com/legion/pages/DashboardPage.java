package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public void verifyTheWelcomeMessage(String userName) throws Exception;
    public String getCurrentDateFromDashboard() throws Exception;
    public String getCurrentTimeFromDashboard() throws Exception;
    public HashMap<String, String> getHoursFromDashboardPage() throws Exception;
    public boolean isProjectedDemandGraphShown() throws Exception;
    public boolean isStartingSoonLoaded() throws Exception;
    public void verifyStartingSoonNScheduledHourWhenGuidanceOrDraft(boolean isStartingSoonLoaded, String scheduledHour)
        throws Exception;
    public HashMap<String, String> getUpComingShifts() throws Exception;
    public boolean isStartingTomorrow() throws Exception;
}
