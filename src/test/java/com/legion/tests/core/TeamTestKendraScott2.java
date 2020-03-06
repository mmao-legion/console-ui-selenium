package com.legion.tests.core;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.legion.pages.*;

import java.util.Map;

import com.legion.pages.core.ConsoleGmailPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
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
	private static Map<String, String> newTMDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/AddANewTeamMember.json");
	private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
	private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");

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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828041 Search Team Members is working correctly")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828042 plus button to add TM is working")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfPlusIcon(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828047 add TM Date Hired calendar is open for current month only and current date" +
			" should be in Red color")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828049 any new home location can be selected")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828050 Temp transfer button is working")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828051 when click on temp Transfer button,Start date and End date calendar is opening")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828048 add TM Calendar can be Navigate to Previous future")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828052 current date is by default selected Other Dates can be selected")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828053 while transferring to a new location old location shift is converting into open shift")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828054 Confirm cancel button is working")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828054 Confirm cancel button is working")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828055 home location is not updating to new location but It is giving information like New Location 07042019 and Back to Home store 07052019")
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

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828057 cancel Transfer button is working After click on Cancel Transfer-one pop-up is coming")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyPopupIsShownAfterClickingCancelTransfer(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToCancelTransfer();
		teamPage.verifyCancelTransferWindowPopup();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828056 After click on Cancel Transfer  button,Transfer button is enabled again")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTransferButtonEnabledAfterCancelingTransfer(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToCancelTransfer();
		teamPage.verifyCancelTransferWindowPopup();
		teamPage.verifyTransferButtonEnabledAfterCancelingTransfer();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828070 After cancelling the transfer Home location should get updated as the previous one")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyHomeLocationAfterCancelingTransfer(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToCancelTransfer();
		teamPage.verifyCancelTransferWindowPopup();
		teamPage.verifyHomeLocationAfterCancelingTransfer(location);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828061 Badges is working and is able to edit into any new one")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfBadges(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToViewProfile();
		teamPage.isProfilePageLoaded();
		teamPage.verifyTheFunctionOfEditBadges();
	}

	@Automated(automated ="Manual")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828062 Badges is visible on Team roster.")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheVisibleOfBadgesOnTeamRoster(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheVisibleOfBadgesOnTeamRoster();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828067 in Email only correct domain is accepted")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheEmailDomainOnInvite(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.isControlsPageLoaded();
		controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
		controlsNewUIPage.isControlsScheduleCollaborationLoaded();
		controlsNewUIPage.setOnBoardOptionAsEmailWhileInviting();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToInvite();
		teamPage.isInviteTeamMemberWindowLoaded();
		List<String> testEmails = new ArrayList<>(Arrays.asList("123456", "nora@legion.co", "@#$%%"));
		teamPage.verifyTheEmailFormatOnInviteWindow(testEmails);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828068 To use the legion when click on invite button Invite Team Member page should be opened")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyInviteTeamMemberPageLoaded(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToInvite();
		teamPage.isInviteTeamMemberWindowLoaded();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828075 Verify Contact Information Enter Committed Availability and Personalize Welcome Message Field should be available")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyThreeTitlesDisplayedOnInvite(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
		teamPage.inviteTheNewCreatedTeamMember(firstName);
		teamPage.isInviteTeamMemberWindowLoaded();
		teamPage.verifyThereSectionsAreLoadedOnInviteWindow();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828076 Send Cancel button should be available on Invite  Team Member")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifySendAndCancelEnabledOnInvite(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectATeamMemberToInvite();
		teamPage.isInviteTeamMemberWindowLoaded();
		teamPage.isSendAndCancelLoadedAndEnabledOnInvite();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828043 add TM Save button is enabled only when all the mandatory details are filled")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifySaveButtonEnableOnNewTMPage(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.isControlsPageLoaded();
		controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
		controlsNewUIPage.isControlsScheduleCollaborationLoaded();
		String mandatoryField = controlsNewUIPage.getOnBoardOptionFromScheduleCollaboration();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.checkAddATMMandatoryFieldsAreLoaded(mandatoryField);
		teamPage.fillInMandatoryFieldsOnNewTMPage(newTMDetails, mandatoryField);
		teamPage.isSaveButtonOnNewTMPageEnabled();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828044 add TM Contact number accept in numeric digits only no special or alphabetical character allowed")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTMContactNumberInNumericOnly(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		List<String> contactNumbers = new ArrayList<>(Arrays.asList("123456", "abc123cfg566", "@#$%%", "1234567890"));
		teamPage.verifyContactNumberFormatOnNewTMPage(contactNumbers);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828040 TM count is correct from roster")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTMCountIsCorrectFromRoster(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTMCountIsCorrectOnRoster();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828046 add TM Cancel button is enabled By-default")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyCancelButtonEnabledOnAddTM(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.verifyCancelButtonOnAddTMAndClick();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828045 add TM Mandatory field can not leave empty")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyMandatoryFieldsOnAddTM(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.isControlsPageLoaded();
		controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
		controlsNewUIPage.isControlsScheduleCollaborationLoaded();
		String mandatoryField = controlsNewUIPage.getOnBoardOptionFromScheduleCollaboration();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.checkAddATMMandatoryFieldsAreLoaded(mandatoryField);
		teamPage.verifyTheMandatoryFieldsCannotEmpty();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828069 Team member is invited and visible on todos")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTMIsInvitedAndVisibleOnTODO(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
		teamPage.openToDoPopupWindow();
		teamPage.verifyTMIsVisibleAndInvitedOnTODO(firstName);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828072 To Invite Team Member for Onboarding Invite Re-Invite button should be available")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyInviteAndReInviteAreAvailable(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.selectAInvitedOrNotInvitedTeamMemberToView();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828058 While activating team Member On boarded date is updating to new one and Deactivate terminate button is enabled")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyOnBoardedDateAndButtonsWhileActivating(String browser, String username, String password, String location) throws Exception {
		String onBoardedDate = null;
		String active = "Active";
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		if (teamPage.selectATeamMemberToActivate() != 0) {
			if (!teamPage.isProfilePageSelected()) {
				teamPage.navigateToProfileTab();
			}
		}else {
			teamPage.searchForTeamMemberByStatus(active);
			teamPage.selectATeamMemberToViewProfile();
			teamPage.isProfilePageLoaded();
			if (!teamPage.isActivateButtonLoaded()) {
				teamPage.cancelActivateOrDeactivateTeamMember();
			}
		}
		onBoardedDate = teamPage.getOnBoardedDate();
		teamPage.clickOnActivateButton();
		teamPage.isActivateWindowLoaded();
		teamPage.selectADateOnCalendarAndActivate();
		teamPage.verifyDeactivateAndTerminateEnabled();
		teamPage.isOnBoardedDateUpdated(onBoardedDate);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828060 Status will change into Activate status according to date")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTeamMemberStatusAfterActivating(String browser, String username, String password, String location) throws Exception {
		String active = "Active";
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchForTeamMemberByStatus(active);
		teamPage.selectATeamMemberToViewProfile();
		teamPage.isProfilePageLoaded();
		if (!teamPage.isActivateButtonLoaded()) {
			teamPage.cancelActivateOrDeactivateTeamMember();
		}
		teamPage.clickOnActivateButton();
		teamPage.isActivateWindowLoaded();
		teamPage.selectADateOnCalendarAndActivate();
		teamPage.verifyTheStatusOfTeamMember();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828073 After Click on Invite Re-invite button invitation code should be sent to Email id")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyInvitationCodeIsSent(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.isControlsPageLoaded();
		controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
		controlsNewUIPage.isControlsScheduleCollaborationLoaded();
		controlsNewUIPage.setOnBoardOptionAsEmailWhileInviting();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
		teamPage.inviteTheNewCreatedTeamMember(firstName);
		teamPage.isInviteTeamMemberWindowLoaded();
		teamPage.sendTheInviteViaEmail();
		GmailPage gmailPage = pageFactory.createConsoleGmailPage();
		gmailPage.loginToGmailWithCredential();
		gmailPage.waitUntilInvitationEmailLoaded();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828074 Invitation code should be available on Email id")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyInvitationCodeIsAvailableOnEmailID(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.isControlsPageLoaded();
		controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
		controlsNewUIPage.isControlsScheduleCollaborationLoaded();
		controlsNewUIPage.setOnBoardOptionAsEmailWhileInviting();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
		teamPage.inviteTheNewCreatedTeamMember(firstName);
		teamPage.isInviteTeamMemberWindowLoaded();
		teamPage.sendTheInviteViaEmail();
		GmailPage gmailPage = pageFactory.createConsoleGmailPage();
		gmailPage.loginToGmailWithCredential();
		gmailPage.waitUntilInvitationEmailLoaded();
		gmailPage.verifyInvitationCodeIsAvailableOnEmailID();
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828066 It is present when any information is pending for TM Contact number email id")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyUpdateInfoIsPresentWhenPendingForContactOrEmail(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.isControlsPageLoaded();
		controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
		controlsNewUIPage.isControlsScheduleCollaborationLoaded();
		String mandatoryField = controlsNewUIPage.getOnBoardOptionFromScheduleCollaboration();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.checkAddATMMandatoryFieldsAreLoaded(mandatoryField);
		String firstName = teamPage.fillInMandatoryFieldsOnNewTMPage(newTMDetails, mandatoryField);
		teamPage.isSaveButtonOnNewTMPageEnabled();
		teamPage.saveTheNewTeamMember();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchTheNewTMAndUpdateInfo(firstName);
		teamPage.isProfilePageLoaded();
		teamPage.isEmailOrPhoneNumberEmptyAndUpdate(newTMDetails, mandatoryField);
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchTheTMAndCheckUpdateInfoNotShow(firstName);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828063 While Clicking on Terminate button particular TM is able to removed from Team roster on the set date")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfTerminateButton(String browser, String username, String password, String location) throws Exception {
		String status = "New";
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchForTeamMemberByStatus(status);
		teamPage.selectATeamMemberToViewProfile();
		teamPage.isProfilePageLoaded();
		teamPage.isTerminateButtonLoaded();
		teamPage.terminateTheTeamMember(true);
		/*
		 * Since employeeID is distinct, so if searching from roster, it shouldn't find the team member if it has been terminated.
		 */
		String employeeID = teamPage.getEmployeeIDFromProfilePage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchTheTeamMemberByEmployeeIDFromRoster(employeeID, true);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828065 Cancel Termination button is working TM will not removed from Roster after click on this button")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionOfCancelTerminateButton(String browser, String username, String password, String location) throws Exception {
		String status = "New";
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchForTeamMemberByStatus(status);
		teamPage.selectATeamMemberToViewProfile();
		teamPage.isProfilePageLoaded();
		boolean isCancel = teamPage.isCancelTerminateButtonLoaded();
		if (!isCancel) {
			teamPage.terminateTheTeamMember(false);
		}
		teamPage.verifyTheFunctionOfCancelTerminate();
		String employeeID = teamPage.getEmployeeIDFromProfilePage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchTheTeamMemberByEmployeeIDFromRoster(employeeID, false);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828059 Shifts will go to Auto Scheduling  after Activating any TM TM will start being auto scheduled the Week of x date")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyShiftCanAssignToTMAfterActivating(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		String currentDay = dashboardPage.getCurrentDateFromDashboard();
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.isProfilePageLoaded();
		String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(firstName);
		teamPage.isProfilePageLoaded();
		teamPage.isManualOnBoardButtonLoaded();
        teamPage.manualOnBoardTeamMember();
        teamPage.isActivateButtonLoaded();
        teamPage.clickOnActivateButton();
        teamPage.isActivateWindowLoaded();
        teamPage.selectADateOnCalendarAndActivate();
        teamPage.verifyTheStatusOfTeamMember();
        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.goToSchedulePage();
        schedulePage.isSchedulePage();
        schedulePage.goToSchedule();
        schedulePage.isSchedule();
        if (!schedulePage.isWeekGenerated()) {
        	// TODO: wait for Nishant generating the code for "Generate Schedule"
		}
        schedulePage.clickOnEditButton();
        schedulePage.clickOnCreateNewShiftWeekView();
        schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		schedulePage.selectDaysFromCurrentDay(currentDay);
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.searchTeamMemberByName(firstName);
		schedulePage.clickOnOfferOrAssignBtn();
		schedulePage.verifyNewShiftsAreShownOnSchedule(firstName);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "T1828064 TMs assigned shift is converting to open shift")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyShiftIsOpenAfterTerminating(String browser, String username, String password, String location) throws Exception {
		String timeZone = null;
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		String currentDay = dashboardPage.getCurrentDateFromDashboard();
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		if (controlsNewUIPage.isControlsPageLoaded()) {
			controlsNewUIPage.clickOnControlsLocationProfileSection();
			if (controlsNewUIPage.isControlsLocationProfileLoaded()) {
				timeZone = controlsNewUIPage.getTimeZoneFromLocationDetailsPage();
			}
		}
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.isProfilePageLoaded();
		String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(firstName);
		teamPage.isProfilePageLoaded();
		teamPage.isManualOnBoardButtonLoaded();
		teamPage.manualOnBoardTeamMember();
		teamPage.isActivateButtonLoaded();
		teamPage.clickOnActivateButton();
		teamPage.isActivateWindowLoaded();
		teamPage.selectADateOnCalendarAndActivate();
		teamPage.verifyTheStatusOfTeamMember();
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.goToSchedulePage();
		schedulePage.isSchedulePage();
		schedulePage.goToSchedule();
		schedulePage.isSchedule();
		if (!schedulePage.isWeekGenerated()) {
			// TODO: wait for Nishant generating the code for "Generate Schedule"
		}
		schedulePage.clickOnEditButton();
		schedulePage.clickOnCreateNewShiftWeekView();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		schedulePage.selectDaysFromCurrentDay(currentDay);
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.searchTeamMemberByName(firstName);
		schedulePage.clickOnOfferOrAssignBtn();
		schedulePage.verifyNewShiftsAreShownOnSchedule(firstName);
		schedulePage.clickSaveBtn();
		List<Integer> indexes = schedulePage.getAddedShiftIndexes(firstName);
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(firstName);
		teamPage.isProfilePageLoaded();
		teamPage.isTerminateButtonLoaded();
		teamPage.terminateTheTeamMember(true);
		String currentTime = SimpleUtils.getCurrentTimeWithTimeZone(timeZone);
		schedulePage.goToSchedulePage();
		schedulePage.isSchedulePage();
		schedulePage.goToSchedule();
		schedulePage.isSchedule();
		schedulePage.verifyShiftsChangeToOpenAfterTerminating(indexes, firstName, currentTime);
	}
}
