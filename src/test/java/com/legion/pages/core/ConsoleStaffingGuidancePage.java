package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.utils.SimpleUtils;

public class ConsoleStaffingGuidancePage extends BasePage implements StaffingGuidancePage{

	@FindBy(className="sub-navigation-view")
	private List<WebElement> scheduleSubNavigationViewTab;
	
	String staffingGuidanceTabLabelText = "STAFFING GUIDANCE";
	String elementEnabledClassName = "enabled";
	
	@FindBy(css = "div.sub-navigation-view-link.active")
	private WebElement schedulePageSelectedSubTab;
	
	@FindBy(css="[ng-click=\"selectDayWeekView($event, 'week')\"]")
	private WebElement staffingGuidancePageWeekViewButton;
	
	@FindBy(css="[ng-click=\"selectDayWeekView($event, 'day')\"]")
	private WebElement projectedSalesPageDayViewButton;
	
	@FindBy(className="schedule-view")
	private WebElement staffingGuidanceDetailViewTable;
	
	@FindBy(css="div.dc-summary-box.first-row div[class='dc-summary-content'] div")
	private List<WebElement> staffingGuidanceDayViewTimeDurationLabels;
	
	@FindBy(css = "div.dc-summary-box.dc-summary-slot-fill div[class='dc-summary-content'] div")
	private List<WebElement> staffingGuidanceDayViewItemsCountLabels;
	
	@FindBy (xpath="//div[@class='dc-summary-box']//div[contains(@helper-text,'Projected')]/parent::div/div[@class='dc-summary-content']/div")
	private List<WebElement> staffingGuidanceDayViewTeamMemberCountLabels;

	@FindBy(className = "sch-calendar-day-dimension")
	private List<WebElement> staffingGuidanceWeekViewDayDateMonthLabels;
	
	@FindBy(className = "sch-week-view-day-summary")
	private List<WebElement> staffingGuidanceWeekViewDaysHours;
	
	public ConsoleStaffingGuidancePage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	public void navigateToStaffingGuidanceTab() throws Exception
	{
		WebElement staffingGuidanceElement = SimpleUtils.getSubTabElement(scheduleSubNavigationViewTab, staffingGuidanceTabLabelText);
		if(staffingGuidanceElement != null)
		{
			staffingGuidanceElement.click();
		}
	}
	
	public Boolean isStaffingGuidanceTabActive() throws Exception
	{
		if(isElementLoaded(schedulePageSelectedSubTab) && schedulePageSelectedSubTab.getText().contains(staffingGuidanceTabLabelText))
		{
			return true;
		}
		return false;
	}
	
	public void navigateToStaffingGuidanceTabWeekView() throws Exception
	{
		if(isStaffingGuidanceTabActive())
		{
			if(isElementLoaded(staffingGuidancePageWeekViewButton))
			{
				staffingGuidancePageWeekViewButton.click();
			}
			else {
				SimpleUtils.fail("Staffing Guidance Week View button not Loaded.", false);
			}
		}
		else {
			SimpleUtils.fail("Staffing Guidance Tab not dispalyed.", false);
		}
	}
	
	public Boolean isStaffingGuidanceForecastTabWeekViewActive() throws Exception {
		if(isElementLoaded(staffingGuidancePageWeekViewButton))
		{
			if(staffingGuidancePageWeekViewButton.getAttribute("class").toString().contains(elementEnabledClassName))
			{
				return true;
			}
		}
		return false;
	}
	
	public void navigateToStaffingGuidanceTabDayView() throws Exception
	{
		if(isStaffingGuidanceTabActive())
		{
			if(isElementLoaded(projectedSalesPageDayViewButton))
			{
				projectedSalesPageDayViewButton.click();
			}
			else {
				SimpleUtils.fail("Staffing Guidance Week View button not Loaded.", false);
			}
		}
		else {
			SimpleUtils.fail("Staffing Guidance Tab not dispalyed.", false);
		}
	}
	
	public Boolean isStaffingGuidanceForecastTabDayViewActive() throws Exception {
		if(isElementLoaded(projectedSalesPageDayViewButton))
		{
			if(projectedSalesPageDayViewButton.getAttribute("class").toString().contains(elementEnabledClassName))
			{
				return true;
			}
		}
		return false;
	}
	
	public List<String> getStaffingGuidanceForecastDayViewTimeDuration() throws Exception
	{
		ArrayList<String> staffingGuidanceTimeDuration = new ArrayList<String>();
		if(isStaffingGuidanceForecastTabDayViewActive())
		{
			if(staffingGuidanceDayViewTimeDurationLabels.size() != 0)
			{
				for(WebElement staffingGuidanceDayViewTimeDurationLabel : staffingGuidanceDayViewTimeDurationLabels)
				{
					staffingGuidanceTimeDuration.add(staffingGuidanceDayViewTimeDurationLabel.getText());
				}
			}
		}
		else
		{
			SimpleUtils.fail("Staffing Guidance Day View not Loaded Successfully!", true);
		}
		return staffingGuidanceTimeDuration;
	}
			
	public List<Integer> getStaffingGuidanceForecastDayViewItemsCount() throws Exception
	{
		ArrayList<Integer> staffingGuidanceItemsCount = new ArrayList<Integer>();
		if(isStaffingGuidanceForecastTabDayViewActive())
		{
			if(staffingGuidanceDayViewItemsCountLabels.size() != 0)
			{
				for(WebElement staffingGuidanceDayViewItemsCountLabel : staffingGuidanceDayViewItemsCountLabels)
				{
					staffingGuidanceItemsCount.add(Integer.valueOf(staffingGuidanceDayViewItemsCountLabel.getText()));
				}
			}
		}
		else
		{
			SimpleUtils.fail("Staffing Guidance Day View not Loaded Successfully!", true);
		}
		return staffingGuidanceItemsCount;
	}
	
	public List<Integer> getStaffingGuidanceForecastDayViewTeamMembersCount() throws Exception
	{
		ArrayList<Integer> staffingGuidanceTeamMembersCount = new ArrayList<Integer>();
		if(isStaffingGuidanceForecastTabDayViewActive())
		{
			
			if(staffingGuidanceDayViewTeamMemberCountLabels.size() != 0)
			{
				for(WebElement staffingGuidanceDayViewTeamMemberCountLabel : staffingGuidanceDayViewTeamMemberCountLabels)
				{
					staffingGuidanceTeamMembersCount.add(Integer.valueOf(staffingGuidanceDayViewTeamMemberCountLabel.getText()));
				}
			}
		}
		else
		{
			SimpleUtils.fail("Staffing Guidance Day View not Loaded Successfully!", true);
		}
		return staffingGuidanceTeamMembersCount;
	}
	
	public List<String> getStaffingGuidanceDayDateMonthLabelsForWeekView() throws Exception{
		ArrayList<String> staffingGuidanceTeamMembersCount = new ArrayList<String>();
		if(isStaffingGuidanceForecastTabWeekViewActive())
		{
			for(WebElement staffingGuidanceWeekViewDayDateMonthLabel : staffingGuidanceWeekViewDayDateMonthLabels)
			{
				staffingGuidanceTeamMembersCount.add(staffingGuidanceWeekViewDayDateMonthLabel.getText());
			}
		}
		else {
			SimpleUtils.fail("Staffing Guidance Week View not Loaded Successfully!", true);
		}
		return staffingGuidanceTeamMembersCount;
	}
	
	public List<Float> getStaffingGuidanceHoursCountForWeekView() throws Exception{
		ArrayList<Float> staffingGuidanceTeamMembersCount = new ArrayList<Float>();
		if(isStaffingGuidanceForecastTabWeekViewActive())
		{
			for(WebElement staffingGuidanceWeekViewDaysHour : staffingGuidanceWeekViewDaysHours)
			{
				staffingGuidanceTeamMembersCount.add(Float.valueOf(staffingGuidanceWeekViewDaysHour.getText().replace(" HRs", "")));
			}
		}
		else {
			SimpleUtils.fail("Staffing Guidance Week View not Loaded Successfully!", true);
		}
		return staffingGuidanceTeamMembersCount;
	}
	
}
