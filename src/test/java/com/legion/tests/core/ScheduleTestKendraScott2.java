package com.legion.tests.core;

import java.awt.print.PrinterGraphics;
import java.lang.reflect.Method;
import java.util.*;

import com.gargoylesoftware.htmlunit.html.HtmlListing;
import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.utils.JsonUtil;
import com.legion.utils.MyThreadLocal;
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


public class ScheduleTestKendraScott2 extends TestBase {

	private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
	private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
	private static HashMap<String, String> schedulePolicyData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
	private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");

	@Override
	@BeforeMethod()
	public void firstTest(Method testMethod, Object[] params) throws Exception {
		this.createDriver((String) params[0], "69", "Window");
		visitPage(testMethod);
		loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);

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
	@TestName(description = "Verify the Schedule functionality  Legion")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleLegionFunctionality(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.goToTodayForNewUI();
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
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
		schedulePage.currentWeekIsGettingOpenByDefault();
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
		@TestName(description = "Verify the Schedule functionality  Compliance Smartcard")
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
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality  Schedule Smartcard")
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
			if (overviewData.get("guidanceHours").equals(scheduleSmartCardHoursWages.get("budgetedHours"))
					& overviewData.get("scheduledHours").equals(scheduleSmartCardHoursWages.get("scheduledHours"))
					& overviewData.get("otherHours").equals(scheduleSmartCardHoursWages.get("otherHours"))) {
				SimpleUtils.pass("Schedule/Budgeted smartcard-is showing the values in Hours and wages, it is displaying the same data as overview page have for the current week .");
			}else {
				SimpleUtils.fail("Scheduled Hours and Overview Schedule Hours not same",true);
			}
		}

	
	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Week View")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalityWeekView(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
		//Edit button should be clickable
		//While click on edit button,if Schedule is finalized then prompt is available and Prompt is in proper alignment and correct msg info.
		//Edit anyway and cancel button is clickable
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();;
		//In week view, Group by All filter have 3 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
		schedulePage.validateGroupBySelectorSchedulePage();
		//click on the context of any TM, 1. View profile 2. Change shift role  3.Assign TM 4.  Convert to open shift is enabled for current and future week day 5.Edit meal break time 6. Delete shift
		schedulePage.selectNextWeekSchedule();
		boolean isActiveWeekGenerated2 = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated2){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
			//"After Click on view profile,then particular TM profile is displayed :1. Personal details 2. Work Preferences 3. Availability
			SimpleUtils.assertOnFail(" context of any TM display doesn't show well" , schedulePage.verifyContextOfTMDisplay(), true);
			schedulePage.clickOnViewProfile();
			schedulePage.verifyPersonalDetailsDisplayed();
			schedulePage.verifyWorkPreferenceDisplayed();
			schedulePage.verifyAvailabilityDisplayed();
			schedulePage.closeViewProfileContainer();
			//"After Click on the Change shift role, one prompt is enabled:various work role any one of them can be selected"
			schedulePage.clickOnProfileIcon();
//			schedulePage.clickOnChangeRole();
//			schedulePage.verifyChangeRoleFunctionality();
			//After Click on Assign TM-Select TMs window is opened,Recommended and search TM tab is enabled
			schedulePage.clickonAssignTM();
			schedulePage.verifyRecommendedAndSearchTMEnabled();
			//Search and select any TM,Click on the assign: new Tm is updated on the schedule table
			schedulePage.verifySelectTeamMembersOption();
			schedulePage.clickOnOfferOrAssignBtn();
			//Click on the Convert to open shift, checkbox is available to offer the shift to any specific TM[optional] Cancel /yes
			//if checkbox is unselected then, shift is convert to open
		    schedulePage.clickOnProfileIcon();
		    schedulePage.clickOnConvertToOpenShift();
			if (schedulePage.verifyConvertToOpenPopUpDisplay()) {
				schedulePage.convertToOpenShiftDirectly();
			}
//			schedulePage.convertToOpenShift();
			//After Click on Edit Meal Break Time, the Edit Meal break windows is pop up which include: 1.profile info 2.add meal break button 3.Specify meal break time period 4 cancel ,continue button
//			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.verifyMealBreakTimeDisplayAndFunctionality();
			//if checkbox is selected then, shift is offered to selected TM
//			schedulePage.verifyConvertToOpenShiftBySelectedSpecificTM();//existing RoleViolation need to enhancement
			//verify delete shift
			schedulePage.verifyDeleteShift();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality  Day View")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalityDayView(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		//go to controls page to get overtime pay data
		ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
		controlsNewUIPage.clickOnControlsConsoleMenu();
		boolean isControlsComplianceCardSection = controlsNewUIPage.isControlsComplianceCard();
		SimpleUtils.assertOnFail("Controls Page: Compliance Section not Loaded.", isControlsComplianceCardSection, true);
		controlsNewUIPage.clickOnControlsComplianceSection();
		SimpleUtils.assertOnFail("Controls:Compliance  Page not loaded Successfully!",controlsNewUIPage.isCompliancePageLoaded() , false);
		HashMap<String, Integer> overtimePayData = controlsNewUIPage.getOvertimePayDataFromControls();
		int dailyOvertimePay = overtimePayData.get("overtimeDailyText");
		HashMap<String, Integer> mealBreakTimeData = controlsNewUIPage.getMealBreakDataFromControls();
		float mealBreakTime = mealBreakTimeData.get("unPaiedMins")/ 60f;
		int everyHoursOfWork = mealBreakTimeData.get("everyXHoursOfWork");
		//Overtime hours = shift total hours  - 8h
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		//Current week and day is selected by default
		schedulePage.currentWeekIsGettingOpenByDefault();
		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			schedulePage.createScheduleForNonDGFlowNewUI();
		}
		//click on Edit button to add new shift
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.clickOnDayView();
		schedulePage.navigateToNextDayIfStoreClosedForActiveDay();
		schedulePage.deleteAllShiftsInDayView();
		schedulePage.saveSchedule();
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		SimpleUtils.assertOnFail("User can add new shift for past week", (schedulePage.isAddNewDayViewShiftButtonLoaded()) , true);

		//"while selecting Open shift:Auto,create button is enabled one open shift will created and system will offer shift automatically
		schedulePage.clickOnDayViewAddNewShiftButton();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
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
		schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
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
		schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
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
		schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.verifySelectTeamMembersOption();
		schedulePage.clickOnOfferOrAssignBtn();
		int otFlagCountAftAddNewShift = schedulePage.getOTShiftCount();
		if (otFlagCountAftAddNewShift > otFlagCount) {
			SimpleUtils.pass("OT shit add successfully");
		}else
			SimpleUtils.fail("add OT new shift failed" , true);


		//If a TM has more than 40 working hours in a week (As defined in Controls) then Week OT hours should show (Week OT should be enabled in Controls)
		int workWeekOverTime = Integer.valueOf(schedulePolicyData.get("single_workweek_overtime"));
		int dayCountInOneWeek = Integer.valueOf(propertyCustomizeMap.get("WORKDAY_COUNT"));
		String teamMemberName = "Donald";
		schedulePage.clickOnWeekView();
		float shiftHoursInWeekForTM = schedulePage.getShiftHoursByTMInWeekView(teamMemberName);
		schedulePage.clickOnDayView();
		if (shiftHoursInWeekForTM == 0) {
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.selectSpecificWorkDay(dayCountInOneWeek);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(teamMemberName);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.clickOnWeekView();
			float shiftHoursInWeekForTMAftAddNewShift = schedulePage.getShiftHoursByTMInWeekView(teamMemberName);

			if ((shiftHoursInWeekForTMAftAddNewShift -shiftHoursInWeekForTM)>workWeekOverTime) {
				schedulePage.verifyWeeklyOverTimeAndFlag(teamMemberName);
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(teamMemberName);
			schedulePage.saveSchedule();
		}else{
			schedulePage.deleteTMShiftInWeekView(teamMemberName);
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			//schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			schedulePage.selectSpecificWorkDay(dayCountInOneWeek);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(teamMemberName);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.clickOnWeekView();
			float shiftHoursInWeekForTMAftAddNewShift = schedulePage.getShiftHoursByTMInWeekView(teamMemberName);

			if (shiftHoursInWeekForTMAftAddNewShift > workWeekOverTime) {
				schedulePage.verifyWeeklyOverTimeAndFlag(teamMemberName);
			}
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(teamMemberName);
			schedulePage.saveSchedule();
		}


	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality  Job Title Filter Functionality")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleInWeekView(String username, String password, String browser, String location)
			throws Exception {

		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage = dashboardPage.goToTodayForNewUI();
		SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , true);

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
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality  Job Title Filter Functionality")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleInDayView(String username, String password, String browser, String location)
			throws Exception {

		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage = dashboardPage.goToTodayForNewUI();
		SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()) , true);

		/*
		 *  Navigate to Schedule day view
		 */
		boolean isWeekView = false;
		schedulePage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		schedulePage.filterScheduleByJobTitle(isWeekView);
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.filterScheduleByJobTitle(isWeekView);
		schedulePage.clickOnCancelButtonOnEditMode();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality  Job Title Filter Functionality")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleFilterCombinationInWeekView(String username, String password, String browser, String location)
			throws Exception {

		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		/*
		 *  Navigate to Schedule week view
		 */
		boolean isWeekView = true;
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
	@TestName(description = "Verification of My Schedule Page when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verificationOfMySchedulePageAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of To and Fro navigation of week picker when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verificationOfToAndFroNavigationOfWeekPickerAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
		String theEarliestAndLatestTimeInScheduleTable = schedulePage.getTheEarliestAndLatestTimeInScheduleTable();
		SimpleUtils.report("theEarliestAndLatestOperationHoursInScheduleTable is " + theEarliestAndLatestTimeInScheduleTable);
		schedulePage.compareOperationHoursBetweenAdminAndTM(theEarliestAndLatestTimeInSummaryView, theEarliestAndLatestTimeInScheduleTable);

		//T1838613 Validate that hours and date is visible of shifts.
		schedulePage.validateThatHoursAndDateIsVisibleOfShifts();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Profile picture functionality when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyProfilePictureFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();

		//T1838614 Validate the clickability of Profile picture in a shift.
		schedulePage.validateProfilePictureInAShiftClickable();

		//T1838615 Validate the data of profile popup in a shift.
		//schedulePage.validateTheDataOfProfilePopupInAShift();
		// todo: <Here is an incident LEG-10929>
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Info icon functionality when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyInfoIconFunctionalityAsTeamMember(String browser, String username, String password, String location) throws Exception {
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();

		//T1838616 Validate the availability of info icon.
		schedulePage.validateTheAvailabilityOfInfoIcon();

		//T1838617 Validate the clickability of info icon.
		schedulePage.validateInfoIconClickable();

		//T1838618 Validate the availability of Meal break as per the control settings.
		//schedulePage.validateMealBreakPerControlSettings();
		// todo: <Meal break is a postpone feature>
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of Open Shift Schedule Smart Card when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyOpenShiftScheduleSmartCardAsTeamMember(String browser, String username, String password, String location) throws Exception {
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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content of copy schedule for non dg flow")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheContentOfCopyScheduleForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

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
		schedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose);

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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content of closed week day schedule for non dg flow ")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheContentOfClosedWeekDayScheduleForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

		boolean isWeekGenerated = schedulePage.isWeekGenerated();
		if (isWeekGenerated) {
			schedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		List<String> weekDaysToClose = new ArrayList<>(Arrays.asList("Sunday", "Tuesday"));
		schedulePage.createScheduleForNonDGByWeekInfo("SUGGESTED", weekDaysToClose);

		// Verify that the closed week day should not have any shifts
		schedulePage.verifyNoShiftsForSpecificWeekDay(weekDaysToClose);
		// Go to day view, check the closed week day should show "Store is Closed"
		schedulePage.clickOnDayView();
		schedulePage.verifyStoreIsClosedForSpecificWeekDay(weekDaysToClose);
		// Toggle Summary view, verify that the specific week days shows Closed
		schedulePage.toggleSummaryView();
		schedulePage.verifyClosedDaysInToggleSummaryView(weekDaysToClose);
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of Open Shift Schedule Smart Card when login through TM View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentOfBudgetHoursForNonDGFlowAsTeamMember(String browser, String username, String password, String location) throws Exception {
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.goToConsoleScheduleAndScheduleSubMenu();
		String weekInfo = "";
		weekInfo = schedulePage.getWeekInfoBeforeCreateSchedule();
		boolean isWeekGenerated = schedulePage.isWeekGenerated();
		if (isWeekGenerated){
			schedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		HashMap<String,String> budgetNScheduledFromGraph;
		budgetNScheduledFromGraph = schedulePage.verifyNGetBudgetNScheduleWhileCreateScheduleForNonDGFlowNewUI(weekInfo, location);
		String budgetFromGraph = "";
		String scheduledFromGraph = "";
		budgetFromGraph = budgetNScheduledFromGraph.get("Budget");
		scheduledFromGraph = budgetNScheduledFromGraph.get("Scheduled");
		if (!schedulePage.isSummaryViewLoaded())
			schedulePage.toggleSummaryView();
		List<String> budgetsOnSTAFF = schedulePage.getBudgetedHoursOnSTAFF();
		String budgetOnWeeklyBudget = schedulePage.getBudgetOnWeeklyBudget();
		HashMap<String, String> budgetNScheduledHoursFromSmartCard = schedulePage.getBudgetNScheduledHoursFromSmartCard();
		String budgetFromSmartCard = "";
		String scheduledFromSmartCard = "";
		budgetFromSmartCard = budgetNScheduledHoursFromSmartCard.get("Budget");
        scheduledFromSmartCard = budgetNScheduledHoursFromSmartCard.get("Scheduled");
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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content after changing Operating Hours for non dg flow ")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentAfterChangingOperatingHrsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content after changing Operating Hours for non dg flow - next day")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentAfterChangingOperatingHrsNextDayAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the budget hour in DM view schedule page for non dg flow")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyBudgetHourInDMViewSchedulePageForNonDGFlowAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

		boolean isWeekGenerated = schedulePage.isWeekGenerated();
		if (isWeekGenerated) {
			schedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		List<String> weekDaysToClose = new ArrayList<>();
		float budgetHoursInSchedule = schedulePage.createScheduleForNonDGByWeekInfo("SUGGESTED", weekDaysToClose);

		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		locationSelectorPage.changeDistrictDirect("Demo District");

		ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
		float budgetedHoursInDMViewSchedule = scheduleDMViewPage.getBudgetedHourOfScheduleInDMViewByLocation(location);
		if (budgetHoursInSchedule != 0 && budgetHoursInSchedule == budgetedHoursInDMViewSchedule) {
			SimpleUtils.pass("Verified the budget hour in DM view schedule page is consistent with the value saved in create schedule page!");
		} else {
			SimpleUtils.fail("Verified the budget hour in DM view schedule page is consistent with the value saved in create schedule page! The budget hour in DM view schedule page is " +
					budgetHoursInSchedule + ". The value saved in create schedule page is " + budgetedHoursInDMViewSchedule, false);
		}
	}


	@Automated(automated = "Automated")
	@Owner(owner = "haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify smart card for schedule not publish(include past weeks)")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySmartCardForScheduleNotPublishAsStoreManager(String browser, String username, String password, String location) throws Exception {
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
		//make edits
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

	}

	// Add the new test cases for "Schedule Not Published"
	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "verify smart card for compliance violation")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyComplianceViolationWhenScheduleIsNotPublishedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

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
		schedulePage.dragOneShiftToMakeItOverTime();
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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "verify smart card for compliance violation -republish")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyComplianceViolationWhenScheduleHasPublishedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), false);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), false);

		schedulePage.navigateToNextWeek();
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
		schedulePage.dragOneShiftToMakeItOverTime();
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
	}
}

