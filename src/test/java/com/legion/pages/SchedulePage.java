package com.legion.pages;

import java.util.List;
import java.util.Map;

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
	public Map<String, Float> getScheduleLabelHoursAndWagges() throws Exception;
	public List<Map<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception;
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

}
