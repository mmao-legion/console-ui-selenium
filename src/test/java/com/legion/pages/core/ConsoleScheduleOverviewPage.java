package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.utils.SimpleUtils;

public class ConsoleScheduleOverviewPage extends BasePage implements ScheduleOverviewPage{

	@FindBy(className="schedule-status-title")
	private List<WebElement> scheduleOverviewWeeksStatus;
	
	@FindBy(css = "div.week.background-current-week-legend-calendar")
	private List<WebElement> currentWeeksOnCalendar;
	
	@FindBy(css = "div.weekday")
	private List<WebElement> overviewPageCalendarWeekdays;
	
	@FindBy(css = "div.fx-center.left-banner")
	private List<WebElement> overviewPageScheduleWeekDurations;
	
	@FindBy(css = "div.week.ng-scope")
	private List<WebElement> overviewscheduledWeeks;
	
	@FindBy(className="schedule-table-row")
	private List<WebElement> overviewTableRows;
	
	@FindBy(css="div.row-fx.schedule-table-row")
	private List<WebElement> overviewScheduleWeekList;

	@FindBy(css = "div.lgn-calendar.current-month")
	private List<WebElement> overviewCalendarMonthsYears;


	public ConsoleScheduleOverviewPage()
	{
		PageFactory.initElements(getDriver(), this);
	}
	
	@Override
	public List<String> getScheduleWeeksStatus()
	{
		List<String> overviewScheduleWeeksStatus = new ArrayList<String>();
		if(scheduleOverviewWeeksStatus.size() != 0)
		{
			for(WebElement overviewWeekStatus: scheduleOverviewWeeksStatus)
			{
				overviewScheduleWeeksStatus.add(overviewWeekStatus.getText());

			}
		}
		return overviewScheduleWeeksStatus;
	}
	
	@Override
	public Map<String, String> getWeekStartDayAndCurrentWeekDates() throws Exception
	{
		Map<String, String> calendarStartWeekAndCurrentWeekDays =  new HashMap<String, String>();
		calendarStartWeekAndCurrentWeekDays.put("weekStartDay", getOverviewCalenderWeekDays());
		if(currentWeeksOnCalendar.size() != 0) {
			String currentWeeksDaysLabel = "";
			for(WebElement weekLabel : currentWeeksOnCalendar)
			{
				currentWeeksDaysLabel = currentWeeksDaysLabel +""+weekLabel.getText();
			}
			String[] currentWeekDaysOnCalendar = currentWeeksDaysLabel.split("\n");
			String currentWeekDaysOnCalendarAsString = "";
			for(String weekDay : currentWeekDaysOnCalendar)
			{
				if(weekDay.length() == 1)
					weekDay = "0" + weekDay;
				if(currentWeekDaysOnCalendarAsString != "")
					currentWeekDaysOnCalendarAsString = currentWeekDaysOnCalendarAsString + "," + weekDay;
				else
					currentWeekDaysOnCalendarAsString = weekDay;
			}
			calendarStartWeekAndCurrentWeekDays.put("weekDates", currentWeekDaysOnCalendarAsString);
		}
		return calendarStartWeekAndCurrentWeekDays;
	}
	
	/*
	 *  Check the 1st week is highlighted and DateAndDay are correct
	 */
	
	@Override
	public Boolean isCurrentWeekHighLighted() throws Exception
	{
		int isoYear = SimpleUtils.getCurrentISOYear();
		int dayOfYearForToday = SimpleUtils.getCurrentDateDayOfYear();
		String weekTypeDate = "currentWeekDate";
	    String currentDayMonthDate = SimpleUtils.getDayMonthDateFormatForCurrentPastAndFutureWeek(
	    		dayOfYearForToday, isoYear).get(weekTypeDate);
		String[] currentWeekDates = getWeekStartDayAndCurrentWeekDates().get("weekDates").split(",");
		Boolean isCurrentdateMatchedWithOverviewCalendarWeekDates = false;
		
		// check current date matched with highlighted week dates or not?
		for(String weekDate : currentWeekDates)
		{
			if(currentDayMonthDate.split(" ")[2].contains(weekDate))
			{
				isCurrentdateMatchedWithOverviewCalendarWeekDates = true;
			}
		}
		if(! isCurrentdateMatchedWithOverviewCalendarWeekDates) {
			SimpleUtils.fail("Current Date not matched with highlighted week dates!", false);
			return false;
		}
		else {
			SimpleUtils.pass("Current Date matched with highlighted week dates!");
		}
		
		// Check Overview page Calendar week start & end date matches or not with Overview Week Duration
		String calendarCurrentWeekStartDate = currentWeekDates[0];
		String scheduleCurrentWeekStartDate = getOverviewPageWeeksDuration().get(0).get("durationWeekStartDay").split(" ")[1];
		String calendarCurrentWeekEndDate = currentWeekDates[currentWeekDates.length - 1];
		String scheduleCurrentWeekEndDate = getOverviewPageWeeksDuration().get(0).get("durationWeekEndDay").split(" ")[1];
		
		if(isScheduleDurationStartDayMatchesWithCalendarWeekStartDay(calendarCurrentWeekStartDate, scheduleCurrentWeekStartDate))
		{
			SimpleUtils.pass("Current Week start date:'"+ calendarCurrentWeekStartDate +"' on Overview calendar and Overview current schedule duration start date:'" + scheduleCurrentWeekStartDate + "' matched!");
			if(isScheduleDurationEndDayMatchesWithCalendarWeekEndDay(calendarCurrentWeekEndDate, scheduleCurrentWeekEndDate))
			{
				SimpleUtils.pass("Current Week end date:'"+ calendarCurrentWeekEndDate +"' on Overview calendar and Overview current schedule duration end Date:'" + scheduleCurrentWeekEndDate + "' matched!");
				return true;
			}
			else {
				SimpleUtils.fail("Current Week end date:'"+ calendarCurrentWeekEndDate +"' on Overview calendar and Overview current schedule week duration end Date:'" + scheduleCurrentWeekEndDate + "' not matched!", true);
			}
		}
		else {
			SimpleUtils.fail("Current Week start date::'"+ calendarCurrentWeekStartDate +"' on Overview calendar and Overview current schedule week duration start date:'" + scheduleCurrentWeekStartDate + "' not matched!", true);	
		}
		
		return false;
	}
	
	@Override
	public List<HashMap<String, String>> getOverviewPageWeeksDuration()
	{
		List<HashMap<String, String>> overviewWeeksDuration = new ArrayList<HashMap<String, String>>();
		if(overviewPageScheduleWeekDurations.size() != 0) {
			for(WebElement overviewPageScheduleWeekDuration : overviewPageScheduleWeekDurations) {
				HashMap<String, String> scheduleEachWeekDuration = new HashMap<String, String>();
				String[] overviewWeekDuration = overviewPageScheduleWeekDuration.getText().replace("\n", "").split("\\—", 2);
				scheduleEachWeekDuration.put("durationWeekStartDay", overviewWeekDuration[0].trim());
				scheduleEachWeekDuration.put("durationWeekEndDay", overviewWeekDuration[1].trim());
				overviewWeeksDuration.add(scheduleEachWeekDuration);
			}
		}
		return overviewWeeksDuration;
	}
	
	//Check each week until weeks are 'Not Available' DateAndDay are correct on overview page
	@Override
	public Boolean verifyDateAndDayForEachWeekUntilNotAvailable() throws Exception
	{
		int index = 0;
		int weekMatched = 0;
		String scheduleWeekStatusToVerify = "Not Available";
		List<String> overviewPageScheduledWeekStatus = getScheduleWeeksStatus();
		List<String> currentAndUpcomingActiveWeeksDaysOnCalendar = getCurrentAndUpcomingActiveWeeksDaysOnCalendar();
		for(String scheduleWeekStatus : overviewPageScheduledWeekStatus)
		{ 
			String[] calenderWeekDates = currentAndUpcomingActiveWeeksDaysOnCalendar.get(index).split(",");
			String calendarWeekStartDate = calenderWeekDates[0];
			String scheduleWeekStartDate = getOverviewPageWeeksDuration().get(index).get("durationWeekStartDay").split(" ")[1];
			String calendarWeekEndDate = calenderWeekDates[calenderWeekDates.length - 1];
			String scheduleWeekEndDate = getOverviewPageWeeksDuration().get(index).get("durationWeekEndDay").split(" ")[1];
			
			if(scheduleWeekStatus.contains(scheduleWeekStatusToVerify)) {
				SimpleUtils.pass("Overview Page: Week Status found as 'Not Available' for Week Duration-'" 
						+ getOverviewPageWeeksDuration().get(index).get("durationWeekStartDay") +" - "	
							+ getOverviewPageWeeksDuration().get(index).get("durationWeekEndDay") +"'");
				break;
			}
			
			if(isScheduleDurationStartDayMatchesWithCalendarWeekStartDay(calendarWeekStartDate, scheduleWeekStartDate))
			{
				SimpleUtils.pass("Current Week start date:'"+ calendarWeekStartDate +"' on Overview calendar and Overview current schedule duration start date:'" + scheduleWeekStartDate + "' matched!");
				if(isScheduleDurationEndDayMatchesWithCalendarWeekEndDay(calendarWeekEndDate, scheduleWeekEndDate)) {
					SimpleUtils.pass("Current Week end date:'"+ calendarWeekEndDate +"' on Overview calendar and Overview current schedule duration end Date:'" + scheduleWeekEndDate + "' matched!");
					weekMatched = weekMatched + 1;
				}
				else {
					SimpleUtils.fail("Current Week end date:'"+ calendarWeekEndDate +"' on Overview calendar and Overview current schedule week duration end Date:'" + scheduleWeekEndDate + "' not matched!", true);
				}
			}
			else {
				SimpleUtils.fail("Current Week start date::'"+ calendarWeekStartDate +"' on Overview calendar and Overview current schedule week duration start date:'" + scheduleWeekStartDate + "' not matched!", true);	
			}
			
			index = index + 1;
		}
		if(index != 0)
			return true;
		else
			return false;
	}
	
	//Click on each week to open schedule page and ensure the DayAndDate on schedule page matches the DayAndDate on overview page
	public Boolean verifyDayAndDateOnSchedulePageMatchesDayAndDateOnOverviewPage() throws Exception
	{
		return false;
	}
	
	@Override
	public List<String> getCurrentAndUpcomingActiveWeeksDaysOnCalendar() throws Exception
	{
		List<String> calendarWeeksOnOverviewPage = new ArrayList<String>();
		Boolean isCurrentWeekStart = false;
		int daysInWeek = 7;
		String currentWeekDaysOnCalendarAsString = "";
		if(overviewscheduledWeeks.size() != 0) {
			for(WebElement overviewscheduledWeek : overviewscheduledWeeks) {
				if(overviewscheduledWeek.getAttribute("class").toString().contains("background-current-week-legend-calendar"))
					isCurrentWeekStart = true;
				if(isCurrentWeekStart)
				{
					String[] weekDaysOnCalendar = overviewscheduledWeek.getText().split("\n");
					for(String weekDay : weekDaysOnCalendar)
					{
						weekDay = weekDay.toString().trim();
						if(weekDay.length() != 0)
						{
							if(weekDay.length() == 1)
								weekDay = "0" + weekDay;
							if(currentWeekDaysOnCalendarAsString != "")
								currentWeekDaysOnCalendarAsString = currentWeekDaysOnCalendarAsString + "," + weekDay;
							else
								currentWeekDaysOnCalendarAsString = weekDay;
						}
					}
					if(currentWeekDaysOnCalendarAsString.split(",").length == daysInWeek)
					{
						calendarWeeksOnOverviewPage.add(currentWeekDaysOnCalendarAsString);
						currentWeekDaysOnCalendarAsString = "";
					}
				}
			}
		}
		return calendarWeeksOnOverviewPage;
	}
	
	
	public Boolean isScheduleDurationStartDayMatchesWithCalendarWeekStartDay(String calendarWeekStartDate, String scheduleWeekStartDate)
	{
		if(calendarWeekStartDate.equals(scheduleWeekStartDate)) {
			return true;
		}
		return false;
	}
	
	public Boolean isScheduleDurationEndDayMatchesWithCalendarWeekEndDay(String calendarWeekEndDate, String scheduleWeekEndDate)
	{
		if(calendarWeekEndDate.equals(scheduleWeekEndDate)) {
				return true;
			}
		return false;
	}
	
	@Override
	public void clickOnCurrentWeekToOpenSchedule() throws Exception
	{
		int currentWeekIndex = 0;
		if(currentWeeksOnCalendar.size() != 0)
		{
			click(currentWeeksOnCalendar.get(currentWeekIndex));
			if(overviewTableRows.size() != 0)
			{
				click(overviewTableRows.get(currentWeekIndex));
			}
			else {
				SimpleUtils.fail("Overview page Schedule table not loaded successfully!", false);
			}
		}
		else {
			SimpleUtils.fail("Current Week Not loaded on Overview calendar!", false);
		}
	}
	
	public String getOverviewCalenderWeekDays() throws Exception 
	{
		String weekDays = "";
		if(overviewPageCalendarWeekdays.size() != 0) {
			for(int index = 0; index < 7; index++)
			{
				if(weekDays != "")
					weekDays = weekDays + "," + overviewPageCalendarWeekdays.get(index).getText();
				else
					weekDays = overviewPageCalendarWeekdays.get(index).getText();
			}
		}
		else {
			SimpleUtils.fail("Overview Page: Calendar week days not found!",true);
		}
		return weekDays;
	}

	@Override
	public List<WebElement> getOverviewScheduleWeeks() {
		return overviewScheduleWeekList;
	}

	public void clickScheduleDraftAndGuidanceStatus(List<String> overviewScheduleWeeksStatus){

		for(int i=0;i<overviewScheduleWeeksStatus.size();i++){
			if(overviewScheduleWeeksStatus.get(i).contains("Finalized") ||
					overviewScheduleWeeksStatus.get(i).contains("Published") ||
					overviewScheduleWeeksStatus.get(i).contains("Draft")&&
					overviewScheduleWeeksStatus.get(i+1).contains("Guidance")){
				System.out.println("pass ho gaya");
			}

		}
	}


	@Override
	public ArrayList<String> getOverviewCalendarMonthsYears() throws Exception
	{
		ArrayList<String> overviewCalendarMonthsYearsText = new ArrayList<String>();
		if(overviewCalendarMonthsYears.size() != 0)
		{
			for(WebElement overviewCalendarMonthYear : overviewCalendarMonthsYears)
			{
				overviewCalendarMonthsYearsText.add(overviewCalendarMonthYear.getText().replace("\n", ""));
			}
		}
		return overviewCalendarMonthsYearsText;
	}

	/*@FindBy(css = "")
	private List<WebElement> weekHoursElement;*/
	@Override
	public LinkedHashMap<String, Float> getWeekHoursByWeekElement(WebElement overViewWeek) {
		LinkedHashMap<String, Float> weekHours = new LinkedHashMap<String, Float>();
		List<WebElement> weekHoursElement = overViewWeek.findElements(By.cssSelector("span.text-hours"));
		if(weekHoursElement.size() == 3)
		{
			float guidanceHours = Float.valueOf(weekHoursElement.get(0).getText().split(" ")[0]);
			float scheduledHours = Float.valueOf(weekHoursElement.get(1).getText().split(" ")[0]);
			float otherHours = Float.valueOf(weekHoursElement.get(2).getText().split(" ")[0]);
			weekHours.put("guidanceHours", guidanceHours);
			weekHours.put("scheduledHours", scheduledHours);
			weekHours.put("otherHours", otherHours);

		}
		return weekHours;
	}
	
	@FindBy(css = "div.week")
	private List<WebElement> calendarWeeks;
	
	@Override
	public int getScheduleOverviewWeeksCountCanBeCreatInAdvance()
	{
		boolean isPastWeek = true;
		float scheduleWeekCountToBeCreated = 0;
		for(WebElement week : calendarWeeks)
		{
			float currentWeekCount = 1;
			if(week.getAttribute("class").contains("current-week"))
				isPastWeek = false;
			int weekDayCount = week.getText().split("\n").length;
			if(weekDayCount < 7)
				currentWeekCount = (float) 0.5;
			boolean isCurrentWeekLocked = false;
			if(week.getAttribute("class").contains("week-locked"))
				isCurrentWeekLocked = true;
			
			if(!isPastWeek && !isCurrentWeekLocked)
			{
				scheduleWeekCountToBeCreated = (scheduleWeekCountToBeCreated + currentWeekCount);
			}
				
		}
		return (int) (scheduleWeekCountToBeCreated - 1);
	}
}
