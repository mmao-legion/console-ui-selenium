package com.legion.tests.core;

import java.lang.reflect.Method;
import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.legion.pages.*;

import java.util.Map;

import com.legion.pages.core.ConsoleGmailPage;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.OpsPortalLocationsPage;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.formula.ptg.ControlPtg;
import org.apache.xpath.operations.Bool;
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
    private static HashMap<String, String> imageFilePath = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ProfileImageFilePath.json");
	//	private static HashMap<String, Object[][]> kendraScott2TeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("KendraScott2TeamMembers.json");
	private static HashMap<String, Object[][]> controlTeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("VailqacnTeamMembers.json");
	private static HashMap<String, Object[][]> opTeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("CinemarkWkdyTeamMembers.json");
	private static String controlEnterprice = "Vailqacn_Enterprise";
	private static String opEnterprice = "CinemarkWkdy_Enterprise";


	@Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
		try {
			this.createDriver((String) params[0], "69", "Window");
			visitPage(testMethod);
			loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
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
        fileName = SimpleUtils.getEnterprise(controlEnterprice)+fileName;
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
        fileName = SimpleUtils.getEnterprise(controlEnterprice)+fileName;
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
	@TestName(description = "Verify the Team Functionality > In Update Info")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInUpdateInfoAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
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
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.searchTheNewTMAndUpdateInfo(firstName);
			teamPage.isProfilePageLoaded();
			teamPage.isEmailOrPhoneNumberEmptyAndUpdate(newTMDetails, mandatoryField);
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.searchTheTMAndCheckUpdateInfoNotShow(firstName);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team functionality>In Roster")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInRosterAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.isControlsPageLoaded();
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			controlsNewUIPage.isControlsScheduleCollaborationLoaded();
			String mandatoryField = controlsNewUIPage.getOnBoardOptionFromScheduleCollaboration();
			String currentDate = getTimeZoneFromControlsAndGetDate();
			// Verify TM Count is correct from roster
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.verifyTMCountIsCorrectOnRoster();
			// Verify Search Team Members is working correctly
			List<String> testStrings = new ArrayList<>(Arrays.asList("jam", "boris", "Retail"));
			teamPage.verifyTheFunctionOfSearchTMBar(testStrings);
			// Verify + button to add TM is working
			teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
			// Verify Cancel button is enabled by default
			teamPage.verifyCancelButtonOnAddTMIsEnabled();
			// Verify Contact number accept in numeric digits only
			List<String> contactNumbers = new ArrayList<>(Arrays.asList("123456", "abc123cfg566", "@#$%%", "1234567890"));
			teamPage.verifyContactNumberFormatOnNewTMPage(contactNumbers);
			// Verify Mandatory field can not leave empty
			teamPage.checkAddATMMandatoryFieldsAreLoaded(mandatoryField);
			teamPage.verifyTheMandatoryFieldsCannotEmpty();
			// Verify Date Hired calendar is open for current month only and current date should be in Red color
			teamPage.verifyTheMonthAndCurrentDayOnCalendar(currentDate);
			// Verify Calendar can be Navigate to Previous/future
			teamPage.verifyTheCalendarCanNavToPreviousAndFuture();
			// Verify Save button is enabled only when all the mandatory details are filled
			teamPage.fillInMandatoryFieldsOnNewTMPage(newTMDetails, mandatoryField);
			teamPage.isSaveButtonOnNewTMPageEnabled();
			// Verify click on Cancel button, it will return to the Roster page
			teamPage.clickCancelButton();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora&Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the team functionality in Roster - Sort")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInRosterForSortAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Check whether the location is location group or not
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
			if(isActiveWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			boolean isLocationGroup = toggleSummaryPage.isLocationGroup();

			// Verify TM Count is correct from roster
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.verifyTMCountIsCorrectOnRoster();
			// Verify Search Team Members is working correctly
			List<String> testStrings = new ArrayList<>(Arrays.asList("jam", "boris", "Retail Manager", "abc"));
			teamPage.verifyTheFunctionOfSearchTMBar(testStrings);
			// Verify the column in roster page
			teamPage.verifyTheColumnInRosterPage(isLocationGroup);
			// Verify NAME column can be sorted in ascending or descending order
			teamPage.verifyTheSortFunctionInRosterByColumnName("NAME");
			// Verify EMPLOYEE ID column can be sorted in ascending or descending order
			teamPage.verifyTheSortFunctionInRosterByColumnName("EMPLOYEE ID");
			// Verify JOB TITLE column can be sorted in ascending or descending order
			teamPage.verifyTheSortFunctionInRosterByColumnName("JOB TITLE");
			// Verify EMPLOYMENT column can be sorted in ascending or descending order
			teamPage.verifyTheSortFunctionInRosterByColumnName("EMPLOYMENT");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team functionality>In Transfer")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInTransferAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			String currentDate = getTimeZoneFromControlsAndGetDate();
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			// Verify any new home location can be selected
			String teamMember = teamPage.selectATeamMemberToTransfer();
			String selectedLocation = teamPage.verifyHomeLocationCanBeSelected();
			// Verify Temp transfer button is working
			teamPage.verifyClickOnTemporaryTransferButton();
			// Verify when click on temp Transfer button,Start date and End date calendar is opening
			teamPage.verifyTwoCalendarsForCurrentMonthAreShown(currentDate);
			// Verify current date is by default selected, Other Dates can be selected
			teamPage.verifyTheCurrentDateAndSelectOtherDateOnTransfer();
			// Verify while transferring to a new location, old location shift is converting into open shift
			teamPage.isApplyButtonEnabled();
			teamPage.verifyClickOnApplyButtonOnTransfer();
			teamPage.verifyTheMessageOnPopupWindow(location, selectedLocation, teamMember);
			// Verify Confirm button is working
			teamPage.verifyTheFunctionOfConfirmTransferButton();
			// Verify home location is not updating to new location
			teamPage.verifyTheHomeStoreLocationOnProfilePage(location, selectedLocation);
			// Verify cancel Transfer button is working,After click on Cancel Transfer-one pop-up is coming
			teamPage.isCancelTransferButtonLoadedAndClick();
			teamPage.verifyCancelTransferWindowPopup();
			// Verify After click on Cancel Transfer button, Transfer button is enabled again
			teamPage.verifyTransferButtonEnabledAfterCancelingTransfer();
			// Verify After cancelling the transfer, Home location should get updated as the previous one
			teamPage.verifyHomeLocationAfterCancelingTransfer(location);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team functionality > In Badges")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInBadgesAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			dashboardPage.isDashboardPageLoaded();
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
			teamPage.isProfilePageLoaded();
			String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.searchAndSelectTeamMemberByName(firstName);
			teamPage.isProfilePageLoaded();
			// Verify Badges is working and is able to edit into any new one
			String badgeID = teamPage.verifyTheFunctionOfEditBadges();
			teamPage.goToTeam();
			// Verify Badges is visible on Team roster.
			teamPage.verifyTheVisibleOfBadgesOnTeamRoster(firstName, badgeID);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team Functionality > Invite Team Member")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInInviteTeamMemberAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			dashboardPage.isDashboardPageLoaded();
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.isControlsPageLoaded();
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			controlsNewUIPage.isControlsScheduleCollaborationLoaded();
			// Set the on boarded option as Email while inviting
			controlsNewUIPage.setOnBoardOptionAsEmailWhileInviting();
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
			String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
			// Verify Team member is invited and visible on todos
			teamPage.openToDoPopupWindow();
			teamPage.verifyTMIsVisibleAndInvitedOnTODO(firstName);
			teamPage.goToTeam();
			teamPage.searchAndSelectTeamMemberByName(firstName);
			teamPage.isProfilePageLoaded();
			// Verify To Invite Team Member for Onboarding, Invite/Re-Invite button should be available
			teamPage.verifyInviteAndReInviteButtonThenInvite();
			// Verify To use the legion, when click on invite button, Invite Team Member page should be opened.
			teamPage.isInviteTeamMemberWindowLoaded();
			// Verify Contact Information, Enter Committed Availability and Personalize Welcome Message Field should be available
			teamPage.verifyThereSectionsAreLoadedOnInviteWindow();
			// Verify in Email, only correct domain is accepted.
			List<String> testEmails = new ArrayList<>(Arrays.asList("123456", "@#$%%", "nora@legion.co"));
			teamPage.verifyTheEmailFormatOnInviteWindow(testEmails);
			// Verify Send/Cancel button should be available on Invite Team Member
			teamPage.isSendAndCancelLoadedAndEnabledOnInvite();
			teamPage.sendTheInviteViaEmail(newTMDetails.get("EMAIL"));
			// Verify Invitation code should be available on Email id
			GmailPage gmailPage = pageFactory.createConsoleGmailPage();
			gmailPage.loginToGmailWithCredential();
			gmailPage.waitUntilInvitationEmailLoaded();
			gmailPage.verifyInvitationCodeIsAvailableOnEmailID();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team functionality > Work Preferences")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInWorkPreferencesAsStoreManager(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			dashboardPage.isDashboardPageLoaded();
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.selectATeamMemberToViewProfile();
			teamPage.isProfilePageLoaded();
			teamPage.navigateToWorkPreferencesTab();
			// Verify shift preferences- can be Edit by clicking on pencil icon Changes are being Cancel by clicking on cancel button
			List<String> previousPreferences = teamPage.getShiftPreferences();
			teamPage.clickOnEditShiftPreference();
			SimpleUtils.assertOnFail("Edit Shift Preferences layout failed to load!", teamPage.isEditShiftPreferLayoutLoaded(), true);
			teamPage.setSliderForShiftPreferences();
			teamPage.changeShiftPreferencesStatus();
			teamPage.clickCancelEditShiftPrefBtn();
			List<String> currentPreferences = teamPage.getShiftPreferences();
			if (previousPreferences.containsAll(currentPreferences) && currentPreferences.containsAll(previousPreferences)) {
				SimpleUtils.pass("Shift preferences don't change after cancelling!");
			} else {
				SimpleUtils.fail("Shift preferences are changed after cancelling!", true);
			}
			// Verify shift preferences- can be Edit by clicking on pencil icon Changes are being Saved by clicking on Save button
			teamPage.clickOnEditShiftPreference();
			SimpleUtils.assertOnFail("Edit Shift Preferences layout failed to load!", teamPage.isEditShiftPreferLayoutLoaded(), true);
			List<String> changedShiftPrefs = teamPage.setSliderForShiftPreferences();
			List<String> status = teamPage.changeShiftPreferencesStatus();
			teamPage.clickSaveShiftPrefBtn();
			currentPreferences = teamPage.getShiftPreferences();
			teamPage.verifyCurrentShiftPrefIsConsistentWithTheChanged(currentPreferences, changedShiftPrefs, status);
			// Verify Availability Graph [Edited by manager/Admin]:Weekly Availability/Unavailability is showing by green/Red color
			teamPage.editOrUnLockAvailability();
			SimpleUtils.assertOnFail("Edit Availability layout failed to load!", teamPage.areCancelAndSaveAvailabilityBtnLoaded(), true);
			teamPage.changePreferredHours();
			teamPage.changeBusyHours();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team Functionality > Profile section")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInProfileSectionAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			// Login with Internal Admin Credentials
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.selectATeamMemberToViewProfile();
			teamPage.isProfilePageLoaded();
			// Verify Profile picture is updating
			String filePath = imageFilePath.get("FilePath");
			teamPage.updateProfilePicture(filePath);
			// Verify Phone number and Email id are updating
			String phoneNumber = "1234455678";
			String emailID = "nora@legion.co";
			teamPage.updatePhoneNumberAndEmailID(phoneNumber, emailID);
			// Verify Engagement details are updating
			teamPage.updateEngagementDetails(newTMDetails);
			// Verify Badges is updating
			teamPage.clickOnEditBadgeButton();
			List<String> selectedBadgeIDs = teamPage.updateTheSelectedBadges();
			List<String> badgeIDs = teamPage.getCurrentBadgesOnEngagement();
			if (badgeIDs.containsAll(selectedBadgeIDs) && selectedBadgeIDs.containsAll(badgeIDs)) {
				SimpleUtils.pass("Badges updated Successfully!");
			} else {
				SimpleUtils.fail("Badges not updated successfully!", true);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	public String getTimeZoneFromControlsAndGetDate() throws Exception {
		String timeZone = "";
		String currentDate = "";
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		if (controlsNewUIPage.isControlsPageLoaded()){
			controlsNewUIPage.clickOnControlsLocationProfileSection();
			if (controlsNewUIPage.isControlsLocationProfileLoaded()){
				timeZone = controlsNewUIPage.getTimeZoneFromLocationDetailsPage();
				if (timeZone != null && !timeZone.isEmpty()){
					SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy dd");
					currentDate = SimpleUtils.getCurrentDateMonthYearWithTimeZone(timeZone, format);
				}
			}
		}
		return currentDate;
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the profile invitation code in Profile UI")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheProfileInvitationCodeInProfileUIAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			// Login with Internal Admin Credentials
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			// Check invite to legion button and invitation code are not exists on the onboarded user profile page
			teamPage.selectARandomOnboardedOrNotTeamMemberToViewProfile(true);
			teamPage.isProfilePageLoaded();
			SimpleUtils.assertOnFail("Invite buttons should not loaded on the onboarded TM profile page! ",
					!profileNewUIPage.isInviteToLegionButtonLoaded()
							&& !profileNewUIPage.isInvitationCodeLoaded()
							&& !profileNewUIPage.isShowOrHideInvitationCodeButtonLoaded(), false);

			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.selectARandomOnboardedOrNotTeamMemberToViewProfile(false);
			teamPage.isProfilePageLoaded();
			// Click Invite to Legion button
			profileNewUIPage.userProfileInviteTeamMember();
			// Check the tooltip of
			String tooltipMessage = "This code has been sent to the team member";
			SimpleUtils.assertOnFail("Show Or Hide Invitation Code Button tooltip is incorrectly",
					tooltipMessage.equals(profileNewUIPage.getShowOrHideInvitationCodeButtonTooltip()), false);
			// Click Show Invitation Code button
			profileNewUIPage.clickOnShowOrHideInvitationCodeButton(true);
			//Check invitation code is loaded
			SimpleUtils.assertOnFail("Invitation code loaded fail! ", profileNewUIPage.isInvitationCodeLoaded(), false);
			// Get invitation code
			String invitationCode = profileNewUIPage.getInvitationCode();
			String fullName = profileNewUIPage.getUserProfileName().get("fullName");
			String lastName = fullName.substring(fullName.indexOf(" "));
			// Click Hide Invitation Code button
			profileNewUIPage.clickOnShowOrHideInvitationCodeButton(false);
			SimpleUtils.assertOnFail("Invitation code should not loaded! ", !profileNewUIPage.isInvitationCodeLoaded(), false);

			//Logout
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();
			loginPage.verifyLoginPageIsLoaded();

			//Check Create Account message display correctly
			loginPage.verifyCreateAccountMessageDisplayCorrectly();

			//Click Sign Up button
			loginPage.clickSignUpLink();
			SimpleUtils.assertOnFail("Verify last name and invitation code page fail to loaded! ", loginPage.isVerifyLastNameAndInvitationCodePageLoaded(), false);

			//Input the incorrect invitation code
			loginPage.verifyLastNameAndInvitationCode(lastName, "123456");
			SimpleUtils.assertOnFail("Error toast failed to loaded", loginPage.isErrorToastLoaded(), false);

			//Input the correct invitation code
			loginPage.verifyLastNameAndInvitationCode(lastName, invitationCode);
			SimpleUtils.assertOnFail("Create Account page fail to loaded! ", loginPage.isCreateAccountPageLoaded(), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Remove access to Employee Profile in Team Schedule view")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyRemoveAccessToEmployeeProfileInTeamScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		// Create schedule and publish it.
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()) , false);

		if (createSchedulePage.isWeekGenerated()){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUI();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		shiftOperatePage.deleteTMShiftInWeekView("unassigned");
		scheduleMainPage.saveSchedule();
		createSchedulePage.publishActiveSchedule();
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		// Login as Store Manager
		loginAsDifferentRole(AccessRoles.StoreManager.getValue());
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		profileNewUIPage.clickOnUserProfileImage();//.getNickNameFromProfile();
		dashboardPage.clickOnSwitchToEmployeeView();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab("Team Schedule");
		SimpleUtils.assertOnFail("SM shouldn't be able to view profile info in employee view", !scheduleShiftTablePage.isProfileIconsClickable(), false);
		loginPage.logOut();


		// Login as Team Member
		loginAsDifferentRole(AccessRoles.TeamMember.getValue());
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab("Team Schedule");
		SimpleUtils.assertOnFail("SM shouldn't be able to view profile info in employee view", !scheduleShiftTablePage.isProfileIconsClickable(), false);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate create change availability request")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateCreateChangeAvailabilityRequestAsStoreManager(String browser, String username, String password, String location) throws Exception {
		// Login with Store Manager Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		// Set availability policy
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		dashboardPage.navigateToDashboard();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		controlsPage.gotoControlsPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		String isApprovalRequired = "Required for all changes";
		controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		//Login as Team Member to change availability
		loginAsDifferentRole(AccessRoles.TeamMember.getValue());
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		profileNewUIPage.getNickNameFromProfile();
		String myWorkPreferencesLabel = "My Work Preferences";
		profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
		//cancel all availability change requests firstly.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		//Update Preferred Hours
		while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
			profileNewUIPage.clickNextWeek();
		}
		String weekInfo = profileNewUIPage.getAvailabilityWeek();
		int sliderIndex = 1;
		double hours = 0.5;//move 1 metric 0.5h right----increase
		String leftOrRightDuration = "Right";
		String hoursType = "Preferred";
		String repeatChanges = "This week only";
		profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		profileNewUIPage.verifyTheLatestAvailabilityRequestInfo(weekInfo, hours, repeatChanges);
		profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		profileNewUIPage.verifyTheLatestAvailabilityRequestInfo(weekInfo, hours*2, repeatChanges);

		//cancel all availability change requests again.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		hours = -0.5;//move 1 metric 0.5h left----decrease
		leftOrRightDuration = "Left";
		hoursType = "Preferred";
		repeatChanges = "repeat forward";
		profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		profileNewUIPage.verifyTheLatestAvailabilityRequestInfo(weekInfo, hours, repeatChanges);
		profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		profileNewUIPage.verifyTheLatestAvailabilityRequestInfo(weekInfo, hours*2, repeatChanges);
		SimpleUtils.assertOnFail("Pending count should be 1.", "1".equalsIgnoreCase(profileNewUIPage.getCountForStatus("pending").trim()), false);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate cancel Change Availability Request")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateCancelAvailabilityRequestAsStoreManager(String browser, String username, String password, String location) throws Exception {
		// Login with Store Manager Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		// Set availability policy
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		dashboardPage.navigateToDashboard();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		controlsPage.gotoControlsPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		String isApprovalRequired = "Required for all changes";
		controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		//Login as Team Member to change availability
		loginAsDifferentRole(AccessRoles.TeamMember.getValue());
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		profileNewUIPage.getNickNameFromProfile();
		String myWorkPreferencesLabel = "My Work Preferences";
		profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
		//cancel all availability change requests firstly.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		//Update Preferred Hours
		while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
			profileNewUIPage.clickNextWeek();
		}
		String weekInfo = profileNewUIPage.getAvailabilityWeek();
		int sliderIndex = 1;
		double hours = 0.5;//move 1 metric 0.5h right----increase
		String leftOrRightDuration = "Right";
		String hoursType = "Preferred";
		String repeatChanges = "This week only";
		profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		profileNewUIPage.verifyTheLatestAvailabilityRequestInfo(weekInfo, hours, repeatChanges);
		SimpleUtils.assertOnFail("Pending count should be 0.", "0".equalsIgnoreCase(profileNewUIPage.getCountForStatus("pending").trim()), false);
		//cancel availability change requests.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		SimpleUtils.assertOnFail("Pending count should be 1.", "1".equalsIgnoreCase(profileNewUIPage.getCountForStatus("pending").trim()), false);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate SM approve change availability request")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateApproveAvailabilityRequestAsStoreManager(String browser, String username, String password, String location) throws Exception {
		// Login with Store Manager Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		// Set availability policy
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		dashboardPage.navigateToDashboard();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		controlsPage.gotoControlsPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		String isApprovalRequired = "Required for all changes";
		controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		//Login as Team Member to change availability
		loginAsDifferentRole(AccessRoles.TeamMember.getValue());
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String requestUserName = profileNewUIPage.getNickNameFromProfile();
		String myWorkPreferencesLabel = "My Work Preferences";
		profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
		//cancel all availability change requests firstly.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		//Update Preferred Hours
		while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
			profileNewUIPage.clickNextWeek();
		}
		String weekInfo = profileNewUIPage.getAvailabilityWeek();
		String oldAvailableHrs = profileNewUIPage.getAvailableHoursForSpecificWeek();
		int sliderIndex = 1;
		double hours = -0.5;//move 1 metric 0.5h left----decrease
		String leftOrRightDuration = "Right";
		String hoursType = "Preferred";
		String repeatChanges = "This week only";
		String newAvailableHrs = profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		loginPage.logOut();

		//Login as store manager to approve the request.
		loginAsDifferentRole(AccessRoles.StoreManager.getValue());
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(requestUserName);
		String workPreferencesLabel = "Work Preferences";
		profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
		String approvedCount = profileNewUIPage.getCountForStatus("approved");
		profileNewUIPage.approveOrRejectSpecificPendingAvailabilityRequest(weekInfo, "Approve");
		if (SimpleUtils.isNumeric(approvedCount)){
			int approvedNum = Integer.valueOf(approvedCount);
			SimpleUtils.assertOnFail("Approved count should be 1 more than before.", String.valueOf((approvedNum+1)).equalsIgnoreCase(profileNewUIPage.getCountForStatus("approved").trim()), false);
		} else {
			SimpleUtils.fail("Count is not numeric", false);
		}
		while (!weekInfo.equalsIgnoreCase(profileNewUIPage.getAvailabilityWeek())){
			profileNewUIPage.clickNextWeek();
		}
		SimpleUtils.assertOnFail("Available hours didn't change to the new version!", newAvailableHrs.equalsIgnoreCase(profileNewUIPage.getAvailableHoursForSpecificWeek()), false);
		SimpleUtils.assertOnFail("Pending count should be 0.", "0".equalsIgnoreCase(profileNewUIPage.getCountForStatus("pending").trim()), false);

		//SCH-4997
		//Go to reject a approved request.
//		profileNewUIPage.rejectSpecificApprovedAvailabilityRequest(weekInfo);
//		SimpleUtils.assertOnFail("Available hours should change back to the old version!", oldAvailableHrs.equalsIgnoreCase(profileNewUIPage.getAvailableHoursForSpecificWeek()), false);
//		SimpleUtils.assertOnFail("Approved count should be the same as before.", approvedCount.equalsIgnoreCase(profileNewUIPage.getCountForStatus("approved").trim()), false);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate cancelled/approved/rejected and dated request has no option when clicking the request")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateCancelledAvailabilityHasNoOptionRequestAsStoreManager(String browser, String username, String password, String location) throws Exception {
		// Login with Store Manager Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		// Set availability policy
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		dashboardPage.navigateToDashboard();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		controlsPage.gotoControlsPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		String isApprovalRequired = "Required for all changes";
		controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		//Login as Team Member to change availability
		loginAsDifferentRole(AccessRoles.TeamMember.getValue());
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String requestUserName = profileNewUIPage.getNickNameFromProfile();
		String myWorkPreferencesLabel = "My Work Preferences";
		profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
		//cancel all availability change requests firstly.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		//Update Preferred Hours
		while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
			profileNewUIPage.clickNextWeek();
		}
		int sliderIndex = 1;
		double hours = -0.5;//move 1 metric 0.5h left----decrease
		String leftOrRightDuration = "Right";
		String hoursType = "Preferred";
		String repeatChanges = "This week only";
		profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		loginPage.logOut();

		//Login as store manager to check cancelled request.
		loginAsDifferentRole(AccessRoles.StoreManager.getValue());
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(requestUserName);
		String workPreferencesLabel = "Work Preferences";
		profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
		profileNewUIPage.verifyClickCancelledAvalabilityRequest();

	}

	@Automated(automated ="Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate SM reject change availability request")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateRejectAvailabilityRequestAsStoreManager(String browser, String username, String password, String location) throws Exception {
		// Login with Store Manager Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		// Set availability policy
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		dashboardPage.navigateToDashboard();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		controlsPage.gotoControlsPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		String isApprovalRequired = "Required for all changes";
		controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		//Login as Team Member to change availability
		loginAsDifferentRole(AccessRoles.TeamMember.getValue());
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String requestUserName = profileNewUIPage.getNickNameFromProfile();
		String myWorkPreferencesLabel = "My Work Preferences";
		profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
		//cancel all availability change requests firstly.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		//Update Preferred Hours
		while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
			profileNewUIPage.clickNextWeek();
		}
		String weekInfo = profileNewUIPage.getAvailabilityWeek();
		String oldAvailableHrs = profileNewUIPage.getAvailableHoursForSpecificWeek();
		int sliderIndex = 1;
		double hours = 0.5;//move 1 metric 0.5h left----decrease
		String leftOrRightDuration = "Right";
		String hoursType = "Preferred";
		String repeatChanges = "This week only";
		String newAvailableHrs = profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		loginPage.logOut();

		//Login as store manager to reject the request.
		loginAsDifferentRole(AccessRoles.StoreManager.getValue());
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(requestUserName);
		String workPreferencesLabel = "Work Preferences";
		profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
		String rejectedCount = profileNewUIPage.getCountForStatus("rejected");
		profileNewUIPage.approveOrRejectSpecificPendingAvailabilityRequest(weekInfo, "Reject");
		if (SimpleUtils.isNumeric(rejectedCount)){
			int rejectedNum = Integer.valueOf(rejectedCount);
			SimpleUtils.assertOnFail("Rejected count should be 1 more than before.", String.valueOf((rejectedNum+1)).equalsIgnoreCase(profileNewUIPage.getCountForStatus("rejected").trim()), false);
		} else {
			SimpleUtils.fail("Count is not numeric", false);
		}
		while (!weekInfo.equalsIgnoreCase(profileNewUIPage.getAvailabilityWeek())){
			profileNewUIPage.clickNextWeek();
		}
		SimpleUtils.assertOnFail("Available hours didn't change to the old version!", oldAvailableHrs.equalsIgnoreCase(profileNewUIPage.getAvailableHoursForSpecificWeek()), false);
		SimpleUtils.assertOnFail("Pending count should be 0.", "0".equalsIgnoreCase(profileNewUIPage.getCountForStatus("pending").trim()), false);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate create change Availability request without approval")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void validateCreateAvailabilityRequestWithoutApprovalAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		// Login with Internal Admin Credentials
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		// Set availability policy
		ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
		controlsPage.gotoControlsPage();
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);

		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		String isApprovalRequired = "Not required";
		controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		loginPage.logOut();

		//Login as Team Member to change availability
		loginAsDifferentRole(AccessRoles.TeamMember.getValue());
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String requestUserName = profileNewUIPage.getNickNameFromProfile();
		String myWorkPreferencesLabel = "My Work Preferences";
		profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myWorkPreferencesLabel);
		//cancel all availability change requests firstly.
		profileNewUIPage.cancelAllPendingAvailabilityRequest();
		//Update Preferred Hours
		while (profileNewUIPage.isMyAvailabilityLockedNewUI()){
			profileNewUIPage.clickNextWeek();
		}
		String weekInfo = profileNewUIPage.getAvailabilityWeek();
		int sliderIndex = 1;
		double hours = 0.5;//move 1 metric 0.5h left----decrease
		String leftOrRightDuration = "Right";
		String hoursType = "Preferred";
		String repeatChanges = "This week only";
		String newAvailableHrs = profileNewUIPage.updateMyAvailability(hoursType, sliderIndex, leftOrRightDuration,
				hours, repeatChanges);
		SimpleUtils.assertOnFail("Available hours should be the new version.", newAvailableHrs.equalsIgnoreCase(profileNewUIPage.getAvailableHoursForSpecificWeek()), false);
		loginPage.logOut();

		//Login as admin to check and set back the setting.
		loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(requestUserName);
		String workPreferencesLabel = "Work Preferences";
		profileNewUIPage.selectProfilePageSubSectionByLabel(workPreferencesLabel);
		while (!profileNewUIPage.getAvailabilityWeek().equalsIgnoreCase(weekInfo)){
			profileNewUIPage.clickNextWeek();
		}
		SimpleUtils.assertOnFail("Available hours should be the new version.", newAvailableHrs.equalsIgnoreCase(profileNewUIPage.getAvailableHoursForSpecificWeek()), false);


		controlsPage.gotoControlsPage();
		SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
		controlsNewUIPage.clickOnControlsSchedulingPolicies();
		SimpleUtils.assertOnFail("Scheduling policy page not loaded successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
		controlsNewUIPage.clickOnGlobalLocationButton();
		isApprovalRequired = "Required for all changes";
		controlsNewUIPage.updateAvailabilityManagementIsApprovalRequired(isApprovalRequired);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Validate the SM, DM, TL can or cannot see the invite button on TM list and profile page when grant or ungrant Invite Employee permission to them")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyAbilityToHideInviteButtonAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

			//Grant Invite Employee permission for DM SM and TL
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			String accessRoleTab = "Access Roles";
			String rolePermissionForDM = "Area Manager";
			String rolePermissionForSM = "Store Manager";
			String rolePermissionForTL = "Team Lead";
			String section = "Team";
			String permission = "Invite Employee";
			String actionOff = "off";
			String actionOn = "on";
			if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
				controlsPage.gotoControlsPage();
				controlsNewUIPage.isControlsPageLoaded();
				controlsNewUIPage.clickOnControlsUsersAndRolesSection();
				controlsNewUIPage.verifyUsersAreLoaded();
				controlsPage.clickGlobalSettings();
				controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
				controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForDM, section, permission, actionOn);
				controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForSM, section, permission, actionOn);
				controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForTL, section, permission, actionOn);

			} else if (getDriver().getCurrentUrl().contains(propertyMap.get(opEnterprice))) {
				OpsPortalLocationsPage opsPortalLocationsPage = (OpsPortalLocationsPage) pageFactory.createOpsPortalLocationsPage();
				opsPortalLocationsPage.clickModelSwitchIconInDashboardPage(LocationsTest.modelSwitchOperation.OperationPortal.getValue());
				ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
				configurationPage.goToUserManagementPage();
				controlsNewUIPage.clickOnControlsUsersAndRolesSection();
				controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
				controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForDM, section, permission, actionOn);
				controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForSM, section, permission, actionOn);
				controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForTL, section, permission, actionOn);
				switchToConsoleWindow();
			}


			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			//Login as DM
			Thread.sleep(3000);
			loginAsDifferentRole(AccessRoles.DistrictManager.getValue());
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			//Go to team page and check the invite button
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			teamPage.goToTeam();
			HashMap<String, Object[][]> teamMembers = null;
			if (getDriver().getCurrentUrl().contains(propertyMap.get(controlEnterprice))){
				teamMembers = controlTeamMembers;
			} else {
				teamMembers = opTeamMembers;
			}

			String tm = teamMembers.get("TeamMember1")[0][0].toString();

			SimpleUtils.assertOnFail("The invite buttons fail to load on Roster page! ", teamPage.checkIsInviteButtonExists(), false);
			teamPage.searchAndSelectTeamMemberByName(tm);
			SimpleUtils.assertOnFail("The invite buttons fail to load on profile page! ", profileNewUIPage.isInviteToLegionButtonLoaded(), false);
			loginPage.logOut();
			//Login as SM
			Thread.sleep(3000);
			loginAsDifferentRole(AccessRoles.StoreManager.getValue());
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			//Go to team page and check the invite button
			teamPage.goToTeam();
			SimpleUtils.assertOnFail("The invite buttons fail to load on Roster page! ", teamPage.checkIsInviteButtonExists(), false);
			teamPage.searchAndSelectTeamMemberByName(tm);
			SimpleUtils.assertOnFail("The invite buttons fail to load on profile page! ", profileNewUIPage.isInviteToLegionButtonLoaded(), false);
			loginPage.logOut();
			Thread.sleep(3000);
			loginAsDifferentRole(AccessRoles.TeamLead.getValue());
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			//Go to team page and check the invite button
			teamPage.goToTeam();
			SimpleUtils.assertOnFail("The invite buttons fail to load on Roster page! ", teamPage.checkIsInviteButtonExists(), false);
			teamPage.searchAndSelectTeamMemberByName(tm);
			SimpleUtils.assertOnFail("The invite buttons fail to load on profile page! ", profileNewUIPage.isInviteToLegionButtonLoaded(), false);
			loginPage.logOut();
			//Login as admin
			Thread.sleep(3000);
			loginToLegionAndVerifyIsLoginDone(username, password, location);
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			controlsPage.gotoControlsPage();
			controlsNewUIPage.isControlsPageLoaded();
			controlsNewUIPage.clickOnControlsUsersAndRolesSection();
			controlsNewUIPage.verifyUsersAreLoaded();
			controlsPage.clickGlobalSettings();
			controlsNewUIPage.selectUsersAndRolesSubTabByLabel(accessRoleTab);
			controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForDM, section, permission, actionOff);
			controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForSM, section, permission, actionOff);
			controlsNewUIPage.turnOnOrOffSpecificPermissionForDifferentRole(rolePermissionForTL, section, permission, actionOff);
			loginPage.logOut();

			//Login as DM
			Thread.sleep(3000);
			loginAsDifferentRole(AccessRoles.DistrictManager.getValue());
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			//Go to team page and check the invite button
			teamPage.goToTeam();
			SimpleUtils.assertOnFail("The invite buttons fail to load on Roster page! ", !teamPage.checkIsInviteButtonExists(), false);
			teamPage.searchAndSelectTeamMemberByName(tm);
			SimpleUtils.assertOnFail("The invite buttons fail to load on profile page! ", !profileNewUIPage.isInviteToLegionButtonLoaded(), false);
			loginPage.logOut();
			//Login as SM
			Thread.sleep(3000);
			loginAsDifferentRole(AccessRoles.StoreManager.getValue());
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			//Go to team page and check the invite button
			teamPage.goToTeam();
			SimpleUtils.assertOnFail("The invite buttons fail to load on Roster page! ", !teamPage.checkIsInviteButtonExists(), false);
			teamPage.searchAndSelectTeamMemberByName(tm);
			SimpleUtils.assertOnFail("The invite buttons fail to load on profile page! ", !profileNewUIPage.isInviteToLegionButtonLoaded(), false);
			loginPage.logOut();
			//Login as TL
			Thread.sleep(3000);
			loginAsDifferentRole(AccessRoles.TeamLead.getValue());
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			//Go to team page and check the invite button
			teamPage.goToTeam();
			SimpleUtils.assertOnFail("The invite buttons fail to load on Roster page! ", !teamPage.checkIsInviteButtonExists(), false);
			teamPage.searchAndSelectTeamMemberByName(tm);
			SimpleUtils.assertOnFail("The invite buttons fail to load on profile page! ", !profileNewUIPage.isInviteToLegionButtonLoaded(), false);


		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}


	@Automated(automated ="Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Verify upcoming shifts when TM has access to multiple locations")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyUpcomingShiftsWhenTMHasAccessToMultipleLocationsAsTeamMember (String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

			//Get two locations of TM
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String tmName = profileNewUIPage.getNickNameFromProfile();
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			String location1 = location;
			String location2 = "";
			int i = 0;
			while (i <20 && (location2 == "" || location2.equalsIgnoreCase(location))) {
				location2 = locationSelectorPage.getOneRandomNameFromUpperFieldDropdownList("Location").split("\n")[0];
			}

			// Team Member logout
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();
			// Login as Admin
			loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location2);
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();

			//Ungenerate the first week schedule
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			//Go to next week and create schedule
			scheduleCommonPage.navigateToNextWeek();
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}

			//Delete all the shifts of TM
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(tmName);
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");

			//Create shift for TM on first day
			String workRole = shiftOperatePage.getRandomWorkRole();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.clearAllSelectedDays();
			newShiftPage.selectSpecificWorkDay(1);
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.moveSliderAtCertainPoint("4pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.moveSliderAtCertainPoint("11am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(tmName);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			scheduleCommonPage.clickOnDayView();
			scheduleCommonPage.navigateDayViewWithIndex(0);
			String weekDay1 = scheduleCommonPage.getScheduleWeekStartDayMonthDate();

			//Select another location
			locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location1);
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			//Ungenerate the first week schedule
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			//Go to next week and create schedule
			scheduleCommonPage.navigateToNextWeek();
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(tmName);
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");

			//Create shift for TM on second day
			workRole = shiftOperatePage.getRandomWorkRole();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.clearAllSelectedDays();
			newShiftPage.selectDaysByIndex(1, 1, 1);
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.moveSliderAtCertainPoint("3pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.moveSliderAtCertainPoint("10am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(tmName);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();

			//Get second shift info
			scheduleCommonPage.clickOnDayView();
			scheduleCommonPage.navigateDayViewWithIndex(1);
			String weekDay2 = scheduleCommonPage.getScheduleWeekStartDayMonthDate();
			scheduleCommonPage.clickOnWeekView();

			// All the shifts that are assigned to this TM should show, if it is created in another location, these shifts should be gray
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyTM.getValue());
			scheduleMainPage.clickOnOpenSearchBoxButton();
			scheduleMainPage.searchShiftOnSchedulePage(tmName);
			List<WebElement> shifts = scheduleShiftTablePage.getOneDayShiftByName(0, tmName);
			SimpleUtils.assertOnFail("The shift display correctly on the first day!",
					shifts.size() == 1 && shifts.get(0).getAttribute("class").contains("no-drag"), false);

			shifts = scheduleShiftTablePage.getOneDayShiftByName(1, tmName);
			SimpleUtils.assertOnFail("The shift dislay correctly on the first day!",
					shifts.size() == 1 && !shifts.get(0).getAttribute("class").contains("no-drag"), false);
			loginPage.logOut();

			//Login as TM, select the first location, observe the upcoming shifts
			loginAsDifferentRole(AccessRoles.TeamMember.getValue());
			List<HashMap<String, String>> shiftsInfo1= dashboardPage.getAllUpComingShiftsInfo();
			String shift1Time = "11:00am - 4:00pm";
			String shift2Time = "10:00am - 3:00pm";
			//Upcoming shifts are shown
			SimpleUtils.assertOnFail("The TM's upcoming shifts should equal or more than 2! ", shiftsInfo1.size() > 1, false);
			//Shift info, shift location should be correct
			SimpleUtils.assertOnFail("The first upcoming shift's location name should be: "+location2 +" , but actual is: " +shiftsInfo1.get(0).get("locationName"),
					shiftsInfo1.get(0).get("locationName").equalsIgnoreCase(location2), false);
			SimpleUtils.assertOnFail("The first upcoming shift's shift info should be: "+weekDay1 + " "+ shift1Time +" , but actual is: " +shiftsInfo1.get(0).get("shiftInfo").replace(",", ""),
					shiftsInfo1.get(0).get("shiftInfo").replace(",", "").equalsIgnoreCase(weekDay1 + " "+ shift1Time), false);
			SimpleUtils.assertOnFail("The second upcoming shift's location name should be: "+location1 +" , but actual is: " +shiftsInfo1.get(1).get("locationName"),
					shiftsInfo1.get(1).get("locationName").equalsIgnoreCase(location1), false);
			SimpleUtils.assertOnFail("The second upcoming shift's shift info should be: "+weekDay2 + " "+ shift2Time +" , but actual is: " +shiftsInfo1.get(1).get("shiftInfo").replace(",", ""),
					shiftsInfo1.get(1).get("shiftInfo").replace(",", "").equalsIgnoreCase(weekDay2 + " "+ shift2Time), false);
			//All the shifts assigned to this TM should show here
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.navigateToNextWeek();
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			SimpleUtils.assertOnFail("All the shifts assigned to this TM should show here! ",
					mySchedulePage.getAllAvailableShifts().size() == 2, false);
			//select the second location, the upcoming shifts should be consistent with the first location
			dashboardPage.clickOnDashboardConsoleMenu();
			locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(location2);
			List<HashMap<String, String>> shiftsInfo2= dashboardPage.getAllUpComingShiftsInfo();
			SimpleUtils.assertOnFail("", shiftsInfo2.equals(shiftsInfo1), false);
			//All the shifts assigned to this TM should show here
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.navigateToNextWeek();
			SimpleUtils.assertOnFail("All the shifts assigned to this TM should show here! ",
					mySchedulePage.getAllAvailableShifts().size() == 2, false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}
}
