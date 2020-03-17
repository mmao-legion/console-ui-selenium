package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import java.util.Map;

import com.legion.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.pages.core.ConsoleControlsPage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class DashboardTest extends TestBase{
	
	private static Map<String, String> propertyMap = SimpleUtils.getParameterMap();
    
	@Override
	@BeforeMethod()
  	public void firstTest(Method testMethod, Object[] params) throws Exception{
	  this.createDriver((String)params[0],"69","Window");
      visitPage(testMethod);
      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }
	
	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-4961: Empty Dashboard issue ")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void navigateToDashboardFromGlobalSettingInternalAdmin(String username, String password, String browser, String location) throws Throwable { 
    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
    	ControlsPage consoleControlsPage = pageFactory.createConsoleControlsPage();
    	consoleControlsPage.gotoControlsPage();
    	dashboardPage.verifySuccessfulNavToDashboardnLoading();
    	consoleControlsPage.gotoControlsPage();
    	consoleControlsPage.clickGlobalSettings();
    	dashboardPage.verifySuccessfulNavToDashboardnLoading();    	
    }
    
    @Automated(automated ="Manual")
	@Owner(owner = "Gunjan")
    @Enterprise(name = "Coffee2_Enterprise")
	@TestName(description = "LEG-5231: Team Lead Should not see Today's Forecast and Projected Demand Graph present in Dashboard Section")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void todayForecastAndProjectedDemandGraphTeamLead(String username, String password, String browser, String location) throws Exception { 
    	SimpleUtils.pass("Login into LegionCooffee2 Application Successfully!");
    	SimpleUtils.pass("Navigate to Dashboard Page Successfully!");
    	SimpleUtils.pass("assert Today's Forecast and Projected Demand Graph should not be present for Team lead and Team member");	
    }

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "Coffee_Enterprise")
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
		// So the following verification will fail since this bug
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
