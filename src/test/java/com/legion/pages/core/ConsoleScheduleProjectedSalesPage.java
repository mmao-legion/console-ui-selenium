package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.ScheduleProjectedSalesPage;
import com.legion.pages.core.ConsoleSchedulePage;
import com.legion.utils.JsonUtil;

public class ConsoleScheduleProjectedSalesPage extends BasePage implements ScheduleProjectedSalesPage{

	
	@FindBy(css="div.console-navigation div:nth-child(4)")
	private WebElement goToScheduleButton;
	
	@FindBy(xpath="//span[contains(text(),'Overview')]")
	private WebElement goToScheduleOverviewTab;
	
	@FindBy(xpath="//span[contains(text(),'Projected Sales')]")
	private WebElement goToProjectedSalesTab;
	
	@FindBy(xpath="//span[contains(text(),'Staffing Guidance')]")
	private WebElement goToStaffingGuidanceTab;
	
	@FindBy(css="div[helper-text*='Work in progress Schedule'] span.legend-label")
	private WebElement draft;
	
	@FindBy(css="div[helper-text-position='top'] span.legend-label")
	private WebElement published;
	
	@FindBy(css="div[helper-text*='final per schedule changes'] span.legend-label")
	private WebElement finalized;
	
	@FindBy(css="ui-view[name='forecastControlPanel'] span.highlight-when-help-mode-is-on")
	private WebElement salesGuidance;
	
	@FindBy(css="div[ng-click*='refresh'] span.sch-control-button-label")
	private WebElement refresh;
	
	@FindBy(xpath="//div[contains(text(),'Guidance')]")
	private WebElement guidance;
	
	@FindBy(css="div[ng-click*='analyze'] span.sch-control-button-label")
	private WebElement analyze;
	
	@FindBy(css="div[ng-click*='edit'] span.sch-control-button-label")
	private WebElement edit;
	
	@FindBy(xpath="//div[contains(@class,'sub-navigation-view')]//span[contains(text(),'Schedule')]")
	private WebElement goToScheduleTab;
	
	@FindBy(css="[ng-if='!loading']")
	private WebElement weeklyScheduleTableBodyElement;
	
	@FindBy(className="schedule-table-row")
	private List<WebElement> weeklyScheduleTableRowElement;
	
	@FindBy(css="[ng-class='nameClass()']")
	private WebElement weeklyScheduleTableLeftColumnElement;
	
	@FindBy(className="left-banner")
	private WebElement weeklyScheduleDateElement;
	
	@FindBy(css="[ng-if='!isLocationGroup()']")
	private WebElement weeklyScheduleStatusElement;
	
	@FindBy(css="[ng-if=\"controlPanel.fns.getVisibility('EDIT')\"]")
	private WebElement weeklyScheduleEditButton;
	
	@FindBy(css="[ng-if=\"controlPanel.fns.getVisibility('CANCEL')\"]")
	private WebElement weeklyScheduleCancelEditingButton;
	
	@FindBy(css="[label='okLabel()']")
	private List<WebElement> editAnywayPopupButton;
	
	@FindBy(css="[ng-click='gotoNextWeek($event)']")
	private WebElement gotoNextWeekArrow;
	
	@FindBy(css="[ng-click=\"gotoPreviousWeek($event)\"]")
	private WebElement gotoPreviousWeekArrow;
	
	@FindBy(className="sch-shift-transpose-grid")
	private WebElement weeklyScheduleTransposeGridElement;
	
	@FindBy(css="[label='cancelLabel()']")
	private WebElement weeklyScheduleAnywayPopupCancelButton;
	
	@FindBy(css="[ng-click=\"selectDayWeekView($event, 'day')\"]")
	private WebElement dayViewButton;
	
	@FindBy(css="[ng-click=\"selectDayWeekView($event, 'week')\"]")
	private WebElement weekViewButton;
	@FindBy(css="[ng-if=\"controlPanel.fns.getVisibility('EDIT')\"]")
	private WebElement dayViewEditButton;
	
	@FindBy(css="[sly-repeat=\"shiftGroup in getShiftGroups()\"]")
	private WebElement dayViewGridItems;
	
	@FindBy(className="sch-control-button-cancel")
	private WebElement dayViewEditCancelButton;
	
	@FindBy(className="sch-grid-container")
	private WebElement dayViewGridContainerElement;
	
	@FindBy(className="right-shift-box")
	private WebElement dayViewRightRowElement;
	
	@FindBy(className="right-shift-box")
	private List<WebElement> dayViewGridRightRows;
	
	@FindBy(className="left-shift-box")
	private WebElement dayViewLeftRowElement;
	
	@FindBy(className="left-shift-box")
	private List<WebElement> dayViewGridLeftRows;
	
	@FindBy(className="schedule-view")
	private WebElement ScheduleViewClassElement;
	
	@FindBy(className="sch-day-view-shift-pinch")
	private WebElement dayViewRowCardElement;
	
	@FindBy(className="tm-time-info-container")
	private WebElement dayViewTimeInfoContainerElement;
	
	@FindBy(className="sch-day-view-shift-time")
	private WebElement dayViewShiftTimeElement;
	
	@FindBy(className="overview-view")
	private WebElement overviewSectionElement;
	
	WebElement scheduleTable = null;
    List<WebElement> scheduleTableRowElement = null;
    Map<String, String> weeklyTableRowsDatesAndStatus = new LinkedHashMap<String, String>();
    WebElement element = null;
    WebElement scheduleTableLeftColumn = null;
    String scheduleWeekDate = null;
    String scheduleWeekStatus = null;
    String memberName = "NA";
	String memberJobTitle = "NA";
	String monthlyHours = "NA";
	String dailyHours = "NA";
	String overTimeHours = "NA";
	String shiftTime = "NA";

	/* Create New Shift Element/Variables */
	
	@FindBy(className="sub-navigation-view")
	private List<WebElement> scheduleSubNavigationViewTab;
	
	@FindBy(className="sub-navigation-view")
	private List<WebElement> scheduleViewSubTabElements;
	
	@FindBy(className="[ng-if=\"canShowNewShiftButton()\"]")
	private WebElement addNewShiftOnDayViewButton;
	
	@FindBy(className="modal-content")
	private WebElement newShiftCreationModalContentElement;
	
	@FindBy(css="[attr-id=\"workerRoles\"]")
	private WebElement newShiftWorkRoleDropdownElement;

	@FindBy(className="dropdown-menu")
	private WebElement newShiftWorkRoleDropdownMenuElement;
	
	@FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	private WebElement newShiftWorkRoleDropdownMenuItems;
	
	@FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	private List<WebElement> newShiftRoleDropdownMenu;
	
	@FindBy(css="[ng-if=\"!isSimpleLayout\"]")
	private WebElement newShiftHoursSliderElement;
	
	@FindBy(css="[ng-if=\"!isSimpleLayout\"]")
	private List<WebElement> newShiftHoursSliderPoints;
	
	@FindBy(css="[ng-model=\"ec.originalShift.name\"]")
	private WebElement newShiftNameTextField;
	
	@FindBy(css="[ng-model=\"ec.originalShift.notes\"]")
	private WebElement newShiftNoteTextField;
	
	@FindBy(className="tma-scroll-table")
	private List<WebElement> newShiftSearchedTeamList;
	
	@FindBy(className="table-row")
	private WebElement newShiftSearchTeamMemberDetailRow;
	
	@FindBy(css="[ng-click='selectAction($event, worker)']")
	private List<WebElement> NewShiftSelectTeamMemberButton;
	
	@FindBy(css="[ng-if=\"showAssign()\"]")
	private WebElement newShiftAssignTeamMemberbutton;
	
	@FindBy(className="tma-staffing-option-choice")
	private WebElement newShiftStaffingOption;
	
	@FindBy(className="tma-staffing-option-choice")
	private List<WebElement> newShiftStaffingOptionChoice;
	
	@FindBy(css="[ng-click=\"nextAction()\"]")
	private WebElement newShiftNextButton;
	
	@FindBy(className="tma-search-container")
	private WebElement newShiftSearchBoxContainer;
	
	@FindBy(css="[ng-model=\"searchText\"]")
	private WebElement newShiftSearchTeamMemberBox;
	
	@FindBy(className="lgn-time-slider-notch-label")
	private WebElement newShiftTimeSliderNotchLabel;
	
	@FindBy(className="sch-control-button-save")
	private WebElement dayViewShiftSaveButton;
	
	@FindBy(className="lgn-time-slider")
	private WebElement newShiftTimeSlider;
	
	@FindBy(css="[ng-click=\"callOkCallback()\"]")
	private WebElement newShiftAssignTeamMemberAnywayButton;
	
	@FindBy(css="[ng-click=\"confirmSaveAction($event)\"]")
	private WebElement newShiftSaveConfirmButton;
	
	@FindBy(css="[ng-click=\"okAction($event)\"]")
	private WebElement newShiftSucessfullySavedOkButton;
	
	@FindBy(css="[ng-click=\"createAction()\"]")
	private WebElement newShiftCreateActionButton;

	String newShiftname = "This is test Shift";
	String newShiftNote = "This is Sample Note for New Shift.";
	
	@FindBy(className="sub-navigation-view")
	private List<WebElement> schedulePageSubNavigationsTabElements;
	
	@FindBy(css="div.sch-calendar-day-dimension.active-day")
	private WebElement dayViewScheduleWeekStartDay;
	
	@FindBy(css="div.sch-calendar-day-dimension.sch-calendar-day-today")
	private WebElement selectedDayOnCalender;
	
	@FindBy(className="dc-summary-box")
	private WebElement today;
	
	@FindBy(className="dc-summary-slot")
	private List<WebElement> dcSummaryBoxDivElements;
	
	@FindBy(className="mt-18")
	private List<WebElement> budgetedScheduledLabelsDivElement;
	
	@FindBy(className="sf-summary-container")
	private WebElement dayViewScheduleProjectedSalesSummaryContainer;
	
	@FindBy(className="kpi")
	private List<WebElement> dayViewProjectedSalesCards;
	
	@FindBy(className="summary-label")
	private List<WebElement> dayViewprojectedSalesCardsLabel;
	
	@FindBy(className="data-item")
	private List<WebElement> dayViewProjectedSalesCardsData;
	
	@FindBy(css="[ng-if=\"controlPanel.view === 'SalesForecast'\"]")
	private List<WebElement> ScheduleProjectedSalesMenuItemFilterDropDown;
	
	@FindBy(css="[attr-id=\"workerRoles\"]")
	private WebElement staffingGuidanceWorkerRolesFilterButton;
	
	@FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	private List<WebElement>staffingGuidanceWorkerRolesFilterOptions;
	
	@FindBy(className="sch-week-view-day-summary-value")
	private List<WebElement>staffingGuidanceWeekViewAssignHours;
	
	@FindBy(className = "sch-calendar-day-dimension")
	private List<WebElement>staffingGuidanceWeekViewDayMonthDateLabels;
	
	@FindBy(css = "div.sub-navigation-view-link.active")
	private WebElement SchedulePageSelectedSubTab;
	
	@FindBy(css = "[attr-id=\"categories\"]")
	private WebElement staffingGuidanceMenuItemsDropDown;
	
	@FindBy(css = "[attr-id='categories'] ul li")
	private List<WebElement> staffingGuidanceMenuItemsDropDownOptions;
	
	Object[][] projectedSalesItemOptions = JsonUtil.getArraysFromJsonFile("src/test/resources/projectedSalesItemOptions.json");

	
	String projectedSalesTabText = "PROJECTED SALES";

	
	
	
	
	public ConsoleScheduleProjectedSalesPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	/*
	 *  LEG-2411 Legion Scheduling (Guidance, Schedule) Test Plan / LEG-2422
	 *  (As a store manager, can view Projected Sales Forecast data for past and current week)
	 */
	
	public void navigateToProjectedSalesForecastTab() throws Exception
	{
		String projectedSalesTabText = "PROJECTED SALES";
		WebElement projectedSalesElement = getSubTabElement(scheduleSubNavigationViewTab, projectedSalesTabText);
		if(projectedSalesElement != null)
		{
			projectedSalesElement.click();
		}
	}
	
	public Boolean isScheduleProjectedSalesTabActive() throws Exception
	{
		if(isElementLoaded(SchedulePageSelectedSubTab) && SchedulePageSelectedSubTab.getText().contains("PROJECTED SALES"))
		{
			return true;
		}
		return false;
	}
	
	public void navigateToProjectedSalesForecastTabWeekView() throws Exception
	{
		if(isElementLoaded(SchedulePageSelectedSubTab) && SchedulePageSelectedSubTab.getText().contains(projectedSalesTabText))
		{
			if(isElementLoaded(weekViewButton))
			{
				weekViewButton.click();
			}
			else {
				fail("Schedule Staffing Guidance Week View button not Loaded.", false);
			}
		}
		else {
			fail("Schedule Staffing Guidance Tab not dispalyed.", false);
		}
	}
	
	public Boolean isProjectedSalesForecastTabWeekViewActive() throws Exception {
		if(isElementLoaded(weekViewButton))
		{
			System.out.println("weekViewButton.getAttribute(\"class\"); :"+weekViewButton.getAttribute("class"));
			if(weekViewButton.getAttribute("class").toString().contains("enabled"))
			{
				return true;
			}
		}
		return false;
	}
	
	public void navigateToProjectedSalesForecastTabDayView() throws Exception
	{
		if(isElementLoaded(SchedulePageSelectedSubTab) && SchedulePageSelectedSubTab.getText().contains(projectedSalesTabText))
		{
			if(isElementLoaded(dayViewButton))
			{
				dayViewButton.click();
			}
			else {
				fail("Schedule Staffing Guidance Day View button not Loaded.", false);
			}
		}
		else {
			fail("Schedule Staffing Guidance Tab not dispalyed.", false);
		}
	}
	
	public Boolean isProjectedSalesForecastTabDayViewActive() throws Exception {
		if(isElementLoaded(dayViewButton))
		{
			if(dayViewButton.getAttribute("class").toString().contains("enabled"))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public Boolean verifyCurrentUserWithUserJobTitle(HashMap<String, String> propertyMap, String jobTitle)
	{
		String loggedUserName = propertyMap.get("DEFAULT_USERNAME");
		String currentUsersJobTitle = getCurrentUsersJobTitle(loggedUserName);
		if(currentUsersJobTitle.toLowerCase().contains(jobTitle.toLowerCase()))
		{
			return true;
		}
		return false;
	}
	
	
	public Boolean validateProjectedSalesItemOptionWithUserJobTitle(String userJobTitle) throws Exception
	{
    	String currentUsersProjectedSalesItemOption = "NA";
		if(isElementLoaded(SchedulePageSelectedSubTab) && SchedulePageSelectedSubTab.getText().contains("PROJECTED SALES"))
		{
			for (Object[] projectedSalesItemOption : projectedSalesItemOptions) {
				String userJobTitleFromJson = (String) projectedSalesItemOption[0];
				String projectedSalesItemOptionsFromJson = (String) projectedSalesItemOption[1];
				if(userJobTitleFromJson.toLowerCase().contains(userJobTitle.toLowerCase()))
					currentUsersProjectedSalesItemOption =  projectedSalesItemOptionsFromJson;
	    	}
			if(! currentUsersProjectedSalesItemOption.contains("N/A")){
				if(isElementLoaded(staffingGuidanceMenuItemsDropDown))
				{
					click(staffingGuidanceMenuItemsDropDown);
					if(staffingGuidanceMenuItemsDropDownOptions.size() != 0)
					{
						if(staffingGuidanceMenuItemsDropDownOptions.size() == currentUsersProjectedSalesItemOption.split(",").length){
							for(WebElement staffingGuidanceMenuItemsDropDownOption : staffingGuidanceMenuItemsDropDownOptions)
							{
								if(! currentUsersProjectedSalesItemOption.toLowerCase().contains(staffingGuidanceMenuItemsDropDownOption.getText().toLowerCase()))
								{
									return false;
								}
							}
							click(staffingGuidanceMenuItemsDropDown);
							return true;						
						}
					}
				}
			}
		}
		return false;
	}
	
	public void navigateProjectedSalesWeekViewTpPastOrFuture(String nextWeekViewOrPreviousWeekView, int weekCount) throws Exception
	{
		if(isScheduleProjectedSalesTabActive())
		{
			for(int i = 0; i < weekCount; i++)
			{
				if(nextWeekViewOrPreviousWeekView.toLowerCase().contains("next") || nextWeekViewOrPreviousWeekView.toLowerCase().contains("future"))
				{
					gotoNextWeekArrow.click();
				}
				else
				{
					gotoPreviousWeekArrow.click();
				}
			}
		}
		else {
			fail("Schedule Projected Sales Tab not Active!", false);
		}
		
	}
	
	
	public Boolean validateWeekViewWithDateFormat(String legionDateFormat)
	{
		String separator = " ";
		String staffingGuidanceWeekViewDayMonthDateLabelsText = getListElementTextAsString(staffingGuidanceWeekViewDayMonthDateLabels, separator);
		staffingGuidanceWeekViewDayMonthDateLabelsText = staffingGuidanceWeekViewDayMonthDateLabelsText.replace("\n", " ");
		if(staffingGuidanceWeekViewDayMonthDateLabelsText.toLowerCase().contains(legionDateFormat.toLowerCase()))
		{
			return true;
		}

		return false;
	}
	
	
	public Map<String, String> getScheduleProjectedSalesForeCastData() throws Exception
	{
		Map<String, String> projectedSalesData = new HashMap<String, String>();
		if(isScheduleProjectedSalesTabActive())
		{
			for(WebElement dayViewProjectedsalesCardsElement : dayViewProjectedSalesCards)
			{
				if(dayViewProjectedsalesCardsElement.getText().contains("PEAK DEMAND"))
				{
					String peakDemandString = dayViewProjectedsalesCardsElement.getText().replace("PEAK DEMAND", "").replace("\n", " ");
					String[] peakDemandStringSplit = peakDemandString.split("Projected");
					if(peakDemandStringSplit.length == 2)
					{
						String peakDemandProjected = peakDemandStringSplit[0];
						String peakDemandActual = peakDemandStringSplit[1].replace("Actual", "");
						projectedSalesData.put("peakDemandProjected", peakDemandProjected);
						projectedSalesData.put("peakDemandActual", peakDemandActual);
					}
				}
				else if(dayViewProjectedsalesCardsElement.getText().contains("TOTAL DEMAND"))
				{
					String totalDemandString = dayViewProjectedsalesCardsElement.getText().replace("TOTAL DEMAND", "").replace("\n", " ");
					String[] totalDemandStringSplit = totalDemandString.split("Projected");
					if(totalDemandStringSplit.length == 2)
					{
						String totalDemandProjected = totalDemandStringSplit[0];
						String totalDemandActual = totalDemandStringSplit[1].replace("Actual", "");
						projectedSalesData.put("totalDemandProjected", totalDemandProjected);
						projectedSalesData.put("totalDemandActual", totalDemandActual);
					}
				}
				else if(dayViewProjectedsalesCardsElement.getText().contains("PEAK TIME"))
				{
					String peakTimeString = dayViewProjectedsalesCardsElement.getText().replace("PEAK TIME", "").replace("\n", " ");
					String[] peakTimeStringSplit = peakTimeString.split("Projected");
					if(peakTimeStringSplit.length == 2)
					{
						String peakTimeProjected = peakTimeStringSplit[0];
						String peakTimeActual = peakTimeStringSplit[1].replace("Actual", "");
						projectedSalesData.put("peakTimeProjected", peakTimeProjected);
						projectedSalesData.put("peakTimeActual", peakTimeActual);
					}
				}
			}
		}
		else {
			fail("Schedule page Projected Sales Tab not Active!", false);
		}
		
		return projectedSalesData;
	}	
	
	// ToDo - Missing locator ID for SubTabs
	public WebElement getSubTabElement(List<WebElement> listWebElements, String subTabText)
	{
		for(WebElement listWebElement : listWebElements)
		{
			if(listWebElement.getText().contains(subTabText))
			{
				return listWebElement;
			}
		}
		return null;
	}
	
}
