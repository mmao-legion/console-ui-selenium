package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;
import com.legion.utils.SimpleUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleSchedulePage extends BasePage implements SchedulePage {

	public ConsoleSchedulePage()
	{
		PageFactory.initElements(getDriver(), this);
	}
	
	@FindBy(xpath="//*[@id='legion-app']/div/div[2]/div/div/div/div[1]/navigation/div/div[4]")
	private WebElement goToScheduleButton;
	
	@FindBy(css="div[helper-text*='Work in progress Schedule'] span.legend-label")
	private WebElement draft;
	
	@FindBy(css="div[helper-text-position='top'] span.legend-label")
	private WebElement published;
	
	@FindBy(css="div[helper-text*='final per schedule changes'] span.legend-label")
	private WebElement finalized;
	
	@FindBy(className="overview-view")
	private WebElement overviewSectionElement;
	
	@FindBy(css="div.sub-navigation-view-link.active")
	private WebElement activatedSubTabElement;
	
	@FindBy(xpath="//div[contains(@class,'sub-navigation-view')]//span[contains(text(),'Schedule')]")
	private WebElement goToScheduleTab;
	
	@FindBy(css="div[ng-click*='analyze'] span.sch-control-button-label")
	private WebElement analyze;
	
	@FindBy(css="div[ng-click*='edit'] span.sch-control-button-label")
	private WebElement edit;
	
	@FindBy(xpath="//span[contains(text(),'Projected Sales')]")
	private WebElement goToProjectedSalesTab;

	@FindBy(css="ui-view[name='forecastControlPanel'] span.highlight-when-help-mode-is-on")
	private WebElement salesGuidance;
	
	@FindBy(css="div[ng-click*='refresh'] span.sch-control-button-label")
	private WebElement refresh;
	
	@FindBy(xpath="//div[contains(text(),'Guidance')]")
	private WebElement guidance;
	
	@FindBy(xpath="//span[contains(text(),'Staffing Guidance')]")
	private WebElement goToStaffingGuidanceTab;
	
	@FindBy(css="[ng-click=\"selectDayWeekView($event, 'week')\"]")
	private WebElement scheduleWeekViewButton;
	
	@FindBy(css="[ng-click=\"selectDayWeekView($event, 'day')\"]")
	private WebElement scheduleDayViewButton;
	
	@FindBy(className="mt-18")
	private List<WebElement> budgetedScheduledLabelsDivElement;
	
	@FindBy(className="sch-calendar-day-dimension")
	private List<WebElement> ScheduleCalendarDayLabels;
	
	@FindBy(css="div.sub-navigation-view-link")
	private List<WebElement> ScheduleSubTabsElement;

	@FindBy(css="[ng-click='gotoNextWeek($event)']")
	private WebElement calendarNavigationNextWeekArrow;
	
	@FindBy(css="[ng-click=\"gotoPreviousWeek($event)\"]")
	private WebElement salesForecastCalendarNavigationPreviousWeekArrow;
	
	@FindBy(css="[ng-click=\"regenerateFromOverview()\"]")
	private WebElement generateSheduleButton;
	
	@FindBy(css="[ng-click=\"controlPanel.fns.publishConfirmation($event, false)\"]")
	private WebElement publishSheduleButton;
	
	@FindBy(css="div.sch-view-dropdown-summary-content-item-heading.ng-binding")
	private WebElement analyzePopupLatestVersionLabel;
	
	@FindBy(css="[ng-click=\"controlPanel.fns.analyzeAction($event)\"]")
	private WebElement scheduleAnalyzeButton;

	@FindBy(className="sch-schedule-analyze-content")
	private WebElement scheduleAnalyzePopup;
	
	@FindBy(className="sch-schedule-analyze-dismiss")
	private WebElement scheduleAnalyzePopupCloseButton;
	
	@FindBy(css="[ng-click=\"goToSchedule()\"]")
	private WebElement checkOutTheScheduleButton;

	@FindBy(className="console-navigation-item")
	private List<WebElement>consoleNavigationMenuItems;

	@FindBy(css="[ng-click=\"callOkCallback()\"]")
	private WebElement editAnywayPopupButton;

	@FindBy(css="[ng-if=\"canShowNewShiftButton()\"]")
	private WebElement addNewShiftOnDayViewButton;

	@FindBy(className="sch-control-button-cancel")
	private WebElement scheduleEditModeCancelButton;

	@FindBy(css="[ng-click=\"regenerateFromOverview()\"]")
	private WebElement scheduleGenerateButton;

	@FindBy (css = "#legion-app navigation div:nth-child(4)")
	private WebElement analyticsConsoleName;

	@FindBy(className = "holiday-text")
	private WebElement noPublishedSchedule;


	String consoleScheduleMenuItemText = "Schedule";

	public void clickOnScheduleConsoleMenuItem() {
		if(consoleNavigationMenuItems.size() != 0)
		{
			WebElement consoleScheduleMenuElement = SimpleUtils.getSubTabElement(consoleNavigationMenuItems, consoleScheduleMenuItemText);
			activeConsoleName = analyticsConsoleName.getText();
			click(consoleScheduleMenuElement);
			SimpleUtils.pass("Console Menu Loaded Successfully!");
		}
		else {
			SimpleUtils.fail("Console Menu Items Not Loaded Successfully!",false);
		}
	}

	@Override
	public void goToSchedulePage() throws Exception {

		checkElementVisibility(goToScheduleButton);
		activeConsoleName = analyticsConsoleName.getText();
		click(goToScheduleButton);
        SimpleUtils.pass("Schedule Page Loading..!");
        
        if(isElementLoaded(draft)){
        	SimpleUtils.pass("Draft is Displayed on the page");
        }else{
        	SimpleUtils.fail("Draft not displayed on the page",true);
        }
        
        if(isElementLoaded(published)){
        	SimpleUtils.pass("Published is Displayed on the page");
        }else{
        	SimpleUtils.fail("Published not displayed on the page",true);
        }
        
        if(isElementLoaded(finalized)){
        	SimpleUtils.pass("Finalized is Displayed on the page");
        }else{
        	SimpleUtils.fail("Finalized not displayed on the page",true);
        }
    }
	

	@Override
	 public boolean isSchedulePage() throws Exception {
        if(isElementLoaded(overviewSectionElement)){
        	return true;
        }else{
        	return false;
        }   
    }


	
	@Override
	 public Boolean varifyActivatedSubTab(String SubTabText) throws Exception
	 {
		 if(isElementLoaded(activatedSubTabElement))
		 {
			 if(activatedSubTabElement.getText().contains(SubTabText))
			 {
				 return true;
			 }
		 }
		 else {
			 SimpleUtils.fail("Schedule Page not loaded successfully", true);
		 }
		 return false;
	 }
	
	@Override
    public void goToSchedule() throws Exception {

    	checkElementVisibility(goToScheduleTab);
    	activeConsoleName = analyticsConsoleName.getText();
        click(goToScheduleTab);
        SimpleUtils.pass("Schedule Page Loading..!");
        
        if(isElementLoaded(analyze)){
        	SimpleUtils.pass("Analyze is Displayed on Schdule page");
        }else{
        	SimpleUtils.fail("Analyze not Displayed on Schedule page",true);
        }
        if(isElementLoaded(edit)){
        	SimpleUtils.pass("Edit is Displayed on Schedule page");
        }else{
        	SimpleUtils.fail("Edit not Displayed on Schedule page",true);
        }
    }
	
	
	@Override
	 public void goToProjectedSales() throws Exception {
    	checkElementVisibility(goToProjectedSalesTab);
        click(goToProjectedSalesTab);
        SimpleUtils.pass("ProjectedSales Page Loading..!");

        if(isElementLoaded(salesGuidance)){
        	SimpleUtils.pass("SalesGuidance is Displayed on the page");
        }else{
        	SimpleUtils.fail("SalesGuidance not Displayed on the page",true);
        }
        
        if(isElementLoaded(refresh)){
        	SimpleUtils.pass("Refresh is Displayed on the page");
        }else{
        	SimpleUtils.fail("Refresh not Displayed on the page",true);
        }
       
    }
	

	@Override
	public void goToStaffingGuidance() throws Exception {
    	checkElementVisibility(goToStaffingGuidanceTab);
        click(goToStaffingGuidanceTab);
        SimpleUtils.pass("StaffingGuidance Page Loading..!");

        if(isElementLoaded(guidance)){
        	SimpleUtils.pass("Guidance is Displayed on Staffing Guidance page");
        }else{
        	SimpleUtils.fail("Guidance not Displayed on Staffing Guidance page",true);
        }
        
        if(isElementLoaded(analyze)){
        	SimpleUtils.pass("Analyze is Displayed on Staffing Guidance page");
        }else{
        	SimpleUtils.fail("Analyze not Displayed on Staffing Guidance page",true);
        }
    }

	@Override
	 public boolean isSchedule() throws Exception {
        if(isElementLoaded(goToScheduleTab)){
        	return true;
        }else{
        	return false;
        }
        
    }


	
	@Override
	public void clickOnWeekView() throws Exception
	{
		if(isElementLoaded(scheduleWeekViewButton))
		{
			if(! scheduleWeekViewButton.getAttribute("class").toString().contains("enabled"))
			{
				click(scheduleWeekViewButton);
			}
			SimpleUtils.pass("Schedule page week view loaded successfully!");
		}
		else
		{
			SimpleUtils.fail("Schedule Page Week View Button not Loaded Successfully!", true);
		}
	}


	@Override
	public void clickOnDayView() throws Exception {
		if(isElementLoaded(scheduleDayViewButton)) {
			if(! scheduleDayViewButton.getAttribute("class").toString().contains("enabled")) {
				click(scheduleDayViewButton);
			}
			SimpleUtils.pass("Schedule Page day view loaded successfully!");
		}
		else {
			SimpleUtils.fail("Schedule Page Day View Button not Loaded Successfully!", true);
		}
	}

	
	@Override
	public HashMap<String, Float> getScheduleLabelHoursAndWagges() throws Exception {
		
		String budgetedHours = "";
		String scheduledHours = "";
		String otherHours = "";
		String wagesBudgetedCount = "";
		String wagesScheduledCount = "";
		HashMap<String, Float> scheduleHoursAndWages = new HashMap<String, Float>();
		if(isElementLoaded(budgetedScheduledLabelsDivElement.get(0)))
		{
			for(WebElement budgetedScheduledLabelDiv : budgetedScheduledLabelsDivElement)
			{

					if(budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Guidance")
							|| budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Budgeted") )
					{
						wagesBudgetedCount = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Wages", "").replace("$", "");
						scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesBudgetedCount, "wagesBudgetedCount");
					}
					else if(budgetedScheduledLabelDiv.getText().contains("Wages") && budgetedScheduledLabelDiv.getText().contains("Scheduled") )
					{
						wagesScheduledCount = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Wages", "").replace("$", "");
						scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesScheduledCount, "wagesScheduledCount");

					}
					else if(budgetedScheduledLabelDiv.getText().contains("Budgeted") || budgetedScheduledLabelDiv.getText().contains("Guidance"))
					{
						budgetedHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Hrs", "");
						scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , budgetedHours, "budgetedHours");
					}
					else if(budgetedScheduledLabelDiv.getText().contains("Scheduled"))
					{
						scheduledHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Hrs", "");
						scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , scheduledHours, "scheduledHours");
					}
					else if(budgetedScheduledLabelDiv.getText().contains("Other"))
					{
						otherHours = budgetedScheduledLabelDiv.findElement(By.className("sch-control-kpi")).getText().replace(" Hrs", "");
						scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , otherHours, "otherHours");
					}
			}
		}
		return scheduleHoursAndWages;
 	}



	private HashMap<String, Float> updateScheduleHoursAndWages(HashMap<String, Float> scheduleHoursAndWages,
			String hours, String hoursAndWagesKey) {
		scheduleHoursAndWages.put(hoursAndWagesKey, Float.valueOf(hours));
		return scheduleHoursAndWages;
	}

	@Override
	public List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception {
		List<HashMap<String, Float>> ScheduleLabelHoursAndWagesDataForDays = new ArrayList<HashMap<String, Float>>();
		if(isScheduleDayViewActive()) {
			if(ScheduleCalendarDayLabels.size() != 0) {
				for(WebElement ScheduleCalendarDayLabel: ScheduleCalendarDayLabels)
				{
					click(ScheduleCalendarDayLabel);
					ScheduleLabelHoursAndWagesDataForDays.add(getScheduleLabelHoursAndWagges());
				}
			}
			else {
				SimpleUtils.fail("Schedule Page Day View Calender not Loaded Successfully!", true);
			}
		}
		else {
			SimpleUtils.fail("Schedule Page Day View Button not Active!", true);
		}
		return ScheduleLabelHoursAndWagesDataForDays;
	}


	@Override
	public void clickOnScheduleSubTab(String subTabString) throws Exception
	{
		if(ScheduleSubTabsElement.size() != 0 && ! varifyActivatedSubTab(subTabString))
		{
			for(WebElement ScheduleSubTabElement : ScheduleSubTabsElement)
			{
				if(ScheduleSubTabElement.getText().contains(subTabString))
				{
					click(ScheduleSubTabElement);
				}
			}
			
		}

		if(varifyActivatedSubTab(subTabString))
		{
			SimpleUtils.pass("Schedule Page: '" + subTabString +"' tab loaded Successfully!");
		}
		else
		{
			SimpleUtils.fail("Schedule Page: '" + subTabString +"' tab not loaded Successfully!", true);
		}
	}

	@Override
	public void navigateWeekViewToPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount)
	{
		String currentWeekStartingDay = "NA";
		for(int i = 0; i < weekCount; i++)
		{
			if(ScheduleCalendarDayLabels.size() != 0)
			{
				currentWeekStartingDay = ScheduleCalendarDayLabels.get(0).getText();
			}

			if(nextWeekViewOrPreviousWeekView.toLowerCase().contains("next") || nextWeekViewOrPreviousWeekView.toLowerCase().contains("future"))
			{
				try {
					if(isElementLoaded(calendarNavigationNextWeekArrow)){
							calendarNavigationNextWeekArrow.click();
							SimpleUtils.pass("Schedule Page Calender view for next week loaded successfully!");
					}
				}
				catch (Exception e) {
					SimpleUtils.fail("Schedule page Calender Next Week Arrows Not Loaded/Clickable after '"+currentWeekStartingDay+ "'", true);
				}
			}
			else
			{
				try {
					if(isElementLoaded(salesForecastCalendarNavigationPreviousWeekArrow)){
						salesForecastCalendarNavigationPreviousWeekArrow.click();
						SimpleUtils.pass("Schedule Page Calender view for Previous week loaded successfully!");
					}
				} catch (Exception e) {
					SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable after '"+currentWeekStartingDay+ "'", true);
				}

			}
			/*if(! currentWeekStartingDay.equals(ScheduleCalendarDayLabels.get(0).getText()))
			{
				SimpleUtils.fail("Week After '"+currentWeekStartingDay+"' not Clickable!", true);
			}
			else {
				SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded!", true);
			}*/
		}
	}


	
	@Override
	 public Boolean isWeekGenerated() throws Exception
	 {
		 if(isElementLoaded(generateSheduleButton))
		 {
			 if(generateSheduleButton.isEnabled())
			 {
				 return false;
			 }
		 }
		 return true;
	 }

	
	@Override
	public Boolean isWeekPublished() throws Exception
	{
		 if(isElementLoaded(publishSheduleButton))
		 {
			 if(publishSheduleButton.isEnabled())
			 {
				 return false;
			 }
			 else {
				 clickOnScheduleAnalyzeButton();
				 if(isElementLoaded(analyzePopupLatestVersionLabel))
				 {
					 String latestVersion = analyzePopupLatestVersionLabel.getText();
					 latestVersion = latestVersion.split(" ")[1].split(".")[0];
					 closeStaffingGuidanceAnalyzePopup();
					 if(Integer.valueOf(latestVersion) < 1)
					 {
						 return false;
					 }
				 }
			 }
		 }
		 return true;
	 }
	
	

	@Override
	public void generateSchedule() throws Exception
	{
		 if(isElementLoaded(generateSheduleButton))
		 {
			 click(generateSheduleButton);
			 Thread.sleep(5000);
			 if(isElementLoaded(checkOutTheScheduleButton))
			 {
				 click(checkOutTheScheduleButton);
				 SimpleUtils.pass("Schedule Generated Successfuly!");
			 }
		 }
		 else {
			 SimpleUtils.assertOnFail("Schedule Already generated for active week!", false, true);
		 }
	}
	
	
	public Boolean isScheduleWeekViewActive() {
		if(scheduleWeekViewButton.getAttribute("class").toString().contains("enabled")) {
			return true;
		}
		return false;
	}
	
	
	public Boolean isScheduleDayViewActive() { 
		if(scheduleDayViewButton.getAttribute("class").toString().contains("enabled")) {
			return true;
		}
		return false;
	}

	public void clickOnScheduleAnalyzeButton() throws Exception
	{
		if(isElementLoaded(scheduleAnalyzeButton))
		{
			click(scheduleAnalyzeButton);
		}
		else {
			SimpleUtils.fail("Schedule Analyze Button not loaded successfully!", false);
		}
	}
	
	public void closeStaffingGuidanceAnalyzePopup() throws Exception
	{
		if(isStaffingGuidanceAnalyzePopupAppear())
		{
			click(scheduleAnalyzePopupCloseButton);
		}
	}
	
	public Boolean isStaffingGuidanceAnalyzePopupAppear() throws Exception
	{
		if(isElementLoaded(scheduleAnalyzePopup))
		{
			return true;
		}
		return false;
	}

	public String getScheduleWeekStartDayMonthDate()
	{
		String scheduleWeekStartDuration = "NA";
		if(ScheduleCalendarDayLabels.size() != 0)
		{
			scheduleWeekStartDuration = ScheduleCalendarDayLabels.get(0).getText().replace("\n", "");
		}
		return scheduleWeekStartDuration;
	}

	public void clickOnEditButton() throws Exception
	{
		if(isElementLoaded(edit))
		{
			click(edit);
			if(isElementLoaded(editAnywayPopupButton))
			{
				click(editAnywayPopupButton);
				SimpleUtils.pass("Schedule edit shift page loaded successfully!");
			}
		}
	}

	public Boolean isAddNewDayViewShiftButtonLoaded() throws Exception
	{
		if(isElementLoaded(addNewShiftOnDayViewButton))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	public void clickOnCancelButtonOnEditMode() throws Exception
	{
		if(isElementLoaded(scheduleEditModeCancelButton))
		{
			click(scheduleEditModeCancelButton);
			SimpleUtils.pass("Schedule edit shift page cancelled successfully!");
		}
	}

	public Boolean isGenerateButtonLoaded() throws Exception
	{
		if(isElementLoaded(scheduleGenerateButton))
		{
			return true;
		}
		return false;
	}

	public String getActiveWeekDayMonthAndDateForEachDay() throws Exception
	{
		String activeWeekTimeDuration = "";
		if(ScheduleCalendarDayLabels.size() != 0)
		{
			for(WebElement ScheduleCalendarDayLabel : ScheduleCalendarDayLabels)
			{
				if(activeWeekTimeDuration != "")
					activeWeekTimeDuration = activeWeekTimeDuration +","+ ScheduleCalendarDayLabel.getText().replace("\n", " ");
				else
					activeWeekTimeDuration = ScheduleCalendarDayLabel.getText().replace("\n", " ");
			}
		}
		return activeWeekTimeDuration;
	}

	public Boolean validateScheduleActiveWeekWithOverviewCalendarWeek(String overviewCalendarWeekDate, String overviewCalendarWeekDays, String scheduleActiveWeekDuration)
	{
		String[] overviewCalendarDates = overviewCalendarWeekDate.split(",");
		String[] overviewCalendarDays = overviewCalendarWeekDays.split(",");
		String[]  scheduleActiveDays = scheduleActiveWeekDuration.split(",");
		int index;
		if(overviewCalendarDates.length == overviewCalendarDays.length && overviewCalendarDays.length == scheduleActiveDays.length)
		{
			for(index = 0; index < overviewCalendarDates.length; index++)
			{
				// Verify Days on Schedule Active week with Overview Calendar week
				if(scheduleActiveDays[index].startsWith(overviewCalendarDays[index]))
				{
					// Verify Days on Schedule Active week with Overview Calendar week
					if(scheduleActiveDays[index].contains(overviewCalendarDays[index]))
					{
						SimpleUtils.pass("Schedule week dayAndDate matched with Overview calendar DayAndDate for '"+scheduleActiveDays[index]+"'");
					}
				}
			}
			if(index != 0 )
				return true;

		}
		return false;
	}

	public boolean isCurrentScheduleWeekPublished()
	{
		String scheduleStatus = "No Published Schedule";
		try {
			if(isElementLoaded(noPublishedSchedule)) {
				if(noPublishedSchedule.getText().contains(scheduleStatus))
					return false;
			}
		} catch (Exception e) {
			SimpleUtils.pass("Schedule is Published for current Week!");
			return true;
		}
		return true;
	}
}
