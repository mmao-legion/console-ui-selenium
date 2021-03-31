package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.SimpleUtils.getCurrentDateMonthYearWithTimeZone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.ScheduleDMViewPage;
import com.legion.pages.SchedulePage;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import com.legion.utils.SimpleUtils;

import cucumber.api.java.hu.Ha;
import cucumber.api.java.ro.Si;
import cucumber.api.java8.Da;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class ConsoleDashboardPage extends BasePage implements DashboardPage {

    @FindBy(css = "[ng-click='openSchedule()']")
    private WebElement goToTodayScheduleButton;

    @FindBy(css = "[ng-class = 'subNavigationViewLinkActiveClass(view)']")
    private WebElement goToTodayScheduleView;

    @FindBy(css = "[ng-if=\"scheduleForToday($index) && !scheduleForToday($index).length\"]")
    private WebElement publishedShiftForTodayDiv;

    @FindBy(css = "[ng-if=\"daySummary($index) && weeklyScheduleData($index) && canViewGuidance()\"]")
    private WebElement dashboardTodaysForecastDiv;

    @FindBy(css = "[ng-if=\"canViewProjectedSales()\"]")
    private WebElement dashboardTodaysProjectedDemandGraphDiv;

    @FindBy(className = "welcome-text")
    private WebElement dashboardWelcomeSection;

    @FindBy(className = "upcoming-shift-container")
    private WebElement dashboardUpcomingShiftContainer;

    @FindBy(css = "div[ng-if*='daySummary($index)) && weeklyScheduleData($index) && canViewGuidance()']")
    private WebElement dashboardTodaysForecastSection;

    @FindBy(css = "[ng-if=\"graphData($index)\"]")
    private WebElement dashboardProjectedDemandGraph;

    @FindBy(className = "home-dashboard")
    private WebElement dashboardSection;

    @FindBy(className = "console-navigation-item")
    private List<WebElement> consoleNavigationMenuItems; //fiona will using

    @FindBy(css = "#legion-app navigation div:nth-child(4)")//fiona using
    private WebElement scheduleConsoleName;

    @FindBy(className = "home-dashboard")
    private WebElement legionDashboardSection;

    @FindBy(css = "div.console-navigation-item-label.Dashboard")  //fiona will using
    private WebElement dashboardConsoleName;

    @FindBy(css = "div.console-navigation-item-label.Controls")//fiona using
    private WebElement controlsConsoleName;

    @FindBy(css = ".lg-location-chooser__global.ng-scope")
    private WebElement globalIconControls;

    @FindBy(css = ".center.ng-scope")//fiona using
    private WebElement controlsPage;

    @FindBy(css = "div.col-sm-8.text-left")
    private WebElement todaysForecast;

    @FindBy(css = "div.col-sm-4.text-left")
    private WebElement startingSoon;

    @FindBy(css = ".header-avatar>img")
    private WebElement iconProfile;

    @FindBy(css = "li[ng-if='canShowTimeoffs']")
    private WebElement timeOffLink;

    @FindBy(css = "[ng-src*='t-m-time-offs']")
    private WebElement iconImage;

    @FindBy(css = ".col-sm-6.text-right")
    private WebElement currentTime;

    @FindBy(css = "div.fx-center.welcome-text h1")
    private WebElement detailWelcomeText;

    public ConsoleDashboardPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @Override
    public boolean isToday() throws Exception {

        //boolean bol = true;
        if (isElementLoaded(publishedShiftForTodayDiv)) {
            SimpleUtils.pass("Today's published Shifts loaded Successfully on Dashboard!");
        } else {
            return false;
        }
        if (isElementLoaded(dashboardTodaysForecastDiv)) {
            SimpleUtils.pass("Today's ForeCast Labels loaded Successfully on Dashboard!");
        } else {
            return false;
        }
        if (isElementLoaded(dashboardTodaysProjectedDemandGraphDiv)) {
            SimpleUtils.pass("Today's Projected Demand Graph loaded Successfully on Dashboard!");
        } else {
            return false;
        }

        return true;
    }

    @Override
    public void verifyDashboardPageLoadedProperly() throws Exception {
        /*
         *  Check whether Welcome Text Section appear or not on Dashboard.
         */
        if (isElementLoaded(dashboardWelcomeSection))
            SimpleUtils.pass("Dashboard Page Welcome Text Section Loaded Successfully!");
        else
            SimpleUtils.fail("Dashboard Page Welcome Text Section not loaded Successfully!", true);

        /*
         *  Check whether 'VIEW TODAY'S SCHEDULE' Button appear or not on Dashboard.
         */
        if (isElementLoaded(goToTodayScheduleButton))
            SimpleUtils.pass("Dashboard Page 'VIEW TODAY'S SCHEDULE' Button Loaded Successfully!");
        else
            SimpleUtils.fail("Dashboard Page 'VIEW TODAY'S SCHEDULE' Button not loaded Successfully!", true);

        /*
         *  Check whether 'Upcoming Shift Container' Section appear or not on Dashboard.
         */
        if (isElementLoaded(dashboardUpcomingShiftContainer))
            SimpleUtils.pass("Dashboard Page 'Upcoming Shift Container' Section Loaded Successfully!");
        else
            SimpleUtils.fail("Dashboard Page 'Upcoming Shift Container' Section not Loaded Successfully!", true);

        /*
         *  Check whether 'Today's Forecast' Section appear or not on Dashboard.
         */
        if (isElementLoaded(dashboardTodaysForecastSection))
            SimpleUtils.pass("Dashboard Page 'Today's Forecast' Section Loaded Successfully!");
        else
            SimpleUtils.fail("Dashboard Page 'Today's Forecast' Section not Loaded Successfully!", true);

        /*
         *  Check whether 'Projected Demand Graph' Section appear or not on Dashboard.
         */
        if (isElementLoaded(dashboardProjectedDemandGraph))
            SimpleUtils.pass("Dashboard Page 'Projected Demand Graph' Section Loaded Successfully!");
        else
            SimpleUtils.fail("Dashboard Page 'Projected Demand Graph' Section not Loaded Successfully!", true);

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
		String dateFormat = "";
		String dateFromDashboard = getCurrentDateFromDashboard() + " " + currentTime.getText().toUpperCase();
		if (dateFromDashboard.toLowerCase().contains("am") || dateFromDashboard.toLowerCase().contains("pm")) {
			dateFormat = "EEEE, MMMM d h:mm a";
		} else {
			dateFormat = "EEEE, MMMM d H:mm";
		}
		String dateFromLocation = getDateFromTimeZoneOfLocation(dateFormat);
		if (dateFromDashboard.equals(dateFromLocation)) {
			SimpleUtils.pass("Dashboard Page: The date and time on Dashboard is consistent with the timezone of current location");
		} else {
			SimpleUtils.fail("Dashboard Page: The date and time on Dashboard is different from the timezone of the current location, date from dashboard is: "
					+ dateFromDashboard + ", date from location is: " + dateFromLocation, false);
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
		String dateFormat = "";
		if (isElementLoaded(currentDate, 10) && isElementLoaded(currentTime, 10)) {
			SimpleUtils.pass("Current date and time are loaded successfully");
			String dateFromDashboard = getCurrentDateFromDashboard() + " " + currentTime.getText().toUpperCase();
			if (dateFromDashboard.toLowerCase().contains("am") || dateFromDashboard.toLowerCase().contains("pm")) {
				dateFormat = "EEEE, MMMM d h:mm a";
			} else {
				dateFormat = "EEEE, MMMM d H:mm";
			}
			String dateFromLocation = getDateFromTimeZoneOfLocation(dateFormat);
			if (dateFromDashboard.equals(dateFromLocation)) {
				SimpleUtils.pass("Date and time shows according to the US(Particular location) timing successfully");
			} else {
				SimpleUtils.fail("Dashboard Page: The date and time on Dashboard is different from the timezone of the current location, date from dashboard is: "
						+ dateFromDashboard + ", date from location is: " + dateFromLocation, false);
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

	@FindBy(css = "[ng-if=\"$ctrl.minutes >= 0 && $ctrl.date && !$ctrl.loading\"]")
	private WebElement lastUpdatedIcon;


	@Override
	public void clickOnRefreshButton() throws Exception {
		if (isElementLoaded(refreshButton, 10)) {
			clickTheElement(refreshButton);
			if(isElementLoaded(lastUpdatedIcon, 60)){
				SimpleUtils.pass("Click on Refresh button Successfully!");
			} else
				SimpleUtils.fail("Refresh timeout! ", false);
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

	@FindBy(css = "lg-select[search-hint=\"Search District\"]")
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


	@FindBy(css = "div.console-navigation-item-label.Report")
	private WebElement reportConsoleMenu;

	@Override
	public boolean isReportConsoleMenuDisplay() throws Exception {
		boolean isReportConsoleMenuDisplay = false;
		try{
			if(isElementLoaded(reportConsoleMenu, 5)) {
				isReportConsoleMenuDisplay = true;
				SimpleUtils.report("Report Console Menu is loaded Successfully!");
			} else
				SimpleUtils.report("Report Console Menu not loaded Successfully!");
		} catch(Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
		return isReportConsoleMenuDisplay;
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
	public String getWeekInfoFromDMView() throws Exception {
		String result = "";
		if (isElementEnabled(weekOnDashboardDM, 10)) {
			result = weekOnDashboardDM.getText();
		} else {
			SimpleUtils.fail("Dashboard Page: Week failed to load", true);
		}
		return result;
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
			SimpleUtils.pass("Dashboard Page: Click on Refresh button Successfully!");
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
			} else if (timestamp.contains("MINS") && timestamp.contains(" ")) {
				timestamp = timestamp.split(" ")[0];
				if (Integer.valueOf(timestamp) < 60 && Integer.valueOf(timestamp) >= 1)
					SimpleUtils.pass("Dashboard Page:  The backstop is last updated " + timestamp + " mins ago");
				else
					SimpleUtils.fail("Dashboard Page: The backup is last updated " + timestamp + " mins ago actually", false);
			} else
				SimpleUtils.fail("Dashboard Page: The backup display \'" + lastUpdated.getText() + "\'",false);
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
        /*Timesheet Approval Rate widget should show:
         a. Title: Timesheet Approval Rate
         b. Timesheet approval chart with 0 Hrs, 24 Hrs, 48 Hrs and > 48 Hrs
         c. Button: View Timesheets*/
		List<String> textItems = new ArrayList<>(Arrays.asList("0 Hrs", "24 Hrs", "48 Hrs", "> 48 Hrs"));
		if (isElementLoaded(timesheetApprovalRateWidgetTitle, 5) && timesheetApprovalRateWidgetTitle.getText().contains("Timesheet Approval Rate") && isElementLoaded(timesheetApprovalRateWidgetChart,5)) {
			SimpleUtils.pass("Dashboard Page: The title and chart on \"Timesheet Approval Rate\" widget are loaded in DM View");
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
		} else
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

	@FindBy(xpath = "//div[contains(text(),'Compliance Violations')]")
	private WebElement complianceViolationsWidgetTitle;

	@FindBy (css = "[ng-repeat=\"cv in scheduleComplianceKPI\"]")
    private List<WebElement> scheduleComplianceKPIOnComplianceViolationsWidget;

	@FindBy (className = "timesheet-approval-chart__svg")
	private WebElement timesheetApprovalRateWidget;

	@Override
	public boolean isTimesheetApprovalRateWidgetDisplay() throws Exception {
		boolean isTimesheetApprovalRateWidgetDisplay = false;
		if(isElementLoaded(timesheetApprovalRateWidget, 30) && isElementLoaded(refreshButton, 30)) {
			isTimesheetApprovalRateWidgetDisplay = true;
			SimpleUtils.report("Timesheet Approval Rate WidgetDisplay Widget is loaded Successfully!");
		} else
			SimpleUtils.report("Timesheet Approval Rate Widget not loaded Successfully!");
		return isTimesheetApprovalRateWidgetDisplay;
	}

	@Override
	public boolean isComplianceViolationsWidgetDisplay() throws Exception {
		boolean isComplianceViolationsWidgetDisplay = false;
		if(areListElementVisible(scheduleComplianceKPIOnComplianceViolationsWidget, 30) && isElementLoaded(refreshButton, 30)) {
			isComplianceViolationsWidgetDisplay = true;
			SimpleUtils.report("Compliance Violations Widget is loaded Successfully!");
		} else
			SimpleUtils.report("Compliance Violations Widget not loaded Successfully!");
		return isComplianceViolationsWidgetDisplay;
	}

	@Override
	public void validateTheContentOnComplianceViolationsWidgetInDMView() throws Exception {
		 /*Compliance Violation widget should show:
         a. Title: Compliance Violations
         b. x Total Hrs
         c. x Violations
         d. x Locations
         e. Button: View Compliance*/
		if (isElementLoaded(complianceViolationsWidgetTitle, 5) && complianceViolationsWidgetTitle.getText().contains("Compliance Violations")) {
			SimpleUtils.pass("Dashboard Page: The title on \"Compliance Violations\" widget is loaded in DM View");
		} else {
			SimpleUtils.fail("Dashboard Page: The widget of \"Compliance Violations\" failed to load in DM View",false);
		}
		if (areListElementVisible(scheduleComplianceKPIOnComplianceViolationsWidget, 5) && scheduleComplianceKPIOnComplianceViolationsWidget.size() == 3) {
			for (WebElement item : scheduleComplianceKPIOnComplianceViolationsWidget) {
				if (item.getText().contains("Total Hrs") || item.getText().contains("Violations") || item.getText().contains("Locations")) {
					SimpleUtils.pass("Dashboard Page: Verified KPI: \"" + item.getText().trim() + "\" loaded");
				} else {
					SimpleUtils.fail("Dashboard Page: Unexpected KPI: \"" + item.getText().trim() + "\" loaded!", false);
				}
			}
		} else {
			SimpleUtils.fail("Dashboard Page: The Legend Items of \"Timesheet Approval Status\" not loaded", false);
		}
		if (isElementLoaded(viewComplianceLink, 5)) {
			SimpleUtils.pass("Dashboard Page: \"View Violations\" link loaded successfully on \"Compliance Violations\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: \"View Violations\" link not loaded on \"Compliance Violations\" widget", false);
		}
	}

	@FindBy (css = ".console-navigation-item-label.Compliance")
	private WebElement complianceConsoleMenu;

	@Override
	public void clickOnViewViolations() throws Exception {
		if (isElementLoaded(viewComplianceLink, 5)) {
			clickTheElement(viewComplianceLink);
			if (complianceConsoleMenu.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Dashboard Page: Click on \"View Violations\" link on \"Compliance Violations\" successfully");
			else
				SimpleUtils.fail("Dasboard Page: Failed to click on \"View Violations\" link on \"Compliance Violations\"",false);
		} else {
			SimpleUtils.fail("Dashboard Page: \"View Violations\" link not loaded on \"Compliance Violations\"", false);
		}
	}

	@Override
	public List<String> getComplianceViolationsOnDMViewWidget() throws Exception {
		List<String> complianceViolationsOnDMViewWidget = new ArrayList<>();
		if (areListElementVisible(scheduleComplianceKPIOnComplianceViolationsWidget, 5) && scheduleComplianceKPIOnComplianceViolationsWidget.size() == 3) {
			for (WebElement item : scheduleComplianceKPIOnComplianceViolationsWidget) {
				if (!item.getText().isEmpty()) {
					complianceViolationsOnDMViewWidget.add(item.getText().trim().replace("\n", " "));
					SimpleUtils.report("Dashboard Page: Get the data: \"" + item.getText().trim().replace("\n", " ") + "\" on Compliance Violations widget Successfully!");
				} else {
					SimpleUtils.fail("Dashboard Page: Failed to get the data on Compliance Violations widget", false);
				}
			}
		} else
			SimpleUtils.fail("Dashboard Page: Data on Compliance Violations widget failed to load",false);
		return complianceViolationsOnDMViewWidget;
	}

	@Override
	public void validateDataOnComplianceViolationsWidget(List<String> complianceViolationsOnDMViewDashboard, List<String> complianceViolationsFromSmartCardOnDMViewCompliance) throws Exception {
		if (complianceViolationsOnDMViewDashboard.size() == 3 && complianceViolationsFromSmartCardOnDMViewCompliance.size() == 3) {
			if (complianceViolationsOnDMViewDashboard.get(0).equals(complianceViolationsFromSmartCardOnDMViewCompliance.get(0))
					&& complianceViolationsOnDMViewDashboard.get(1).equals(complianceViolationsFromSmartCardOnDMViewCompliance.get(1))
					&& complianceViolationsOnDMViewDashboard.get(2).equals(complianceViolationsFromSmartCardOnDMViewCompliance.get(2))) {
				SimpleUtils.pass("Dashboard Page: The data in Compliance Violations on Dashboard is consistent with the smart card in Compliance tab");
			} else {
				SimpleUtils.fail("Dashboard Page: The data in Compliance Violations on Dashboard is inconsistent with the smart card in Timesheet tab",false);
			}
		} else
			SimpleUtils.fail("Dashboard Page: Compliance Violations get incorrectly",false);
	}

	@FindBy(xpath = "//div[contains(text(),'Payroll Projection')]")
	private WebElement payrollProjectionWidgetTitle;

	@FindBy(css= "[ng-if*=\"forecastKPI.futureBudgetSurplus\"]")
	private List<WebElement> budgetSurplusOnPayrollProjectionWidget;

	@FindBy (xpath = "//div[contains(text(),'Payroll Projection')]/../../div[contains(@class,\"dms-action-link\")]")
	private WebElement viewSchedulesLinkOnPayrollProjectionWidget;

	@FindBy (xpath = "//div[contains(text(),'Payroll Projection')]/../..//div[contains(@class, \"dms-box-item-title-color-light\")]")
	private WebElement noteOnPayrollProjectionWidget;

	@FindBy(xpath = "//div[contains(text(),'Payroll Projection')]/../../div[2]/div")
	private WebElement legendOnPayrollProjectionWidget;

	@FindBy(css = ".payroll-projection-chart__svg [width=\"20\"]")
	private List<WebElement> rectsOnPayrollProjectionWidget;

	@FindBy(css = ".dms-payroll-time-legend")
	private WebElement weekOnPayrollProjectionWidget;

	@FindBy(css = ".payroll-projection-chart__svg text[text-anchor][style]")
	private WebElement asOfDateTimeOnPayrollProjectionWidget;

	@FindBy(css = ".payroll-projection-chart__svg line[style]")
	private WebElement asOfLineOnPayrollProjectionWidget;

	@FindBy (className = "payroll-projection-chart__svg")
	private WebElement payrollProjectionWidget;


	@Override
	public boolean isPayrollProjectionWidgetDisplay() throws Exception {
		boolean isPayrollProjectionWidgetDisplay = false;
		if(isElementLoaded(payrollProjectionWidget, 30) && isElementLoaded(refreshButton, 30)) {
			isPayrollProjectionWidgetDisplay = true;
			SimpleUtils.report("Payroll Projection Widget is loaded Successfully!");
		} else
			SimpleUtils.report("Payroll Projection Widget not loaded Successfully!");
		return isPayrollProjectionWidgetDisplay;
	}

	@Override
	public void clickOnViewSchedulesOnPayrollProjectWidget() throws Exception {
		if (isElementLoaded(viewSchedulesLinkOnPayrollProjectionWidget, 5)) {
			clickTheElement(viewSchedulesLinkOnPayrollProjectionWidget);
			if (scheduleConsoleMenu.findElement(By.xpath("./..")).getAttribute("class").contains("active"))
				SimpleUtils.pass("Dashboard Page: Click on \"View Schedules\" link on \"Payroll Projection\" successfully");
			else
				SimpleUtils.fail("Dashboard Page: Failed to click on \"View Schedules\" link on \"Payroll Projection\"",false);
		} else {
			SimpleUtils.fail("Dashboard Page: \"View Schedules\" link not loaded on \"Payroll Projection\"", false);
		}
	}

	@Override
	public void validateTheContentOnPayrollProjectionWidget() throws Exception {
		 /*Payroll Projection widget should show:
         a. Title: Payroll Projection
         b. x Hrs Over/Under Budget in red/green
         c. Projected for remainder of this week
         d. 3 legends: Budgeted/Scheduled/Projected
         e. Payroll projection chart with 7 days
         f. Current week, e.g. Dec 12 - Dec 18
         e. Button: View Schedules*/
		if (isElementLoaded(payrollProjectionWidgetTitle, 5) && payrollProjectionWidgetTitle.getText().contains("Payroll Projection")) {
			SimpleUtils.pass("Dashboard Page: The title on \"Payroll Projection\" widget is loaded in DM View");
		} else {
			SimpleUtils.fail("Dashboard Page: The title on \"Payroll Projection\" widget failed to load in DM View",false);
		}
		if (areListElementVisible(budgetSurplusOnPayrollProjectionWidget,10) && budgetSurplusOnPayrollProjectionWidget.size() == 2) {
			SimpleUtils.pass("Dashboard Page: Budget surplus \"" + budgetSurplusOnPayrollProjectionWidget.get(1).getText() + "\" on \"Payroll Projection\" widget is loaded in DM View");
		} else {
			SimpleUtils.fail("Dashboard Page: The Budget surplus on \"Payroll Projection\" widget failed to load in DM View",false);
		}
		if (isElementLoaded(noteOnPayrollProjectionWidget, 5) && noteOnPayrollProjectionWidget.getText().contains("Projected for remainder of this week")) {
			SimpleUtils.pass("Dashboard Page: \"Projected for remainder of this week\" loaded successfully on \"Payroll Projection\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: \"Projected for remainder of this week\" not loaded on \"Payroll Projection\" widget", false);
		}
		if (isElementLoaded(legendOnPayrollProjectionWidget, 5) && legendOnPayrollProjectionWidget.getText().contains("Budgeted")
				&& legendOnPayrollProjectionWidget.getText().contains("Scheduled") && legendOnPayrollProjectionWidget.getText().contains("Projected")) {
			SimpleUtils.pass("Dashboard Page: Budgeted/Scheduled/Projected legends loaded successfully on \"Payroll Projection\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: Budgeted/Scheduled/Projected legends not loaded on \"Payroll Projection\" widget", false);
		}
		if (areListElementVisible(rectsOnPayrollProjectionWidget,5) && rectsOnPayrollProjectionWidget.size() == 21) {
			SimpleUtils.pass("Dashboard Page: 21 rects with 7 days loaded successfully on \"Payroll Projection\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: 21 rects with 7 days not loaded on \"Payroll Projection\" widget",false);
		}
		if (isElementLoaded(weekOnPayrollProjectionWidget, 5) && weekOnDashboardDM.getText().contains(weekOnPayrollProjectionWidget.getText())) {
			SimpleUtils.pass("Dashboard Page: Current week \""+ weekOnPayrollProjectionWidget.getText() + "\" loaded successfully on \"Payroll Projection\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: Current week not loaded on \"Payroll Projection\" widget", false);
		}
		if (isElementLoaded(viewSchedulesLinkOnPayrollProjectionWidget, 5)) {
			SimpleUtils.pass("Dashboard Page: \"View Schedules\" link loaded successfully on \"Payroll Projection\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: \"View Schedules\" link not loaded on \"Payroll Projection\" widget", false);
		}
	}

	@Override
	public String getWeekOnPayrollProjectionWidget() throws Exception {
		String week = "";
		if (isElementLoaded(weekOnPayrollProjectionWidget, 5)) {
			week = weekOnPayrollProjectionWidget.getText();
			SimpleUtils.pass("Dashboard Page: Get week \""+ week + "\" on \"Payroll Projection\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: Current week not loaded on \"Payroll Projection\" widget", false);
		}
		return week;
	}

	@Override
	public void validateWeekOnPayrollProjectionWidget(String weekOnPayrollProjectionWidget, String currentWeekInScheduleTab) throws Exception {
		if (weekOnPayrollProjectionWidget.contains(" - ")) {
			if (weekOnPayrollProjectionWidget.split(" - ")[0].contains(" ") && weekOnPayrollProjectionWidget.split(" - ")[1].contains(" ")) {
				weekOnPayrollProjectionWidget = weekOnPayrollProjectionWidget.split(" - ")[0].split(" ")[0] + " " + weekOnPayrollProjectionWidget.split(" - ")[0].split(" ")[1].replaceFirst("^(0*)", "") + " - "
				+ weekOnPayrollProjectionWidget.split(" - ")[1].split(" ")[0] + " " + weekOnPayrollProjectionWidget.split(" - ")[1].split(" ")[1].replaceFirst("^(0*)", "");
			}
		}
        SimpleUtils.report("Dashboard Page: The week on Payroll Projection widget is \"" + weekOnPayrollProjectionWidget + "\"");
		if (currentWeekInScheduleTab.contains("\n")) {
			currentWeekInScheduleTab = currentWeekInScheduleTab.split("\n")[1];
		}
		SimpleUtils.report("Schedule Page: The current week is \"" + currentWeekInScheduleTab + "\" in Schedule tab");
		if (currentWeekInScheduleTab.equals(weekOnPayrollProjectionWidget)) {
			SimpleUtils.pass("Dashboard Page: The week on Payroll Projection widget is consistent with the week in Schedule tab");
		} else {
			SimpleUtils.fail("Dashboard Page: The week on Payroll Projection widget is inconsistent with the week in Schedule tab",false);
		}
	}

	@Override
	public String getBudgetSurplusOnPayrollProjectionWidget() throws Exception {
		String budgetSurplus = "";
		if (areListElementVisible(budgetSurplusOnPayrollProjectionWidget, 10) && budgetSurplusOnPayrollProjectionWidget.size() == 2) {
			budgetSurplus = budgetSurplusOnPayrollProjectionWidget.get(1).getText();
			SimpleUtils.pass("Dashboard Page: Get budget surplus \""+ budgetSurplus + "\" on \"Payroll Projection\" widget");
		} else {
			SimpleUtils.fail("Dashboard Page: Current budget surplus not loaded on \"Payroll Projection\" widget", false);
		}
		return budgetSurplus;
	}

	@Override
	public void validateBudgetSurplusOnPayrollProjectionWidget(String budgetSurplusOnPayrollProjectionWidget, String budgetSurplusInScheduleTab) throws Exception {
		if (budgetSurplusOnPayrollProjectionWidget.contains("Under") && budgetSurplusInScheduleTab.contains("▼")) {
			budgetSurplusOnPayrollProjectionWidget = budgetSurplusOnPayrollProjectionWidget.contains(" ")? budgetSurplusOnPayrollProjectionWidget.split(" ")[0]:budgetSurplusOnPayrollProjectionWidget;
			budgetSurplusInScheduleTab = budgetSurplusInScheduleTab.contains(" ")? budgetSurplusInScheduleTab.split(" ")[0]:budgetSurplusInScheduleTab;
			SimpleUtils.report("Dashboard Page: The hours on Payroll Projection widget is \"" + budgetSurplusOnPayrollProjectionWidget + "\"");
			SimpleUtils.report("Schedule Page: The hours is \"" + budgetSurplusInScheduleTab + "\" in Schedule tab");
			if (budgetSurplusOnPayrollProjectionWidget.equals(budgetSurplusInScheduleTab))
				SimpleUtils.pass("Dashboard Page: The budget surplus on Payroll Projection widget is consistent with the hours in Schedule tab");
			else
				//SimpleUtils.fail("Dashboard Page: The budget surplus on Payroll Projection widget is inconsistent with the hours in Schedule tab",false);
			SimpleUtils.warn("SCH-2767: [DM View] Data accuracy is inconsistent on Dashboard");
		} else if (budgetSurplusOnPayrollProjectionWidget.contains("Over") && budgetSurplusInScheduleTab.contains("▲")) {
			budgetSurplusOnPayrollProjectionWidget = budgetSurplusOnPayrollProjectionWidget.contains(" ")? budgetSurplusOnPayrollProjectionWidget.split(" ")[0]:budgetSurplusOnPayrollProjectionWidget;
			budgetSurplusInScheduleTab = budgetSurplusInScheduleTab.contains(" ")? budgetSurplusInScheduleTab.split(" ")[0]:budgetSurplusInScheduleTab;
			SimpleUtils.report("Dashboard Page: The hours on Payroll Projection widget is \"" + budgetSurplusOnPayrollProjectionWidget + "\"");
			SimpleUtils.report("Schedule Page: The hours is \"" + budgetSurplusInScheduleTab + "\" in Schedule tab");
			if (budgetSurplusOnPayrollProjectionWidget.equals(budgetSurplusInScheduleTab))
				SimpleUtils.pass("Dashboard Page: The budget surplus on Payroll Projection widget is consistent with the hours in Schedule tab");
			else
				//SimpleUtils.fail("Dashboard Page: The budget surplus on Payroll Projection widget is inconsistent with the hours in Schedule tab",false);
				SimpleUtils.warn("SCH-2767: [DM View] Data accuracy is inconsistent on Dashboard");
		} else
			SimpleUtils.fail("Dashboard Page: The budget surplus on dashboard is inconsistent with the one on schedule, whatever the hours or caret",false);
	}

	@Override
	public void validateAsOfTimeOnPayrollProjectionWidget() throws Exception {
		if (isElementLoaded(asOfDateTimeOnPayrollProjectionWidget,5)) {
			Date asOfDate = null;
			String asOfDateTime = asOfDateTimeOnPayrollProjectionWidget.getText();
			//  Dec 10, 01:54 AM
			try {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, hh:mm a");
				asOfDate = sdf.parse(year + " " + asOfDateTime.replace("As of ",""));
				SimpleUtils.pass("Dashboard Page: Dashboard Page: As of date time is \"" + asOfDateTime + "\" on Payroll Projection widget");
			} catch (Exception e) {
				SimpleUtils.fail("Dashboard Page: Dashboard Page: As of date time format is \"" + asOfDateTime + "\" unexpectedly on Payroll Projection widget",false);
			}
			SimpleDateFormat sdfNew = new SimpleDateFormat("EEE");
			String asOfWeek = sdfNew.format(asOfDate);
			if (asOfLineOnPayrollProjectionWidget.findElement(By.xpath("./following-sibling::*[name()='text'][2]")).getText().equals(asOfWeek)) {
				SimpleUtils.pass("Dashboard Page: As of date time line displays in correct position between bars on Payroll Projection widget");
			} else {
				SimpleUtils.fail("Dashboard Page: As of date time line doesn't display in correct position between bars on Payroll Projection widget",false);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: As of date time failed to load on Payroll Projection widget",false);
		}
	}

	@Override
	public void validateTheFutureBudgetSurplusOnPayrollProjectionWidget() throws Exception {
		if (areListElementVisible(budgetSurplusOnPayrollProjectionWidget,10) && budgetSurplusOnPayrollProjectionWidget.size() ==2) {
			if (budgetSurplusOnPayrollProjectionWidget.get(0).getAttribute("class").contains("sch-clr-primary-red")) {
				if (budgetSurplusOnPayrollProjectionWidget.get(1).getText().contains("Hrs Over Budget"))
					SimpleUtils.pass("Dashboard Page: budget surplus \"" + budgetSurplusOnPayrollProjectionWidget.get(1).getText() + "\" on \"Payroll Projection\" widget is loaded in DM View");
				else
					SimpleUtils.fail("Dashboard Page: budget surplus \"" + budgetSurplusOnPayrollProjectionWidget.get(1).getText() + "\" on \"Payroll Projection\" widget failed to load or loaded incorrectly in DM View",false);
			}
			if (budgetSurplusOnPayrollProjectionWidget.get(0).getAttribute("class").contains("sch-clr-primary-green")) {
				if (budgetSurplusOnPayrollProjectionWidget.get(1).getText().contains("Hrs Under Budget"))
					SimpleUtils.pass("Dashboard Page: budget surplus \"" + budgetSurplusOnPayrollProjectionWidget.get(1).getText() + "\" on \"Payroll Projection\" widget is loaded in DM View");
				else
					SimpleUtils.fail("Dashboard Page: budget surplus \"" + budgetSurplusOnPayrollProjectionWidget.get(1).getText() + "\" on \"Payroll Projection\" widget failed to load or loaded incorrectly in DM View",false);
			}
		} else {
			SimpleUtils.fail("Dashboard Page: The budget surplus on \"Payroll Projection\" widget failed to load in DM View",false);
		}
	}

	@Override
	public void validateHoursTooltipsOfPayrollProjectionWidget() throws Exception {
		if (areListElementVisible(rectsOnPayrollProjectionWidget,5) && rectsOnPayrollProjectionWidget.size() == 21) {
			for (WebElement rect: rectsOnPayrollProjectionWidget) {
				// mouseHover(rect);
				// todo: Locate the tooltip web element and if it loads, it will pass, or else it will fail.
			}
			SimpleUtils.warn("SCH-2634: [DM Dashboard] The value tooltips should display when hover the mouse on the chart in Payroll Projection widget");
		} else
			SimpleUtils.fail("Dashboard Page: Hours on Timesheet Approval Rate widget failed to load",false);
	}

    @Override
    public void validateTheDataOfMyProfile() throws Exception {
        clickOnSubMenuOnProfile("My Profile");
        if (isElementLoaded(personalDetails, 20) && isElementLoaded(hrProfileInfo, 20) && isElementLoaded(legionInfo, 20)) {
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
        if (areListElementVisible(goToProfile, 10) && goToProfile.size() != 0) {
            for (WebElement e : goToProfile) {
                if (e.getText().toLowerCase().contains(subMenu.toLowerCase())) {
                    clickTheElement(e);
                    if (isElementLoaded(alertDialog, 5))
                        click(OKButton);
                    else clickTheElement(companyIconImg);
                    SimpleUtils.pass("Able to click on '" + subMenu + "' link Successfully!!");
                    break;
                }
            }
        } else {
            SimpleUtils.fail("'" + subMenu + "' failed to load", true);
        }
    }

    @Override
    public void validateTheDataOfMyWorkPreferences(String date) throws Exception {
        SimpleUtils.report(date);
        clickOnSubMenuOnProfile("My Work Preferences");
        if (areListElementVisible(userProfileSection, 10) && userProfileSection.size() == 3) {
            SimpleUtils.pass("My Work Preferences: It shows the Availability,  Availability Change Requests and Shift Preferences successfully");
            if (date.contains(",") && date.contains(" ")) {
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
                if (areListElementVisible(timeoffRequests, 10) && timeoffRequests.size() > 1) {
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
    public void verifyTheWelcomeMessageOfDM(String userName) throws Exception {
        String time = dmsTimeStamp.getText().contains(",") ? dmsTimeStamp.getText().split(",")[1] : dmsTimeStamp.getText();
        String greetingTime = getTimePeriod(time.toLowerCase());
        String expectedText = "Good " + greetingTime + ", " + userName + "." + "\n" + "Welcome to Legion" + "\n" + "Your Complete Workforce Engagement Solution";
        String actualText = "";
        if (isElementLoaded(detailWelcomeText, 5)) {
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
    public void validateRefreshWhenNavigationBack() throws Exception {
        String timestamp1 = "";
        String timestamp2 = "";
        if (isElementLoaded(lastUpdated, 5)) {
            timestamp1 = lastUpdated.getText();
        } else if (isElementLoaded(justUpdated, 5)) {
            timestamp1 = justUpdated.getText();
        } else
            SimpleUtils.fail("Dashboard Page: Timestamp failed to load", false);
        click(scheduleConsoleNameInTM);
        navigateToDashboard();
        if (isElementLoaded(lastUpdated, 5)) {
            timestamp2 = lastUpdated.getText();
        } else if (isElementLoaded(justUpdated, 5)) {
            timestamp2 = justUpdated.getText();
        } else
            SimpleUtils.fail("Dashboard Page: Timestamp failed to load", false);
        if (timestamp2.equals(timestamp1) && !timestamp1.equals("") && !refreshButton.getAttribute("label").equals("Refreshing...")) {
            SimpleUtils.pass("Dashboard Page: It keeps the previous Last Updated time, not refreshing every time");
        } else {
            SimpleUtils.fail("Dashboard Page: It doesn't keep the previous Last Updated time", false);
        }
    }


    @FindBy(css = "div.dms-row221")
    private WebElement schedulePublishStatusWidget;

    @FindBy(css = "div.dms-not-started")
    private WebElement notStartedLegend;

    @FindBy(css = "div.dms-in-progress")
    private WebElement inProgressLegend;

    @FindBy(css = "div.dms-published")
    private WebElement publishedLegend;

    @FindBy(css = "svg.schedule-publish-status-chart__svg")
    private WebElement schedulePublishStatusChart;

    @FindBy(css = "svg.schedule-publish-status-chart__svg rect")
    private List<WebElement> schedulePublishStatus;

    @FindBy(css = "div[class=\"dms-box-title dms-box-item-title-row ng-binding ng-scope\"]")
    private WebElement schedulePublishStatusWidgetTitle;


    public Map<String, Integer> getAllScheduleStatusFromSchedulePublishStatusWidget() {

        int scheduleNumber = Integer.parseInt(schedulePublishStatusWidget.findElements(By.cssSelector("[class=\"tick\"]")).get(1).getText());
        int notStartedNumberForCurrentWeek = Integer.parseInt(schedulePublishStatus.get(1).getAttribute("height").toString());
        int inProgressForCurrentWeek = Integer.parseInt(schedulePublishStatus.get(2).getAttribute("height").toString());
        int publishedForCurrentWeek = Integer.parseInt(schedulePublishStatus.get(3).getAttribute("height").toString());
        int notStartedNumberForNextWeek = Integer.parseInt(schedulePublishStatus.get(4).getAttribute("height").toString());
        int inProgressForNextWeek = Integer.parseInt(schedulePublishStatus.get(5).getAttribute("height").toString());
        int publishedForNextWeek = Integer.parseInt(schedulePublishStatus.get(6).getAttribute("height").toString());
        int notStartedNumberForTheWeekAfterNext = Integer.parseInt(schedulePublishStatus.get(7).getAttribute("height").toString());
        int inProgressForTheWeekAfterNext = Integer.parseInt(schedulePublishStatus.get(8).getAttribute("height").toString());
        int publishedForTheWeekAfterNext = Integer.parseInt(schedulePublishStatus.get(9).getAttribute("height").toString());

        Map<String, Integer> scheduleStatusFromSchedulePublisStatusWidget = new HashMap<>();
        int r = 10;
        r = rate(scheduleNumber, notStartedNumberForCurrentWeek, inProgressForCurrentWeek, publishedForCurrentWeek);
        scheduleStatusFromSchedulePublisStatusWidget.put("notStartedNumberForCurrentWeek", notStartedNumberForCurrentWeek / r);
        scheduleStatusFromSchedulePublisStatusWidget.put("inProgressForCurrentWeek", inProgressForCurrentWeek / r);
        scheduleStatusFromSchedulePublisStatusWidget.put("publishedForCurrentWeek", publishedForCurrentWeek / r);

        r = rate(scheduleNumber, notStartedNumberForNextWeek, inProgressForNextWeek, publishedForNextWeek);
        scheduleStatusFromSchedulePublisStatusWidget.put("notStartedNumberForNextWeek", notStartedNumberForNextWeek / r);
        scheduleStatusFromSchedulePublisStatusWidget.put("inProgressForNextWeek", inProgressForNextWeek / r);
        scheduleStatusFromSchedulePublisStatusWidget.put("publishedForNextWeek", publishedForNextWeek / r);

        r = rate(scheduleNumber, notStartedNumberForTheWeekAfterNext, inProgressForTheWeekAfterNext, publishedForTheWeekAfterNext);
        scheduleStatusFromSchedulePublisStatusWidget.put("notStartedNumberForTheWeekAfterNext", notStartedNumberForTheWeekAfterNext / r);
        scheduleStatusFromSchedulePublisStatusWidget.put("inProgressForTheWeekAfterNext", inProgressForTheWeekAfterNext / r);
        scheduleStatusFromSchedulePublisStatusWidget.put("publishedForTheWeekAfterNext", publishedForTheWeekAfterNext / r);

        return scheduleStatusFromSchedulePublisStatusWidget;

    }

    private int rate(int sum, int a, int b, int c) {
        int r = 10;
        while (a / r + b / r + c / r != sum)
            r++;
        return r;
    }

    public void clickOnViewSchedulesLinkInSchedulePublishStatusWidget() throws Exception {
        if (isElementLoaded(schedulePublishStatusWidget, 5)) {
            WebElement viewSchedulesLink = schedulePublishStatusWidget.findElement(By.cssSelector("[ng-click=\"viewSchedules()\"]"));
            if (isElementLoaded(viewSchedulesLink, 5)) {
                scrollToElement(viewSchedulesLink);
                click(viewSchedulesLink);
                SimpleUtils.pass("View Schedules link successfully");
            } else {
                SimpleUtils.fail("View Schedules link not loaded successfully", false);
            }
        }
    }

    public boolean isSchedulePublishStatusWidgetDisplay() throws Exception {
        boolean isSchedulePublishStatusWidgetDisplay = false;
        if (isElementLoaded(schedulePublishStatusWidget, 5)) {
            isSchedulePublishStatusWidgetDisplay = true;
            SimpleUtils.report("Schedule Publish Status Widget is loaded Successfully!");
        } else
            SimpleUtils.report("Schedule Publish Status Widget not loaded Successfully!");
        return isSchedulePublishStatusWidgetDisplay;
    }

    public void verifyTheContentInSchedulePublishStatusWidget() throws Exception {
        if (isElementLoaded(schedulePublishStatusWidget, 5)) {

            List<String> weekTextsFromSchedulePage = getThreeWeeksInfoFromSchedulePage();
            List<WebElement> legendTexts = schedulePublishStatusWidget.findElements(By.className("dms-legend-text"));
            List<WebElement> weekTexts = schedulePublishStatusChart.findElements(By.cssSelector("text[text-anchor=\"middle\"]"));
            String test1 = weekTexts.get(0).getText().replace(" 0", " ");
            String test2 = weekTextsFromSchedulePage.get(0);
            WebElement viewSchedulesLink = schedulePublishStatusWidget.findElement(By.cssSelector("[ng-click=\"viewSchedules()\"]"));
            if (schedulePublishStatusWidgetTitle.getText().equalsIgnoreCase("Schedule Publish Status")
                    && isElementLoaded(schedulePublishStatusWidgetTitle, 5)
                    && isElementLoaded(notStartedLegend, 5)
                    && isElementLoaded(inProgressLegend, 5)
                    && isElementLoaded(publishedLegend, 5)
                    && areListElementVisible(legendTexts) && legendTexts.size() == 3
                    && legendTexts.get(0).getText().equalsIgnoreCase("Not Started")
                    && legendTexts.get(1).getText().equalsIgnoreCase("In Progress")
                    && legendTexts.get(2).getText().equalsIgnoreCase("Published")
                    && isElementLoaded(schedulePublishStatusChart, 5)
                    && areListElementVisible(weekTexts, 5) && weekTexts.size() == 3
                    && weekTexts.get(0).getText().replace(" 0", " ").equalsIgnoreCase(weekTextsFromSchedulePage.get(0))
                    && weekTexts.get(1).getText().replace(" 0", " ").equalsIgnoreCase(weekTextsFromSchedulePage.get(1))
                    && weekTexts.get(2).getText().replace(" 0", " ").equalsIgnoreCase(weekTextsFromSchedulePage.get(2))
                    && isElementLoaded(viewSchedulesLink, 5)) {
                SimpleUtils.pass("The content in Schedule Publish Status widget display correctly");
            } else {
                SimpleUtils.fail("The content in Schedule Publish Status widget display incorrectly", false);
            }
        } else
            SimpleUtils.report("Schedule Publish Status widget not loaded Successfully!");
    }


    @FindBy(css = "[ng-click=\"selectPeriod(p)\"] [class=\"ng-binding\"]")
    private List<WebElement> weeksInfoInWeekPicker;

    @FindBy(className = "day-week-picker-arrow-right")
    private WebElement calendarNavigationNextWeekArrow;

    public List<String> getThreeWeeksInfoFromSchedulePage() {
        List<String> weeksInfo = new ArrayList<>();
        click(scheduleConsoleMenu);
        if (areListElementVisible(weeksInfoInWeekPicker, 10)) {
            String currentWeek = weeksInfoInWeekPicker.get(1).getText().split("\n")[1];
            String nextWeek = weeksInfoInWeekPicker.get(2).getText().split("\n")[1];
            click(calendarNavigationNextWeekArrow);
            String theWeekAfterNext = weeksInfoInWeekPicker.get(0).getText().split("\n")[1];
            weeksInfo.add(currentWeek);
            weeksInfo.add(nextWeek);
            weeksInfo.add(theWeekAfterNext);
        } else {
            SimpleUtils.fail("Week picker loaded fail", false);
        }
        click(dashboardConsoleMenu);
        return weeksInfo;
    }

	@FindBy(css = "div[class=\"dms-row21\"]")
	private WebElement scheduleVsGuidanceByDayWidget;

	@FindBy(css = "div[class=\"dms-box-title dms-box-item-title-row ng-binding col-sm-5\"]")
	private WebElement scheduleVsGuidanceByDayWidgetTitle;

	@FindBy(css = "div.dms-budgeted")
	private WebElement budgetedLegend;

	@FindBy(css = "div.dms-scheduled")
	private WebElement scheduledLegend;

	@FindBy(css = "[class=\"payroll-projection-chart__svg\"] rect")
	private List<WebElement> scheduleVsGuidanceChartBars;

	@FindBy(css = "[class=\"payroll-projection-chart__svg\"]")
	private WebElement scheduleVsGuidanceChart;

	@FindBy(css = "div.text-right.dms-legend-text")
	private WebElement weekInfoOnScheduleVsGuidanceByDayWidget;

	@FindBy(css = "[class=\"dms-row21\"] .dms-caret-large")
	private WebElement budgetHoursCaret;

	@FindBy(css = "span.dms-box-item-title.dms-box-item-title-row")
	private WebElement budgetHoursMessageSpan;

	public boolean isScheduleVsGuidanceByDayWidgetDisplay() throws Exception {
		boolean isScheduleVsGuidanceByDayWidgetDisplay = false;
		if (isElementLoaded(scheduleVsGuidanceByDayWidget, 5)) {
			isScheduleVsGuidanceByDayWidgetDisplay = true;
			SimpleUtils.report("Schedule Vs Guidance By Day Widget is loaded Successfully!");
		} else
			SimpleUtils.report("Schedule Vs Guidance By Day Widget not loaded Successfully!");
		return isScheduleVsGuidanceByDayWidgetDisplay;
	}

	public void verifyTheContentOnScheduleVsGuidanceByDayWidget() throws Exception {
		List<WebElement> legendTexts = scheduleVsGuidanceByDayWidget.findElements(By.cssSelector(".ml-10.dms-legend-text"));
		WebElement viewSchedulesLink = scheduleVsGuidanceByDayWidget.findElement(By.cssSelector("[ng-click=\"viewSchedules()\"]"));
		if(isElementLoaded(scheduleVsGuidanceByDayWidgetTitle, 5)
				&& isElementLoaded(budgetedLegend, 5)
				&& isElementLoaded(scheduledLegend, 5)
				&& areListElementVisible(legendTexts, 5) && legendTexts.size() ==2
				&& isElementLoaded(scheduleVsGuidanceChart, 5)
				&& isElementLoaded(weekInfoOnScheduleVsGuidanceByDayWidget, 5)
				&& weekInfoOnScheduleVsGuidanceByDayWidget.getText().equalsIgnoreCase(getWeekInfoFromDMView().substring(8))
				&& areListElementVisible(scheduleVsGuidanceChartBars, 5)
				&& isElementLoaded(budgetHoursCaret, 5)
				&& isElementLoaded(viewSchedulesLink, 5)){
			SimpleUtils.pass("The content on Schedule Vs Guidance By Day Widget display correctly! ");
		} else
			SimpleUtils.fail("The content on Schedule Vs Guidance By Day Widget display incorrectly! ", false);
	}

	public void verifyTheHrsUnderOrCoverBudgetOnScheduleVsGuidanceByDayWidget() throws Exception {

		if (isElementLoaded(budgetHoursMessageOnLocationSummaryWidget, 5)) {
			if (isElementLoaded(budgetHoursMessageSpan, 5) && isElementLoaded(budgetHoursCaret, 5)){
				if (budgetHoursMessageSpan.getText().contains("Under")) {
					if(budgetHoursMessageSpan.getAttribute("class").contains("green")
							&&budgetHoursCaret.getAttribute("class").contains("green")
							&&budgetHoursCaret.getAttribute("class").contains("down")){
						SimpleUtils.pass("Budget hrs message display correctly when scheduled hours under budget!");
					} else {
						SimpleUtils.fail("Budget hrs message display incorrectly when scheduled hours under budget!", false);
					}
				} else {
					if(budgetHoursCaret.getAttribute("class").contains("red")
							&&budgetHoursCaret.getAttribute("class").contains("up")){
						SimpleUtils.pass("Budget hrs caret and message display correctly when scheduled hours cover budget!");
					} else {
						SimpleUtils.fail("Budget hrs message display incorrectly when scheduled hours under budget!", false);
					}
				}

				//compare the hours on widget and on schedule page
				String budgetHoursFromDashboard = budgetHoursMessageSpan.getText().split(" ")[0];
				click(scheduleConsoleMenu);
				ScheduleDMViewPage scheduleDMViewPage = new ConsoleScheduleDMViewPage();
				String budgetHoursFromSchedulePage = scheduleDMViewPage.
						getTextFromTheChartInLocationSummarySmartCard().get(4).split(" ")[0];
				if (budgetHoursFromDashboard.equalsIgnoreCase(budgetHoursFromSchedulePage)) {
					SimpleUtils.pass("Budget hrs display correctly on Schedule Vs Guidance By Day Widget!");
				} else
					SimpleUtils.fail("Budget hrs display incorrectly on Schedule Vs Guidance By Day Widget!", false);
			}
		} else {
			if (isElementLoaded(budgetHoursCaret, 5)&& !isElementLoaded(budgetHoursMessageSpan)){
				SimpleUtils.pass("Budget hrs caret display correctly and no message display because the budget hour and schedule hours are consistent! ");
			} else
				SimpleUtils.fail("Budget hrs caret and message display incorrectly when the budget hour and schedule hours are inconsistent!!", false);
		}
	}

	public boolean areBudgetHoursAndScheduleHoursConsistent(){
		boolean areBudgetHoursAndScheduleHoursConsistent = false;
		if(scheduleVsGuidanceChartBars.get(1).getAttribute("height").toString().
				equals(scheduleVsGuidanceChartBars.get(2).getAttribute("height").toString())
				&& scheduleVsGuidanceChartBars.get(3).getAttribute("height").toString().
				equals(scheduleVsGuidanceChartBars.get(4).getAttribute("height").toString())
				&& scheduleVsGuidanceChartBars.get(5).getAttribute("height").toString().
				equals(scheduleVsGuidanceChartBars.get(6).getAttribute("height").toString())
				&& scheduleVsGuidanceChartBars.get(7).getAttribute("height").toString().
				equals(scheduleVsGuidanceChartBars.get(8).getAttribute("height").toString())
				&& scheduleVsGuidanceChartBars.get(9).getAttribute("height").toString().
				equals(scheduleVsGuidanceChartBars.get(10).getAttribute("height").toString())
				&& scheduleVsGuidanceChartBars.get(11).getAttribute("height").toString().
				equals(scheduleVsGuidanceChartBars.get(12).getAttribute("height").toString())
				&& scheduleVsGuidanceChartBars.get(13).getAttribute("height").toString().
				equals(scheduleVsGuidanceChartBars.get(14).getAttribute("height").toString())) {
			areBudgetHoursAndScheduleHoursConsistent = true;
			SimpleUtils.report("Budget Hours and Schedule Hours are consistent! ");
		} else {
			SimpleUtils.report("Budget Hours and Schedule Hours are inconsistent");
		}
		return areBudgetHoursAndScheduleHoursConsistent;
	}

	@FindBy(css = "div.dms-row1")
	private WebElement locationSummaryWidget;

	@FindBy(css = "div[class=\"dms-box-title dms-box-item-title-row ng-binding\"]")
	private WebElement locationSummaryWidgetTitle;

	@FindBy(css = "div.dms-box-item-title.dms-box-item-title-color-light")
	private List<WebElement> scheduledHoursTitles;

	@FindBy(css = "span.dms-box-item-number-small")
	private List<WebElement> scheduledHours;

	@FindBy(css = "span.dms-time-stamp")
	private WebElement projectedHoursAsCurrentTime;

	@FindBy(css = "[ng-if=\"b.withinBudget\"]")
	private WebElement projectedWithinBudgetCaret;

	@FindBy(css = "[ng-if=\"!b.withinBudget\"]")
	private WebElement projectedOverBudgetCaret;

	@FindBy(css = "span.dms-box-item-title-color-dark")
	private List<WebElement> projectedWithInOrOverBudgetLocations;

	@FindBy(css = "div.dms-box-item-title-2.pt-15")
	private List<WebElement> projectedWithInOrOverBudgetMessage;

	@FindBy(css = "i.dms-caret-small")
	private WebElement budgetHoursCaretOnLocationSummaryWidget;

	@FindBy(css = "span.dms-box-item-unit-trend")
	private WebElement budgetHoursMessageOnLocationSummaryWidget;

	public void verifyTheHrsOverOrUnderBudgetOnLocationSummaryWidget() throws Exception {
		if(isElementLoaded(budgetHoursMessageSpan, 5) && isElementLoaded(budgetHoursCaret, 5)){
			if(isElementLoaded(budgetHoursCaretOnLocationSummaryWidget, 5)
					&& isElementLoaded(budgetHoursMessageOnLocationSummaryWidget, 5)){
				//Verify the caret is display correctly
				if(budgetHoursCaret.getAttribute("class").contains("up")){
					if (budgetHoursCaretOnLocationSummaryWidget.getAttribute("class").contains("up")
							&& budgetHoursCaretOnLocationSummaryWidget.getAttribute("class").contains("red")){
						SimpleUtils.pass("The budget hour caret display correctly! ");
					} else
						SimpleUtils.fail("The budget hour caret display incorrectly! ", false);
				} else {
					if (budgetHoursCaretOnLocationSummaryWidget.getAttribute("class").contains("down")
							&& budgetHoursCaretOnLocationSummaryWidget.getAttribute("class").contains("green")){
						SimpleUtils.pass("The budget hour caret display correctly! ");
					} else
						SimpleUtils.fail("The budget hour caret display incorrectly! ", false);
				}

				//Verify the message display correctly
				if (budgetHoursMessageOnLocationSummaryWidget.getText().split(" ")[0].
						equals(budgetHoursMessageSpan.getText().split(" ")[0])) {
					SimpleUtils.pass("The budget hour message display correctly! ");
				} else
					SimpleUtils.fail("The budget hour message display incorrectly! ", false);
			} else
				SimpleUtils.fail("The hours over or under budget panel on Location Summary widget loaded fail! ", false);
		} else{
			if(!isElementLoaded(budgetHoursCaretOnLocationSummaryWidget, 5)
					&& !isElementLoaded(budgetHoursMessageOnLocationSummaryWidget, 5)){
				SimpleUtils.pass("The budget hours message panel is not display on Location Summary widget! ");
			} else
				SimpleUtils.fail("The budget hours message panel on Location Summary widget should not display! ", false);
		}
	}

	public List<String> getTheDataOnLocationSummaryWidget() throws Exception {
		/*
		*  0: budgeted Hrs
		*  1: scheduled Hrs
		*  2: projected Hrs
		*  3: projected Within Budget Locations
		*  4: projected Over Budget Locations
		*  5: the Hrs Over Or Under Budget
		* */
		List<String> dataOnLocationSummaryWidget = new ArrayList<>();
		if(areListElementVisible(scheduledHours, 5)
				&& scheduledHours.size()==3
				&& areListElementVisible(projectedWithInOrOverBudgetLocations, 5)
				&& projectedWithInOrOverBudgetLocations.size()==2){

			String budgetedHrs = scheduledHours.get(0).getText().replaceAll(",","");
			dataOnLocationSummaryWidget.add(budgetedHrs);
			String scheduledHrs = scheduledHours.get(1).getText().replaceAll(",","");
			dataOnLocationSummaryWidget.add(scheduledHrs);
			String projectedHrs = scheduledHours.get(2).getText().replaceAll(",","");
			dataOnLocationSummaryWidget.add(projectedHrs);
			String projectedWithinBudgetLocation = projectedWithInOrOverBudgetLocations.get(0).getText();
			dataOnLocationSummaryWidget.add(projectedWithinBudgetLocation);
			String projectedOverBudgetLocation = projectedWithInOrOverBudgetLocations.get(1).getText();
			dataOnLocationSummaryWidget.add(projectedOverBudgetLocation);
			String theHrsOverOrUnderBudget = "0 Hrs";
			if (isElementLoaded(budgetHoursMessageOnLocationSummaryWidget, 5)){
				theHrsOverOrUnderBudget = budgetHoursMessageOnLocationSummaryWidget.getText();
			}
			dataOnLocationSummaryWidget.add(theHrsOverOrUnderBudget);
			SimpleUtils.report("Get the data on location summary widget successfully! ");
		} else
			SimpleUtils.fail("The data on Location Summary Widget loaded fail! ", false);
		return dataOnLocationSummaryWidget;
	}

	public void verifyTheContentOnLocationSummaryWidget() throws Exception {
		WebElement viewSchedulesLink = locationSummaryWidget.findElement(By.cssSelector("[ng-click=\"viewSchedules()\"]"));
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("MMM dd, hh:mm a");
		String currentTimeOnProjectedHrs = "As of "+ SimpleUtils.getCurrentDateMonthYearWithTimeZone("PST", dateFormat);
		clickOnRefreshButton();
		String test1 = projectedHoursAsCurrentTime.getText();
		if(isElementLoaded(locationSummaryWidgetTitle, 5)
				&& locationSummaryWidgetTitle.getText().contains("Locations Summary")
				&& areListElementVisible(scheduledHoursTitles, 5)
				&& scheduledHoursTitles.size() == 3
				&& scheduledHoursTitles.get(0).getText().equalsIgnoreCase("Budgeted")
				&& scheduledHoursTitles.get(1).getText().equalsIgnoreCase("Scheduled")
				&& scheduledHoursTitles.get(2).getText().equalsIgnoreCase("Projected")
				&& areListElementVisible(scheduledHours, 5)
				&& scheduledHours.size() == 3
				&& isElementLoaded(projectedHoursAsCurrentTime, 5)
				&& projectedHoursAsCurrentTime.getText().equalsIgnoreCase(currentTimeOnProjectedHrs)
				&& isElementLoaded(projectedWithinBudgetCaret, 5)
				&& projectedWithinBudgetCaret.getAttribute("class").contains("down")
				&& isElementLoaded(projectedOverBudgetCaret, 5)
				&& projectedOverBudgetCaret.getAttribute("class").contains("up")
				&& areListElementVisible(projectedWithInOrOverBudgetLocations, 5)
				&& projectedWithInOrOverBudgetLocations.size() ==2
				&& areListElementVisible(projectedWithInOrOverBudgetMessage, 5)
				&& projectedWithInOrOverBudgetMessage.size() ==2
				&& projectedWithInOrOverBudgetMessage.get(0).getText().equalsIgnoreCase("Projected Within Budget")
				&& projectedWithInOrOverBudgetMessage.get(1).getText().equalsIgnoreCase("Projected Over Budget")
				&& isElementLoaded(viewSchedulesLink, 5)){
			SimpleUtils.pass("The content on Location Summary Widget display correctly! ");
		} else
			SimpleUtils.fail("The content on Location Summary Widget display incorrectly! ", false);
	}

	public boolean isLocationSummaryWidgetDisplay() throws Exception {
		boolean isLocationSummaryWidgetDisplay = false;
		if (isElementLoaded(locationSummaryWidget, 5)) {
			isLocationSummaryWidgetDisplay = true;
			SimpleUtils.report("Location Summary Widget is loaded Successfully!");
		} else
			SimpleUtils.report("Location Summary Widget not loaded Successfully!");
		return isLocationSummaryWidgetDisplay;
	}

    @FindBy(css = "div.dms-smart-card-4.fl-left")
	private WebElement openShiftsWidgetInDMView;
	@Override
	public boolean isOpenShiftsWidgetDisplay() throws Exception {
		boolean result = false;
		if (isElementLoaded(openShiftsWidgetInDMView, 5)) {
			result = true;
		}
		return result;
	}

	@Override
	public void clickViewSchedulesLinkOnOpenShiftsWidget() throws Exception {
		if (isElementLoaded(openShiftsWidgetInDMView, 5) && isElementLoaded(openShiftsWidgetInDMView.findElement(By.cssSelector("[ng-click=\"viewSchedules()\"]")),5)) {
			click(openShiftsWidgetInDMView.findElement(By.cssSelector("[ng-click=\"viewSchedules()\"]")));
			SimpleUtils.pass("Open Shifts Widget is loaded Successfully!");
		} else {
			SimpleUtils.report("Open Shifts Widget not loaded correctly!");
		}
	}

	@Override
	public HashMap<String, Integer> verifyContentOfOpenShiftsWidgetForDMView() throws Exception {
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		if (isElementLoaded(openShiftsWidgetInDMView.findElement(By.cssSelector(".dms-box-item-title-row")), 5) && openShiftsWidgetInDMView.findElement(By.cssSelector(".dms-box-item-title-row")).getText().equalsIgnoreCase("open shifts")) {
			SimpleUtils.pass("Open Shifts title is correct!");
		} else {
			SimpleUtils.report("Open Shifts title not loaded correctly!");
		}
		if (openShiftsWidgetInDMView.findElements(By.cssSelector(".dms-legend")).size()==2 && openShiftsWidgetInDMView.findElement(By.cssSelector(".dms-legend-text")).getText().toLowerCase().contains("open")
				&& openShiftsWidgetInDMView.findElement(By.cssSelector(".dms-legend-text")).getText().toLowerCase().contains("assigned")) {
			SimpleUtils.pass("Open Shifts legends are correct!");
		} else {
			SimpleUtils.report("Open Shifts legends are not loaded correctly!");
		}
		if (areListElementVisible(openShiftsWidgetInDMView.findElements(By.cssSelector("div.lg-dashboard-charts text")),10)
				&& openShiftsWidgetInDMView.findElements(By.cssSelector("div.lg-dashboard-charts text")).size() ==2) {
			String open = openShiftsWidgetInDMView.findElements(By.cssSelector("div.lg-dashboard-charts text")).get(0).getText().replace("%","");
			String assigned = openShiftsWidgetInDMView.findElements(By.cssSelector("div.lg-dashboard-charts text")).get(1).getText().replace("%","");
			if (SimpleUtils.isNumeric(open) && SimpleUtils.isNumeric(assigned)){
				Integer openValue = Integer.parseInt(open);
				Integer assignedValue = Integer.parseInt(assigned);
				results.put("open",openValue);
				results.put("assigned", assignedValue);
			} else {
				SimpleUtils.fail("No chart value you want!", false);
			}

			SimpleUtils.pass("Open Shifts legends are correct!");
		} else {
			SimpleUtils.report("Open Shifts legends are not loaded correctly!");
		}
		return results;
	}
}
