package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.SimpleUtils.getCurrentDateMonthYearWithTimeZone;

import java.text.SimpleDateFormat;
import java.util.*;

import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.SchedulePage;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;

import cucumber.api.java.hu.Ha;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	private List<WebElement>consoleNavigationMenuItems; //fiona will using

	@FindBy (css = "#legion-app navigation div:nth-child(4)")//fiona using
	private WebElement scheduleConsoleName;
	
	@FindBy(className="home-dashboard")
	private WebElement legionDashboardSection;
	    
	@FindBy (css = "div.console-navigation-item-label.Dashboard")  //fiona will using
	private WebElement dashboardConsoleName;
	
	@FindBy (css = "div.console-navigation-item-label.Controls")//fiona using
	private WebElement controlsConsoleName;
	
	@FindBy (css = ".lg-location-chooser__global.ng-scope")
	private WebElement globalIconControls;
	
	@FindBy (css = ".center.ng-scope")//fiona using
	private WebElement controlsPage;

	@FindBy (css = "div.col-sm-8.text-left")
	private WebElement todaysForecast;
	
	@FindBy (css = "div.col-sm-4.text-left")
	private WebElement startingSoon;

	@FindBy (css = ".header-avatar>img")
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
    



	@Override//fiona using
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
			moveToElementAndClick(iconProfile);
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
	@FindBy( css = ".header-company-icon.fl-right .company-icon-img")
	private WebElement companyIconImg;

	@FindBy(css = ".user-profile-section__title.ng-binding")
	private List<WebElement> userProfileSection;

	@FindBy(css = "div.console-navigation-item-label.Schedule")
	private WebElement scheduleConsoleNameInTM;

	@FindBy(css = "[ng-show*=\"showLocation()\"]")
	private WebElement showLocation;

	@FindBy(css = "[search-hint=\"Search Location\"] input-field[placeholder=\"Select...\"] div.input-faked")
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

	@FindBy(css = "sub-content-box[box-title=\"User Profile\"]")
	private WebElement personalDetails;

	@FindBy(css = "sub-content-box[box-title=\"HR Profile Information\"]")
	private WebElement hrProfileInfo;

	@FindBy(css = "sub-content-box[box-title=\"Legion Information\"]")
	private WebElement legionInfo;

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

	@FindBy(css = "header-user-switch-menu[ng-show=\"showMenu\"]")
	private WebElement switchMenu;

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
				if (getDriver().findElement(By.xpath("//header//div[contains(@class,'text-right')]/div[2]//img[contains(@class,'company-icon-img')]")).equals(companyIconImg)) {
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
				if (getDriver().findElement(By.xpath("//header//div[contains(@class,'text-right')]/div[1]/img")).equals(iconProfile)) {
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
		if (isElementEnabled(switchMenu, 5) && switchMenu.getAttribute("class").contains("ng-hide")) {
			clickOnProfileIconOnDashboard();
		}
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
		if (isElementEnabled(switchMenu, 5) && switchMenu.getAttribute("class").contains("ng-hide")) {
			clickOnProfileIconOnDashboard();
		}
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
		if (isElementEnabled(switchMenu, 5) && switchMenu.getAttribute("class").contains("ng-hide")) {
			clickOnProfileIconOnDashboard();
		}
		if (areListElementVisible(goToProfile, 10) && goToProfile.size() != 0) {
			for (int i = 0; i < goToProfile.size(); i++) {
				clickTheElement(goToProfile.get(i));
				SimpleUtils.pass("Profile Page: " + goToProfile.get(i).getText() + " is clickable successfully");
				if (isElementLoaded(alertDialog, 5)) {
					click(OKButton);
					if (isElementEnabled(switchMenu, 5) && switchMenu.getAttribute("class").contains("ng-hide")) {
						clickOnProfileIconOnDashboard();
					}
				}
				if (isElementEnabled(switchMenu, 5) && switchMenu.getAttribute("class").contains("ng-hide")) {
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
		if (isElementLoaded(personalDetails, 20) && isElementLoaded(hrProfileInfo, 20)&& isElementLoaded(legionInfo, 20)) {
			if (personalDetails.isDisplayed() && hrProfileInfo.isDisplayed())
				SimpleUtils.pass("My Profile: It shows the TM's profile information details successfully");
		} else {
			SimpleUtils.fail("My Profile: Failed to show the TM's profile information", true);
		}
	}

	@Override
	public void clickOnSubMenuOnProfile(String subMenu) throws Exception {
		if (isElementEnabled(switchMenu, 5) && switchMenu.getAttribute("class").contains("ng-hide")) {
			clickOnProfileIconOnDashboard();
		}
		if (areListElementVisible(goToProfile,10) && goToProfile.size() != 0 ) {
			for(WebElement e : goToProfile) {
				if(e.getText().toLowerCase().contains(subMenu.toLowerCase())) {
					clickTheElement(e);
					if (isElementLoaded(alertDialog, 5))
						click(OKButton);
					else clickTheElement(companyIconImg);
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

	@FindBy(css = "div.row-fx.schedule-table-row.ng-scope")
	private List<WebElement> dashboardScheduleWeeks;

	@Override
	public List<WebElement> getDashboardScheduleWeeks() {
		if(areListElementVisible(dashboardScheduleWeeks,10)){
			return dashboardScheduleWeeks;
		}
		return dashboardScheduleWeeks;
	}

	@FindBy(css = "[ng-click=\"$ctrl.onReload(true)\"]")
	private WebElement refreshButton;


	@Override
	public void clickOnRefreshButton() throws Exception {
		if (isElementLoaded(refreshButton, 10)) {
			clickTheElement(refreshButton);
			waitForSeconds(15);
			SimpleUtils.pass("Click on Refresh button Successfully!");
		} else {
			SimpleUtils.fail("Refresh button not Loaded!", true);
		}
	}
	//added by Estelle

	@FindBy(css = "div.console-navigation-item-label.Dashboard")
	private WebElement dashboardConsoleMenu;
	@Override
	public void clickOnDashboardConsoleMenu() throws Exception {
		if(isElementLoaded(dashboardConsoleMenu))
			click(dashboardConsoleMenu);
		else
			SimpleUtils.fail("Dashboard Console Menu not loaded Successfully!", false);
	}

	//added by Estelle

	@Override
	public String getCurrentLocation() throws Exception {
		if (isElementLoaded(currentLocation,5)) {
			return   currentLocation.getText();
		}
		return null;
	}

	@FindBy(css = "[ng-if=\"$ctrl.parentLocation\"]")
	private WebElement currentDistrict;
	@Override
	public String getCurrentDistrict() throws Exception {
		if (isElementLoaded(currentDistrict,5)) {
			return   currentDistrict.getText();
		}
		return null;
	}

	@Override
	public boolean IsThereDistrictNavigationForLegionBasic() throws Exception {
		if (isElementLoaded(currentDistrict,5)) {
			return true;
		}
		return false;
	}

	@FindBy(css = "div[ng-class=\"{'lg-search-options__subLabel': !option.subLabel}\"]")
	private List<WebElement> locationsListInDashboardPage;
	@Override
	public List<String> getLocationListInDashboard() {
		click(currentLocation);
		waitForSeconds(3);
			if (locationsListInDashboardPage.size()>0) {
				List<String> locationList = new ArrayList<String>();
				for (WebElement location: locationsListInDashboardPage
					 ) {
					locationList.add(location.getText().split("\n")[0]);
				}
				return locationList;
			}
		return null;
	}

	//add by Fiona
	@FindBy(css = "lg-select[search-hint='Search Location'] label+div")
	private WebElement dmViewCurrentLocation;
	@Override
	public String getCurrentLocationInDMView() throws Exception {
		if (isElementLoaded(dmViewCurrentLocation,5)) {
			return   dmViewCurrentLocation.getText();
		}
		return null;
	}

	@FindBy(css = "img[class=\"widgetMainImage\"]")
	private WebElement legionLogoImg;

	@Override
	public boolean isLegionLogoDisplay() throws Exception {
		boolean isLegionLogoDisplay = false;
		try{
			if(isElementLoaded(legionLogoImg, 5)) {
				isLegionLogoDisplay = true;
				SimpleUtils.report("Legion logo is loaded Successfully!");
			} else
				SimpleUtils.report("Legion logo not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isLegionLogoDisplay;
	}


	@Override
	public boolean isDashboardConsoleMenuDisplay() throws Exception {
		boolean isDashboardConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(dashboardConsoleMenu, 5)) {
				isDashboardConsoleMenuDisplay = true;
				SimpleUtils.report("Dashboard Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Dashboard Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isDashboardConsoleMenuDisplay;
	}

	@FindBy(css = "div.console-navigation-item-label.Team")
	private WebElement teamConsoleMenu;
	@Override
	public boolean isTeamConsoleMenuDisplay() throws Exception {
		boolean isTeamConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(teamConsoleMenu, 5)) {
				isTeamConsoleMenuDisplay = true;
				SimpleUtils.report("Team Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Team Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isTeamConsoleMenuDisplay;
	}

	@FindBy(css = "div.console-navigation-item-label.Schedule")
	private WebElement scheduleConsoleMenu;

	@Override
	public boolean isScheduleConsoleMenuDisplay() throws Exception {
		boolean isScheduleConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(scheduleConsoleMenu, 5)) {
				isScheduleConsoleMenuDisplay = true;
				SimpleUtils.report("Schedule Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Schedule Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isScheduleConsoleMenuDisplay;
	}


	@FindBy(css = "div.console-navigation-item-label.Analytics")
	private WebElement analyticsConsoleMenu;

	@Override
	public boolean isAnalyticsConsoleMenuDisplay() throws Exception {
		boolean isAnalyticsConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(analyticsConsoleMenu, 5)) {
				isAnalyticsConsoleMenuDisplay = true;
				SimpleUtils.report("Analytics Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Analytics Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isAnalyticsConsoleMenuDisplay;
	}


	@FindBy(css = "div.console-navigation-item-label.Inbox")
	private WebElement inboxConsoleMenu;

	@Override
	public boolean isInboxConsoleMenuDisplay() throws Exception {
		boolean isInboxConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(inboxConsoleMenu, 5)) {
				isInboxConsoleMenuDisplay = true;
				SimpleUtils.report("Inbox Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Inbox Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isInboxConsoleMenuDisplay;
	}


	@FindBy(css = "div.console-navigation-item-label.Admin")
	private WebElement adminConsoleMenu;

	@Override
	public boolean isAdminConsoleMenuDisplay() throws Exception {
		boolean isAdminConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(adminConsoleMenu, 5)) {
				isAdminConsoleMenuDisplay = true;
				SimpleUtils.report("Admin Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Admin Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isAdminConsoleMenuDisplay;
	}

	@Override
	public void clickOnAdminConsoleMenu() throws Exception {
		try{
			if(isElementLoaded(adminConsoleMenu, 5)) {
				click(adminConsoleMenu);
				SimpleUtils.report("Admin Console Menu been clicked Successfully!");
			} else
				SimpleUtils.report("Admin Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}


	@FindBy(css = "div.console-navigation-item-label.Integration")
	private WebElement integrationConsoleMenu;

	@Override
	public boolean isIntegrationConsoleMenuDisplay() throws Exception {
		boolean isIntegrationConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(integrationConsoleMenu, 5)) {
				isIntegrationConsoleMenuDisplay = true;
				SimpleUtils.report("Integration Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Integration Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isIntegrationConsoleMenuDisplay;
	}

	@Override
	public void clickOnIntegrationConsoleMenu() throws Exception {
		try{
			if(isElementLoaded(integrationConsoleMenu,5)) {
				click(integrationConsoleMenu);
				SimpleUtils.report("Integration Console Menu been clicked Successfully!");
			} else
				SimpleUtils.report("Integration Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}


	@FindBy(css = "div.console-navigation-item-label.Controls")
	private WebElement controlsConsoleMenu;

	@Override
	public boolean isControlsConsoleMenuDisplay() throws Exception {
		boolean isControlsConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(controlsConsoleMenu, 5)) {
				isControlsConsoleMenuDisplay = true;
				SimpleUtils.report("Controls Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Controls Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isControlsConsoleMenuDisplay;
	}


	@FindBy(css = "div.console-navigation-item-label.Logout")
	private WebElement logoutConsoleMenu;

	@Override
	public boolean isLogoutConsoleMenuDisplay() throws Exception {
		boolean isLogoutConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(logoutConsoleMenu, 5)) {
				isLogoutConsoleMenuDisplay = true;
				SimpleUtils.report("Logout Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Logout Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isLogoutConsoleMenuDisplay;
	}


	@FindBy(css = "div.console-navigation-item-label.Timesheet")
	private WebElement timesheetConsoleMenu;

	@Override
	public boolean isTimesheetConsoleMenuDisplay() throws Exception {
		boolean isTimesheetConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(timesheetConsoleMenu, 5)) {
				isTimesheetConsoleMenuDisplay = true;
				SimpleUtils.report("Timesheet Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Timesheet Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isTimesheetConsoleMenuDisplay;
	}

	@FindBy(css = "div[ng-if=\"hasViewAdminPermission()\"]")
	private WebElement adminPanel;
	@FindBy(css = "div[ng-show=\"subNavigation.canShowSelf\"]")
	private WebElement subNavigation;

	@Override
	public void verifyAdminPageIsLoaded() throws Exception {
		try{
			if(isElementLoaded(adminPanel,5)
					&& isElementLoaded(subNavigation, 5)) {
				SimpleUtils.pass("Admin page is loaded Successfully!");
			} else
				SimpleUtils.fail("Admin page not loaded Successfully!", false);
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@FindBy(css = "table[ng-if=\"configs.length\"]")
	private WebElement integrationConfigTable;
	@Override
	public void verifyIntegrationPageIsLoaded() throws Exception {
		try{
			if(isElementLoaded(integrationConfigTable, 5)
					&&isElementLoaded(subNavigation, 5)) {
				SimpleUtils.pass("Admin page is loaded Successfully!");
			} else
				SimpleUtils.fail("Admin page not loaded Successfully!", false);
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@FindBy(css = "div.header-navigation-label")
	private WebElement headerNavigation;

	@Override
	public void verifyHeaderNavigationMessage(String headerNavigationMessage) throws Exception {
		try{
			if(isElementLoaded(headerNavigation, 5)
					&&headerNavigation.getText().equalsIgnoreCase(headerNavigationMessage)) {
				SimpleUtils.pass("Header navigation message display correctly! ");
			} else
				SimpleUtils.fail("Header navigation message not display correctly!", false);
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}


	@FindBy(css = "[class=\"wm-ignore-css-reset\"]")
	private WebElement closeNewFeatureEnhancementsButton;

	@Override
	public void closeNewFeatureEnhancementsPopup() throws Exception {
		try{
			if(isElementLoaded(closeNewFeatureEnhancementsButton, 5)) {
				click(closeNewFeatureEnhancementsButton);
				SimpleUtils.pass("New Feature Enhancements Popup been closed successfully! ");
			} else
				SimpleUtils.report("New Feature Enhancements Popup is not loaded!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	//Added By Julie
	@FindBy (className = "header-navigation-label")
	private WebElement headerLabel;

	@FindBy (css = "[search-hint='Search District'] div.lg-select")
	private WebElement showDistrict;

	@FindBy (css = ".dashboard-time .text-left")
	private WebElement districtOnDashboardDM;

	@FindBy (css = ".dashboard-time .text-right")
	private WebElement weekOnDashboardDM;

	@FindBy (css = ".dms-time-stamp")
	private WebElement dmsTimeStamp;

	@Override
	public String getHeaderOnDashboard() throws Exception {
		String header = "";
		if (isElementLoaded(headerLabel,5))
			header = headerLabel.getText();
		return header;
	}

	@Override
	public void verifyHeaderOnDashboard() throws Exception {
		String header = getHeaderOnDashboard();
		if (header.equals("Dashboard"))
			SimpleUtils.pass("Dashboard Page: Header is \"Dashboard\" as expected");
		else
			SimpleUtils.fail("Dashboard Page: Header isn't \"Dashboard\"",true);
	}

	@Override
	public void validateThePresenceOfDistrict() throws Exception {
		if (isElementEnabled(districtOnDashboardDM, 10)) {
			if (districtOnDashboardDM.isDisplayed() && !districtOnDashboardDM.getText().isEmpty() && districtOnDashboardDM.getText() != null) {
				if (getDriver().findElement(By.xpath("//body//div[contains(@class,'welcome-text')]/following-sibling::div[1]/div[contains(@class,'text-left')]")).equals(districtOnDashboardDM)) {
					SimpleUtils.pass("Dashboard Page: District shows at left corner below welcome section successfully");
				} else {
					SimpleUtils.fail("Dashboard Page: District is not at left corner below welcome section", true);
				}
			} else {
				SimpleUtils.fail("Dashboard Page: District isn't present", true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: District failed to load", true);
		}
	}

	@Override
	public void validateTheVisibilityOfWeek() throws Exception {
		if (isElementEnabled(weekOnDashboardDM, 10)) {
			if (weekOnDashboardDM.isDisplayed() && !weekOnDashboardDM.getText().isEmpty() && weekOnDashboardDM.getText() != null) {
				if (getDriver().findElement(By.xpath("//body//div[contains(@class,'welcome-text')]/following-sibling::div[1]/div[contains(@class,'text-right')]")).equals(weekOnDashboardDM)) {
					SimpleUtils.pass("Dashboard Page: Week shows at right corner below welcome section successfully");
				} else {
					SimpleUtils.fail("Dashboard Page: Week is not at right corner below welcome section", true);
				}
			} else {
				SimpleUtils.fail("Dashboard Page: Week isn't present", true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Week failed to load", true);
		}
	}

	@Override
	public String getDistrictNameOnDashboard() throws Exception {
		String districtName = "";
		if (isElementEnabled(districtOnDashboardDM, 10)) {
			districtName = districtOnDashboardDM.getText();
			SimpleUtils.pass("Dashboard Page: District name is '" + districtName + "'");
		} else {
			SimpleUtils.fail("Dashboard Page: District failed to load", true);
		}
		return districtName;
	}

	@Override
	public void verifyTheWelcomeMessageOfDM(String userName) throws Exception {
		String time = dmsTimeStamp.getText().contains(",")? dmsTimeStamp.getText().split(",")[1]:dmsTimeStamp.getText();
		String greetingTime = getTimePeriod(time.toLowerCase());
		String expectedText = "Good " + greetingTime + ", " + userName + "." + "\n" + "Welcome to Legion" + "\n" + "Your Complete Workforce Engagement Solution";
		String actualText = "";
		if(isElementLoaded(detailWelcomeText, 5)){
			actualText = detailWelcomeText.getText();
			if (actualText.equals(expectedText)) {
				SimpleUtils.pass("Dashboard Page: Verified Welcome Text is as expected!");
			} else {
				SimpleUtils.fail("Dashboard Page: Verify Welcome Text failed! Expected is: " + expectedText + "\n" + "Actual is: " + actualText, true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Welcome Text Section doesn't Load successfully!", true);
		}
	}

	@Override
	public void validateThePresenceOfRefreshButton() throws Exception {
		if (isElementLoaded(refreshButton,10)) {
			if (refreshButton.isDisplayed() && !refreshButton.getText().isEmpty() && refreshButton.getText() != null) {
				if (getDriver().findElement(By.xpath("//body//div[contains(@class,'welcome-text')]/preceding-sibling::last-updated-countdown/div/lg-button")).equals(refreshButton)) {
					SimpleUtils.pass("Dashboard Page: Refresh button shows above welcome section successfully");
				} else {
					SimpleUtils.fail("Dashboard Page: Refresh button is not above welcome section", true);
				}
			} else {
				SimpleUtils.fail("Dashboard Page: Refresh button isn't present", true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Refresh button failed to load", true);
		}
	}

	@FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes === 0\"]")
	private WebElement justUpdated;

	@FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes > 0\"]")
	private WebElement lastUpdated;

	@FindBy (css = "last-updated-countdown span[ng-if^=\"$ctrl.minutes > 0\"] span")
	private WebElement lastUpdatedMinutes;

	@Override
	public void validateRefreshFunction() throws Exception {
		int minutes = 0;
		if (isElementLoaded(lastUpdatedMinutes,10) ) {
			minutes = lastUpdatedMinutes.getText().contains(" ")? Integer.valueOf(lastUpdatedMinutes.getText().split(" ")[0]):Integer.valueOf(lastUpdatedMinutes.getText());
			if (minutes >= 30 ) {
				if (lastUpdatedMinutes.getAttribute("class").contains("last-updated-countdown-time-orange"))
					SimpleUtils.pass("Dashboard Page: When the Last Updated time >= 30 mins, the color changes to orange");
				else
					SimpleUtils.fail("Dashboard Page: When the Last Updated time >= 30 mins, the color failed to change to orange",false);
			}
		}
		if (isElementLoaded(refreshButton, 10)) {
			clickTheElement(refreshButton);
			SimpleUtils.pass("Click on Refresh button Successfully!");
			if (dashboardSection.getAttribute("class").contains("home-dashboard-loading") && refreshButton.getAttribute("label").equals("Refreshing...")) {
				SimpleUtils.pass("Dashboard Page: After clicking Refresh button, the background is muted and it shows an indicator 'Refreshing...' that we are processing the info");
				if (isElementLoaded(justUpdated,60) && !dashboardSection.getAttribute("class").contains("home-dashboard-loading"))
					SimpleUtils.pass("Dashboard Page: Once the data is done refreshing, the page shows 'JUST UPDATED' and page becomes brighter again");
				else
					SimpleUtils.fail("Dashboard Page: When the data is done refreshing, the page doesn't show 'JUST UPDATED' and page doesn't become brighter again",false);
				if (isElementLoaded(lastUpdated,60) && lastUpdatedMinutes.getAttribute("class").contains("last-updated-countdown-time-blue"))
					SimpleUtils.pass("Dashboard Page: The Last Updated info provides the minutes last updated in blue");
				else
					SimpleUtils.fail("Dashboard Page: The Last Updated info doesn't provide the minutes last updated in blue",false);
			} else {
				SimpleUtils.fail("Dashboard Page: After clicking Refresh button, the background isn't muted and it doesn't show 'Refreshing...'",true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Refresh button not Loaded!", true);
		}
	}

	@Override
	public void validateRefreshPerformance() throws Exception {
		if (isElementLoaded(refreshButton, 10)) {
			clickTheElement(refreshButton);
			if (refreshButton.getAttribute("label").equals("Refreshing...")) {
				SimpleUtils.pass("Dashboard Page: After clicking Refresh button, the button becomes 'Refreshing...'");
				WebElement element = (new WebDriverWait(getDriver(), 60))
						.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[label=\"Refresh\"]")));
				if (element.isDisplayed()) {
					SimpleUtils.pass("Dashboard Page: Page refreshes within 1 minute successfully");
				} else {
					SimpleUtils.fail("Dashboard Page: Page doesn't refresh within 1 minute", false);
				}
			} else {
				SimpleUtils.fail("Dashboard Page: After clicking Refresh button, the background isn't muted and it doesn't show 'Refreshing...'",true);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: Refresh button not Loaded!", true);
		}
	}

	@Override
	public void validateRefreshWhenNavigationBack() throws Exception {
		String timestamp1 = "";
		String timestamp2 = "";
		if (isElementLoaded(lastUpdated,5)) {
			timestamp1 = lastUpdated.getText();
		} else if (isElementLoaded(justUpdated,5)) {
			timestamp1 = lastUpdated.getText();
		} else
			SimpleUtils.fail("Dashboard Page: Timestamp failed to load",false);
		click(scheduleConsoleNameInTM);
		navigateToDashboard();
		if (isElementLoaded(lastUpdated,5)) {
			timestamp2 = lastUpdated.getText();
		} else if (isElementLoaded(justUpdated,5)) {
			timestamp2 = justUpdated.getText();
		} else
			SimpleUtils.fail("Dashboard Page: Timestamp failed to load",false);
		if (timestamp2.equals(timestamp1) && !timestamp1.equals("") && !refreshButton.getAttribute("label").equals("Refreshing...")) {
			SimpleUtils.pass("Dashboard Page: It keeps the previous Last Updated time, not refreshing every time");
		} else {
			SimpleUtils.fail("Dashboard Page: It doesn't keep the previous Last Updated time",false);
		}
	}

	@Override
	public void validateRefreshTimestamp() throws Exception {
		String timestamp = "";
		if (isElementLoaded(justUpdated, 5)) {
			SimpleUtils.pass("Dashboard Page:  The page just refreshed");
		} else if (isElementLoaded(lastUpdatedMinutes, 5)) {
			timestamp = lastUpdatedMinutes.getText();
			if (timestamp.contains("HOURS") && timestamp.contains(" ")) {
				timestamp = timestamp.split(" ")[0];
				if (Integer.valueOf(timestamp) == 1)
					SimpleUtils.pass("Dashboard Page:  The backstop is 1 hour so that the data is not older than 1 hour stale");
				else
					// SimpleUtils.fail("Dashboard Page:  The backstop is older than 1 hour stale",false);
					SimpleUtils.warn("SCH-2589: [DM View] Refresh time is older than 1 hour stale");
			}
			if (timestamp.contains("MINS") && timestamp.contains(" ")) {
				timestamp = timestamp.split(" ")[0];
				if (Integer.valueOf(timestamp) < 60 && Integer.valueOf(timestamp) >= 1)
					SimpleUtils.pass("Dashboard Page:  The backstop is last updated" + timestamp + " ago");
				else
					SimpleUtils.fail("Dashboard Page:  The backstop isn't refreshed in 1 hour stale", false);
			}
		} else
			SimpleUtils.fail("Dashboard Page: Timestamp failed to load", false);
	}

	@FindBy (css = "div.fl-right.width-48.dms-smart-card-4")
	private WebElement projectedComplianceWidget;

	@FindBy (css = "div.dms-number-x-large")
	private WebElement totalViolationHrs;

	@FindBy (css = "div.tc.dms-box-item-title-2.dms-box-item-title-color-light")
	private WebElement totalViolationHrsMessage;

	@FindBy (css = "div[ng-click=\"viewCompliance()\"]")
	private WebElement viewComplianceLink;


	public boolean isProjectedComplianceWidgetDisplay() throws Exception {
		boolean isProjectedComplianceWidgetDisplay = false;
		if(isElementLoaded(projectedComplianceWidget, 5)) {
			isProjectedComplianceWidgetDisplay = true;
			SimpleUtils.report("Projected Compliance Widget is loaded Successfully!");
		} else
			SimpleUtils.report("Projected Compliance Widget not loaded Successfully!");
		return isProjectedComplianceWidgetDisplay;
	}

	public void verifyTheContentInProjectedComplianceWidget() throws Exception {
		if(isElementLoaded(projectedComplianceWidget, 5)) {
			WebElement projectedComplianceWidgetTitle = projectedComplianceWidget.findElement(By.cssSelector("div.dms-box-title.dms-box-item-title-row"));
			if (isElementLoaded(projectedComplianceWidgetTitle, 5)
					&& projectedComplianceWidgetTitle.getText().equalsIgnoreCase("Projected Compliance")
					&& isElementLoaded(totalViolationHrs, 5)
					&& isElementLoaded(totalViolationHrsMessage, 5)
					&& totalViolationHrsMessage.getText().equalsIgnoreCase("Total Violation Hrs")
					&& isElementLoaded(viewComplianceLink, 5)
					&& viewComplianceLink.getText().equalsIgnoreCase("View Compliance")){
				SimpleUtils.pass("The content in Projected Compliance widget display correctly");
			} else {
				SimpleUtils.fail("The content in Projected Compliance widget display incorrectly", false);
			}
		} else
			SimpleUtils.report("Projected Compliance Widget not loaded Successfully!");
	}

	public String getTheTotalViolationHrsFromProjectedComplianceWidget() throws Exception {
		String hrsOfTotalViolation = "";
		if (isElementLoaded(totalViolationHrs, 5)){
			hrsOfTotalViolation = totalViolationHrs.getText();
			SimpleUtils.pass("Get the total violation hrs successfully");
		} else {
			SimpleUtils.fail("Total violation hours not loaded successfully", false);
		}
		return hrsOfTotalViolation;
	}

	public void clickOnViewComplianceLink() throws Exception {
		if (isElementLoaded(viewComplianceLink, 5)){
			scrollToElement(viewComplianceLink);
			click(viewComplianceLink);
			SimpleUtils.pass("Click View Compliance link successfully");
		} else {
			SimpleUtils.fail("View Compliance link not loaded successfully", false);
		}
	}

	@FindBy(className = "dms-timesheet-chart-pos")
	private WebElement timesheetApprovalRateWidgetChart;

	@FindBy(xpath = "//div[contains(text(),'Timesheet Approval Rate')]")
	private WebElement timesheetApprovalRateWidgetTitle;

	@FindBy(css = "[ng-click=\"viewTimesheet()\"]")
	private WebElement viewTimesheetsButton;

	@FindBy(css = "timesheet-approval-chart > div > svg > g > text[font-size=\"11\"][text-anchor]")
	private List<WebElement> timesheetApprovalRateItems;

	@FindBy(css = "timesheet-approval-chart > div > svg > g > text[text-anchor][style]")
	private List<WebElement> timesheetApprovalRatePercentages;

	@Override
	public void validateTheContentOnTimesheetApprovalRateWidgetInDMView() throws Exception {
        /*Timesheet Approval Rate  widget should show:
         a. Title: Timesheet Approval Rate
         b. Timesheet approval chart with 0 Hrs, 24 Hrs, 48 Hrs and > 48 Hrs
         c. Button: View Timesheets*/
		List<String> textItems = new ArrayList<>(Arrays.asList("0 Hrs", "24 Hrs", "48 Hrs", "> 48 Hrs"));
		if (isElementLoaded(timesheetApprovalRateWidgetTitle, 5) && timesheetApprovalRateWidgetTitle.getText().contains("Timesheet Approval Rate") && isElementLoaded(timesheetApprovalRateWidgetChart,5)) {
			SimpleUtils.pass("Dashboard Page: The title and chart on \"Timesheet Approval Rate\" widget are loaded  in DM View");
		} else {
			SimpleUtils.fail("Dashboard Page: The widget of \"Timesheet Approval Rate\" failed to load in DM View",false);
		}
		if (areListElementVisible(timesheetApprovalRateItems, 5) && timesheetApprovalRateItems.size() == 4) {
			for (WebElement item : timesheetApprovalRateItems) {
				if (textItems.contains(item.getText().trim())) {
					SimpleUtils.pass("Dashboard Page: Verified Text Item: \"" + item.getText().trim() + "\" loaded");
				} else {
					SimpleUtils.fail("Dashboard Page: Unexpected text item: \"" + item.getText().trim() + "\" loaded!", false);
				}
			}
		} else {
			SimpleUtils.fail("Dashboard Page: The Legend Items of \"Timesheet Approval Status\" not loaded", false);
		}
		if (isElementLoaded(viewTimesheetsButton, 5)) {
			SimpleUtils.pass("Dashboard Page: \"View Timesheets\" link loaded successfully on \"Timesheet Approval Rate\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: \"View Timesheets\" link not loaded on \"Timesheet Approval Rate\" widget", false);
		}
	}

	@Override
	public void clickOnViewTimesheets() throws Exception {
		if (isElementLoaded(viewTimesheetsButton, 5)) {
			clickTheElement(viewTimesheetsButton);
			if (timesheetConsoleMenu.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Dashboard Page: Click on \"View Timesheets\" link on \"Timesheet Approval Rate\" successfully");
			else
				SimpleUtils.fail("Dasboard Page: Failed to click on \"View Timesheets\" link on \"Timesheet Approval Rate\"",false);
		} else {
			SimpleUtils.fail("Dashboard Page: \"View Timesheets\" link not loaded on \"Timesheet Approval Rate\"", false);
		}
	}

	@Override
	public void validateStatusValueOfTimesheetApprovalRateWidget() throws Exception {
		if (areListElementVisible(timesheetApprovalRatePercentages,5) && timesheetApprovalRatePercentages.size() == 3) {
			for (WebElement percentage: timesheetApprovalRatePercentages) {
				// mouseHover(percentage);
				// todo: Locate the tooltip web element and if it loads, it will pass, or else it will fail.
				SimpleUtils.warn("SCH-2636: [DM Dashboard] The value tooltips should display when hover the mouse on the chart in Timesheet Approval Rate widget");
			}
		} else
			SimpleUtils.fail("Dashboard Page: Percentages on Timesheet Approval Rate widget failed to load",false);
	}

	@Override
	public List<String> getTimesheetApprovalRateOnDMViewWidget() throws Exception {
		List<String> timesheetApprovalRateFromChart = new ArrayList<>();
		if (areListElementVisible(timesheetApprovalRatePercentages, 5) && timesheetApprovalRatePercentages.size() == 3) {
			for (WebElement percentage : timesheetApprovalRatePercentages) {
				if (!percentage.getText().isEmpty()) {
					timesheetApprovalRateFromChart.add(percentage.getText().trim());
					SimpleUtils.report("Dashboard Page: Get the percentage Data: \"" + percentage.getText().trim() + "\" on Timesheet Approval Rate widget Successfully!");
				} else {
					SimpleUtils.fail("Dashboard Page: Failed to get the percentage data on Timesheet Approval Rate widget", false);
				}
			}
		}else
			SimpleUtils.fail("Dashboard Page: Percentages on Timesheet Approval Rate widget failed to load",false);
		return timesheetApprovalRateFromChart;
	}

	@Override
	public void validateDataOnTimesheetApprovalRateWidget(List<String> timesheetApprovalRateOnDMViewDashboard, List<String> timesheetApprovalRateFromSmartCardOnDMViewTimesheet) throws Exception {
		if (timesheetApprovalRateOnDMViewDashboard.size() == 3 && timesheetApprovalRateFromSmartCardOnDMViewTimesheet.size() == 4) {
			if (timesheetApprovalRateOnDMViewDashboard.get(0).equals(timesheetApprovalRateFromSmartCardOnDMViewTimesheet.get(0))
					&& timesheetApprovalRateOnDMViewDashboard.get(1).equals(timesheetApprovalRateFromSmartCardOnDMViewTimesheet.get(1))
					&& timesheetApprovalRateOnDMViewDashboard.get(2).equals(timesheetApprovalRateFromSmartCardOnDMViewTimesheet.get(2))) {
				SimpleUtils.pass("Dashboard Page: The data in Timesheet approval chart on Dashboard is consistent with the smart card in Timesheet tab");
			} else {
				SimpleUtils.fail("Dashboard Page: The data in Timesheet approval chart on Dashboard is inconsistent with the smart card in Timesheet tab",false);
			}
		} else
			SimpleUtils.fail("Dashboard Page: Timesheet Approval Rate get incorrectly",false);
	}
}
