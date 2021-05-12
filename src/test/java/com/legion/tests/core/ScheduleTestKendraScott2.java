package com.legion.tests.core;

import java.awt.print.PrinterGraphics;
import java.lang.reflect.Method;
import java.util.*;

import com.gargoylesoftware.htmlunit.html.HtmlListing;
import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
import cucumber.api.java.ro.Si;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getWorkerRole;


public class ScheduleTestKendraScott2 extends TestBase {

	private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
	private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
	private static HashMap<String, String> schedulePolicyData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
	private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");

	@Override
	@BeforeMethod()
	public void firstTest(Method testMethod, Object[] params) {
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
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-4977: Republish Button is missing for finalized week")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void shouldRepublishButtonDisplyedForFinalizedWeek(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to leginTech Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Select date which is finalized week present in Schedule Overview");
		SimpleUtils.pass("Republish button is visible");

	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5064: On click refresh, Publish/Republish button disappears")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void onRefreshPublishButtonDisappears(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to leginCoffee Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Navigate to Oct15-Oct21 in Schedule tab");
		SimpleUtils.pass("Click Refresh");
		SimpleUtils.pass("assert on click refresh publish/republish button should not disappear");

	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-4845: Changes for Schedule wages are not getting reflected after adding new shift in Day view")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void scheduleWagesDoesNotGetUpdatedForAdminShift(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to LeginCoffee/LegionCoffee2 Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Add a Admin Shift Manual/Auto");
		SimpleUtils.pass("assert schedule wages should get increased for Admin shift");

	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "TP-43: should be able to convert to open shift for Current date")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void shouldConvertToOpenShiftOption(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to leginTech Successfully");
		SimpleUtils.pass("Successfully opened the Schedule app");
		SimpleUtils.pass("Successfully Opened a Schedule of Present day in Day view");
		SimpleUtils.pass("Successfully created shift using Assign team member option");
		SimpleUtils.pass("Click on edit button");
		SimpleUtils.pass("Convert to Open shift option is coming for the shift created in previous step");

	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-4845:Changes for Schedule wages are not getting reflected after adding new shift in Day view in LegionTech")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void scheduleWagesDoesNotChangeForNewAddedShift(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to LegionTech Successfully");
		SimpleUtils.pass("Navigate to Schedule tab and Add a new");
		SimpleUtils.pass("Observe the change in Schedule wages");
		SimpleUtils.pass("assert schedule wages should have some value according to admin working hour ");

	}


	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5110:Facing issue while deleting Shift using close icon in all the environments")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void scheduleDeletionNotWorking(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to Environment Successfully");
		SimpleUtils.pass("Navigate to Schedule tab and Add a new shift by editing the schedule");
		SimpleUtils.pass("Try deleting any shift by clicking over the desired schedule");
		SimpleUtils.pass("assert on click a red cross icon should appear and it should be clickable ");

	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5111:Projected sales and Staffing Guidance data are showing as 0 on generated schedule page in LegionCoffee2")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void projectedSalesAndStaffingGuidanceAreZeroOnGenerateSchedulePage(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to LegionCoffee2 Successfully");
		SimpleUtils.pass("Navigate to Schedule>Schedule (Mountain view location) and look for the week with schedule not generated");
		SimpleUtils.pass("Open week Oct15-Oct21");
		SimpleUtils.pass("assert for Projected sales and Staffing Guidance data should be non-0 once the schedule is generated ");

	}


	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5148:Budgeted Hrs and  Guidance Hrs  are different for the week ( Oct 07 - Oct 13) in LegionTech env")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void budgetAndGuidanceHourNotEqual(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to environment Successfully");
		SimpleUtils.pass("Navigate to schedule page");
		SimpleUtils.pass("click on (Oct 07 - Oct 13) week present in Overview Page ");
		SimpleUtils.pass("In Week view click on Analyze button");
		SimpleUtils.pass("Guidance Hrs should be equals to Budgeted Hrs");

	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5147:On click edit shifts under Compliance Review filter disappears")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void complianceReviewShiftsDisappear(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to environment Successfully");
		SimpleUtils.pass("Navigate to schedule page");
		SimpleUtils.pass("Open any week schedule");
		SimpleUtils.pass("Select Compliance Review in All Shift Type filters");
		SimpleUtils.pass("Click Edit button");
		SimpleUtils.fail("assert on click edit button shifts should not disappear", false);
	}


	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5183:Not able to select a member for Assign Team Member shift in LegionCoffee envirnment")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void shouldBeAddShiftUsingAssignTeamMember(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login to environment Successfully");
		SimpleUtils.pass("Navigate to schedule page");
		SimpleUtils.pass("Open any future week schedule");
		SimpleUtils.pass("Click Edit button");
		SimpleUtils.pass("Try adding a shift in day view");
		SimpleUtils.pass("Try adding a shift in day view");
		SimpleUtils.pass("Select Assign Team Member and try selecting a member from Search TM pop-up");
		SimpleUtils.pass("Able to select a member for Assign Team Member shift");
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5195: Schedule shifts are not aligned for Nov-12 when we select environment as LegionCoffee and location as Carmel Club")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void scheduleShiftsNotAligned(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login into LegionCoffee application successfully");
		SimpleUtils.pass("Change location to Carmel Club");
		SimpleUtils.pass("Click on Schedule button");
		SimpleUtils.pass("Click on Schedule and navigate till Nov -12 to Nov-18 week");
		SimpleUtils.pass("Click on day view and select Nov -12");
		SimpleUtils.pass("Click on Next week arrow");
		SimpleUtils.pass("Click on previous week arrow and select Nov-12");
		SimpleUtils.pass("assert schedule shifts should be aligned");
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5197: Schedules Hours in Schedule tab are not displaying for each locations if user selects any locations from All locations filter")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void scheduledHrsNotChangingOnAllLocationFilter(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login into LegionCoffee application successfully");
		SimpleUtils.pass("Select location as Bay area");
		SimpleUtils.pass("Click on Schedule button");
		SimpleUtils.pass("Click on Schedule Sub tab");
		SimpleUtils.pass("Select Carmal Club from All locations filter");
		SimpleUtils.pass("Click on Day view and select day as current date");
		SimpleUtils.fail("assert Schedule hours should display for each locations", false);
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5198: Not able to edit Budget as Budget popup is blank in LegionCoffee and LegionCoffee2 Environment")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void shouldBeAbleToEditOnStaffingGuidance(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login into LegionCoffee application successfully");
		SimpleUtils.pass("Select location as Carmel Club");
		SimpleUtils.pass("Click on Schedule button");
		SimpleUtils.pass("Click on Schedule Sub tab");
		SimpleUtils.pass("Select toggle summary view");
		SimpleUtils.pass("Open a Guidance week from Schedule Overview");
		SimpleUtils.fail("assert Budget popup should not be blank", false);
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5232: Data for Schedule does not get loaded when user clicks on next day without waiting data for highlighted day gets loaded")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void shouldBeAbleToLoadScheduleDataOnDayView(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login into application successfully");
		SimpleUtils.pass("Click on Schedule button");
		SimpleUtils.pass("Click on Schedule Sub tab");
		SimpleUtils.pass("Click on Day view");
		SimpleUtils.pass("Click on Next week arrow");
		SimpleUtils.fail("assert Click on day which is not highlighted and make sure Highlighted day does not get loaded before user clicks on other day.", false);
	}


	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-5232: Data for Schedule does not get loaded when user clicks on next day without waiting data for highlighted day gets loaded")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void groupByLocationFilterShouldBeSelected(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login into https://enterprise-stage.legion.work/legion/?enterprise=Coffee#/");
		SimpleUtils.pass("Select location as Bay Area");
		SimpleUtils.pass("Navigate to Schedule page");
		SimpleUtils.fail("assert GroupByLocation should be selected by Default on Schedule Page", false);
	}

	@Automated(automated = "Manual")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "LEG-5230: Group By selection filter is blank on navigating back from different tabs")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void groupByAllShouldNotBeBlank(String username, String password, String browser, String location)
			throws Exception {
		SimpleUtils.pass("Login into application successfully");
		SimpleUtils.pass("Navigate to Schedule page");
		SimpleUtils.pass("Select “Group By WorkRole” in the filter");
		SimpleUtils.pass("Navigate to overview tab and then navigate back to schedule tab");
		SimpleUtils.fail("assert Group By Workrole filter should be Sticky and should not be blank", false);
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Legion")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleLegionFunctionality(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.goToTodayForNewUI();
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.navigateToNextWeek();
		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
		schedulePage.legionButtonIsClickableAndHasNoEditButton();
		schedulePage.legionIsDisplayingTheSchedul();
	}


	@Automated(automated = "Auto")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Schedule")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalitySchedule(String username, String password, String browser, String location)
			throws Exception {
		boolean isWeekView = false;
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.goToScheduleNewUI();
		//Current week is getting open by default
		schedulePage.currentWeekIsGettingOpenByDefault(location);
		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
		//Weather week smartcard is displayed correctly-Current week[Sun-Sat] Next week will show which days are past day for current week: Eg: Sun for current week
		schedulePage.weatherWeekSmartCardIsDisplayedForAWeek();
		//If selecting one week then data is getting updating on Schedule page according to corresponding week
		schedulePage.scheduleUpdateAccordingToSelectWeek();
		//Print button is clickable
		String handle = getDriver().getWindowHandle();
		schedulePage.printButtonIsClickable();
		getDriver().switchTo().window(handle);

		//In week View > Clicking on Print button  it should give option to print in both Landscape or Portrait mode Both should work.
		//schedulePage.landscapePortraitModeShowWellInWeekView();
		//In Week view should be able to change the mode between Landscape and Portrait
		//schedulePage.landscapeModeWorkWellInWeekView();
		//schedulePage.portraitModeWorkWellInWeekView();
		//Day-week picker section navigating correctly
		//Todo:Run failed by LEG-10221
		schedulePage.dayWeekPickerSectionNavigatingCorrectly();
		//In Day view  Clicking on Print button it should give option to print in Landscape mode only
//		schedulePage.landscapeModeOnlyInDayView();
		schedulePage.clickOnWeekView();
		//Filter is working correctly if we select any one or more filters then schedule table data is updating according to that
		//Todo:Run failed by  LEG-10210
		schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
		//to do button is clickable
		//schedulePage.todoButtonIsClickable();
		//Todo:Run failed by Swap Cover Requested function error
		//schedulePage.verifyShiftSwapCoverRequestedIsDisplayInTo();
		//Analyze button is clickable:Staffing Guidance Schedule History-Scrollbar is working correctly version x details and close button is working
		schedulePage.verifyAnalyzeBtnFunctionAndScheduleHistoryScroll();


	}

		@Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality >  Compliance smartcard")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyComplianceSmartCardFunctionality(String username, String password, String browser, String location)
				throws Exception {
			SchedulePage schedulePage  = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.goToScheduleNewUI();
			if(schedulePage.verifyComplianceShiftsSmartCardShowing() && schedulePage.verifyRedFlagIsVisible()){
				schedulePage.verifyComplianceFilterIsSelectedAftClickingViewShift();
				schedulePage.verifyComplianceShiftsShowingInGrid();
				schedulePage.verifyClearFilterFunction();
			}else
				SimpleUtils.report("There is no compliance and no red flag");
		}


        @Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "Coffee_Enterprise")
		@TestName(description = "Verify the Schedule functionality > Schedule smartcard")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyScheduleFunctionalityScheduleSmartCard(String username, String password, String browser, String location)
				throws Exception {
			ArrayList<LinkedHashMap<String, Float>> scheduleOverviewAllWeekHours = new ArrayList<LinkedHashMap<String, Float>>();
			HashMap<String, Float> scheduleSmartCardHoursWages = new HashMap<>();
			HashMap<String, Float> overviewData = new HashMap<>();
			SchedulePage schedulePage  = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.goToScheduleNewUI();
			boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
			if(!isActiveWeekGenerated){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleSmartCardHoursWages = schedulePage.getScheduleBudgetedHoursInScheduleSmartCard();
			SimpleUtils.report("scheduleSmartCardHoursWages :"+scheduleSmartCardHoursWages);
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			scheduleOverviewPage.clickOverviewTab();
			List<WebElement> scheduleOverViewWeeks =  scheduleOverviewPage.getOverviewScheduleWeeks();
			overviewData = scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(0));
			SimpleUtils.report("overview data :"+scheduleOverviewPage.getWeekHoursByWeekElement(scheduleOverViewWeeks.get(0)));
			if (Math.abs(overviewData.get("guidanceHours") - (scheduleSmartCardHoursWages.get("budgetedHours"))) <= 0.05
					&& Math.abs(overviewData.get("scheduledHours") - (scheduleSmartCardHoursWages.get("scheduledHours"))) <= 0.05
					&& Math.abs(overviewData.get("otherHours") - (scheduleSmartCardHoursWages.get("otherHours"))) <= 0.05) {
				SimpleUtils.pass("Schedule/Budgeted smartcard-is showing the values in Hours and wages, it is displaying the same data as overview page have for the current week .");
			}else {
				SimpleUtils.fail("Scheduled Hours and Overview Schedule Hours not same, hours on smart card for budget, scheduled and other are: " +
						scheduleSmartCardHoursWages.get("budgetedHours") + ", " + scheduleSmartCardHoursWages.get("scheduledHours") + ", " + scheduleSmartCardHoursWages.get("otherHours")
						+ ". But hours on Overview page are: " + overviewData.get("guidanceHours") + ", " + overviewData.get("scheduledHours") + ", " + overviewData.get("otherHours"),false);
			}
		}

	
	@Automated(automated = "Automated")
	@Owner(owner = "Estelle/Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality - Week View - Context Menu")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalityWeekViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		schedulePage.navigateToNextWeek();

		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(isActiveWeekGenerated){
			schedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		schedulePage.createScheduleForNonDGFlowNewUI();
		//In week view, Group by All filter have 4 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
		schedulePage.validateGroupBySelectorSchedulePage(false);
		//Selecting any of them, check the schedule table
		schedulePage.validateScheduleTableWhenSelectAnyOfGroupByOptions(false);

		//Edit button should be clickable
		//While click on edit button,if Schedule is finalized then prompt is available and Prompt is in proper alignment and correct msg info.
		//Edit anyway and cancel button is clickable
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

		//click on the context of any TM, 1. View profile 2. Change shift role  3.Assign TM 4.  Convert to open shift is enabled for current and future week day 5.Edit meal break time 6. Delete shift
		schedulePage.selectNextWeekSchedule();
		schedulePage.navigateToNextWeek();
		boolean isActiveWeekGenerated2 = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated2){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.deleteTMShiftInWeekView("Unassigned");
		schedulePage.saveSchedule();
		schedulePage.publishActiveSchedule();
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		SimpleUtils.assertOnFail(" context of any TM display doesn't show well" , schedulePage.verifyContextOfTMDisplay(), false);

		//"After Click on view profile,then particular TM profile is displayed :1. Personal details 2. Work Preferences 3. Availability
		schedulePage.clickOnViewProfile();
		schedulePage.verifyPersonalDetailsDisplayed();
		schedulePage.verifyWorkPreferenceDisplayed();
		schedulePage.verifyAvailabilityDisplayed();
		schedulePage.closeViewProfileContainer();

		//"After Click on the Change shift role, one prompt is enabled:various work role any one of them can be selected"
		schedulePage.clickOnProfileIcon();
		schedulePage.clickOnChangeRole();
		schedulePage.verifyChangeRoleFunctionality();
		//check the work role by click Apply button
		schedulePage.changeWorkRoleInPrompt(true);
		//check the work role by click Cancel button
		schedulePage.changeWorkRoleInPrompt(false);

		//After Click on Assign TM-Select TMs window is opened,Recommended and search TM tab is enabled
		schedulePage.clickOnProfileIcon();
		schedulePage.clickonAssignTM();
		schedulePage.verifyRecommendedAndSearchTMEnabled();

		//Search and select any TM,Click on the assign: new Tm is updated on the schedule table
		//Select new TM from Search Team Member tab
		schedulePage.clickOnFilterBtn();
		schedulePage.selectWorkRoleFilterByText("MOD", true);
		WebElement selectedShift = null;
		selectedShift = schedulePage.clickOnProfileIcon();
		String selectedShiftId= selectedShift.getAttribute("id").toString();
		schedulePage.clickonAssignTM();
		String firstNameOfSelectedTM = schedulePage.selectTeamMembers();
		schedulePage.clickOnOfferOrAssignBtn();
		SimpleUtils.assertOnFail(" New selected TM doesn't display in scheduled table" , firstNameOfSelectedTM.equals(schedulePage.getShiftById(selectedShiftId).findElement(By.className("week-schedule-worker-name")).getText().trim()), false);
		//Select new TM from Recommended TMs tab
		selectedShift = schedulePage.clickOnProfileIcon();
		String selectedShiftId2 = selectedShift.getAttribute("id").toString();
		schedulePage.clickonAssignTM();
		schedulePage.switchSearchTMAndRecommendedTMsTab();
		String firstNameOfSelectedTM2 = schedulePage.selectTeamMembers();
		schedulePage.clickOnOfferOrAssignBtn();
		SimpleUtils.assertOnFail(" New selected TM doesn't display in scheduled table" , firstNameOfSelectedTM2.equals(schedulePage.getShiftById(selectedShiftId2).findElement(By.className("week-schedule-worker-name")).getText().trim()), false);
		schedulePage.clickOnFilterBtn();
		schedulePage.clickOnClearFilterOnFilterDropdownPopup();
		schedulePage.clickOnFilterBtn();

		//Click on the Convert to open shift, checkbox is available to offer the shift to any specific TM[optional] Cancel /yes
		//if checkbox is unselected then, shift is convert to open
		selectedShift = schedulePage.clickOnProfileIcon();
		String tmFirstName = selectedShift.findElement(By.className("week-schedule-worker-name")).getText();
		schedulePage.clickOnConvertToOpenShift();
		if (schedulePage.verifyConvertToOpenPopUpDisplay(tmFirstName)) {
			schedulePage.convertToOpenShiftDirectly();
		}
        //if checkbox is select then select team member page will display
		selectedShift = schedulePage.clickOnProfileIcon();
		tmFirstName = selectedShift.findElement(By.className("week-schedule-worker-name")).getText();
		schedulePage.clickOnConvertToOpenShift();
		if (schedulePage.verifyConvertToOpenPopUpDisplay(tmFirstName)) {
			schedulePage.convertToOpenShiftAndOfferToSpecificTMs();
		}

		//After click on Edit Shift Time, the Edit Shift window will display
		schedulePage.clickOnProfileIcon();
		schedulePage.clickOnEditShiftTime();
		schedulePage.verifyEditShiftTimePopUpDisplay();
		schedulePage.clickOnCancelEditShiftTimeButton();
		//Edit shift time and click update button
		schedulePage.editAndVerifyShiftTime(true);
		//Edit shift time and click Cancel button
		schedulePage.editAndVerifyShiftTime(false);

		//Verify Edit/View Meal Break
		if (schedulePage.isEditMealBreakEnabled()){
			//After click on Edit Meal Break Time, the Edit Meal Break window will display
			schedulePage.verifyMealBreakTimeDisplayAndFunctionality(true);
			//Verify Delete Meal Break
			schedulePage.verifyDeleteMealBreakFunctionality();
			//Edit meal break time and click update button
			schedulePage.verifyEditMealBreakTimeFunctionality(true);
			//Edit meal break time and click cancel button
			schedulePage.verifyEditMealBreakTimeFunctionality(false);
		} else
			schedulePage.verifyMealBreakTimeDisplayAndFunctionality(false);

		//verify cancel button
		schedulePage.verifyDeleteShiftCancelButton();

		//verify delete shift
		schedulePage.verifyDeleteShift();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Day View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalityDayViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

		// Select one team member to view profile
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		String userName = teamPage.selectATeamMemberToViewProfile();
		String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

		//Overtime hours = shift total hours  - 8h
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		//Current week and day is selected by default
		schedulePage.currentWeekIsGettingOpenByDefault(location);
		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(isActiveWeekGenerated){
			schedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		schedulePage.createScheduleForNonDGFlowNewUI();
		//click on Edit button to add new shift
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.clickOnDayView();
		schedulePage.navigateToNextDayIfStoreClosedForActiveDay();
		schedulePage.deleteAllShiftsInDayView();
		schedulePage.saveSchedule();
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		SimpleUtils.assertOnFail("Add new shift button is not loaded", !schedulePage.isAddNewDayViewShiftButtonLoaded() , true);

		//"while selecting Open shift:Auto,create button is enabled one open shift will created and system will offer shift automatically
		schedulePage.clickOnDayViewAddNewShiftButton();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();

//		"While selecting Open shift:Manual,Next button is enabled,After Click on Next Select Tms window is enabled and after selecting N number of TMs, offer will send to them"
		schedulePage.clickOnDayViewAddNewShiftButton();
		schedulePage.customizeNewShiftPage();
		String defaultTimeDuration = schedulePage.getTimeDurationWhenCreateNewShift();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		String defaultTimeDurationAftDrag = schedulePage.getTimeDurationWhenCreateNewShift();
		if (!defaultTimeDurationAftDrag.equals(defaultTimeDuration)) {
			SimpleUtils.pass("A shift time and duration can be changed by dragging it");
		}else
			SimpleUtils.report("there is no change for time duration");
		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.customizeNewShiftPage();
		schedulePage.verifySelectTeamMembersOption();
		schedulePage.clickOnOfferOrAssignBtn();

//		While selecting Assign TM,Next button is enabled, After Click on Next, Select Tm window is enabled and only one TM can be selected, and shift will assign to him/her
		schedulePage.clickOnDayViewAddNewShiftButton();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.verifySelectTeamMembersOption();
		schedulePage.clickOnOfferOrAssignBtn();
        //While click on any shift from Schedule grid , X red button is enabled to delete the shift
		schedulePage.validateXButtonForEachShift();

		//If a shift is more than 8 hours (defined in Controls) then Daily OT hours(Daily OT should be enabled in Controls) should show
		//if make X hour overtime, the Daily OT will be show
		int otFlagCount = schedulePage.getOTShiftCount();
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.clickOnDayViewAddNewShiftButton();
		schedulePage.customizeNewShiftPage();
		//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), dragIncreasePoint, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.verifySelectTeamMembersOption();
		schedulePage.clickOnOfferOrAssignBtn();
		schedulePage.saveSchedule();
		int otFlagCountAftAddNewShift = schedulePage.getOTShiftCount();
		if (otFlagCountAftAddNewShift > otFlagCount) {
			SimpleUtils.pass("OT shit add successfully");
		}else
			SimpleUtils.fail("add OT new shift failed" , false);


		//If a TM has more than 40 working hours in a week (As defined in Controls) then Week OT hours should show (Week OT should be enabled in Controls)
		int workWeekOverTime = Integer.valueOf(schedulePolicyData.get("single_workweek_overtime"));
		int dayCountInOneWeek = Integer.valueOf(propertyCustomizeMap.get("WORKDAY_COUNT"));
		schedulePage.clickOnWeekView();
		float shiftHoursInWeekForTM = schedulePage.getShiftHoursByTMInWeekView(firstName);
		schedulePage.clickOnDayView();
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.deleteTMShiftInWeekView(firstName);
		if (shiftHoursInWeekForTM == 0) {
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
			if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
			}
			schedulePage.selectSpecificWorkDay(dayCountInOneWeek);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(firstName);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.clickOnWeekView();
			float shiftHoursInWeekForTMAftAddNewShift = schedulePage.getShiftHoursByTMInWeekView(firstName);

			if ((shiftHoursInWeekForTMAftAddNewShift -shiftHoursInWeekForTM)>workWeekOverTime) {
				schedulePage.verifyWeeklyOverTimeAndFlag(firstName);
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(firstName);
			schedulePage.saveSchedule();
		}else{
			schedulePage.deleteTMShiftInWeekView(firstName);
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
			if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
			}
			schedulePage.selectSpecificWorkDay(dayCountInOneWeek);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(firstName);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.clickOnWeekView();
			float shiftHoursInWeekForTMAftAddNewShift = schedulePage.getShiftHoursByTMInWeekView(firstName);

			if (shiftHoursInWeekForTMAftAddNewShift > workWeekOverTime) {
				schedulePage.verifyWeeklyOverTimeAndFlag(firstName);
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(firstName);
			schedulePage.saveSchedule();
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Week View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleInWeekViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {

		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());

		/*
		 *  Navigate to Schedule Week view
		 */
		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
		boolean isWeekView = true;
		schedulePage.clickOnWeekView();
		schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		schedulePage.filterScheduleByJobTitle(isWeekView);
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.filterScheduleByJobTitle(isWeekView);
		schedulePage.clickOnCancelButtonOnEditMode();
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Day View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleInDayViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {

		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());

		/*
		 *  Navigate to Schedule day view
		 */
		boolean isWeekView = false;
		boolean isWeekGenerated = schedulePage.isWeekGenerated();
		if (!isWeekGenerated){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
		schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		schedulePage.filterScheduleByJobTitle(isWeekView);
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.filterScheduleByJobTitle(isWeekView);
		schedulePage.clickOnCancelButtonOnEditMode();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Combination")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleFilterCombinationInWeekViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {

		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		/*
		 *  Navigate to Schedule week view
		 */
		boolean isWeekView = true;
		boolean isWeekGenerated = schedulePage.isWeekGenerated();
		if (!isWeekGenerated){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}

		schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		schedulePage.filterScheduleByWorkRoleAndJobTitle(isWeekView);
		schedulePage.filterScheduleByShiftTypeAndJobTitle(isWeekView);
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.filterScheduleByJobTitle(isWeekView);
		schedulePage.clickOnCancelButtonOnEditMode();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of My Schedule Page")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verificationOfMySchedulePageAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();

			//T1838603 Validate the availability of schedule table.
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String nickName = profileNewUIPage.getNickNameFromProfile();
			schedulePage.validateTheAvailabilityOfScheduleTable(nickName);

			//T1838604 Validate the disability of location selector on Schedule page.
			schedulePage.validateTheDisabilityOfLocationSelectorOnSchedulePage();

			//T1838605 Validate the availability of profile menu.
			schedulePage.validateTheAvailabilityOfScheduleMenu();

			//T1838606 Validate the focus of schedule.
			schedulePage.validateTheFocusOfSchedule();

			//T1838607 Validate the default filter is selected as Scheduled.
			schedulePage.validateTheDefaultFilterIsSelectedAsScheduled();

			//T1838608 Validate the focus of week.
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			dashboardPage.navigateToDashboard();
			String currentDate = dashboardPage.getCurrentDateFromDashboard();
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.validateTheFocusOfWeek(currentDate);

			//T1838609 Validate the selection of previous and upcoming week.
			schedulePage.verifySelectOtherWeeks();
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of To and Fro navigation of week picker")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verificationOfToAndFroNavigationOfWeekPickerAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String nickName = profileNewUIPage.getNickNameFromProfile();

			//T1838610 Validate the click ability of forward and backward button.
			schedulePage.validateForwardAndBackwardButtonClickable();

			//T1838611 Validate the data according to the selected week.
			schedulePage.validateTheDataAccordingToTheSelectedWeek();

			//T1838612 Validate the seven days - Sunday to Saturday is available in schedule table.
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.validateTheSevenDaysIsAvailableInScheduleTable();
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			///Log in as admin to get the operation hours
			String fileName = "UsersCredentials.json";
			fileName = SimpleUtils.getEnterprise("KendraScott2_Enterprise") + fileName;
			HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
			Object[][] internalAdminCredentials = userCredentials.get("InternalAdmin");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(internalAdminCredentials[0][0]), String.valueOf(internalAdminCredentials[0][1])
					, String.valueOf(internalAdminCredentials[0][2]));
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Go to Scheduling Policies to get the additional Scheduled Hour
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			SimpleUtils.assertOnFail("Scheduling Policies Page not loaded Successfully!", controlsNewUIPage.isControlsSchedulingPoliciesLoaded(), false);
			HashMap<String, Integer> schedulePoliciesBufferHours = controlsNewUIPage.getScheduleBufferHours();

			SchedulePage schedulePageAdmin = pageFactory.createConsoleScheduleNewUIPage();
			schedulePageAdmin.clickOnScheduleConsoleMenuItem();
			schedulePageAdmin.clickOnScheduleSubTab("Schedule");
			schedulePageAdmin.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.selectWorkRole("MOD");
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(nickName);
			if(schedulePage.displayAlertPopUp())
				schedulePage.displayAlertPopUpForRoleViolation();
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
			if (!schedulePage.isSummaryViewLoaded())
				schedulePage.toggleSummaryView();
			String theEarliestAndLatestTimeInSummaryView = schedulePage.getTheEarliestAndLatestTimeInSummaryView(schedulePoliciesBufferHours);
			SimpleUtils.report("theEarliestAndLatestOperationHoursInSummaryView is " + theEarliestAndLatestTimeInSummaryView);
			loginPage.logOut();

			///Log in as team member again to compare the operation hours
			loginToLegionAndVerifyIsLoginDone(username, password, location);
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.navigateToNextWeek();
			String theEarliestAndLatestTimeInScheduleTable = schedulePage.getTheEarliestAndLatestTimeInScheduleTable();
			SimpleUtils.report("theEarliestAndLatestOperationHoursInScheduleTable is " + theEarliestAndLatestTimeInScheduleTable);
			schedulePage.compareOperationHoursBetweenAdminAndTM(theEarliestAndLatestTimeInSummaryView, theEarliestAndLatestTimeInScheduleTable);

			//T1838613 Validate that hours and date is visible of shifts.
			schedulePage.validateThatHoursAndDateIsVisibleOfShifts();
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Profile picture functionality")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyProfilePictureFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();

			//T1838614 Validate the clickability of Profile picture in a shift.
			schedulePage.validateProfilePictureInAShiftClickable();

			//T1838615 Validate the data of profile popup in a shift.
			//schedulePage.validateTheDataOfProfilePopupInAShift();
			// todo: <Here is an incident LEG-10929>
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Info icon functionality")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyInfoIconFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();

			//T1838616 Validate the availability of info icon.
			schedulePage.validateTheAvailabilityOfInfoIcon();

			//T1838617 Validate the clickability of info icon.
			schedulePage.validateInfoIconClickable();

			//T1838618 Validate the availability of Meal break as per the control settings.
			//schedulePage.validateMealBreakPerControlSettings();
			// todo: <Meal break is a postpone feature>
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of Open Shift Schedule Smart Card")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyOpenShiftScheduleSmartCardAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();

			//T1838619 Validate the availability of Open shift Smartcard.
			schedulePage.validateTheAvailabilityOfOpenShiftSmartcard();

			//T1838620 Validate the clickability of View shifts in Open shift smartcard.
			schedulePage.validateViewShiftsClickable();

			//T1838621 Validate the number of open shifts in smartcard and schedule table.
			schedulePage.validateTheNumberOfOpenShifts();

			//T1838622 Verify the availability of claim open shift popup.
			schedulePage.verifyTheAvailabilityOfClaimOpenShiftPopup();
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the content of copy schedule for non dg flow")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheContentOfCopyScheduleForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();

			// Get the hours and the count of the tms for each day, ex: "37.5 Hrs 5TMs"
			HashMap<String, String> hoursNTMsCountFirstWeek = schedulePage.getTheHoursNTheCountOfTMsForEachWeekDays();
			HashMap<String, List<String>> shiftsForEachDayFirstWeek = schedulePage.getTheContentOfShiftsForEachWeekDay();
			HashMap<String, String> budgetNScheduledHoursFirstWeek = schedulePage.getBudgetNScheduledHoursFromSmartCard();
			String cardName = "COMPLIANCE";
			boolean isComplianceCardLoadedFirstWeek = schedulePage.isSpecificSmartCardLoaded(cardName);
			int complianceShiftCountFirstWeek = 0;
			if (isComplianceCardLoadedFirstWeek) {
				complianceShiftCountFirstWeek = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
			}
			String firstWeekInfo = schedulePage.getActiveWeekText();
			if (firstWeekInfo.length() > 11) {
				firstWeekInfo = firstWeekInfo.trim().substring(10);
				if (firstWeekInfo.contains("-")) {
					String[] temp = firstWeekInfo.split("-");
					if (temp.length == 2 && temp[0].contains(" ") && temp[1].contains(" ")) {
						firstWeekInfo = temp[0].trim().split(" ")[0] + " " + (temp[0].trim().split(" ")[1].length() == 1 ? "0" + temp[0].trim().split(" ")[1] : temp[0].trim().split(" ")[1])
								+ " - " + temp[1].trim().split(" ")[0] + " " + (temp[1].trim().split(" ")[1].length() == 1 ? "0" + temp[1].trim().split(" ")[1] : temp[1].trim().split(" ")[1]);
					}
				}
			}

			schedulePage.navigateToNextWeek();
			schedulePage.isSchedule();
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			List<String> weekDaysToClose = new ArrayList<>();
			schedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, null);

			HashMap<String, String> hoursNTMsCountSecondWeek = schedulePage.getTheHoursNTheCountOfTMsForEachWeekDays();
			HashMap<String, List<String>> shiftsForEachDaySecondWeek = schedulePage.getTheContentOfShiftsForEachWeekDay();
			HashMap<String, String> budgetNScheduledHoursSecondWeek = schedulePage.getBudgetNScheduledHoursFromSmartCard();
			boolean isComplianceCardLoadedSecondWeek = schedulePage.isSpecificSmartCardLoaded(cardName);
			int complianceShiftCountSecondWeek = 0;
			if (isComplianceCardLoadedFirstWeek) {
				complianceShiftCountSecondWeek = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
			}

			if (hoursNTMsCountFirstWeek.equals(hoursNTMsCountSecondWeek)) {
				SimpleUtils.pass("Verified the scheduled hour and TMs of each week day are consistent with the copied schedule!");
			} else {
				SimpleUtils.fail("Verified the scheduled hour and TMs of each week day are inconsistent with the copied schedule", true);
			}
			if (SimpleUtils.compareHashMapByEntrySet(shiftsForEachDayFirstWeek, shiftsForEachDaySecondWeek)) {
				SimpleUtils.pass("Verified the shifts of each week day are consistent with the copied schedule!");
			} else {
				SimpleUtils.fail("Verified the shifts of each week day are inconsistent with the copied schedule!", true);
			}
			if (budgetNScheduledHoursFirstWeek.get("Scheduled").equals(budgetNScheduledHoursSecondWeek.get("Scheduled"))) {
				SimpleUtils.pass("The Scheduled hour is consistent with the copied scheudle: " + budgetNScheduledHoursFirstWeek.get("Scheduled"));
			} else {
				SimpleUtils.fail("The Scheduled hour is inconsistent, the first week is: " + budgetNScheduledHoursFirstWeek.get("Scheduled")
						+ ", but second week is: " + budgetNScheduledHoursSecondWeek.get("Scheduled"), true);
			}
			if ((isComplianceCardLoadedFirstWeek == isComplianceCardLoadedSecondWeek) && (complianceShiftCountFirstWeek == complianceShiftCountSecondWeek)) {
				SimpleUtils.pass("Verified Compliance is consistent with the copied schedule");
			} else {
				SimpleUtils.fail("Verified Compliance is inconsistent with the copied schedule!", true);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content of closed week day schedule for non dg flow ")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheContentOfClosedWeekDayScheduleForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			List<String> weekDaysToClose = new ArrayList<>(Arrays.asList("Sunday", "Tuesday"));
			schedulePage.createScheduleForNonDGByWeekInfo("SUGGESTED", weekDaysToClose, null);

			// Verify that the closed week day should not have any shifts
			schedulePage.verifyNoShiftsForSpecificWeekDay(weekDaysToClose);
			// Go to day view, check the closed week day should show "Store is Closed"
			schedulePage.clickOnDayView();
			schedulePage.verifyStoreIsClosedForSpecificWeekDay(weekDaysToClose);
			// Toggle Summary view, verify that the specific week days shows Closed
			schedulePage.toggleSummaryView();
			schedulePage.verifyClosedDaysInToggleSummaryView(weekDaysToClose);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content after changing Budget Hours, and the budget and scheduled hours in smart card for non dg flow")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentOfBudgetHoursForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.goToConsoleScheduleAndScheduleSubMenu();
			String weekInfo = schedulePage.getWeekInfoBeforeCreateSchedule();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			HashMap<String,String> budgetNScheduledFromGraph = schedulePage.verifyNGetBudgetNScheduleWhileCreateScheduleForNonDGFlowNewUI(weekInfo, location);
			String budgetFromGraph = budgetNScheduledFromGraph.get("Budget");
			String scheduledFromGraph = budgetNScheduledFromGraph.get("Scheduled");
			if (!schedulePage.isSummaryViewLoaded())
				schedulePage.toggleSummaryView();
			List<String> budgetsOnSTAFF = schedulePage.getBudgetedHoursOnSTAFF();
			String budgetOnWeeklyBudget = schedulePage.getBudgetOnWeeklyBudget();
			HashMap<String, String> budgetNScheduledHoursFromSmartCard = schedulePage.getBudgetNScheduledHoursFromSmartCard();
			String budgetFromSmartCard = budgetNScheduledHoursFromSmartCard.get("Budget");
			String scheduledFromSmartCard = budgetNScheduledHoursFromSmartCard.get("Scheduled");
			System.out.println("budgetOnWeeklyBudget is: "+budgetOnWeeklyBudget);
			String totalBudgetOnSTAFF = "";
			if (budgetsOnSTAFF.size() > 1) {
				totalBudgetOnSTAFF = budgetsOnSTAFF.get(budgetsOnSTAFF.size() - 1);
				System.out.println("totalBudgetOnSTAFF is: "+totalBudgetOnSTAFF);
			}
			if (budgetOnWeeklyBudget.equals(budgetFromGraph) && budgetFromSmartCard.equals(budgetOnWeeklyBudget) && totalBudgetOnSTAFF.equals(budgetFromGraph))
				SimpleUtils.pass("The budget hours is consistent with the saved value both in STAFF and smart cards (including Weekly)");
			else
				SimpleUtils.warn("The budget hours is inconsistent with the saved value both in STAFF and smart cards (including Weekly) since there are bugs https://legiontech.atlassian.net/browse/SF-1054 and (https://legiontech.atlassian.net/browse/SF-1113");
			if (scheduledFromGraph.equals(scheduledFromSmartCard))
				SimpleUtils.pass("The scheduled hours is consistent with the saved value both in STAFF and smart card");
			else
				SimpleUtils.fail("The scheduled hours is inconsistent with the saved value both in STAFF and smart card",false);
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content after changing Operating Hours for non dg flow ")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentAfterChangingOperatingHrsAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.clickOnScheduleSubTab("Schedule");
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			if (schedulePage.isWeekGenerated()){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			String day = "Sunday";
			String startTime = "07:00AM";
			String endTime = "06:00PM";
			//set operating hours: Sunday -> start time: 07:00AM, end time: 06:00PM
			schedulePage.createScheduleForNonDGFlowNewUIWithGivingParameters(day, startTime, endTime);
			schedulePage.verifyDayHasShifts(day);
			schedulePage.verifyDayHasShifts("Monday");
			schedulePage.verifyDayHasShifts("Tuesday");
			schedulePage.verifyDayHasShifts("Wednesday");
			schedulePage.verifyDayHasShifts("Thursday");
			schedulePage.verifyDayHasShifts("Friday");
			schedulePage.verifyDayHasShifts("Saturday");
			schedulePage.goToToggleSummaryView();
			//verify the operating hours in Toggle Summary View
			schedulePage.verifyOperatingHrsInToggleSummary(day, startTime, endTime);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content after changing Operating Hours for non dg flow - next day")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentAfterChangingOperatingHrsNextDayAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.clickOnScheduleSubTab("Schedule");
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			if (schedulePage.isWeekGenerated()){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			String day = "Sunday";
			String startTime = "11:00AM";
			String endTime = "02:00AM";
			//set operating hours: Sunday -> start time: 11:00AM, end time: 02:00AM
			schedulePage.createScheduleForNonDGFlowNewUIWithGivingParameters(day, startTime, endTime);
			//verify the day we set a next day time has shifts.
			schedulePage.verifyDayHasShifts(day);
			//verify the operating hours in Toggle Summary View
			schedulePage.goToToggleSummaryView();
			schedulePage.verifyOperatingHrsInToggleSummary(day, startTime, endTime);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the budget hour in DM view schedule page for non dg flow")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyBudgetHourInDMViewSchedulePageForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try{
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			float budgetHoursInSchedule = Float.parseFloat(schedulePage.getBudgetNScheduledHoursFromSmartCard().get("Budget"));
			dashboardPage.clickOnDashboardConsoleMenu();
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.changeDistrictDirect();
			schedulePage.clickOnScheduleConsoleMenuItem();

			ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
			float budgetedHoursInDMViewSchedule = scheduleDMViewPage.getBudgetedHourOfScheduleInDMViewByLocation(location);
			if (budgetHoursInSchedule != 0 && budgetHoursInSchedule == budgetedHoursInDMViewSchedule) {
				SimpleUtils.pass("Verified the budget hour in DM view schedule page is consistent with the value saved in create schedule page!");
			} else {
				SimpleUtils.fail("Verified the budget hour in DM view schedule page is consistent with the value saved in create schedule page! The budget hour in DM view schedule page is " +
						budgetedHoursInDMViewSchedule + ". The value saved in create schedule page is " + budgetHoursInSchedule, false);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}


	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify smart card for schedule not publish(include past weeks)")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySmartCardForScheduleNotPublishAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.clickOnScheduleSubTab("Schedule");
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			if (schedulePage.isWeekGenerated()){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("unassigned");
			//make edits
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.saveSchedule();
			//generate and save, should not display number of changes, we set it as 0.
			int changesNotPublished = 0;
			//Verify changes not publish smart card.
			SimpleUtils.assertOnFail("Changes not publish smart card is not loaded!",schedulePage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
			schedulePage.verifyChangesNotPublishSmartCard(changesNotPublished);
			schedulePage.verifyLabelOfPublishBtn("Publish");
			String activeWeek = schedulePage.getActiveWeekText();
			schedulePage.clickOnScheduleSubTab("Overview");
			List<String> resultListInOverview = schedulePage.getOverviewData();
			for (String s : resultListInOverview){
				String a = s.substring(1,7);
				if (activeWeek.toLowerCase().contains(a.toLowerCase())){
					if (s.contains("Unpublished Edits")){
						SimpleUtils.pass("Warning message in overview page is correct!");
					} else {
						SimpleUtils.fail("Warning message is not expected: "+ s.split(",")[4],false);
					}
				}
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify smart card for schedule not publish(include past weeks) - republish")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyNumberOnSmartCardForScheduleNotPublishAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.clickOnScheduleSubTab("Schedule");
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			if (schedulePage.isWeekGenerated()){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			//make edits and publish
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
			//make edits and save
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.saveSchedule();
			//generate and save, should not display number of changes, we set it as 0.
			int changesNotPublished = 1;
			//Verify changes not publish smart card.
			SimpleUtils.assertOnFail("Changes not publish smart card is not loaded!",schedulePage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
			schedulePage.verifyChangesNotPublishSmartCard(changesNotPublished);
			schedulePage.verifyLabelOfPublishBtn("Republish");
			String activeWeek = schedulePage.getActiveWeekText();
			schedulePage.clickOnScheduleSubTab("Overview");
			List<String> resultListInOverview = schedulePage.getOverviewData();
			for (String s : resultListInOverview){
				String a = s.substring(1,7);
				if (activeWeek.toLowerCase().contains(a.toLowerCase())){
					if (s.contains("Unpublished Edits")){
						SimpleUtils.pass("Warning message in overview page is correct!");
					} else {
						SimpleUtils.fail("Warning message is not expected: "+ s.split(",")[4],false);
					}
				}
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	// Add the new test cases for "Schedule Not Published"
	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "verify smart card for compliance violation")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyComplianceViolationWhenScheduleIsNotPublishedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			String cardName = "COMPLIANCE";
			int originalComplianceCount = 0;
			if (schedulePage.isSpecificSmartCardLoaded(cardName)) {
				originalComplianceCount = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
			}
			schedulePage.clickOnDayView();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				schedulePage.dragOneShiftToMakeItOverTime();
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				schedulePage.clickOnProfileIcon();
				schedulePage.clickOnEditShiftTime();
				schedulePage.verifyEditShiftTimePopUpDisplay();
				schedulePage.editShiftTimeToTheLargest();
				schedulePage.clickOnUpdateEditShiftTimeButton();
			}
			schedulePage.saveSchedule();
			schedulePage.clickOnWeekView();
			int currentComplianceCount = 0;
			if (schedulePage.isSpecificSmartCardLoaded(cardName)) {
				currentComplianceCount = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
				if (currentComplianceCount == originalComplianceCount + 1) {
					SimpleUtils.pass("Schedule Week View: Compliance Count is correct after updating a new overtime shift!");
				} else {
					SimpleUtils.fail("Schedule Week View: Compliance Count is incorrect, original is: " + originalComplianceCount + ", current is: "
							+ currentComplianceCount + ", the difference between two numbers should equal to 1!", false);
				}
			} else {
				SimpleUtils.fail("Schedule Week View: Compliance smart card failed to show!", false);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "verify smart card for compliance violation -republish")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyComplianceViolationWhenScheduleHasPublishedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();

			String cardName = "COMPLIANCE";
			int originalComplianceCount = 0;
			if (schedulePage.isSpecificSmartCardLoaded(cardName)) {
				originalComplianceCount = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
			}
			schedulePage.clickOnDayView();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				schedulePage.dragOneShiftToMakeItOverTime();
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				schedulePage.clickOnProfileIcon();
				schedulePage.clickOnEditShiftTime();
				schedulePage.verifyEditShiftTimePopUpDisplay();
				schedulePage.editShiftTimeToTheLargest();
				schedulePage.clickOnUpdateEditShiftTimeButton();
			}
			schedulePage.saveSchedule();
			schedulePage.clickOnWeekView();
			int currentComplianceCount = 0;
			if (schedulePage.isSpecificSmartCardLoaded(cardName)) {
				currentComplianceCount = schedulePage.getComplianceShiftCountFromSmartCard(cardName);
				if (currentComplianceCount > originalComplianceCount) {
					SimpleUtils.pass("Schedule Week View: Compliance Count is correct after updating a new overtime shift!");
				} else {
					SimpleUtils.fail("Schedule Week View: Compliance Count is incorrect, original is: " + originalComplianceCount + ", current is: "
							+ currentComplianceCount + ", the difference between two numbers should equal or larger than 1!", false);
				}
			} else {
				SimpleUtils.report("Schedule Week View: Compliance smart card failed to show! Please check if the TM is exempt!");
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}
	
	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify tooltip for delete shifts")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTooltipForDeleteShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.goToConsoleScheduleAndScheduleSubMenu();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated)
				schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.addOpenShiftWithLastDay("MOD");
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.addOpenShiftWithFirstDay("MOD");
			schedulePage.deleteLatestOpenShift();
			schedulePage.saveSchedule();

			// Check the number on changes not publish smart card
			if (schedulePage.getChangesOnActionRequired().contains("2 Changes"))
				SimpleUtils.pass("ACTION REQUIRED Smart Card: The number on changes not publish smart card is 2");
			else
				SimpleUtils.fail("ACTION REQUIRED Smart Card: The number on changes not publish smart card is incorrect",true);

			// Filter unpublished changes to check the shifts and tooltip
			schedulePage.selectShiftTypeFilterByText("Unpublished changes");
			if (schedulePage.getShiftsCount() == 1)
				SimpleUtils.pass("ACTION REQUIRED Smart Card: There is only one shift as expected");
			else
				SimpleUtils.fail("ACTION REQUIRED Smart Card: There is not only one shift unexpectedly",true);
			if (schedulePage.getTooltipOfUnpublishedDeleted().contains("1 shift deleted"))
				SimpleUtils.pass("ACTION REQUIRED Smart Card: \"1 shift deleted\" tooltip shows up on smart card");
			else
				SimpleUtils.fail("ACTION REQUIRED Smart Card: \"1 shift deleted\" tooltip doesn't show up on smart card",false);
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify assign TM warning: TM status is inactive")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMWarningWhenTMIsInactiveAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			// Prepare a TM who is inactive
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.clickOnControlsUsersAndRolesSection();
			String inactiveUser = controlsNewUIPage.selectAnyActiveTM();
			String date = controlsNewUIPage.deactivateActiveTM();

			// Assign to the TM to verify the message and warning
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.goToConsoleScheduleAndScheduleSubMenu();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.selectAShiftToAssignTM(inactiveUser);
			schedulePage.verifyInactiveMessageNWarning(inactiveUser,date);

			// Restore the TM to be active
			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.clickOnControlsUsersAndRolesSection();
			controlsNewUIPage.searchAndSelectTeamMemberByName(inactiveUser);
			controlsNewUIPage.activateInactiveTM();
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "verify the Unpublished Edits on dashboard and overview page")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyUnpublishedEditsTextOnDashboardAndOverviewPageAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try{
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.addOpenShiftWithLastDay("MOD");
			schedulePage.saveSchedule();

			//Verify the Unpublished Edits text on overview page
			schedulePage.clickOnScheduleConsoleMenuItem();
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			List<WebElement> schedulesInOverviewPage = scheduleOverviewPage.getOverviewScheduleWeeks();
			if (schedulesInOverviewPage != null && schedulesInOverviewPage.size()>0){
				WebElement warningTextOfCurrentScheduleWeek = schedulesInOverviewPage.get(0).findElement(By.cssSelector("div.text-small.ng-binding"));
				if (warningTextOfCurrentScheduleWeek != null){
					String warningText = warningTextOfCurrentScheduleWeek.getText();
					if (warningText !=null && warningText.equals("Unpublished Edits")){
						SimpleUtils.pass("Verified the Unpublished Edits on Overview page display correctly. ");
					} else{
						SimpleUtils.fail("Verified the Unpublished Edits on Overview page display incorrectly. The actual warning text is " + warningText +".", true);
					}
				}
			} else{
				SimpleUtils.fail("Overview Page: Schedule weeks not found!" , true);
			}

			//Verify the Unpublished Edits text on dashboard page
			dashboardPage.navigateToDashboard();
			dashboardPage.clickOnRefreshButton();
			List<WebElement> dashboardScheduleWeeks = dashboardPage.getDashboardScheduleWeeks();
			if (dashboardScheduleWeeks != null && dashboardScheduleWeeks.size()>0){
				WebElement warningTextOfCurrentScheduleWeek = dashboardScheduleWeeks.get(1).findElement(By.cssSelector("div.text-small.ng-binding"));
				if (warningTextOfCurrentScheduleWeek != null){
					String warningText = warningTextOfCurrentScheduleWeek.getText();
					if (warningText !=null && warningText.equals("Unpublished Edits")){
						SimpleUtils.pass("Verified the Unpublished Edits text on Dashboard page display correctly. ");
					} else{
						SimpleUtils.fail("Verified the Unpublished Edits text on Dashboard page display incorrectly. The actual warning text is " + warningText +".", false);
					}
				}
			} else{
				SimpleUtils.fail("Dashboard Page: Schedule weeks not found!" , false);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "verify Assign TM warning: TM status is on time off")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMWarningForTMIsOnTimeOffAsStoreManager(String browser, String username, String password, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String nickNameFromProfile = profileNewUIPage.getNickNameFromProfile();
			String myProfileLabel = "My Profile";
			profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
			SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
			String aboutMeLabel = "About Me";
			profileNewUIPage.selectProfilePageSubSectionByLabel(aboutMeLabel);
			String myTimeOffLabel = "My Time Off";
			profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
			profileNewUIPage.cancelAllTimeOff();
			profileNewUIPage.clickOnCreateTimeOffBtn();
			SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
			String timeOffReasonLabel = "VACATION";
			// select time off reason
			profileNewUIPage.selectTimeOffReason(timeOffReasonLabel);
			profileNewUIPage.selectStartAndEndDate();
			profileNewUIPage.clickOnSaveTimeOffRequestBtn();


			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnDayView();
			//navigate to the time off day
			for (int i=0; i<6;i++){
				schedulePage.clickOnNextDaySchedule(schedulePage.getActiveAndNextDay());
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
			}
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(nickNameFromProfile);
			schedulePage.verifyMessageIsExpected("time off");
			schedulePage.verifyWarningModelForAssignTMOnTimeOff(nickNameFromProfile);
			schedulePage.clickOnCancelButtonOnEditMode();


			//go to cancel the time off.
			profileNewUIPage.getNickNameFromProfile();
			profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myProfileLabel);
			SimpleUtils.assertOnFail("Profile page not loaded Successfully!", profileNewUIPage.isProfilePageLoaded(), false);
			profileNewUIPage.selectProfilePageSubSectionByLabel(aboutMeLabel);
			profileNewUIPage.selectProfilePageSubSectionByLabel(myTimeOffLabel);
			profileNewUIPage.cancelAllTimeOff();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify assign TM warning: Assignment rule violation with conf is yes")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMWarningForTMHasRoleViolationWithConfYesAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			//Go to control to set the override assignment rule as Yes.
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.clickOnGlobalLocationButton();
			controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
			controlsNewUIPage.enableOverRideAssignmentRuleAsYes();

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName("Keanu");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Assign TM warning: TM status is already Scheduled at Home location")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMWarningForTMIsAlreadyScheduledAsStoreManager(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			List<String> firstShiftInfo = new ArrayList<>();
			while(firstShiftInfo.size() == 0 || firstShiftInfo.get(0).equalsIgnoreCase("open")
					|| firstShiftInfo.get(0).equalsIgnoreCase("unassigned")){
				firstShiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkingDaysOnNewShiftPageByIndex(Integer.parseInt(firstShiftInfo.get(1)));
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.verifyScheduledWarningWhenAssigning(firstShiftInfo.get(0), firstShiftInfo.get(2));
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Assign TM warning: TM is from another store and is already scheduled at this store")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMWarningForTMIsAlreadyScheduledAtAnotherStoreAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			String nearByLocation = "NY CENTRAL";
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			List<String> firstShiftInfo = schedulePage.getTheShiftInfoByIndex(0);
			if (!schedulePage.isWeekPublished()) {
				schedulePage.publishActiveSchedule();
			}

			// Navigate to the near by location to create the shift for this TM from AUSTIN DOWNTOWN
			dashboardPage.navigateToDashboard();
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.changeLocation(nearByLocation);
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkingDaysOnNewShiftPageByIndex(Integer.parseInt(firstShiftInfo.get(1)));
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.verifyScheduledWarningWhenAssigning(firstShiftInfo.get(0), firstShiftInfo.get(2));
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify version number in Analyze page and edits persist when navigating to Suggested and back to Manager")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyVersionNumberAndEditsAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//verify version number in analyze page

			schedulePage.clickOnAnalyzeBtn();
			String version0 = "Suggested Schedule";
			String version1 = "0.1";
			String version2 = "1.1";
			schedulePage.verifyScheduleVersion(version0);
			schedulePage.closeAnalyzeWindow();
			//make edits and save
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.verifyVersionInSaveMessage(version1);
			//suggested tab
			schedulePage.clickOnSuggestedButton();
			SimpleUtils.assertOnFail("Changes not publish smart card is loaded in suggested page!",!schedulePage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
			schedulePage.clickOnManagerButton();
			SimpleUtils.assertOnFail("Changes not publish smart card is not loaded in Manager page!",schedulePage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
			schedulePage.clickOnAnalyzeBtn();
			schedulePage.verifyScheduleVersion(version1);
			schedulePage.closeAnalyzeWindow();
			schedulePage.publishActiveSchedule();
			schedulePage.clickOnAnalyzeBtn();
			schedulePage.verifyScheduleVersion(version2);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify offers generated for open shift")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyOffersGeneratedForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//verify shifts are auto assigned.
			//schedulePage.verifyAllShiftsAssigned();
			//schedulePage.clickOnEditButton();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("unassigned");
			schedulePage.deleteTMShiftInWeekView("open");
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnProfileIcon();
			schedulePage.clickOnConvertToOpenShift();
			schedulePage.convertToOpenShiftDirectly();
			int index = schedulePage.getTheIndexOfEditedShift();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
			//debug log---start
			for (String s: schedulePage.getTheShiftInfoByIndex(index)){
				SimpleUtils.report(s);
			}
			//debug log---end
			schedulePage.clickProfileIconOfShiftByIndex(index);
			schedulePage.clickViewStatusBtn();
			schedulePage.verifyListOfOfferNotNull();

		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify \"Offer Team Members\" option is available for Open shift")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyOfferTMOptionAvailableForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
				schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

		schedulePage.navigateToNextWeek();
		boolean isWeekGenerated = schedulePage.isWeekGenerated();
		if (isWeekGenerated){
			schedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		schedulePage.createScheduleForNonDGFlowNewUI();

		//delete unassigned shifts and open shifts.
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.deleteTMShiftInWeekView("unassigned");
		schedulePage.deleteTMShiftInWeekView("open");
		schedulePage.saveSchedule();
		List<String> shiftInfo = new ArrayList<>();
		while (shiftInfo.size() == 0) {
			shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
		}
		String firstNameOfTM = shiftInfo.get(0);
		String workRoleOfTM = shiftInfo.get(4);


		//verify assigned shift in non-edit mode for week view and day view
		schedulePage.clickOnProfileIcon();
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
		schedulePage.clickOnDayView();
		schedulePage.clickOnProfileIconOfShiftInDayView("no");
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
		schedulePage.clickOnWeekView();

		//verify assigned shift in edit mode for week view and day view
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.clickOnProfileIcon();
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
		schedulePage.clickOnDayView();
		schedulePage.clickOnProfileIconOfShiftInDayView("no");
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", !schedulePage.isOfferTMOptionVisible(), false);
		schedulePage.saveSchedule();
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

		//create auto open shifts.
		schedulePage.clickOnDayViewAddNewShiftButton();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.selectWorkRole(workRoleOfTM);
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();

		//verify auto open shift in edit mode.
			//--verify in day view
		schedulePage.clickOnProfileIconOfShiftInDayView("open");
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			//--verify in week view
		schedulePage.clickOnWeekView();
		schedulePage.clickOnProfileIconOfOpenShift();
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
		schedulePage.saveSchedule();
		//verify auto open shift in non-edit mode.
			//--verify in day view
		schedulePage.clickOnDayView();
		schedulePage.clickOnProfileIconOfShiftInDayView("open");
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			//--verify in week view
		schedulePage.clickOnWeekView();
		schedulePage.clickOnProfileIconOfOpenShift();
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);

		//create manual open shifts.
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.deleteTMShiftInWeekView("open");
		schedulePage.clickOnDayView();
		schedulePage.clickOnDayViewAddNewShiftButton();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.selectWorkRole(workRoleOfTM);
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.verifySelectTeamMembersOption();
		schedulePage.clickOnOfferOrAssignBtn();
		//verify manual open shift in edit mode.
			//--verify in day view
		schedulePage.clickOnProfileIconOfShiftInDayView("open");
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			//--verify in week view
		schedulePage.clickOnWeekView();
		schedulePage.clickOnProfileIconOfOpenShift();
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
		schedulePage.saveSchedule();
		//verify manual open shifts in non-edit mode.
			//--verify in day view
		schedulePage.clickOnDayView();
		schedulePage.clickOnProfileIconOfShiftInDayView("open");
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			//--verify in week view
		schedulePage.clickOnWeekView();
		schedulePage.clickOnProfileIconOfOpenShift();
		SimpleUtils.assertOnFail("Offer TMs option should be visible!", schedulePage.isOfferTMOptionVisible(), false);
		SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Auto in non-Edit mode")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyFunctionalityOfOfferTMForAutoOpenShiftsInNonEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			LoginPage loginPage = pageFactory.createConsoleLoginPage();

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
			loginPage.logOut();

			String fileName = "UsersCredentials.json";
			String workRoleOfTM = "";
			fileName = this.enterpriseName+fileName;
			if (this.enterpriseName.contains("cinemark-wkdy")){
				workRoleOfTM = "Team Member Corporate-Theatre";
			} else {//KendraScott2_Enterprise by default
				workRoleOfTM = scheduleWorkRoles.get("MOD");
			}

			HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

			Object[][] adminCredentials = userCredentials.get("InternalAdmin");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
					, String.valueOf(adminCredentials[0][2]));
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//delete unassigned shifts and open shifts.
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("unassigned");
			schedulePage.deleteTMShiftInWeekView("open");



			//create auto open shifts.
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(workRoleOfTM);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.saveSchedule();

			//verify auto open shift in non-edit mode.
			schedulePage.clickOnProfileIconOfOpenShift();
			SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			schedulePage.clickOnOfferTMOption();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.verifyRecommendedTableHasTM();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.searchTeamMemberByName(firstNameOfTM);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.clickOnProfileIconOfOpenShift();
			schedulePage.clickViewStatusBtn();
			schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
			schedulePage.closeViewStatusContainer();
			loginPage.logOut();

			//login with TM.
			Object[][] TMCredentials = userCredentials.get("TeamMember");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
					, String.valueOf(TMCredentials[0][2]));
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.navigateToNextWeek();
			schedulePage.clickLinkOnSmartCardByName("View Shifts");
			SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Auto in Edit Mode")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyFunctionalityOfOfferTMForAutoOpenShiftsInEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			LoginPage loginPage = pageFactory.createConsoleLoginPage();

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
			loginPage.logOut();

			String fileName = "UsersCredentials.json";
			String workRoleOfTM = "";
			fileName = this.enterpriseName+fileName;
			if (this.enterpriseName.contains("cinemark-wkdy")){
				workRoleOfTM = "Team Member Corporate-Theatre";
			} else {//KendraScott2_Enterprise by default
				workRoleOfTM = scheduleWorkRoles.get("MOD");
			}

			HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

			Object[][] adminCredentials = userCredentials.get("InternalAdmin");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
					, String.valueOf(adminCredentials[0][2]));
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//delete unassigned shifts and open shifts.
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.deleteTMShiftInWeekView("Open");

			//create auto open shifts.
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.moveSliderAtCertainPoint("8","8");
			schedulePage.selectWorkRole(workRoleOfTM);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
			String selectedShiftId= selectedShift.getAttribute("id");
			int index = schedulePage.getShiftIndexById(selectedShiftId);

			//verify auto open shift in edit mode.
			List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
			schedulePage.clickOnProfileIconOfOpenShift();
			SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			schedulePage.clickOnOfferTMOption();
			String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.verifyRecommendedTableHasTM();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.searchTeamMemberByName(firstNameOfTM);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.clickOnProfileIconOfOpenShift();
			schedulePage.clickViewStatusBtn();
			schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
			schedulePage.closeViewStatusContainer();
			SimpleUtils.assertOnFail("shift time is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(2).toLowerCase()), false);
			SimpleUtils.assertOnFail("shift work role is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(4).toLowerCase()), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Manual in non-Edit mode")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyFunctionalityOfOfferTMForManualOpenShiftsInNonEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			LoginPage loginPage = pageFactory.createConsoleLoginPage();

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
			loginPage.logOut();

			String fileName = "UsersCredentials.json";
			String workRoleOfTM = "";
			fileName = this.enterpriseName+fileName;
			if (this.enterpriseName.contains("cinemark-wkdy")){
				workRoleOfTM = "Team Member Corporate-Theatre";
			} else {//KendraScott2_Enterprise by default
				workRoleOfTM = scheduleWorkRoles.get("MOD");
			}

			HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

			Object[][] adminCredentials = userCredentials.get("InternalAdmin");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
					, String.valueOf(adminCredentials[0][2]));
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//delete unassigned shifts and open shifts.
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("unassigned");
			schedulePage.deleteTMShiftInWeekView("open");



			//create manual open shifts.
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(workRoleOfTM);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.verifySelectTeamMembersOption();
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();

			//verify manual open shift in non-edit mode.
			schedulePage.clickOnProfileIconOfOpenShift();
			SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			schedulePage.clickOnOfferTMOption();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.verifyRecommendedTableHasTM();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.clickOnProfileIconOfOpenShift();
			schedulePage.clickViewStatusBtn();
			schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
			schedulePage.closeViewStatusContainer();
			loginPage.logOut();

			//login with TM.
			Object[][] TMCredentials = userCredentials.get("TeamMember");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
					, String.valueOf(TMCredentials[0][2]));
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.navigateToNextWeek();
			schedulePage.clickLinkOnSmartCardByName("View Shifts");
			SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Verify the functionality of \"Offer Team Members\" for Open Shift: Manual in Edit Mode")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyFunctionalityOfOfferTMForManualOpenShiftsInEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			LoginPage loginPage = pageFactory.createConsoleLoginPage();

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
			loginPage.logOut();

			String fileName = "UsersCredentials.json";
			String workRoleOfTM = "";
			fileName = this.enterpriseName+fileName;
			if (this.enterpriseName.contains("cinemark-wkdy")){
				workRoleOfTM = "Team Member Corporate-Theatre";
			} else {//KendraScott2_Enterprise by default
				workRoleOfTM = scheduleWorkRoles.get("MOD");
			}

			HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

			Object[][] adminCredentials = userCredentials.get("InternalAdmin");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
					, String.valueOf(adminCredentials[0][2]));
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//delete unassigned shifts and open shifts.
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.deleteTMShiftInWeekView("Open");

			//create manual open shifts.
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.moveSliderAtCertainPoint("8","8");
			schedulePage.selectWorkRole(workRoleOfTM);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.verifySelectTeamMembersOption();
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
			String selectedShiftId= selectedShift.getAttribute("id");
			int index = schedulePage.getShiftIndexById(selectedShiftId);

			//verify manual open shift in edit mode.
			List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
			schedulePage.clickOnProfileIconOfOpenShift();
			SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			schedulePage.clickOnOfferTMOption();
			String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.verifyRecommendedTableHasTM();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.clickOnProfileIconOfOpenShift();
			schedulePage.clickViewStatusBtn();
			schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
			schedulePage.closeViewStatusContainer();
			SimpleUtils.assertOnFail("shift time is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(2).toLowerCase()), false);
			SimpleUtils.assertOnFail("shift work role is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(4).toLowerCase()), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the functionality of \"Offer Team Members\" for converting assigned shift to open in non-Edit mode")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyFunctionalityOfOfferTMForConvertToOpenShiftsInNonEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			LoginPage loginPage = pageFactory.createConsoleLoginPage();

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
			loginPage.logOut();

			String fileName = "UsersCredentials.json";
			String workRoleOfTM = "";
			fileName = this.enterpriseName+fileName;
			if (this.enterpriseName.contains("cinemark-wkdy")){
				workRoleOfTM = "Team Member Corporate-Theatre";
			} else {//KendraScott2_Enterprise by default
				workRoleOfTM = scheduleWorkRoles.get("MOD");
			}

			HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

			Object[][] adminCredentials = userCredentials.get("InternalAdmin");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
					, String.valueOf(adminCredentials[0][2]));
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//delete unassigned shifts and open shifts.
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("unassigned");
			schedulePage.deleteTMShiftInWeekView("open");
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
			schedulePage.saveSchedule();

			//convert a shift to open shift
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnFilterBtn();
			schedulePage.selectWorkRoleFilterByText(workRoleOfTM, true);
			schedulePage.clickOnProfileIcon();
			schedulePage.clickOnConvertToOpenShift();
			schedulePage.convertToOpenShiftDirectly();
			schedulePage.saveSchedule();

			//verify the open shift in non-edit mode.
			schedulePage.clickOnProfileIconOfOpenShift();
			SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			schedulePage.clickOnOfferTMOption();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.verifyRecommendedTableHasTM();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.clickOnProfileIconOfOpenShift();
			schedulePage.clickViewStatusBtn();
			schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
			schedulePage.closeViewStatusContainer();
			loginPage.logOut();

			//login with TM.
			Object[][] TMCredentials = userCredentials.get("TeamMember");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(TMCredentials[0][0]), String.valueOf(TMCredentials[0][1])
					, String.valueOf(TMCredentials[0][2]));
			schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.navigateToNextWeek();
			schedulePage.clickLinkOnSmartCardByName("View Shifts");
			SimpleUtils.assertOnFail("Didn't get open shift offer!", schedulePage.getShiftsCount()==1, false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Verify the functionality of \"Offer Team Members\" for converting assigned shift to open in Edit mode")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyFunctionalityOfOfferTMForConvertToOpenShiftsInEditModeAsTeamMember(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			LoginPage loginPage = pageFactory.createConsoleLoginPage();

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String firstNameOfTM = profileNewUIPage.getNickNameFromProfile();
			loginPage.logOut();

			String fileName = "UsersCredentials.json";
			String workRoleOfTM = "";
			fileName = this.enterpriseName+fileName;
			if (this.enterpriseName.contains("cinemark-wkdy")){
				workRoleOfTM = "Team Member Corporate-Theatre";
			} else {//KendraScott2_Enterprise by default
				workRoleOfTM = scheduleWorkRoles.get("MOD");
			}

			HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);

			Object[][] adminCredentials = userCredentials.get("InternalAdmin");
			loginToLegionAndVerifyIsLoginDone(String.valueOf(adminCredentials[0][0]), String.valueOf(adminCredentials[0][1])
					, String.valueOf(adminCredentials[0][2]));
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//delete unassigned shifts and open shifts.
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("unassigned");
			schedulePage.deleteTMShiftInWeekView("open");
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM);
			schedulePage.saveSchedule();

			//convert a shift to open shift
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnFilterBtn();
			schedulePage.selectWorkRoleFilterByText(workRoleOfTM, true);
			schedulePage.clickOnProfileIcon();
			schedulePage.clickOnConvertToOpenShift();
			schedulePage.convertToOpenShiftDirectly();
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			WebElement selectedShift = schedulePage.clickOnProfileIconOfOpenShift();
			String selectedShiftId= selectedShift.getAttribute("id");
			int index = schedulePage.getShiftIndexById(selectedShiftId);

			//verify manual open shift in edit mode.
			List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(index);
			schedulePage.clickOnProfileIconOfOpenShift();
			SimpleUtils.assertOnFail("Offer TMs option should be enabled!", schedulePage.isOfferTMOptionEnabled(), false);
			schedulePage.clickOnOfferTMOption();
			String shiftInfoInWindows = schedulePage.getViewStatusShiftsInfo();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.verifyRecommendedTableHasTM();
			schedulePage.switchSearchTMAndRecommendedTMsTab();
			schedulePage.searchTeamMemberByNameNLocation(firstNameOfTM, location);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.clickOnProfileIconOfOpenShift();
			schedulePage.clickViewStatusBtn();
			schedulePage.verifyTMInTheOfferList(firstNameOfTM, "offered");
			schedulePage.closeViewStatusContainer();
			SimpleUtils.assertOnFail("shift time is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(2).toLowerCase()), false);
			SimpleUtils.assertOnFail("shift work role is not consistent!", shiftInfoInWindows.toLowerCase().contains(shiftInfo.get(4).toLowerCase()), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify search bar on schedule page")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySearchBarOnSchedulePageAsInternalAdmin (String browser, String username, String password, String location) throws Exception {
		try{
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();

			//click search button
			schedulePage.clickOnOpenSearchBoxButton();

			//Check the ghost text inside the Search bar
			schedulePage.verifyGhostTextInSearchBox();

			//Get the info of first shift
			List<String> shiftInfo = schedulePage.getTheShiftInfoByIndex(schedulePage.getRandomIndexOfShift());
			//Search shift by TM names: first name and last name
			String firstNameOfTM = shiftInfo.get(0);
			List<WebElement> searchResultOfFirstName = schedulePage.searchShiftOnSchedulePage(firstNameOfTM);
			schedulePage.verifySearchResult(firstNameOfTM, null, null, null, searchResultOfFirstName);

			String lastNameOfTM = shiftInfo.get(5);
			List<WebElement> searchResultOfLastName = schedulePage.searchShiftOnSchedulePage(lastNameOfTM);
			schedulePage.verifySearchResult(null, lastNameOfTM, null, null, searchResultOfLastName);

			//Search shift by work role
			String workRole = shiftInfo.get(4);
			List<WebElement> searchResultOfWorkRole = schedulePage.searchShiftOnSchedulePage(workRole);
			schedulePage.verifySearchResult(null, null, workRole, null, searchResultOfWorkRole);

			//Search shift by job title
			String jobTitle = shiftInfo.get(3);
			List<WebElement> searchResultOfJobTitle = schedulePage.searchShiftOnSchedulePage(jobTitle);
			schedulePage.verifySearchResult(null, null, null, jobTitle, searchResultOfJobTitle);

			//Click X button to close search box
			schedulePage.clickOnCloseSearchBoxButton();

			//Go to edit mode
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

			//click search button
			schedulePage.clickOnOpenSearchBoxButton();
			//Click X button to close search box
			schedulePage.clickOnCloseSearchBoxButton();

			//The search box will not display in day view
			schedulePage.verifySearchBoxNotDisplayInDayView();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}


	@Automated(automated ="Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify assign TM message: If SM wants to schedule a TM from another location and schedule hasn’t been generated or published yet")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
	public void verifyAssignTMMessageWhenScheduleTMToAnotherLocationWithHomeLocationScheduleNotBeenGeneratedOrPublishedAsInternalAdmin (String browser, String username, String password, String location) throws Exception {
		try{
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsPage.gotoControlsPage();
			SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
			controlsNewUIPage.updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule("Yes, anytime");

			dashboardPage.navigateToDashboard();
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			String nyLocation = "NY CENTRAL (Previously New York Central Park)";
			locationSelectorPage.changeLocation(nyLocation);
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Select one team member to view profile
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			String userName = teamPage.selectATeamMemberToViewProfile();
			String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

			//Go to schedule page
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			// Navigate to next week
			schedulePage.navigateToNextWeek();

			// create the schedule if not created
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}

			//Select AUSTIN DOWNTOWN location
			dashboardPage.navigateToDashboard();
			locationSelectorPage.changeLocation("AUSTIN DOWNTOWN");
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
			schedulePage.navigateToNextWeek();

			boolean isWeekGenerated2 = schedulePage.isWeekGenerated();
			if (!isWeekGenerated2) {
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			// Edit the Schedule
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			// Delete all the shifts that are assigned to the team member on Step #1
			schedulePage.deleteTMShiftInWeekView(firstName);

			// Create new shift for this schedule
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.clearAllSelectedDays();
			schedulePage.selectSpecificWorkDay(1);
			schedulePage.selectWorkRole("MOD");
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(firstName);
			SimpleUtils.assertOnFail("TM scheduled status message display failed",
					schedulePage.getTheMessageOfTMScheduledStatus().equalsIgnoreCase("Schedule not published") ||
							schedulePage.getTheMessageOfTMScheduledStatus().equalsIgnoreCase("Schedule Not Created"), false);

			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.verifyDayHasShiftByName(0, firstName);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}

	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify assign TM warning: If SM wants to schedule a TM from another location and schedule hasn’t been published")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMWarningForScheduleTMFromAnotherLocAndScheduleNotPublishedAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsPage.gotoControlsPage();
			SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();

			SimpleUtils.assertOnFail("collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
			controlsNewUIPage.updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule("No, home location must publish schedule first");
			dashboardPage.navigateToDashboard();
			String anotherLocation = "NY CENTRAL";
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.changeLocation(anotherLocation);
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
			// Navigate to a week
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			// create the schedule if not created
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0 || shiftInfo.get(0).equalsIgnoreCase("Open")) {
				shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			String workRoleOfTM1 = shiftInfo.get(4);
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
			schedulePage.saveSchedule();
			dashboardPage.navigateToDashboard();
			locationSelectorPage.changeLocation(location);
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
			// Navigate to a week
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			// create the schedule if not created
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.selectWorkRole(workRoleOfTM1);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(firstNameOfTM1);
			schedulePage.verifyMessageIsExpected("schedule not published");
			schedulePage.verifyWarningModelMessageAssignTMInAnotherLocWhenScheduleNotPublished();
			schedulePage.verifyTMNotSelected();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "SCH-2016: verify shifts display normally after switch to day view")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyShiftsDisplayNormallyInDayViewAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , false);
			// Navigate to a week
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			// create the schedule if not created
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated){
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "09:00AM", "08:00PM");
			schedulePage.verifyDayHasShifts("Sunday");
			schedulePage.verifyDayHasShifts("Monday");
			schedulePage.verifyDayHasShifts("Tuesday");
			schedulePage.verifyDayHasShifts("Wednesday");
			schedulePage.verifyDayHasShifts("Thursday");
			schedulePage.verifyDayHasShifts("Friday");
			schedulePage.verifyDayHasShifts("Saturday");
			schedulePage.clickOnDayView();
			List<WebElement> shiftsInDayView = schedulePage.getAvailableShiftsInDayView();
			SimpleUtils.assertOnFail("Day view shifts don't diaplay successfully!", !shiftsInDayView.isEmpty(), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the functionality for Schedule Copy Restrictions")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheFunctionalityForScheduleCopyRestrictionsAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("yes");
			controlsNewUIPage.setViolationLimit("2");
			controlsNewUIPage.setBudgetOverageLimit("0");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the functionality of Violation limit and Budget overage limit")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyViolationLimitAndBudgetOverageLimitAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("yes");
			controlsNewUIPage.setViolationLimit("2");
			controlsNewUIPage.setBudgetOverageLimit("0");
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			//Make current week 3 or more violation
			String pastWeekInfo1 = schedulePage.getActiveWeekText().substring(10);
			pastWeekInfo1 = schedulePage.convertDateStringFormat(pastWeekInfo1);
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			String workRoleOfTM1 = shiftInfo.get(4);
			List<String> shiftInfo2 = new ArrayList<>();
			while (shiftInfo2.size() == 0) {
				shiftInfo2 = schedulePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM2 = shiftInfo2.get(0);
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.selectWorkRole(workRoleOfTM1);
			schedulePage.clearAllSelectedDays();
			schedulePage.selectDaysByIndex(1, 2, 3);
			schedulePage.moveSliderAtSomePoint("8", 12, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			//schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(firstNameOfTM1);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
			//Make next week 2 or less violation
			schedulePage.navigateToNextWeek();
			String pastWeekInfo2 = schedulePage.getActiveWeekText().substring(10);
			pastWeekInfo2 = schedulePage.convertDateStringFormat(pastWeekInfo2);
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM2);
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.selectWorkRole(workRoleOfTM1);
			schedulePage.clearAllSelectedDays();
			//create 2 overtime violation
			schedulePage.selectDaysByIndex(1, 1, 1);
			schedulePage.selectDaysByIndex(2, 2, 2);
			schedulePage.moveSliderAtSomePoint("8", 12, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			//schedulePage.moveSliderAtSomePoint("8", 0, ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(firstNameOfTM1);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();


			//Make another next week 0 violation
			schedulePage.navigateToNextWeek();
			String pastWeekInfo3 = schedulePage.getActiveWeekText().substring(10);
			pastWeekInfo3 = schedulePage.convertDateStringFormat(pastWeekInfo3);
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.publishActiveSchedule();

			//Go to another next week to check copy restriction.
			schedulePage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.clickCreateScheduleBtn();
			schedulePage.clickNextBtnOnCreateScheduleWindow();
			schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, false);
			schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo2, true);
			schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo3, true);
			schedulePage.clickBackBtnAndExitCreateScheduleWindow();

/*			//Verify budget overage limit
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			//Regenerate current week schedule
			pastWeekInfo1 = schedulePage.getActiveWeekText().substring(10);
			pastWeekInfo1 = schedulePage.convertDateStringFormat(pastWeekInfo1);
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value1 = schedulePage.getStaffingGuidanceHrs();
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.publishActiveSchedule();
			//Make next week has higher schedule hours than budget hours
			schedulePage.navigateToNextWeek();
			pastWeekInfo2 = schedulePage.getActiveWeekText().substring(10);
			pastWeekInfo2 = schedulePage.convertDateStringFormat(pastWeekInfo2);
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value2 = schedulePage.getStaffingGuidanceHrs();
			schedulePage.createScheduleForNonDGFlowNewUI();
			shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
			}
			workRoleOfTM1 = shiftInfo.get(4);
			schedulePage.publishActiveSchedule();


			//Regenerate another next week schedule
			schedulePage.navigateToNextWeek();
			pastWeekInfo3 = schedulePage.getActiveWeekText().substring(10);
			pastWeekInfo3 = schedulePage.convertDateStringFormat(pastWeekInfo3);
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value3 = schedulePage.getStaffingGuidanceHrs();
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.publishActiveSchedule();

			//Go to another next week to check copy restriction.
			schedulePage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value4 = schedulePage.getStaffingGuidanceHrs();
			if (value4>=value3){
				schedulePage.clickOnScheduleConsoleMenuItem();
				SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
						schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
				schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
				SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
						schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

				schedulePage.navigateToNextWeek();
				schedulePage.navigateToNextWeek();
				int i = Math.round((value4-value3)/7) + 1;
				schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
				for (int j=0; j<i+1; j++){
					schedulePage.clickOnDayViewAddNewShiftButton();
					schedulePage.selectWorkRole(workRoleOfTM1);
					schedulePage.clearAllSelectedDays();
					Random r = new Random();
					int index = r.nextInt(6); // 生成[0,6]区间的整数
					schedulePage.selectDaysByIndex(index, index, index);
					schedulePage.moveSliderAtSomePoint("8", 8, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
					schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
					schedulePage.clickOnCreateOrNextBtn();
					schedulePage.switchSearchTMAndRecommendedTMsTab();
					schedulePage.selectTeamMembers();
					schedulePage.clickOnOfferOrAssignBtn();
				}
				schedulePage.saveSchedule();
				schedulePage.publishActiveSchedule();
				schedulePage.navigateToNextWeek();
			}
			schedulePage.clickCreateScheduleBtn();
			schedulePage.clickNextBtnOnCreateScheduleWindow();

			schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo3, false);
			schedulePage.verifyTooltipForCopyScheduleWeek(pastWeekInfo3);
			if (value4>=value2){
				schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo2,  true);
			} else {
				schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo2,  false);
			}
			if (value4>=value2){
				schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, true);
			} else {
				schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, false);
			}
*/		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify turn off the Schedule Copy Restriction")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTurnOffCopyRestrictionAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("no");

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			//Make current week 3 or more violation
			String pastWeekInfo1 = schedulePage.getActiveWeekText().substring(10);
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				schedulePage.createScheduleForNonDGFlowNewUI();
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			String workRoleOfTM1 = shiftInfo.get(4);
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.selectWorkRole(workRoleOfTM1);
			schedulePage.clearAllSelectedDays();
			schedulePage.selectDaysByIndex(1, 2, 3);
			schedulePage.moveSliderAtSomePoint("8", 12, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(firstNameOfTM1);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
			Random r = new Random();
			int index = r.nextInt(10);
			List<String> randomShiftInfoFromCopiedWeek = schedulePage.getTheShiftInfoByIndex(index);

			//Go to next week to check copy restriction.
			schedulePage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.clickCreateScheduleBtn();
			schedulePage.clickNextBtnOnCreateScheduleWindow();
			pastWeekInfo1 = schedulePage.convertDateStringFormat(pastWeekInfo1);
			schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, true);
			schedulePage.selectWhichWeekToCopyFrom(pastWeekInfo1);
			schedulePage.clickOnFinishButtonOnCreateSchedulePage();
			schedulePage.verifyDayHasShifts("Sunday");
			schedulePage.verifyDayHasShifts("Monday");
			schedulePage.verifyDayHasShifts("Tuesday");
			schedulePage.verifyDayHasShifts("Wednesday");
			schedulePage.verifyDayHasShifts("Thursday");
			schedulePage.verifyDayHasShifts("Friday");
			schedulePage.verifyDayHasShifts("Saturday");
			List<String> sameIndexShiftInfoFromThisWeek = schedulePage.getTheShiftInfoByIndex(index);

			if (randomShiftInfoFromCopiedWeek.equals(sameIndexShiftInfoFromThisWeek)){
				SimpleUtils.pass("The 2 week have the same shifts!");
			} else {
				SimpleUtils.fail("Shifts are not the same as copied week!", false);
			}

			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("yes");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify turn off the Schedule Copy Restriction")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTurnOnCopyRestrictionAndCheckCopyResultAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("yes");
			controlsNewUIPage.setViolationLimit("2");
			controlsNewUIPage.setBudgetOverageLimit("0");

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			String pastWeekInfo1 = schedulePage.getActiveWeekText().substring(10);
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = schedulePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(firstNameOfTM1);
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();

			//Go to next week.
			schedulePage.navigateToNextWeek();
			// Ungenerate the schedule if it has generated
			isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.clickCreateScheduleBtn();
			schedulePage.editOperatingHoursWithGivingPrameters("Sunday", "10:00AM", "9:00PM");
			schedulePage.clickNextBtnOnCreateScheduleWindow();
			pastWeekInfo1 = schedulePage.convertDateStringFormat(pastWeekInfo1);
			schedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, true);
			schedulePage.selectWhichWeekToCopyFrom(pastWeekInfo1);
			schedulePage.verifyDifferentOperatingHours(pastWeekInfo1);
			schedulePage.clickOnFinishButtonOnCreateSchedulePage();
			List<String> shiftsInfos = schedulePage.getDayShifts("0");
			String startTime = null;
			String endTime = null;
			if (shiftsInfos!=null){
				startTime = shiftsInfos.get(0).split("\n")[0].split(" - ")[0];
				endTime = shiftsInfos.get(shiftsInfos.size()-1).split("\n")[0].split(" - ")[1];
			}
			//compare startTime to 10:00AM
			String items[] = startTime.substring(0,startTime.length()-2).split(":");
			if (startTime.contains("pm") ){
				SimpleUtils.pass("Shift start time is within time range!");
			} else if (items.length > 0 && SimpleUtils.isNumeric(items[0]) && Integer.parseInt(items[0])>=10){
				SimpleUtils.pass("Shift start time is within time range!");
			} else {
				SimpleUtils.fail("Shift start time is out of time range!", false);
			}
			//compare endTime to 9:00PM
			String items2[] = endTime.substring(0,endTime.length()-2).split(":");
			if (endTime.contains("am") ){
				SimpleUtils.pass("Shift end time is within time range!");
			} else if (items2.length > 0 && SimpleUtils.isNumeric(items2[0]) && Integer.parseInt(items2[0])<=9){
				SimpleUtils.pass("Shift end time is within time range!");
			} else {
				SimpleUtils.fail("Shift end time is out of time range!", false);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify assign TM warning: If SM wants to schedule a TM from another location and schedule hasn’t been generated")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMMessageWhenScheduleTMFromAnotherLocationWhereScheduleNotBeenGeneratedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Set this setting "Can a manager add another locations' employee in schedule before the employee's home location has published the schedule?" to "No, home location must publish schedule first"
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsPage.gotoControlsPage();
			SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
			controlsNewUIPage.updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule("No, home location must publish schedule first");

			// Change the location to "NY CENTRAL"
			dashboardPage.navigateToDashboard();
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			String nyLocation = "NY CENTRAL (Previously New York Central Park)";
			locationSelectorPage.changeLocation(nyLocation);
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Select one team member to view profile
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
			String userName = teamPage.searchAndSelectTeamMemberByName("Active");
			String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;
			String lastName = userName.contains(" ") ? userName.split(" ")[1] : userName;

			// Go to schedule page, schedule tab
			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			// Navigate to a week
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}

			// Select AUSTIN DOWNTOWN location
			dashboardPage.navigateToDashboard();
			locationSelectorPage.changeLocation("AUSTIN DOWNTOWN");
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Go to Schedule page, Schedule tab
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

			// Navigate to a week
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();

			// Create the schedule if it is not created
			boolean isWeekGenerated2 = schedulePage.isWeekGenerated();
			if (!isWeekGenerated2){
				schedulePage.createScheduleForNonDGFlowNewUI();
			}

			// Edit the Schedule
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

			// Create new shift for TM
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.selectWorkRole("MOD");
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();

			// Search TM and verify the message
			schedulePage.searchText(firstName + " " + lastName.substring(0,1));
			if (schedulePage.getTheMessageOfTMScheduledStatus().equalsIgnoreCase("Schedule Not Created"))
				SimpleUtils.pass("TM scheduled status message display correctly");
			else
				SimpleUtils.fail("TM scheduled status message failed to display or displays incorrectly",true);

			// Select the team member and verify the pop-up warning message
			schedulePage.searchTeamMemberByName(firstName + " " + lastName.substring(0,1));
			String expectedMessage = firstName + " cannot be assigned because the schedule has not been published yet at the home location, " + nyLocation;
			schedulePage.verifyAlertMessageIsExpected(expectedMessage);

			// Click on OK button and verify that TM is not selected
			schedulePage.clickOnOkButtonInWarningMode();
			schedulePage.verifyTMNotSelected();
			schedulePage.closeCustomizeNewShiftWindow();
			schedulePage.saveSchedule();

			// Revert the setting
			controlsPage.gotoControlsPage();
			SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
			controlsNewUIPage.updateCanManagerAddAnotherLocationsEmployeeInScheduleBeforeTheEmployeeHomeLocationHasPublishedTheSchedule("Yes, anytime");
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the buffer hours display in schedule")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheBufferHoursDisplayInScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);
			schedulePage.navigateToNextWeek();
			schedulePage.navigateToNextWeek();
			boolean isWeekGenerated = schedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

			schedulePage.clickOnCreateNewShiftButton();
			List<String> allOperatingHrsOnCreateShiftPage = schedulePage.getAllOperatingHrsOnCreateShiftPage();
			SimpleUtils.assertOnFail("The operating hours on create shift page display incorrectly! ",
					allOperatingHrsOnCreateShiftPage.get(0).equalsIgnoreCase("6am")
							&& allOperatingHrsOnCreateShiftPage.get(allOperatingHrsOnCreateShiftPage.size()-1).equalsIgnoreCase("11pm"),false);
			schedulePage.clickOnCloseButtonOnCustomizeShiftPage();

			schedulePage.clickOnProfileIcon();
			schedulePage.clickOnEditShiftTime();
			schedulePage.verifyEditShiftTimePopUpDisplay();
			List<String> startAndEndHrsOnEditShiftPage = schedulePage.getStartAndEndOperatingHrsOnEditShiftPage();
			SimpleUtils.assertOnFail("The operating hours on create shift page display incorrectly! ",
					startAndEndHrsOnEditShiftPage.get(0).equalsIgnoreCase("6")
							&& startAndEndHrsOnEditShiftPage.get(1).equalsIgnoreCase("11"),false);
			schedulePage.clickOnCancelEditShiftTimeButton();
			schedulePage.clickOnCancelButtonOnEditMode();

			schedulePage.clickOnDayView();
			List<String> gridHeaderTimes = new ArrayList();
			gridHeaderTimes = schedulePage.getScheduleDayViewGridTimeDuration();
			SimpleUtils.assertOnFail("The grid header time should start as 6 AM, the actual time is: " +
					gridHeaderTimes.get(0), gridHeaderTimes.get(0).contains("6 AM"), false);
			SimpleUtils.assertOnFail("The grid header time should end with 10 PM, the actual time is: " +
					gridHeaderTimes.get(gridHeaderTimes.size() - 1), gridHeaderTimes.get(gridHeaderTimes.size() - 1).contains("10 PM"), false);
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}
}