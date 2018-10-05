package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private WebElement currentWeekOnCalendar;
	
	@FindBy(css = "div.weekday")
	private List<WebElement> overviewPageCalendarWeekdays;
	
	@FindBy(css = "div.fx-center.left-banner")
	private List<WebElement> overviewPageScheduleWeekDurations;
	
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
		if(overviewPageCalendarWeekdays.size() != 0) {
			calendarStartWeekAndCurrentWeekDays.put("weekStartDay", overviewPageCalendarWeekdays.get(0).getText());
		}
		else {
			SimpleUtils.fail("Overview Page: Calendar week days not found!",true);
		}
		
		if(isElementLoaded(currentWeekOnCalendar)) {
			calendarStartWeekAndCurrentWeekDays.put("weekDates", currentWeekOnCalendar.getText().replace("\n", ","));
		}
		return calendarStartWeekAndCurrentWeekDays;
	}
	
	/*
	 *  Check the 1st week is highlighted and DateAndDay are correct
	 */
	
	
	public Boolean isCurrentWeekHighLighted()
	{
		return null;
	}
	
	//public Boolean 
}
