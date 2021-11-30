package com.legion.tests.core;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import com.legion.api.toggle.ToggleAPI;
import com.legion.api.toggle.Toggles;
import com.legion.pages.*;
import com.legion.pages.core.ConsoleScheduleNewUIPage;
import com.legion.pages.core.OpsPortalConfigurationPage;
import com.legion.utils.JsonUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;

import static com.legion.utils.MyThreadLocal.*;


public class ScheduleTestKendraScott2 extends TestBase {

	private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
	private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
	private static HashMap<String, String> schedulePolicyData = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SchedulingPoliciesData.json");
	private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
	private static HashMap<String, Object[][]> kendraScott2TeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("KendraScott2TeamMembers.json");
	private static HashMap<String, Object[][]> cinemarkWkdyTeamMembers = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson("CinemarkWkdyTeamMembers.json");
	private static String opWorkRole = scheduleWorkRoles.get("RETAIL_ASSOCIATE");
	private static String controlWorkRole = scheduleWorkRoles.get("RETAIL_RENTAL_MGMT");


	public enum weekCount {
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5);
		private final int value;

		weekCount(final int newValue) {
			value = newValue;
		}

		public int getValue() {
			return value;
		}
	}

	public enum filtersIndex {
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5);
		private final int value;

		filtersIndex(final int newValue) {
			value = newValue;
		}

		public int getValue() {
			return value;
		}
	}


	public enum dayCount{
		Seven(7);
		private final int value;
		dayCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	public enum schedulePlanningWindow{
		Eight(8);
		private final int value;
		schedulePlanningWindow(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	public enum sliderShiftCount {
		SliderShiftStartCount(2),
		SliderShiftEndTimeCount(10),
		SliderShiftEndTimeCount2(14),
		SliderShiftEndTimeCount3(40);
		private final int value;
		sliderShiftCount(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
	}

	public enum staffingOption{
		OpenShift("Auto"),
		ManualShift("Manual"),
		AssignTeamMemberShift("Assign Team Member");
		private final String value;
		staffingOption(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
	}

	  public enum overviewWeeksStatus{
		  NotAvailable("Not Available"),
		  Draft("Draft"),
		  Guidance("Guidance"),
		  Published("Published"),
		  Finalized("Finalized");

		  private final String value;
		  overviewWeeksStatus(final String newValue) {
            value = newValue;
          }
          public String getValue() { return value; }
		}


	public enum SchedulePageSubTabText {
		Overview("OVERVIEW"),
		Forecast("FORECAST"),
		ProjectedSales("PROJECTED SALES"),
		StaffingGuidance("STAFFING GUIDANCE"),
		Schedule("SCHEDULE"),
		MySchedule("MY SCHEDULE"),
		ProjectedTraffic("PROJECTED TRAFFIC");
		private final String value;

		SchedulePageSubTabText(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	public enum weekViewType {
		Next("Next"),
		Previous("Previous");
		private final String value;

		weekViewType(final String newValue) {
			value = newValue;
		}

		public String getValue() {
			return value;
		}
	}

	  public enum shiftSliderDroppable{
		  StartPoint("Start"),
		  EndPoint("End");
			private final String value;
			shiftSliderDroppable(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}

	  public enum scheduleHoursAndWagesData{
		  scheduledHours("scheduledHours"),
		  budgetedHours("budgetedHours"),
		  otherHours("otherHours"),
		  wagesBudgetedCount("wagesBudgetedCount"),
		  wagesScheduledCount("wagesScheduledCount");
			private final String value;
			scheduleHoursAndWagesData(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}

	  public enum scheduleGroupByFilterOptions{
		  groupbyAll("Group by All"),
		  groupbyWorkRole("Group by Work Role"),
		  groupbyTM("Group by TM"),
		  groupbyJobTitle("Group by Job Title"),
		  groupbyLocation("Group by Location"),
		  groupbyDayParts("Group by Day Parts");
			private final String value;
			scheduleGroupByFilterOptions(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}

	public enum dayWeekOrPayPeriodViewType{
		  Next("Next"),
		  Previous("Previous");
			private final String value;
			dayWeekOrPayPeriodViewType(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
	}

	public enum schedulingPoliciesShiftIntervalTime{
		  FifteenMinutes("15 minutes"),
		  ThirtyMinutes("30 minutes");
			private final String value;
			schedulingPoliciesShiftIntervalTime(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
	}

	public enum usersAndRolesSubTabs{
		AllUsers("Users"),
		AccessByJobTitles("Access Roles"),
		Badges("Badges");
		private final String value;
		usersAndRolesSubTabs(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
	}

	public enum tasksAndWorkRolesSubTab{
		WorkRoles("Work Roles"),
		LaborCalculator("Labor Calculator");
		private final String value;
		tasksAndWorkRolesSubTab(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
	}

	public enum dayWeekOrPayPeriodCount{
		Zero(0),
		One(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5);
		private final int value;
		dayWeekOrPayPeriodCount(final int newValue) {
          value = newValue;
      }
      public int getValue() { return value; }
	}

	public enum schedulingPolicyGroupsTabs{
		  FullTimeSalariedExempt("Full Time Salaried Exempt"),
		  FullTimeSalariedNonExempt("Full Time Salaried Non Exempt"),
		  FullTimeHourlyNonExempt("Full Time Hourly Non Exempt"),
          PartTimeHourlyNonExempt("Part Time Hourly Non Exempt");
			private final String value;
			schedulingPolicyGroupsTabs(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
	}

    public enum DayOfWeek {
        Mon,
        Tue,
        Wed,
        Thu,
        Fri,
        Sat,
        Sun;
    }

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
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		dashboardPage.goToTodayForNewUI();
		scheduleCommonPage.navigateToNextWeek();
		boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		scheduleMainPage.legionButtonIsClickableAndHasNoEditButton();
		scheduleMainPage.legionIsDisplayingTheSchedul();
	}


	@Automated(automated = "Auto")
	@Owner(owner = "Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Schedule")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalitySchedule(String username, String password, String browser, String location)
			throws Exception {
		boolean isWeekView = false;
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		AnalyzePage analyzePage = pageFactory.createAnalyzePage();
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		scheduleCommonPage.goToScheduleNewUI();
		//Current week is getting open by default
		scheduleCommonPage.currentWeekIsGettingOpenByDefault(location);
		boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//Weather week smartcard is displayed correctly-Current week[Sun-Sat] Next week will show which days are past day for current week: Eg: Sun for current week
		smartCardPage.weatherWeekSmartCardIsDisplayedForAWeek();
		//If selecting one week then data is getting updating on Schedule page according to corresponding week
		scheduleShiftTablePage.scheduleUpdateAccordingToSelectWeek();
		//Print button is clickable
		String handle = getDriver().getWindowHandle();
		scheduleMainPage.printButtonIsClickable();
		getDriver().switchTo().window(handle);

		//In week View > Clicking on Print button  it should give option to print in both Landscape or Portrait mode Both should work.
		//schedulePage.landscapePortraitModeShowWellInWeekView();
		//In Week view should be able to change the mode between Landscape and Portrait
		//schedulePage.landscapeModeWorkWellInWeekView();
		//schedulePage.portraitModeWorkWellInWeekView();
		//Day-week picker section navigating correctly
		//Todo:Run failed by LEG-10221
		scheduleCommonPage.dayWeekPickerSectionNavigatingCorrectly();
		//In Day view  Clicking on Print button it should give option to print in Landscape mode only
//		schedulePage.landscapeModeOnlyInDayView();
		scheduleCommonPage.clickOnWeekView();
		//Filter is working correctly if we select any one or more filters then schedule table data is updating according to that
		//Todo:Run failed by  LEG-10210
		scheduleMainPage.filterScheduleByWorkRoleAndShiftType(isWeekView);
		//to do button is clickable
		//schedulePage.todoButtonIsClickable();
		//Todo:Run failed by Swap Cover Requested function error
		//schedulePage.verifyShiftSwapCoverRequestedIsDisplayInTo();
		//Analyze button is clickable:Staffing Guidance Schedule History-Scrollbar is working correctly version x details and close button is working
		analyzePage.verifyAnalyzeBtnFunctionAndScheduleHistoryScroll();


	}

		@Automated(automated = "Automated")
		@Owner(owner = "Estelle")
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "Verify the Schedule functionality >  Compliance smartcard")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass = CredentialDataProviderSource.class)
		public void verifyComplianceSmartCardFunctionality(String username, String password, String browser, String location)
				throws Exception {
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.goToScheduleNewUI();
			if(smartCardPage.verifyComplianceShiftsSmartCardShowing() && smartCardPage.verifyRedFlagIsVisible()){
				smartCardPage.verifyComplianceFilterIsSelectedAftClickingViewShift();
				smartCardPage.verifyComplianceShiftsShowingInGrid();
				smartCardPage.verifyClearFilterFunction();
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
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.goToScheduleNewUI();
			boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
			if(!isActiveWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleSmartCardHoursWages = smartCardPage.getScheduleBudgetedHoursInScheduleSmartCard();
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
	@Owner(owner = "Mary/Estelle")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Schedule functionality - Week View - Context Menu")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalityWeekViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		scheduleCommonPage.navigateToNextWeek();

		boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
		if(isActiveWeekGenerated){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		Thread.sleep(5000);
		createSchedulePage.createScheduleForNonDGFlowNewUI();
		//In week view, Group by All filter have 4 filters:1.Group by all  2. Group by work role  3. Group by TM 4.Group by job title
		scheduleMainPage.validateGroupBySelectorSchedulePage(false);
		//Selecting any of them, check the schedule table
		scheduleMainPage.validateScheduleTableWhenSelectAnyOfGroupByOptions(false);

		//Edit button should be clickable
		//While click on edit button,if Schedule is finalized then prompt is available and Prompt is in proper alignment and correct msg info.
		//Edit anyway and cancel button is clickable
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

		//click on the context of any TM, 1. View profile 2. Change shift role  3.Assign TM 4.  Convert to open shift is enabled for current and future week day 5.Edit meal break time 6. Delete shift
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		boolean isActiveWeekGenerated2 = createSchedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated2){
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("assigned");
		String workRole = shiftOperatePage.getRandomWorkRole();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();

		createSchedulePage.publishActiveSchedule();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		SimpleUtils.assertOnFail(" context of any TM display doesn't show well" , shiftOperatePage.verifyContextOfTMDisplay(), false);

		//"After Click on view profile,then particular TM profile is displayed :1. Personal details 2. Work Preferences 3. Availability
		shiftOperatePage.clickOnViewProfile();
		shiftOperatePage.verifyPersonalDetailsDisplayed();
		shiftOperatePage.verifyWorkPreferenceDisplayed();
		shiftOperatePage.verifyAvailabilityDisplayed();
		shiftOperatePage.closeViewProfileContainer();

		//"After Click on the Change shift role, one prompt is enabled:various work role any one of them can be selected"
		shiftOperatePage.clickOnProfileIcon();
		shiftOperatePage.clickOnChangeRole();
		shiftOperatePage.verifyChangeRoleFunctionality();
		//check the work role by click Apply button
		shiftOperatePage.changeWorkRoleInPrompt(true);
		//check the work role by click Cancel button
		shiftOperatePage.changeWorkRoleInPrompt(false);

		//After Click on Assign TM-Select TMs window is opened,Recommended and search TM tab is enabled
		shiftOperatePage.clickOnProfileIcon();
		shiftOperatePage.clickonAssignTM();
		shiftOperatePage.verifyRecommendedAndSearchTMEnabled();

		//Search and select any TM,Click on the assign: new Tm is updated on the schedule table
		//Select new TM from Search Team Member tab
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectWorkRoleFilterByText(workRole, true);
		WebElement selectedShift = null;
		selectedShift = shiftOperatePage.clickOnProfileIcon();
		String selectedShiftId= selectedShift.getAttribute("id").toString();
		shiftOperatePage.clickonAssignTM();
		String firstNameOfSelectedTM = newShiftPage.selectTeamMembers().split(" ")[0];
		newShiftPage.clickOnOfferOrAssignBtn();
		SimpleUtils.assertOnFail(" New selected TM doesn't display in scheduled table" ,
				firstNameOfSelectedTM.equals(scheduleShiftTablePage.getShiftById(selectedShiftId).findElement(By.className("week-schedule-worker-name")).getText().split(" ")[0].trim()), false);
		//Select new TM from Recommended TMs tab
		String firstNameOfSelectedTM2 = "";
		String selectedShiftId2 = "";
		int i = 0;
		while (firstNameOfSelectedTM2.equals("") && i<10) {
			selectedShift = shiftOperatePage.clickOnProfileIcon();
			selectedShiftId2  = selectedShift.getAttribute("id").toString();
			shiftOperatePage.clickonAssignTM();
			shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
			firstNameOfSelectedTM2 = newShiftPage.selectTeamMembers().split(" ")[0];
			i++;
		}
		if (firstNameOfSelectedTM2.equals("")) {
			SimpleUtils.fail("Cannot found TMs in recommended TMs tab! ", false);
		}
		newShiftPage.clickOnOfferOrAssignBtn();
		SimpleUtils.assertOnFail(" New selected TM doesn't display in scheduled table" ,
				firstNameOfSelectedTM2.equals(scheduleShiftTablePage.getShiftById(selectedShiftId2).findElement(By.className("week-schedule-worker-name")).getText().split(" ")[0].trim()), false);
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		scheduleMainPage.clickOnFilterBtn();

		//Click on the Convert to open shift, checkbox is available to offer the shift to any specific TM[optional] Cancel /yes
		//if checkbox is unselected then, shift is convert to open
		selectedShift = shiftOperatePage.clickOnProfileIcon();
		String tmFirstName = selectedShift.findElement(By.className("week-schedule-worker-name")).getText().split(" ")[0];
		shiftOperatePage.clickOnConvertToOpenShift();
		if (shiftOperatePage.verifyConvertToOpenPopUpDisplay(tmFirstName)) {
			shiftOperatePage.convertToOpenShiftDirectly();
		}
        //if checkbox is select then select team member page will display
		selectedShift = shiftOperatePage.clickOnProfileIcon();
		tmFirstName = selectedShift.findElement(By.className("week-schedule-worker-name")).getText().split(" ")[0];
		shiftOperatePage.clickOnConvertToOpenShift();
		if (shiftOperatePage.verifyConvertToOpenPopUpDisplay(tmFirstName)) {
			shiftOperatePage.convertToOpenShiftAndOfferToSpecificTMs();
		}

		//After click on Edit Shift Time, the Edit Shift window will display
		shiftOperatePage.clickOnProfileIcon();
		shiftOperatePage.clickOnEditShiftTime();
		shiftOperatePage.verifyEditShiftTimePopUpDisplay();
		shiftOperatePage.clickOnCancelEditShiftTimeButton();
		//Edit shift time and click update button
		shiftOperatePage.editAndVerifyShiftTime(true);
		//Edit shift time and click Cancel button
		shiftOperatePage.editAndVerifyShiftTime(false);

		//Verify Edit/View Meal Break
		if (shiftOperatePage.isEditMealBreakEnabled()){
			//After click on Edit Meal Break Time, the Edit Meal Break window will display
			shiftOperatePage.verifyMealBreakTimeDisplayAndFunctionality(true);
			//Verify Delete Meal Break
			shiftOperatePage.verifyDeleteMealBreakFunctionality();
			//Edit meal break time and click update button
			shiftOperatePage.verifyEditMealBreakTimeFunctionality(true);
			//Edit meal break time and click cancel button
			shiftOperatePage.verifyEditMealBreakTimeFunctionality(false);
		} else
			shiftOperatePage.verifyMealBreakTimeDisplayAndFunctionality(false);

		//verify cancel button
		shiftOperatePage.verifyDeleteShiftCancelButton();

		//verify delete shift
		shiftOperatePage.verifyDeleteShift();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Day View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleFunctionalityDayViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

		// Select one team member to view profile
		TeamPage teamPage = pageFactory.createConsoleTeamPage();
		teamPage.goToTeam();
		teamPage.verifyTeamPageLoadedProperlyWithNoLoadingIcon();
		String userName = teamPage.selectATeamMemberToViewProfile();
		String firstName = userName.contains(" ") ? userName.split(" ")[0] : userName;

		//Overtime hours = shift total hours  - 8h

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		//Current week and day is selected by default
		scheduleCommonPage.currentWeekIsGettingOpenByDefault(location);
		boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
		if(isActiveWeekGenerated){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUI();
		//click on Edit button to add new shift
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleCommonPage.clickOnDayView();
		scheduleCommonPage.navigateToNextDayIfStoreClosedForActiveDay();
		shiftOperatePage.deleteAllShiftsInDayView();
		scheduleMainPage.saveSchedule();
		String workRole = shiftOperatePage.getRandomWorkRole();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		SimpleUtils.assertOnFail("Add new shift button is not loaded", !scheduleMainPage.isAddNewDayViewShiftButtonLoaded() , true);

		//"while selecting Open shift:Auto,create button is enabled one open shift will created and system will offer shift automatically
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		newShiftPage.selectWorkRole(workRole);
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
/*		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get(workRole));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}
*/		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();

//		"While selecting Open shift:Manual,Next button is enabled,After Click on Next Select Tms window is enabled and after selecting N number of TMs, offer will send to them"
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		String defaultTimeDuration = newShiftPage.getTimeDurationWhenCreateNewShift();
		newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
		String defaultTimeDurationAftDrag = newShiftPage.getTimeDurationWhenCreateNewShift();
		if (!defaultTimeDurationAftDrag.equals(defaultTimeDuration)) {
			SimpleUtils.pass("A shift time and duration can be changed by dragging it");
		}else
			SimpleUtils.report("there is no change for time duration");
/*		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}*/
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.ManualShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.verifySelectTeamMembersOption();
		newShiftPage.clickOnOfferOrAssignBtn();

//		While selecting Assign TM,Next button is enabled, After Click on Next, Select Tm window is enabled and only one TM can be selected, and shift will assign to him/her
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
/*		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}*/
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		newShiftPage.verifySelectTeamMembersOption();
		newShiftPage.clickOnOfferOrAssignBtn();
        //While click on any shift from Schedule grid , X red button is enabled to delete the shift
		scheduleShiftTablePage.validateXButtonForEachShift();

		//If a shift is more than 8 hours (defined in Controls) then Daily OT hours(Daily OT should be enabled in Controls) should show
		//if make X hour overtime, the Daily OT will be show
		int otFlagCount = scheduleShiftTablePage.getOTShiftCount();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), dragIncreasePoint, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
/*		if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get("MOD"));
		} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
			newShiftPage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
		}*/
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		newShiftPage.verifySelectTeamMembersOption();
		newShiftPage.clickOnOfferOrAssignBtn();
		scheduleMainPage.saveSchedule();
		int otFlagCountAftAddNewShift = scheduleShiftTablePage.getOTShiftCount();
		if (otFlagCountAftAddNewShift > otFlagCount) {
			SimpleUtils.pass("OT shit add successfully");
		}else
			SimpleUtils.fail("add OT new shift failed" , false);


		//If a TM has more than 40 working hours in a week (As defined in Controls) then Week OT hours should show (Week OT should be enabled in Controls)
		int workWeekOverTime = Integer.valueOf(schedulePolicyData.get("single_workweek_overtime"));
		int dayCountInOneWeek = Integer.valueOf(propertyCustomizeMap.get("WORKDAY_COUNT"));
		scheduleCommonPage.clickOnWeekView();
		float shiftHoursInWeekForTM = scheduleShiftTablePage.getShiftHoursByTMInWeekView(firstName);
		scheduleCommonPage.clickOnDayView();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		shiftOperatePage.deleteTMShiftInWeekView(firstName);
		if (shiftHoursInWeekForTM == 0) {
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			/*if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				newShiftPage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				newShiftPage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
			}*/
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.selectSpecificWorkDay(dayCountInOneWeek);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(firstName);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			scheduleCommonPage.clickOnWeekView();
			float shiftHoursInWeekForTMAftAddNewShift = scheduleShiftTablePage.getShiftHoursByTMInWeekView(firstName);

			if ((shiftHoursInWeekForTMAftAddNewShift -shiftHoursInWeekForTM)>workWeekOverTime) {
				scheduleShiftTablePage.verifyWeeklyOverTimeAndFlag(firstName);
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(firstName);
			scheduleMainPage.saveSchedule();
		}else{
			shiftOperatePage.deleteTMShiftInWeekView(firstName);
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  ScheduleTestKendraScott2.sliderShiftCount.SliderShiftStartCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			/*if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				newShiftPage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				newShiftPage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
			}*/
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.selectSpecificWorkDay(dayCountInOneWeek);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(firstName);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			scheduleCommonPage.clickOnWeekView();
			float shiftHoursInWeekForTMAftAddNewShift = scheduleShiftTablePage.getShiftHoursByTMInWeekView(firstName);

			if (shiftHoursInWeekForTMAftAddNewShift > workWeekOverTime) {
				scheduleShiftTablePage.verifyWeeklyOverTimeAndFlag(firstName);
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(firstName);
			scheduleMainPage.saveSchedule();
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
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

		/*
		 *  Navigate to Schedule Week view
		 */
		boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
		if(!isActiveWeekGenerated){
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		boolean isWeekView = true;
		scheduleCommonPage.clickOnWeekView();
		scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		scheduleMainPage.filterScheduleByJobTitle(isWeekView);
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.filterScheduleByJobTitle(isWeekView);
		scheduleMainPage.clickOnCancelButtonOnEditMode();
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Day View")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleInDayViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());

		/*
		 *  Navigate to Schedule day view
		 */
		boolean isWeekView = false;
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated){
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		scheduleMainPage.filterScheduleByJobTitle(isWeekView);
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.filterScheduleByJobTitle(isWeekView);
		scheduleMainPage.clickOnCancelButtonOnEditMode();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Estelle")
	@Enterprise(name = "Coffee_Enterprise")
	@TestName(description = "Verify the Schedule functionality > Job Title Filter Functionality > Combination")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void viewAndFilterScheduleWithGroupByJobTitleFilterCombinationInWeekViewAsInternalAdmin(String username, String password, String browser, String location)
			throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , true);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		/*
		 *  Navigate to Schedule week view
		 */
		boolean isWeekView = true;
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated){
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}

		scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		scheduleMainPage.filterScheduleByWorkRoleAndJobTitle(isWeekView);
		scheduleMainPage.filterScheduleByShiftTypeAndJobTitle(isWeekView);
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.filterScheduleByJobTitle(isWeekView);
		scheduleMainPage.clickOnCancelButtonOnEditMode();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of My Schedule Page")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verificationOfMySchedulePageAsTeamMember(String browser, String username, String password, String location) throws Exception {
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();

		//T1838603 Validate the availability of schedule table.
		ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
		String nickName = profileNewUIPage.getNickNameFromProfile();
		mySchedulePage.validateTheAvailabilityOfScheduleTable(nickName);

		//T1838604 Validate the disability of location selector on Schedule page.
		mySchedulePage.validateTheDisabilityOfLocationSelectorOnSchedulePage();

		//T1838605 Validate the availability of profile menu.
		mySchedulePage.validateTheAvailabilityOfScheduleMenu();

		//T1838606 Validate the focus of schedule.
		mySchedulePage.validateTheFocusOfSchedule();

		//T1838607 Validate the default filter is selected as Scheduled.
		mySchedulePage.validateTheDefaultFilterIsSelectedAsScheduled();

		//T1838608 Validate the focus of week.
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		dashboardPage.navigateToDashboard();
		String currentDate = dashboardPage.getCurrentDateFromDashboard();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		mySchedulePage.validateTheFocusOfWeek(currentDate);

		//T1838609 Validate the selection of previous and upcoming week.
		mySchedulePage.verifySelectOtherWeeks();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verification of To and Fro navigation of week picker")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verificationOfToAndFroNavigationOfWeekPickerAsTeamMember(String browser, String username, String password, String location) throws Exception {
		try {
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String nickName = profileNewUIPage.getNickNameFromProfile();

			//T1838610 Validate the click ability of forward and backward button.
			scheduleCommonPage.validateForwardAndBackwardButtonClickable();

			//T1838611 Validate the data according to the selected week.
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			mySchedulePage.validateTheDataAccordingToTheSelectedWeek();

			//T1838612 Validate the seven days - Sunday to Saturday is available in schedule table.
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			mySchedulePage.validateTheSevenDaysIsAvailableInScheduleTable();
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			///Log in as admin to get the operation hours
			loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
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

			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab("Schedule");
			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
			scheduleMainPage.saveSchedule();
			String workRole = shiftOperatePage.getRandomWorkRole();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(nickName);
//			if(newShiftPage.displayAlertPopUp())
//				newShiftPage.displayAlertPopUpForRoleViolation();
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			if (!toggleSummaryPage.isSummaryViewLoaded())
				toggleSummaryPage.toggleSummaryView();
			String theEarliestAndLatestTimeInSummaryView = toggleSummaryPage.getTheEarliestAndLatestTimeInSummaryView(schedulePoliciesBufferHours);
			SimpleUtils.report("theEarliestAndLatestOperationHoursInSummaryView is " + theEarliestAndLatestTimeInSummaryView);
			loginPage.logOut();

			///Log in as team member again to compare the operation hours
			loginToLegionAndVerifyIsLoginDone(username, password, location);
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.navigateToNextWeek();
			String theEarliestAndLatestTimeInScheduleTable = mySchedulePage.getTheEarliestAndLatestTimeInScheduleTable();
			SimpleUtils.report("theEarliestAndLatestOperationHoursInScheduleTable is " + theEarliestAndLatestTimeInScheduleTable);
			// mySchedulePage.compareOperationHoursBetweenAdminAndTM(theEarliestAndLatestTimeInSummaryView, theEarliestAndLatestTimeInScheduleTable);
			// todo: BLocked by https://legiontech.atlassian.net/browse/SCH-4413

			//T1838613 Validate that hours and date is visible of shifts.
			mySchedulePage.validateThatHoursAndDateIsVisibleOfShifts();
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
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			//T1838614 Validate the clickability of Profile picture in a shift.
			mySchedulePage.validateProfilePictureInAShiftClickable();

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

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();

			//T1838616 Validate the availability of info icon.
			mySchedulePage.validateTheAvailabilityOfInfoIcon();

			//T1838617 Validate the clickability of info icon.
			mySchedulePage.validateInfoIconClickable();

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

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			//T1838619 Validate the availability of Open shift Smartcard.
			smartCardPage.validateTheAvailabilityOfOpenShiftSmartcard();

			//T1838620 Validate the clickability of View shifts in Open shift smartcard.
			mySchedulePage.validateViewShiftsClickable();

			//T1838621 Validate the number of open shifts in smartcard and schedule table.
			mySchedulePage.validateTheNumberOfOpenShifts();

			//T1838622 Verify the availability of claim open shift popup.
			mySchedulePage.verifyTheAvailabilityOfClaimOpenShiftPopup();
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
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();

			// Get the hours and the count of the tms for each day, ex: "37.5 Hrs 5TMs"
			HashMap<String, String> hoursNTMsCountFirstWeek = scheduleShiftTablePage.getTheHoursNTheCountOfTMsForEachWeekDays();
			HashMap<String, List<String>> shiftsForEachDayFirstWeek = scheduleShiftTablePage.getTheContentOfShiftsForEachWeekDay();
			HashMap<String, String> budgetNScheduledHoursFirstWeek = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
			String cardName = "COMPLIANCE";
			boolean isComplianceCardLoadedFirstWeek = smartCardPage.isSpecificSmartCardLoaded(cardName);
			int complianceShiftCountFirstWeek = 0;
			if (isComplianceCardLoadedFirstWeek) {
				complianceShiftCountFirstWeek = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
			}
			String firstWeekInfo = scheduleCommonPage.getActiveWeekText();
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

			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			List<String> weekDaysToClose = new ArrayList<>();
			createSchedulePage.createScheduleForNonDGByWeekInfo(firstWeekInfo, weekDaysToClose, null);

			HashMap<String, String> hoursNTMsCountSecondWeek = scheduleShiftTablePage.getTheHoursNTheCountOfTMsForEachWeekDays();
			HashMap<String, List<String>> shiftsForEachDaySecondWeek = scheduleShiftTablePage.getTheContentOfShiftsForEachWeekDay();
			HashMap<String, String> budgetNScheduledHoursSecondWeek = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
			boolean isComplianceCardLoadedSecondWeek = smartCardPage.isSpecificSmartCardLoaded(cardName);
			int complianceShiftCountSecondWeek = 0;
			if (isComplianceCardLoadedFirstWeek) {
				complianceShiftCountSecondWeek = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			List<String> weekDaysToClose = new ArrayList<>(Arrays.asList("Sunday", "Tuesday"));
			createSchedulePage.createScheduleForNonDGByWeekInfo("SUGGESTED", weekDaysToClose, null);

			// Verify that the closed week day should not have any shifts
			scheduleShiftTablePage.verifyNoShiftsForSpecificWeekDay(weekDaysToClose);
			// Go to day view, check the closed week day should show "Store is Closed"
			scheduleCommonPage.clickOnDayView();
			scheduleShiftTablePage.verifyStoreIsClosedForSpecificWeekDay(weekDaysToClose);
			// Toggle Summary view, verify that the specific week days shows Closed
			toggleSummaryPage.toggleSummaryView();
			toggleSummaryPage.verifyClosedDaysInToggleSummaryView(weekDaysToClose);
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
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.goToConsoleScheduleAndScheduleSubMenu();
			String weekInfo = toggleSummaryPage.getWeekInfoBeforeCreateSchedule();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			HashMap<String,String> budgetNScheduledFromGraph = createSchedulePage.verifyNGetBudgetNScheduleWhileCreateScheduleForNonDGFlowNewUI(weekInfo, location);
			String budgetFromGraph = budgetNScheduledFromGraph.get("Budget");
			String scheduledFromGraph = budgetNScheduledFromGraph.get("Scheduled");
			if (!toggleSummaryPage.isSummaryViewLoaded())
				toggleSummaryPage.toggleSummaryView();
			List<String> budgetsOnSTAFF = toggleSummaryPage.getBudgetedHoursOnSTAFF();
			String budgetOnWeeklyBudget = toggleSummaryPage.getBudgetOnWeeklyBudget();
			HashMap<String, String> budgetNScheduledHoursFromSmartCard = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
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
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content after changing Operating Hours for non dg flow ")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentAfterChangingOperatingHrsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		try {
			ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab("Schedule");
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();
			if (createSchedulePage.isWeekGenerated()){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			String day = "Sunday";
			String startTime = "07:00AM";
			String endTime = "06:00PM";
			//set operating hours: Sunday -> start time: 07:00AM, end time: 06:00PM
			createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingParameters(day, startTime, endTime);
			scheduleShiftTablePage.verifyDayHasShifts(day);
			scheduleShiftTablePage.verifyDayHasShifts("Monday");
			scheduleShiftTablePage.verifyDayHasShifts("Tuesday");
			scheduleShiftTablePage.verifyDayHasShifts("Wednesday");
			scheduleShiftTablePage.verifyDayHasShifts("Thursday");
			scheduleShiftTablePage.verifyDayHasShifts("Friday");
			scheduleShiftTablePage.verifyDayHasShifts("Saturday");
			scheduleMainPage.goToToggleSummaryView();
			//verify the operating hours in Toggle Summary View
			toggleSummaryPage.verifyOperatingHrsInToggleSummary(day, startTime, endTime);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the content after changing Operating Hours for non dg flow - next day")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyContentAfterChangingOperatingHrsNextDayAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab("Schedule");
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();
			if (createSchedulePage.isWeekGenerated()){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			String day = "Sunday";
			String startTime = "11:00AM";
			String endTime = "02:00AM";
			//set operating hours: Sunday -> start time: 11:00AM, end time: 02:00AM
			createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingParameters(day, startTime, endTime);
			//verify the day we set a next day time has shifts.
			scheduleShiftTablePage.verifyDayHasShifts(day);
			//verify the operating hours in Toggle Summary View
			scheduleMainPage.goToToggleSummaryView();
			toggleSummaryPage.verifyOperatingHrsInToggleSummary(day, startTime, endTime);
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
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			String budgetHours = smartCardPage.getBudgetNScheduledHoursFromSmartCard().get("Budget");
			String guidanceHours = smartCardPage.getBudgetNScheduledHoursFromSmartCard().get("Guidance");
			float budgetHoursInSchedule = 0;
			if (budgetHours != null) {
				budgetHoursInSchedule = Float.parseFloat(budgetHours.replace(",",""));
			} else if (guidanceHours != null) {
				budgetHoursInSchedule = Float.parseFloat(guidanceHours.replace(",",""));
			} else
				SimpleUtils.fail("The budget and guidance hour fail to load! ", false);

			dashboardPage.clickOnDashboardConsoleMenu();
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.selectCurrentUpperFieldAgain("District");
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();

			ScheduleDMViewPage scheduleDMViewPage = pageFactory.createScheduleDMViewPage();
			BigDecimal round = new BigDecimal(scheduleDMViewPage.getBudgetedHourOfScheduleInDMViewByLocation(location));
			float budgetedHoursInDMViewSchedule = round.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();
			budgetHoursInSchedule  = (new BigDecimal(budgetHoursInSchedule)).setScale(1 ,BigDecimal.ROUND_HALF_UP).floatValue();

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
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify smart card for schedule not publish(include past weeks)")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySmartCardForScheduleNotPublishAsInternalAdmin(String browser, String username, String password, String location) throws Exception{

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab("Schedule");
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		if (createSchedulePage.isWeekGenerated()){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUI();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		//make edits
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		newShiftPage.selectWorkRole("");
		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		scheduleMainPage.saveSchedule();
		//generate and save, should not display number of changes, we set it as 0.
		int changesNotPublished = 0;
		//Verify changes not publish smart card.
		SimpleUtils.assertOnFail("Changes not publish smart card is not loaded!",smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
		smartCardPage.verifyChangesNotPublishSmartCard(changesNotPublished);
		createSchedulePage.verifyLabelOfPublishBtn("Publish");
		String activeWeek = scheduleCommonPage.getActiveWeekText();
		scheduleCommonPage.clickOnScheduleSubTab("Overview");
		List<String> resultListInOverview = scheduleOverviewPage.getOverviewData();
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
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify smart card for schedule not publish(include past weeks) - republish")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyNumberOnSmartCardForScheduleNotPublishAsInternalAdmin(String browser, String username, String password, String location) {
		try {

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab("Schedule");
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();
			if (createSchedulePage.isWeekGenerated()){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			scheduleMainPage.clickOnFilterBtn();
			scheduleMainPage.selectShiftTypeFilterByText("Action Required");
			shiftOperatePage.deleteTMShiftInWeekView("");
			scheduleMainPage.clickOnFilterBtn();
			scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
			//make edits and publish
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.selectWorkRole("");
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			//make edits and save
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.selectWorkRole("");
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			scheduleMainPage.saveSchedule();

			int changesNotPublished = 1;
			//Verify changes not publish smart card.
			SimpleUtils.assertOnFail("Changes not publish smart card is not loaded!",smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
			smartCardPage.verifyChangesNotPublishSmartCard(changesNotPublished);
			createSchedulePage.verifyLabelOfPublishBtn("Republish");
			String activeWeek = scheduleCommonPage.getActiveWeekText();
			scheduleCommonPage.clickOnScheduleSubTab("Overview");
			List<String> resultListInOverview = scheduleOverviewPage.getOverviewData();
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
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			String cardName = "COMPLIANCE";
			int originalComplianceCount = 0;
			if (smartCardPage.isSpecificSmartCardLoaded(cardName)) {
				originalComplianceCount = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
			}
			scheduleCommonPage.clickOnDayView();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				scheduleShiftTablePage.dragOneShiftToMakeItOverTime();
			} else {
				//if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				shiftOperatePage.clickOnProfileIcon();
				shiftOperatePage.clickOnEditShiftTime();
				shiftOperatePage.verifyEditShiftTimePopUpDisplay();
				shiftOperatePage.editShiftTimeToTheLargest();
				shiftOperatePage.clickOnUpdateEditShiftTimeButton();
			}
			scheduleMainPage.saveSchedule();
			scheduleCommonPage.clickOnWeekView();
			int currentComplianceCount = 0;
			if (smartCardPage.isSpecificSmartCardLoaded(cardName)) {
				currentComplianceCount = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
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
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();

			String cardName = "COMPLIANCE";
			int originalComplianceCount = 0;
			if (smartCardPage.isSpecificSmartCardLoaded(cardName)) {
				originalComplianceCount = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
			}
			scheduleCommonPage.clickOnDayView();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				scheduleShiftTablePage.dragOneShiftToMakeItOverTime();
			} else {
				//if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				shiftOperatePage.clickOnProfileIcon();
				shiftOperatePage.clickOnEditShiftTime();
				shiftOperatePage.verifyEditShiftTimePopUpDisplay();
				shiftOperatePage.editShiftTimeToTheLargest();
				shiftOperatePage.clickOnUpdateEditShiftTimeButton();
			}
			scheduleMainPage.saveSchedule();
			scheduleCommonPage.clickOnWeekView();
			int currentComplianceCount = 0;
			if (smartCardPage.isSpecificSmartCardLoaded(cardName)) {
				currentComplianceCount = smartCardPage.getComplianceShiftCountFromSmartCard(cardName);
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

			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			scheduleCommonPage.goToConsoleScheduleAndScheduleSubMenu();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated)
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			String workRole = shiftOperatePage.getRandomWorkRole();
			newShiftPage.addOpenShiftWithLastDay(workRole);
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.addOpenShiftWithFirstDay(workRole);
			shiftOperatePage.deleteLatestOpenShift();
			scheduleMainPage.saveSchedule();

			// Check the number on changes not publish smart card
			if (smartCardPage.getChangesOnActionRequired().contains("2 Changes"))
				SimpleUtils.pass("ACTION REQUIRED Smart Card: The number on changes not publish smart card is 2");
			else
				SimpleUtils.fail("ACTION REQUIRED Smart Card: The number on changes not publish smart card is incorrect",true);

			// Filter unpublished changes to check the shifts and tooltip
			scheduleMainPage.selectShiftTypeFilterByText("Unpublished changes");
			if (scheduleShiftTablePage.getShiftsCount() == 1)
				SimpleUtils.pass("ACTION REQUIRED Smart Card: There is only one shift as expected");
			else
				SimpleUtils.fail("ACTION REQUIRED Smart Card: There is not only one shift unexpectedly",true);
			if (smartCardPage.getTooltipOfUnpublishedDeleted().contains("1 shift deleted"))
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.clickOnControlsUsersAndRolesSection();
			String inactiveUser = controlsNewUIPage.selectAnyActiveTM();
			String date = controlsNewUIPage.deactivateActiveTM();

			// Assign to the TM to verify the message and warning

			scheduleCommonPage.goToConsoleScheduleAndScheduleSubMenu();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.selectAShiftToAssignTM(inactiveUser);
			shiftOperatePage.verifyInactiveMessageNWarning(inactiveUser,date);

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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
			String workRole = shiftOperatePage.getRandomWorkRole();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.addOpenShiftWithLastDay(workRole);
			scheduleMainPage.saveSchedule();

			//Verify the Unpublished Edits text on overview page
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
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



			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleCommonPage.clickOnDayView();
			//navigate to the time off day
			for (int i=0; i<6;i++){
				scheduleCommonPage.clickOnNextDaySchedule(scheduleCommonPage.getActiveAndNextDay());
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			String workRole = shiftOperatePage.getRandomWorkRole();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			/*if (getDriver().getCurrentUrl().contains(propertyMap.get("KendraScott2_Enterprise"))) {
				newShiftPage.selectWorkRole(scheduleWorkRoles.get("MOD"));
			} else if (getDriver().getCurrentUrl().contains(propertyMap.get("Coffee_Enterprise"))) {
				newShiftPage.selectWorkRole(scheduleWorkRoles.get("WorkRole_BARISTA"));
			}*/
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(nickNameFromProfile);
			shiftOperatePage.verifyMessageIsExpected("time off");
			shiftOperatePage.verifyWarningModelForAssignTMOnTimeOff(nickNameFromProfile);
			scheduleMainPage.clickOnCancelButtonOnEditMode();


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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			//Go to control to set the override assignment rule as Yes.
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.clickOnGlobalLocationButton();
			controlsNewUIPage.clickOnSchedulingPoliciesShiftAdvanceBtn();
			controlsNewUIPage.enableOverRideAssignmentRuleAsYes();


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			String workRole = shiftOperatePage.getRandomWorkRole();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName("Keanu");
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
            ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			List<String> firstShiftInfo = new ArrayList<>();
			while(firstShiftInfo.size() == 0 || firstShiftInfo.get(0).equalsIgnoreCase("open")
					|| firstShiftInfo.get(0).equalsIgnoreCase("unassigned")){
				firstShiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.selectWorkRole(firstShiftInfo.get(4));
			String shiftEndTime = "";
			String shiftStartTime = "";
			if (firstShiftInfo.get(2).split("-")[1].contains(":")) {
				shiftEndTime = firstShiftInfo.get(2).split("-")[1].split(":")[0] + firstShiftInfo.get(2).split("-")[1].substring(firstShiftInfo.get(2).split("-")[1].length() - 2);
				SimpleUtils.report("Get the shift end time: " + shiftEndTime);
			}
			if (firstShiftInfo.get(2).split("-")[0].contains(":")) {
				shiftStartTime = firstShiftInfo.get(2).split("-")[0].split(":")[0] + firstShiftInfo.get(2).split("-")[0].substring(firstShiftInfo.get(2).split("-")[0].length() - 2);
				SimpleUtils.report("Get the shift start time: " + shiftStartTime);
			}
			newShiftPage.moveSliderAtCertainPoint(shiftEndTime, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.moveSliderAtCertainPoint(shiftStartTime, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.selectWorkingDaysOnNewShiftPageByIndex(Integer.parseInt(firstShiftInfo.get(1)));
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			shiftOperatePage.verifyScheduledWarningWhenAssigning(firstShiftInfo.get(0), firstShiftInfo.get(2));
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
			String nearByLocation = getCrendentialInfo("NearByLocationInfo");
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			controlsPage.gotoControlsPage();
			SimpleUtils.assertOnFail("Controls page not loaded successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			SimpleUtils.assertOnFail("Scheduling collaboration page not loaded successfully!", controlsNewUIPage.isControlsScheduleCollaborationLoaded(), false);
			controlsNewUIPage.clickOnGlobalLocationButton();
			ConfigurationPage configurationPage = pageFactory.createOpsPortalConfigurationPage();
			configurationPage.setWFS("Yes");

			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
			List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
			if (!createSchedulePage.isWeekPublished()) {
				createSchedulePage.publishActiveSchedule();
			}

			// Navigate to the near by location to create the shift for this TM from AUSTIN DOWNTOWN
			dashboardPage.navigateToDashboard();
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(nearByLocation);
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			scheduleCommonPage.navigateToNextWeek();
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.selectWorkRole(shiftInfo.get(4));
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount2.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.selectWorkingDaysOnNewShiftPageByIndex(Integer.parseInt(shiftInfo.get(1)));
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			shiftOperatePage.verifyScheduledWarningWhenAssigning(shiftInfo.get(0), shiftInfo.get(2));
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			AnalyzePage analyzePage = pageFactory.createAnalyzePage();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();

			//verify version number in analyze page

			analyzePage.clickOnAnalyzeBtn("history");
			String version0 = "Suggested Schedule";
			String version1 = "0.1";
			String version2 = "1.1";
			analyzePage.verifyScheduleVersion(version0);
			analyzePage.closeAnalyzeWindow();
			//make edits and save
			String workRole = shiftOperatePage.getRandomWorkRole();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			scheduleMainPage.verifyVersionInSaveMessage(version1);
			//suggested tab
			scheduleMainPage.clickOnSuggestedButton();
			SimpleUtils.assertOnFail("Changes not publish smart card is loaded in suggested page!",!smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
			scheduleMainPage.clickOnManagerButton();
			SimpleUtils.assertOnFail("Changes not publish smart card is not loaded in Manager page!",smartCardPage.isSpecificSmartCardLoaded("ACTION REQUIRED"),false);
			analyzePage.clickOnAnalyzeBtn("history");
			analyzePage.verifyScheduleVersion(version1);
			analyzePage.closeAnalyzeWindow();
			createSchedulePage.publishActiveSchedule();
			analyzePage.clickOnAnalyzeBtn("history");
			analyzePage.verifyScheduleVersion(version2);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Verify the schedule version and work role shows correctly")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyScheduleVersionAndWorkRoleShowsCorrectlyAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		AnalyzePage analyzePage = pageFactory.createAnalyzePage();
		SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (isWeekGenerated){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUI();

		//verify version number in analyze page

		HashMap<String, String> valuesOnTheSmartCard = smartCardPage.getBudgetNScheduledHoursFromSmartCard();
		analyzePage.clickOnAnalyzeBtn("labor");
		String resultTotalHrs = analyzePage.getPieChartTotalHrsFromLaborGuidanceTab();
		SimpleUtils.assertOnFail("Budget hours are inconsistent!", resultTotalHrs.contains(valuesOnTheSmartCard.get("Budget")), false);
		analyzePage.closeAnalyzeWindow();
		analyzePage.clickOnAnalyzeBtn("history");
		String versionInfo = analyzePage.getPieChartHeadersFromHistoryTab("schedule");
		String version0 = "0.0";
		SimpleUtils.assertOnFail("Version info is inconsistent!", versionInfo.contains(version0), false);
		SimpleUtils.assertOnFail("Labor guidance is inconsistent!", resultTotalHrs.equalsIgnoreCase(analyzePage.getPieChartTotalHrsFromHistoryTab("guidance")), false);
		analyzePage.closeAnalyzeWindow();

		String version1 = "0.1";
		String version2 = "1.0";
		//make edits and save
		String workRole = shiftOperatePage.getRandomWorkRole();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		scheduleMainPage.saveSchedule();

		analyzePage.clickOnAnalyzeBtn("history");
		versionInfo = analyzePage.getPieChartHeadersFromHistoryTab("schedule");
		SimpleUtils.assertOnFail("Version info is inconsistent!", versionInfo.contains(version1), false);
		SimpleUtils.assertOnFail("Labor guidance is inconsistent!", resultTotalHrs.equalsIgnoreCase(analyzePage.getPieChartTotalHrsFromHistoryTab("guidance")), false);
		analyzePage.closeAnalyzeWindow();

		createSchedulePage.publishActiveSchedule();
		analyzePage.clickOnAnalyzeBtn("history");
		versionInfo = analyzePage.getPieChartHeadersFromHistoryTab("schedule");
		SimpleUtils.assertOnFail("Version info is inconsistent!", versionInfo.contains(version2), false);
		SimpleUtils.assertOnFail("Labor guidance is inconsistent!", resultTotalHrs.equalsIgnoreCase(analyzePage.getPieChartTotalHrsFromHistoryTab("guidance")), false);

	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Verify offers generated for open shift")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyOffersGeneratedForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (isWeekGenerated){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUI();

		//delete unassigned shifts and open shifts.
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		//shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
		//Delete all shifts are action required.
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Open");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
		shiftOperatePage.deleteAllShiftsInWeekView();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		shiftOperatePage.clickOnProfileIcon();
		shiftOperatePage.clickOnConvertToOpenShift();
		shiftOperatePage.convertToOpenShiftDirectly();
		scheduleMainPage.saveSchedule();
		createSchedulePage.publishActiveSchedule();
		BasePage.waitForSeconds(5);
		shiftOperatePage.clickOnProfileIconOfOpenShift();
		scheduleShiftTablePage.clickViewStatusBtn();
		shiftOperatePage.verifyListOfOfferNotNull();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify x Compliance warnings in publish confirm modal")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyComplianceWarningInPublishConfirmModalAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();

			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("unassigned");
			scheduleMainPage.saveSchedule();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.clickOnProfileIcon();
			shiftOperatePage.clickOnEditShiftTime();
			shiftOperatePage.editShiftTimeToTheLargest();
			shiftOperatePage.clickOnUpdateEditShiftTimeButton();
			scheduleMainPage.saveSchedule();

			String smardCard ="COMPLIANCE";
			int complianceShiftCount= smartCardPage.getComplianceShiftCountFromSmartCard(smardCard);
			createSchedulePage.publishActiveSchedule();
			String complianceMsg = createSchedulePage.getMessageForComplianceWarningInPublishConfirmModal();
			if (complianceMsg != null && !complianceMsg.equals("") && SimpleUtils.isNumeric(complianceMsg.split(" ")[0])){
				SimpleUtils.assertOnFail("Compliance count is not correct!", Integer.parseInt(complianceMsg.split(" ")[0]) == complianceShiftCount, false);
			} else {
				SimpleUtils.fail("Compliance warning has issue with count, please check!", false);
			}

			createSchedulePage.clickConfirmBtnOnPublishConfirmModal();

			//verify compliance count on smart card is consistent with before.
			SimpleUtils.assertOnFail("Compliance count on smart card is inconsistent with before", complianceShiftCount == smartCardPage.getComplianceShiftCountFromSmartCard(smardCard), false);

			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			createSchedulePage.createScheduleForNonDGFlowNewUI();

			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("unassigned");
			scheduleMainPage.clickOnFilterBtn();
			scheduleMainPage.selectShiftTypeFilterByText("Compliance Review");
			shiftOperatePage.deleteTMShiftInWeekView("");
			scheduleMainPage.saveSchedule();

			SimpleUtils.assertOnFail("Comliance smart card should not load!", !smartCardPage.isSpecificSmartCardLoaded(smardCard), false);
			createSchedulePage.publishActiveSchedule();
			SimpleUtils.assertOnFail("Compliance warning message shouldn't show up on publish confirm modal!",
					!createSchedulePage.isComplianceWarningMsgLoad(), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify x Hours over budget in publish confirm modal on non DG")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyHrsOverBudgetInPublishConfirmModalForNonDGAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Succerssfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();

			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			String workRoleOfTM = shiftOperatePage.getRandomWorkRole();
			scheduleMainPage.clickOnFilterBtn();
			scheduleMainPage.selectShiftTypeFilterByText("Action Required");
			while (scheduleShiftTablePage.getShiftsCount() != 0){
				scheduleShiftTablePage.clickOnProfileOfUnassignedShift();
				shiftOperatePage.clickOnConvertToOpenShift();
				shiftOperatePage.convertToOpenShiftDirectly();
			}
			scheduleMainPage.clickOnFilterBtn();
			scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			SimpleUtils.assertOnFail("Over budget warning message shouldn't show up on publish confirm modal!", createSchedulePage.isComplianceWarningMsgLoad() , false);
			scheduleCommonPage.clickCancelButtonOnPopupWindow();
			HashMap<String, Float> values = smartCardPage.getScheduleBudgetedHoursInScheduleSmartCard();
			float value3 = values.get("scheduledHours");
			float value4 = values.get("budgetedHours");
			if (value4>=value3) {
				scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
				int i = Math.round((value4 - value3) / 7) + 1;
				for (int j = 0; j < i + 1; j++) {
					newShiftPage.clickOnDayViewAddNewShiftButton();
					newShiftPage.selectWorkRole(workRoleOfTM);
					newShiftPage.clearAllSelectedDays();
					Random r = new Random();
					int index = r.nextInt(6); // 生成[0,6]区间的整数
					newShiftPage.selectDaysByIndex(index, index, index);
					//newShiftPage.moveSliderAtSomePoint("8", 12, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
					newShiftPage.moveSliderAtCertainPoint("1", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
					newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
					newShiftPage.clickOnCreateOrNextBtn();
				}
				scheduleMainPage.saveSchedule();
			}

			values = smartCardPage.getScheduleBudgetedHoursInScheduleSmartCard();
			value3 = values.get("scheduledHours");
			value4 = values.get("budgetedHours");
			createSchedulePage.publishActiveSchedule();
			String warningMsg = createSchedulePage.getMessageForComplianceWarningInPublishConfirmModal();
			SimpleUtils.assertOnFail("Over budget warning message shouldn't show up on publish confirm modal!", warningMsg.contains(String.valueOf(Math.abs(value3-value4)).replace(".0", "")) && warningMsg.contains("Hrs over guidance") , false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Verify search bar on schedule page")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySearchBarOnSchedulePageInWeekViewAsInternalAdmin (String browser, String username, String password, String location) throws Exception {
		try{
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();

			//click search button
			scheduleMainPage.clickOnOpenSearchBoxButton();

			//Check the ghost text inside the Search bar
			scheduleMainPage.verifyGhostTextInSearchBox();

			//Get the info of first shift
			List<String> shiftInfo = new ArrayList<>();
			String firstNameOfTM = "";
			while (firstNameOfTM.equals("") || firstNameOfTM.equalsIgnoreCase("Open")
					|| firstNameOfTM.equalsIgnoreCase("Unassigned")) {
				shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
				//Search shift by TM names: first name and last name
				firstNameOfTM = shiftInfo.get(0);
			}

			List<WebElement> searchResultOfFirstName = scheduleMainPage.searchShiftOnSchedulePage(firstNameOfTM);
			scheduleShiftTablePage.verifySearchResult(firstNameOfTM, null, null, null, searchResultOfFirstName);

			String lastNameOfTM = shiftInfo.get(5);
			List<WebElement> searchResultOfLastName = scheduleMainPage.searchShiftOnSchedulePage(lastNameOfTM);
			scheduleShiftTablePage.verifySearchResult(null, lastNameOfTM, null, null, searchResultOfLastName);

			//Search shift by work role
			String workRole = shiftInfo.get(4);
			List<WebElement> searchResultOfWorkRole = scheduleMainPage.searchShiftOnSchedulePage(workRole);
			scheduleShiftTablePage.verifySearchResult(null, null, workRole, null, searchResultOfWorkRole);

			//Search shift by job title
			String jobTitle = shiftInfo.get(3);
			List<WebElement> searchResultOfJobTitle = scheduleMainPage.searchShiftOnSchedulePage(jobTitle);
			scheduleShiftTablePage.verifySearchResult(null, null, null, jobTitle, searchResultOfJobTitle);

			//Click X button to close search box
			scheduleMainPage.clickOnCloseSearchBoxButton();

			//Go to edit mode
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

			//click search button
			scheduleMainPage.clickOnOpenSearchBoxButton();
			//Click X button to close search box
			scheduleMainPage.clickOnCloseSearchBoxButton();
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
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
			String nyLocation = getCrendentialInfo("NearByLocationInfo");
			locationSelectorPage.changeUpperFieldDirect("Location", nyLocation);
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Select one team member to view profile

			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			List<String> tmList = teamPage.getTMNameList();
			String firstName = tmList.get(0);
			teamPage.activeTMAndRejectOrApproveAllAvailabilityAndTimeOff(firstName);

			//Go to schedule page

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			// Navigate to next week
			scheduleCommonPage.navigateToNextWeek();

			// create the schedule if not created
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}

			//Select AUSTIN DOWNTOWN location
			dashboardPage.navigateToDashboard();
			locationSelectorPage.changeUpperFieldDirect("Location", location);
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
			scheduleCommonPage.navigateToNextWeek();

			boolean isWeekGenerated2 = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated2) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			// Edit the Schedule
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			// Delete all the shifts that are assigned to the team member on Step #1
			shiftOperatePage.deleteTMShiftInWeekView(firstName);
			String workRole = shiftOperatePage.getRandomWorkRole();

			// Create new shift for this schedule
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.clearAllSelectedDays();
			newShiftPage.selectSpecificWorkDay(1);
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(firstName);
			String scheduleStatus = shiftOperatePage.getTheMessageOfTMScheduledStatus();
			SimpleUtils.assertOnFail("TM scheduled status message display failed",
					scheduleStatus.contains("Schedule not published") ||
							scheduleStatus.contains("Schedule Not Created"), false);

			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleShiftTablePage.verifyDayHasShiftByName(0, firstName.split(" ")[0]);
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
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


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()) , false);
			// Navigate to a week
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();
			// create the schedule if not created
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0 || shiftInfo.get(0).equalsIgnoreCase("Open")) {
				shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			String workRoleOfTM1 = shiftInfo.get(4);
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
			scheduleMainPage.saveSchedule();
			dashboardPage.navigateToDashboard();
			locationSelectorPage.changeLocation(location);
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()) , false);
			// Navigate to a week
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();
			// create the schedule if not created
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.selectWorkRole(workRoleOfTM1);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(firstNameOfTM1);
			shiftOperatePage.verifyMessageIsExpected("schedule not published");
			shiftOperatePage.verifyWarningModelMessageAssignTMInAnotherLocWhenScheduleNotPublished();
			shiftOperatePage.verifyTMNotSelected();
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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()) , false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()) , false);
			// Navigate to a week
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();
			// create the schedule if not created
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated){
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange( "09:00AM", "08:00PM");
			scheduleShiftTablePage.verifyDayHasShifts("Sunday");
			scheduleShiftTablePage.verifyDayHasShifts("Monday");
			scheduleShiftTablePage.verifyDayHasShifts("Tuesday");
			scheduleShiftTablePage.verifyDayHasShifts("Wednesday");
			scheduleShiftTablePage.verifyDayHasShifts("Thursday");
			scheduleShiftTablePage.verifyDayHasShifts("Friday");
			scheduleShiftTablePage.verifyDayHasShifts("Saturday");
			scheduleCommonPage.clickOnDayView();
			List<WebElement> shiftsInDayView = scheduleShiftTablePage.getAvailableShiftsInDayView();
			SimpleUtils.assertOnFail("Day view shifts don't diaplay successfully!", !shiftsInDayView.isEmpty(), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
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
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("no");
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the functionality of Violation limit and Budget overage limit")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyViolationLimitAndBudgetOverageLimitAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ToggleSummaryPage toggleSummaryPage = pageFactory.createToggleSummaryPage();
			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("yes");
			controlsNewUIPage.setViolationLimit("2");
			controlsNewUIPage.setBudgetOverageLimit("0");

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			//Make current week 3 or more violation
			String pastWeekInfo1 = scheduleCommonPage.getActiveWeekText().substring(10);
			pastWeekInfo1 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo1);
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			String workRoleOfTM1 = shiftInfo.get(4);
			List<String> shiftInfo2 = new ArrayList<>();
			while (shiftInfo2.size() == 0) {
				shiftInfo2 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM2 = shiftInfo2.get(0);
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM2);
			scheduleMainPage.saveSchedule();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.selectWorkRole(workRoleOfTM1);
			newShiftPage.clearAllSelectedDays();
			newShiftPage.selectDaysByIndex(1, 2, 3);
			newShiftPage.moveSliderAtSomePoint("8", 12, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			//newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(firstNameOfTM1);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			//Make next week 2 or less violation
			scheduleCommonPage.navigateToNextWeek();
			String pastWeekInfo2 = scheduleCommonPage.getActiveWeekText().substring(10);
			pastWeekInfo2 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo2);
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM2);
			scheduleMainPage.saveSchedule();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.selectWorkRole(workRoleOfTM1);
			newShiftPage.clearAllSelectedDays();
			//create 2 overtime violation
			newShiftPage.selectDaysByIndex(1, 1, 1);
			newShiftPage.selectDaysByIndex(2, 2, 2);
			newShiftPage.moveSliderAtSomePoint("8", 12, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			//newShiftPage.moveSliderAtSomePoint("8", 0, ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(firstNameOfTM1);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();


			//Make another next week 0 violation
			scheduleCommonPage.navigateToNextWeek();
			String pastWeekInfo3 = scheduleCommonPage.getActiveWeekText().substring(10);
			pastWeekInfo3 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo3);
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			createSchedulePage.publishActiveSchedule();

			//Go to another next week to check copy restriction.
			scheduleCommonPage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.clickCreateScheduleBtn();
			createSchedulePage.clickNextBtnOnCreateScheduleWindow();
			createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, false);
			createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo2, true);
			createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo3, true);
			createSchedulePage.clickBackBtnAndExitCreateScheduleWindow();

			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("no");

			//Verify budget overage limit
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			//Regenerate current week schedule
			pastWeekInfo1 = scheduleCommonPage.getActiveWeekText().substring(10);
			pastWeekInfo1 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo1);
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value1 = toggleSummaryPage.getStaffingGuidanceHrs();
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			createSchedulePage.publishActiveSchedule();
			//Make next week has higher schedule hours than budget hours
			scheduleCommonPage.navigateToNextWeek();
			pastWeekInfo2 = scheduleCommonPage.getActiveWeekText().substring(10);
			pastWeekInfo2 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo2);
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value2 = toggleSummaryPage.getStaffingGuidanceHrs();
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
			}
			workRoleOfTM1 = shiftInfo.get(4);
			createSchedulePage.publishActiveSchedule();


			//Regenerate another next week schedule
			scheduleCommonPage.navigateToNextWeek();
			pastWeekInfo3 = scheduleCommonPage.getActiveWeekText().substring(10);
			pastWeekInfo3 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo3);
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value3 = toggleSummaryPage.getStaffingGuidanceHrs();
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			createSchedulePage.publishActiveSchedule();

			//Go to another next week to check copy restriction.
			scheduleCommonPage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			float value4 = toggleSummaryPage.getStaffingGuidanceHrs();
			if (value4>=value3){
				scheduleCommonPage.clickOnScheduleConsoleMenuItem();
				SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
						scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
				scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
				SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
						scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

				scheduleCommonPage.navigateToNextWeek();
				scheduleCommonPage.navigateToNextWeek();
				int i = Math.round((value4-value3)/7) + 1;
				scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
				for (int j = 0; j < i + 1; j++) {
					newShiftPage.clickOnDayViewAddNewShiftButton();
					newShiftPage.selectWorkRole(workRoleOfTM1);
					newShiftPage.clearAllSelectedDays();
					Random r = new Random();
					int index = r.nextInt(6); // 生成[0,6]区间的整数
					newShiftPage.selectDaysByIndex(index, index, index);
					//create shifts has 7 hours.
					newShiftPage.moveSliderAtCertainPoint("1", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
					newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
					newShiftPage.clickOnCreateOrNextBtn();
				}
			}
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			scheduleCommonPage.navigateToNextWeek();
			createSchedulePage.clickCreateScheduleBtn();
			createSchedulePage.clickNextBtnOnCreateScheduleWindow();

			createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo3, false);
			createSchedulePage.verifyTooltipForCopyScheduleWeek(pastWeekInfo3);
			if (value4>=value2){
				createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo2,  true);
			} else {
				createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo2,  false);
			}
			if (value4>=value2){
				createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, true);
			} else {
				createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, false);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify turn off the Schedule Copy Restriction")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTurnOffCopyRestrictionAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("no");


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			//Make current week 3 or more violation
			String pastWeekInfo1 = scheduleCommonPage.getActiveWeekText().substring(10);
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			String workRoleOfTM1 = shiftInfo.get(4);
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.selectWorkRole(workRoleOfTM1);
			newShiftPage.clearAllSelectedDays();
			newShiftPage.selectDaysByIndex(1, 2, 3);
			newShiftPage.moveSliderAtSomePoint("8", 12, ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(firstNameOfTM1);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			Random r = new Random();
			int index = r.nextInt(10);
			List<String> randomShiftInfoFromCopiedWeek = scheduleShiftTablePage.getTheShiftInfoByIndex(index);

			//Go to next week to check copy restriction.
			scheduleCommonPage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.clickCreateScheduleBtn();
			createSchedulePage.clickNextBtnOnCreateScheduleWindow();
			pastWeekInfo1 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo1);
			createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, true);
			createSchedulePage.selectWhichWeekToCopyFrom(pastWeekInfo1);
			createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
			scheduleShiftTablePage.verifyDayHasShifts("Sunday");
			scheduleShiftTablePage.verifyDayHasShifts("Monday");
			scheduleShiftTablePage.verifyDayHasShifts("Tuesday");
			scheduleShiftTablePage.verifyDayHasShifts("Wednesday");
			scheduleShiftTablePage.verifyDayHasShifts("Thursday");
			scheduleShiftTablePage.verifyDayHasShifts("Friday");
			scheduleShiftTablePage.verifyDayHasShifts("Saturday");
			List<String> sameIndexShiftInfoFromThisWeek = scheduleShiftTablePage.getTheShiftInfoByIndex(index);

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
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify turn off the Schedule Copy Restriction")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTurnOnCopyRestrictionAndCheckCopyResultAsInternalAdmin(String browser, String username, String password, String location) {
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();

			controlsPage.gotoControlsPage();
			controlsPage.clickGlobalSettings();

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.enableOrDisableScheduleCopyRestriction("yes");
			controlsNewUIPage.setViolationLimit("2");
			controlsNewUIPage.setBudgetOverageLimit("0");


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			String pastWeekInfo1 = scheduleCommonPage.getActiveWeekText().substring(10);
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			List<String> shiftInfo = new ArrayList<>();
			while (shiftInfo.size() == 0) {
				shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
			}
			String firstNameOfTM1 = shiftInfo.get(0);
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView(firstNameOfTM1);
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();

			//Go to next week.
			scheduleCommonPage.navigateToNextWeek();
			// Ungenerate the schedule if it has generated
			isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.clickCreateScheduleBtn();
			createSchedulePage.editOperatingHoursWithGivingPrameters("Sunday", "10:00AM", "9:00PM");
			createSchedulePage.clickNextBtnOnCreateScheduleWindow();
			pastWeekInfo1 = scheduleCommonPage.convertDateStringFormat(pastWeekInfo1);
			createSchedulePage.verifyPreviousWeekWhenCreateAndCopySchedule(pastWeekInfo1, true);
			createSchedulePage.selectWhichWeekToCopyFrom(pastWeekInfo1);
			createSchedulePage.verifyDifferentOperatingHours(pastWeekInfo1);
			createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();
			List<String> shiftsInfos = scheduleShiftTablePage.getDayShifts("0");
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
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify internal admin can see employee home location on shift menu")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyCanSeeEmployeeHomeLocationAsInternalAdmin(String browser, String username, String password, String location) {
		try {

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("unassigned");
			shiftOperatePage.deleteTMShiftInWeekView("open");
			scheduleMainPage.saveSchedule();

			//Click on one of the profile icons to get the home location and related info.
			shiftOperatePage.clickOnProfileIcon();
			Map<String, String> workerInfo = scheduleShiftTablePage.getHomeLocationInfo();

			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.searchAndSelectTeamMemberByName(workerInfo.get("worker name"));

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			ArrayList<String> badges = profileNewUIPage.getUserBadgesDetailsFromProfilePage();
			profileNewUIPage.verifyHRProfileInformationSectionIsLoaded();
			Map<String, String> result = profileNewUIPage.getHRProfileInfo();

			SimpleUtils.assertOnFail("Employment status is not consistent!", result.get("EMPLOYMENT STATUS").contains(workerInfo.get("PTorFT").substring(0,1)), false);
			SimpleUtils.assertOnFail("Home location is not consistent!", result.get("HOME STORE").contains(workerInfo.get("homeLocation").substring(0,1)), false);
			SimpleUtils.assertOnFail("Badge info is not consistent!", workerInfo.get("badgeSum").contains(String.valueOf(badges.size())), false);
			SimpleUtils.assertOnFail("Job title is not consistent!", result.get("JOB TITLE").contains(workerInfo.get("job title").substring(0,1)), false);

		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify store manager can see employee home location on shift menu")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyCanSeeEmployeeHomeLocationAsStoreManager(String browser, String username, String password, String location) {
		try {

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("unassigned");
			shiftOperatePage.deleteTMShiftInWeekView("open");
			scheduleMainPage.saveSchedule();

			//Click on one of the profile icons to get the home location and related info.
			shiftOperatePage.clickOnProfileIcon();
			Map<String, String> workerInfo = scheduleShiftTablePage.getHomeLocationInfo();

			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.searchAndSelectTeamMemberByName(workerInfo.get("worker name"));

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			ArrayList<String> badges = profileNewUIPage.getUserBadgesDetailsFromProfilePage();
			profileNewUIPage.verifyHRProfileInformationSectionIsLoaded();
			Map<String, String> result = profileNewUIPage.getHRProfileInfo();

			SimpleUtils.assertOnFail("Employment status is not consistent!", result.get("EMPLOYMENT STATUS").contains(workerInfo.get("PTorFT").substring(0,1)), false);
			SimpleUtils.assertOnFail("Home location is not consistent!", result.get("HOME STORE").contains(workerInfo.get("homeLocation").substring(0,1)), false);
			SimpleUtils.assertOnFail("Badge info is not consistent!", workerInfo.get("badgeSum").contains(String.valueOf(badges.size())), false);
			SimpleUtils.assertOnFail("Job title is not consistent!", result.get("JOB TITLE").contains(workerInfo.get("job title").substring(0,1)), false);

		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify area manager can see employee home location on shift menu")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyCanSeeEmployeeHomeLocationAsDistrictManager(String browser, String username, String password, String location) {
		try {

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("unassigned");
			shiftOperatePage.deleteTMShiftInWeekView("open");
			scheduleMainPage.saveSchedule();

			//Click on one of the profile icons to get the home location and related info.
			shiftOperatePage.clickOnProfileIcon();
			Map<String, String> workerInfo = scheduleShiftTablePage.getHomeLocationInfo();

			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			teamPage.goToTeam();
			teamPage.searchAndSelectTeamMemberByName(workerInfo.get("worker name"));

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			ArrayList<String> badges = profileNewUIPage.getUserBadgesDetailsFromProfilePage();
			profileNewUIPage.verifyHRProfileInformationSectionIsLoaded();
			Map<String, String> result = profileNewUIPage.getHRProfileInfo();

			SimpleUtils.assertOnFail("Employment status is not consistent!", result.get("EMPLOYMENT STATUS").contains(workerInfo.get("PTorFT").substring(0,1)), false);
			SimpleUtils.assertOnFail("Home location is not consistent!", result.get("HOME STORE").contains(workerInfo.get("homeLocation").substring(0,1)), false);
			SimpleUtils.assertOnFail("Badge info is not consistent!", workerInfo.get("badgeSum").contains(String.valueOf(badges.size())), false);
			SimpleUtils.assertOnFail("Job title is not consistent!", result.get("JOB TITLE").contains(workerInfo.get("job title").substring(0,1)), false);

		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Group by Day Parts can be collapsed/expanded")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyGroupByDayPartsCanBeCollapsedNExpandedAsStoreManager(String browser, String username, String password, String location) {
		try {
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyDayParts.getValue());
			scheduleShiftTablePage.verifyGroupCanbeCollapsedNExpanded();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Group by Work Role can be collapsed/expanded")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyGroupByWorkRoleCanBeCollapsedNExpandedAsStoreManager(String browser, String username, String password, String location) throws Exception{
		try {
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
			scheduleShiftTablePage.verifyGroupCanbeCollapsedNExpanded();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Group by Job Title can be collapsed/expanded")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyGroupByJobTitleCanBeCollapsedNExpandedAsStoreManager(String browser, String username, String password, String location) throws Exception{
		try {
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
			scheduleShiftTablePage.verifyGroupCanbeCollapsedNExpanded();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify Group by Location can be collapsed/expanded")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyGroupByLocationCanBeCollapsedNExpandedAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		try {
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			locationSelectorPage.searchSpecificUpperFieldAndNavigateTo(getCrendentialInfo("LGInfo"));
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}
			scheduleMainPage.selectGroupByFilter(GroupByDayPartsTest.scheduleGroupByFilterOptions.groupbyLocation.getValue());
			scheduleShiftTablePage.verifyGroupCanbeCollapsedNExpanded();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Validate there is option to edit notes for assigned shifts")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyEditNotesOptionIsAvailableAsInternalAdmin(String browser, String username, String password, String location) throws Exception{

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		int i = 0;
		int index = 0;
		List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(i);
		while (shiftInfo.size() == 0 && shiftInfo.get(0).toLowerCase().contains("open")) {
			i++;
			if (i>10 && i>= scheduleShiftTablePage.getShiftsCount()){
				break;
			}
			shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(i);
			index = i;
		}
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		String shiftInfoOnDialog = shiftOperatePage.getShiftInfoInEditShiftDialog();
		//add shift notes.
		shiftOperatePage.addShiftNotesToTextarea("new shift notes");
		scheduleMainPage.saveSchedule();
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		shiftOperatePage.verifyShiftNotesContent("new shift notes");
		if (shiftInfoOnDialog!=null && !shiftInfoOnDialog.equalsIgnoreCase("") && shiftInfoOnDialog.contains(shiftInfo.get(2))&& shiftInfoOnDialog.contains(shiftInfo.get(3))
				&& shiftInfoOnDialog.contains(shiftInfo.get(0))){
			SimpleUtils.pass("Shift info is consistent!");
		} else {
			SimpleUtils.fail("Shift info on edit shift notes dialog is not correct!", false);
		}

	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Validate there is option to edit notes for open shifts")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyEditNotesOptionIsAvailableForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		String workRole = shiftOperatePage.getRandomWorkRole();
		//create an open shifts.
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		newShiftPage.moveSliderAtCertainPoint("8","8");
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("open");
		int index = 0;
		List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		String shiftInfoOnDialog = shiftOperatePage.getShiftInfoInEditShiftDialog();
		//add shift notes.
		shiftOperatePage.addShiftNotesToTextarea("new shift notes for open shift");
		scheduleMainPage.saveSchedule();
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(0);
		shiftOperatePage.clickOnEditShiftNotesOption();
		shiftOperatePage.verifyShiftNotesContent("new shift notes for open shift");
		if (shiftInfoOnDialog!=null && !shiftInfoOnDialog.equalsIgnoreCase("") && shiftInfoOnDialog.contains(shiftInfo.get(2))&& shiftInfoOnDialog.contains(shiftInfo.get(3))){
			SimpleUtils.pass("Shift info is consistent!");
		} else {
			SimpleUtils.fail("Shift info on edit shift notes dialog is not correct!", false);
		}

	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "validate update notes for assigned shifts")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyUpdateShiftNotesForAssignedShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		int i = 0;
		int index = 0;
		List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(i);
		while (shiftInfo.size() == 0 && shiftInfo.get(0).toLowerCase().contains("open")) {
			i++;
			if (i>10 && i>= scheduleShiftTablePage.getShiftsCount()){
				break;
			}
			shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(i);
			index = i;
		}
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		//add shift notes.
		shiftOperatePage.addShiftNotesToTextarea("shift notes version1");
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		//update shift notes.
		shiftOperatePage.addShiftNotesToTextarea("shift notes version2");
		scheduleMainPage.saveSchedule();
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		shiftOperatePage.verifyShiftNotesContent("shift notes version2");
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "validate update notes for open shifts")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyUpdateEditNotesForOpenShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception{

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		String workRole = shiftOperatePage.getRandomWorkRole();
		//create an open shifts.
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.moveSliderAtCertainPoint("8","8");
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.OpenShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("open");
		int index = 0;
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		//add shift notes.
		shiftOperatePage.addShiftNotesToTextarea("shift notes version1");
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		//update shift notes.
		shiftOperatePage.addShiftNotesToTextarea("shift notes version2");
		scheduleMainPage.saveSchedule();
		scheduleShiftTablePage.clickProfileIconOfShiftByIndex(index);
		shiftOperatePage.clickOnEditShiftNotesOption();
		shiftOperatePage.verifyShiftNotesContent("shift notes version2");
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Validate the those employees scheduled in other locations show up when group by TM")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyEmployeesScheduledInOtherLocationShowUpInGroupByTMAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		String nyLocation = getCrendentialInfo("NearByLocationInfo");
		locationSelectorPage.changeLocation(nyLocation);
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.saveSchedule();
		createSchedulePage.publishActiveSchedule();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Assigned");
		List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
		String employeeName = shiftInfo.get(0);

		//change location back.
		locationSelectorPage.changeLocation(location);
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		shiftOperatePage.deleteTMShiftInWeekView(employeeName);
		String workRole = shiftOperatePage.getRandomWorkRole();
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.moveSliderAtCertainPoint("8","8");
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		newShiftPage.searchTeamMemberByNameNLocation(employeeName, nyLocation);
		newShiftPage.clickOnOfferOrAssignBtn();
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnOpenSearchBoxButton();
		scheduleMainPage.searchShiftOnSchedulePage(employeeName);
		List<String> shiftInfo2 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);

		//change to nearby location.
		locationSelectorPage.changeLocation(nyLocation);
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
		scheduleMainPage.clickOnOpenSearchBoxButton();
		scheduleMainPage.searchShiftOnSchedulePage(employeeName);
		List<String> shiftInfo3 = scheduleShiftTablePage.getTheGreyedShiftInfoByIndex(0);
		shiftInfo2.remove(7);
		shiftInfo3.remove(7);
		shiftInfo2.remove(4);
		shiftInfo3.remove(4);
		SimpleUtils.assertOnFail("ShiftInfo should be consistent!", shiftInfo2.containsAll(shiftInfo3), false);
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Validate the those employees scheduled come from other locations show up when group by TM")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyEmployeesFromOtherLocationShowUpInGroupByTMAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		String nyLocation = getCrendentialInfo("NearByLocationInfo");
		locationSelectorPage.changeLocation(nyLocation);
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.saveSchedule();
		createSchedulePage.publishActiveSchedule();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Assigned");
		List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
		String employeeName = shiftInfo.get(0);

		//change location back.
		locationSelectorPage.changeLocation(location);
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		shiftOperatePage.deleteTMShiftInWeekView(employeeName);
		String workRole = shiftOperatePage.getRandomWorkRole();
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		newShiftPage.moveSliderAtCertainPoint("8","8");
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		//newShiftPage.searchTeamMemberByName(employeeName);
		newShiftPage.searchTeamMemberByNameNLocation(employeeName, nyLocation);
		newShiftPage.clickOnOfferOrAssignBtn();
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnOpenSearchBoxButton();
		scheduleMainPage.searchShiftOnSchedulePage(employeeName);
		List<String> shiftInfo2 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);

		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
		List<String> shiftInfo3 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
		SimpleUtils.assertOnFail("Total hrs info should be consistent!", shiftInfo3.get(shiftInfo3.size()-1).contains(scheduleShiftTablePage.getTotalHrsFromRightStripCellByIndex(0)), false);
		SimpleUtils.assertOnFail("ShiftInfo should be consistent!", shiftInfo2.containsAll(shiftInfo3), false);
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Validate location info in the profile popup for those shifts that are assigned to employees from other location")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyEmployeesLocationInfoFromOtherLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		String nyLocation = getCrendentialInfo("NearByLocationInfo");
		locationSelectorPage.changeLocation(nyLocation);
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.saveSchedule();
		createSchedulePage.publishActiveSchedule();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Assigned");
		List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
		String employeeName = shiftInfo.get(0);

		//change location back.
		locationSelectorPage.changeLocation(location);
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		shiftOperatePage.deleteTMShiftInWeekView(employeeName);
		String workRole = shiftOperatePage.getRandomWorkRole();
		//create an open shifts.
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		newShiftPage.moveSliderAtCertainPoint("8","8");
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		//newShiftPage.searchTeamMemberByName(employeeName);
		newShiftPage.searchTeamMemberByNameNLocation(employeeName, nyLocation);
		newShiftPage.clickOnOfferOrAssignBtn();
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnOpenSearchBoxButton();
		scheduleMainPage.searchShiftOnSchedulePage(employeeName);

		shiftOperatePage.clickOnProfileIcon();
		Map <String, String> locationInfo = scheduleShiftTablePage.getHomeLocationInfo();
		SimpleUtils.assertOnFail("Home location info is incorrect!", nyLocation.contains(locationInfo.get("homeLocation").replace("...", "")), false);

		//change to nearby location.
		locationSelectorPage.changeLocation(nyLocation);
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
		scheduleMainPage.clickOnOpenSearchBoxButton();
		scheduleMainPage.searchShiftOnSchedulePage(employeeName);
		List<String> shiftInfo3 = scheduleShiftTablePage.getTheGreyedShiftInfoByIndex(0);
		SimpleUtils.assertOnFail("Work location info is incorrect!", shiftInfo3.get(4).contains(location), false);
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Validate the total hours in the 'information' popup")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheTotalHrsOfEmployeesScheduledInOtherLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
		String nyLocation = getCrendentialInfo("NearByLocationInfo");
		locationSelectorPage.changeLocation(nyLocation);
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.saveSchedule();
		createSchedulePage.publishActiveSchedule();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Assigned");
		List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(0);
		String employeeName = shiftInfo.get(0);

		//change location back.
		locationSelectorPage.changeLocation(location);
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//edit schedule
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.selectShiftTypeFilterByText("Action Required");
		shiftOperatePage.deleteTMShiftInWeekView("");
		scheduleMainPage.clickOnFilterBtn();
		scheduleMainPage.clickOnClearFilterOnFilterDropdownPopup();
		shiftOperatePage.deleteTMShiftInWeekView(employeeName);
		String workRole = shiftOperatePage.getRandomWorkRole();
		//create an open shifts.
		newShiftPage.clickOnDayViewAddNewShiftButton();
		newShiftPage.customizeNewShiftPage();
		//newShiftPage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"), ScheduleTestKendraScott2.sliderShiftCount.SliderShiftEndTimeCount.getValue(), ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
		newShiftPage.moveSliderAtCertainPoint("8","8");
		newShiftPage.selectWorkRole(workRole);
		newShiftPage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
		newShiftPage.clickOnCreateOrNextBtn();
		//newShiftPage.searchTeamMemberByName(employeeName);
		newShiftPage.searchTeamMemberByNameNLocation(employeeName, nyLocation);
		newShiftPage.clickOnOfferOrAssignBtn();
		scheduleMainPage.saveSchedule();
		scheduleMainPage.clickOnOpenSearchBoxButton();
		scheduleMainPage.searchShiftOnSchedulePage(employeeName);
		List<String> shiftInfo2 = scheduleShiftTablePage.getTheShiftInfoByIndex(0);

		//change to nearby location.
		locationSelectorPage.changeLocation(nyLocation);
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		scheduleCommonPage.navigateToNextWeek();
		isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
		scheduleMainPage.clickOnOpenSearchBoxButton();
		scheduleMainPage.searchShiftOnSchedulePage(employeeName);
		List<String> shiftInfo3 = scheduleShiftTablePage.getTheGreyedShiftInfoByIndex(0);
		SimpleUtils.assertOnFail("Total hrs info should be consistent!", shiftInfo3.get(shiftInfo3.size()-1).contains(scheduleShiftTablePage.getTotalHrsFromRightStripCellByIndex(0)), false);
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "validate group by XXX results order")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyGroupByResultsOrderedAlphabetilyAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//Group by work role and check the results.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesOrder();
		//Group by job title and check the results.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesOrder();
		//Group by TM and check the results.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
		scheduleShiftTablePage.verifyGroupByTMOrderResults();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "validate group by all and group by day parts results order")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyGroupByAllResultsOrderedByStartTimeAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//Group by work role and check the results.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyAll.getValue());
		scheduleShiftTablePage.verifyShiftsOrderByStartTime();
		//Group by day parts and check the results.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.getValue());
		scheduleShiftTablePage.expandOnlyOneGroup("UNSPECIFIED");
		scheduleShiftTablePage.verifyShiftsOrderByStartTime();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Haya")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Verify automatically expand when clicking group by on regular location")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAutomaticallyExpandWhenGroupByInRegularLocationAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (!isWeekGenerated) {
			createSchedulePage.createScheduleForNonDGFlowNewUI();
		}
		//Group by work role and check the group.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
		//Group by job title and check the group.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
		//Group by day parts and check the group.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();

		//Edit-mode
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
		//Group by work role and check the group.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
		//Group by job title and check the group.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyJobTitle.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
		//Group by day parts and check the group.
		scheduleMainPage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyDayParts.getValue());
		scheduleShiftTablePage.verifyGroupByTitlesAreExpanded();
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Julie")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify assign TM warning: If SM wants to schedule a TM from another location and schedule hasn’t been generated")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyAssignTMMessageWhenScheduleTMFromAnotherLocationWhereScheduleNotBeenGeneratedAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
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

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			// Navigate to a week
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();

			// Ungenerate the schedule if it has generated
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}

			// Select AUSTIN DOWNTOWN location
			dashboardPage.navigateToDashboard();
			locationSelectorPage.changeLocation("AUSTIN DOWNTOWN");
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Go to Schedule page, Schedule tab
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			// Navigate to a week
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();

			// Create the schedule if it is not created
			boolean isWeekGenerated2 = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated2){
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}

			// Edit the Schedule
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			String workRole = shiftOperatePage.getRandomWorkRole();

			// Create new shift for TM
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();

			// Search TM and verify the message
			newShiftPage.searchText(firstName + " " + lastName.substring(0,1));
			if (shiftOperatePage.getTheMessageOfTMScheduledStatus().equalsIgnoreCase("Schedule Not Created"))
				SimpleUtils.pass("TM scheduled status message display correctly");
			else
				SimpleUtils.fail("TM scheduled status message failed to display or displays incorrectly",true);

			// Select the team member and verify the pop-up warning message
			newShiftPage.searchTeamMemberByName(firstName + " " + lastName.substring(0,1));
			String expectedMessage = firstName + " cannot be assigned because the schedule has not been published yet at the home location, " + nyLocation;
			shiftOperatePage.verifyAlertMessageIsExpected(expectedMessage);

			// Click on OK button and verify that TM is not selected
			scheduleShiftTablePage.clickOnOkButtonInWarningMode();
			shiftOperatePage.verifyTMNotSelected();
			newShiftPage.closeCustomizeNewShiftWindow();
			scheduleMainPage.saveSchedule();

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
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();

			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
			scheduleCommonPage.navigateToNextWeek();
			scheduleCommonPage.navigateToNextWeek();
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			Thread.sleep(5000);
			createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "08:00PM");
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			List<String> allOperatingHrsOnCreateShiftPage = newShiftPage.getAllOperatingHrsOnCreateShiftPage();
			SimpleUtils.assertOnFail("The operating hours on create shift page display incorrectly! ",
					allOperatingHrsOnCreateShiftPage.get(0).equalsIgnoreCase("6am")
							&& allOperatingHrsOnCreateShiftPage.get(allOperatingHrsOnCreateShiftPage.size()-1).equalsIgnoreCase("11pm"),false);
			newShiftPage.clickOnCloseButtonOnCustomizeShiftPage();

			shiftOperatePage.clickOnProfileIcon();
			shiftOperatePage.clickOnEditShiftTime();
			shiftOperatePage.verifyEditShiftTimePopUpDisplay();
			List<String> startAndEndHrsOnEditShiftPage = shiftOperatePage.getStartAndEndOperatingHrsOnEditShiftPage();
			SimpleUtils.assertOnFail("The operating hours on create shift page display incorrectly! ",
					startAndEndHrsOnEditShiftPage.get(0).equalsIgnoreCase("6")
							&& startAndEndHrsOnEditShiftPage.get(1).equalsIgnoreCase("11"),false);
			shiftOperatePage.clickOnCancelEditShiftTimeButton();
			scheduleMainPage.clickOnCancelButtonOnEditMode();

			scheduleCommonPage.clickOnDayView();
			List<String> gridHeaderTimes = new ArrayList();
			gridHeaderTimes = scheduleShiftTablePage.getScheduleDayViewGridTimeDuration();
			SimpleUtils.assertOnFail("The grid header time should start as 6 AM, the actual time is: " +
					gridHeaderTimes.get(0), gridHeaderTimes.get(0).contains("6 AM"), false);
			SimpleUtils.assertOnFail("The grid header time should end with 10 PM, the actual time is: " +
					gridHeaderTimes.get(gridHeaderTimes.size() - 1), gridHeaderTimes.get(gridHeaderTimes.size() - 1).contains("10 PM"), false);
		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(),false);
		}
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	//@Enterprise(name = "KendraScott2_Enterprise")
    @Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Validate search bar on schedule page in day view")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifySearchBarOnSchedulePageInDayViewAsInternalAdmin (String browser, String username, String password, String location) throws Exception {
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
		ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
		SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);


		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
		scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
				scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);
		scheduleCommonPage.navigateToNextWeek();
		boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
		if (isWeekGenerated) {
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUI();

		//Go to day view
		scheduleCommonPage.clickOnDayView();

		//click search button
		scheduleMainPage.clickOnOpenSearchBoxButton();

		//Check the ghost text inside the Search bar
		scheduleMainPage.verifyGhostTextInSearchBox();

		//Get the info of a random shift
		List<String> shiftInfo = new ArrayList<>();
		String firstNameOfTM = "";
		int i = 0;
		while (i < 50 && (firstNameOfTM.equals("") || firstNameOfTM.equalsIgnoreCase("Open")
				|| firstNameOfTM.equalsIgnoreCase("Unassigned"))) {
			shiftInfo = scheduleShiftTablePage.getTheShiftInfoInDayViewByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
			//Search shift by TM names: first name and last name
			firstNameOfTM = shiftInfo.get(0);
			i++;
		}
		List<WebElement> searchResultOfFirstName = scheduleMainPage.searchShiftOnSchedulePage(firstNameOfTM);
		scheduleShiftTablePage.verifySearchResult(firstNameOfTM, null, null, null, searchResultOfFirstName);

		String lastNameOfTM = shiftInfo.get(5);
		List<WebElement> searchResultOfLastName = scheduleMainPage.searchShiftOnSchedulePage(lastNameOfTM);
		scheduleShiftTablePage.verifySearchResult(null, lastNameOfTM, null, null, searchResultOfLastName);

		//Search shift by work role
		String workRole = shiftInfo.get(4);
		List<WebElement> searchResultOfWorkRole = scheduleMainPage.searchShiftOnSchedulePage(workRole);
		scheduleShiftTablePage.verifySearchResult(null, null, workRole, null, searchResultOfWorkRole);

		//Search shift by job title
		String jobTitle = shiftInfo.get(3);
		List<WebElement> searchResultOfJobTitle = scheduleMainPage.searchShiftOnSchedulePage(jobTitle);
		scheduleShiftTablePage.verifySearchResult(null, null, null, jobTitle, searchResultOfJobTitle);

		//Click X button to close search box
		scheduleMainPage.clickOnCloseSearchBoxButton();

		//Go to edit mode
		scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

		//click search button
		scheduleMainPage.clickOnOpenSearchBoxButton();
		//Click X button to close search box
		scheduleMainPage.clickOnCloseSearchBoxButton();
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the first name and last name are all display on Search TM , Recommended TMs and View profile page")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTMFullNameDisplayOnSearchTMRecommendedAndViewProfilePageAsInternalAdmin(String username, String password, String browser, String location) {
		try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			controlsNewUIPage.clickOnGlobalLocationButton();
			OpsPortalConfigurationPage opsPortalConfigurationPage = (OpsPortalConfigurationPage) pageFactory.createOpsPortalConfigurationPage();
			opsPortalConfigurationPage.setWFS("No");

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			scheduleCommonPage.navigateToNextWeek();

			boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
			if (isActiveWeekGenerated) {
				createSchedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			createSchedulePage.createScheduleForNonDGFlowNewUI();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();

			//Select new TM from Search Team Member tab
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.selectWorkRole(controlWorkRole);
			newShiftPage.moveSliderAtCertainPoint("2pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.moveSliderAtCertainPoint("11am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			String nameOfSelectedTM = newShiftPage.selectTeamMembers();
			newShiftPage.clickOnOfferOrAssignBtn();

			//Select new TM from Recommended TMs tab
			String nameOfSelectedTM2 = "";
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.selectWorkRole(controlWorkRole);
			newShiftPage.moveSliderAtCertainPoint("2pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.moveSliderAtCertainPoint("11am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			shiftOperatePage.switchSearchTMAndRecommendedTMsTab();
			nameOfSelectedTM2 = newShiftPage.selectTeamMembers();
			newShiftPage.clickOnOfferOrAssignBtn();

			//Get TM full name from view profile page
			List<String> shiftInfo = scheduleShiftTablePage.getTheShiftInfoByIndex(scheduleShiftTablePage.getRandomIndexOfShift());
			String nameOfSelectedTM3 = shiftInfo.get(0) +" " + shiftInfo.get(5);

			scheduleMainPage.saveSchedule();
			Thread.sleep(3000);
			TeamPage teamPage = pageFactory.createConsoleTeamPage();
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();

			teamPage.goToTeam();
			teamPage.searchAndSelectTeamMemberByName(nameOfSelectedTM);
			String nameOnProfilePage = profileNewUIPage.getUserProfileName().get("fullName");
			SimpleUtils.assertOnFail("Name on profile page display incorrectly! The expected is: "+ nameOfSelectedTM +
					", The actual is: " + nameOnProfilePage, nameOfSelectedTM.equalsIgnoreCase(nameOnProfilePage), false);

			teamPage.goToTeam();
			teamPage.searchAndSelectTeamMemberByName(nameOfSelectedTM2);
			nameOnProfilePage = profileNewUIPage.getUserProfileName().get("fullName");
			SimpleUtils.assertOnFail("Name on profile page display incorrectly! The expected is: "+ nameOfSelectedTM2 +
					", The actual is: " + nameOnProfilePage, nameOfSelectedTM2.equalsIgnoreCase(nameOnProfilePage), false);

			teamPage.goToTeam();
			teamPage.searchAndSelectTeamMemberByName(nameOfSelectedTM3);
			nameOnProfilePage = profileNewUIPage.getUserProfileName().get("fullName");
			SimpleUtils.assertOnFail("Name on profile page display incorrectly! The expected is: "+ nameOfSelectedTM3 +
					", The actual is: " + nameOnProfilePage, nameOfSelectedTM3.equalsIgnoreCase(nameOnProfilePage), false);

			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			controlsNewUIPage.clickOnGlobalLocationButton();
			opsPortalConfigurationPage.setWFS("Yes");
		} catch (Exception e) {
		SimpleUtils.fail(e.getMessage(), false);
		}
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "Vailqacn_Enterprise")
//	@Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Verify Action Required smart card display correctly when the schedule created by copy partial")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyActionRequiredSmartCardDisplayCorrectlyWhenScheduleCreatedByCopyPartialAsInternalAdmin(String browser, String username, String password, String location) throws Exception
	{
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

		ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
		CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
		SmartCardPage smartCardPage = pageFactory.createSmartCardPage();
		ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
		scheduleCommonPage.clickOnScheduleConsoleMenuItem();
		scheduleCommonPage.clickOnScheduleSubTab("Schedule");
		scheduleCommonPage.navigateToNextWeek();
		if (createSchedulePage.isWeekGenerated()){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.createScheduleForNonDGFlowNewUIWithGivingTimeRange("08:00AM", "05:00PM");
		shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
		createSchedulePage.publishActiveSchedule();

		String firstWeekInfo = scheduleCommonPage.getActiveWeekText();
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
		scheduleCommonPage.navigateToNextWeek();
		if (createSchedulePage.isWeekGenerated()){
			createSchedulePage.unGenerateActiveScheduleScheduleWeek();
		}
		createSchedulePage.clickCreateScheduleBtn();
		createSchedulePage.editOperatingHoursWithGivingPrameters("Sunday", "11:00AM", "05:00PM");
		createSchedulePage.editOperatingHoursWithGivingPrameters("Monday", "11:00AM", "05:00PM");
		createSchedulePage.editOperatingHoursWithGivingPrameters("Tuesday", "11:00AM", "05:00PM");
		createSchedulePage.editOperatingHoursWithGivingPrameters("Wednesday", "11:00AM", "05:00PM");
		createSchedulePage.editOperatingHoursWithGivingPrameters("Thursday", "11:00AM", "05:00PM");
		createSchedulePage.editOperatingHoursWithGivingPrameters("Friday", "11:00AM", "05:00PM");
		createSchedulePage.editOperatingHoursWithGivingPrameters("Saturday", "11:00AM", "05:00PM");
		createSchedulePage.clickNextBtnOnCreateScheduleWindow();
		createSchedulePage.selectWhichWeekToCopyFrom(firstWeekInfo);
		createSchedulePage.copyAllPartialSchedule();
		createSchedulePage.clickOnFinishButtonOnCreateSchedulePage();

		//Check the Action required smart card is display
		SimpleUtils.assertOnFail("Action Required smart card should be loaded! ",
				smartCardPage.isRequiredActionSmartCardLoaded(), false);
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Verify the employee name on shift in day and week view when enable ScheduleShowFullNames toggle")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheFullNamesOnShiftInDayAndWeekViewWhenEnableScheduleShowFullNamesToggleAsTeamMember(String username, String password, String browser, String location) throws Exception {
		try {
			ToggleAPI.enableToggle(Toggles.ScheduleShowFullNames.getValue(), "stoneman@legion.co", "admin11.a");
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			profileNewUIPage.clickOnUserProfileImage();
			profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
			String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			//Login as admin
			loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
			//Enable ScheduleShowFullNames toggle
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

			//Go to one schedule page week view
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			scheduleCommonPage.navigateToNextWeek();
			boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isActiveWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}

			//Delete all unassigned shifts and tm's shifts
			shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
			shiftOperatePage.deleteTMShiftInWeekView(tmFullName.split(" ")[0]);
			scheduleMainPage.saveSchedule();

			//Add shifts for TM
			String workRole = shiftOperatePage.getRandomWorkRole();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.clearAllSelectedDays();
			newShiftPage.selectSpecificWorkDay(1);
			newShiftPage.moveSliderAtCertainPoint("4pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.moveSliderAtCertainPoint("8am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(tmFullName);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			scheduleMainPage.clickOnOpenSearchBoxButton();
			scheduleMainPage.searchShiftOnSchedulePage(tmFullName);

			//Get employee full name in week view
			String fullNameInWeekView = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in week view, the expected full name is: "+ tmFullName +" The actual full name is: " + fullNameInWeekView,
					fullNameInWeekView.equalsIgnoreCase(tmFullName), false);

			//Get employee full name in day view
			scheduleCommonPage.clickOnDayView();
			scheduleCommonPage.navigateDayViewWithIndex(0);
		    String fullNameInDayView = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in day view, the expected full name is: "+ tmFullName +" The actual full name is: " + fullNameInDayView,
					fullNameInDayView.equalsIgnoreCase(tmFullName), false);
		    loginPage.logOut();
		    //Login as TM
			loginAsDifferentRole(AccessRoles.TeamMember.getValue());
			//Get employee full name on TM schedule view
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.navigateToNextWeek();
			String fullNameInMySchedulePage = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in my schedule page, the expected full name is: "+ tmFullName +" The actual full name is: " + fullNameInMySchedulePage,
					fullNameInMySchedulePage.equalsIgnoreCase(tmFullName), false);

			//Try to create swap request for one shift and check the shift on swap page
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			mySchedulePage.verifyClickOnAnyShift();
			String request = "Request to Swap Shift";
			String title = "Find Shifts to Swap";
			mySchedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
			String fullNameInFindShiftsToSwapPage = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in swap page, the expected full name is: "+ tmFullName +" The actual full name is: " + fullNameInFindShiftsToSwapPage,
					fullNameInFindShiftsToSwapPage.equalsIgnoreCase(tmFullName), false);

			//Try to create cover request for one shift and check the shift on cover page
			mySchedulePage.clickCloseDialogButton();
			Thread.sleep(3000);
			mySchedulePage.verifyClickOnAnyShift();
			request = "Request to Cover Shift";
			title = "Submit Cover Request";
			mySchedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
			String fullNameInFindShiftsToCoverPage = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in cover page, the expected full name is: "+ tmFullName +" The actual full name is: " + fullNameInFindShiftsToCoverPage,
					fullNameInFindShiftsToCoverPage.equalsIgnoreCase(tmFullName), false);
			mySchedulePage.clickCloseDialogButton();

			String subTitle = "Team Schedule";
			mySchedulePage.gotoScheduleSubTabByText(subTitle);
			int indexInWeekView = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName.split(" ")[0]).get(0);
			//Get employee full name in week
			fullNameInWeekView = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(indexInWeekView);
			SimpleUtils.assertOnFail("The full name display incorrectly in team schedule page, the expected full name is: "+ tmFullName +" The actual full name is: " + fullNameInWeekView,
					fullNameInWeekView.equalsIgnoreCase(tmFullName), false);

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Mary")
	@Enterprise(name = "Vailqacn_Enterprise")
//    @Enterprise(name = "CinemarkWkdy_Enterprise")
	@TestName(description = "Verify the employee name on shift in day and week view when disable ScheduleShowFullNames toggle")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyTheFullNamesOnShiftInDayAndWeekViewWhenDisableScheduleShowFullNamesToggleAsTeamMember(String username, String password, String browser, String location) throws Exception {
		try {
			ToggleAPI.disableToggle(Toggles.ScheduleShowFullNames.getValue(), "stoneman@legion.co", "admin11.a");
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			profileNewUIPage.clickOnUserProfileImage();
			profileNewUIPage.selectProfileSubPageByLabelOnProfileImage("My Profile");
			String tmFullName = profileNewUIPage.getUserProfileName().get("fullName");
			String firstAndInitialSecondName = tmFullName.split(" ")[0] + " " + tmFullName.split(" ")[1].substring(0, 1) + ".";
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			//Login as admin
			loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
			//Enable ScheduleShowFullNames toggle
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

			//Go to one schedule page week view
			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), true);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			scheduleCommonPage.navigateToNextWeek();
			boolean isActiveWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isActiveWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}

			//Delete all unassigned shifts and tm's shifts
			shiftOperatePage.convertAllUnAssignedShiftToOpenShift();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			shiftOperatePage.deleteTMShiftInWeekView("Unassigned");
			shiftOperatePage.deleteTMShiftInWeekView(tmFullName.split(" ")[0]);
			scheduleMainPage.saveSchedule();

			//Add shifts for TM
			String workRole = shiftOperatePage.getRandomWorkRole();
			scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			newShiftPage.clickOnDayViewAddNewShiftButton();
			newShiftPage.customizeNewShiftPage();
			newShiftPage.clearAllSelectedDays();
			newShiftPage.selectSpecificWorkDay(1);
			newShiftPage.moveSliderAtCertainPoint("4pm", ScheduleTestKendraScott2.shiftSliderDroppable.EndPoint.getValue());
			newShiftPage.moveSliderAtCertainPoint("8am", ScheduleTestKendraScott2.shiftSliderDroppable.StartPoint.getValue());
			newShiftPage.selectWorkRole(workRole);
			newShiftPage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
			newShiftPage.clickOnCreateOrNextBtn();
			newShiftPage.searchTeamMemberByName(tmFullName);
			newShiftPage.clickOnOfferOrAssignBtn();
			scheduleMainPage.saveSchedule();
			createSchedulePage.publishActiveSchedule();
			scheduleMainPage.clickOnOpenSearchBoxButton();
			scheduleMainPage.searchShiftOnSchedulePage(tmFullName);

			//Get employee full name in week view
			String fullNameInWeekView = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in week view, the expected full name is: "+ firstAndInitialSecondName +" The actual full name is: " + fullNameInWeekView,
					fullNameInWeekView.equalsIgnoreCase(firstAndInitialSecondName), false);

			//Get employee full name in day view
			scheduleCommonPage.clickOnDayView();
			scheduleCommonPage.navigateDayViewWithIndex(0);
			String fullNameInDayView = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in day view, the expected full name is: "+ firstAndInitialSecondName +" The actual full name is: " + fullNameInDayView,
					fullNameInDayView.equalsIgnoreCase(firstAndInitialSecondName), false);
			loginPage.logOut();
			//Login as TM
			loginAsDifferentRole(AccessRoles.TeamMember.getValue());
			//Get employee full name on TM schedule view
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			scheduleCommonPage.navigateToNextWeek();
			String fullNameInMySchedulePage = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in my schedule page, the expected full name is: "+ firstAndInitialSecondName +" The actual full name is: " + fullNameInMySchedulePage,
					fullNameInMySchedulePage.equalsIgnoreCase(firstAndInitialSecondName), false);

			//Try to create swap request for one shift and check the shift on swap page
			MySchedulePage mySchedulePage = pageFactory.createMySchedulePage();
			mySchedulePage.verifyClickOnAnyShift();
			String request = "Request to Swap Shift";
			String title = "Find Shifts to Swap";
			mySchedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
			String fullNameInFindShiftsToSwapPage = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in swap page, the expected full name is: "+ firstAndInitialSecondName +" The actual full name is: " + fullNameInFindShiftsToSwapPage,
					fullNameInFindShiftsToSwapPage.equalsIgnoreCase(firstAndInitialSecondName), false);

			//Try to create cover request for one shift and check the shift on cover page
			mySchedulePage.clickCloseDialogButton();
			Thread.sleep(3000);
			mySchedulePage.verifyClickOnAnyShift();
			request = "Request to Cover Shift";
			title = "Submit Cover Request";
			mySchedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", mySchedulePage.isPopupWindowLoaded(title), true);
			String fullNameInFindShiftsToCoverPage = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(0);
			SimpleUtils.assertOnFail("The full name display incorrectly in cover page, the expected full name is: "+ firstAndInitialSecondName +" The actual full name is: " + fullNameInFindShiftsToCoverPage,
					fullNameInFindShiftsToCoverPage.equalsIgnoreCase(firstAndInitialSecondName), false);
			mySchedulePage.clickCloseDialogButton();

			String subTitle = "Team Schedule";
			mySchedulePage.gotoScheduleSubTabByText(subTitle);
			int indexInWeekView = scheduleShiftTablePage.getAddedShiftIndexes(tmFullName.split(" ")[0]).get(0);
			//Get employee full name in week
			fullNameInWeekView = scheduleShiftTablePage.getFullNameOfOneShiftByIndex(indexInWeekView);
			SimpleUtils.assertOnFail("The full name display incorrectly in team schedule page, the expected full name is: "+ firstAndInitialSecondName +" The actual full name is: " + fullNameInWeekView,
					fullNameInWeekView.equalsIgnoreCase(firstAndInitialSecondName), false);

		} catch (Exception e) {
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Owner(owner = "Nora")
	@Enterprise(name = "Vailqacn_Enterprise")
	@TestName(description = "Verify whole day PTO requests should be recognized in schedule")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyPTORequestInScheduleAsInternalAdmin(String browser, String username, String password, String location) throws Exception{
		try {
			ControlsPage controlsPage = pageFactory.createConsoleControlsPage();
			CreateSchedulePage createSchedulePage = pageFactory.createCreateSchedulePage();
			ScheduleMainPage scheduleMainPage = pageFactory.createScheduleMainPage();
			ShiftOperatePage shiftOperatePage = pageFactory.createShiftOperatePage();
			NewShiftPage newShiftPage = pageFactory.createNewShiftPage();
			ScheduleShiftTablePage scheduleShiftTablePage = pageFactory.createScheduleShiftTablePage();

			controlsPage.gotoControlsPage();
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			SimpleUtils.assertOnFail("Controls Page not loaded Successfully!", controlsNewUIPage.isControlsPageLoaded(), false);
			controlsNewUIPage.clickOnControlsSchedulingPolicies();
			controlsNewUIPage.clickOnSchedulingPoliciesTimeOffAdvanceBtn();
			int advancedDays = controlsNewUIPage.getDaysInAdvanceCreateTimeOff();
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			// Login as Team Member to create time off
			loginAsDifferentRole(AccessRoles.TeamMember.getValue());
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);

			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String requestUserName = profileNewUIPage.getNickNameFromProfile();
			String myTimeOffLabel = "My Time Off";
			profileNewUIPage.selectProfileSubPageByLabelOnProfileImage(myTimeOffLabel);
			profileNewUIPage.cancelAllTimeOff();
			profileNewUIPage.clickOnCreateTimeOffBtn();
			SimpleUtils.assertOnFail("New time off request window not loaded Successfully!", profileNewUIPage.isNewTimeOffWindowLoaded(), false);
			// select time off reason
			if (profileNewUIPage.isReasonLoad(ActivityTest.timeOffReasonType.FamilyEmergency.getValue())){
				profileNewUIPage.selectTimeOffReason(ActivityTest.timeOffReasonType.FamilyEmergency.getValue());
			} else if (profileNewUIPage.isReasonLoad(ActivityTest.timeOffReasonType.PersonalEmergency.getValue())){
				profileNewUIPage.selectTimeOffReason(ActivityTest.timeOffReasonType.PersonalEmergency.getValue());
			} else if (profileNewUIPage.isReasonLoad(ActivityTest.timeOffReasonType.JuryDuty.getValue())){
				profileNewUIPage.selectTimeOffReason(ActivityTest.timeOffReasonType.JuryDuty.getValue());
			} else if (profileNewUIPage.isReasonLoad(ActivityTest.timeOffReasonType.Sick.getValue())){
				profileNewUIPage.selectTimeOffReason(ActivityTest.timeOffReasonType.Sick.getValue());
			} else if (profileNewUIPage.isReasonLoad(ActivityTest.timeOffReasonType.Vacation.getValue())){
				profileNewUIPage.selectTimeOffReason(ActivityTest.timeOffReasonType.Vacation.getValue());
			}
			List<String> timeOffDates = profileNewUIPage.selectStartAndEndDate(advancedDays, 1, 1);
			profileNewUIPage.clickOnSaveTimeOffRequestBtn();
			loginPage.logOut();

			// Login as Store Manager again to check message and reject
			String RequestTimeOff = "requested";
			loginAsDifferentRole(AccessRoles.StoreManager.getValue());
			String respondUserName = profileNewUIPage.getNickNameFromProfile();
			ActivityPage activityPage = pageFactory.createConsoleActivityPage();
			activityPage.verifyClickOnActivityIcon();
			activityPage.clickActivityFilterByIndex(ActivityTest.indexOfActivityType.TimeOff.getValue(), ActivityTest.indexOfActivityType.TimeOff.name());
			activityPage.verifyTheNotificationForReqestTimeOff(requestUserName, getTimeOffStartTime(),getTimeOffEndTime(), RequestTimeOff);
			activityPage.approveOrRejectTTimeOffRequestOnActivity(requestUserName,respondUserName, ActivityTest.approveRejectAction.Approve.getValue());
			activityPage.closeActivityWindow();

			// Go to Schedule page, Schedule tab

			ScheduleCommonPage scheduleCommonPage = pageFactory.createScheduleCommonPage();
			scheduleCommonPage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Overview.getValue()), false);
			scheduleCommonPage.clickOnScheduleSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!",
					scheduleCommonPage.verifyActivatedSubTab(ScheduleTestKendraScott2.SchedulePageSubTabText.Schedule.getValue()), false);

			// Navigate to the week that contains the date that provided
			scheduleCommonPage.goToSpecificWeekByDate(timeOffDates.get(0));

			// Create schedule if it is not created
			boolean isWeekGenerated = createSchedulePage.isWeekGenerated();
			if (!isWeekGenerated) {
				createSchedulePage.createScheduleForNonDGFlowNewUI();
			}

			scheduleMainPage.clickOnOpenSearchBoxButton();
			List<WebElement> shifts = scheduleMainPage.searchShiftOnSchedulePage(requestUserName);

			if (shifts == null || (shifts != null && shifts.size() == 0)) {
				// Edit schedule to create the new shift for new TM
				scheduleMainPage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
				shiftOperatePage.deleteTMShiftInWeekView(requestUserName);
				String workRole = shiftOperatePage.getRandomWorkRole();
				newShiftPage.clickOnDayViewAddNewShiftButton();
				newShiftPage.customizeNewShiftPage();
				newShiftPage.clearAllSelectedDays();
				newShiftPage.selectDaysByCountAndCannotSelectedDate(1, timeOffDates.get(0));
				newShiftPage.selectWorkRole(workRole);
				newShiftPage.clickRadioBtnStaffingOption(ScheduleTestKendraScott2.staffingOption.AssignTeamMemberShift.getValue());
				newShiftPage.clickOnCreateOrNextBtn();
				newShiftPage.searchTeamMemberByName(firstName + " " + lastName.toString().substring(0, 1));
				newShiftPage.clickOnOfferOrAssignBtn();
				scheduleMainPage.saveSchedule();
				scheduleMainPage.searchShiftOnSchedulePage(requestUserName);
			}
			scheduleMainPage.selectGroupByFilter(ConsoleScheduleNewUIPage.scheduleGroupByFilterOptions.groupbyTM.getValue());
			// Verify Time Off card will show when group by TM
			int index = scheduleShiftTablePage.getTheIndexOfTheDayInWeekView(timeOffDates.get(0).substring(timeOffDates.get(0).length() - 2));
			scheduleShiftTablePage.verifyTimeOffCardShowInCorrectDay(index);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}
}