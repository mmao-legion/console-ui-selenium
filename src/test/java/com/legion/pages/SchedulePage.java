package com.legion.pages;

import java.util.List;
import java.util.Map;

public interface SchedulePage {
	public void gotoToSchedulePage() throws Exception;
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
	public void clickOnScheduleSubTab(String subTabString);
	public List<String> getScheduleWeeksStatus();
	public void navigateSalesForecastWeekViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount) throws Exception;
	public Boolean isWeekGenerated() throws Exception;
	public Boolean isWeekPublished() throws Exception;
	public void generateSchedule() throws Exception;
}
