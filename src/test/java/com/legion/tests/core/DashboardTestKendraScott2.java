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

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getEnterprise;

public class DashboardTestKendraScott2 extends TestBase {

	private static Map<String, String> propertyMap = SimpleUtils.getParameterMap();

	@Override
	@BeforeMethod()
	public void firstTest(Method testMethod, Object[] params) throws Exception {
		try {
			this.createDriver((String) params[0], "69", "Window");
			visitPage(testMethod);
			loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
		} catch (Exception e){
			SimpleUtils.fail(e.toString(), false);
		}
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-4961: Should be able to set Location at Global Level")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void navigateToDashboardFromGlobalSetting(String username, String password, String browser, String location) throws Exception {
		SimpleUtils.pass("Navigate to Dashboard Page Successfully!");
		SimpleUtils.pass("Click on Settings menu");
		SimpleUtils.pass("Go back to Dashboard assert Dashboard loaded Successfully");
		SimpleUtils.pass("Click on Settings menu again");
		SimpleUtils.pass("Click on Global icon present next to Settings at top left section");
		SimpleUtils.pass("Navigate back to Dashboard Page");
		SimpleUtils.pass("assert Dashboard page is Loaded Successfully!");
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-5231: Team Lead Should not see Today's Forecast and Projected Demand Graph present in Dashboard Section")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void todayForecastAndProjectedDemandGraphTeamLead(String username, String password, String browser, String location) throws Exception {
		SimpleUtils.pass("Login into LegionCooffee2 Application Successfully!");
		SimpleUtils.pass("Navigate to Dashboard Page Successfully!");
		SimpleUtils.fail("assert Today's Forecast and Projected Demand Graph should not be present for Team lead and Team member", false);
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the Store Company Location")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheDisplayLocationWithSelectedLocation(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.verifyTheDisplayLocationWithSelectedLocationConsistent();
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the functionality of Change Location button on click")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheClickActionOnChangeLocationButton(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.verifyClickChangeLocationButton();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the content getting displayed in the Change Location flyout")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheContentDisplayedInChangeLocationLayout(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.verifyTheContentOfDetailLocations();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the functionality of Search textbox in Change Location flyout")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheFunctionOfSearchTextBox(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			List<String> testStrings = new ArrayList<>(Arrays.asList("s", "h", "W"));
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.verifyTheFunctionOfSearchTextBox(testStrings);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify dashboard functionality when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyDashboardFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
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

			// T1838583 Validate the information after selecting different location.
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());

			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			teamPage.searchAndSelectTeamMemberByName(nickName);
			profileNewUIPage.selectProfilePageSubSectionByLabel("Time Off");
			profileNewUIPage.rejectAllTimeOff();

			SchedulePage schedulePageAdmin = pageFactory.createConsoleScheduleNewUIPage();
			scheduleCommonPage.goToConsoleScheduleAndScheduleSubMenu();
			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			String workRole = shiftOperatePage.getRandomWorkRole();
			shiftOperatePage.deleteTMShiftInWeekView(nickName);
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(nickName);
//		if (schedulePageAdmin.displayAlertPopUp())
//			schedulePageAdmin.displayAlertPopUpForRoleViolation();
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			List<String> scheduleListAdmin = scheduleShiftTablePage.getWeekScheduleShiftTimeListOfWeekView(nickName);
			loginPage.logOut();

			loginToLegionAndVerifyIsLoginDone(username, password, location);
			dashboardPage.validateDateAndTimeAfterSelectingDifferentLocation();
			SchedulePage schedulePageTM = pageFactory.createConsoleScheduleNewUIPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.navigateToNextWeek();
			SimpleUtils.assertOnFail("My Schedule page failed to load!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			List<String> scheduleListTM = new ArrayList<>();
			if (mySchedulePage.getShiftHoursFromInfoLayout().size() > 0) {
				for (String tmShiftTime : mySchedulePage.getShiftHoursFromInfoLayout()) {
					tmShiftTime = tmShiftTime.replaceAll(":00", "");
					scheduleListTM.add(tmShiftTime);
				}
			}
			if (scheduleListTM != null && scheduleListTM.size() > 0 && scheduleListAdmin != null && scheduleListAdmin.size() > 0) {
				if (scheduleListTM.size() == scheduleListAdmin.size() && scheduleListTM.containsAll(scheduleListAdmin)) {
					SimpleUtils.pass("Schedules in TM view is consistent with the Admin view of the location successfully");
				} else
					SimpleUtils.fail("Schedule doesn't show of the location correctly", true);
			} else {
				SimpleUtils.report("Schedule may have not been generated");
			}

			//T1838585 Validate date and time.
			dashboardPage.navigateToDashboard();
			dashboardPage.validateDateAndTime();

			//T1838586 Validate the upcoming schedules.
			dashboardPage.validateTheUpcomingSchedules(location);

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

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Dashboard functionality")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyDashboardFunctionality(String browser, String username, String password, String location) throws Exception {
		HashMap<String, String> upComingShifts = new HashMap<>();
		HashMap<String, String> fourShifts = new HashMap<>();
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		dashboardPage.verifyDashboardPageLoadedProperly();
		// Verify the Welcome section
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String nickName = profileNewUIPage.getNickNameFromProfile();
		dashboardPage.verifyTheWelcomeMessage(nickName);
		// Verify Today's forecast section > Projected Demand graph is present
		dashboardPage.isProjectedDemandGraphShown();

		// Make sure schedule is published
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
		if (isActiveWeekGenerated) {
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUI();
		createSchedulePage.publishActiveSchedule();
		dashboardPage.navigateToDashboard();

		HashMap<String, String> hoursOnDashboard = dashboardPage.getHoursFromDashboardPage();
		String dateFromDashboard = dashboardPage.getCurrentDateFromDashboard();
		String timeFromDashboard = dashboardPage.getCurrentTimeFromDashboard();

		schedulePage = dashboardPage.goToTodayForNewUI();
		SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(
				ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		// Verify View Today's schedule button is working and navigating to the schedule page[Current date in day view]
		scheduleCommonPage.isScheduleForCurrentDayInDayView(dateFromDashboard);
		HashMap<String, String> hoursOnSchedule = smartCardPage.getHoursFromSchedulePage();
		// Verify scheduled and other hours are matching with the Schedule smart card of Schedule page
		if (hoursOnDashboard != null && hoursOnSchedule != null) {
			if (hoursOnDashboard.equals(hoursOnSchedule)) {
				SimpleUtils.pass("Data Source for Budget, Scheduled and Other are consistent with the data on schedule page!");
			} else {
				SimpleUtils.fail("Data Source for Budget, Scheduled and Other are inconsistent with the data " +
						"on schedule page!", false);
			}
		} else {
			SimpleUtils.fail("Failed to get the hours!", false);
		}
		// Verify that Starting soon shifts and Scheduled hours are not showing when current week's schedule is in Guidance or Draft
		if (!createSchedulePage.isGenerateButtonLoaded()) {
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			createSchedulePage.isGenerateButtonLoaded();
		}
		dashboardPage.navigateToDashboard();
		boolean startingSoonLoaded = dashboardPage.isStartingSoonLoaded();
		HashMap<String, String> hours = dashboardPage.getHoursFromDashboardPage();
		// LEG-8474: When schedule of Current week is in Guidance, still data is showing on Dashboard
		// TODO: following check will fail since LEG-8474
		dashboardPage.verifyStartingSoonNScheduledHourWhenGuidanceOrDraft(startingSoonLoaded, hours.get("Scheduled"));
		// Verify starting soon section
		schedulePage = dashboardPage.goToTodayForNewUI();
		scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		if (!createSchedulePage.isPublishButtonLoaded()) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		createSchedulePage.publishActiveSchedule();
		dashboardPage.navigateToDashboard();
		dashboardPage.verifyDashboardPageLoadedProperly();
		startingSoonLoaded = dashboardPage.isStartingSoonLoaded();
		boolean isStartingTomorrow = dashboardPage.isStartingTomorrow();
		if (startingSoonLoaded) {
			upComingShifts = dashboardPage.getUpComingShifts();
			schedulePage = dashboardPage.goToTodayForNewUI();
			scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			fourShifts = scheduleShiftTablePage.getFourUpComingShifts(isStartingTomorrow, timeFromDashboard);
			scheduleShiftTablePage.verifyUpComingShiftsConsistentWithSchedule(upComingShifts, fourShifts);
		} else {
			SimpleUtils.fail("Shifts failed to load on Dashboard when the schedule is published!", false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the left navigation menu on login using admin access")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheLeftNavigationMenuOnLoginUsingAdminAccessAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try{
			verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("Admin");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the left navigation menu on login using SM access")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheLeftNavigationMenuOnLoginUsingSMAccessAsStoreManager(String browser, String username, String password, String location) throws Exception {
		try{
			verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("StoreManager");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the left navigation menu on login using TL access")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheLeftNavigationMenuOnLoginUsingTLAccessAsTeamLead(String browser, String username, String password, String location) throws Exception {
		try{
			verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("TeamLead");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the left navigation menu on login using TM access")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheLeftNavigationMenuOnLoginUsingTMAccessAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try{
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			dashboardPage.closeNewFeatureEnhancementsPopup();
			verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("TeamMember");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Validate the left navigation menu on login using DM access")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheLeftNavigationMenuOnLoginUsingDMAccessAsDistrictManager(String browser, String username, String password, String location) throws Exception {
		try{
			verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("DistrictManager");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Validate the left navigation menu on login using CA (Customer Admin) access")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheLeftNavigationMenuOnLoginUsingCAAccessAsCustomerAdmin(String browser, String username, String password, String location) throws Exception {
		try{
			verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess("CustomerAdmin");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	private void verifyTheLeftNavigationMenuOnLoginUsingDifferentAccess(String userRole) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
		//Check DashBoard console menu is display
		SimpleUtils.assertOnFail("DashBoard console menu not loaded Successfully!", dashboardPage.isDashboardConsoleMenuDisplay(), false);
		//Check dashboard page is display after click Dashboard console menu
		dashboardPage.clickOnDashboardConsoleMenu();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
		dashboardPage.verifyHeaderNavigationMessage("Dashboard");

		//Check Legion logo is display or not
		if (userRole.contains("Admin") || userRole.contains("StoreManager")
				|| userRole.contains("DistrictManager") || userRole.contains("CustomerAdmin")) {
			SimpleUtils.assertOnFail("Legion logo should be loaded!", dashboardPage.isLegionLogoDisplay(), false);
		} else
			SimpleUtils.assertOnFail("Legion logo should not be loaded!", !dashboardPage.isLegionLogoDisplay(), false);

		//Check District selector is display or not
		if (userRole.contains("Admin") || userRole.contains("CustomerAdmin") || userRole.contains("DistrictManager")) {
			SimpleUtils.assertOnFail("District selector should be loaded!", dashboardPage.IsThereDistrictNavigationForLegionBasic(), false);
			//Check District dropdown is display after click District selector
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.verifyClickChangeDistrictButton();
		} else
			SimpleUtils.assertOnFail("District selector should not be loaded!", !dashboardPage.IsThereDistrictNavigationForLegionBasic(), false);

		//Check Team console menu is display or not
		if (userRole.contains("TeamMember")) {
			SimpleUtils.assertOnFail("Team console menu should not loaded!", !dashboardPage.isTeamConsoleMenuDisplay(), false);
		} else{
			SimpleUtils.assertOnFail("Team console menu not loaded Successfully!", dashboardPage.isTeamConsoleMenuDisplay(), false);
			//Check Team page is display after click Team tab
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			dashboardPage.verifyHeaderNavigationMessage("Team");
		}

		//Check Schedule console menu is display
		SimpleUtils.assertOnFail("Schedule console menu not loaded Successfully!", dashboardPage.isScheduleConsoleMenuDisplay(), false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		//Check Schedule overview page is display after click Schedule tab
		if (userRole.contains("TeamLead") || userRole.contains("TeamMember")) {
			scheduleCommonPage.verifyTMSchedulePanelDisplay();
		} else {
			SimpleUtils.assertOnFail("Schedule page not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		}
		dashboardPage.verifyHeaderNavigationMessage("Schedule");

		//Check TimeSheet console menu is display or not
		if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			if (userRole.contains("Admin") || userRole.contains("StoreManager")
					|| userRole.contains("CustomerAdmin") || userRole.contains("DistrictManager")){
				SimpleUtils.assertOnFail("Timesheet console menu not loaded Successfully!", dashboardPage.isTimesheetConsoleMenuDisplay(), false);
				TimeSheetPage timeSheetPage = pageFactory.createTimeSheetPage();
				timeSheetPage.clickOnTimeSheetConsoleMenu();
				SimpleUtils.assertOnFail("Timesheet console page not loaded Successfully!", timeSheetPage.isTimeSheetPageLoaded(), false);
			} else{
				SimpleUtils.assertOnFail("Timesheet console menu should not loaded!", !dashboardPage.isTimesheetConsoleMenuDisplay(), false);
			}
		}

		//Check Report console menu is display or not
		if (userRole.contains("TeamLead") || userRole.contains("TeamMember")) {
			SimpleUtils.assertOnFail("Report console menu should not be loaded Successfully!",
					!dashboardPage.isReportConsoleMenuDisplay(), false);
		} else {
			SimpleUtils.assertOnFail("Report console menu not loaded Successfully!", dashboardPage.isReportConsoleMenuDisplay(), false);
			//Check Report page is display after click Report tab
			AnalyticsPage analyticsPage = pageFactory.createConsoleAnalyticsPage();
			analyticsPage.clickOnAnalyticsConsoleMenu();
			SimpleUtils.assertOnFail("Report Page not loaded Successfully!", analyticsPage.isReportsPageLoaded(), false);
			dashboardPage.verifyHeaderNavigationMessage("Report");
		}


		//Check Inbox console menu is display
		SimpleUtils.assertOnFail("Inbox console menu not loaded Successfully!", dashboardPage.isInboxConsoleMenuDisplay(), false);
		//Check Inbox page is display after click Inbox tab
		InboxPage inboxPage = pageFactory.createConsoleInboxPage();
		inboxPage.clickOnInboxConsoleMenuItem();
		SimpleUtils.assertOnFail("Inbox console menu not loaded Successfully!", inboxPage.isAnnouncementListPanelDisplay(), false);
		dashboardPage.verifyHeaderNavigationMessage("Inbox");

		//Check Admin console menu is display
		if (userRole.equalsIgnoreCase("Admin")){
			SimpleUtils.assertOnFail("Admin console menu not loaded Successfully!", dashboardPage.isAdminConsoleMenuDisplay(), false);
			//Check Admin page is display after click Admin tab
			dashboardPage.clickOnAdminConsoleMenu();
			dashboardPage.verifyAdminPageIsLoaded();
			dashboardPage.verifyHeaderNavigationMessage("Admin");
		} else
			SimpleUtils.assertOnFail("Admin console menu should not be loaded!", !dashboardPage.isAdminConsoleMenuDisplay(), false);

		//Check Integration console menu is display
		if (userRole.equalsIgnoreCase("Admin")) {
			SimpleUtils.assertOnFail("Integration console menu not loaded Successfully!", dashboardPage.isIntegrationConsoleMenuDisplay(), false);
			//Check Integration page is display after click Integration tab
			dashboardPage.clickOnIntegrationConsoleMenu();
			dashboardPage.verifyIntegrationPageIsLoaded();
			dashboardPage.verifyHeaderNavigationMessage("Integration");
		} else
			SimpleUtils.assertOnFail("Integration console menu should not be loaded!", !dashboardPage.isIntegrationConsoleMenuDisplay(), false);

		//Check Controls console menu is display
		if (userRole.contains("TeamLead") || userRole.contains("TeamMember")){
			SimpleUtils.assertOnFail("Controls console menu should not be loaded!", !dashboardPage.isControlsConsoleMenuDisplay(), false);
		} else {
			SimpleUtils.assertOnFail("Controls console menu not loaded Successfully!", dashboardPage.isControlsConsoleMenuDisplay(), false);
			//Check Controls page is display after click Controls tab
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.isControlsPageLoaded();
			dashboardPage.verifyHeaderNavigationMessage("Controls");
		}

		//Check Logout console menu is display
		SimpleUtils.assertOnFail("Logout console menu not loaded Successfully!", dashboardPage.isLogoutConsoleMenuDisplay(), false);
		//Check Login page is display after click Logout tab
		LoginPage loginPage = pageFactory.createConsoleLoginPage();
		Thread.sleep(5000);
		loginPage.logOut();
		loginPage.verifyLoginPageIsLoaded();
	}
}
