package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.*;

import com.legion.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class DashboardTestKendraScott2 extends TestBase{
	
	private static Map<String, String> propertyMap = SimpleUtils.getParameterMap();
	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		  this.createDriver((String)params[0],"69","Window");
	      visitPage(testMethod);
	      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	  }
	
	@Automated(automated ="Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-4961: Should be able to set Location at Global Level")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void navigateToDashboardFromGlobalSetting(String username, String password, String browser, String location) throws Exception { 
    	SimpleUtils.pass("Navigate to Dashboard Page Successfully!");
    	SimpleUtils.pass("Click on Settings menu");
    	SimpleUtils.pass("Go back to Dashboard assert Dashboard loaded Successfully");
    	SimpleUtils.pass("Click on Settings menu again");
    	SimpleUtils.pass("Click on Global icon present next to Settings at top left section");
    	SimpleUtils.pass("Navigate back to Dashboard Page");
    	SimpleUtils.pass("assert Dashboard page is Loaded Successfully!");	
    }
    
    @Automated(automated ="Manual")
	@Owner(owner = "Gunjan")
    @Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-5231: Team Lead Should not see Today's Forecast and Projected Demand Graph present in Dashboard Section")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void todayForecastAndProjectedDemandGraphTeamLead(String username, String password, String browser, String location) throws Exception { 
    	SimpleUtils.pass("Login into LegionCooffee2 Application Successfully!");
    	SimpleUtils.pass("Navigate to Dashboard Page Successfully!");
    	SimpleUtils.fail("assert Today's Forecast and Projected Demand Graph should not be present for Team lead and Team member",false);	
    }

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the Store Company Location")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheDisplayLocationWithSelectedLocation(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		locationSelectorPage.verifyTheDisplayLocationWithSelectedLocationConsistent();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the functionality of Change Location button on click")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheClickActionOnChangeLocationButton(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		locationSelectorPage.verifyClickChangeLocationButton();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the content getting displayed in the Change Location flyout")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheContentDisplayedInChangeLocationLayout(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		locationSelectorPage.verifyTheContentOfDetailLocations();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the functionality of Search textbox in Change Location flyout")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfSearchTextBox(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		List<String> testStrings = new ArrayList<>(Arrays.asList("s", "h", "W"));
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		locationSelectorPage.verifyTheFunctionOfSearchTextBox(testStrings);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Dashboard functionality")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyDashboardFunctionality(String browser, String username, String password, String location) throws Exception {
		HashMap<String, String> upComingShifts = new HashMap<>();
		HashMap<String, String> fourShifts = new HashMap<>();
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SchedulePage schedulePage = null;
		dashboardPage.verifyDashboardPageLoadedProperly();
		// Verify the Welcome section
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String nickName = profileNewUIPage.getNickNameFromProfile();
		dashboardPage.verifyTheWelcomeMessage(nickName);
		// Verify Today's forecast section > Projected Demand graph is present
		// LEG-9104: Projected Demand and Shoppers are showing up Zero for Current and future weeks
		// TODO: following check will fail since LEG-9104
		dashboardPage.isProjectedDemandGraphShown();
		HashMap<String, String> hoursOnDashboard = dashboardPage.getHoursFromDashboardPage();
		String dateFromDashboard = dashboardPage.getCurrentDateFromDashboard();
		String timeFromDashboard = dashboardPage.getCurrentTimeFromDashboard();
		schedulePage = dashboardPage.goToTodayForNewUI();
		SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(
				ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , true);
		// Verify View Today's schedule button is working and navigating to the schedule page[Current date in day view]
		schedulePage.isScheduleForCurrentDayInDayView(dateFromDashboard);
		HashMap<String, String> hoursOnSchedule = schedulePage.getHoursFromSchedulePage();
		// Verify scheduled and other hours are matching with the Schedule smart card of Schedule page
		if(hoursOnDashboard != null && hoursOnSchedule != null){
			if(hoursOnDashboard.equals(hoursOnSchedule)){
				SimpleUtils.pass("Data Source for Budget, Scheduled and Other are consistent with the data on schedule page!");
			}else{
				SimpleUtils.fail("Data Source for Budget, Scheduled and Other are inconsistent with the data " +
						"on schedule page!", true);
			}
		}else{
			SimpleUtils.fail("Failed to get the hours!", true);
		}
		// Verify that Starting soon shifts and Scheduled hours are not showing when current week's schedule is in Guidance or Draft
		if (!schedulePage.isGenerateButtonLoaded()) {
			schedulePage.unGenerateActiveScheduleScheduleWeek();
			schedulePage.isGenerateButtonLoaded();
		}
		dashboardPage.navigateToDashboard();
		boolean startingSoonLoaded = dashboardPage.isStartingSoonLoaded();
		HashMap<String, String> hours = dashboardPage.getHoursFromDashboardPage();
		// LEG-8474: When schedule of Current week is in Guidance, still data is showing on Dashboard
		// TODO: following check will fail since LEG-8474
		dashboardPage.verifyStartingSoonNScheduledHourWhenGuidanceOrDraft(startingSoonLoaded, hours.get("Scheduled"));
		// Verify starting soon section
		schedulePage = dashboardPage.goToTodayForNewUI();
		schedulePage.isSchedule();
		if (!schedulePage.isPublishButtonLoaded()) {
			schedulePage.generateOrUpdateAndGenerateSchedule();
		}
		schedulePage.publishActiveSchedule();
		dashboardPage.navigateToDashboard();
		dashboardPage.verifyDashboardPageLoadedProperly();
		startingSoonLoaded = dashboardPage.isStartingSoonLoaded();
		boolean isStartingTomorrow = dashboardPage.isStartingTomorrow();
		if (startingSoonLoaded) {
			upComingShifts = dashboardPage.getUpComingShifts();
			schedulePage = dashboardPage.goToTodayForNewUI();
			schedulePage.isSchedule();
			fourShifts = schedulePage.getFourUpComingShifts(isStartingTomorrow, timeFromDashboard);
			schedulePage.verifyUpComingShiftsConsistentWithSchedule(upComingShifts, fourShifts);
		}else {
			SimpleUtils.fail("Shifts failed to load on Dashboard when the schedule is published!", true);
		}
	}

}
