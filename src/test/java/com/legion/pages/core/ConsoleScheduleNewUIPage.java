package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getCurrentTestMethodName;
import static com.legion.utils.MyThreadLocal.getDriver;
import static org.testng.Assert.fail;

import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.SchedulePage;
import com.legion.tests.core.ScheduleNewUITest.staffingOption;
import com.legion.utils.SimpleUtils;

import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleScheduleNewUIPage extends BasePage implements SchedulePage {

	private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
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
	
	public enum dayCount{
		Seven(7);
		private final int value;
		dayCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
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
	
	@FindBy(css="[ng-click=\"controlPanel.fns.refreshConfirmation($event)\"]")
	private WebElement refresh;
	
	@FindBy(css = "button.btn.sch-publish-confirm-btn")
	private WebElement confirmRefreshButton;

	@FindBy(css = "button.btn.successful-publish-message-btn-ok")
	private WebElement okRefreshButton;

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
	
	//added by Nishant
	
	@FindBy(css=".modal-content")
	private WebElement customizeNewShift;
	
	@FindBy(css="div.sch-day-view-grid-header span:nth-child(1)")
	private WebElement shiftStartday;
	
	@FindBy(css="div.lgn-time-slider-notch-selector-start span.lgn-time-slider-label")
	private WebElement customizeShiftStartdayLabel;
	
	@FindBy(css="div.lgn-time-slider-notch-selector-end span.lgn-time-slider-label")
	private WebElement customizeShiftEnddayLabel;
	
	@FindBy(css="div.lgn-time-slider-notch-selector-start")
	private WebElement sliderNotchStart;
	
	@FindBy(css="div.lgn-time-slider-notch-selector-end")
	private WebElement sliderNotchEnd;
	
	@FindBy(css="div.lgn-time-slider-notch.droppable")
	private List<WebElement> sliderDroppableCount;
	
	@FindBy(css="div.lgn-dropdown.open ul li")
	private List<WebElement> listWorkRoles;
	
	@FindBy(css="button.lgn-dropdown-button:nth-child(1)")
	private WebElement btnWorkRole;
	
	@FindBy(xpath="//div[contains(text(),'Open Shift: Auto')]")
	private WebElement textOpenShift;
	
	@FindBy(xpath="//div[contains(text(),'Open Shift: Auto')]/parent::div/parent::div/div/div[@class='tma-staffing-option-outer-circle']")
	private WebElement radioBtnOpenShift;
	
	@FindBy(css=".tma-staffing-option-text-title")
	private List<WebElement> radioBtnShiftTexts;
	
	@FindBy(css=".tma-staffing-option-radio-button")
	private List<WebElement> radioBtnStaffingOptions;
	
	@FindBy(xpath="//div[contains(@class,'sch-day-view-right-gutter-text')]//parent::div//parent::div/parent::div[contains(@class,'sch-shift-container')]")
	private List<WebElement> shiftContainer;

	@FindBy(css="button.tma-action")
	private WebElement btnSave;
	
	@FindBy(css="div.sch-day-view-shift-delete")
	private List<WebElement> shiftDeleteBtn;

	@FindBy(xpath="//div[contains(text(),'Delete')]")
	private List<WebElement> shiftDeleteGutterText;

	@FindBy(css="div.sch-day-view-right-gutter-text")
	private List<WebElement> gutterText;
	
	@FindBy(css="div.sch-day-view-right-gutter")
	private List<WebElement> gutterCount;

	@FindBy(css="div.sch-day-view-grid-header.fill span")
	private List<WebElement> gridHeaderDayHour;
	
	@FindBy(xpath="//div[contains(@class,'sch-day-view-grid-header tm-count ng-scope')]")
	private List<WebElement> gridHeaderTeamCount;
	
	@FindBy(xpath="//span[contains(text(),'Save')]")
	private WebElement scheduleSaveBtn;
	
	@FindBy(xpath="//div[contains(@ng-if,'PostSave')]")
	private WebElement popUpPostSave;
	
	@FindBy(css="button.btn.sch-ok-single-btn")
	private WebElement btnOK;
	
	@FindBy(xpath="//div[contains(@ng-if,'PreSave')]")
	private WebElement popUpPreSave;
	
	@FindBy(css="button.btn.sch-save-confirm-btn")
	private WebElement scheduleVersionSaveBtn;

	@FindBy(css=".tma-search-field-input-text")
	private WebElement textSearch;

	@FindBy(css=".sch-search")
	private WebElement searchIcon;

	@FindBy(css=".table-row")
	private List<WebElement> tableRowCount;

	@FindBy(xpath="//div[@class='worker-edit-availability-status']//span[contains(text(),'Available')]")
	private List<WebElement> availableStatus;

	@FindBy(xpath="//div[@class='worker-edit-availability-status']")
	private List<WebElement> scheduleStatus;

	@FindBy(xpath="//span[contains(text(),'Best')]")
	private List<WebElement> scheduleBestMatchStatus;

	@FindBy(css="td.table-field.action-field.tr>div")
	private List<WebElement> radionBtnSelectTeamMembers;

	@FindBy(css="button.tma-action.sch-save")
	private WebElement btnOffer;



	//added by Naval

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

    @FindBy(css = "div.tab.ng-scope")
    private List<WebElement> selectTeamMembersOption ;

    @FindBy(xpath = "//span[contains(text(),'TMs')]")
    private WebElement selectRecommendedOption ;


	@FindBy(css="button.btn-success")
	private WebElement upgradeAndGenerateScheduleBtn;

    @FindBy(css = "div.version-label")
    private List<WebElement> versionHistoryLabels;

    @FindBy(className = "sch-schedule-analyze-dismiss-button")
    private WebElement dismissanAlyzeButton;

    @FindBy(className = "sch-publish-confirm-btn")
    private WebElement publishConfirmBtn;

    @FindBy(className = "successful-publish-message-btn-ok")
    private WebElement successfulPublishOkBtn;

    @FindBy(css = "div.holiday-logo-container")
    private WebElement holidayLogoContainer;

    @FindBy(css = "tr[ng-repeat=\"day in summary.workingHours\"]")
	private List<WebElement> guidanceWeekOperatingHours;

    @FindBy(className = "sch-group-header")
	private List<WebElement> scheduleShiftHeaders;

	@FindBy(css = "div.card-carousel-card.card-carousel-card-smart-card-required")
	private WebElement requiredActionCard;

	@FindBy(className= "sch-day-view-shift-outer")
	private List<WebElement> dayViewAvailableShifts;

	@FindBy(css = "div.card-carousel-card")
	private List<WebElement> carouselCards;
	
	@FindBy(css = "div.sch-calendar-day-dimension.sch-calendar-day")
	private List<WebElement> ScheduleWeekCalendarDates;
	
	@FindBy(css = "div.card-carousel")
	private WebElement smartCardPanel;

	final static String consoleScheduleMenuItemText = "Schedule";


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
		String currentWeekStartingDay = "NA";
		List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
		for(int i = 0; i < weekCount; i++)
		{
			if(ScheduleCalendarDayLabels.size() != 0)
			{
				currentWeekStartingDay = ScheduleCalendarDayLabels.get(0).getText();
			}

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
			 else if(isElementLoaded(upgradeAndGenerateScheduleBtn))
			 {
				 click(upgradeAndGenerateScheduleBtn);
				 Thread.sleep(5000);
				 if(isElementLoaded(checkOutTheScheduleButton))
				 {
					 click(checkOutTheScheduleButton);
					 SimpleUtils.pass("Schedule Generated Successfuly!");
				 }
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
			SimpleUtils.pass("User is not allowed to add new shift for past week!");
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
		String scheduleStatus = "No Published Schedule";
		try {
			WebElement noPublishedSchedule = MyThreadLocal.getDriver().findElement(By.className("holiday-text"));
			if(isElementLoaded(noPublishedSchedule)) {
				if(noPublishedSchedule.getText().contains(scheduleStatus))
					return false;
			}
			else if(isConsoleMessageError())
			{
				return false;
			}
		} catch (Exception e) {
			SimpleUtils.pass("Schedule is Published for current Week!");
			return true;
		}
		SimpleUtils.pass("Schedule is Published for current Week!");
		return true;
	}
	
	public boolean isConsoleMessageError() throws Exception 
	{
		List<WebElement> carouselCards = MyThreadLocal.getDriver().findElements(By.cssSelector("div.card-carousel-card.card-carousel-card-default"));
		WebElement activeWeek = MyThreadLocal.getDriver().findElement(By.className("day-week-picker-period-active")); 
		if(carouselCards.size() != 0)
		{
			for(WebElement carouselCard: carouselCards)
			{
				if(carouselCard.getText().toUpperCase().contains("CONSOLE MESSAGE"))
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

	@Override
	public void validatingRefreshButtononPublishedSchedule() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isGenerateScheduleButton() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validatingScheduleRefreshButton() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickOnSchedulePublishButton() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//added by Nishant
	
	public void navigateDayViewToPast(String PreviousWeekView, int dayCount)
	{
		String currentWeekStartingDay = "NA";
		List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
		for(int i = 0; i < dayCount; i++)
		{
		
			try {
				System.out.println(ScheduleCalendarDayLabels.get(i).getText());
				click(ScheduleCalendarDayLabels.get(i));
				clickOnEditButton();
				clickOnCancelButtonOnEditMode();
//						click(ScheduleCalendarDayLabel);
				} catch (Exception e) {
					SimpleUtils.fail("Schedule page Calender Previous Week Arrows Not Loaded/Clickable", true);
				}
				
			
		}
	}
	
	
	//added by Nishant
	
	
	public String clickNewDayViewShiftButtonLoaded() throws Exception
	{
		String textStartDay = null;
		if(isElementLoaded(addNewShiftOnDayViewButton))
		{
			SimpleUtils.pass("User is allowed to add new shift for past week!");
			if(isElementLoaded(shiftStartday)){
				String[] txtStartDay = shiftStartday.getText().split(" ");
				textStartDay = txtStartDay[0];
				System.out.println(txtStartDay);
			}else{
				SimpleUtils.fail("Shift Start day not getting Loaded!",true);
			}
			click(addNewShiftOnDayViewButton);
		}
		else
		{
			SimpleUtils.fail("User is allowed to add new shift for past week!",true);
		}
		return textStartDay;
	}
	
	public void customizeNewShiftPage() throws Exception
	{
		if(isElementLoaded(customizeNewShift))
		{
			SimpleUtils.pass("Customize New Shift Page loaded Successfully!");
		}
		else
		{
			SimpleUtils.fail("Customize New Shift Page not loaded Successfully!",false);
		}
	}
	
	
	public void compareCustomizeStartDay(String textStartDay) throws Exception
	{
		if(isElementLoaded(customizeShiftStartdayLabel))
		{
			String[] actualTextStartDay = customizeShiftStartdayLabel.getText().split(":");
			if(actualTextStartDay[0].equals(textStartDay)){
				SimpleUtils.pass("Start time on Custimize New shift page "+actualTextStartDay[0]+" is same as "+textStartDay);
			}else{
				SimpleUtils.fail("Start time on Custimize New shift page "+actualTextStartDay[0]+" is not same as "+textStartDay,true);
			}
		}else
		{
			SimpleUtils.fail("Customize New Shift Page not loaded Successfully!",false);
		}
			
	}
	
	
	public void clickOnStartDay(String textStartDay) throws Exception
	{
		if(isElementLoaded(customizeShiftStartdayLabel))
		{
			String[] actualTextStartDay = customizeShiftStartdayLabel.getText().split(":");
			if(actualTextStartDay[0].equals(textStartDay)){
				SimpleUtils.pass("Start time on Customize New shift page "+actualTextStartDay[0]+" is same as "+textStartDay);
			}else{
				SimpleUtils.fail("Start time on Customize New shift page "+actualTextStartDay[0]+" is not same as "+textStartDay,true);
			}
		}else
		{
			SimpleUtils.fail("Customize New Shift Page not loaded Successfully!",false);
		}
			
	}
	
	
	public void moveSliderAtSomePoint(String shiftTime, int shiftStartingCount, String startingPoint) throws Exception
	{
		if(startingPoint.equalsIgnoreCase("End")){
			if(isElementLoaded(sliderNotchEnd) && sliderDroppableCount.size()>0){
				SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for End Point");
				for(int i= shiftStartingCount; i<= sliderDroppableCount.size();i++){
					if(i == (shiftStartingCount + Integer.parseInt(shiftTime))){
						WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+Integer.parseInt(shiftTime))+")"));
						mouseHoverDragandDrop(sliderNotchEnd,element);
						MyThreadLocal.setScheduleHoursEndTime((i-2)+Integer.parseInt(shiftTime));
						break;
					}
					
				}
				
			}else{
				SimpleUtils.fail("Shift timings with Sliders not loading on page Successfully", false);
			}
		}else if(startingPoint.equalsIgnoreCase("Start")){
			if(isElementLoaded(sliderNotchStart) && sliderDroppableCount.size()>0){
				SimpleUtils.pass("Shift timings with Sliders loaded on page Successfully for Starting point");
				for(int i= shiftStartingCount; i<= sliderDroppableCount.size();i++){
					if(i == (shiftStartingCount + Integer.parseInt(shiftTime))){
						WebElement element = getDriver().findElement(By.cssSelector("div.lgn-time-slider-notch.droppable:nth-child("+(i+Integer.parseInt(shiftTime))+")"));
						mouseHoverDragandDrop(sliderNotchStart,element);
						MyThreadLocal.setScheduleHoursStartTime((i-2)+Integer.parseInt(shiftTime));
						break;
					}
					
				}
				
			}else{
				SimpleUtils.fail("Shift timings with Sliders not loading on page Successfully", false);
			}
		}		
	}
	
	public HashMap<String, String> calculateHourDifference() throws Exception {
		int scheduleHoursDifference = 0;
		HashMap<String, String> shiftTimeSchedule = new HashMap<String, String>();
		if(isElementLoaded(sliderNotchStart) && sliderDroppableCount.size()>0){
			int scheduledHoursStartTime = MyThreadLocal.getScheduleHoursStartTime();
			int scheduledHoursEndTime = MyThreadLocal.getScheduleHoursEndTime();
			scheduleHoursDifference = (scheduledHoursEndTime - scheduledHoursStartTime)/2;
			String[] customizeStartTimeLabel = customizeShiftStartdayLabel.getText().split(":");
			String[] customizeEndTimeLabel = customizeShiftEnddayLabel.getText().split(":");
			SimpleUtils.pass("Schedule hour difference is "+scheduleHoursDifference);
			shiftTimeSchedule.put("ScheduleHrDifference",Integer.toString(scheduleHoursDifference));
			shiftTimeSchedule.put("CustomizeStartTimeLabel",customizeStartTimeLabel[0]);
			shiftTimeSchedule.put("CustomizeEndTimeLabel",customizeEndTimeLabel[0]);
		}
		return shiftTimeSchedule; 
	}
	
	public void selectWorkRole(String workRoles) throws Exception{
		if(isElementLoaded(btnWorkRole)){
			  	click(btnWorkRole);
				SimpleUtils.pass("Work Role button clicked Successfully");
			}else{
				SimpleUtils.fail("Work Role button is not clickable",false);
			}
		if(listWorkRoles.size() >0){
			for(WebElement listWorkRole : listWorkRoles){
				if(listWorkRole.getText().equalsIgnoreCase(workRoles)){
					click(listWorkRole);
					SimpleUtils.pass("Work Role "+workRoles+ "selected Successfully");
					break;
				}else{
					SimpleUtils.report("Work Role "+workRoles+ " not selected");
				}
			}
			
		}else{
			SimpleUtils.fail("Work Roles size are empty",false);
		}
		
	}
	
	public void clickRadioBtnStaffingOption(String staffingOption) throws Exception{
		boolean flag = false;
		int index = -1;
		if(radioBtnStaffingOptions.size()!=0 && radioBtnShiftTexts.size() !=0 &&
				radioBtnStaffingOptions.size() == radioBtnShiftTexts.size()){

			for(WebElement radioBtnShiftText :  radioBtnShiftTexts){
				index = index+1;
				if(radioBtnShiftText.getText().contains(staffingOption)){
					click(radioBtnStaffingOptions.get(index));
					SimpleUtils.pass(radioBtnShiftText.getText()+ "Radio Button clicked Successfully!");
					flag = true;
					break;
				}
			}

			if(flag == false){
				SimpleUtils.fail("No Radio Button Selected!",false);
			}
			
		}else{
			SimpleUtils.fail("Staffing option Radio Button is not clickable!",false);
		}
	}
	
	public void clickOnCreateOrNextBtn() throws Exception{
		if(isElementLoaded(btnSave)){
			click(btnSave);
			SimpleUtils.pass("Create or Next Button clicked Successfully on Customize new Shift page!");
		}else{
			SimpleUtils.fail("Create or Next Button not clicked Successfully on Customize new Shift page!",false);
		}
	}
		
	public HashMap<List<String>,List<String>> calculateTeamCount() throws Exception{
		HashMap<List<String>,List<String>> gridDayHourTeamCount = new HashMap<List<String>,List<String>>();
		List<String> gridDayCount = new ArrayList<String>();
		List<String> gridTeamCount = new ArrayList<String>();
		if(gridHeaderDayHour.size()!=0 && gridHeaderTeamCount.size() !=0 &&
				gridHeaderDayHour.size() == gridHeaderTeamCount.size()){
			for(int i=0; i< gridHeaderDayHour.size();i++){
				gridDayCount.add(gridHeaderDayHour.get(i).getText());
				gridTeamCount.add(gridHeaderTeamCount.get(i).getText());
				SimpleUtils.pass("Day Hour is "+gridHeaderDayHour.get(i).getText()+ " And Team Count is "+gridHeaderTeamCount.get(i).getText());
			}
		}else{
			SimpleUtils.fail("Team Count and Day hour does not match",false);
		}
		gridDayHourTeamCount.put(gridDayCount,gridTeamCount);
		return gridDayHourTeamCount;
	}
	
	public List<String> calculatePreviousTeamCount(
			HashMap<String, String> shiftTimeSchedule, HashMap<List<String>,List<String>> 
			gridDayHourPrevTeamCount)throws Exception{
		int count = 0;
		waitForSeconds(3);
		List<String> gridDayHourTeamCount = new ArrayList<String>();
		exit:
		for (Map.Entry<List<String>,List<String>> entry : gridDayHourPrevTeamCount.entrySet()) {
			 List<String> previousKeys = entry.getKey();
			 List<String> val = entry.getValue();
			 for(String previousKey : previousKeys){
				 String[] arrayPreviousKey = previousKey.split(" ");
				 count =count + 1;
				 if(shiftTimeSchedule.get("CustomizeStartTimeLabel").equals(arrayPreviousKey[0])){
					 for(int j=0;j<Integer.parseInt(shiftTimeSchedule.get("ScheduleHrDifference"));j++){
						 count = count + 1;
						 gridDayHourTeamCount.add(val.get(count-2));
						 if(j==Integer.parseInt(shiftTimeSchedule.get("ScheduleHrDifference"))-1){
							 break exit;
						 }
					 }
				 } 
			 }
		}
		return gridDayHourTeamCount;
	}
	
	public List<String> calculateCurrentTeamCount(HashMap<String, String> shiftTiming )throws Exception{
		int count = 0;
		waitForSeconds(3);
		HashMap<List<String>,List<String>> gridDayHourCurrentTeamCount = calculateTeamCount();
		List<String> gridDayHourTeamCount = new ArrayList<String>();
		exit:
		for (Map.Entry<List<String>,List<String>> entry : gridDayHourCurrentTeamCount.entrySet()) {
			 List<String> previousKeys = entry.getKey();
			 List<String> val = entry.getValue();
			 for(String previousKey : previousKeys){
				 String[] arrayPreviousKey = previousKey.split(" ");
				 count =count + 1;
				 if(shiftTiming.get("CustomizeStartTimeLabel").equals(arrayPreviousKey[0])){
					 for(int j=0;j<Integer.parseInt(shiftTiming.get("ScheduleHrDifference"));j++){
						 count = count + 1;
						 gridDayHourTeamCount.add(val.get(count-2));
						 if(j==Integer.parseInt(shiftTiming.get("ScheduleHrDifference"))-1){
							 break exit;
						 }
					 }
				 } 
			 }
		}
		return gridDayHourTeamCount;
	}
	
	public void clickSaveBtn() throws Exception{
		if(isElementLoaded(scheduleSaveBtn)){
			click(scheduleSaveBtn);
			if(isElementLoaded(scheduleVersionSaveBtn))
				click(scheduleVersionSaveBtn);
			if(isElementLoaded(btnOK))
				click(btnOK);
			SimpleUtils.pass("Schedule Save button clicked Successfully!");
		}else{
			SimpleUtils.fail("Schedule Save button not clicked Successfully!",false);
		}
	}
	
	public void clickOnVersionSaveBtn() throws Exception{
		if(isElementLoaded(popUpPreSave) && isElementLoaded(scheduleVersionSaveBtn)){
			click(scheduleVersionSaveBtn);
			SimpleUtils.pass("Schedule Version Save button clicked Successfully!");
			waitForSeconds(3);
		}else{
			SimpleUtils.fail("Schedule Version Save button not clicked Successfully!",false);
		}
	}
	
	public void clickOnPostSaveBtn() throws Exception{
		if(isElementLoaded(popUpPostSave) && isElementLoaded(btnOK)){
			click(btnOK);
			SimpleUtils.pass("Schedule Ok button clicked Successfully!");
			waitForSeconds(3);
		}else{
			SimpleUtils.fail("Schedule Ok button not clicked Successfully!",false);
		}
	}

    public HashMap<String, ArrayList<WebElement>> getAvailableFilters()
    {
        HashMap<String, ArrayList<WebElement>> scheduleFilters = new HashMap<String,ArrayList<WebElement>>();
        try {
            if(isElementLoaded(filterButton))
            {
            	if(filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
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


	@Override
	public boolean inActiveWeekDayClosed(int dayIndex) throws Exception {
		if(isWeekGenerated())
		{
			navigateDayViewWithIndex(dayIndex);
			if(isElementLoaded(holidayLogoContainer))
				return true;
		}
		else
		{
			if(guidanceWeekOperatingHours.size() != 0)
			{
				if(guidanceWeekOperatingHours.get(dayIndex).getText().contains("Closed"))
					return true;
			}
		}
		return false;

	}

	@Override
	public void navigateDayViewWithIndex(int dayIndex) {
		if(dayIndex < 7 && dayIndex >= 0) {
			try {
				clickOnDayView();
				List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
				if(ScheduleCalendarDayLabels.size() == 7)
				{
					click(ScheduleCalendarDayLabels.get(dayIndex));
				}
			} catch (Exception e) {
				SimpleUtils.fail("Unable to navigate to in Day View", false);
			}
		}
		else {
			SimpleUtils.fail("Invalid dayIndex value to verify Store is Closed for the day", false);
		}

	}

	@Override
	public String getActiveGroupByFilter() throws Exception {
		String selectedGroupByFilter = "";
		if(isElementLoaded(scheduleGroupByButton))
		{
			Select groupBySelectElement = new Select(scheduleGroupByButton);
			selectedGroupByFilter = groupBySelectElement.getFirstSelectedOption().getText();
		}
		else
		{
			SimpleUtils.fail("Group By Filter not loaded successfully for active Week/Day: '"+ getActiveWeekText() +"'", false);
		}
		return selectedGroupByFilter;
	}


	@Override
	public boolean isActiveWeekHasOneDayClose() throws Exception {
		for(int index =0; index < dayCount.Seven.getValue(); index++)
		{
			if(inActiveWeekDayClosed(index))
				return true;
		}
		return false;
	}

	@Override
	public boolean isActiveWeekAssignedToCurrentUser(String userName) throws Exception {
		clickOnWeekView();
		if(shiftsOnScheduleView.size() != 0)
        {
            for(WebElement shiftOnScheduleView : shiftsOnScheduleView)
            {
                if(shiftOnScheduleView.getText().trim().length() > 0 && shiftOnScheduleView.isDisplayed()
                		&& shiftOnScheduleView.getText().toLowerCase().contains(userName.toLowerCase()))
                {
                    SimpleUtils.pass("Active Week/Day: '"+ getActiveWeekText() +"' assigned to '"+userName+"'.");
                    return true;
                }
            }
        }
		SimpleUtils.report("Active Week/Day: '"+ getActiveWeekText() +"' not assigned to '"+userName+"'.");
		return false;
	}


	@Override
	public boolean isScheduleGroupByWorkRole(String workRoleOption) throws Exception {
		if(getActiveGroupByFilter().equalsIgnoreCase(workRoleOption))
		{
			String filterType = "workrole";
			ArrayList<WebElement> availableWorkRoleFilters = getAvailableFilters().get(filterType);
			if(availableWorkRoleFilters.size() == scheduleShiftHeaders.size())
			{
				return true;
			}

		}
		return false;
	}

	public void selectWorkRoleFilterByIndex(int index) throws Exception
	{
		String filterType = "workrole";
		ArrayList<WebElement> availableWorkRoleFilters = getAvailableFilters().get(filterType);
		if(availableWorkRoleFilters.size() >= index)
		{
			unCheckFilters(availableWorkRoleFilters);
			click(availableWorkRoleFilters.get(index));
			SimpleUtils.pass("Schedule Work Role:'"+ availableWorkRoleFilters.get(index).getText() +"' Filter selected Successfully!");
		}
	}

	@Override
	public ArrayList<String> getSelectedWorkRoleOnSchedule() throws Exception {
		ArrayList<String> selectedScheduleTabWorkRoles = new ArrayList<String>();
		String filterType = "workrole";
		ArrayList<WebElement> availableWorkRoleFilters = getAvailableFilters().get(filterType);
		if(availableWorkRoleFilters.size() > 0)
		{
			for(WebElement filterElement: availableWorkRoleFilters)
	        {
	            WebElement filterCheckBox = filterElement.findElement(By.cssSelector("input[type=\"checkbox\"]"));
	            String elementClasses = filterCheckBox.getAttribute("class").toLowerCase();
	            if(elementClasses.contains("ng-not-empty"))
	            {
	            	selectedScheduleTabWorkRoles.add(filterElement.getText());
	            	SimpleUtils.report("Selected Work Role: '" + filterElement.getText() + "'");
	            }
	        }
			if(! filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
                click(filterButton);
		}
		return selectedScheduleTabWorkRoles;
	}


	@Override
	public boolean isRequiredActionUnAssignedShiftForActiveWeek() throws Exception {
		String unAssignedShiftRequireActionText = "unassigned shift";
		if(isElementLoaded(requiredActionCard))
		{
			if(requiredActionCard.getText().toLowerCase().contains(unAssignedShiftRequireActionText))
			{
				SimpleUtils.report("Required Action for Unassigned Shift found for the week: '"+ getActiveWeekText() +"'");
				return true;
			}
		}
		return false;
	}


	public void clickOnRefreshButton() throws Exception
	{
		if(isElementLoaded(refresh))
			click(refresh);
			if(isElementLoaded(confirmRefreshButton))
				click(confirmRefreshButton);
				if(isElementLoaded(okRefreshButton)) {
					click(okRefreshButton);
					SimpleUtils.pass("Active Week: '"+ getActiveWeekText() +"' refreshed successfully!");
				}
	}

	public void selectShiftTypeFilterByText(String filterText) throws Exception
	{
		String shiftTypeFilterKey = "shifttype";
		ArrayList<WebElement> shiftTypeFilters = getAvailableFilters().get(shiftTypeFilterKey);
		unCheckFilters(shiftTypeFilters);
		for(WebElement shiftTypeOption : shiftTypeFilters)
		{
			if(shiftTypeOption.getText().toLowerCase().contains(filterText.toLowerCase()))
			{
				click(shiftTypeOption);
				break;
			}
		}
		if(! filterPopup.getAttribute("class").toLowerCase().contains("ng-hide"))
            click(filterButton);
	}


	public List<WebElement> getAvailableShiftsInDayView()
	{

		return dayViewAvailableShifts;
	}

	public void dragShiftToRightSide(WebElement shift, int xOffSet)
	{
		WebElement shiftRightSlider = shift.findElement(By.cssSelector("div.sch-day-view-shift-pinch.right"));
		moveDayViewCards(shiftRightSlider, xOffSet);
	}

	public void moveDayViewCards(WebElement webElement, int xOffSet)
	{
		Actions builder = new Actions(MyThreadLocal.getDriver());
		builder.moveToElement(webElement)
	         .clickAndHold()
	         .moveByOffset(xOffSet, 0)
	         .release()
	         .build()
	         .perform();
	}


	public boolean isSmartCardAvailableByLabel(String cardLabel) throws Exception
	{
		if(carouselCards.size() !=0)
		{
			for(WebElement carouselCard: carouselCards)
			{
				if(carouselCard.isDisplayed() && carouselCard.getText().toLowerCase().contains(cardLabel.toLowerCase()))
					return true;
			}
		}
		return false;
	}


  //added by Nishant

  	@Override
  	public HashMap<String, Float> getScheduleLabelHours() throws Exception {
  		HashMap<String, Float> scheduleHours = new HashMap<String, Float>();
  		WebElement budgetedScheduledLabelsDivElement = MyThreadLocal.getDriver().findElement(By.cssSelector("div.card-carousel-card.card-carousel-card-primary"));
  		if(isElementLoaded(budgetedScheduledLabelsDivElement))
  		{
  			String scheduleWagesAndHoursCardText = budgetedScheduledLabelsDivElement.getText();
  			String[] scheduleWagesAndHours = scheduleWagesAndHoursCardText.split("\n");
  			for(String wagesAndHours: scheduleWagesAndHours)
  			{
  				if(wagesAndHours.toLowerCase().contains(scheduleHoursAndWagesData.hours.getValue().toLowerCase()))
  				{
  					scheduleHours = updateScheduleHoursAndWages(scheduleHours , wagesAndHours.split(" ")[2],
  							scheduleHoursAndWagesData.scheduledHours.getValue());
  					scheduleHours = updateScheduleHoursAndWages(scheduleHours , wagesAndHours.split(" ")[3],
  							scheduleHoursAndWagesData.otherHours.getValue());
  				}

  			}
  		}
  		return scheduleHours;
   	}


  	public int getgutterSize(){
  		int guttercount =0;
  		try{
  			guttercount = gutterCount.size();
  		}catch(Exception e){
  			SimpleUtils.fail("Gutter element not available on the page", false);
  		}
  		return guttercount;
  	}

  	public void verifySelectTeamMembersOption() throws Exception{
  		waitForSeconds(4);
  		if(selectTeamMembersOption.size()!=0){
  			if(isElementLoaded(selectRecommendedOption)){
  				String[] txtRecommendedOption = selectRecommendedOption.getText().replaceAll("\\p{P}","").split(" ");
  				if(Integer.parseInt(txtRecommendedOption[2])==0){
  					searchText(propertySearchTeamMember.get("AssignTeamMember"));
  					SimpleUtils.pass(txtRecommendedOption[0]+" Option selected By default for Select Team member option");
  				}else{
  					getScheduleBestMatchStatus();
  					SimpleUtils.pass(txtRecommendedOption[0]+" Option selected By default for Select Team member option");
  				}

			}
  		}
  	}

	public void searchText(String searchInput) throws Exception {
		String[] searchAssignTeamMember = searchInput.split(",");
		if(isElementLoaded(textSearch) && isElementLoaded(searchIcon)){
			for(int i=0; i<searchAssignTeamMember.length;i++){
				textSearch.sendKeys(searchAssignTeamMember[i]);
				click(searchIcon);
				if(getScheduleStatus()){
					break;
				}

			}

		}else{
			SimpleUtils.fail("Search text not editable and icon are not clickable", false);
		}

	}

	public boolean getScheduleStatus()throws Exception {
		boolean ScheduleStatus = false;
		if(scheduleStatus.size()!=0 && radionBtnSelectTeamMembers.size() == scheduleStatus.size()){
			for(int i=0; i<scheduleStatus.size();i++){
				if(scheduleStatus.get(i).getText().contains("Available")
						|| scheduleStatus.get(i).getText().contains("Unknown")){
					click(radionBtnSelectTeamMembers.get(i));
					ScheduleStatus = true;
					break;
				}
			}
		}else{
			SimpleUtils.fail("Not able to found Available status in SearchResult", false);
		}

		return ScheduleStatus;
	}

	public boolean getScheduleBestMatchStatus()throws Exception {
		boolean ScheduleBestMatchStatus = false;
		if(scheduleStatus.size()!=0 || scheduleBestMatchStatus.size()!=0 && radionBtnSelectTeamMembers.size() == scheduleStatus.size()){
			for(int i=0; i<scheduleBestMatchStatus.size();i++){
				if(scheduleBestMatchStatus.get(i).getText().contains("Best")
						|| scheduleStatus.get(i).getText().contains("Unknown")){
					click(radionBtnSelectTeamMembers.get(i));
					ScheduleBestMatchStatus = true;
					break;
				}
			}
		}else{
			SimpleUtils.fail("Not able to found Available status in SearchResult", false);
		}

		return ScheduleBestMatchStatus;
	}

	public void getAvailableStatus()throws Exception {
		if(scheduleStatus.size()!=0 && availableStatus.size()!=0 && radionBtnSelectTeamMembers.size() == scheduleStatus.size()){
			for(int i=0; i<scheduleStatus.size();i++){
				if(scheduleStatus.get(i).getText().contains(availableStatus.get(i).getText())){
					click(radionBtnSelectTeamMembers.get(i));
					break;
				}
			}
		}else{
			SimpleUtils.fail("Not able to found Available status in SearchResult", false);
		}
	}

	public void clickOnOfferOrAssignBtn() throws Exception{
		if(isElementLoaded(btnOffer)){
			click(btnOffer);
		}else{
			SimpleUtils.fail("Offer Or Assign Button is not clickable", false);
		}
	}

	public void clickOnShiftContainer(int index) throws Exception{
		int guttercount = getgutterSize();
		waitForSeconds(2);
		if(shiftContainer.size()!=0 && index<=shiftContainer.size() && isElementLoaded(shiftContainer.get(0))){
			for(int i=0;i<index;i++){
				mouseHover(shiftContainer.get(i));
//				click(shiftContainer.get(i));
				deleteShift();
				waitForSeconds(2);
			}

		}else{
			SimpleUtils.report("Shift container does not any gutter text");
		}
	}


	public void deleteShift(){
		if(shiftDeleteBtn.size()!=0){
			for(int i=0;i<shiftDeleteBtn.size();i++){
				click(shiftDeleteBtn.get(i));
			}
		}else{
			SimpleUtils.fail("Delete button is not available on Shift container",false);
		}
	}


	public void deleteShiftGutterText(){
		if(shiftDeleteGutterText.size()!=0){
			for(int i=0;i<shiftDeleteGutterText.size();i++){
				if(shiftDeleteGutterText.get(i).equals("Delete")){
					SimpleUtils.pass(shiftDeleteGutterText.get(i)+" Option as gutter is present on shift container");
				}

			}
		}else{
			SimpleUtils.fail("Delete text is not present on Shift container gutter",true);
		}
	}
	
	public ArrayList<String> getActiveWeekCalendarDates() throws Exception
	{
		ArrayList<String> scheduleWeekCalendarDates = new ArrayList<String>();
		String catendarWeekDatesAsText = "";
		for(WebElement ScheduleWeekCalendarDate : ScheduleWeekCalendarDates)
		{
			scheduleWeekCalendarDates.add(ScheduleWeekCalendarDate.getText().replace("\n", " "));
			if(catendarWeekDatesAsText == "")
				catendarWeekDatesAsText = ScheduleWeekCalendarDate.getText().replace("\n", " ");
			else
				catendarWeekDatesAsText = catendarWeekDatesAsText+ " | " +ScheduleWeekCalendarDate.getText().replace("\n", " ");
		}
		SimpleUtils.report("Active Week Calendar Dates: '" + catendarWeekDatesAsText + "'");
		return scheduleWeekCalendarDates;
	}

	@Override
	public void refreshBrowserPage() throws Exception {
		MyThreadLocal.getDriver().navigate().refresh();
		Thread.sleep(5000);
		SimpleUtils.pass("Browser Refreshed Successfully for the Week/Day: '"+ getActiveWeekText() +"'!");
		
	}
	
	@Override
	public void addOpenShiftWithDefaultTime(String workRole) throws Exception
	{
		if(isElementLoaded(addNewShiftOnDayViewButton))
		{
			click(addNewShiftOnDayViewButton);
			selectWorkRole(workRole);
			clickRadioBtnStaffingOption(staffingOption.OpenShift.getValue());
			clickOnCreateOrNextBtn();
			Thread.sleep(2000);
		}
		else
			SimpleUtils.fail("Day View Schedule edit mode, add new shift button not found for Week Day: '" + 
					getActiveWeekText() + "'", false);
	}
	
	@Override
	public boolean isNextWeekAvaibale() throws Exception
	{
		if(! isElementLoaded(calendarNavigationNextWeekArrow))
		{
			List<WebElement> ScheduleCalendarDayLabels = MyThreadLocal.getDriver().findElements(By.className("day-week-picker-period"));
			if(ScheduleCalendarDayLabels.get(ScheduleCalendarDayLabels.size() - 1).getAttribute("class").toLowerCase().contains("active"))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean isSmartCardPanelDisplay() throws Exception
	{
		if(isElementLoaded(smartCardPanel))
		{
			return true;
		}
		return false;
	}
}