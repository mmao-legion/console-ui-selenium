package com.legion.pages;

import org.openqa.selenium.WebElement;

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
    public boolean isViewMySchedulePresentAndClickable() throws Exception;
    public void validateTMAccessibleTabs() throws Exception;
    public void validateThePresenceOfLocation() throws Exception;
    public void validateTheAccessibleLocation() throws Exception;
    public void validateThePresenceOfLogo() throws Exception;
    public void validateDateAndTimeAfterSelectingDifferentLocation() throws Exception;
    public void validateTheVisibilityOfUsername(String userName) throws Exception;
    public void validateDateAndTime() throws Exception;
    public void validateTheUpcomingSchedules(String userName) throws Exception;
    public void validateVIEWMYSCHEDULEButtonClickable() throws Exception;
    public void validateTheVisibilityOfProfilePicture() throws Exception;
    public void validateProfilePictureIconClickable() throws Exception;
    public void validateTheVisibilityOfProfile() throws Exception;
    public void validateProfileDropdownClickable() throws Exception;
    public void validateTheDataOfMyProfile() throws Exception;
    public void validateTheDataOfMyWorkPreferences(String date) throws Exception;
    public void validateTheDataOfMyTimeOff() throws Exception;
    public void clickOnSubMenuOnProfile(String subMenu) throws Exception;
    public String getDateFromTimeZoneOfLocation(String pattern) throws Exception;
    public void clickOnSwitchToEmployeeView() throws Exception;
    public boolean isSwitchToEmployeeViewPresent() throws Exception;
    public List<WebElement> getDashboardScheduleWeeks() throws Exception;
}
