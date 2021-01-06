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
    public void clickOnRefreshButton() throws Exception;
    public void clickOnDashboardConsoleMenu() throws Exception;
    public String getCurrentLocation() throws Exception;
    public String  getCurrentDistrict() throws Exception;
    public boolean IsThereDistrictNavigationForLegionBasic() throws Exception;
    public List<String> getLocationListInDashboard();
    public String getHeaderOnDashboard() throws Exception;
    public void verifyHeaderOnDashboard() throws Exception;
    public void validateThePresenceOfDistrict() throws Exception;
    public void validateTheVisibilityOfWeek() throws Exception;
    public String getDistrictNameOnDashboard() throws Exception;
    public void verifyTheWelcomeMessageOfDM(String userName) throws Exception;
    public String getCurrentLocationInDMView() throws Exception;
    public boolean isLegionLogoDisplay() throws Exception;
    public boolean isDashboardConsoleMenuDisplay() throws Exception;
    public boolean isTeamConsoleMenuDisplay() throws Exception;
    public boolean isScheduleConsoleMenuDisplay() throws Exception;
    public boolean isAnalyticsConsoleMenuDisplay() throws Exception;
    public boolean isInboxConsoleMenuDisplay() throws Exception;
    public boolean isAdminConsoleMenuDisplay() throws Exception;
    public void clickOnAdminConsoleMenu() throws Exception;
    public boolean isIntegrationConsoleMenuDisplay() throws Exception;
    public void clickOnIntegrationConsoleMenu() throws Exception;
    public boolean isControlsConsoleMenuDisplay() throws Exception;
    public boolean isLogoutConsoleMenuDisplay() throws Exception;
    public boolean isTimesheetConsoleMenuDisplay() throws Exception;
    public void verifyAdminPageIsLoaded() throws Exception;
    public void verifyIntegrationPageIsLoaded() throws Exception;
    public void verifyHeaderNavigationMessage(String headerNavigationMessage) throws Exception;
    public void closeNewFeatureEnhancementsPopup() throws Exception;
    public void validateThePresenceOfRefreshButton() throws Exception;
    public void validateRefreshFunction() throws Exception;
    public void validateRefreshPerformance() throws Exception;
    public void validateRefreshWhenNavigationBack() throws Exception;
    public void validateRefreshTimestamp() throws Exception;
    public boolean isProjectedComplianceWidgetDisplay() throws Exception;
    public void verifyTheContentInProjectedComplianceWidget() throws Exception;
    public String getTheTotalViolationHrsFromProjectedComplianceWidget() throws Exception;
    public void clickOnViewComplianceLink() throws Exception;
}
