package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

public interface SchedulePage {
	public void clickOnScheduleConsoleMenuItem();
	public void goToSchedulePage() throws Exception;
	public boolean isSchedulePage() throws Exception;
	public Boolean varifyActivatedSubTab(String SubTabText) throws Exception;
	public void goToSchedule() throws Exception;
	public void goToProjectedSales() throws Exception;
	public void goToStaffingGuidance() throws Exception;
	public boolean isSchedule() throws Exception;
	public void clickOnWeekView() throws Exception;
	public void clickOnDayView() throws Exception;
	public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception;
	public List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception;
	public void clickOnScheduleSubTab(String subTabString) throws Exception;
	public void navigateWeekViewOrDayViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount);
	public Boolean isWeekGenerated() throws Exception;
	public Boolean isWeekPublished() throws Exception;
	public void generateSchedule() throws Exception;
	public String getScheduleWeekStartDayMonthDate();
	public void clickOnEditButton() throws Exception;
	public void clickImmediateNextToCurrentActiveWeekInDayPicker() throws Exception;
	public Boolean isAddNewDayViewShiftButtonLoaded() throws Exception;
	public void clickOnCancelButtonOnEditMode() throws Exception;
	public Boolean isGenerateButtonLoaded() throws Exception;
	public String getActiveWeekDayMonthAndDateForEachDay() throws Exception;
	public Boolean validateScheduleActiveWeekWithOverviewCalendarWeek(String overviewCalendarWeekDate, String overviewCalendarWeekDays, String scheduleActiveWeekDuration);
	public boolean isCurrentScheduleWeekPublished();
	public void validatingRefreshButtononPublishedSchedule() throws Exception;
	public void isGenerateScheduleButton() throws Exception;
	public void validatingScheduleRefreshButton() throws Exception;
	public void clickOnSchedulePublishButton() throws Exception;
	public void navigateDayViewToPast(String nextWeekViewOrPreviousWeekView, int weekCount) throws Exception;
	public String clickNewDayViewShiftButtonLoaded() throws Exception;
	public void customizeNewShiftPage() throws Exception;
	public void compareCustomizeStartDay(String textStartDay) throws Exception;
	public void moveSliderAtSomePoint(String shiftTime, int shiftStartingCount, String startingPoint) throws Exception;
	public HashMap<String, String> calculateHourDifference() throws Exception;
	public void selectWorkRole(String workRoles) throws Exception;
	public void clickRadioBtnStaffingOption(String staffingOption) throws Exception;
	public void clickOnCreateOrNextBtn() throws Exception;
	public HashMap<List<String>,List<String>> calculateTeamCount()throws Exception;
	public List<String> calculatePreviousTeamCount(
			HashMap<String, String> previousTeamCount, HashMap<List<String>,List<String>> 
			gridDayHourPrevTeamCount)throws Exception;
	public List<String> calculateCurrentTeamCount(HashMap<String, String> shiftTiming)throws Exception;
	public void clickSaveBtn() throws Exception;
	public void clickOnVersionSaveBtn() throws Exception;
	public void clickOnPostSaveBtn() throws Exception;
    public void filterScheduleByWorkRoleAndShiftType(boolean isWeekView);
    public void selectGroupByFilter(String optionVisibleText);
    public String getActiveWeekText() throws Exception;
    public ArrayList<WebElement> getAllAvailableShiftsInWeekView();
    public ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception;
    public ArrayList<Float> getAllVesionLabels() throws Exception;
	public void publishActiveSchedule()throws Exception;
	public boolean isPublishButtonLoaded();
	public HashMap<String, Float> getScheduleLabelHours() throws Exception;
	public int getgutterSize();
	public void verifySelectTeamMembersOption() throws Exception;
	public void searchText(String searchInput) throws Exception;
	public void getAvailableStatus()throws Exception;
	public void clickOnOfferOrAssignBtn() throws Exception;
	public void clickOnShiftContainer(int index) throws Exception;
	public void deleteShift();
	public void deleteShiftGutterText();
	public boolean getScheduleStatus()throws Exception;
	public boolean inActiveWeekDayClosed(int dayIndex) throws Exception;
	public void navigateDayViewWithIndex(int dayIndex);
	public String getActiveGroupByFilter() throws Exception;
	public boolean isActiveWeekHasOneDayClose() throws Exception;
	public boolean isActiveWeekAssignedToCurrentUser(String userName) throws Exception;
	public boolean isScheduleGroupByWorkRole(String workRoleOption) throws Exception;
	public void selectWorkRoleFilterByIndex(int index, boolean isClearWorkRoleFilters) throws Exception;
	public ArrayList<String> getSelectedWorkRoleOnSchedule() throws Exception;
	public boolean isRequiredActionUnAssignedShiftForActiveWeek() throws Exception;
	public void clickOnRefreshButton() throws Exception;
	public void selectShiftTypeFilterByText(String filterText) throws Exception;
	public List<WebElement> getAvailableShiftsInDayView();
	public void dragShiftToRightSide(WebElement shift, int xOffSet);
	public boolean isSmartCardAvailableByLabel(String cardLabel) throws Exception;
	public void validateBudgetPopUpHeader(String nextWeekView, int weekCount);
	public void noBudgetDisplayWhenBudgetNotEntered(String nextWeekView, int weekCount);
//	public void budgetHourInScheduleNBudgetedSmartCard(String nextWeekView, int weekCount);
//	public void budgetHourByWagesInScheduleNBudgetedSmartCard(String nextWeekView,int weekCount);
	public void budgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount, int tolerance);
	public void disableNextWeekArrow() throws Exception;
	public void clickScheduleDraftAndGuidanceStatus(List<String> overviewScheduleWeeksStatus);
	public void editBudgetHours();
	public void navigateScheduleDayWeekView(String nextWeekView, int weekCount);

	public ArrayList<String> getActiveWeekCalendarDates() throws Exception;
	public void refreshBrowserPage() throws Exception;
	public void addOpenShiftWithDefaultTime(String workRole) throws Exception;
	public boolean isNextWeekAvaibale() throws Exception;
	public boolean isSmartCardPanelDisplay() throws Exception;
	public void convertAllUnAssignedShiftToOpenShift() throws Exception;
	public void selectWorkRoleFilterByText(String workRoleLabel, boolean isClearWorkRoleFilters) throws Exception;
	public void reduceOvertimeHoursOfActiveWeekShifts() throws Exception;
	public boolean isActionButtonLoaded(String actionBtnText) throws Exception;
	public void navigateToNextDayIfStoreClosedForActiveDay() throws Exception;
	/*public void validatingRequiredActionforUnAssignedShift() throws Exception;*/
	public String getsmartCardTextByLabel(String cardLabel);
	public String getWeatherTemperature() throws Exception;
	public void validatingGenrateSchedule() throws Exception;
	public boolean loadSchedule() throws Exception;
	public void generateOrUpdateAndGenerateSchedule() throws Exception;
	public HashMap<String, Integer> getScheduleBufferHours() throws Exception;
	public boolean isComlianceReviewRequiredForActiveWeek() throws Exception;
	public void unGenerateActiveScheduleScheduleWeek() throws Exception;
	public boolean isStoreClosedForActiveWeek() throws Exception;
	public int getScheduleShiftIntervalCountInAnHour() throws Exception;
	public void toggleSummaryView() throws Exception;
	public boolean isSummaryViewLoaded() throws Exception;
	public void updateScheduleOperatingHours(String day, String startTime, String endTime) throws Exception;
	public void dragRollerElementTillTextMatched(WebElement rollerElement, String textToMatch) throws Exception;
	public boolean isScheduleOperatingHoursUpdated(String startTime, String endTime) throws Exception;
	public void verifyScheduledHourNTMCountIsCorrect() throws Exception;
	public void complianceShiftSmartCard() throws Exception;
	public void viewProfile();
	public void changeWorkerRole() throws Exception;
	public void assignTeamMember() throws Exception;
	public void convertToOpenShift();
	public void beforeEdit();
	public void selectTeamMembersOptionForOverlappingSchedule() throws Exception;
	public boolean getScheduleOverlappingStatus()throws Exception;
	public void searchWorkerName(String searchInput) throws Exception;
	public void verifyScheduleStatusAsOpen() throws Exception;
	public void verifyScheduleStatusAsTeamMember() throws Exception;
	public String getActiveAndNextDay() throws Exception;
	public HashMap<String, String> getOperatingHrsValue(String day) throws Exception;
	public void moveSliderAtCertainPoint(String shiftTime, String startingPoint) throws Exception;
	public void clickOnNextDaySchedule(String activeDay) throws Exception;
	public void selectTeamMembersOptionForSchedule() throws Exception;
	public void selectTeamMembersOptionForScheduleForClopening() throws Exception;
	public void verifyClopeningHrs() throws Exception;
	public void clickOnPreviousDaySchedule(String activeDay) throws Exception;
	public void verifyActiveScheduleType() throws Exception;
	public List<Float> validateScheduleAndBudgetedHours() throws Exception;
	public void compareHoursFromScheduleAndDashboardPage(List<Float> totalHoursFromSchTbl) throws Exception;
	public List<Float> getHoursOnLocationSummarySmartCard() throws Exception;
	public void compareHoursFromScheduleSmartCardAndDashboardSmartCard(List<Float> totalHoursFromSchTbl) throws Exception;
	public void compareProjectedWithinBudget(int totalCountProjectedOverBudget) throws Exception;
	public int getProjectedOverBudget();
	public String getDateFromDashboard() throws Exception;
	public void compareDashboardAndScheduleWeekDate(String DateOnSchdeule, String DateOnDashboard) throws Exception;
	public List<String> getLocationSummaryDataFromDashBoard() throws Exception;
	public List<String> getLocationSummaryDataFromSchedulePage() throws Exception;
	public void compareLocationSummaryFromDashboardAndSchedule(List<String> ListLocationSummaryOnDashboard, List<String> ListLocationSummaryOnSchedule);
	public void openBudgetPopUpGenerateSchedule() throws Exception;
	public void updatebudgetInScheduleNBudgetSmartCard(String nextWeekView, int weekCount);
	public void toNFroNavigationFromDMToSMSchedule(String CurrentWeek, String locationToSelectFromDMViewSchedule, String districtName, String nextWeekViewOrPreviousWeekView) throws Exception;
	public void toNFroNavigationFromDMDashboardToDMSchedule(String CurrentWeek) throws Exception;
	public void clickOnViewScheduleLocationSummaryDMViewDashboard();
	public void clickOnViewSchedulePayrollProjectionDMViewDashboard();
	public void loadingOfDMViewSchedulePage(String SelectedWeek) throws Exception;
	public void districtSelectionSMView(String districtName) throws Exception;
	public void isScheduleForCurrentDayInDayView(String dateFromDashboard) throws Exception;
	public HashMap<String, String> getHoursFromSchedulePage() throws Exception;
	public void printButtonIsClickable() throws Exception;
	public void todoButtonIsClickable()throws Exception;
	public void closeButtonIsClickable();
	public void legionButtonIsClickableAndHasNoEditButton() throws Exception;;
	public void clickOnSuggestedButton() throws Exception;
	public void legionIsDisplayingTheSchedul() throws Exception;

	public void currentWeekIsGettingOpenByDefault() throws Exception;

	public void goToScheduleNewUI() throws Exception;

	public void dayWeekPickerSectionNavigatingCorrectly() throws Exception;

	public void landscapePortraitModeShowWellInWeekView() throws Exception;

	public void landscapeModeWorkWellInWeekView() throws Exception;

	public void portraitModeWorkWellInWeekView()throws Exception;

	public void landscapeModeOnlyInDayView() throws Exception;

	public void weatherWeekSmartCardIsDisplayedForAWeek() throws Exception;

	public void scheduleUpdateAccordingToSelectWeek() throws Exception;

	public boolean verifyRedFlagIsVisible() throws Exception;

	public void verifyComplianceShiftsSmartCardShowing() throws Exception;

	public boolean clickViewShift() throws Exception;

	public void verifyComplianceFilterIsSelectedAftClickingViewShift() throws Exception;

	public void verifyComplianceShiftsShowingInGrid() throws Exception;

	public void verifyClearFilterFunction() throws Exception;

	public void clickOnFilterBtn() throws Exception;



	public void  verifyShiftSwapCoverRequestedIsDisplayInTo();

	public void verifyAnalyzeBtnFunctionAndScheduleHistoryScroll() throws Exception;

	public HashMap<String, Float> getScheduleBudgetedHoursInScheduleSmartCard() throws Exception;


	public HashMap<String, String> getFourUpComingShifts(boolean isStartTomorrow) throws Exception;
	public void verifyUpComingShiftsConsistentWithSchedule(HashMap<String, String> dashboardShifts, HashMap<String, String> scheduleShifts) throws Exception;
	public void clickOnCreateNewShiftWeekView() throws Exception;
	public void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception;
	public void selectDaysFromCurrentDay(String currentDay) throws Exception;
	public void searchTeamMemberByName(String name) throws Exception;
	public void verifyNewShiftsAreShownOnSchedule(String name) throws Exception;
	public void verifyShiftsChangeToOpenAfterTerminating(List<Integer> indexes, String name, String currentTime) throws Exception;
	public List<Integer> getAddedShiftIndexes(String name) throws Exception;
}
