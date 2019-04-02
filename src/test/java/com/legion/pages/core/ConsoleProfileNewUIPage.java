package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.ProfileNewUIPage;
import com.legion.utils.SimpleUtils;

public class ConsoleProfileNewUIPage extends BasePage implements ProfileNewUIPage{
	
	public ConsoleProfileNewUIPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	final static String consoleProfileMenuItemText = "Profile";

	@FindBy(className="console-navigation-item")
	private List<WebElement>consoleNavigationMenuItems;
	
	@FindBy(css = "div.profile")
	private WebElement profilePageSection;
	
	@FindBy(css="div.collapsible-title-text")
	private List<WebElement> profilePageSubSections;
	
	@FindBy(css="a[ng-click=\"newTimeOff()\"]")
	private WebElement newTimeOffBtn;
	
	@Override
	public void clickOnProfileConsoleMenu() throws Exception {
		if(consoleNavigationMenuItems.size() != 0)
		{
			WebElement consoleScheduleMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleProfileMenuItemText);
			click(consoleScheduleMenuElement);
			SimpleUtils.pass("'Profile' Console Menu Loaded Successfully!");
		}
		else {
			SimpleUtils.fail("'Profile' Console Menu Items Not Loaded Successfully!",false);
		}
	}
	
	@Override
	public boolean isProfilePageLoaded() throws Exception
	{
		if(isElementLoaded(profilePageSection)) {
			SimpleUtils.pass("Profile Page loaded successfully.");
			return true;
		}
		return false;
	}
	
	@Override
	public void selectProfilePageSubSectionByLabel(String profilePageSubSectionLabel) throws Exception {
		boolean isSubSectionSelected = false;
		if(isElementLoaded(profilePageSubSections.get(0))) {
			for(WebElement profilePageSubSection : profilePageSubSections) {
				if(profilePageSubSection.getText().toLowerCase().contains(profilePageSubSectionLabel.toLowerCase())) {
					click(profilePageSubSection);
					isSubSectionSelected = true;
					break;
				}
			}
			if(isSubSectionSelected)
				SimpleUtils.pass("Controls Page: '"+profilePageSubSectionLabel+"' sub section selected Successfully.");
			else
				SimpleUtils.fail("Controls Page: '"+profilePageSubSectionLabel+"' sub section not loaded.",false);
		}
		else
			SimpleUtils.fail("Profile Page: Sub Section not loaded.", false);
	}

	@Override
	public void clickOnCreateTimeOffBtn() throws Exception {
		if(isElementLoaded(newTimeOffBtn)) {
			click(newTimeOffBtn);
			SimpleUtils.pass("Controls Page: 'Create Time Off' button Clicked.");
		}
		else
			SimpleUtils.fail("Controls Page: 'Create Time Off' button not loaded.", false);
	}
	
	@FindBy(css = "div.reasons-reason ")
	private List<WebElement> timeOffReasons;
	
	@Override
	public void selectTimeOffReason(String reasonLabel) throws Exception
	{
		boolean isTimeOffReasonSelected = false;
		if(timeOffReasons.size() > 0) {
			for(WebElement timeOffReason : timeOffReasons) {
				if(timeOffReason.getText().toLowerCase().contains(reasonLabel.toLowerCase())) {
					click(timeOffReason);
					isTimeOffReasonSelected = true;
				}
			}
			if(isTimeOffReasonSelected)
				SimpleUtils.pass("Controls Page: Time Off Reason '"+ reasonLabel +"' selected successfully.");
			else
				SimpleUtils.fail("Controls Page: Time Off Reason '"+ reasonLabel +"' not found.", false);
		}
		else
			SimpleUtils.fail("Controls Page: 'Time Off Reasons' not loaded.", false);
	}
	
	@FindBy(css = "textarea[placeholder=\"Optional explanation\"]")
	private WebElement timeOffExplanationtextArea;
	
	@Override
	public void updateTimeOffExplanation(String explanationText) throws Exception
	{
		if(isElementLoaded(timeOffExplanationtextArea)) {
			timeOffExplanationtextArea.sendKeys(explanationText);
			SimpleUtils.pass("Controls Page: Time Off Optional explanation updated successfully.");
		}
		else
			SimpleUtils.fail("Controls Page: 'Optional explanation' text Area not loaded.", false);
	}
	
	@FindBy(css= "div.real-day")
	private List<WebElement> calendarDates;
	
	@FindBy(css="a.calendar-nav-left")
	private WebElement timeOffCalendarLeftArrow;
	
	@FindBy(css="a.calendar-nav-right")
	private WebElement timeOffCalendarRightArrow;
	
	@FindBy(css="ranged-calendar[range-start=\"rangeStart\"]")
	private List<WebElement> timeOffCalendarMonths;
	
	@FindBy(css="button[ng-click=\"apply($event)\"]")
	private WebElement timeOffApplyBtn;
	@Override
	public void selectTimeOffDuration(String startDate, String endDate) throws Exception
	{		
		String disabledCalendarDayClass = "can-not-select";
		String timeOffStartDate = startDate.split(",")[0].split(" ")[1];
		String timeOffStartMonth = startDate.split(",")[0].split(" ")[0];
		int timeOffStartYear = Integer.valueOf(startDate.split(",")[1].trim());
		
		String timeOffEndDate = endDate.split(",")[0].split(" ")[1];
		String timeOffEndMonth = endDate.split(",")[0].split(" ")[0];
		int timeOffEndYear = Integer.valueOf(endDate.split(",")[1].trim());
		
		WebElement changeMonthArrow = timeOffCalendarRightArrow;
		WebElement timeOffMonthAndYear = timeOffCalendarMonths.get(0).findElement(By.cssSelector("div.ranged-calendar__month-name"));
		// Selecting Start date
		while(timeOffStartYear != Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1])
				|| ! timeOffMonthAndYear.getText().toLowerCase().contains(timeOffStartMonth.toLowerCase()))
		{
			if(timeOffMonthAndYear.getText().toLowerCase().contains(timeOffStartMonth.toLowerCase())) {
				if(timeOffStartYear > Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1]))
					changeMonthArrow = timeOffCalendarRightArrow;
				else
					changeMonthArrow = timeOffCalendarLeftArrow;
			}
			click(changeMonthArrow);
		}	
		if(calendarDates.size() > 0) {
			for(WebElement calendarDate : calendarDates) {
				if(Integer.valueOf(calendarDate.getText()) == Integer.valueOf(timeOffStartDate)) {
					if(calendarDate.getAttribute("class").contains(disabledCalendarDayClass)) {
						SimpleUtils.fail("Profile Page: Time Off requests must be made at least 15 days in advance, given Start Date '"
								+startDate+"'.", false);
					}
					else {
						click(calendarDate);
						SimpleUtils.pass("Profile Page: New Time Off Request Start Date '" +startDate+" selected successfully'.");
						break;
					}
				}
			}
		}
		else
			SimpleUtils.fail("Controls Page: 'Calendar' not loaded.", false);
		
		
		// Selecting End date
		while(timeOffEndYear != Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1])
				|| ! timeOffMonthAndYear.getText().toLowerCase().contains(timeOffEndMonth.toLowerCase()))
		{
			if(timeOffMonthAndYear.getText().toLowerCase().contains(timeOffEndMonth.toLowerCase())) {
				if(timeOffEndYear > Integer.valueOf(timeOffMonthAndYear.getText().split(" ")[1]))
					changeMonthArrow = timeOffCalendarRightArrow;
				else
					changeMonthArrow = timeOffCalendarLeftArrow;
			}
			click(changeMonthArrow);
		}	
		if(calendarDates.size() > 0) {
			for(WebElement calendarDate : calendarDates) {
				if(Integer.valueOf(calendarDate.getText()) == Integer.valueOf(timeOffEndDate)) {
					if(calendarDate.getAttribute("class").contains(disabledCalendarDayClass)) {
						SimpleUtils.fail("Profile Page: Time Off requests must be made at least 15 days in advance, given End Date '"
								+endDate+"'.", false);
					}
					else {
						click(calendarDate);
						SimpleUtils.pass("Profile Page: New Time Off Request End Date '" +endDate+" selected successfully'.");
						break;
					}
				}
			}
		}
		else
			SimpleUtils.fail("Controls Page: 'Calendar' not loaded.", false);
	}

	@Override
	public void clickOnSaveTimeOffRequestBtn() throws Exception
	{
		if(timeOffApplyBtn.isEnabled()) {
			click(timeOffApplyBtn);
		}
		else
			SimpleUtils.fail("Profile Page: Unable to save New Time Off Request.", false);
			
	}
	@FindBy(css="div.timeoff-requests-request.row-fx")
	private List<WebElement> timeOffRequestRows;
	
	@Override
	public void createNewTimeOffRequest(String timeOffReasonLabel, String timeOffExplanationText,
			String timeOffStartDate, String timeOffEndDate) throws Exception {
		int timeOffRequestCount = timeOffRequestRows.size();
		clickOnCreateTimeOffBtn();
		Thread.sleep(1000);
		selectTimeOffReason(timeOffReasonLabel);
        updateTimeOffExplanation(timeOffExplanationText);
        selectTimeOffDuration(timeOffStartDate, timeOffEndDate);
        clickOnSaveTimeOffRequestBtn();
        if(timeOffRequestRows.size() > timeOffRequestCount) 
        	SimpleUtils.pass("Profile Page: New Time Off Save Successfully.");
        else
        	SimpleUtils.fail("Profile Page: New Time Off not Save Successfully.", false);
	}

	@Override
	public String getTimeOffRequestStatus(String timeOffReasonLabel, String timeOffExplanationText,
			String timeOffStartDuration, String timeOffEndDuration) throws Exception {
		String timeOffStartDate = timeOffStartDuration.split(",")[0].split(" ")[1];
		String timeOffStartMonth = timeOffStartDuration.split(",")[0].split(" ")[0];
		String timeOffEndDate = timeOffEndDuration.split(",")[0].split(" ")[1];
		String timeOffEndMonth = timeOffEndDuration.split(",")[0].split(" ")[0];
		
		String requestStatusText = "";
		int timeOffRequestCount = timeOffRequestRows.size();
		if(timeOffRequestCount > 0) {
			for(WebElement timeOffRequest : timeOffRequestRows) {
				WebElement requestType = timeOffRequest.findElement(By.cssSelector("span.request-type"));
				if(requestType.getText().toLowerCase().contains(timeOffReasonLabel.toLowerCase())) {
					WebElement requestDate = timeOffRequest.findElement(By.cssSelector("div.request-date"));
					String[] requestDateText = requestDate.getText().replace("\n", "").split("-");
					if(requestDateText[0].toLowerCase().contains(timeOffStartMonth.toLowerCase()) 
							&& requestDateText[0].toLowerCase().contains(timeOffStartDate.toLowerCase())
							&& requestDateText[1].toLowerCase().contains(timeOffEndMonth.toLowerCase()) 
							&& requestDateText[1].toLowerCase().contains(timeOffEndDate.toLowerCase())) {
						WebElement requestStatus = timeOffRequest.findElement(By.cssSelector("span.request-status"));
						requestStatusText = requestStatus.getText();
					}
				}
			}
		}
		else
			SimpleUtils.fail("Profile Page: No Time off request found.", false);
		
		return requestStatusText;
	}
}
