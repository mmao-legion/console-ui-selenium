package com.legion.pages;

import java.util.List;
public interface StaffingGuidancePage {

	public void navigateToStaffingGuidanceTab() throws Exception;
	public Boolean isStaffingGuidanceTabActive() throws Exception;
	public void navigateToStaffingGuidanceTabWeekView() throws Exception;
	public Boolean isStaffingGuidanceForecastTabWeekViewActive() throws Exception;
	public void navigateToStaffingGuidanceTabDayView() throws Exception;
	public Boolean isStaffingGuidanceForecastTabDayViewActive() throws Exception;
	public List<String> getStaffingGuidanceForecastDayViewTimeDuration() throws Exception;
	public List<Integer> getStaffingGuidanceForecastDayViewItemsCount() throws Exception;
	public List<Integer> getStaffingGuidanceForecastDayViewTeamMembersCount() throws Exception;
	public List<String> getStaffingGuidanceDayDateMonthLabelsForWeekView() throws Exception;
	public List<Float> getStaffingGuidanceHoursCountForWeekView() throws Exception;
}
