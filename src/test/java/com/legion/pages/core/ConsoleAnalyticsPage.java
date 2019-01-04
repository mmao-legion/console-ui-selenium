package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.legion.pages.AnalyticsPage;
import com.legion.pages.BasePage;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.tests.testframework.ScreenshotManager;
import com.legion.utils.SimpleUtils;

public class ConsoleAnalyticsPage extends BasePage implements AnalyticsPage{
	
	 @FindBy(css="div.console-navigation-item-label.Analytics")
	 private WebElement consoleAnalyticsPageTabElement;
	 
	 @FindBy(className="analytics-dashboard-section")
	 private WebElement analyticsSectionsDivClass;
	 
	 @FindBy(className="analytics-dashboard-section")
	 private List<WebElement> analyticsDivElements;
	 
	 @FindBy(css="[ng-click=\"selectType('All')\"]")
	 private WebElement analyticsAllHoursCheckBox;
	 
	 @FindBy(css="[ng-click=\"selectType('Peak')\"]")
	 private WebElement analyticsPeakHoursCheckBox;
	 
	 @FindBy(css="[ng-if=\"hasHoursData()\"]")
	 private WebElement analyticsHoursDataSectionDiv;
	 
	 @FindBy(css=".analytics-dashboard-sub-section")
	 private WebElement analyticsSubSection;
	 
	 @FindBy(css=".analytics-dashboard-sub-section")
	 private List<WebElement> analyticsShiftOffersSubSectionElements;
	 
	 @FindBy(css=".analytics-dashboard-sub-section")
	 private List<WebElement> teamMemberSatisfactionsElements;
	 
	 @FindBy(css = "div.console-navigation-item.active")
	 private WebElement activeConsoleMenuItem;
	  
	 @FindBy(className="sub-title")
	 private WebElement sectionSubTitleDiv;
	    
	 @FindBy(className="[ng-if=\"showRoleFilter()\"]")
	 private WebElement TMSShowRoleFilterDropDownButton;
	 
	 @FindBy(css="ul.dropdown-menu.dropdown-menu-right")
	 private WebElement TMSDropdownMenuDiv;
	 
	 @FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	 private WebElement TMSDropdownMenuOptionsList;
	 
	 @FindBy(css="[ng-click=\"selectChoice($event, choice)\"]")
	 private List<WebElement> TMSDropdownMenuOptionsListElements;
	
	 @FindBy(className="sub-navigation-view-link")
	 private WebElement analyticsSubNavigationView;
	 
	 @FindBy (css = "div.console-navigation-item-label.Analytics")
	 private WebElement analyticsConsoleName;
	 
	 @FindBy(css = "div[data=\"hours\"]")
	 private WebElement forecastedHoursElement;
		
	 @FindBy(css = "div[data=\"scheduleHours\"]")
	 private WebElement scheduleHoursElement;
	 
	 @FindBy(css = "div.sch-calendar-navigation.right")
	 private WebElement navigationArrowRight;
	 
	 @FindBy(css= "div.sch-calendar-day-dimension.sch-calendar-day")
	 private List<WebElement> analyticsCalendarDays;


	public ConsoleAnalyticsPage(){
		PageFactory.initElements(getDriver(), this);
	}
	
	@Override
	public void gotoAnalyticsPage() throws Exception {
		if(isElementLoaded(consoleAnalyticsPageTabElement))
		{
			activeConsoleName = analyticsConsoleName.getText();
			click(consoleAnalyticsPageTabElement);
			if(isElementLoaded(analyticsSectionsDivClass))
			{
				for(WebElement analyticsDivElement:analyticsDivElements)
				{
					if(analyticsDivElement.getText().contains("Staffing Forecast Accuracy"))
					{
						System.out.println("Staffing Forecast Accuracy");
						//getStaffingForecastAccuracy(analyticsDivElement);
					}
					else if(analyticsDivElement.getText().contains("Team Member Satisfaction"))
					{
						ExtentTestManager.getTest().log(Status.INFO, "\n******************** Team Member Satisfaction Logs *****************************");

						getTeamMemberSatisfaction(analyticsDivElement);
					}
					else if(analyticsDivElement.getText().contains("Schedule Compliance"))
					{
						ExtentTestManager.getTest().log(Status.INFO,"\n******************** Schedule Compliance Logs *****************************");

//						getScheduleCompliance(analyticsDivElement);
					}
					else if(analyticsDivElement.getText().contains("Shift Offers"))
					{
						ExtentTestManager.getTest().log(Status.INFO,"\n******************** Shift Offers Logs *****************************");
						getShiftOffers(analyticsDivElement);
					}
				}
			}
		}
		
		/*
		 * Change tab to report
		 */
		if(isElementLoaded(analyticsSubNavigationView))
		{
			ExtentTestManager.getTest().log(Status.INFO,"\n******************** Analytics Report Logs *****************************");
			List<WebElement> analyticsSubNavigationViewTabs = getDriver().findElements(By.className("table-responsive"));
			for(WebElement analyticsSubNavigationViewTab : analyticsSubNavigationViewTabs) {
				if(analyticsSubNavigationViewTab.getText().contains("REPORTS"))
				{
					analyticsSubNavigationViewTab.click();
					SimpleUtils.pass("Analytics Report Section report Table Loaded Successfully!");
							
				}
			}
		}
		
	}
	
	public void getStaffingForecastAccuracy(WebElement analyticsDivElement) throws Exception
	{
		String analyticsTMSHasHoursDataSectionText = "";
		if(isElementLoaded(analyticsHoursDataSectionDiv)){
			SimpleUtils.pass("Analytics Staffing Forecast Accuracy Section Loaded Successfully!");
		}else{
			SimpleUtils.fail("Analytics Staffing Forecast Accuracy Section not Loaded Successfully!",true);
		}

		String allHoursCheckboxClasses = analyticsAllHoursCheckBox.getAttribute("class");
		String peakHoursCheckboxClasses = analyticsPeakHoursCheckBox.getAttribute("class");
		if(!allHoursCheckboxClasses.contains("checked"))
		{
			click(analyticsAllHoursCheckBox);
		}
		
		if(!peakHoursCheckboxClasses.contains("checked"))
		{
			click(analyticsPeakHoursCheckBox);
		}
		analyticsTMSHasHoursDataSectionText = analyticsHoursDataSectionDiv.getText();
		
		if(analyticsTMSHasHoursDataSectionText.contains("All Hrs")){
			SimpleUtils.pass("All Hours under Analytics Staffing Forecast Accuracy Section Loaded Successfully!");
		}else{
			SimpleUtils.fail("All Hours under Analytics Staffing Forecast Accuracy Section not Loaded Successfully!",true);
		}
	
		if(analyticsTMSHasHoursDataSectionText.contains("Peak Hrs")){
			SimpleUtils.pass("Peak Hours under Analytics Staffing Forecast Accuracy Section Loaded Successfully!");
		}else{
			SimpleUtils.fail("Peak Hours under Analytics Staffing Forecast Accuracy Section not Loaded Successfully!",true);
		}
		
	}
	
	public void getTeamMemberSatisfaction(WebElement analyticsDivElement) throws Exception {
		
		if(isElementLoaded(analyticsSubSection)){
			SimpleUtils.pass("Analytics Team Member Satisfaction Section Loaded Successfully!");
		}else{
			SimpleUtils.fail("Analytics Team Member Satisfaction Section not Loaded Successfully!",false);
		}
			
		if(isElementLoaded(TMSShowRoleFilterDropDownButton))
		{
			click(TMSShowRoleFilterDropDownButton);
			if(isElementLoaded(TMSDropdownMenuDiv))
			{
								
				for(WebElement TMSDropdownMenuOptionsListElement : TMSDropdownMenuOptionsListElements)
				{
					int dropdownIndex = TMSDropdownMenuOptionsListElements.indexOf(TMSDropdownMenuOptionsListElement);
					TMSDropdownMenuOptionsListElement.click();
					if(dropdownIndex > 1)
						TMSDropdownMenuOptionsListElements.get(dropdownIndex - 1).click();
					waitForSeconds(2000);
					for(WebElement teamMemberSatisfactionsElement : teamMemberSatisfactionsElements)
					{
						if(isElementLoaded(sectionSubTitleDiv))
						{
							String sectionSubHeaderText = sectionSubTitleDiv.getText();
							ExtentTestManager.getTest().log(Status.INFO,"Team Member Satisfaction - '"+sectionSubHeaderText+"' Loaded Successfully for - '"+TMSDropdownMenuOptionsListElement.getText()+"' Filter!");
						}
					}
				}
				click(TMSShowRoleFilterDropDownButton);
			}
		}
	}
	
	
	
	public void getShiftOffers(WebElement analyticsDivElement) throws Exception {
		if(isElementLoaded(analyticsSubSection))
		{
			for(WebElement analyticsShiftOffersSubSectionElement : analyticsShiftOffersSubSectionElements)
			{
				ExtentTestManager.getTest().log(Status.INFO,"Analytics Shift Offers Section - '"+analyticsShiftOffersSubSectionElement.getText()+"' Loaded Successfully!");
			}
		}
	}
	
	@Override
	public HashMap<String, Float> getForecastedHours() throws Exception {
		HashMap<String, Float> forecastedHours = new HashMap<String, Float>();
		if(isElementLoaded(forecastedHoursElement))
		{
			forecastedHours.put("forecastedHours", Float.valueOf(forecastedHoursElement.getText().replace(",", "").split("\n")[3]));
			forecastedHours.put("forecastedActualHours", Float.valueOf(forecastedHoursElement.getText().replace(",", "").split("\n")[4]));
		}
		return forecastedHours;
	}
	
	
	@Override
	public HashMap<String, Float> getScheduleHours() throws Exception {
		HashMap<String, Float> forecastedHours = new HashMap<String, Float>();
		if(isElementLoaded(scheduleHoursElement))
		{
			forecastedHours.put("initialScheduleHours", Float.valueOf(scheduleHoursElement.getText().replace(",", "").split("\n")[3]));
			forecastedHours.put("publishedScheduleHours", Float.valueOf(scheduleHoursElement.getText().replace(",", "").split("\n")[4]));
		}
		return forecastedHours;
	}

	
	@Override
	public void navigateToNextWeek() throws Exception {
		if(isElementLoaded(navigationArrowRight))
		{
			click(navigationArrowRight);
			SimpleUtils.pass("Analytics: Navigated to next week Successfully!");
		}
		else {
			SimpleUtils.fail("Analytics: Navigated to next week arrow not loaded!", false);
		}
		
	}

	@Override
	public String getAnalyticsActiveDuration() {
		String duration = "";
		if(analyticsCalendarDays.size() != 0)
		{
			duration = analyticsCalendarDays.get(0).getText().replace("\n", " ") + " - " + analyticsCalendarDays.get(analyticsCalendarDays.size() - 1).getText().replace("\n", " ");
		}
		return duration;
	}

}
