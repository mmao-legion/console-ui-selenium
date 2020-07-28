package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.SimpleUtils.getCurrentDateMonthYearWithTimeZone;

import java.text.SimpleDateFormat;
import java.util.*;

import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.SchedulePage;
import com.legion.utils.JsonUtil;
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

	@FindBy (css = "[ng-src*='t-m-time-offs']")
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
    	clickTheElement(goToTodayScheduleButton);
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
	public boolean isViewMySchedulePresentAndClickable() throws Exception {
		boolean isPresentAndClickable = false;
		if (isElementLoaded(goToTodayScheduleButton, 5) && isClickable(goToTodayScheduleButton, 5)) {
			isPresentAndClickable = true;
		}
		return isPresentAndClickable;
	}

	@Override
	public String getCurrentDateFromDashboard() throws Exception {
		if (isElementLoaded(currentDate, 5)){
			return currentDate.getText();
		}else{
			return null;
		}
	}

	@Override
	public String getCurrentTimeFromDashboard() throws Exception {
		if (isElementLoaded(currentTime, 5)){
			return currentTime.getText();
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
				SimpleUtils.fail("Projected Demand Graph failed to show!", true);
			}
		}else {
			SimpleUtils.fail("Project Demand section failed to show!", true);
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
			SimpleUtils.warn("Starting soon shifts should not show when schedule is Guidance or draft. This is blocked by bug: https://legiontech.atlassian.net/browse/LEG-8474 : " +
					"When schedule of Current week is in Guidance, still data is showing on Dashboard");
		}
		if (scheduledHour.equals("0")) {
			SimpleUtils.pass("Scheduled hour is 0 when schedule is Guidance or draft.");
		}else {
			SimpleUtils.warn("Scheduled hour should be 0 when schedule is Guidance or draft, but the actual is: " + scheduledHour +
					" This is blocked by bug: https://legiontech.atlassian.net/browse/LEG-8474 : When schedule of Current week is in Guidance, still data is showing on Dashboard");
		}
	}

	@Override
	public HashMap<String, String> getUpComingShifts() throws Exception {
		HashMap<String, String> shifts = new HashMap<>();
		String name = null;
		String role = null;
		if (areListElementVisible(upComingShifts, 15)) {
			for (WebElement upComingShift : upComingShifts) {
				name = upComingShift.findElement(By.cssSelector("span.name-muted")).getText().toLowerCase();
				role = upComingShift.findElement(By.cssSelector("span.role-name")).getText().toLowerCase();
				shifts.put(name, role);
			}
		}else {
			SimpleUtils.report("Up Coming shifts are not loaded!");
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

	//Added by Julie
	@FindBy( css = ".col-sm-4 > .header-company-icon > img")
	private WebElement companyIconImg;

	@FindBy(css = ".user-profile-section__title.ng-binding")
	private List<WebElement> userProfileSection;

	@FindBy(css = "div.console-navigation-item-label.Schedule")
	private WebElement scheduleConsoleNameInTM;

	@FindBy(css = "[ng-show*=\"showLocation()\"]")
	private WebElement showLocation;

	@FindBy(css = "lg-picker-input > div > input-field > ng-form > div")
	private WebElement currentLocation;

	@FindBy(css = ".lg-search-options__option")
	private List<WebElement> allLocations;

	@FindBy(css = ".upcoming-shift")
	private List<WebElement> upcomingShifts;

	@FindBy(css = "li[ng-click^='goToProfile']")
	private List<WebElement> goToProfile;

	@FindBy(css = ".lgn-alert-modal")
	private WebElement alertDialog;

	@FindBy(css = ".lgn-action-button-success")
	private WebElement OKButton;

	@FindBy(css = ".quick-profile")
	private WebElement personalDetails;

	@FindBy(css = ".quick-engagement")
	private WebElement engagementDetails;

	@FindBy(xpath = "//span[contains(text(),'My Shift Preferences')]")
	private WebElement myShiftPreferences;

	@FindBy(xpath = "//span[contains(text(),'My Availability')]")
	private WebElement myAvailability;

	@FindBy(css = ".week-nav-icon-main")
	private WebElement currentWeek;

	@FindBy(css = ".timeoff-requests")
	private List<WebElement> timeoffRequests;

	@FindBy(css = ".col-sm-5 .count-block-pending")
	private WebElement pending;

	@FindBy(css = ".col-sm-5 .count-block-approved")
	private WebElement approved;

	@FindBy(css = ".col-sm-5 .count-block-rejected")
	private WebElement rejected;

	private static HashMap<String, String> propertyLocationTimeZone = JsonUtil.getPropertiesFromJsonFile("src/test/resources/LocationTimeZone.json");

	@Override
	public void validateTMAccessibleTabs() throws Exception {
		if (isElementLoaded(dashboardConsoleName, 5) && isElementLoaded(scheduleConsoleNameInTM, 5)) {
			if (isElementEnabled(dashboardConsoleName, 5) && isElementEnabled(scheduleConsoleNameInTM, 5)) {
				SimpleUtils.pass("Dashboard and Schedule tabs are accessible successfully");
				for (WebElement consoleMenu : consoleNavigationMenuItems) {
					if (consoleMenu.getAttribute("class").contains("ng-hide")) {
						SimpleUtils.assertOnFail(" This console is also enabled, which is not expected in TM view", consoleMenu.isEnabled(), true);
					}
				}
			} else {
				SimpleUtils.fail("Dashboard and Schedule tabs are disabled", true);
			}
		} else {
			SimpleUtils.fail("Dashboard and Schedule tabs failed to load", true);
		}
	}

	@Override
	public void validateThePresenceOfLocation() throws Exception {
		if (isElementEnabled(showLocation, 20)) {
			if (currentLocation.isDisplayed() && !currentLocation.getText().isEmpty() && currentLocation.getText() != null) {
				if (getDriver().findElement(By.xpath("//header//*[@class=\"location\"]")).equals(showLocation)) {
					SimpleUtils.pass("Dashboard Page: Location shows at top of the page successfully");
				} else {
					SimpleUtils.fail("Dashboard Page: Location is not at top of the page", true);
				}
			} else {
				SimpleUtils.fail("Dashboard Page: Location isn't present", true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Location failed to load", true);
		}
	}

	@Override
	public void validateTheAccessibleLocation() throws Exception {
		if (areListElementVisible(allLocations, 10)) {
			if (allLocations.size() > 1) {
				click(currentLocation);
				for (int i = 0; i < allLocations.size(); i++) {
					if (allLocations.get(i).isEnabled()) {
						try {
							if (allLocations.get(i).getText().contains(currentLocation.getText())) {
								SimpleUtils.pass("Dashboard Page: " + currentLocation.getText() + " is accessible successfully");
								continue;
							} else {
								SimpleUtils.pass("Dashboard Page: " + allLocations.get(i).getText() + " is accessible successfully");
								click(allLocations.get(i));
							}
						} catch (Exception e) {
							SimpleUtils.fail("Dashboard Page: Exception occurs when clicking location", true);
						}
						if (i != allLocations.size() - 1)
							click(currentLocation);
					} else {
						SimpleUtils.fail("Dashboard Page: " + allLocations.get(i).getText() + " isn't accessible", true);
					}
				}
			} else {
				SimpleUtils.report("Dashboard Page: No more locations are accessible");
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Locations failed to load", true);
		}
	}

	@Override
	public void validateThePresenceOfLogo() throws Exception {
		if (isElementLoaded(companyIconImg, 5)) {
			if (companyIconImg.isDisplayed()) {
				if (getDriver().findElement(By.xpath("//header//div[contains(@class,'text-right')]/div[1]/img")).equals(companyIconImg)) {
					SimpleUtils.pass("Dashboard Page: Logo is present at right corner of page successfully");
				} else {
					SimpleUtils.fail("Dashboard Page: Logo isn't present at right corner of page", true);
				}
			} else {
				SimpleUtils.fail("Dashboard Page: Company logo failed to display", true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Company logo failed to load", true);
		}
	}

	@Override
	public void validateDateAndTimeAfterSelectingDifferentLocation() throws Exception {
		String dateFromLocation = getDateFromTimeZoneOfLocation("EEEE, MMMM d h:mm a");
		String dateFromDashboard = getCurrentDateFromDashboard() + " " + currentTime.getText().toUpperCase();
		if (dateFromDashboard.equals(dateFromLocation)) {
			SimpleUtils.pass("Dashboard Page: The date and time on Dashboard is consistent with the timezone of current location");
		} else {
			SimpleUtils.fail("Dashboard Page: The date and time on Dashboard is different from the timezone of the current location", true);
		}

		click(currentLocation);
		if (allLocations.size() > 1) {
			for (int i = 0; i < allLocations.size(); i++) {
				if (currentLocation.getText().equals(allLocations.get(i).getText())) {
					continue;
				} else {
					click(allLocations.get(i));
					SimpleUtils.pass("Dashboard Page: Another location is selected successfully");
					break;
				}
			}
			String dateFromAnotherLocation = getDateFromTimeZoneOfLocation("EEEE, MMMM d h:mm a");
			String dateFromAnotherDashboard = getCurrentDateFromDashboard() + " " + currentTime.getText().toUpperCase();
			if (dateFromAnotherDashboard.equals(dateFromAnotherLocation)) {
				SimpleUtils.pass("Dashboard Page: The date and time on Dashboard is consistent with the timezone of another location");
			} else {
				SimpleUtils.fail("Dashboard Page: The date and time on Dashboard is different from the timezone of another location", true);
			}
		} else {
			SimpleUtils.report("Dashboard Page: No more locations can be selected");
		}
	}

	@Override
	public void validateTheVisibilityOfUsername(String userName) throws Exception {
		if (isElementLoaded(dashboardWelcomeSection, 5)) {
			if (dashboardWelcomeSection.getText().contains(userName)) {
				if (dashboardWelcomeSection.getAttribute("class").contains("center")) {
					SimpleUtils.pass("Dashboard Page: Username shows in center of the page successfully");
				} else {
					SimpleUtils.fail("Dashboard Page: Username doesn't show in center of the page", true);
				}
			} else {
				SimpleUtils.fail("Dashboard Page: Username doesn't show in the dashboard page", true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Welcome Text Section doesn't Load successfully!", true);
		}
	}

	@Override
	public void validateDateAndTime() throws Exception {
		if (isElementLoaded(currentDate, 10) && isElementLoaded(currentTime, 10)) {
			SimpleUtils.pass("Current date and time are loaded successfully");
			String dateFromDashboard = getCurrentDateFromDashboard() + " " + currentTime.getText().toUpperCase();
			String dateFromLocation = getDateFromTimeZoneOfLocation("EEEE, MMMM d h:mm a");
			if (dateFromDashboard.equals(dateFromLocation)) {
				SimpleUtils.pass("Date and time shows according to the US(Particular location) timing successfully");
			} else {
				SimpleUtils.fail("The date and time on Dashboard is different from the current location", true);
			}
		} else {
			SimpleUtils.fail("Current date and time failed to load", true);
		}
	}

	@Override
	public void validateTheUpcomingSchedules(String userName) throws Exception {
		if (isElementLoaded(dashboardUpcomingShiftContainer, 20)) {
			SimpleUtils.pass("Today's published Shifts loaded Successfully on Dashboard!");
			if (dashboardUpcomingShiftContainer.getText().contains("No Published Shifts for today")) {
				SimpleUtils.pass("No Published Shifts for today");
			} else {
				for (WebElement us : upcomingShifts) {
					if (us.getText().contains(userName) && us.getText().contains("am") || us.getText().contains("pm")) {
						SimpleUtils.pass("All the upcoming schedules are present with shift timings successfully");
					} else {
						SimpleUtils.fail("Shifts don't display on Dashboard", true);
					}
				}
			}
		} else {
			SimpleUtils.fail("Today's Published Shifts failed to load on Dashboard!", true);
		}
	}

	@Override
	public void validateVIEWMYSCHEDULEButtonClickable() throws Exception {
		if (isElementLoaded(goToTodayScheduleButton, 10)) {
			click(goToTodayScheduleButton);
			waitForSeconds(2);
			for (WebElement consoleMenu : consoleNavigationMenuItems) {
				if (consoleMenu.getAttribute("class").contains("active") && consoleMenu.getText().equals("Schedule")) {
					SimpleUtils.pass("Click on the [VIEW MY SCHEDULE] button, it redirects to Schedule page successfully");
					break;
				}
			}
		} else {
			SimpleUtils.fail("'VIEW MY SCHEDULE' button failed to load", true);
		}
	}

	@Override
	public void validateTheVisibilityOfProfilePicture() throws Exception {
		if (isElementLoaded(iconProfile, 5)) {
			if (iconProfile.isDisplayed()) {
				if (getDriver().findElement(By.xpath("//header//div[contains(@class,'text-right')]/div[2]")).equals(iconProfile)) {
					SimpleUtils.pass("Profile picture is visible at right corner of the page successfully");
				} else {
					SimpleUtils.fail("Profile picture isn't visible at right corner of the page", true);
				}
			} else {
				SimpleUtils.fail("Profile picture failed to display", true);
			}
		} else {
			SimpleUtils.fail("Profile picture failed to load", true);
		}
	}

	@Override
	public void validateProfilePictureIconClickable() throws Exception {
		clickOnProfileIconOnDashboard();
		if (areListElementVisible(goToProfile, 10)) {
			if (goToProfile.size() != 0) {
				SimpleUtils.pass("Profile Page: Dropdown list opens after clicking on profile picture icon");
			} else {
				SimpleUtils.fail("Profile Page: Dropdown list doesn't open after clicking on profile picture icon", true);
			}
		} else {
			SimpleUtils.fail("Profile Page: Dropdown list failed to load", true);
		}
	}

	@Override
	public void validateTheVisibilityOfProfile() throws Exception {
		clickOnProfileIconOnDashboard();
		if (areListElementVisible(goToProfile, 10)) {
			if (goToProfile.size() == 3) {
				SimpleUtils.pass("Profile Page: Dropdown list has three rows successfully");
			} else {
				SimpleUtils.fail("Profile Page: Dropdown list doesn't have three rows", true);
			}
			for (WebElement e : goToProfile) {
				if (e.getText().trim().equals("My Profile") || e.getText().trim().equals("My Work Preferences") || e.getText().trim().equals("My Time Off")) {
					SimpleUtils.pass("Profile Page: Dropdown list includes " + e.getText());
				} else {
					SimpleUtils.fail("Profile Page: " + e.getText() + " isn't expected in dropdown list", true);
				}
			}
		} else {
			SimpleUtils.fail("Profile Page: Dropdown list failed to load", true);
		}
	}

	@Override
	public void validateProfileDropdownClickable() throws Exception {
		if (areListElementVisible(goToProfile, 10) && goToProfile.size() != 0) {
			for (int i = 0; i < goToProfile.size(); i++) {
				click(goToProfile.get(i));
				SimpleUtils.pass("Profile Page: " + goToProfile.get(i).getText() + " is clickable successfully");
				if (isElementLoaded(alertDialog, 5)) {
					click(OKButton);
					clickOnProfileIconOnDashboard();
				}
			}
		} else {
			SimpleUtils.fail("Profile Page: Dropdown list failed to load", true);
		}
	}

	@Override
	public void validateTheDataOfMyProfile() throws Exception {
		clickOnSubMenuOnProfile("My Profile");
		if (isElementLoaded(personalDetails, 20) && isElementLoaded(engagementDetails, 20)) {
			if (personalDetails.isDisplayed() && engagementDetails.isDisplayed())
				SimpleUtils.pass("My Profile: It shows the TM's personal and engagement details successfully");
		} else {
			SimpleUtils.fail("My Profile: Failed to show the TM's personal and engagement", true);
		}
	}

	@Override
	public void clickOnSubMenuOnProfile(String subMenu) throws Exception {
		clickOnProfileIconOnDashboard();
		if (areListElementVisible(goToProfile,10) && goToProfile.size() != 0 ) {
			for(WebElement e : goToProfile) {
				if(e.getText().toLowerCase().contains(subMenu.toLowerCase())) {
					click(e);
					if (isElementLoaded(alertDialog, 5))
						click(OKButton);
					else click(companyIconImg);
					SimpleUtils.pass("Able to click on '"+ subMenu+"' link Successfully!!");
					break;
				}
			}
		} else {
			SimpleUtils.fail("'"+subMenu+ "' failed to load", true);
		}
	}

	@Override
	public void validateTheDataOfMyWorkPreferences(String date) throws Exception {
		SimpleUtils.report(date);
		clickOnSubMenuOnProfile("My Work Preferences");
		if (areListElementVisible(userProfileSection, 10) && userProfileSection.size() == 3) {
			SimpleUtils.pass("My Work Preferences: It shows the Availability,  Availability Change Requests and Shift Preferences successfully");
			if(date.contains(",") && date.contains(" ")) {
				date = date.split(",")[1].trim().split(" ")[1];
				SimpleUtils.report("Current date is " + date);
			}
			//currentWeek.getText() is Apr 6-Apr 12
			String weekDefaultEnd = "";
			String weekDefaultBegin = "";
			if (currentWeek.getText().contains("-") && currentWeek.getText().contains(" ")) {
				try {
					weekDefaultBegin = currentWeek.getText().split("-")[0].split(" ")[1];
					SimpleUtils.report("weekDefaultBegin is:" + weekDefaultBegin);
					weekDefaultEnd = currentWeek.getText().split("-")[1].split(" ")[1];
					SimpleUtils.report("weekDefaultEnd is:" + weekDefaultEnd);
				} catch (Exception e) {
					SimpleUtils.fail("Active week text doesn't have enough length", true);
				}
			}
			if ((Integer.parseInt(weekDefaultBegin) <= Integer.parseInt(date) && Integer.parseInt(date) <= Integer.parseInt(weekDefaultEnd))
					|| (Integer.parseInt(date) <= Integer.parseInt(weekDefaultEnd) && (weekDefaultBegin.length() == 2 && date.length() == 1))
					|| (Integer.parseInt(date) >= Integer.parseInt(weekDefaultBegin) && (weekDefaultBegin.length() == 2 && date.length() == 2))) {
				SimpleUtils.pass("My Work Preferences: Current week availability shows by default successfully");
			} else
				SimpleUtils.fail("My Work Preferences: Current week availability shows incorrectly", true);
		} else {
			SimpleUtils.fail("Failed to show the Availability and Shift Preferences on My Work Preferences", true);
		}
	}

	@Override
	public void validateTheDataOfMyTimeOff() throws Exception {
		clickOnSubMenuOnProfile("My Time Off");
		if (isElementLoaded(pending, 10) && isElementLoaded(approved, 10) && isElementLoaded(rejected, 10)) {
			SimpleUtils.pass("A summary of all pending, approved and rejected shows successfully on My Time Off");
			if (Integer.valueOf(pending.getText().substring(0, 1)) != 0 || Integer.valueOf(approved.getText().substring(0, 1)) != 0 || Integer.valueOf(rejected.getText().substring(0, 1)) != 0) {
				if (areListElementVisible(timeoffRequests, 10) && timeoffRequests.size() > 1 ) {
					SimpleUtils.pass("My Time Off: All the leaves of employee are visible successfully");
				} else {
					SimpleUtils.fail("My Time Off: No leaves of employee are visible", true);
				}
			} else {
				SimpleUtils.report("My Time Off: All the leaves of employee aren't visible since request count is 0");
			}
		} else {
			SimpleUtils.fail("My Time Off: A summary of all pending, approved and rejected doesn't show", true);
		}
	}

	@Override
	public String getDateFromTimeZoneOfLocation(String pattern) throws Exception {
		String dateFromTimeZone = "";
		if (isElementLoaded(currentLocation, 10)) {
			String jsonTimeZone = propertyLocationTimeZone.get(currentLocation.getText().trim());
			if (jsonTimeZone != null && !jsonTimeZone.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				dateFromTimeZone = getCurrentDateMonthYearWithTimeZone(jsonTimeZone,sdf);
			} else {
				SimpleUtils.fail("Current timezone doesn't exist, please check your location", false);
			}
		} else {
			SimpleUtils.fail("Current location failed to load", false);
		}
		return dateFromTimeZone;
	}

	//Added by Julie
	@FindBy(css = "[ng-click=\"switchView()\"]")
	private WebElement switchToEmployeeView;

	@Override
	public boolean isSwitchToEmployeeViewPresent() throws Exception {
		if (isElementLoaded(switchToEmployeeView, 10))
			return true;
		else
			return false;
	}

	@Override
	public void clickOnSwitchToEmployeeView() throws Exception {
		if (isElementLoaded(switchToEmployeeView, 10)) {
			click(switchToEmployeeView);
			SimpleUtils.pass("Click on Switch To Employee View Successfully!");
		} else {
			SimpleUtils.fail("Switch To Employee View not Loaded!", true);
		}
	}
}
