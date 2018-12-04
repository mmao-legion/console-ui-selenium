package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.legion.pages.BasePage;
import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.pages.StaffingGuidancePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
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

	public enum dayCount{
		Seven(7);
		private final int value;
		dayCount(final int newValue) {
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
	        schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Previous.getValue(), weekCount.One.getValue());
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
	        schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        for(int index = 1; index < weekCount.values().length; index++)
	        {
	        	schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
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

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "KendraScott2_Enterprise")
	    @TestName(description = "TP-21: Automation Script for - JIRA ID - 2592 - \"Should be able to view Day View and filter Schedule and Group By 'All'\"")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void viewAndFilterScheduleWithGroupByAllDayViewAsStoreManager(String browser, String username, String password, String location)
	    		throws Exception {
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = dashboardPage.goToTodayForNewUI();
	        SimpleUtils.assertOnFail("'Schedule' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);

	        /*
	         *  Navigate to Schedule Day view
	         */
	        boolean isWeekView = false;
	        schedulePage.clickOnDayView();;
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
	        	if(weekStatus.toLowerCase().equals(overviewWeeksStatus.Draft.getValue().toLowerCase()))
	        		SimpleUtils.assertOnFail("UnPublished week:'"+ schedulePage.getActiveWeekText()
	        		+ "' Schedule is visible for Team Lead", ! isSchedulePublished, true);
	        	else
	        		SimpleUtils.assertOnFail("Published week:'"+ schedulePage.getActiveWeekText()
	        		+"' Schedule is not visible for Team Lead", isSchedulePublished, true);
				schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
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
	        	if(weekStatus.toLowerCase().equals(overviewWeeksStatus.Draft.getValue().toLowerCase()))
	        		SimpleUtils.assertOnFail("UnPublished week:'"+ schedulePage.getActiveWeekText()
	        		+"' Schedule is visible for Team Member", ! isSchedulePublished, true);
	        	else
	        	{
	        		boolean isWeekAssignedToUser = schedulePage.isActiveWeekAssignedToCurrentUser(String.valueOf(teamMemberCredentials[0][0]));
	        		SimpleUtils.assertOnFail("Published week:'"+ schedulePage.getActiveWeekText()
	        		+"' Schedule is not visible for Team Member", isSchedulePublished, true);
	        	}
	        	schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
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
					schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

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
//	    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);

	        //Must have at least "Past Week" schedule published
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Previous.getValue(), weekCount.One.getValue());
	        SimpleUtils.assertOnFail("Schedule Page: Past week not generated!",schedulePage.isWeekGenerated() , true);
	        SimpleUtils.assertOnFail("Schedule Page: Past week not Published!",schedulePage.isWeekPublished() , true);
	        //The schedules that are already published should remain unchanged
	        schedulePage.clickOnDayView();
	        schedulePage.navigateDayViewToPast(weekViewType.Previous.getValue(), dayCount.Seven.getValue());
	        schedulePage.clickOnEditButton();
	        SimpleUtils.assertOnFail("User can add new shift for past week", (! schedulePage.isAddNewDayViewShiftButtonLoaded()) , false);
//	        schedulePage.clickOnCancelButtonOnEditMode();

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
//	        schedulePage.clickRadioBtnStaffingOption(staffingOption.ManualShift.getValue());
//	        schedulePage.clickRadioBtnStaffingOption(staffingOption.AssignTeamMemberShift.getValue());
	        schedulePage.clickOnCreateOrNextBtn();
	        int updatedGutterCount = schedulePage.getgutterSize();
	        List<String> previousTeamCount = schedulePage.calculatePreviousTeamCount(shiftTimeSchedule,teamCount);
	        List<String> currentTeamCount = schedulePage.calculateCurrentTeamCount(shiftTimeSchedule);
	        verifyTeamCount(previousTeamCount,currentTeamCount);
	        schedulePage.clickSaveBtn();
	        schedulePage.clickOnVersionSaveBtn();
	        schedulePage.clickOnPostSaveBtn();
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
	        schedulePage.clickOnVersionSaveBtn();
	        schedulePage.clickOnPostSaveBtn();
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
	        schedulePage.clickOnVersionSaveBtn();
	        schedulePage.clickOnPostSaveBtn();
	        HashMap<String, Float> editScheduledHours = schedulePage.getScheduleLabelHours();
	        Float scheduledHoursAfterEditing = editScheduledHours.get("scheduledHours");
	        verifyScheduleLabelHours(shiftTimeSchedule.get("ScheduleHrDifference"), scheduledHoursBeforeEditing, scheduledHoursAfterEditing);

	    }

	    public void verifyScheduleLabelHours(String shiftTimeSchedule,
	  			Float scheduledHoursBeforeEditing, Float scheduledHoursAfterEditing) throws Exception{
	  			Float scheduledHoursExpectedValueEditing = 0.0f;
	  		if(Float.parseFloat(shiftTimeSchedule) >= 6){
	  			scheduledHoursExpectedValueEditing = (float) (scheduledHoursBeforeEditing + (Float.parseFloat(shiftTimeSchedule) - 0.5));
	  			System.out.println("Scheduled hour is "+scheduledHoursAfterEditing);
	  		}else{
	  			scheduledHoursExpectedValueEditing = (float)(scheduledHoursBeforeEditing + Float.parseFloat(shiftTimeSchedule));
	  			System.out.println("Scheduled hour is "+scheduledHoursAfterEditing);
	  		}

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
		    	 schedulePage.clickOnVersionSaveBtn();
		    	 schedulePage.clickOnPostSaveBtn();
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
			for(int index = 0; index < scheduleOverViewStatusCount; index++)
			{
				String status = overviewPageScheduledWeekStatus.get(index);
				if(index != 0)
					schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

				SimpleUtils.report("Selected Week: '"+schedulePage.getActiveWeekText()+"'");
				/*
				 * Assertions: First week have 1 day closed
				 */

				if(status.toLowerCase().equals(overviewWeeksStatus.Guidance.getValue().toLowerCase())
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
				else if(status.toLowerCase().equals(overviewWeeksStatus.Guidance.getValue().toLowerCase())) {

					System.out.println("Active Week: '"+schedulePage.getActiveWeekText()+"' Not Generated.");

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
	        schedulePage.clickOnWeekView();
	        schedulePage.selectGroupByFilter(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
	        schedulePage.isScheduleGroupByWorkRole(scheduleGroupByFilterOptions.groupbyWorkRole.getValue());
	        schedulePage.selectWorkRoleFilterByIndex(workRoleIndex);
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
					schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());

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
				String compareWeekStatus = "draft";
				if(overviewScheduleWeek.getText().toLowerCase().contains(compareWeekStatus))
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

    }



