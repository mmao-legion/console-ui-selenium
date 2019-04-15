package com.legion.tests.core;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.pages.BasePage;
import com.legion.pages.ControlsNewUIPage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.LoginPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.test.core.mobile.LoginTest;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.annotations.UseAsTestRailId;
import com.legion.tests.core.ScheduleNewUITest.SchedulePageSubTabText;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ScheduleNewUITest extends TestBase{
	  private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	  private static HashMap<String, String> propertyCustomizeMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/ScheduleCustomizeNewShift.json");
	  private static HashMap<String, String> scheduleWorkRoles = JsonUtil.getPropertiesFromJsonFile("src/test/resources/WorkRoleOptions.json");
	  private static HashMap<String, String> propertySearchTeamMember = JsonUtil.getPropertiesFromJsonFile("src/test/resources/SearchTeamMember.json");
	  SchedulePage schedulePage = null;

	  @Override
	  @BeforeMethod
	  public void firstTest(Method method, Object[] params) throws Exception {
	      this.createDriver((String) params[0], "68", "Linux");
	      visitPage(method);
	      loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
	    }
	  

	public enum weekCount{
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
	        public int getValue() { return value; }
		}

	public enum filtersIndex{
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
        public int getValue() { return value; }
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

	public enum sliderShiftCount{
		SliderShiftStartCount(2),
		SliderShiftEndTimeCount(10);
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


	  public enum SchedulePageSubTabText{
		  Overview("OVERVIEW"),
		  ProjectedSales("PROJECTED SALES"),
		  StaffingGuidance("STAFFING GUIDANCE"),
		  Schedule("SCHEDULE"),
		  ProjectedTraffic("PROJECTED TRAFFIC");
			private final String value;
			SchedulePageSubTabText(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
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
		  groupbyTM("Group by TM");
			private final String value;
			scheduleGroupByFilterOptions(final String newValue) {
	            value = newValue;
	        }
	        public String getValue() { return value; }
		}






	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "LEG-2424: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void reviewPastGenerateCurrentAndFutureWeekScheduleAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
//	    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
	    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	    	SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	    	schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	    	schedulePage.clickOnScheduleConsoleMenuItem();
	    	schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	    	SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	    	//Schedule overview should show 5 week's schedule
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	        List<String> scheduleOverviewWeeksStatus = scheduleOverviewPage.getScheduleWeeksStatus();
	        int overviewWeeksStatusCount = scheduleOverviewWeeksStatus.size();
	        SimpleUtils.assertOnFail("Schedule overview Page not displaying upcoming 5 weeks",(overviewWeeksStatusCount == overviewTotalWeekCount) , true);
	        for( int index = 0;  index<scheduleOverviewWeeksStatus.size(); index++)
	        {
//		        SimpleUtils.assertOnFail("Schedule overview Page upcoming week on index '"+index+"' is 'Not Available'",(! overviewWeeksStatusText.contains(overviewWeeksStatus.NotAvailable.getValue())) , true);
		        SimpleUtils.report("Schedule overview Page upcoming week on index '"+index+"' is '"+scheduleOverviewWeeksStatus.get(index)+"'");
	        }
	        //Must have at least "Past Week" schedule published
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Previous.getValue(), weekCount.One.getValue());
	        SimpleUtils.assertOnFail("Schedule Page: Past week not generated!",schedulePage.isWeekGenerated() , true);
	        SimpleUtils.assertOnFail("Schedule Page: Past week not Published!",schedulePage.isWeekPublished() , true);
	        //The schedules that are already published should remain unchanged
	        schedulePage.clickOnDayView();
	        schedulePage.clickOnEditButton();
	        SimpleUtils.assertOnFail("User can add new shift for past week", (! schedulePage.isAddNewDayViewShiftButtonLoaded()) , true);
	        schedulePage.clickOnCancelButtonOnEditMode();
	        // No generate button for Past Week
	        SimpleUtils.assertOnFail("Generate Button displaying for Past week", (! schedulePage.isGenerateButtonLoaded()) , true);
	        //there are at least one week in the future where schedule has not yet been published
	        schedulePage.clickOnWeekView();
	        schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        for(int index = 1; index < weekCount.values().length; index++)
	        {
	        	schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        	if(! schedulePage.isWeekGenerated()){
        			ExtentTestManager.getTest().log(Status.INFO, "Schedule Page: Future week '"+schedulePage.getScheduleWeekStartDayMonthDate()+"' not Generated!");
	        	}
	        	else {
	        		if(! schedulePage.isWeekPublished()){
	        			ExtentTestManager.getTest().log(Status.INFO, "Schedule Page: Future week '"+schedulePage.getScheduleWeekStartDayMonthDate()+"' not Published!");
	        		}
	        	}
	        }
	    }



	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-21: Automation Script for - JIRA ID - 2592 - \"Should be able to view and filter Schedule and Group By 'All'\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByAllInWeekViewAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Week view
	         */
	        boolean isWeekView = true;
	        schedulePage.clickOnWeekView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyAll.getValue());
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnEditButton();
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnCancelButtonOnEditMode();
	    }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-21: Automation Script for - JIRA ID - 2592 - \"Should be able to view and filter Schedule and Group By 'Work Role'\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByWorkRoleInWeekViewAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Week view
	         */
	        boolean isWeekView = true;
	        schedulePage.clickOnWeekView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnEditButton();
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnCancelButtonOnEditMode();

	    }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-21: Automation Script for - JIRA ID - 2592 - \"Should be able to view and filter Schedule and Group By 'TMs'\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByTMsInWeekViewAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Week view
	         */
	        boolean isWeekView = true;
	        schedulePage.clickOnWeekView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnEditButton();
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnCancelButtonOnEditMode();
	    }

	    @UseAsTestRailId(testRailId = 32)
	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-22: Automation Script for - JIRA ID - 2592 - \"Should be able to view Day View and filter Schedule and Group By 'All'\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByAllDayViewAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        //SimpleUtils.fail("Test Failed", false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Day view
	         */
	        boolean isWeekView = false;
	        schedulePage.clickOnDayView();
	        schedulePage.navigateToNextDayIfStoreClosedForActiveDay();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyAll.getValue());
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnEditButton();
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnCancelButtonOnEditMode();
	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-21: Automation Script for - JIRA ID - 2592 - \"Should be able to view Day View and filter Schedule and Group By 'Work Role'\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByWorkRoleDayViewAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Day view
	         */
	        boolean isWeekView = false;
	        schedulePage.clickOnDayView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnEditButton();
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnCancelButtonOnEditMode();
	    }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-21: Automation Script for - JIRA ID - 2592 - \"Should be able to view Day View and filter Schedule and Group By 'TMs'\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByTMsDayViewAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Day view
	         */
	        boolean isWeekView = false;
	        schedulePage.clickOnWeekView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
	        schedulePage.clickOnDayView();
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnEditButton();
	        schedulePage.filterScheduleByWorkRoleAndShiftType(isWeekView);
	        schedulePage.clickOnCancelButtonOnEditMode();
	    }



	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-80: Should not show unpublished schedule in AV view for Team Member, Team Lead")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void verifyUnpublishedScheduleVisibilityForTMsAndTLsAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Day view
	         */
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			String fileName = "UsersCredentials.json";
	        fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
	        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
	        Object[][] teamLeadCredentials = userCredentials.get("TeamLead");
	        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");


	        /*
	         * Login as Team Lead
	         */
	        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamLeadCredentials[0][0]), String.valueOf(teamLeadCredentials[0][1]), String.valueOf(teamLeadCredentials[0][2]));
	        dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        schedulePage.clickOnWeekView();
	        for(String weekStatus : overviewPageScheduledWeekStatus)
			{
	        	boolean isSchedulePublished = schedulePage.isCurrentScheduleWeekPublished();
	        	if(weekStatus.toLowerCase().contains(overviewWeeksStatus.Draft.getValue().toLowerCase()) 
	        			|| weekStatus.toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase()))
	        		SimpleUtils.assertOnFail("UnPublished week:'"+ schedulePage.getActiveWeekText()
	        		+ "' Schedule is visible for Team Lead", ! isSchedulePublished, true);
	        	else
	        		SimpleUtils.assertOnFail("Published week:'"+ schedulePage.getActiveWeekText()
	        		+"' Schedule is not visible for Team Lead", isSchedulePublished, true);
				schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
			}
	        loginPage.logOut();

	        /*
	         * Login as Team Member
	         */
	        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]),
	        		String.valueOf(teamMemberCredentials[0][1]), String.valueOf(teamMemberCredentials[0][2]));
	        dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        schedulePage.clickOnWeekView();
	        for(String weekStatus : overviewPageScheduledWeekStatus)
			{
	        	boolean isSchedulePublished = schedulePage.isCurrentScheduleWeekPublished();
	        	if(weekStatus.toLowerCase().contains(overviewWeeksStatus.Draft.getValue().toLowerCase()) 
	        			|| weekStatus.toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase()))
	        		SimpleUtils.assertOnFail("UnPublished week:'"+ schedulePage.getActiveWeekText()
	        		+"' Schedule is visible for Team Member", ! isSchedulePublished, true);
	        	else
	        	{
	        		boolean isWeekAssignedToUser = schedulePage.isActiveWeekAssignedToCurrentUser(String.valueOf(teamMemberCredentials[0][0]));
	        		SimpleUtils.assertOnFail("Published week:'"+ schedulePage.getActiveWeekText()
	        		+"' Schedule is not visible for Team Member", isSchedulePublished, true);
	        	}
	        	schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
			}
	    }

	    /*
	     * Automation Script for - JIRA ID -LEG-2428 : "Store manager should be able to publish schedule versioned 1.0"
	     */

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-82: Should not show unpublished schedule in AV view for Team Member, Team Lead")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void verifyAndPublishScheduleAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {

	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!" ,
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.clickOnWeekView();

	        int scheduleOverViewStatusCount = overviewPageScheduledWeekStatus.size();
			for(int index = 0; index < scheduleOverViewStatusCount; index++)
			{
				String status = overviewPageScheduledWeekStatus.get(index);
				if(index != 0)
					schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

				if(status.toLowerCase().equals(overviewWeeksStatus.Draft.getValue().toLowerCase()))
				{
					SimpleUtils.report("Selected Week: '"+schedulePage.getActiveWeekText()+"'");
					/*
					 * Assertions:
					 */

					/*
					 * Total # of scheduled hours under Version 0.n Details should not be 0
					 */
					HashMap<String, Float> ScheduleWeekViewCardData = schedulePage.getScheduleLabelHoursAndWages();
					SimpleUtils.report("Schedule Hours for Selected Week: '"+
							ScheduleWeekViewCardData.get(scheduleHoursAndWagesData.scheduledHours.getValue()) +"'");
					SimpleUtils.assertOnFail("Active Unpublished Schedule contains '0' Hours.",
							! (0 == Math.round(ScheduleWeekViewCardData.get(scheduleHoursAndWagesData.scheduledHours.getValue()))),
									false);

					/*
					 * Total # of shifts under Version 0.n Details should not be 0
					 */
					SimpleUtils.report("Available Shift count for Selected Week: '"+
							schedulePage.getAllAvailableShiftsInWeekView().size() +"'");

					SimpleUtils.assertOnFail("Available Shift count for active week is '0'.",
							! (0 == schedulePage.getAllAvailableShiftsInWeekView().size()), false);


					/*
					 * Each enabled workrole should have an entry and #HRs/#Shifts can't be 0
					 */

					ArrayList<HashMap<String, String>> workRoleData = schedulePage.getHoursAndShiftsCountForEachWorkRolesInWeekView();
					for(HashMap<String, String> eachWorkRoledata : workRoleData)
					{
						SimpleUtils.report("Selected Week Schudule Hours for '"
								+ eachWorkRoledata.get("workRole") + "': '"
									+ eachWorkRoledata.get("scheduledHours")+"'");
						SimpleUtils.assertOnFail("Available Schedule Hours of WorkRole: '"+ eachWorkRoledata.get("workRole") +"' for active week is '0'.",
								! (0 == Math.round(Float.valueOf(eachWorkRoledata.get("scheduledHours")))), false);

						SimpleUtils.report("Selected Week Shift count for '"
								+ eachWorkRoledata.get("workRole") + "': '"
									+ eachWorkRoledata.get("shiftsCount")+"'");
						SimpleUtils.assertOnFail("Available Shift count of WorkRole: '"+ eachWorkRoledata.get("workRole") +"' for active week is '0'.",
								! (0 == Integer.valueOf(eachWorkRoledata.get("shiftsCount"))), false);
					}

					/*
					 * Version History should have entries from 0.0 to 0.n
					 */
					ArrayList<Float> versionHistory = schedulePage.getAllVesionLabels();
					String versionHistoryText = "";
					for(Float version : versionHistory)
					{
						if(versionHistoryText == "")
							versionHistoryText = "Version "+version;
						else
							versionHistoryText = versionHistoryText + ", " + "Version "+version;

						SimpleUtils.assertOnFail("Version History contains 'Version "+ version + " for active Unpublished Schedule.",
								(version < 1), true);
					}

					SimpleUtils.report("Selected Week Version History: '"
							+ versionHistoryText +"'");

					/*
					 * On the publish page, Scheduled hours should be non 0 - pending
					 */


					/*
					 * Publish the Schedule
					 */
					schedulePage.publishActiveSchedule();

					/*
					 * Verify: Schedule Button Should not appear.
					 */
					Thread.sleep(2000);
					SimpleUtils.assertOnFail("'Publish' button loaded for Active Week: '"+schedulePage.getActiveWeekText()+"' after Schedule published.",
							! schedulePage.isPublishButtonLoaded(), true);
				}
			}
	    }



	    @Automated(automated =  "Automated")
		@Owner(owner = "Nishant")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-18: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void editPopUpScheduleShouldNotPresentAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);

	        //Must have at least "Past Week" schedule published
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Previous.getValue(), weekCount.One.getValue());
	        SimpleUtils.assertOnFail("Schedule Page: Past week not generated!",schedulePage.isWeekGenerated() , true);
	        SimpleUtils.assertOnFail("Schedule Page: Past week not Published!",schedulePage.isWeekPublished() , true);
	        //The schedules that are already published should remain unchanged
	        schedulePage.clickOnDayView();
	        schedulePage.navigateDayViewToPast(weekViewType.Previous.getValue(), dayCount.Seven.getValue());
	        schedulePage.clickOnEditButton();
	        SimpleUtils.assertOnFail("User can add new shift for past week", (! schedulePage.isAddNewDayViewShiftButtonLoaded()) , false);
	        schedulePage.clickOnCancelButtonOnEditMode();

	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Nishant")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-18: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void editOpenShiftScheduleAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
//	    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        //The schedules that are already published should remain unchanged
	        schedulePage.clickOnDayView();
	        int previousGutterCount = schedulePage.getgutterSize();
	        scheduleNavigationTest(previousGutterCount);
	        HashMap<String, Float> ScheduledHours = schedulePage.getScheduleLabelHours();
	        Float scheduledHoursBeforeEditing = ScheduledHours.get("scheduledHours");
	        HashMap<List<String>,List<String>> teamCount = schedulePage.calculateTeamCount();
	        SimpleUtils.assertOnFail("User can add new shift for past week", (schedulePage.isAddNewDayViewShiftButtonLoaded()) , true);
	        String textStartDay = schedulePage.clickNewDayViewShiftButtonLoaded();
	        schedulePage.customizeNewShiftPage();
	        schedulePage.compareCustomizeStartDay(textStartDay);
	        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"),sliderShiftCount.SliderShiftEndTimeCount.getValue(), shiftSliderDroppable.EndPoint.getValue());
	        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  sliderShiftCount.SliderShiftStartCount.getValue(), shiftSliderDroppable.StartPoint.getValue());
	        HashMap<String, String> shiftTimeSchedule = schedulePage.calculateHourDifference();
	        schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
	        schedulePage.clickRadioBtnStaffingOption(staffingOption.OpenShift.getValue());
	        schedulePage.clickOnCreateOrNextBtn();
	        int updatedGutterCount = schedulePage.getgutterSize();
	        List<String> previousTeamCount = schedulePage.calculatePreviousTeamCount(shiftTimeSchedule,teamCount);
	        List<String> currentTeamCount = schedulePage.calculateCurrentTeamCount(shiftTimeSchedule);
	        verifyTeamCount(previousTeamCount,currentTeamCount);
	        schedulePage.clickSaveBtn();
	        HashMap<String, Float> editScheduledHours = schedulePage.getScheduleLabelHours();
	        Float scheduledHoursAfterEditing = editScheduledHours.get("scheduledHours");
	        verifyScheduleLabelHours(shiftTimeSchedule.get("ScheduleHrDifference"), scheduledHoursBeforeEditing, scheduledHoursAfterEditing);

	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Nishant")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-39: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void editManualShiftScheduleAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
//	    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        //The schedules that are already published should remain unchanged
	        schedulePage.clickOnDayView();
	        int previousGutterCount = schedulePage.getgutterSize();
	        scheduleNavigationTest(previousGutterCount);
	        HashMap<String, Float> ScheduledHours = schedulePage.getScheduleLabelHours();
	        Float scheduledHoursBeforeEditing = ScheduledHours.get("scheduledHours");
	        HashMap<List<String>,List<String>> teamCount = schedulePage.calculateTeamCount();
	        SimpleUtils.assertOnFail("User can add new shift for past week", (schedulePage.isAddNewDayViewShiftButtonLoaded()) , true);
	        String textStartDay = schedulePage.clickNewDayViewShiftButtonLoaded();
	        schedulePage.customizeNewShiftPage();
	        schedulePage.compareCustomizeStartDay(textStartDay);
	        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"),sliderShiftCount.SliderShiftEndTimeCount.getValue(), shiftSliderDroppable.EndPoint.getValue());
	        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  sliderShiftCount.SliderShiftStartCount.getValue(), shiftSliderDroppable.StartPoint.getValue());
	        HashMap<String, String> shiftTimeSchedule = schedulePage.calculateHourDifference();
	        schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
	        schedulePage.clickRadioBtnStaffingOption(staffingOption.ManualShift.getValue());
	        schedulePage.clickOnCreateOrNextBtn();
	        schedulePage.customizeNewShiftPage();
	        schedulePage.verifySelectTeamMembersOption();
	        schedulePage.clickOnOfferOrAssignBtn();
	        int updatedGutterCount = schedulePage.getgutterSize();
	        List<String> previousTeamCount = schedulePage.calculatePreviousTeamCount(shiftTimeSchedule,teamCount);
	        List<String> currentTeamCount = schedulePage.calculateCurrentTeamCount(shiftTimeSchedule);
	        verifyTeamCount(previousTeamCount,currentTeamCount);
	        schedulePage.clickSaveBtn();
	        HashMap<String, Float> editScheduledHours = schedulePage.getScheduleLabelHours();
	        Float scheduledHoursAfterEditing = editScheduledHours.get("scheduledHours");
	        verifyScheduleLabelHours(shiftTimeSchedule.get("ScheduleHrDifference"), scheduledHoursBeforeEditing, scheduledHoursAfterEditing);
	    }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Nishant")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-39: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void editAssignTeamScheduleAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
//	    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.clickOnDayView();
	        int previousGutterCount = schedulePage.getgutterSize();
	        scheduleNavigationTest(previousGutterCount);
	        HashMap<String, Float> ScheduledHours = schedulePage.getScheduleLabelHours();
	        Float scheduledHoursBeforeEditing = ScheduledHours.get("scheduledHours");
	        HashMap<List<String>,List<String>> teamCount = schedulePage.calculateTeamCount();
	        SimpleUtils.assertOnFail("User can add new shift for past week",
	        		(schedulePage.isAddNewDayViewShiftButtonLoaded()) , true);
	        String textStartDay = schedulePage.clickNewDayViewShiftButtonLoaded();
	        schedulePage.customizeNewShiftPage();
	        schedulePage.compareCustomizeStartDay(textStartDay);
	        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_END_TIME"),sliderShiftCount.SliderShiftEndTimeCount.getValue(), shiftSliderDroppable.EndPoint.getValue());
	        schedulePage.moveSliderAtSomePoint(propertyCustomizeMap.get("INCREASE_START_TIME"),  sliderShiftCount.SliderShiftStartCount.getValue(), shiftSliderDroppable.StartPoint.getValue());
	        HashMap<String, String> shiftTimeSchedule = schedulePage.calculateHourDifference();
	        schedulePage.selectWorkRole(scheduleWorkRoles.get("WorkRole"));
	        schedulePage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
	        schedulePage.clickOnCreateOrNextBtn();
	        schedulePage.customizeNewShiftPage();
	        schedulePage.verifySelectTeamMembersOption();
	        schedulePage.clickOnOfferOrAssignBtn();
	        int updatedGutterCount = schedulePage.getgutterSize();
	        List<String> previousTeamCount = schedulePage.calculatePreviousTeamCount(shiftTimeSchedule,teamCount);
	        List<String> currentTeamCount = schedulePage.calculateCurrentTeamCount(shiftTimeSchedule);
	        verifyTeamCount(previousTeamCount,currentTeamCount);
	        schedulePage.clickSaveBtn();
	        HashMap<String, Float> editScheduledHours = schedulePage.getScheduleLabelHours();
	        Float scheduledHoursAfterEditing = editScheduledHours.get("scheduledHours");
	        verifyScheduleLabelHours(shiftTimeSchedule.get("ScheduleHrDifference"), scheduledHoursBeforeEditing, scheduledHoursAfterEditing);

	    }

	    public void verifyScheduleLabelHours(String shiftTimeSchedule,
	  			Float scheduledHoursBeforeEditing, Float scheduledHoursAfterEditing) throws Exception{
	  			Float scheduledHoursExpectedValueEditing = 0.0f;
            // If meal break is applicable
//	  		if(Float.parseFloat(shiftTimeSchedule) >= 6){
//	  			scheduledHoursExpectedValueEditing = (float) (scheduledHoursBeforeEditing + (Float.parseFloat(shiftTimeSchedule) - 0.5));
//	  		}else{
//	  			scheduledHoursExpectedValueEditing = (float)(scheduledHoursBeforeEditing + Float.parseFloat(shiftTimeSchedule));
//	  		}
           // If meal break is not applicable
            scheduledHoursExpectedValueEditing = (float)(scheduledHoursBeforeEditing + Float.parseFloat(shiftTimeSchedule));
	  		if(scheduledHoursExpectedValueEditing.equals(scheduledHoursAfterEditing)){
	  			SimpleUtils.pass("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" matches with Scheduled Hours Actual value "+scheduledHoursAfterEditing);
	  		}else{
	  			SimpleUtils.fail("Scheduled Hours Expected value "+scheduledHoursExpectedValueEditing+" does not match with Scheduled Hours Actual value "+scheduledHoursAfterEditing,false);
	  		}
	  	}


	    public void verifyTeamCount(List<String> previousTeamCount, List<String> currentTeamCount) throws Exception {
			if(previousTeamCount.size() == currentTeamCount.size()){
				for(int i =0; i<currentTeamCount.size();i++){
					String currentCount = currentTeamCount.get(i);
					String previousCount = previousTeamCount.get(i);
					if(Integer.parseInt(currentCount) == Integer.parseInt(previousCount)+1){
						SimpleUtils.pass("Current Team Count is greater than Previous Team Count");
					}else{
						SimpleUtils.fail("Current Team Count is not greater than Previous Team Count",true);
					}
				}
			}else{
				SimpleUtils.fail("Size of Current Team Count should be equal to Previous Team Count",false);
			}
		}

	    public void verifyGutterCount(int previousGutterCount, int updatedGutterCount){
	    	if(updatedGutterCount == previousGutterCount + 1){
	    		SimpleUtils.pass("Size of gutter is "+updatedGutterCount+" greater than previous value "+previousGutterCount);
	    	}else{
	    		SimpleUtils.fail("Size of gutter is "+updatedGutterCount+" greater than previous value "+previousGutterCount, false);
	    	}
	    }


	    public void scheduleNavigationTest(int previousGutterCount) throws Exception{
	    	 schedulePage.clickOnEditButton();
	    	 boolean bolDeleteShift = checkAddedShift(previousGutterCount);
	    	 if(bolDeleteShift){
	    		 schedulePage.clickSaveBtn();
		    	 schedulePage.clickOnEditButton();
	    	 }
	    }

	    public boolean checkAddedShift(int guttercount)throws Exception {
            boolean bolDeleteShift = false;
            if (guttercount > 0) {
                schedulePage.clickOnShiftContainer(guttercount);
                bolDeleteShift = true;
            }
            return bolDeleteShift;
        }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-19: Automation Script for - JIRA ID - LEG-4249 - \"As a manager I should be able to navigate to view current week or other weeks' schedule\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void navigateToCurrentAndOtherWeekAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {

	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!" ,
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.clickOnWeekView();

	        int scheduleOverViewStatusCount = overviewPageScheduledWeekStatus.size();
	        boolean isStoreClosed = false;
			for(int index = 0; index < (scheduleOverViewStatusCount - 1); index++)
			{
				String status = overviewPageScheduledWeekStatus.get(index);
				if(index != 0)
					schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

				SimpleUtils.report("Selected Week: '"+schedulePage.getActiveWeekText()+"'");
				/*
				 * Assertions: First week have 1 day closed
				 */

				if(status.toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase())
						&& overviewPageScheduledWeekStatus.get(index + 1).toLowerCase().equals(overviewWeeksStatus.Guidance.getValue().toLowerCase())) {
					isStoreClosed = schedulePage.isActiveWeekHasOneDayClose();
					if(isStoreClosed)
					{
						SimpleUtils.report("Generating Schedule for the Week: '"+schedulePage.getActiveWeekText()+"'");
						schedulePage.generateSchedule();
						schedulePage.clickOnWeekView();
						schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
					}

				}
				else if(status.toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase())) {


					// Generating Active Week
					if(isStoreClosed)
					{
						SimpleUtils.report("Generating Schedule for the Week: '"+schedulePage.getActiveWeekText()+"'");
						schedulePage.generateSchedule();
						break;
					}


					isStoreClosed = schedulePage.isActiveWeekHasOneDayClose();
					schedulePage.clickOnWeekView();
					if(isStoreClosed)
						schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());
				}
			}

			/*
			 * Remember the Last Visited Week
				Week View
				Open a Schedule of any Day (Not necessarily the same Day) in Day View say (Sep 1)
				Then go to Schedule overview
				Then go back to Schedule tab directly
				Should open the the schedule of the same Day (Sep 1) in Day View
				Then switch to TM view of week of (Sep 1)
				Then go to Schedule overview
				Then go to Schedule Tab directly. Should see TM view of the week of (Sep 1)
			 */

			String oldActiveDay = "";
			String newActiveDay = "";
			String oldGroupByFilter = "";
			String newGroupByFilter = "";

			// Remember Last Visited Day
			schedulePage.clickOnWeekView();
			if(! schedulePage.isWeekGenerated())
				schedulePage.generateSchedule();
			Thread.sleep(1000);
			schedulePage.clickOnDayView();
			int dayIndex = 1;
			schedulePage.navigateDayViewWithIndex(dayIndex);
			oldActiveDay = schedulePage.getActiveWeekText();
			SimpleUtils.report("Last visited day: '"+oldActiveDay+"'");
			schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        newActiveDay = schedulePage.getActiveWeekText();
	        SimpleUtils.report("Current visited day: '"+newActiveDay+"'");
	        SimpleUtils.assertOnFail("Not remember the Last Active Week/Day ("+oldGroupByFilter +" / "+newGroupByFilter+").", oldActiveDay.contains(newActiveDay) , true);


	        // Remember Last Group By Filter
	        schedulePage.clickOnWeekView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyTM.getValue());

	        oldGroupByFilter = schedulePage.getActiveGroupByFilter();
	        SimpleUtils.report("Previous Active Group By Filter: '"+oldGroupByFilter+"'");

	    	schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);

	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        newGroupByFilter = schedulePage.getActiveGroupByFilter();
	        SimpleUtils.report("Current Active Group By Filter: '"+newGroupByFilter+"'");

	        SimpleUtils.assertOnFail("Not remember the Last Active Group By Filter ("+oldGroupByFilter +" / "+newGroupByFilter+").",
	        		oldGroupByFilter.contains(newGroupByFilter) , true);
	    }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-83: Automation Script for - JIRA ID - FOR-559 and LEG-2592 ")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByAndNavigateToOtherTabAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Week view
	         */
	        int workRoleIndex = 1;
	        Boolean isClearWorkRoleFilters = true;
	        schedulePage.clickOnWeekView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
	        schedulePage.isScheduleGroupByWorkRole(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
	        schedulePage.selectWorkRoleFilterByIndex(workRoleIndex, isClearWorkRoleFilters);
	        ArrayList<String> scheduleSelectedWorkRole = schedulePage.getSelectedWorkRoleOnSchedule();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.StaffingGuidance.getValue());
	        SimpleUtils.assertOnFail("'Staffing Guidance' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.StaffingGuidance.getValue()) , true);
	        StaffingGuidancePage staffingGuidancePage = pageFactory.createStaffingGuidancePage();
	        String staffingGuidanceWorkRoleFilter = staffingGuidancePage.getActiveWorkRole();
	        SimpleUtils.assertOnFail("Work Role filter changed under Staffing Guidance Tab",
	        		scheduleSelectedWorkRole.get(0).equalsIgnoreCase(staffingGuidanceWorkRoleFilter) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        SimpleUtils.assertOnFail("Work Role filter changed under Schedule Tab",
	        		staffingGuidanceWorkRoleFilter.equalsIgnoreCase(schedulePage.getSelectedWorkRoleOnSchedule().get(0)) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.ProjectedTraffic.getValue());
	        SimpleUtils.assertOnFail("'Projected Traffic' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.ProjectedTraffic.getValue()) , true);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        SimpleUtils.assertOnFail("Work Role filter not reseting after visiting Projected Traffic Tab",
	        		(0 == schedulePage.getSelectedWorkRoleOnSchedule().size()) , true);
	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-91: Automation Script for - JIRA ID - "
	    		+ "FOR-733- Required Action(Unassigned Shift) changes to Console Message(Infeasible Schedule) on clicking Refresh Button(KendraScott2) ")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void verifyRequiredActionToConsoleMessageAfterRefreshAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.clickOnWeekView();

	        int scheduleOverViewStatusCount = overviewPageScheduledWeekStatus.size();
			for(int index = 0; index < scheduleOverViewStatusCount; index++)
			{
				String status = overviewPageScheduledWeekStatus.get(index);
				if(index != 0)
					schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

				if(status.toLowerCase().equals(overviewWeeksStatus.Draft.getValue().toLowerCase()))
				{
					boolean isRequiredActionUnAssignedShift = schedulePage.isRequiredActionUnAssignedShiftForActiveWeek();
					schedulePage.clickOnRefreshButton();

					SimpleUtils.assertOnFail("Action Required as Unassigned Shift changed to Console Message After Refresh for The Week: '"+schedulePage.getActiveWeekText()+"'",
			        		(isRequiredActionUnAssignedShift == schedulePage.isRequiredActionUnAssignedShiftForActiveWeek()) , true);
				}
			}
	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-89: Automation Script for - JIRA ID - FOR-599- Compliance smartcard is missing when new compliance warning introduced after initial schedule generation.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void verifyComplianceSmartcardAfterExdendingAShiftToOTAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
			List<WebElement> overviewPageScheduledWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
			for(WebElement overviewScheduleWeek : overviewPageScheduledWeeks)
			{
				if(overviewScheduleWeek.getText().toLowerCase().contains(overviewWeeksStatus.Draft.getValue().toLowerCase()))
				{
					BasePage basePage = new BasePage();
					basePage.click(overviewScheduleWeek);
					schedulePage.clickOnDayView();
					String shiftTypeFilterText = "Compliance Review";
					schedulePage.selectShiftTypeFilterByText(shiftTypeFilterText);
					List<WebElement> availableShifts = schedulePage.getAvailableShiftsInDayView();
					if(availableShifts.size() == 0)
					{
						shiftTypeFilterText = "None";
						schedulePage.selectShiftTypeFilterByText(shiftTypeFilterText);
						schedulePage.clickOnEditButton();
						schedulePage.dragShiftToRightSide(schedulePage.getAvailableShiftsInDayView().get(0), 400);
						schedulePage.clickSaveBtn();
						shiftTypeFilterText = "Compliance Review";
						schedulePage.selectShiftTypeFilterByText(shiftTypeFilterText);
						availableShifts = schedulePage.getAvailableShiftsInDayView();
						SimpleUtils.assertOnFail("Shift with OT not displaying under 'Compliance Review filter",
				        		(availableShifts.size()!= 0) , true);
					}

					String complianceReviewCardTextLabel = "require compliance review";
					SimpleUtils.assertOnFail("Compliance smartcard is missing when extended a shift into OT for the Week/Day: '"+ schedulePage.getActiveWeekText() +"'.",
							schedulePage.isSmartCardAvailableByLabel(complianceReviewCardTextLabel) , true);
					break;
				}
			}
	    }

	@Automated(automated =  "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "TP-35: Automation Script for - JIRA ID - "
			+ "FOR-394- Forecast and schedule should disable navigation selecting future week outside schedule planning window")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void disableWeekViewNavOutsideSchPlanningWindowStoreManager(String browser, String username, String password, String location)
			throws Exception {
	    DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	    SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
	    schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	    SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	    		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	    ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
	    schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	    schedulePage.clickOnWeekView();
	    int scheduleOverViewStatusCount = schedulePlanningWindow.Eight.getValue();
		for(int index = 0; index <= scheduleOverViewStatusCount; index++)
		{
			if(index == scheduleOverViewStatusCount){
				schedulePage.disableNextWeekArrow();
				break;
			}
			schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
		}
	}


	@Automated(automated = "Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "Kendrascott2_Enterprise")
	@TestName(description = "LEG-5232: Data for Schedule does not get loaded when user clicks on next day without waiting data for highlighted day gets loaded")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void generateScheduleAndCheckScheduleNTMSizeWeekView(String username, String password, String browser, String location)
			throws Exception
	{
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
		schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
		schedulePage.clickOnScheduleSubTab(LoginTest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		List<WebElement> overviewPageScheduledWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
		for(int i=0; i <overviewPageScheduledWeeks.size();i++)
		{
			if(overviewPageScheduledWeeks.get(i).getText().toLowerCase().contains(LoginTest.overviewWeeksStatus.Guidance.getValue().toLowerCase()))
			{
				scheduleOverviewPage.clickOnGuidanceBtnOnOverview(i);
				if(schedulePage.isGenerateButtonLoaded())
				{
					SimpleUtils.pass("Guidance week found : '"+ schedulePage.getActiveWeekText() +"'");
					schedulePage.generateOrUpdateAndGenerateSchedule();
					schedulePage.verifyScheduledHourNTMCountIsCorrect();

					break;
				}
			}
		}

	}

	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate functioning of compliance smartcard")
	@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	public void complianceSmartCard(String username, String password, String browser, String location) throws Throwable {
//        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
//        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	    schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()) , true);
		schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
		schedulePage.complianceShiftSmartCard();
	}



    @Automated(automated =  "Automated")
    @Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "TP-93: Automation Script for - JIRA ID - FOR-707:- Incorrect date when refreshing.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyFutureScheduleWeekDateAfteRefreshBrowserAsStoreManager(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        List<WebElement> overviewPageScheduledWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
        for(WebElement overviewScheduleWeek : overviewPageScheduledWeeks)
        {
            if(! overviewScheduleWeek.getText().toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase())
                    && ! overviewScheduleWeek.getText().toLowerCase().contains(overviewWeeksStatus.NotAvailable.getValue().toLowerCase()))
            {
                BasePage basePage = new BasePage();
                basePage.click(overviewScheduleWeek);

                ArrayList<String> ScheduleWeekDatesBeforeRefresh = schedulePage.getActiveWeekCalendarDates();

                schedulePage.refreshBrowserPage();

                ArrayList<String> ScheduleWeekDatesAfterRefresh = schedulePage.getActiveWeekCalendarDates();

                SimpleUtils.assertOnFail("Incorrect dates when refreshing for the active Week/day: '"+ schedulePage.getActiveWeekText() +"'" ,
                        ScheduleWeekDatesBeforeRefresh.equals(ScheduleWeekDatesAfterRefresh), false);
                break;
            }
        }
    }


    @Automated(automated =  "Automated")
    @Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "TP-79: As a TM should be able to view the new schedule and offers")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyTMCanViewNewScheduleAndOffersAsStoreManager(String browser, String username, String password, String location)
            throws Exception {

        String fileName = "UsersCredentials.json";
        fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        Object[][] storeMenageCredentials = userCredentials.get("StoreManager");

        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        List<String> overviewPageScheduledWeekStatus = scheduleOverviewPage.getScheduleWeeksStatus();
        ArrayList<Boolean> isTeamMemberAssignedToOverviewWeeks = new ArrayList<Boolean>();

        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
        schedulePage.clickOnWeekView();
        for(String weekStatus : overviewPageScheduledWeekStatus)
        {
            if(! weekStatus.toLowerCase().contains(overviewWeeksStatus.NotAvailable.getValue().toLowerCase()))
                isTeamMemberAssignedToOverviewWeeks.add(
                        schedulePage.isActiveWeekAssignedToCurrentUser(String.valueOf(teamMemberCredentials[0][0])));
            else
                isTeamMemberAssignedToOverviewWeeks.add(false);
            schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
        }
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();

        /*
         * Login as Team Member
         */
        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]),
                String.valueOf(teamMemberCredentials[0][1]), String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
        schedulePage.clickOnWeekView();
        for(int index = 0; index < overviewPageScheduledWeekStatus.size(); index++)
        {
            String weekStatus = overviewPageScheduledWeekStatus.get(index);
            boolean isWeekAssignedToTM = isTeamMemberAssignedToOverviewWeeks.get(index);
            boolean isSchedulePublished = schedulePage.isCurrentScheduleWeekPublished();
            if(weekStatus.toLowerCase().contains(overviewWeeksStatus.Draft.getValue().toLowerCase()))
            {
                SimpleUtils.assertOnFail("UnPublished week:'"+ schedulePage.getActiveWeekText()
                +"' Schedule is visible for Team Member", ! isSchedulePublished, true);
            }
            else
            {
                if(isWeekAssignedToTM)
                    SimpleUtils.assertOnFail("Published week:'"+ schedulePage.getActiveWeekText()
                    +"' assigned to current Team Member but Schedule is not visible", isSchedulePublished, true);
                else
                    SimpleUtils.assertOnFail("Published week:'"+ schedulePage.getActiveWeekText()
                    +"' not assigned to current Team Member and Schedule is not visible", isSchedulePublished, true);
            }
            schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
        }
        loginPage.logOut();

        // If there is any modifications done in published week schedule, it should not be visible in TM’s login unless or until it is republished.
        // Adding new Shift to Published Schedule as Store Manager
        String activeDayText = "";
        int shiftCountBeforeAdded = 0;
        loginToLegionAndVerifyIsLoginDone(String.valueOf(storeMenageCredentials[0][0]),
                String.valueOf(storeMenageCredentials[0][1]), String.valueOf(storeMenageCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        schedulePage.clickOnWeekView();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
        for(String weekStatus : overviewPageScheduledWeekStatus)
        {
            if(weekStatus.toLowerCase().contains(overviewWeeksStatus.Finalized.getValue().toLowerCase())
                    || weekStatus.toLowerCase().contains(overviewWeeksStatus.Published.getValue().toLowerCase()))
            {
                schedulePage.clickOnDayView();
                activeDayText = schedulePage.getActiveWeekText();
                shiftCountBeforeAdded = schedulePage.getAvailableShiftsInDayView().size();
                schedulePage.clickOnEditButton();
                schedulePage.addOpenShiftWithDefaultTime(scheduleWorkRoles.get("WorkRole"));
                schedulePage.clickSaveBtn();
                SimpleUtils.assertOnFail("Unable to add shift in day view",
                        (schedulePage.getAvailableShiftsInDayView().size() > shiftCountBeforeAdded) , true);
                break;
            }
            schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
        }
        loginPage.logOut();



        // Login as Team Member to verify Schedule modification Reflects or not.

        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]),
                String.valueOf(teamMemberCredentials[0][1]), String.valueOf(teamMemberCredentials[0][2]));
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
        schedulePage.clickOnWeekView();
        for(String weekStatus : overviewPageScheduledWeekStatus)
        {
            if(weekStatus.toLowerCase().contains(overviewWeeksStatus.Finalized.getValue().toLowerCase())
                    || weekStatus.toLowerCase().contains(overviewWeeksStatus.Published.getValue().toLowerCase()))
            {
                schedulePage.clickOnDayView();
                if(activeDayText.contains(schedulePage.getActiveWeekText()))
                {
                    SimpleUtils.assertOnFail("Schedule Modification Reflected to Team Member",
                            (schedulePage.getAvailableShiftsInDayView().size() <= shiftCountBeforeAdded) , false);
                    SimpleUtils.pass("Schedule Modification not reflecting to Team Member without Re-Publishing Schedule.");
                    break;
                }
            }
            schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
        }
    }


    @Automated(automated =  "Automated")
    @Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "TP-96: TM and TL should not see smart card panel")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifySmartCardPanelForTMAndTLAsTeamLead(String browser, String username, String password, String location)
            throws Exception {
        SimpleUtils.pass("Logged-in to Legion as Team Lead");
        String fileName = "UsersCredentials.json";
        boolean isFirstWeek = true;
        fileName=SimpleUtils.getEnterprise("KendraScott2_Enterprise")+fileName;
        HashMap<String, Object[][]> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName);
        Object[][] teamMemberCredentials = userCredentials.get("TeamMember");
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
        schedulePage.clickOnWeekView();
        while(schedulePage.isNextWeekAvaibale())
        {
            if(! isFirstWeek)
               schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
            boolean isSmartCardPanel = schedulePage.isSmartCardPanelDisplay();
            SimpleUtils.assertOnFail("Schedule Page: Smart Card Panel loaded for Team Lead for Week: '"+ schedulePage.getActiveWeekText() +"'",
                (! isSmartCardPanel) , false);
            SimpleUtils.pass("Schedule Page: Smart Card Panel not available for Team Lead for Week: '"+ schedulePage.getActiveWeekText()+"'");
            isFirstWeek = false;
        }
        LoginPage loginPage = pageFactory.createConsoleLoginPage();
        loginPage.logOut();


        loginToLegionAndVerifyIsLoginDone(String.valueOf(teamMemberCredentials[0][0]),
                String.valueOf(teamMemberCredentials[0][1]), String.valueOf(teamMemberCredentials[0][2]));
        SimpleUtils.pass("Logged-in to Legion as Team Member");
        dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
        schedulePage.clickOnWeekView();
        isFirstWeek = true;
        while(schedulePage.isNextWeekAvaibale())
        {
            if(! isFirstWeek)
               schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
            boolean isSmartCardPanel = schedulePage.isSmartCardPanelDisplay();
            SimpleUtils.assertOnFail("Schedule Page: Smart Card Panel loaded for Team Member for Week: '"+ schedulePage.getActiveWeekText() +"'",
                    (! isSmartCardPanel) , false);
            SimpleUtils.pass("Schedule Page: Smart Card Panel not available for Team Member for Week: '"+ schedulePage.getActiveWeekText()+"'");
            isFirstWeek = false;
        }
    }



    @Automated(automated =  "Automated")
    @Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "TP-98: FOR-710 :- Workrole filter selection should preserve selection (across new, draft, published schedule week) until user update/clear the filter.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyWorkRoleFilterPreserveSelectionOnScheduleGeneratePageAsStoreManager(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , false);
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        List<WebElement> overViewWeekList = scheduleOverviewPage.getOverviewScheduleWeeks();
        boolean isGuidanceWeekFound = false;
        for(int index = 0;index < (overViewWeekList.size() - 1); index++)
        {
            int nextIndex = index + 1;
            WebElement overviewWeek = overViewWeekList.get(index);
            WebElement overviewNextWeek = overViewWeekList.get(nextIndex);
            String nextWeekText = overviewNextWeek.getText().replace("\n", " ");
            String overviewWeekText = overviewWeek.getText().replace("\n", " ");
            if(nextWeekText.toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase())
                    && ! overviewWeekText.toLowerCase().contains(overviewWeeksStatus.Guidance.getValue().toLowerCase())
                        && ! overviewWeekText.toLowerCase().contains(overviewWeeksStatus.NotAvailable.getValue().toLowerCase()))
            {
                isGuidanceWeekFound = true;
                BasePage basePage = new BasePage();
                basePage.click(overviewWeek);
                schedulePage.clickOnWeekView();
                boolean isClearWorkRoleFilters = true;
                schedulePage.selectWorkRoleFilterByIndex(filtersIndex.Zero.getValue(), isClearWorkRoleFilters);
                isClearWorkRoleFilters = false;
                schedulePage.selectWorkRoleFilterByIndex(filtersIndex.One.getValue(), isClearWorkRoleFilters);
                ArrayList<String> workroleActiveFiltersBeforeNavigation = schedulePage.getSelectedWorkRoleOnSchedule();
                //Navigate to Guidance Week
                schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
                SimpleUtils.pass("Visited to Guidance Week: '"+ schedulePage.getActiveWeekText() +"'");
                //Navigate to Previous Week
                schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Previous.getValue(), weekCount.One.getValue());
                ArrayList<String> workroleActiveFiltersAfterNavigation = schedulePage.getSelectedWorkRoleOnSchedule();
                SimpleUtils.assertOnFail("WorkRole Filter not preserve selection on schedule generate page for the week: '"+ schedulePage.getActiveWeekText() +"'",
                        (workroleActiveFiltersBeforeNavigation.equals(workroleActiveFiltersAfterNavigation)) , false);
                SimpleUtils.pass("WorkRole Filter preserves selection on schedule generate page for the week: '"+ schedulePage.getActiveWeekText()+"'");
                break;
            }
        }
        if(! isGuidanceWeekFound)
            SimpleUtils.report("No Guidance week found!");

    }


    @Automated(automated =  "Automated")
    @Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "TP-103: LEG-5000:- On schedule overview page wrong calendar is displayed when transitioning to next month.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyScheduleOverviewPageCalendarMonthsAndYearsAsStoreManager(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , false);
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        ArrayList<String> overviewPageCalendarMonthsAndYears = scheduleOverviewPage.getOverviewCalendarMonthsYears();
        int duplicateValueCount = SimpleUtils.countDuplicates(overviewPageCalendarMonthsAndYears);
        LocalDate currentDate = SimpleUtils.getCurrentLocalDateObject();
        for(int index = 0; index < overviewPageCalendarMonthsAndYears.size(); index++)
        {
            String[] overviewCalenderMonthAndYear = overviewPageCalendarMonthsAndYears.get(index).split(" ");
            String month = String.valueOf(currentDate.plusMonths(index).getMonth());
            String year = String.valueOf(currentDate.plusMonths(index).getYear());
            SimpleUtils.assertOnFail("Wrong month ('" +overviewCalenderMonthAndYear[0]
                + "') displaying on Overview Calendar. Currect Month:'"+ month +"'",
                    overviewPageCalendarMonthsAndYears.get(index).toLowerCase().contains(month.toLowerCase()) , false);

            SimpleUtils.assertOnFail("Wrong Year ('" +overviewCalenderMonthAndYear[overviewCalenderMonthAndYear.length - 1]
                    + "') displaying on Overview Calendar. Currect Year:'"+ year +"'",
                        overviewPageCalendarMonthsAndYears.get(index).toLowerCase().contains(year.toLowerCase()) , false);

        }
        SimpleUtils.pass("Overview Calendar diplaying currect Months & Years.");
    }

    @Automated(automated =  "Automated")
    @Owner(owner = "Naval")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "TP-94: FOR-718:- When unassigned or compliance shifts are resolved, the unassigned/compliance smartcard should disappear upon save.")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void verifyUnAssignedAndComplianceSmartCardAsStoreManager(String browser, String username, String password, String location)
            throws Exception {
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = dashboardPage.goToTodayForNewUI();
        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
                schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , false);
        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
        List<WebElement> overViewWeekList = scheduleOverviewPage.getOverviewScheduleWeeks();
        int weekSize = overViewWeekList.size();
        BasePage basePage = new BasePage();
        basePage.click(overViewWeekList.get(0));
        for(int index = 0; index < weekSize; index++)
        {
            schedulePage.clickOnWeekView();
            if(index != 0)
                schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

            // Verify UnAssigned Shift SmartCard
            String SmartCardLabelForUnassignedShift = "unassigned shift";
            if(schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForUnassignedShift))
            {
                SimpleUtils.report("UnAssigned Shift SmartCard found for the Week: '"+ schedulePage.getActiveWeekText() +"'");
                schedulePage.convertAllUnAssignedShiftToOpenShift();
                SimpleUtils.assertOnFail("Unassigned smartcard not disappeared After Converting UnAssigned Shift to Open Shift for: '"
                + schedulePage.getActiveWeekText() +"'", (! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForUnassignedShift)), true);

                if(! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForUnassignedShift))
                    SimpleUtils.pass("UnAssigned Shift SmartCard disappear after converting unassigned shift to open shift.");
            }

            // Verify compliance review Shift SmartCard
            String SmartCardLabelForComplianceReview = "compliance review";
            if(schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForComplianceReview))
            {
                SimpleUtils.report("UnAssigned Shift SmartCard found for the Week: '"+ schedulePage.getActiveWeekText() +"'");
                String shiftTypeFilterText = "Compliance Review";
                schedulePage.selectShiftTypeFilterByText(shiftTypeFilterText);
                schedulePage.clickOnDayView();
                schedulePage.reduceOvertimeHoursOfActiveWeekShifts();

                SimpleUtils.assertOnFail("Compliance Review smartcard not disappeared After reducing Overtime of Shift(s) for: '"
                        + schedulePage.getActiveWeekText() +"'", (! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForComplianceReview)), true);

                if(! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForComplianceReview))
                    SimpleUtils.pass("Compliance Review smartcard disappeared After reducing Overtime of Shift(s) for: '"
                            + schedulePage.getActiveWeekText() +"'");

                schedulePage.selectShiftTypeFilterByText("Clearing All Shift Type Filters");
            }
        }

    }


    @Automated(automated =  "Automated")
    @Owner(owner = "Manideep")
    @Enterprise(name = "KendraScott2_Enterprise")
    @TestName(description = "FOR-679: Cannot generate schedule when directly go to unscheduled week from overview")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
    public void validatingGenerateScheduleStoreManager(String browser, String username, String password, String location)
            throws Exception {
        int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
        //	    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
        schedulePage.clickOnScheduleConsoleMenuItem();
        schedulePage.validatingGenrateSchedule();
				// Verify UnAssigned Shift SmartCard
				String SmartCardLabelForUnassignedShift = "unassigned shift";
				if(schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForUnassignedShift))
				{
					SimpleUtils.report("UnAssigned Shift SmartCard found for the Week: '"+ schedulePage.getActiveWeekText() +"'");
					schedulePage.convertAllUnAssignedShiftToOpenShift();
					SimpleUtils.assertOnFail("Unassigned smartcard not disappeared After Converting UnAssigned Shift to Open Shift for: '"
					+ schedulePage.getActiveWeekText() +"'", (! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForUnassignedShift)), true);

					if(! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForUnassignedShift))
						SimpleUtils.pass("UnAssigned Shift SmartCard disappear after converting unassigned shift to open shift.");
				}

				// Verify compliance review Shift SmartCard
				String SmartCardLabelForComplianceReview = "compliance review";
				if(schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForComplianceReview))
				{
					SimpleUtils.report("UnAssigned Shift SmartCard found for the Week: '"+ schedulePage.getActiveWeekText() +"'");
					String shiftTypeFilterText = "Compliance Review";
					schedulePage.selectShiftTypeFilterByText(shiftTypeFilterText);
					schedulePage.clickOnDayView();
					schedulePage.reduceOvertimeHoursOfActiveWeekShifts();

					SimpleUtils.assertOnFail("Compliance Review smartcard not disappeared After reducing Overtime of Shift(s) for: '"
							+ schedulePage.getActiveWeekText() +"'", (! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForComplianceReview)), true);

					if(! schedulePage.isSmartCardAvailableByLabel(SmartCardLabelForComplianceReview))
						SimpleUtils.pass("Compliance Review smartcard disappeared After reducing Overtime of Shift(s) for: '"
								+ schedulePage.getActiveWeekText() +"'");

					schedulePage.selectShiftTypeFilterByText("Clearing All Shift Type Filters");
				}
        }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-92: FOR-456 :- Should not keep the stickiness of filter in Guidance and Schedule when re-entering schedule app.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void verifyFiltersWhileNavigatingToGuidanceAndScheduleTabAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
	        schedulePage.clickOnWeekView();
	        schedulePage.selectWorkRoleFilterByText("Manager", true);
	        dashboardPage.navigateToDashboard();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , false);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
	        ArrayList<String> selectedWorkRoles = schedulePage.getSelectedWorkRoleOnSchedule();
	        SimpleUtils.assertOnFail("Work Role filter not cleared after navigating to Dashboard and overview page.",
	        		(selectedWorkRoles.size() == 0) , false);
	        SimpleUtils.pass("Work Role filter cleared after navigating to Dashboard and overview page.");
	    }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-106: Validate Schedule genation functionality works fine.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateScheduleGenerationFunctionalityAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
	        schedulePage.clickOnWeekView();
	        int scheduleWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
	        for(int index = 0; index < scheduleWeekCount; index++)
	        {
	        	if(index != 0)
					schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

	        	if(schedulePage.isGenerateButtonLoaded())
	        	{
	        		SimpleUtils.pass("Guidance week found : '"+ schedulePage.getActiveWeekText() +"'");

	        		SimpleUtils.assertOnFail("Schedule Page: 'Edit' Button Displaying on Guidance Week :'"+schedulePage.getActiveWeekText() +"'",
	        				(! schedulePage.isActionButtonLoaded("Edit")) , false);

	        		SimpleUtils.assertOnFail("Schedule Page: 'Analyze' Button Displaying on Guidance Week :'"+schedulePage.getActiveWeekText() +"'",
	        				(! schedulePage.isActionButtonLoaded("Analyze")) , false);

	        		SimpleUtils.assertOnFail("Schedule Page: 'Republish' Button Displaying on Guidance Week :'"+schedulePage.getActiveWeekText() +"'",
	        				(! schedulePage.isActionButtonLoaded("Republish")) , false);

	        		SimpleUtils.assertOnFail("Schedule Page: 'Refresh' Button Displaying on Guidance Week :'"+schedulePage.getActiveWeekText() +"'",
	        				(! schedulePage.isActionButtonLoaded("Refresh")) , false);

	        		SimpleUtils.assertOnFail("Schedule Page: 'Publish' Button Displaying on Guidance Week :'"+schedulePage.getActiveWeekText() +"'",
	        				(! schedulePage.isActionButtonLoaded("Publish")) , false);

	        		schedulePage.generateSchedule();

	        		SimpleUtils.assertOnFail("Schedule Page: 'Generate' Button Displaying on after Generating Schedule on :'"+schedulePage.getActiveWeekText() +"'",
	        				(! schedulePage.isGenerateButtonLoaded()) , false);

	        		ArrayList<Float> versionHistory = schedulePage.getAllVesionLabels();
	        		float scheduleInitialVersion = (float) 0.0;

	        		SimpleUtils.assertOnFail("Schedule Page: Version History Displaying more then one versions after Generating Schedule on :'"+schedulePage.getActiveWeekText() +"'",
	        				(1 == versionHistory.size()) , false);

	        		SimpleUtils.assertOnFail("Schedule Page: Version History not starting with '0.0' after Generating Schedule on :'"+schedulePage.getActiveWeekText() +"'",
	        				(versionHistory.get(0).equals(scheduleInitialVersion)) , false);

	        		SimpleUtils.pass("Schedule Generation functionality working fine.");
	        		break;

	        	}
	        }
	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-108: Validate Store is closed for current day and if it is so then navigate to next day schedule.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void navigateToNextDayIfActiveDayHasStoreClosedAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);

	        schedulePage.navigateToNextDayIfStoreClosedForActiveDay();
	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "Coffee_Enterprise")
	    @TestName(description = "TP-126: LegionCoffee :- Validate Guidance Hours, Scheduled and Other Hrs on Dashboard page for all the locations.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateGuidanceScheduleAndOtherHoursOnDashBoardForAllLocationAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	String bayAreaLocation = "Bay Area";
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
	        locationSelectorPage.changeLocation(bayAreaLocation);
	        float totalGuidanceHours = 0;
	        float totalScheduledHours = 0;
	        float totalOtherHours = 0;
	        ArrayList<HashMap<String, Float>> dashBoardBayAreaForcastdata = dashboardPage.getDashboardForeCastDataForAllLocation();
	        for(HashMap<String, Float> locationHours : dashBoardBayAreaForcastdata)
	        {
	        	totalGuidanceHours = totalGuidanceHours + locationHours.get("Guidance");
	        	totalScheduledHours = totalScheduledHours + locationHours.get("Scheduled");
	        	totalOtherHours = totalOtherHours + locationHours.get("Other");
	        }

	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        SimpleUtils.assertOnFail("'Overview' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , false);
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
	        schedulePage.clickOnDayView();
	        HashMap<String, Float> dayViewHours = schedulePage.getScheduleLabelHoursAndWages();

	        float bayAreaBudgetedHours = dayViewHours.get("budgetedHours");
	        float bayScheduledHoursHours = dayViewHours.get("scheduledHours");
	        float bayOtherHoursHours = dayViewHours.get("otherHours");

	        SimpleUtils.assertOnFail("Sum of All Locatons Guidance Hours not matched with Bay Area Budgeted Hours ('" +
	        		totalScheduledHours+"/"+ bayScheduledHoursHours +"'",
	        		(totalScheduledHours == bayScheduledHoursHours) , false);

	        SimpleUtils.assertOnFail("Sum of All Locatons Guidance Hours not matched with Bay Area Budgeted Hours ('" +
	        		totalGuidanceHours+"/"+ bayAreaBudgetedHours +"'",
	        		(totalGuidanceHours == bayAreaBudgetedHours) , false);

	        SimpleUtils.assertOnFail("Sum of All Locatons Guidance Hours not matched with Bay Area Budgeted Hours ('" +
	        		totalOtherHours+"/"+ bayOtherHoursHours +"'",
	        		(totalOtherHours == bayOtherHoursHours) , false);

	        SimpleUtils.pass("Bay Area Day view Guidance, Scheduled And Other Hours matched with sum of all locations Hours.");


	    }


	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-128: Validation of weather forecast page on Schedule tab.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateWeatherForecastOnSchedulePageAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	String bayAreaLocation = "Bay Area";
	    	String austinDowntownLocation = "AUSTIN DOWNTOWN";
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
	        if(locationSelectorPage.isLocationSelected(bayAreaLocation))
	        	locationSelectorPage.changeLocation(austinDowntownLocation);

	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);

	        String WeatherCardText = "WEATHER";
	        //Validate Weather Smart card on Week View
	        schedulePage.clickOnWeekView();
	        Thread.sleep(5000);
	        String activeWeekText = schedulePage.getActiveWeekText();

	        if(schedulePage.isSmartCardAvailableByLabel(WeatherCardText))
	        {
	        	SimpleUtils.pass("Weather Forecart Smart Card appeared for week view duration: '"+activeWeekText+"'");
	        	String[] splitActiveWeekText = activeWeekText.split(" ");
	        	String smartCardTextByLabel = schedulePage.getsmartCardTextByLabel(WeatherCardText);
	        	String weatherTemperature = schedulePage.getWeatherTemperature();

	        	SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain starting day('"+ splitActiveWeekText[0] +"') of active week: '"+activeWeekText+"'",
	        			smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[0].toLowerCase()), true);

	        	SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain Ending day('"+ splitActiveWeekText[0] +"') of active week: '"+activeWeekText+"'",
	        			smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[0].toLowerCase()), true);
	        	if(weatherTemperature != "")
	        		SimpleUtils.pass("Weather Forecart Smart Card contains Temperature value: '" + weatherTemperature + "' for the duration: '"+
	        				activeWeekText+"'");
	        	else
	        		SimpleUtils.fail("Weather Forecart Smart Card not contains Temperature value for the duration: '"+ activeWeekText+"'", true);
	        }
	        else {
	        	SimpleUtils.fail("Weather Forecart Smart Card not appeared for week view duration: '"+activeWeekText+"'", true);
	        }

	      //Validate Weather Smart card on day View
	        schedulePage.clickOnDayView();
	        for(int index = 0; index < dayCount.Seven.getValue(); index++)
	        {
	        	if(index != 0)
	        		schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

	        	String activeDayText = schedulePage.getActiveWeekText();
	        	if(schedulePage.isSmartCardAvailableByLabel(WeatherCardText))
		        {
		        	SimpleUtils.pass("Weather Forecart Smart Card appeared for week view duration: '"+activeDayText+"'");
		        	String[] splitActiveWeekText = activeDayText.split(" ");
		        	String smartCardTextByLabel = schedulePage.getsmartCardTextByLabel(WeatherCardText);
		        	SimpleUtils.assertOnFail("Weather Forecart Smart Card not contain starting day('"+ splitActiveWeekText[1] +"') of active day: '"+activeDayText+"'",
		        			smartCardTextByLabel.toLowerCase().contains(splitActiveWeekText[1].toLowerCase()), true);
		        	String weatherTemperature = schedulePage.getWeatherTemperature();
		        	if(weatherTemperature != "")
		        		SimpleUtils.pass("Weather Forecart Smart Card contains Temperature value: '" + weatherTemperature + "' for the duration: '"+
		        				activeWeekText+"'");
		        	else
		        		SimpleUtils.pass("Weather Forecart Smart Card not contains Temperature value for the duration: '"+ activeWeekText+"'");
		        }
		        else {
		        	SimpleUtils.fail("Weather Forecart Smart Card not appeared for week view duration: '"+activeDayText+"'", true);
		        }
	        }
	    }
	    
	    
	    @UseAsTestRailId(testRailId = 32)
	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-108: Validate Store is closed for current day and if it is so then navigate to next day schedule.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void sampleTestRail(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);

	        schedulePage.navigateToNextDayIfStoreClosedForActiveDay();
	    }
	    
	    
	    @Automated(automated =  "Automated")
	    @Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-143 : Validate loading of smart card on Schedule tab[ No Spinning icon].")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateScheduleSmartCardsAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",
	        		schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , false);
	        
	        String budgetSmartCardText = "Budget Hours";
	        String scheduleSmartCardText = "SCHEDULE V";
	        String holidaySmartCardText = "holiday";
            String complianceSmartCardText = "requires compliance review";
            String unassignedSmartCardText = "unassigned";
            String weatherSmartCardText = "WEATHER";
            
	        int weeksToValidate = 6;
	        schedulePage.clickOnWeekView();
	        // Validation Start with Past week
	        schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Previous.getValue(), weekCount.One.getValue());
	        for(int index = 0; index < weeksToValidate; index++)
	        {
	        	if(index != 0)
	        		schedulePage.navigateWeekViewOrDayViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        	boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
		        if(!isActiveWeekGenerated)
		        	schedulePage.generateSchedule();
		        boolean isBudgetSmartCardLoaded = schedulePage.isSmartCardAvailableByLabel(budgetSmartCardText);
		        boolean isScheduleSmartCardLoaded = schedulePage.isSmartCardAvailableByLabel(scheduleSmartCardText);
		        boolean isHolidaySmartCardLoaded = schedulePage.isSmartCardAvailableByLabel(holidaySmartCardText);
		        boolean isComplianceSmartCardLoaded = schedulePage.isSmartCardAvailableByLabel(complianceSmartCardText);
		        boolean isUnassignedSmartCardLoaded = schedulePage.isSmartCardAvailableByLabel(unassignedSmartCardText);
		        boolean isWeatherSmartCardLoaded = schedulePage.isSmartCardAvailableByLabel(weatherSmartCardText);
		        
		        if(isBudgetSmartCardLoaded)
		        	SimpleUtils.report("Schedule Page: Budget Smartcard loaded successfully for the week - '"+ schedulePage.getActiveWeekText() +"'.");
		        
		        if(isScheduleSmartCardLoaded)
		        	SimpleUtils.report("Schedule Page: Scheduled Smartcard loaded successfully for the week - '"+ schedulePage.getActiveWeekText() +"'.");
		        
		        if(isHolidaySmartCardLoaded)
		        	SimpleUtils.report("Schedule Page: Holiday Smartcard loaded successfully for the week - '"+ schedulePage.getActiveWeekText() +"'.");
		        
		        if(isComplianceSmartCardLoaded && schedulePage.isComlianceReviewRequiredForActiveWeek())
		        	SimpleUtils.report("Schedule Page: Compliance Smartcard loaded successfully for the week - '"+ schedulePage.getActiveWeekText() +"'.");
		        else if(! isComplianceSmartCardLoaded && schedulePage.isComlianceReviewRequiredForActiveWeek())
		        	SimpleUtils.fail("Schedule Page: Compliance Smartcard not loaded even complaince review required for Active week ('"
		        			+ schedulePage.getActiveWeekText() +"').", true);
		        if(isUnassignedSmartCardLoaded)
		        	SimpleUtils.report("Schedule Page: Unassigned Smartcard loaded successfully for the week - '"+ schedulePage.getActiveWeekText() +"'.");
		        
		        if(isWeatherSmartCardLoaded)
		        	SimpleUtils.report("Schedule Page: Weather Smartcard loaded successfully for the week - '"+ schedulePage.getActiveWeekText() +"'.");
	        }
	    }
	    
	    @Automated(automated =  "Automated")
	    @Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-145 : Validate schedule publish feature[Check by publishing one weeks schedule].")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateSchedulePublishFeatureAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	    	schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	    	SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!"
	    			,schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	    	
	    	ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	    	BasePage basePase = new BasePage();
	        List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
	        boolean isWeekFoundToPublish = false;
	        for(WebElement overviewWeek : overviewWeeks)
	        {
	        	if(overviewWeek.getText().contains(overviewWeeksStatus.Draft.getValue()))
	        	{
	        		isWeekFoundToPublish = true;
	        		basePase.click(overviewWeek);
	        		schedulePage.publishActiveSchedule();
	        		SimpleUtils.assertOnFail("Schedule not published for week: '"+ schedulePage.getActiveWeekText() +"'"
	        				,schedulePage.isWeekPublished(), false);
	        		break;
	        	}
	        	else if(overviewWeek.getText().contains(overviewWeeksStatus.Guidance.getValue()))
	        	{
	        		isWeekFoundToPublish = true;
	        		basePase.click(overviewWeek);
	        		schedulePage.generateSchedule();
	        		SimpleUtils.assertOnFail("Schedule not Generated for week: '"+ schedulePage.getActiveWeekText() +"'"
	        				, schedulePage.isWeekGenerated(), false);
	        		schedulePage.publishActiveSchedule();
	        		Thread.sleep(2000);
	        		SimpleUtils.assertOnFail("Schedule not published for week: '"+ schedulePage.getActiveWeekText() +"'"
	        				, schedulePage.isWeekPublished(), false);
	        		break;
	        	}
	        }
	        
	        if(! isWeekFoundToPublish)
	        	SimpleUtils.report("No Draft/Guidance week found.");
	    }
	    
	    @Automated(automated =  "Automated")
	    @Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-148 : Validate Schedule ungenerate feature.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateScheduleUngenerateFeatureAsInternalAdmin(String browser, String username, String password, String location)
	    		throws Exception {
	    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	    	schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	    	SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!"
	    			,schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	    	
	    	ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	    	BasePage basePase = new BasePage();
	        List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
	        boolean isWeekFoundToUnGenerate = false;
	        for(WebElement overviewWeek : overviewWeeks)
	        {
	        	if(! overviewWeek.getText().contains(overviewWeeksStatus.Guidance.getValue()))
	        	{
	        		String weekStatus = overviewWeek.getText();
	        		isWeekFoundToUnGenerate = true;
	        		basePase.click(overviewWeek);
	        		boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
	        		SimpleUtils.assertOnFail("Schedule with status: '" + weekStatus + "' not Generated for week: '"+ schedulePage.getActiveWeekText() +"'"
	        				, isActiveWeekGenerated, false);
	        		schedulePage.unGenerateActiveScheduleScheduleWeek();
	        		isActiveWeekGenerated = schedulePage.isWeekGenerated();
	        		if(! isActiveWeekGenerated)
	        			SimpleUtils.pass("Schedule Page: Schedule week for duration:'"+ schedulePage.getActiveWeekText() +"' UnGenerated Successfully.");
	        		else
	        			SimpleUtils.fail("Schedule Page: Schedule week for duration:'"+ schedulePage.getActiveWeekText() +"' not UnGenerated.", false);
	        		break;
	        	}
	        }
	        
	        if(! isWeekFoundToUnGenerate)
	        	SimpleUtils.report("No Draft/Published/Finalized week found to Ungenerate Schedule.");
	    }
	    
	    
	    @Automated(automated =  "Automated")
	    @Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-146 : Validate Today's Forecast should have a non-0 number[If zero, it should be for the day store is closed]")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void validateDashboardTodaysForcastAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        HashMap<String, Float> todaysForcastData = dashboardPage.getTodaysForcastData();
	        if(todaysForcastData.size() > 0)
	        {
	        	float demandForecast = todaysForcastData.get("demandForecast");
	        	float guidanceHours = todaysForcastData.get("guidanceHours");
	        	SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
		        boolean isStoreClosedToday = schedulePage.isStoreClosedForActiveWeek();
		        if(! isStoreClosedToday && (demandForecast <= 0))
		        	SimpleUtils.fail("Dashboard Page: Today's Forecast contains '0' Demand Forecast.", true);
		        else
		        	SimpleUtils.pass("Dashboard Page: Today's Forecast contains '"+ (int) demandForecast +"' Shoppers.");
		        
		        if(! isStoreClosedToday && (guidanceHours <= 0))
		        	SimpleUtils.fail("Dashboard Page: Today' Forecast contains '0' Guidance Hours.", true);
		        else
		        	SimpleUtils.pass("Dashboard Page: Today's Forecast contains '"+ guidanceHours +"' Guidance Hours.");
 	        }	        
	    }
	    
	    @Automated(automated =  "Automated")
	    @Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-156 : Schedule :- Verify whether schedule timing is getting reflected once user changes timing locally.")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void changeAndValidateOperatingHoursAsInternalAdmin(String browser, String username, String password, String location)
	    		throws Exception {
	    	DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        schedulePage.clickOnWeekView();
	        if(!schedulePage.isWeekGenerated())
	        	schedulePage.generateSchedule();
	        
	        schedulePage.clickOnDayView();
	        System.out.println("Active Duration : "+schedulePage.getActiveWeekText());
	        schedulePage.toggleSummaryView();
	        if(schedulePage.isSummaryViewLoaded())
	        {
	        	String day = schedulePage.getActiveWeekText().split(" ")[0];
	        	String startTime = "10:00am";
	        	String endTime = "6:00pm";
	        	schedulePage.updateScheduleOperatingHours(day, startTime, endTime);
	        	schedulePage.toggleSummaryView();
	        	boolean isOperatingHoursUpdated = schedulePage.isScheduleOperatingHoursUpdated(startTime, endTime);
	        	if(isOperatingHoursUpdated)
	        		SimpleUtils.pass("Updated Operating Hours Reflecting on Schedule page.");
	        	else
	        		SimpleUtils.fail("Updated Operating Hours not Reflecting on Schedule page.", false);
	        }
	        else
	        	SimpleUtils.fail("Unable to load Summary view on the week '"+ schedulePage.getActiveWeekText() +"'.", false);
	    }

	@Automated(automated = "Automated")
	@Owner(owner = "Nishant")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "TP-18: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
	public void verifyOpenScheduleViewAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {
		int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
//            loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
		DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
		SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
		schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
		SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()), true);
		schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
//		schedulePage.beforeEdit();
		schedulePage.clickImmediateNextToCurrentActiveWeekInDayPicker();
		schedulePage.clickOnEditButton();
//		schedulePage.viewProfile();
//		schedulePage.changeWorkerRole();
//		schedulePage.assignTeamMember();
		schedulePage.convertToOpenShift();



	}
	    
    }
