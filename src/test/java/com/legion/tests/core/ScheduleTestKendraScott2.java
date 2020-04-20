package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.*;

import com.gargoylesoftware.htmlunit.html.HtmlListing;
import com.legion.pages.*;
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
	@Enterprise(name = "Kendrascott2_Enterprise")
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
	@Enterprise(name = "Kendrascott2_Enterprise")
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
	@Enterprise(name = "Kendrascott2_Enterprise")
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
	@Enterprise(name = "Kendrascott2_Enterprise")
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
		@Enterprise(name = "Kendrascott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality  Compliance smartcard")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyComplianceSmartCardFunctionality(String username, String password, String browser, String location)
				throws Exception {
			SchedulePage schedulePage  = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.goToScheduleNewUI();
			schedulePage.verifyComplianceShiftsSmartCardShowing();
			SimpleUtils.assertOnFail("there is no red flag when there is no compliance" , schedulePage.verifyRedFlagIsVisible(), true);
			schedulePage.verifyComplianceFilterIsSelectedAftClickingViewShift();
			schedulePage.verifyComplianceShiftsShowingInGrid();
			schedulePage.verifyClearFilterFunction();
		}


        @Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "Kendrascott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality  Schedule smartcard")
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
}

