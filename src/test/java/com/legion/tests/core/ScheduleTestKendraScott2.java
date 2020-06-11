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
		//Weather week smartcard is displayed correctly-Current week[Sun-Sat] Next week will show which days are past day for current week: Eg: Sun for current week
		schedulePage.weatherWeekSmartCardIsDisplayedForAWeek();
		//If selecting one week then data is getting updating on Schedule page according to corresponding week
		schedulePage.scheduleUpdateAccordingToSelectWeek();
		//Print button is clickable
		schedulePage.printButtonIsClickable();
		//In week View > Clicking on Print button  it should give option to print in both Landscape or Portrait mode Both should work.
		schedulePage.landscapePortraitModeShowWellInWeekView();
		//In Week view should be able to change the mode between Landscape and Portrait
		schedulePage.landscapeModeWorkWellInWeekView();
		schedulePage.portraitModeWorkWellInWeekView();
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
	@Enterprise(name = "DGStaging_Enterprise")
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
			schedulePage.generateOrUpdateAndGenerateSchedule();
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
			schedulePage.generateOrUpdateAndGenerateSchedule();
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
	@Enterprise(name = "DGStaging_Enterprise")
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
		//Overtime hours = shift total hours  - meal time(defined in controls )-overtime pay hours in controls
		//for example OT is one hour
		int dragIncreasePoint = (int) (((1+dailyOvertimePay)*everyHoursOfWork/(everyHoursOfWork-mealBreakTime))*2)-Integer.valueOf(propertyCustomizeMap.get("INCREASE_END_TIME"));
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		//Current week and day is selected by default
		schedulePage.currentWeekIsGettingOpenByDefault();
		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			schedulePage.generateOrUpdateAndGenerateSchedule();
		}
		//click on Edit button to add new shift
		schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		schedulePage.clickOnDayView();
		schedulePage.navigateToNextDayIfStoreClosedForActiveDay();
		SimpleUtils.assertOnFail("User can add new shift for past week", (schedulePage.isAddNewDayViewShiftButtonLoaded()) , true);

		//"while selecting Open shift:Auto,create button is enabled one open shift will created and system will offer shift automatically
		schedulePage.clickNewDayViewShiftButtonLoaded();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();

//		"While selecting Open shift:Manual,Next button is enabled,After Click on Next Select Tms window is enabled and after selecting N number of TMs, offer will send to them"
		schedulePage.clickNewDayViewShiftButtonLoaded();
		schedulePage.customizeNewShiftPage();
		String defaultTimeDuration = schedulePage.getTimeDurationWhenCreateNewShift();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		String defaultTimeDurationAftDrag = schedulePage.getTimeDurationWhenCreateNewShift();
		if (!defaultTimeDurationAftDrag.equals(defaultTimeDuration)) {
			SimpleUtils.pass("A shift time and duration can be changed by dragging it");
		}else
			SimpleUtils.report("there is no change for time duration");
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
		schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
		schedulePage.clickOnCreateOrNextBtn();
		schedulePage.customizeNewShiftPage();
		schedulePage.verifySelectTeamMembersOption();
		schedulePage.clickOnOfferOrAssignBtn();

//		While selecting Assign TM,Next button is enabled, After Click on Next, Select Tm window is enabled and only one TM can be selected, and shift will assign to him/her
		schedulePage.clickNewDayViewShiftButtonLoaded();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleNewUITest.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleNewUITest.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleNewUITest.shiftSliderDroppable.StartPoint.getValue());
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
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
		schedulePage.clickNewDayViewShiftButtonLoaded();
		schedulePage.customizeNewShiftPage();
		schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), dragIncreasePoint, ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
		schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
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
		int shiftHoursEachDay;
		if ((workWeekOverTime % dayCountInOneWeek) == 0) {
			shiftHoursEachDay = workWeekOverTime / dayCountInOneWeek;
		} else {
			shiftHoursEachDay = (workWeekOverTime / dayCountInOneWeek) + 1;
		}
//		float totalShiftHoursInWeekForTM = (shiftHoursEachDay-0.5f)*dayCountInOneWeek;
//		int overtimeHoursInWeekForTM = totalShiftHoursInWeekForTM-workWeekOverTime;
		String teamMemberName = propertySearchTeamMember.get("TeamMember");
		float shiftHoursInWeekForTM = schedulePage.getShiftHoursByTMInWeekView(teamMemberName);
		if (shiftHoursInWeekForTM == 0) {
			schedulePage.clickNewDayViewShiftButtonLoaded();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), (2+shiftHoursEachDay*2-7), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
//			schedulePage.moveSliderAtCertainPoint2( String.valueOf(2+(shiftHoursEachDay+0.5f)*2), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
			schedulePage.selectSpecificWorkDay(dayCountInOneWeek);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.selectSpecificTMWhileCreateNewShift(teamMemberName);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			float shiftHoursInWeekForTMAftAddNewShift = schedulePage.getShiftHoursByTMInWeekView(teamMemberName);

			if ((shiftHoursInWeekForTMAftAddNewShift -shiftHoursInWeekForTM)>workWeekOverTime) {
				schedulePage.verifyWeeklyOverTimeAndFlag(teamMemberName);
			}
		}else{
			schedulePage.deleteTMShiftInWeekView(teamMemberName);
			schedulePage.clickNewDayViewShiftButtonLoaded();
			schedulePage.customizeNewShiftPage();
			schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), (2+shiftHoursEachDay*2-7), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
//			schedulePage.moveSliderAtCertainPoint2( String.valueOf(2+shiftHoursEachDay*2), ScheduleNewUITest.shiftSliderDroppable.EndPoint.getValue());
			schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
			schedulePage.selectSpecificWorkDay(dayCountInOneWeek);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.AssignTeamMemberShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.selectSpecificTMWhileCreateNewShift(teamMemberName);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			float shiftHoursInWeekForTMAftAddNewShift = schedulePage.getShiftHoursByTMInWeekView(teamMemberName);

			if (shiftHoursInWeekForTMAftAddNewShift > workWeekOverTime) {
				schedulePage.verifyWeeklyOverTimeAndFlag(teamMemberName);
			}
		}


	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "DGStaging_Enterprise")
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

}

