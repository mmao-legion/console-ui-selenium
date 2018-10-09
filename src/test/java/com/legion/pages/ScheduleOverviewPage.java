package com.legion.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ScheduleOverviewPage {

	public List<String> getScheduleWeeksStatus();
	public Map<String, String> getWeekStartDayAndCurrentWeekDates() throws Exception;
	public List<HashMap<String, String>> getOverviewPageWeeksDuration();
	public Boolean isCurrentWeekHighLighted() throws Exception;
	public Boolean verifyDateAndDayForEachWeekUntilNotAvailable() throws Exception;
	public Boolean verifyDayAndDateOnSchedulePageMatchesDayAndDateOnOverviewPage() throws Exception;
	public List<String> getCurrentAndUpcomingActiveWeeksDaysOnCalendar() throws Exception;
	public void clickOnCurrentWeekToOpenSchedule() throws Exception;
	public String getOverviewCalenderWeekDays() throws Exception;
}
