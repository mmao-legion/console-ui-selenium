package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.util.HashMap;
import java.util.List;

import com.aventstack.extentreports.Status;
import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.SchedulePage;
import com.legion.utils.SimpleUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class ConsoleDashboardPage extends BasePage implements DashboardPage {

	@FindBy(css="[ng-click='openSchedule()']")
	private WebElement goToTodayScheduleButton;
	
	@FindBy(css="[ng-class = 'subNavigationViewLinkActiveClass(view)']")
	private WebElement goToTodayScheduleView;
	
	@FindBy(css="[ng-if=\"scheduleForToday($index) && !scheduleForToday($index).length\"]")
	private WebElement publishedShiftForTodayDiv;
	
	@FindBy(css="[ng-if=\"daySummary($index) && weeklyScheduleData($index) && canViewGuidance()\"]")
	private WebElement dashboardTodaysForecastDiv;
	
	@FindBy(css="[ng-if=\"canViewProjectedSales()\"]")
	private WebElement dashboardTodaysProjectedDemandGraphDiv;
	
	@FindBy(className="welcome-text")
	private WebElement dashboardWelcomeSection;
	
	@FindBy(className="upcoming-shift-container")
	private WebElement dashboardUpcomingShiftContainer;
	
	@FindBy(css="[ng-if=\"daySummary($index) && weeklyScheduleData($index) && canViewGuidance()\"]")
	private WebElement dashboardTodaysForecastSection;
	
	@FindBy(css="[ng-if=\"graphData($index)\"]")
	private WebElement dashboardProjectedDemandGraph;
	
	@FindBy(className="home-dashboard")
	private WebElement dashboardSection;

	@FindBy(className="console-navigation-item")
	private List<WebElement>consoleNavigationMenuItems;

	@FindBy (css = "#legion-app navigation div:nth-child(4)")
	private WebElement scheduleConsoleName;
	
	@FindBy(className="home-dashboard")
	private WebElement legionDashboardSection;
	    
	@FindBy (css = "div.console-navigation-item-label.Dashboard")
	private WebElement dashboardConsoleName;
	
	@FindBy (css = "div.console-navigation-item-label.Controls")
	private WebElement controlsConsoleName;
	
	@FindBy (css = ".lg-location-chooser__global.ng-scope")
	private WebElement globalIconControls;
	
	@FindBy (css = ".center.ng-scope")
	private WebElement controlsPage;

    public ConsoleDashboardPage() {
    	PageFactory.initElements(getDriver(), this);
    }

    @Override
    public boolean isToday() throws Exception {

    	//boolean bol = true;
    	if(isElementLoaded(publishedShiftForTodayDiv)){
    		SimpleUtils.pass("Today's published Shifts loaded Successfully on Dashboard!");
    	}else{
    		return false;
    	}
    	if(isElementLoaded(dashboardTodaysForecastDiv)){
    		SimpleUtils.pass("Today's Fore Cast Labels loaded Successfully on Dashboard!");
    	}else{
    		return false;
    	}
    	if(isElementLoaded(dashboardTodaysProjectedDemandGraphDiv)){
    		SimpleUtils.pass("Today's Projected Demand Graph loaded Successfully on Dashboard!");
    	}else{
    		return false;
    	}
   
    	return true;
    }
    
    @Override
    public void verifyDashboardPageLoadedProperly() throws Exception {
    	/*
    	 *  Check whether Welcome Text Section appear or not on Dashboard.
    	 */
    	if(isElementLoaded(dashboardWelcomeSection))
    		SimpleUtils.pass("Dashboard Page Welcome Text Section Loaded Successfully!");
    	else
    		SimpleUtils.fail("Dashboard Page Welcome Text Section not loaded Successfully!",true);
    	
    	/*
    	 *  Check whether 'VIEW TODAY'S SCHEDULE' Button appear or not on Dashboard.
    	 */
    	if(isElementLoaded(goToTodayScheduleButton))
    		SimpleUtils.pass("Dashboard Page 'VIEW TODAY'S SCHEDULE' Button Loaded Successfully!");
    	else
    		SimpleUtils.fail("Dashboard Page 'VIEW TODAY'S SCHEDULE' Button not loaded Successfully!",true);
    	
    	/*
    	 *  Check whether 'Upcoming Shift Container' Section appear or not on Dashboard.
    	 */
    	if(isElementLoaded(dashboardUpcomingShiftContainer))
    		SimpleUtils.pass("Dashboard Page 'Upcoming Shift Container' Section Loaded Successfully!");
    	else
    		SimpleUtils.fail("Dashboard Page 'Upcoming Shift Container' Section Loaded Successfully!",true);
    	
    	/*
    	 *  Check whether 'Today's Forecast' Section appear or not on Dashboard.
    	 */
    	if(isElementLoaded(dashboardTodaysForecastSection))
    		SimpleUtils.pass("Dashboard Page 'Today's Forecast' Section Loaded Successfully!");
    	else
    		SimpleUtils.fail("Dashboard Page 'Today's Forecast' Section Loaded Successfully!",true);
    	
    	/*
    	 *  Check whether 'Projected Demand Graph' Section appear or not on Dashboard.
    	 */
    	if(isElementLoaded(dashboardProjectedDemandGraph))
    		SimpleUtils.pass("Dashboard Page 'Projected Demand Graph' Section Loaded Successfully!");
    	else
    		SimpleUtils.fail("Dashboard Page 'Projected Demand Graph' Section Loaded Successfully!",true);

    }

    @Override
    public SchedulePage goToToday() throws Exception {
    	waitForPageLoaded(getDriver());
    	checkElementVisibility(goToTodayScheduleButton);
    	SimpleUtils.pass("Dashboard Page Loaded Successfully!");
    	activeConsoleName = scheduleConsoleName.getText();
    	click(goToTodayScheduleButton);
        return new ConsoleSchedulePage();
    }
    
    @Override
    public SchedulePage goToTodayForNewUI() throws Exception {
    	waitForPageLoaded(getDriver());
    	checkElementVisibility(goToTodayScheduleButton);
    	SimpleUtils.pass("Dashboard Page Loaded Successfully!");
    	activeConsoleName = scheduleConsoleName.getText();
    	click(goToTodayScheduleButton);
        return new ConsoleScheduleNewUIPage();
    }
    

    public Boolean isDashboardPageLoaded() throws Exception
    {
    	if(isElementLoaded(dashboardSection))
    	{
    		SimpleUtils.pass("Dashboard loaded successfully");
    		return true;
       	}else{
    		SimpleUtils.fail("Dashboard not Loaded",false);
    		return false;
       	}
    }
    



	@Override
	public void navigateToDashboard() throws Exception {
		// TODO Auto-generated method stub
		if(isElementLoaded(dashboardConsoleName)){
			dashboardConsoleName.click();
    	}else{
    		SimpleUtils.fail("Dashboard menu in left navigation is not loaded!",false);
    	}
	}

	@Override
	public void verifySuccessfulNavToDashboardnLoading() throws Exception {
		// TODO Auto-generated method stub
		navigateToDashboard();
    	isDashboardPageLoaded();
	}

	
}
