package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

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

public class TeamTest extends TestBase{
	
	private static Map<String, String> propertyMap = SimpleUtils.getParameterMap();
	private static HashMap<String, String> searchDetails = JsonUtil.getPropertiesFromJsonFile("src/test/resources/searchDetails.json");
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
	
	public enum weekCount{
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5),
		Six(6);
		private final int value;
		weekCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}
	
	public enum weekViewType{
		  Next("Next"),
		  Previous("Previous");
			private final String value;
			weekViewType(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}
	
	 @Automated(automated = "Manual")
	 @Owner(owner = "Gunjan")
	 @Enterprise(name = "Tech_Enterprise")
	 @TestName(description = "LEG-4978: In Team Page ,Coverage section is not displayed for LegionTech for Nov 4- Nov 10")
	 @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	 public void coverageForTeamPageNotWorking(String username, String password, String browser, String location)
	          throws Exception
	 {
	       SimpleUtils.pass("Login to leginTech Successfully");
	       SimpleUtils.pass("Successfully opened the Team Page");
	       SimpleUtils.pass("Click on Coverage tab");
	       SimpleUtils.pass("assert coverage page should load and show data");

	  }
	 
	 @Automated(automated = "Automated")
	 @Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "TP-81: Validate Team Search and Coverage in Team Tab")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateTeamTab(String username, String password, String browser, String location)
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
	        teamPage.coverageViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.Six.getValue());
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Team functionality In Activate")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInActivate(String browser, String username, String password, String location) throws Exception {
		String onBoarded = "Onboarded";
		String active = "Active";
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.isDashboardPageLoaded();
		String currentDay = dashboardPage.getCurrentDateFromDashboard();
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
		teamPage.isManualOnBoardButtonLoaded();
		teamPage.manualOnBoardTeamMember();
		teamPage.verifyTheStatusOfTeamMember(onBoarded);
		teamPage.isActivateButtonLoaded();
		//String onBoardedDate = teamPage.getOnBoardedDate();
		teamPage.clickOnActivateButton();
		teamPage.isActivateWindowLoaded();
		teamPage.selectADateOnCalendarAndActivate();
		// Verify While activating team Member, On boarded date is updating to new one and Deactivate & terminate button is enabled
		teamPage.verifyDeactivateAndTerminateEnabled();
		// TODO: To check whether the on boarded date should update or not
		//teamPage.isOnBoardedDateUpdated(onBoardedDate);
		// Verify Status will change into Activate status according to date
		teamPage.verifyTheStatusOfTeamMember(active);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.goToSchedulePage();
		schedulePage.isSchedulePage();
		schedulePage.goToSchedule();
		schedulePage.isSchedule();
		if (!schedulePage.isWeekGenerated()) {
			schedulePage.generateOrUpdateAndGenerateSchedule();
		}
		schedulePage.clickOnDayView();
		schedulePage.clickOnEditButton();
		schedulePage.isAddNewDayViewShiftButtonLoaded();
		schedulePage.clickNewDayViewShiftButtonLoaded();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		schedulePage.selectDaysFromCurrentDay(currentDay);
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.searchTeamMemberByName(firstName);
		schedulePage.clickOnOfferOrAssignBtn();
		schedulePage.clickOnWeekView();
		// Verify Shifts will go to Auto Scheduling  after Activating any TM
		schedulePage.verifyNewShiftsAreShownOnSchedule(firstName);
	}

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Team functionality In Terminate")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamFunctionalityInTerminate(String browser, String username, String password, String location) throws Exception {
		String timeZone = "";
		String active = "Active";
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.isDashboardPageLoaded();
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
		// Create a new team member
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.verifyTheFunctionOfAddNewTeamMemberButton();
		teamPage.isProfilePageLoaded();
		String firstName = teamPage.addANewTeamMemberToInvite(newTMDetails);
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(firstName);
		teamPage.isProfilePageLoaded();
		teamPage.isManualOnBoardButtonLoaded();
		teamPage.manualOnBoardTeamMember();
		teamPage.isActivateButtonLoaded();
		teamPage.clickOnActivateButton();
		teamPage.isActivateWindowLoaded();
		teamPage.selectADateOnCalendarAndActivate();
		teamPage.verifyTheStatusOfTeamMember(active);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		// Go to schedule page, assign shift to the new team member
		schedulePage.goToSchedulePage();
		schedulePage.isSchedulePage();
		schedulePage.goToSchedule();
		schedulePage.isSchedule();
		if (!schedulePage.isWeekGenerated()) {
			schedulePage.generateOrUpdateAndGenerateSchedule();
		}
		schedulePage.clickOnDayView();
		schedulePage.clickOnEditButton();
		schedulePage.isAddNewDayViewShiftButtonLoaded();
		schedulePage.clickNewDayViewShiftButtonLoaded();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		schedulePage.selectDaysFromCurrentDay(currentDay);
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.searchTeamMemberByName(firstName);
		schedulePage.clickOnOfferOrAssignBtn();
		schedulePage.clickOnWeekView();
		schedulePage.verifyNewShiftsAreShownOnSchedule(firstName);
		schedulePage.clickSaveBtn();
		List<Integer> indexes = schedulePage.getAddedShiftIndexes(firstName);
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchAndSelectTeamMemberByName(firstName);
		teamPage.isProfilePageLoaded();
		// Verify Cancel Termination button is working, TM will not removed from Roster after click on this button
		boolean isCancel = teamPage.isCancelTerminateButtonLoaded();
		if (!isCancel) {
			teamPage.isTerminateButtonLoaded();
			teamPage.terminateTheTeamMember(false);
		}
		teamPage.verifyTheFunctionOfCancelTerminate();
		String employeeID = teamPage.getEmployeeIDFromProfilePage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchTheTeamMemberByEmployeeIDFromRoster(employeeID, false);
		// Verify While Clicking on Terminate button, particular TM is able to removed from Team roster on the set date
		teamPage.searchAndSelectTeamMemberByName(firstName);
		teamPage.isProfilePageLoaded();
		teamPage.isTerminateButtonLoaded();
		teamPage.terminateTheTeamMember(true);
		String currentTime = SimpleUtils.getCurrentTimeWithTimeZone(timeZone);
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		teamPage.searchTheTeamMemberByEmployeeIDFromRoster(employeeID, true);
		// Verify TM's assigned shift is converting to open shift
		schedulePage.goToSchedulePage();
		schedulePage.isSchedulePage();
		schedulePage.goToSchedule();
		schedulePage.isSchedule();
		schedulePage.verifyShiftsChangeToOpenAfterTerminating(indexes, firstName, currentTime);
	}

}
