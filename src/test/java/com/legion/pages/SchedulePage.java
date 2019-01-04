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
	public void budgetHourInScheduleNBudgetedSmartCard(String nextWeekView, int weekCount);
	public void disableNextWeekArrow() throws Exception;
	public void clickScheduleDraftAndGuidanceStatus(List<String> overviewScheduleWeeksStatus);
	public void editBudgetHours();


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
	public void validatingGenrateSchedule() throws Exception;
}
