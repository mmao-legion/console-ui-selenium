package com.legion.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

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
	public List<WebElement> getOverviewScheduleWeeks();
	public void clickScheduleDraftAndGuidanceStatus(List<String> overviewScheduleWeeksStatus);
	public ArrayList<String> getOverviewCalendarMonthsYears() throws Exception;
	public LinkedHashMap<String, Float> getWeekHoursByWeekElement(WebElement overViewWeek);
	public void clickOnGuidanceBtnOnOverview(int index);
	public boolean loadScheduleOverview() throws Exception;
	public int getScheduleOverviewWeeksCountCanBeCreatInAdvance();
	public String getOverviewWeekDuration(WebElement webElement) throws Exception;
	public void clickOverviewTab();
}
