package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getCurrentTestMethodName;
import static com.legion.utils.MyThreadLocal.getDriver;
import com.legion.utils.MyThreadLocal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;
import com.legion.utils.SimpleUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConsoleScheduleNewUIPage extends BasePage implements SchedulePage {

	public enum scheduleHoursAndWagesData {
        scheduledHours("scheduledHours"),
        budgetedHours("budgetedHours"),
        otherHours("otherHours"),
        budgetedWages("budgetedWages"),
        scheduledWages("scheduledWages"),
        otherWages("otherWages"),
        wages("Wages"),
        hours("hours");
        private final String value;

        scheduleHoursAndWagesData(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }
	
	public ConsoleScheduleNewUIPage()
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
	
	@FindBy(css="lg-button[label=\"Analyze\"]")
	private WebElement analyze;
	
	@FindBy(css="lg-button[label=\"Edit\"]")
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

//	@FindBy(className="sch-calendar-day-dimension")
//	private List<WebElement> ScheduleCalendarDayLabels;
	
	@FindBy(css="div.sub-navigation-view-link")
	private List<WebElement> ScheduleSubTabsElement;

	@FindBy(className="day-week-picker-arrow-right")
	private WebElement calendarNavigationNextWeekArrow;
	
	@FindBy(className="day-week-picker-arrow-left")
	private WebElement calendarNavigationPreviousWeekArrow;
	
	@FindBy(css="[ng-click=\"regenerateFromOverview()\"]")
	private WebElement generateSheduleButton;
	
	@FindBy(css="lg-button[label=\"Publish\"]")
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

	@FindBy(css="lg-button[label=\"Cancel\"]")
	private WebElement scheduleEditModeCancelButton;

	@FindBy(css="[ng-click=\"regenerateFromOverview()\"]")
	private WebElement scheduleGenerateButton;

	@FindBy (css = "#legion-app navigation div:nth-child(4)")
	private WebElement analyticsConsoleName;

	final static String consoleScheduleMenuItemText = "Schedule";
	
	@FindBy(css = "ng-form.input-form.ng-pristine.ng-valid-minlength")
	private WebElement filterButton;
	
	@FindBy(css="[ng-repeat=\"(key, opts) in $ctrl.displayFilters\"]")
    private List<WebElement> scheduleFilterElements;
	
	@FindBy(css="div.lg-filter__wrapper")
	private WebElement filterPopup;
	
	@FindBy(css = "div.sch-calendar-day-dimension")
	private List<WebElement> weekViewDaysAndDates;
	
	@FindBy(css = "div.sch-week-view-day-summary")
	private List<WebElement> weekDaySummeryHoursAndTeamMembers;
	
	@FindBy(css = "div.sch-shift-transpose-data-container")
	private List<WebElement> shiftsOnScheduleView;
	
    
    @FindBy(css = "div.sch-day-view-grid-header.fill")
    private List<WebElement> dayViewShiftsTimeDuration;
    
    @FindBy(css = "div.sch-day-view-grid-header.tm-count.guidance")
    private List<WebElement> dayViewbudgetedTMCount;
    
    @FindBy(xpath = "//div[contains(@class,'sch-day-view-grid-header tm-count') and not(contains(@class,'guidance'))]")
    private List<WebElement> dayViewScheduleTMsCount;
    
    @FindBy(css = "select.ng-valid-required")
    private WebElement scheduleGroupByButton;
    

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
			 if(activatedSubTabElement.getText().equalsIgnoreCase(SubTabText))
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
        	SimpleUtils.pass("Sales Forecast is Displayed on the page");
        }else{
        	SimpleUtils.fail("Sales Forecast not Displayed on the page",true);
        }
        
        verifyRefreshBtnOnSalesForecast();
    }
	
	public void verifyRefreshBtnOnSalesForecast() throws Exception{
		 if(getCurrentTestMethodName().contains("InternalAdmin")){
        	if(isElementLoaded(refresh)){
            	SimpleUtils.pass("Refresh button is Displayed on Sales Forecast for Legion Internal Admin");
            }else{
            	SimpleUtils.fail("Refresh button not Displayed on Sales Forecast for Legion Internal Admin",true);
            }
        }else{
        	if(!isElementLoaded(refresh)){
            	SimpleUtils.pass("Refresh button should not be Displayed on Sales Forecast for other than Legion Internal Admin");
            }else{
            	SimpleUtils.fail("Refresh button Displayed on Sales Forecast for other than Internal Admin",true);
            }
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
		/*WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'week')\"]"));*/
	
		WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("div.lg-button-group-last"));
		if(isElementLoaded(scheduleWeekViewButton))
		{
			if(! scheduleWeekViewButton.getAttribute("class").toString().contains("selected"))//selected
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
		/*WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'day')\"]"));*/
		WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
				findElement(By.cssSelector("div.lg-button-group-first"));
		
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
	public HashMap<String, Float> getScheduleLabelHoursAndWages() throws Exception {
		HashMap<String, Float> scheduleHoursAndWages = new HashMap<String, Float>();
		WebElement budgetedScheduledLabelsDivElement = MyThreadLocal.getDriver().findElement(By.cssSelector("div.card-carousel-card.card-carousel-card-primary"));
		Thread.sleep(1000);
		if(isElementLoaded(budgetedScheduledLabelsDivElement))
		{
			String scheduleWagesAndHoursCardText = budgetedScheduledLabelsDivElement.getText();
			String[] scheduleWagesAndHours = scheduleWagesAndHoursCardText.split("\n");
			for(String wagesAndHours: scheduleWagesAndHours)
			{
				if(wagesAndHours.toLowerCase().contains(scheduleHoursAndWagesData.hours.getValue().toLowerCase()))
				{
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[1], 
							scheduleHoursAndWagesData.budgetedHours.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[2], 
							scheduleHoursAndWagesData.scheduledHours.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[3], 
							scheduleHoursAndWagesData.otherHours.getValue());
				}
				else if(wagesAndHours.toLowerCase().contains(scheduleHoursAndWagesData.wages.getValue().toLowerCase()))
				{
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[1]
							.replace("$", ""), scheduleHoursAndWagesData.budgetedWages.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[2]
							.replace("$", ""), scheduleHoursAndWagesData.scheduledWages.getValue());
					scheduleHoursAndWages = updateScheduleHoursAndWages(scheduleHoursAndWages , wagesAndHours.split(" ")[3]
							.replace("$", ""), scheduleHoursAndWagesData.otherWages.getValue());
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
	public synchronized List<HashMap<String, Float>> getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek() throws Exception {
		List<HashMap<String, Float>> ScheduleLabelHoursAndWagesDataForDays = new ArrayList<HashMap<String, Float>>();
		List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
		if(isScheduleDayViewActive()) {
			if(ScheduleCalendarDayLabels.size() != 0) {
				for(WebElement ScheduleCalendarDayLabel: ScheduleCalendarDayLabels)
				{
					click(ScheduleCalendarDayLabel);
					ScheduleLabelHoursAndWagesDataForDays.add(getScheduleLabelHoursAndWages());
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
				if(ScheduleSubTabElement.getText().equalsIgnoreCase(subTabString))
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
		List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
		for(int i = 0; i < weekCount; i++)
		{
			int displayedWeekCount = ScheduleCalendarDayLabels.size();
			for(WebElement ScheduleCalendarDayLabel: ScheduleCalendarDayLabels)
			{
				if(ScheduleCalendarDayLabel.getAttribute("class").toString().contains("day-week-picker-period-active"))
				{	
					if(nextWeekViewOrPreviousWeekView.toLowerCase().contains("next") || nextWeekViewOrPreviousWeekView.toLowerCase().contains("future"))
					{
						try {
								int activeWeekIndex = ScheduleCalendarDayLabels.indexOf(ScheduleCalendarDayLabel);
								if(activeWeekIndex < (displayedWeekCount - 1))
								{
									click(ScheduleCalendarDayLabels.get(activeWeekIndex + 1));
								}
								else {
									click(calendarNavigationNextWeekArrow);
									click(ScheduleCalendarDayLabels.get(0));
								}
						}
						catch (Exception e) {
							SimpleUtils.report("Schedule page Calender Next Week Arrows Not Loaded/Clickable after '"+ScheduleCalendarDayLabel.getText().replace("\n", "")+ "'");
						}
					}
					else
					{
						try {
							int activeWeekIndex = ScheduleCalendarDayLabels.indexOf(ScheduleCalendarDayLabel);
							if(activeWeekIndex > 0)
							{
								click(ScheduleCalendarDayLabels.get(activeWeekIndex - 1));
							}
							else {
								click(calendarNavigationPreviousWeekArrow);
								click(ScheduleCalendarDayLabels.get(displayedWeekCount - 1));
							}
						} catch (Exception e) {
							SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable after '"+ScheduleCalendarDayLabel.getText().replace("\n", "")+ "'", true);
						}
					}
					break;
				}
			}
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
		 SimpleUtils.pass("Week: '"+getActiveWeekText()+"' Already Generated!");
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
		 else if(isConsoleMessageError())
		 {
			return false;
		 }
		 SimpleUtils.pass("Week: '"+getActiveWeekText()+"' Already Published!");
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
		WebElement scheduleWeekViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'week')\"]"));
		if(scheduleWeekViewButton.getAttribute("class").toString().contains("enabled")) {
			return true;
		}
		return false;
	}
	
	
	public Boolean isScheduleDayViewActive() {
		WebElement scheduleDayViewButton = MyThreadLocal.getDriver().
			findElement(By.cssSelector("[ng-click=\"selectDayWeekView($event, 'day')\"]"));
		if(scheduleDayViewButton.getAttribute("class").toString().contains("enabled")) {
			return true;
		}
		return false;
	}

	public void clickOnScheduleAnalyzeButton() throws Exception
	{
		if(isElementLoaded(analyze))
		{
			click(analyze);
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
		WebElement scheduleCalendarActiveWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active"));
		try {
			if(isElementLoaded(scheduleCalendarActiveWeek))
			{
				scheduleWeekStartDuration = scheduleCalendarActiveWeek.getText().replace("\n", "");
			}
		} catch (Exception e) {
			SimpleUtils.fail("Calender duration bar not loaded successfully", true);
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
		List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("sch-calendar-day-dimension"));
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
		//todo yt 2018.10.28 this looks like a hack
		WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active")); 
		String scheduleStatus = "No Published Schedule";
		try {
			List<WebElement> noPublishedSchedules = MyThreadLocal.getDriver().findElements(By.className("holiday-text"));
			if(noPublishedSchedules.size() != 0) {
				if(noPublishedSchedules.get(0).getText().contains(scheduleStatus))
					return false;
			}
			else if(isConsoleMessageError())
			{
				return false;
			}
			else if(isElementLoaded(publishSheduleButton))
			{
				return false;
			}
		} catch (Exception e) {
			SimpleUtils.pass("Schedule is Published for current Week! ('"+ activeWeek.getText().replace("\n", " ") +"')");
			return true;
		}
		SimpleUtils.pass("Schedule is Published for current Week! ('"+ activeWeek.getText().replace("\n", " ") +"')");
		return true;
	}
	
	public boolean isConsoleMessageError() throws Exception 
	{
		List<WebElement> carouselCards = MyThreadLocal.getDriver().findElements(By.cssSelector("div.card-carousel-card"));
		WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active")); 
		if(carouselCards.size() != 0)
		{
			for(WebElement carouselCard: carouselCards)
			{
				if((carouselCard.getText().toUpperCase().contains("CONSOLE MESSAGE") 
						|| carouselCard.getText().toUpperCase().contains("REQUIRED ACTION")) 
							&& !isElementLoaded(publishSheduleButton))
				{
					SimpleUtils.report("Week: '"+activeWeek.getText().replace("\n", " ")+"' Not Published because of Console Message Error: '"+carouselCard.getText().replace("\n", " ")+"'");
					return true;
				}
			}
		}
    	return false;
	}
	
	public String getActiveWeekText() throws Exception
	{
		WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active")); 
		if(isElementLoaded(activeWeek))
			return activeWeek.getText().replace("\n", " ");
		return "";
	}
	
    
    public HashMap<String, ArrayList<WebElement>> getAvailableFilters()
    {
    	HashMap<String, ArrayList<WebElement>> scheduleFilters = new HashMap<String,ArrayList<WebElement>>();
    	try {
			if(isElementLoaded(filterButton))
			{
				click(filterButton);
				for(WebElement scheduleFilterElement: scheduleFilterElements)
		    	{
					WebElement filterLabel = scheduleFilterElement.findElement(By.className("lg-filter__category-label"));
					String filterType = filterLabel.getText().toLowerCase().replace(" ", "");
		    		List<WebElement> filters = scheduleFilterElement.findElements(By.cssSelector("input-field[type=\"checkbox\"]"/*"[ng-repeat=\"opt in opts\"]"*/));
		    		ArrayList<WebElement> filterList = new ArrayList<WebElement>();
		    		for(WebElement filter: filters)
		    		{
		    			filterList.add(filter);
		    		}
		    		scheduleFilters.put(filterType, filterList);
		    	}
			}
			else {
				SimpleUtils.fail("Filters button not found on Schedule page!", false);
			}
		} catch (Exception e) {
			SimpleUtils.fail("Filters button not loaded successfully on Schedule page!", false);
		}
    	return scheduleFilters;
    }
    
    public void filterScheduleByWorkRoleAndShiftType(boolean isWeekView)
    {
    	String shiftTypeFilterKey = "shifttype";
    	String workRoleFilterKey = "workrole";
    	HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
    	if(availableFilters.size() > 1)
    	{
    		ArrayList<WebElement> shiftTypeFilters = availableFilters.get(shiftTypeFilterKey);
        	ArrayList<WebElement> workRoleFilters = availableFilters.get(workRoleFilterKey);
        	for(WebElement workRoleFilter: workRoleFilters)
        	{
        		if(filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
        			click(filterButton);
        		unCheckFilters(workRoleFilters);
        		click(workRoleFilter);
        		SimpleUtils.report("Data for Work Role: '"+ workRoleFilter.getText() +"'");
        		if(isWeekView)
        			filterScheduleByShiftTypeWeekView(shiftTypeFilters);
        		else
        			filterScheduleByShiftTypeDayView(shiftTypeFilters);
        	}
    	}
    	else {
    		SimpleUtils.fail("Filters are not appears on Schedule page!", false);
    	}
    }
    
    public void filterScheduleByShiftTypeWeekView(ArrayList<WebElement> shiftTypeFilters)
    {
    	for(WebElement shiftTypeFilter : shiftTypeFilters)
    	{
    		try {
	    		if(filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
	    			click(filterButton);
	    		unCheckFilters(shiftTypeFilters);
	    		SimpleUtils.report("Data for Shift Type: '"+ shiftTypeFilter.getText() +"'");
	    		click(shiftTypeFilter);
	    		click(filterButton);
    			String cardHoursAndWagesText = "";
				HashMap<String, Float> hoursAndWagesCardData = getScheduleLabelHoursAndWages();
				for(Entry<String, Float> hoursAndWages: hoursAndWagesCardData.entrySet())
				{
					if(cardHoursAndWagesText != "")
						cardHoursAndWagesText = cardHoursAndWagesText +", "+hoursAndWages.getKey()+": '"+ hoursAndWages.getValue() +"'";
					else
						cardHoursAndWagesText = hoursAndWages.getKey()+": '"+ hoursAndWages.getValue() +"'";
				}
				SimpleUtils.report("Active Week Card's Data: "+cardHoursAndWagesText);
				getHoursAndTeamMembersForEachDaysOfWeek();
				SimpleUtils.assertOnFail("Sum of Daily Schedule Hours not equal to Active Week Schedule Hours!", verifyActiveWeekDailyScheduleHoursInWeekView(), true);
				verifyActiveWeekTeamMembersCountAvailableShiftCount();
			} catch (Exception e) {
				SimpleUtils.fail("Unable to get Card data for active week!", true);
			}
    	}
    }
    
    
    public void filterScheduleByShiftTypeDayView(ArrayList<WebElement> shiftTypeFilters)
    {
    	for(WebElement shiftTypeFilter : shiftTypeFilters)
    	{
    		try {
	    		if(filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
	    			click(filterButton);
	    		unCheckFilters(shiftTypeFilters);
	    		SimpleUtils.report("Data for Shift Type: '"+ shiftTypeFilter.getText() +"'");
	    		click(shiftTypeFilter);
	    		click(filterButton);
    			String cardHoursAndWagesText = "";
				HashMap<String, Float> hoursAndWagesCardData = getScheduleLabelHoursAndWages();
				for(Entry<String, Float> hoursAndWages: hoursAndWagesCardData.entrySet())
				{
					if(cardHoursAndWagesText != "")
						cardHoursAndWagesText = cardHoursAndWagesText +", "+hoursAndWages.getKey()+": '"+ hoursAndWages.getValue() +"'";
					else
						cardHoursAndWagesText = hoursAndWages.getKey()+": '"+ hoursAndWages.getValue() +"'";
				}
				SimpleUtils.report("Active Week Card's Data: "+cardHoursAndWagesText);
				String timeDurationText = "";
				for(String timeDuration : getScheduleDayViewGridTimeDuration())
				{
					if(timeDurationText == "")
						timeDurationText = timeDuration;
					else
						timeDurationText = timeDurationText + " | " + timeDuration;
				}
				SimpleUtils.report("Schedule Day View Shift Duration: "+timeDurationText);
				
				String budgetedTeamMembersCount = "";
				for(String budgetedTeamMembers : getScheduleDayViewBudgetedTeamMembersCount())
				{
					if(budgetedTeamMembersCount == "")
						budgetedTeamMembersCount = budgetedTeamMembers;
					else
						budgetedTeamMembersCount = budgetedTeamMembersCount + " | " + budgetedTeamMembers;
				}
				SimpleUtils.report("Schedule Day View budgeted Team Members count: "+budgetedTeamMembersCount);

				String scheduleTeamMembersCount = "";
				for(String scheduleTeamMembers : getScheduleDayViewScheduleTeamMembersCount())
				{
					if(scheduleTeamMembersCount == "")
						scheduleTeamMembersCount = scheduleTeamMembers;
					else
						scheduleTeamMembersCount = scheduleTeamMembersCount + " | " + scheduleTeamMembers;
				}
				SimpleUtils.report("Schedule Day View budgeted Team Members count: "+scheduleTeamMembersCount);
			} catch (Exception e) {
				SimpleUtils.fail("Unable to get Card data for active week!", true);
			}
    	}
    }
    
    
    public ArrayList<String> getScheduleDayViewGridTimeDuration()
    {
    	ArrayList<String> gridTimeDurations = new ArrayList<String>();
    	if(dayViewShiftsTimeDuration.size() != 0)
    	{
    		for(WebElement timeDuration : dayViewShiftsTimeDuration)
    		{
    			gridTimeDurations.add(timeDuration.getText());
    		}
    	}
    	
    	return gridTimeDurations;
    }

    
    public ArrayList<String> getScheduleDayViewBudgetedTeamMembersCount()
    {
    	ArrayList<String> BudgetedTMsCount = new ArrayList<String>();
    	if(dayViewbudgetedTMCount.size() != 0)
    	{
    		for(WebElement BudgetedTMs : dayViewbudgetedTMCount)
    		{
    			BudgetedTMsCount.add(BudgetedTMs.getText());
    		}
    	}
    	
    	return BudgetedTMsCount;
    }
    
    public ArrayList<String> getScheduleDayViewScheduleTeamMembersCount()
    {
    	ArrayList<String> BudgetedTMsCount = new ArrayList<String>();
    	if(dayViewScheduleTMsCount.size() != 0)
    	{
    		for(WebElement scheduleTMs : dayViewScheduleTMsCount)
    		{
    			BudgetedTMsCount.add(scheduleTMs.getText());
    		}
    	}
    	
    	return BudgetedTMsCount;
    }
    
    
    public void unCheckFilters(ArrayList<WebElement> filterElements)
    {
    	if(filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
			click(filterButton);
    	
    	for(WebElement filterElement: filterElements)
    	{
    		WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
    		String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
    		if(elementClasses.contains("ng-not-empty"))
    			click(filterElement);

    	}
    }
    
    public void getHoursAndTeamMembersForEachDaysOfWeek()
    {
    	String weekDaysAndDatesText = "";
    	String weekDaysHoursAndTMsCount = "";
    	try {
    		if(weekViewDaysAndDates.size() != 0)
    		{
    			for(WebElement weekViewDayAndDate : weekViewDaysAndDates)
    			{
    				if(weekDaysAndDatesText != "")
    					weekDaysAndDatesText = weekDaysAndDatesText +" | "+ weekViewDayAndDate.getText();
    				else
    					weekDaysAndDatesText = weekViewDayAndDate.getText();
    			}
    			SimpleUtils.report("Active Week Days And Dates: " + weekDaysAndDatesText);
    		}
			if(weekDaySummeryHoursAndTeamMembers.size() != 0)
			{
				for(WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers)
    			{
    				if(weekDaysHoursAndTMsCount != "")
    					weekDaysHoursAndTMsCount = weekDaysHoursAndTMsCount +" | "+ weekDayHoursAndTMs.getText();
    				else
    					weekDaysHoursAndTMsCount = weekDayHoursAndTMs.getText();
    			}
    			SimpleUtils.report("Active Week Hours And TeamMembers: " + weekDaysHoursAndTMsCount);				
			}
		} catch (Exception e) {
			SimpleUtils.fail("Unable to get Hours & Team Members for active Week!", true);
		}
    }
    
    public boolean verifyActiveWeekDailyScheduleHoursInWeekView()
    {
    	Float weekDaysScheduleHours = (float) 0;
    	Float activeWeekScheduleHoursOnCard = (float) 0;
    	try {
    		activeWeekScheduleHoursOnCard = getScheduleLabelHoursAndWages().get(scheduleHoursAndWagesData.scheduledHours.getValue());
    		if(weekDaySummeryHoursAndTeamMembers.size() != 0)
    		{
    			for(WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers)
    			{
    				weekDaysScheduleHours = weekDaysScheduleHours + Float.parseFloat(weekDayHoursAndTMs.getText().split(" HRs")[0]);
    			}
    		}
        	if(weekDaysScheduleHours.equals(activeWeekScheduleHoursOnCard))
        	{
        		SimpleUtils.pass("Sum of Daily Schedule Hours equal to Week Schedule Hours! ('"+weekDaysScheduleHours+ "/"+activeWeekScheduleHoursOnCard+"')");
        		return true;
        	}
		} catch (Exception e) {
			SimpleUtils.fail("Unable to Verify Daily Schedule Hours with Week Schedule Hours!", true);
		}
    	return false;
    }
    
    public boolean verifyActiveWeekTeamMembersCountAvailableShiftCount()
    {
    	int weekDaysTMsCount = 0;
    	int weekDaysShiftsCount = 0;
    	try {
    		if(weekDaySummeryHoursAndTeamMembers.size() != 0)
    		{
    			for(WebElement weekDayHoursAndTMs : weekDaySummeryHoursAndTeamMembers)
    			{
    				String TeamMembersCount = weekDayHoursAndTMs.getText().split(" HRs")[1].replace("TMs", "").trim();
    				weekDaysTMsCount = weekDaysTMsCount + Integer.parseInt(TeamMembersCount);
    			}
    		}
    			
    		
    		if(shiftsOnScheduleView.size() != 0)
    		{
    			for(WebElement shiftOnScheduleView : shiftsOnScheduleView)
    			{
    				if(shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed())
    				{
    					weekDaysShiftsCount = weekDaysShiftsCount + 1;
    				}
    			}
    		}
    		
        	if(weekDaysTMsCount == weekDaysShiftsCount)
        	{
        		SimpleUtils.pass("Sum of Daily Team Members Count equal to Sum of Daily Shifts Count! ('"+weekDaysTMsCount+ "/"+weekDaysShiftsCount+"')");
        		return true;
        	}
        	else {
        		SimpleUtils.fail("Sum of Daily Team Members Count not equal to Sum of Daily Shifts Count! ('"+weekDaysTMsCount+ "/"+weekDaysShiftsCount+"')", true);
        	}
		} catch (Exception e) {
			SimpleUtils.fail("Unable to Verify Daily Team Members Count with Daily Shifts Count!", true);
		}
    	return false;
    }
    
    public void selectGroupByFilter(String optionVisibleText)
    {
    	Select groupBySelectElement = new Select(scheduleGroupByButton);
    	List<WebElement> scheduleGroupByButtonOptions = groupBySelectElement.getOptions();
    	groupBySelectElement.selectByIndex(1);
    	for(WebElement scheduleGroupByButtonOption: scheduleGroupByButtonOptions)
    	{
    		if(scheduleGroupByButtonOption.getText().toLowerCase().contains(optionVisibleText.toLowerCase()))
    		{
    			groupBySelectElement.selectByIndex(scheduleGroupByButtonOptions.indexOf(scheduleGroupByButtonOption));
    			SimpleUtils.report("Selected Group By Filter: '"+optionVisibleText+"'");
    		}
    	}
    }
    
    public ArrayList<WebElement> getAllAvailableShiftsInWeekView()
    {
    	ArrayList<WebElement> avalableShifts = new ArrayList<WebElement>();
    	if(shiftsOnScheduleView.size() != 0)
		{
			for(WebElement shiftOnScheduleView : shiftsOnScheduleView)
			{
				if(shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed())
				{
					avalableShifts.add(shiftOnScheduleView);
				}
			}
		}
    	return avalableShifts;
    }
    
    public ArrayList<HashMap<String, String>> getHoursAndShiftsCountForEachWorkRolesInWeekView() throws Exception
    {
    	String workRoleFilterKey = "workrole";
    	ArrayList<HashMap<String, String>> eachWorkRolesData = new ArrayList<HashMap<String, String>>();
    	HashMap<String, ArrayList<WebElement>> availableFilters = getAvailableFilters();
    	if(availableFilters.size() > 1)
    	{
        	ArrayList<WebElement> workRoleFilters = availableFilters.get(workRoleFilterKey);
        	for(WebElement workRoleFilter: workRoleFilters)
        	{        		
        		if(filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
        			click(filterButton);
        		unCheckFilters(workRoleFilters);
        		click(workRoleFilter);
        		
        		//adding workrole name
        		HashMap<String, String> workRole = new HashMap<String, String>();
        		workRole.put("workRole", workRoleFilter.getText());
        		
        		//Adding Card data (Hours & Wages)
        		for (Entry<String, Float> e : getScheduleLabelHoursAndWages().entrySet())
        			workRole.put(e.getKey(), e.getValue().toString());
        		// Adding Shifts Count
        		workRole.put("shiftsCount", ""+getAllAvailableShiftsInWeekView().size());
        		
        		eachWorkRolesData.add(workRole);
        	}
        	unCheckFilters(workRoleFilters);
        	if(! filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
    			click(filterButton);
    	}
    	else {
    		SimpleUtils.fail("Filters are not appears on Schedule page!", false);
    	}
    	
    	return eachWorkRolesData;
    }
    
    @FindBy(css = "div.version-label")
    private List<WebElement> versionHistoryLabels;
    
    @FindBy(className = "sch-schedule-analyze-dismiss-button")
    private WebElement dismissanAlyzeButton;
    
    public ArrayList<Float> getAllVesionLabels() throws Exception
    {
    	ArrayList<Float> allVersions = new ArrayList<Float>();
    	if(isElementLoaded(analyze))
    	{
    		click(analyze);
        	if(versionHistoryLabels.size() != 0)
        	{
        		for(WebElement eachVersionLabel: versionHistoryLabels)
        		{
        			String LabelsText = eachVersionLabel.getText().split("\\(Created")[0];
        			allVersions.add(Float.valueOf(LabelsText.split("Version")[1].trim()));
        		}
        	}
        	closeAnalyticPopup();
    	}
    	
    	
    	return allVersions;
    }
    
    public void closeAnalyticPopup() throws Exception
    {
    	if(isElementLoaded(dismissanAlyzeButton))
    	{
    		click(dismissanAlyzeButton);
    	}
    }

    @FindBy(className = "sch-publish-confirm-btn")
    private WebElement publishConfirmBtn;
    
    @FindBy(className = "successful-publish-message-btn-ok")
    private WebElement successfulPublishOkBtn;
    
	@Override
	public void publishActiveSchedule() throws Exception {
		if(! isCurrentScheduleWeekPublished())
		{
			if(isConsoleMessageError())
				SimpleUtils.fail("Schedule Can not be publish because of Action Require for week: '"+ getActiveWeekText() +"'", false);
			else
			{
				click(publishSheduleButton);
				if(isElementLoaded(publishConfirmBtn))
				{
					click(publishConfirmBtn);
					SimpleUtils.pass("Schedule published successfully for week: '"+ getActiveWeekText() +"'");
					if(isElementLoaded(successfulPublishOkBtn))
					{
						click(successfulPublishOkBtn);
					}
				}
			}
				
		}
	}
	
	public boolean isPublishButtonLoaded()
	{
		try {
			if(isElementLoaded(publishSheduleButton))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
}