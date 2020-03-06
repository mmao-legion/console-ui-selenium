package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.SchedulePage;
import com.legion.utils.SimpleUtils;

import cucumber.api.java.hu.Ha;
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
	
	@FindBy(css="div[ng-if*='daySummary($index)) && weeklyScheduleData($index) && canViewGuidance()']")
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

	@FindBy (css = "div.col-sm-8.text-left")
	private WebElement todaysForecast;
	
	@FindBy (css = "div.col-sm-4.text-left")
	private WebElement startingSoon;

	@FindBy (css = "div.header-avatar")
	private WebElement iconProfile;

	@FindBy (css = "li[ng-if='canShowTimeoffs']")
	private WebElement timeOffLink;

	@FindBy (css = "div.header-company-icon")
	private WebElement iconImage;

	@FindBy (css = ".col-sm-6.text-right")
	private WebElement currentTime;

	@FindBy (css = "div.fx-center.welcome-text h1")
	private WebElement detailWelcomeText;

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
    		SimpleUtils.pass("Today's ForeCast Labels loaded Successfully on Dashboard!");
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
    		SimpleUtils.fail("Dashboard Page 'Upcoming Shift Container' Section not Loaded Successfully!",true);
    	
    	/*
    	 *  Check whether 'Today's Forecast' Section appear or not on Dashboard.
    	 */
    	if(isElementLoaded(dashboardTodaysForecastSection))
    		SimpleUtils.pass("Dashboard Page 'Today's Forecast' Section Loaded Successfully!");
    	else
    		SimpleUtils.fail("Dashboard Page 'Today's Forecast' Section not Loaded Successfully!",true);
    	
    	/*
    	 *  Check whether 'Projected Demand Graph' Section appear or not on Dashboard.
    	 */
    	if(isElementLoaded(dashboardProjectedDemandGraph))
    		SimpleUtils.pass("Dashboard Page 'Projected Demand Graph' Section Loaded Successfully!");
    	else
    		SimpleUtils.fail("Dashboard Page 'Projected Demand Graph' Section not Loaded Successfully!",true);

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

	
	@FindBy(css = "div.forecast.row-fx")
	private List<WebElement> forecastDataElements;
	@Override
	public ArrayList<HashMap<String, Float>> getDashboardForeCastDataForAllLocation() {
		waitForSeconds(4);
		ArrayList<HashMap<String, Float>> forecastDataForAllLocations = new ArrayList<HashMap<String, Float>>();
		if(forecastDataElements.size() != 0)
		{
			for(WebElement forecastData: forecastDataElements)
			{
				HashMap<String, Float> locationHours = new HashMap<String, Float>();
				List<WebElement> forecastHours = forecastData.findElements(By.cssSelector("[ng-if=\"!forecastOnly\"]"));
				if(forecastHours.size() != 0)
				{
					for(WebElement forecastHour : forecastHours)
					{
						String[] forecastHoursString = forecastHour.getText().replace("\n", " ").split("Hrs");
						if(forecastHoursString.length > 1)
						{
							
							float hours = Float.valueOf(forecastHoursString[0].trim());
							String hoursType = forecastHoursString[1].trim();
							locationHours.put(hoursType, hours);
						}
					}	
					forecastDataForAllLocations.add(locationHours);
				}
				else {
					SimpleUtils.fail("Dashboard Data Forecast Hours not loaded.", false);
				}
			}
		}
		else {
			SimpleUtils.fail("Dashboard Data Forecast not loaded.", false);
		}
		return forecastDataForAllLocations;
	}

	@FindBy(css = "div.forecast")
	private WebElement todaysForecastDataDiv;
	
	@Override
	public HashMap<String, Float> getTodaysForcastData() throws Exception
	{
		HashMap<String, Float> todaysForcastData = new HashMap<String, Float>();
		String demandForecastLabel = "Demand Forecast";
		String guidanceHoursLabelGuidance = "Guidance";
		String guidanceHoursLabelBudget = "Budget";
		String scheduledHoursLabel = "Scheduled";
		String otherHoursLabel = "Other";
		if(isElementLoaded(todaysForecastDataDiv))
		{
			String[] todaysForecastString = todaysForecastDataDiv.getText().split("\n");
			if(todaysForecastString[1].toLowerCase().contains(demandForecastLabel.toLowerCase()))
				todaysForcastData.put("demandForecast" , Float.valueOf(todaysForecastString[0].split(" ")[0]));
			else
				SimpleUtils.fail("Dashboard Page: Unable to fetch Demand Forecast data.", true);

			if(todaysForecastString[3].toLowerCase().contains(guidanceHoursLabelGuidance.toLowerCase()) || todaysForecastString[3].toLowerCase().contains(guidanceHoursLabelBudget.toLowerCase()))
				todaysForcastData.put("guidanceHours" , Float.valueOf(todaysForecastString[2].split(" ")[0]));
			else
				SimpleUtils.fail("Dashboard Page: Unable to fetch Guidance Hours.", true);

			if(todaysForecastString[5].toLowerCase().contains(scheduledHoursLabel.toLowerCase())) 
				todaysForcastData.put("scheduledHours" , Float.valueOf(todaysForecastString[4].split(" ")[0]));
			else
				SimpleUtils.fail("Dashboard Page: Unable to fetch schedule Hours.", true);

			if(todaysForecastString[7].toLowerCase().contains(otherHoursLabel.toLowerCase())) 
				todaysForcastData.put("otherHours" , Float.valueOf(todaysForecastString[6].split(" ")[0]));
			else
				SimpleUtils.fail("Dashboard Page: Unable to fetch Other Hours.", true);
		}
		else
		{
			SimpleUtils.fail("Dashboard Page: Today's Forecast not loaded.", false);
		}
		return todaysForcastData;
	}

	//added by Nishant

	public void clickOnProfileIconOnDashboard() throws Exception {
		if(isElementEnabled(iconProfile,5)){
			click(iconProfile);
			SimpleUtils.pass("Able to click on profile icon Successfully!!");
		}else{
			SimpleUtils.fail("Profile icon is not clickable",false);
		}
	}

	public void clickOnTimeOffLink() throws Exception {
		if(isElementEnabled(timeOffLink,5)){
			click(timeOffLink);
			SimpleUtils.pass("Able to click on time off link Successfully!!");
			if(isElementEnabled(iconImage,5)){
				click(iconImage);
			}
		}else{
			SimpleUtils.fail("Time Off is not clickable",false);
		}
	}

	@Override
	public void verifyTheWelcomeMessage(String userName) throws Exception {
		String greetingTime = getTimePeriod(currentTime.getText());
		String expectedText = "Good " + greetingTime + ", " + userName + "." + "\n" + "Welcome to Legion" + "\n" + "Your Complete Workforce Engagement Solution";
		String actualText = "";
		if(isElementLoaded(detailWelcomeText, 5)){
			actualText = detailWelcomeText.getText();
			if(actualText.equals(expectedText)){
				SimpleUtils.pass("Verified Welcome Text is as expected!");
			}else{
				SimpleUtils.fail("Verify Welcome Text failed! Expected is: " + expectedText + "\n" + "Actual is: " + actualText, true);
			}
		}
		else{
			SimpleUtils.fail("Welcome Text Section doesn't Load successfully!", true);
		}
	}

	@FindBy (css = "div.col-sm-6.text-left")
	private WebElement currentDate;
	@FindBy (css = "div.forecast>div:nth-child(2)")
	private WebElement budgetSection;
	@FindBy (css = "div.forecast>div:nth-child(3)")
	private WebElement scheduledSection;
	@FindBy (css = "div.forecast>div:nth-child(4)")
	private WebElement otherSection;
	@FindBy (css = "div#curved-graph0 svg")
	private WebElement projectedDemand;
	@FindBy (className = "no-shifts-message")
	private WebElement noShiftMessage;
	@FindBy (css = "div.upcoming-shift")
	private List<WebElement> upComingShifts;
	@FindBy (css = "h4.title-blue.text-left")
	private WebElement startingSoonTitle;

	@Override
	public String getCurrentDateFromDashboard() throws Exception {
		if (isElementLoaded(currentDate, 5)){
			return currentDate.getText();
		}else{
			return null;
		}
	}

	@Override
	public HashMap<String, String> getHoursFromDashboardPage() throws Exception {
		HashMap<String, String> scheduledHours = new HashMap<>();
		if (isElementLoaded(budgetSection, 5) && isElementLoaded(scheduledSection, 5)
				&& isElementLoaded(otherSection, 5)) {
			List<WebElement> guidanceElements = budgetSection.findElements(By.tagName("span"));
			List<WebElement> scheduledElements = scheduledSection.findElements(By.tagName("span"));
			List<WebElement> otherElements = otherSection.findElements(By.tagName("span"));
			if (guidanceElements != null && scheduledElements != null && otherElements != null) {
				if (guidanceElements.size() == 3 && scheduledElements.size() == 3 && otherElements.size() == 3) {
					scheduledHours.put(guidanceElements.get(2).getText(), guidanceElements.get(0).getText());
					scheduledHours.put(scheduledElements.get(2).getText(), scheduledElements.get(0).getText());
					scheduledHours.put(otherElements.get(2).getText(), otherElements.get(0).getText());
					SimpleUtils.pass("Get Budget, Scheduled, Other hours Successfully!");
				} else {
					SimpleUtils.fail("Element size is incorrect!", true);
				}
			}
		}else {
			SimpleUtils.fail("Failed to find the elements!", true);
		}
		return scheduledHours;
	}

	@Override
	public boolean isProjectedDemandGraphShown() throws Exception {
		boolean isShown = false;
		if (isElementLoaded(projectedDemand, 15)) {
			List<WebElement> g = projectedDemand.findElements(By.tagName("g"));
			WebElement path = projectedDemand.findElement(By.tagName("path"));
			if (g != null && g.size() > 0 && path != null) {
				isShown = true;
				SimpleUtils.pass("Projected Demand Graph shows!");
			}else{
				SimpleUtils.fail("Projected Demand Graph failed to show!", false);
			}
		}else {
			SimpleUtils.fail("Project Demand section failed to show!", false);
		}
		return isShown;
	}

	@Override
	public boolean isStartingSoonLoaded() throws Exception {
		if (isElementLoaded(noShiftMessage, 10)) {
			return false;
		}else {
			if (areListElementVisible(upComingShifts, 10)){
				return true;
			}else {
				return false;
			}
		}
	}

	@Override
	public void verifyStartingSoonNScheduledHourWhenGuidanceOrDraft(boolean isStartingSoonLoaded, String scheduledHour) throws Exception {
		if (!isStartingSoonLoaded) {
			SimpleUtils.pass("Starting soon shifts are not shown when schedule is Guidance or draft.");
		}else {
			SimpleUtils.fail("Starting soon shifts should not show when schedule is Guidance or draft.", true);
		}
		if (scheduledHour.equals("0")) {
			SimpleUtils.pass("Scheduled hour is 0 when schedule is Guidance or draft.");
		}else {
			SimpleUtils.fail("Scheduled hour should be 0 when schedule is Guidance or draft, but the actual is: " + scheduledHour, true);
		}
	}

	@Override
	public HashMap<String, String> getUpComingShifts() throws Exception {
		HashMap<String, String> shifts = new HashMap<>();
		String name = null;
		String role = null;
		if (areListElementVisible(upComingShifts, 15)) {
			for (WebElement upComingShift : upComingShifts) {
				name = upComingShift.findElement(By.cssSelector("span.name-muted")).getText();
				role = upComingShift.findElement(By.cssSelector("span.role-name")).getText();
				shifts.put(name, role);
			}
		}else {
			SimpleUtils.fail("Up Coming shifts failed to load!", true);
		}
		return shifts;
	}

	@Override
	public boolean isStartingTomorrow() throws Exception {
		String tomorrow = "Starting tomorrow";
		boolean isTomorrow = false;
		if (isElementLoaded(startingSoonTitle, 10)) {
			if (tomorrow.equals(startingSoonTitle.getText())){
				isTomorrow = true;
			}
		}
		return isTomorrow;
	}

	private String getTimePeriod(String date) throws Exception {
		String timePeriod = "";
		int pmHour = 12;
		final String pm = "pm";
		final String am = "am";
		try {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date now = format.parse(date);
			int hour = now.getHours();
			if (date.endsWith(pm) && hour != pmHour) {
				hour += pmHour;
			}
			if (date.endsWith(am) && hour == pmHour){
				hour -= pmHour;
			}
			if (hour >= 5 && hour <= 11) {
				timePeriod = "morning";
			} else if (hour >= 12 && hour < 19) {
				timePeriod = "afternoon";
			} else if (hour >= 19 && hour < 22) {
				timePeriod = "evening";
			} else {
				timePeriod = "night";
			}
		}catch(Exception e) {
			SimpleUtils.fail("Get Time Period failed!", true);
		}
		return timePeriod;
	}
}
