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

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify dashboard functionality when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyDashboardFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String nickName = profileNewUIPage.getNickNameFromProfile();

    	//T1838579 Validate the TM accessible tabs.
		dashboardPage.validateTMAccessibleTabs();

		//T1838580 Validate the presence of location.
		dashboardPage.validateThePresenceOfLocation();

		//T1838581 Validate the accessible location.
		dashboardPage.validateTheAccessibleLocation();

		//T1838582 Validate the presence of logo.
		dashboardPage.validateThePresenceOfLogo();

		//T1838584 Validate the visibility of Username.
		dashboardPage.validateTheVisibilityOfUsername(nickName);

		//T1838583 Validate the information after selecting different location.
		dashboardPage.validateDateAndTimeAfterSelectingDifferentLocation();
		SchedulePage schedulePageTM = pageFactory.createConsoleScheduleNewUIPage();
		schedulePageTM.clickOnScheduleConsoleMenuItem();
		List<String> scheduleListTM = schedulePageTM.getWeekScheduleShiftTimeListOfMySchedule();
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		String fileName = "UsersCredentials.json";
		fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
		HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
		Object[][] internalAdminCredentials = userCredentials.get("InternalAdmin");
		loginToLegionAndVerifyIsLoginDone(String.valueOf(internalAdminCredentials[0][0]), String.valueOf(internalAdminCredentials[0][1])
				, String.valueOf(internalAdminCredentials[0][2]));
		SchedulePage schedulePageAdmin = pageFactory.createConsoleScheduleNewUIPage();
		schedulePageAdmin.goToConsoleScheduleAndScheduleSubMenu();
		List<String> scheduleListAdmin = schedulePageAdmin.getWeekScheduleShiftTimeListOfWeekView(nickName);
		if (scheduleListTM != null && scheduleListTM.size() > 0 && scheduleListAdmin != null && scheduleListAdmin.size() > 0) {
			if (scheduleListTM.size() == scheduleListAdmin.size() && scheduleListTM.containsAll(scheduleListAdmin)) {
				SimpleUtils.pass("Schedules in TM view is consistent with the Admin view of the location successfully");
			} else
				SimpleUtils.fail("Schedule doesn't show of the location correctly", true);
		} else {
			SimpleUtils.report("Schedule may have not been generated");
		}
		loginPage.logOut();
		loginToLegionAndVerifyIsLoginDone(username, password, location);

		//T1838585 Validate date and time.
		dashboardPage.validateDateAndTime();

		//T1838586 Validate the upcoming schedules.
		dashboardPage.validateTheUpcomingSchedules(nickName);

		//T1838587 Validate the click ability of VIEW MY SCHEDULE button.
		dashboardPage.validateVIEWMYSCHEDULEButtonClickable();
		dashboardPage.navigateToDashboard();

		//T1838588 Validate the visibility of profile picture.
		dashboardPage.validateTheVisibilityOfProfilePicture();

		//T1838589 Validate the click ability of Profile picture icon.
		dashboardPage.validateProfilePictureIconClickable();

		//T1838590 Validate the visibility of Profile.
		dashboardPage.validateTheVisibilityOfProfile();

		//T1838591 Validate the click ability of My profile, My Work Preferences, My Time off.
		dashboardPage.validateProfileDropdownClickable();

		//T1838592 Validate the data of My profile.
		dashboardPage.validateTheDataOfMyProfile();

		//T1838593 Validate the functionality My Work Preferences and My Availability.
		dashboardPage.navigateToDashboard();
		String dateFromDashboard = dashboardPage.getCurrentDateFromDashboard();
		dashboardPage.validateTheDataOfMyWorkPreferences(dateFromDashboard);

		//T1838594 Validate the presence of data on Time off page.
		dashboardPage.validateTheDataOfMyTimeOff();
	}

}
