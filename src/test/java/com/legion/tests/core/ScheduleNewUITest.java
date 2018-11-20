package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.legion.pages.DashboardPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
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
		  Schedule("SCHEDULE");
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
		  groupbyWorkRole(" Group by Work Role"),
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
}
