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
	public void navigateWeekViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount);
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
	
	
	//public void getAvailableFilters();
    public void filterScheduleByWorkRoleAndShiftType(boolean isWeekView);
    public void selectGroupByFilter(String optionVisibleText);
    public String getActiveWeekText() throws Exception;
    public ArrayList<WebElement> getAllAvailableShiftsInWeekView();
    public ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception;
    public ArrayList<Float> getAllVesionLabels() throws Exception;
	public void publishActiveSchedule()throws Exception;
	public boolean isPublishButtonLoaded();
	public void validateBudgetPopUpHeader(String nextWeekView, int weekCount);
	
}
