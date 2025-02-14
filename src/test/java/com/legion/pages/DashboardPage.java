package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, String> getUpComingShifts() throws Exception;
    public boolean isStartingTomorrow() throws Exception;
    public boolean isViewMySchedulePresentAndClickable() throws Exception;
    public void validateTMAccessibleTabs() throws Exception;
    public void validateThePresenceOfLocation() throws Exception;
    public void validateTheAccessibleLocation() throws Exception;
    public void validateThePresenceOfLogo() throws Exception;
    public void validateDateAndTimeAfterSelectingDifferentLocation() throws Exception;
    public void validateTheVisibilityOfUsername(String userName) throws Exception;
    public void validateDateAndTime() throws Exception;
    public void validateTheUpcomingSchedules(String location) throws Exception;
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
    public void verifyTheWelcomeMessageOfDM(String userName) throws Exception;
    public String getCurrentLocationInDMView() throws Exception;
    public boolean isLegionLogoDisplay() throws Exception;
    public boolean isDashboardConsoleMenuDisplay() throws Exception;
    public boolean isTeamConsoleMenuDisplay() throws Exception;
    public boolean isScheduleConsoleMenuDisplay() throws Exception;
    public boolean isReportConsoleMenuDisplay() throws Exception;
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
    public List<String> getDistrcitListInDashboard() throws Exception;
    public void validateThePresenceOfRefreshButton() throws Exception;
    public void validateRefreshFunction() throws Exception;
    public void validateRefreshPerformance() throws Exception;
    public void validateRefreshWhenNavigationBack(String consoleName) throws Exception;
    public void validateRefreshTimestamp() throws Exception;
    public void validateTheContentOnTimesheetApprovalRateWidgetInUpperfieldView() throws Exception;
    public void validateStatusValueOfTimesheetApprovalRateWidget() throws Exception;
    public List<String> getTimesheetApprovalRateOnDMViewWidget() throws Exception;
    public void clickOnViewTimesheets() throws Exception;
    public void validateDataOnTimesheetApprovalRateWidget(List<String> timesheetApprovalRateOnDMViewDashboard, List<String> timesheetApprovalRateFromSmartCardOnDMViewTimesheet) throws Exception;
    public boolean isProjectedComplianceWidgetDisplay() throws Exception;
    public void verifyTheContentInProjectedComplianceWidget() throws Exception;
    public String getTheTotalViolationHrsFromProjectedComplianceWidget() throws Exception;
    public void clickOnViewViolationsLink() throws Exception;
    public void validateTheContentOnComplianceViolationsWidgetInUpperfield() throws Exception;
    public void validateDataOnComplianceViolationsWidget(List<String> complianceViolationsOnDashboard, List<String> complianceViolationsFromSmartCardOnCompliance) throws Exception;
    public List<String> getComplianceViolationsOnDashboard() throws Exception;
    public void validateTheContentOnPayrollProjectionWidget(boolean isLaborBudgetToApply) throws Exception;
    public String getWeekOnPayrollProjectionWidget() throws Exception;
    public void validateWeekOnPayrollProjectionWidget(String weekOnPayrollProjectionWidget, String currentWeekInScheduleTab) throws Exception;
    public void validateTodayAtTimeOnPayrollProjectionWidget() throws Exception;
    public void clickOnViewSchedulesOnPayrollProjectWidget() throws Exception;
    public boolean isComplianceViolationsWidgetDisplay() throws Exception;
    public boolean isTimesheetApprovalRateWidgetDisplay() throws Exception;
    public boolean isPayrollProjectionWidgetDisplay() throws Exception;
    public void validateTheFutureBudgetComparisonOnPayrollProjectionWidget() throws Exception;
    public void validateHoursTooltipsOfPayrollProjectionWidget() throws Exception;
    public String getBudgetComparisonOnPayrollProjectionWidget() throws Exception;
    public void validateBudgetComparisonOnPayrollProjectionWidget(String forecastKPIOnPayrollProjectionWidget, String forecastKPIInScheduleTab) throws Exception;
    public void clickOnViewSchedulesLinkInSchedulePublishStatusWidget() throws Exception;
    public boolean isSchedulePublishStatusWidgetDisplay() throws Exception;
    public void verifyTheContentInSchedulePublishStatusWidget() throws Exception;
    public Map<String,Integer> getAllScheduleStatusFromSchedulePublishStatusWidget() throws Exception;
    public String getWeekInfoFromUpperfieldView() throws Exception;
    public boolean isScheduleVsGuidanceByDayWidgetDisplay() throws Exception;
    public void verifyTheContentOnScheduleVsGuidanceByDayWidget() throws Exception;
    public void verifyTheHrsUnderOrCoverBudgetOnScheduleVsGuidanceByDayWidget() throws Exception;
    public List<String> getTheDataOnLocationSummaryWidget() throws Exception;
    public void verifyTheContentOnOrgSummaryWidget(boolean isClockEnable,boolean isLaborBudgetToApply) throws Exception;
    public boolean isLocationSummaryWidgetDisplay() throws Exception;
    public boolean isOpenShiftsWidgetDisplay() throws Exception;
    public void clickViewSchedulesLinkOnOpenShiftsWidget() throws Exception;
    public HashMap<String, Integer> verifyContentOfOpenShiftsWidgetForDMView() throws Exception;
    public void verifyTheHrsOverOrUnderBudgetOnLocationSummaryWidget() throws Exception;
    public boolean isConsoleNavigationBarIsGray(String consoleNavigationBarName) throws Exception;
    public boolean isConsoleNavigationBarBeenSelected (String consoleNavigationBarName) throws Exception;
    public String getTitleOnOrgSummaryWidget() throws Exception;

    public void validateThePresenceOfUpperfield() throws Exception;
    public String getUpperfieldNameOnDashboard() throws Exception;
    public void verifyTheWelcomeMessageForUpperfield(String userName) throws Exception;
    public void validateThePresenceOfRefreshButtonUpperfield() throws Exception;
    public void validateRefreshTimestampUpperfield() throws Exception;
    public void validateRefreshWhenNavigationBackUpperfied() throws Exception;
    public void validateRefreshFunctionUpperfield()  throws Exception;
    public void validateRefreshPerformanceUpperfield() throws Exception;
    public void validateTooltipsOfSchedulePublishStatusWidget() throws Exception;
    public String getWeekOnScheduleVsGuidanceByDayWidget() throws Exception;
    public void clickOnViewSchedulesOnScheduleVsGuidanceByDayWidget() throws Exception;
    public void validateWeekOnScheduleVsGuidanceByDayWidget(String weekOnScheduleVsGuidanceByDayWidget, String currentWeekInScheduleTab) throws Exception;
    public void validateValueInScheduleVsGuidanceByDayWidget() throws Exception;
    public HashMap<String, Integer> getTheSumOfValuesOnScheduleVsGuidanceByDayWidget() throws Exception;
    public void verifyTheContentOnScheduleVsBudgetByDayWidget() throws Exception;
    public HashMap<String, Integer> verifyContentOfOpenShiftsWidgetForUpperfield() throws Exception;
    public boolean isOpenShiftsWidgetPresent() throws Exception;
    public void clickOnViewSchedulesOnOpenShiftsWidget() throws Exception;
    public boolean isRefreshButtonDisplay() throws Exception;
    public HashMap<String, Integer> getTheSumOfValuesOnPayrollProjectionWidget() throws Exception;
    public void validateAsOfTimeUnderProjectedOnOrgSummaryWidget() throws Exception;
    public void clickOnViewSchedulesOnOrgSummaryWidget() throws Exception;
    public List<HashMap<String, String>> getAllUpComingShiftsInfo() throws Exception;
    public void getWeekFromDate(String sun) throws Exception;
    public void clickOnRefreshButtonOnSMDashboard() throws Exception;
}
