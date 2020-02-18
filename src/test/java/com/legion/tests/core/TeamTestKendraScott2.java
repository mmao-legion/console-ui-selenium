package com.legion.tests.core;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.legion.pages.*;

import java.util.Map;

import org.apache.poi.ss.formula.ptg.ControlPtg;
import org.openqa.selenium.WebElement;
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

import static com.legion.utils.MyThreadLocal.*;

public class TeamTestKendraScott2 extends TestBase{
	
	public enum timeOffRequestAction{
	  Approve("APPROVE"),
	  Reject("REJECT");
		private final String value;
		timeOffRequestAction(final String newValue) {
		    value = newValue;
		}
		public String getValue() { return value; }
	}
	
	public enum timeOffRequestStatus{
		  Approved("APPROVED"),
		  Pending("PENDING"),
		  Cancelled("CANCELLED"),
		  Rejected("REJECTED");
			private final String value;
			timeOffRequestStatus(final String newValue) {
			    value = newValue;
			}
			public String getValue() { return value; }
	}
	
	private static Map<String, String> propertyMap = SimpleUtils.getParameterMap();
	private static Map<String, String> searchDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/searchDetails.json");
	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		  this.createDriver((String)params[0],"69","Window");
	      visitPage(testMethod);
	      loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
	  }
	 @Automated(automated = "Manual")
	 @Owner(owner = "Gunjan")
	 @Enterprise(name = "KendraScott2_Enterprise")
	 @TestName(description = "LEG-4978: In Team Page ,Coverage section is not displayed for LegionTech for Nov 4- Nov 10")
	 @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	 public void coverageForTeamPageNotWorking(String username, String password, String browser, String location)
	          throws Exception
	 {
	       SimpleUtils.pass("Login to leginTech Successfully");
	       SimpleUtils.pass("Successfully opened the Team Page");
	       SimpleUtils.pass("Click on Coverage tab");
	       SimpleUtils.fail("assert coverage page should load and show data",false);

	  }


	@Automated(automated = "Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate Team Search and Coverage in Team Tab")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void validateTeamTabAsStoreManager(String username, String password, String browser, String location)
			throws Exception
	{
		//To Do Should be separate Test from Schedule test
		//loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"), propertyMap.get("DEFAULT_PASSWORD"));
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		String key=searchDetails.get("jobTitle");
		List<String> list = new ArrayList<String>(Arrays.asList(key.split(",")));
		teamPage.performSearchRoster(list);
		teamPage.coverage();
		teamPage.coverageViewToPastOrFuture(TeamTest.weekViewType.Next.getValue(), TeamTest.weekCount.Six.getValue());
	}
	
	
	@Automated(automated = "Automated")
	@Owner(owner = "Naval")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "TP-157: Team Tab :- Verify whether Manager is able to approve Time Off request")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void VerifyWhetherManagerCanApproveTimeOffRequestAsTeamMember(String browser, String username, String password, String location)
			throws Exception
	{
		// Login with Team Member Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        profileNewUIPage.clickOnProfileConsoleMenu();
        SimpleUtils.assertOnFail("Profile Page not loaded.", profileNewUIPage.isProfilePageLoaded(), false);
        String myTimeOffSectionLabel = "My Time Off";
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffSectionLabel);
        String expectedRequestStatus = "PENDING";
        String timeOffReasonLabel = "VACATION";
        String timeOffExplanationText = "Sample Explanation Text";
        profileNewUIPage.createNewTimeOffRequest(timeOffReasonLabel, timeOffExplanationText);
        String requestStatus = profileNewUIPage.getTimeOffRequestStatus(timeOffReasonLabel
        	, timeOffExplanationText, getTimeOffStartTime(), getTimeOffEndTime());
        if(requestStatus.toLowerCase().contains(expectedRequestStatus.toLowerCase()))
        	SimpleUtils.pass("Profile Page: New Time Off Request status is '"+requestStatus+"'.");
        else
        	SimpleUtils.fail("Profile Page: New Time Off Request status is  not '"+expectedRequestStatus
        			+"', status found as '"+requestStatus+"'.", false);
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();
		
        // Login as Store Manager
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
        		, String.valueOf(storeManagerCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.openToDoPopupWindow();
		teamPage.approveOrRejectTimeOffRequestFromToDoList(username, getTimeOffStartTime(), getTimeOffEndTime(), timeOffRequestAction.Approve.getValue());
		teamPage.closeToDoPopupWindow();
		teamPage.searchAndSelectTeamMemberByName(username);
		String TeamMemberProfileSubSectionLabel = "Time Off";
        profileNewUIPage.selectProfilePageSubSectionByLabel(TeamMemberProfileSubSectionLabel);
        requestStatus = profileNewUIPage.getTimeOffRequestStatus(timeOffReasonLabel, 
        		timeOffExplanationText, getTimeOffStartTime(), getTimeOffEndTime());
        if(requestStatus.toLowerCase().contains(timeOffRequestStatus.Approved.getValue().toLowerCase()))
        	SimpleUtils.pass("Team Page: Time Off request Approved By Store Manager reflected on Team Page.");
        else
        	SimpleUtils.fail("Team Page: Time Off request Approved By Store Manager not reflected on Team Page.", false);
        
        loginPage.logOut();
        
        // Login as Team Member Again
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        profileNewUIPage.clickOnProfileConsoleMenu();
        SimpleUtils.assertOnFail("Profile Page not loaded.", profileNewUIPage.isProfilePageLoaded(), false);
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffSectionLabel);
        requestStatus = profileNewUIPage.getTimeOffRequestStatus(timeOffReasonLabel
            	, timeOffExplanationText, getTimeOffStartTime(), getTimeOffEndTime()

		);
        if(requestStatus.toLowerCase().contains(timeOffRequestStatus.Approved.getValue().toLowerCase()))
         	SimpleUtils.pass("Profile Page: New Time Off Request status is '"+requestStatus
         			+"' after Store Manager Approved the request.");
        else
          	SimpleUtils.fail("Profile Page: New Time Off Request status is '"+requestStatus
          			+"' after Store Manager Approved the request.", false);
	}
	
	@Automated(automated = "Automated")
	@Owner(owner = "Naval")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "TP-157: Team Tab :- Verify whether Manager is able to approve Time Off request")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void VerifyWhetherManagerCanRejectTimeOffRequestAsTeamMember(String browser, String username, String password, String location)
			throws Exception
	{
		// Login with Team Member Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
        profileNewUIPage.clickOnProfileConsoleMenu();
        SimpleUtils.assertOnFail("Profile Page not loaded.", profileNewUIPage.isProfilePageLoaded(), false);
        String myTimeOffSectionLabel = "My Time Off";
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffSectionLabel);
        String expectedRequestStatus = "PENDING";
        String timeOffReasonLabel = "VACATION";
        String timeOffExplanationText = "Sample Explanation Text";
        profileNewUIPage.createNewTimeOffRequest(timeOffReasonLabel, timeOffExplanationText);
        String requestStatus = profileNewUIPage.getTimeOffRequestStatus(timeOffReasonLabel
        	, timeOffExplanationText, getTimeOffStartTime(), getTimeOffEndTime());
        if(requestStatus.toLowerCase().contains(expectedRequestStatus.toLowerCase()))
        	SimpleUtils.pass("Profile Page: New Time Off Request status is '"+requestStatus+"'.");
        else
        	SimpleUtils.fail("Profile Page: New Time Off Request status is  not '"+expectedRequestStatus
        			+"', status found as '"+requestStatus+"'.", false);
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();
		
        // Login as Store Manager
        String fileName = "UsersCredentials.json";
        fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] storeManagerCredentials = userCredentials.get("StoreManager");
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeManagerCredentials[0][0]), String.valueOf(storeManagerCredentials[0][1])
        		, String.valueOf(storeManagerCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.openToDoPopupWindow();
		teamPage.approveOrRejectTimeOffRequestFromToDoList(username, getTimeOffStartTime(), getTimeOffEndTime(), timeOffRequestAction.Reject.getValue());
		teamPage.closeToDoPopupWindow();
		teamPage.searchAndSelectTeamMemberByName(username);
		String TeamMemberProfileSubSectionLabel = "Time Off";
        profileNewUIPage.selectProfilePageSubSectionByLabel(TeamMemberProfileSubSectionLabel);
        requestStatus = profileNewUIPage.getTimeOffRequestStatus(timeOffReasonLabel, 
        		timeOffExplanationText, getTimeOffStartTime(), getTimeOffEndTime());
        if(requestStatus.toLowerCase().contains(timeOffRequestStatus.Rejected.getValue().toLowerCase()))
        	SimpleUtils.pass("Team Page: Time Off request Rejected By Store Manager reflected on Team Page.");
        else
        	SimpleUtils.fail("Team Page: Time Off request Rejected By Store Manager not reflected on Team Page.", false);
        
        loginPage.logOut();
        
        // Login as Team Member Again
        loginToLegionAndVerifyIsLoginDone(username, password, location);
        profileNewUIPage.clickOnProfileConsoleMenu();
        SimpleUtils.assertOnFail("Profile Page not loaded.", profileNewUIPage.isProfilePageLoaded(), false);
        profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffSectionLabel);
        requestStatus = profileNewUIPage.getTimeOffRequestStatus(timeOffReasonLabel
            	, timeOffExplanationText, getTimeOffStartTime(), getTimeOffEndTime());
        if(requestStatus.toLowerCase().contains(timeOffRequestStatus.Rejected.getValue().toLowerCase()))
         	SimpleUtils.pass("Profile Page: New Time Off Request status is '"+requestStatus
         			+"' after Store Manager Rejected the request.");
        else
          	SimpleUtils.fail("Profile Page: New Time Off Request status is '"+requestStatus
          			+"' after Store Manager Rejected the request.", false);
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828041: Search Team Members is working correctly")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfSearchTMBar(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		List<String> testStrings = new ArrayList<>(Arrays.asList("jam", "boris", "h"));
		teamPage.verifyTheFunctionOfSearchTMBar(testStrings);
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828042: ’+’ button to add ™ is working")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfPlusIcon(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828047: add TM(+) > Date Hired calendar is open for current month only and current date" +
			" should be in Red color.")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheCalendarLoadForCurrentDayAndColor(String browser, String username, String password, String location) throws Exception {
		String timeZone = null;
		String currentDate = null;
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		if (controlsNewUIPage.isControlsPageLoaded()){
			controlsNewUIPage.clickOnControlsLocationProfileSection();
			if (controlsNewUIPage.isControlsLocationProfileLoaded()){
				timeZone = controlsNewUIPage.getTimeZoneFromLocationDetailsPage();
				if (timeZone != null && !timeZone.isEmpty()){
					currentDate = SimpleUtils.getCurrentDateMonthYearWithTimeZone(timeZone);
				}
			}
		}
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.verifyTheMonthAndCurrentDayOnCalendar(currentDate);
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828049: any new home location can be selected")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyNewHomeLocationCanBeSelectedOnTransfer(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToTransfer();
		teamPage.verifyHomeLocationCanBeSelected();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828050: Temp transfer button is working")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfTemporaryTransferButton(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToTransfer();
		teamPage.verifyClickOnTemporaryTransferButton();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828051: when click on temp Transfer button,Start date and End date calendar is opening")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTwoCalendarsAreShownAfterClickTemp(String browser, String username, String password, String location) throws Exception {
		String timeZone = null;
		String currentDate = null;
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		if (controlsNewUIPage.isControlsPageLoaded()){
			controlsNewUIPage.clickOnControlsLocationProfileSection();
			if (controlsNewUIPage.isControlsLocationProfileLoaded()){
				timeZone = controlsNewUIPage.getTimeZoneFromLocationDetailsPage();
				if (timeZone != null && !timeZone.isEmpty()){
					currentDate = SimpleUtils.getCurrentDateMonthYearWithTimeZone(timeZone);
				}
			}
		}
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToTransfer();
		teamPage.verifyTwoCalendarsForCurrentMonthAreShown(currentDate);
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828048: add TM(+) > Calendar can be Navigate to Previous/future")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfAddTMCalendar(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.verifyTheCalendarCanNavToPreviousAndFuture();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828052: current date is by default selected, Other Dates can be selected")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheCurrentDateAndSelectOtherDateOnTransfer(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToTransfer();
		teamPage.verifyTheCurrentDateAndSelectOtherDateOnTransfer();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828053: while transferring to a new location, old location shift is converting into open shift")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTransferToANewLocation(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		String teamMember = teamPage.selectATeamMemberToTransfer();
		String selectedLocation = teamPage.verifyHomeLocationCanBeSelected();
		teamPage.verifyDateCanBeSelectedOnTransfer();
		teamPage.isApplyButtonEnabled();
		teamPage.verifyClickOnApplyButtonOnTransfer();
		teamPage.verifyTheMessageOnPopupWindow(location, selectedLocation, teamMember);
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828054: Confirm/ cancel button is working")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyConfirmButtonOnPopupWindowWorking(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToTransfer();
		teamPage.verifyHomeLocationCanBeSelected();
		teamPage.verifyDateCanBeSelectedOnTransfer();
		teamPage.isApplyButtonEnabled();
		teamPage.verifyClickOnApplyButtonOnTransfer();
		teamPage.verifyTheFunctionOfConfirmTransferButton();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828054: Confirm/ cancel button is working")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyCancelButtonOnPopupWindowWorking(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToTransfer();
		teamPage.verifyHomeLocationCanBeSelected();
		teamPage.verifyDateCanBeSelectedOnTransfer();
		teamPage.isApplyButtonEnabled();
		teamPage.verifyClickOnApplyButtonOnTransfer();
		teamPage.verifyTheFunctionOfCancelTransferButton();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828055: home location is not updating to new location but It is giving information like New Location-07/04/2019 and Back to Home store-07/05/2019")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyHomeLocationNotUpdateAndGivenInformation(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToTransfer();
		String selectedLocation = teamPage.verifyHomeLocationCanBeSelected();
		teamPage.verifyDateCanBeSelectedOnTransfer();
		teamPage.isApplyButtonEnabled();
		teamPage.verifyClickOnApplyButtonOnTransfer();
		teamPage.verifyTheFunctionOfConfirmTransferButton();
		teamPage.verifyTheHomeStoreLocationOnProfilePage(location, selectedLocation);
	}
}
