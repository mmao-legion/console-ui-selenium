package com.legion.tests.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.ScheduleOverviewPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.core.ScheduleTest.SchedulePageSubTabText;
import com.legion.tests.core.ScheduleTest.overviewWeeksStatus;
import com.legion.tests.core.ScheduleTest.scheduleHoursAndWagesData;
import com.legion.tests.core.ScheduleTest.weekCount;
import com.legion.tests.core.ScheduleTest.weekViewType;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class BartenderScheduleTest extends TestBase{
	  private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	  private static HashMap<String, String> enterpriseCoffee2Credentials = JsonUtil.getPropertiesFromJsonFile("src/test/resources/enterpriseCoffee2Credentials.json");

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
	  
	  
		/*@Automated(automated = "Automated")
		@Owner(owner = "Naval")
	    @TestName(description = "TP-33: Hours and Wage calculation on Console-UI")
	    @Test(dataProvider = "browsers")
	    public void hoursAndWagesCalculationOnSchedulePage(String browser, String version, String os, String pageobject)
	    		throws Exception {
	        LoginPage loginPage = pageFactory.createConsoleLoginPage();
	        loginPage.goToDashboardHome(propertyMap);
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        SchedulePage schedulePage = dashboardPage.goToToday();
	        SimpleUtils.assertOnFail("Today's Schedule not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        //get Week view Hours & Wages
	        schedulePage.clickOnWeekView();
	        Map<String, Float> scheduleWeekViewLabelData = schedulePage.getScheduleLabelHoursAndWagges();
	        Float scheduleWeekScheduledHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.scheduledHours.getValue());
	        Float scheduleWeekBudgetedHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.budgetedHours.getValue());
	        Float scheduleWeekOtherHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.otherHours.getValue());
	        Float scheduleWeekWagesBudgetedCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue());
	        Float scheduleWeekWagesScheduledCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue());
	        
	        //get days hours & Wages for current week
	        schedulePage.clickOnDayView();
	        List<Map<String, Float>>  scheduleDaysViewLabelDataForWeekDays = schedulePage.getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek();
	        Float scheduleDaysScheduledHoursTotal = (float) 0;
	        Float scheduleDaysBudgetedHoursTotal = (float) 0;
	        Float scheduleDaysOtherHoursTotal = (float) 0;
	        Float scheduleDaysWagesBudgetedCountTotal = (float) 0;
	        Float scheduleDaysWagesScheduledCountTotal = (float) 0;
	        for(Map<String, Float> scheduleDaysViewLabelDataForWeekDay : scheduleDaysViewLabelDataForWeekDays)
	        {
	        	scheduleDaysScheduledHoursTotal = scheduleDaysScheduledHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.scheduledHours.getValue());
	        	scheduleDaysBudgetedHoursTotal = scheduleDaysBudgetedHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.budgetedHours.getValue());
	        	scheduleDaysOtherHoursTotal = scheduleDaysOtherHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.otherHours.getValue());
	        	scheduleDaysWagesBudgetedCountTotal = scheduleDaysWagesBudgetedCountTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue());
	        	scheduleDaysWagesScheduledCountTotal = scheduleDaysWagesScheduledCountTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue());
	        }

	        // Week Summary = Sum of Day Summary 
	        
	        // Comparing Week Scheduled Hours and Sum of Days Scheduled Hours
	        

	        if(scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal)scheduleWeekScheduledHours == scheduleDaysScheduledHoursTotal) {
	        	SimpleUtils.pass("Week Scheduled Hours are matched with Sum of Days Scheduled Hours ("+scheduleWeekScheduledHours+"/"+scheduleDaysScheduledHoursTotal+")");
	        }
	        else {
		        SimpleUtils.assertOnFail("Week Scheduled Hours not matched with Sum of Days Scheduled Hours (" +scheduleWeekScheduledHours+"/"+scheduleDaysScheduledHoursTotal+ ")", scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal), true);
	        }
	        
	        if(scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal)scheduleWeekBudgetedHours == scheduleDaysBudgetedHoursTotal) {
	        	SimpleUtils.pass("Week Scheduled Hours are matched with Sum of Days Scheduled Hours ("+scheduleWeekBudgetedHours+"/"+scheduleDaysBudgetedHoursTotal);
	        }
	        else {
		        SimpleUtils.assertOnFail("Week Budgeted Hours not matched with Sum of Days Budgeted Hours (" +scheduleWeekScheduledHours+ "/" + scheduleDaysBudgetedHoursTotal + ")", scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal), true);
	        }
	        
	  }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @TestName(description = "LEG-2424: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	    @Test(dataProvider = "browsers")
	    public void reviewPastGenerateCurrentAndFutureWeekSchedule(String browser, String version, String os, String pageobject)
	    		throws Exception {
	    	int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
	    	System.out.println("overviewTotalWeekCount: "+overviewTotalWeekCount);
	        LoginPage loginPage = pageFactory.createConsoleLoginPage();
	        loginPage.goToDashboardHome(propertyMap);
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        
	        //Schedule overview should show 5 week's schedule
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	        List<String> scheduleOverviewWeeksStatus = scheduleOverviewPage.getScheduleWeeksStatus();
	        int overviewWeeksStatusCount = scheduleOverviewWeeksStatus.size();
	        SimpleUtils.assertOnFail("Schedule overview Page not dispaying upcomming 5 weeks",(overviewWeeksStatusCount < overviewTotalWeekCount) , true);
	        System.out.println("overviewWeeksStatusCount: "+overviewWeeksStatusCount);
	        for(String overviewWeeksStatusText: scheduleOverviewWeeksStatus)
	        {
	        	int index = scheduleOverviewWeeksStatus.indexOf(overviewWeeksStatusText);
		        SimpleUtils.assertOnFail("Schedule overview Page upcoming week on index '"+index+"' is 'Not Available'",(! overviewWeeksStatusText.contains(overviewWeeksStatus.NotAvailable.getValue())) , true);
	        	System.out.println("overviewWeeksStatus: "+overviewWeeksStatusText);
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
	        SimpleUtils.assertOnFail("Generate Button dispaying for Past week", (! schedulePage.isGenerateButtonLoaded()) , true);

	        
	        //there are at least one week in the future where schedule has not yet been published
	        schedulePage.clickOnWeekView();
	        schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        
	        // to do - 
	        for(int index = 1; index < weekCount.values().length; index++)
	        {
	        	schedulePage.navigateWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        	if(! schedulePage.isWeekGenerated()){
	        	        SimpleUtils.assertOnFail("Schedule Page: Future week '"+schedulePage.getScheduleWeekStartDayMonthDate()+"' not Generated!",schedulePage.isWeekPublished() , true);
	        	}
	        	else {
	        		if(! schedulePage.isWeekPublished()){
	        	        SimpleUtils.assertOnFail("Schedule Page: Future week '"+schedulePage.getScheduleWeekStartDayMonthDate()+"' not Published!",schedulePage.isWeekPublished() , true);
	        		}
	        	}
	        }  
	    }*/
	    
	  @Owner(owner = "Naval")
	  @TestName(description = "Login as Bartender, navigate & verify Schedule page")
	  @Test (groups = { "loginToLegion" })
	  public void scheduleTestAsBartender()
	  {
		  System.out.println("scheduleTestAsBartender called");
		  loginToLegion();
		  navigateToSchedulePage();
	  }

	  
	  public void loginToLegion()
	  {
		  System.out.println("loginToLegion called");
		  try {
			loginToLegionAndVerifyIsLoginDone(enterpriseCoffee2Credentials.get("BARTENDER_USERNAME"), enterpriseCoffee2Credentials.get("BARTENDER_PASSWORD"));
		} catch (Exception e) {
			SimpleUtils.fail("Login to legion app failed!", false);
		}
	  }
	  
	  public void navigateToSchedulePage()
	  {
		  SchedulePage schedulePage = pageFactory.createConsoleSchedulePage();
	      schedulePage.clickOnScheduleConsoleMenuItem();
	  }
	    
	   
}
