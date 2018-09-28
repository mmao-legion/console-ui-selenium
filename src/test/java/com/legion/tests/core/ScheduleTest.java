package com.legion.tests.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.legion.pages.DashboardPage;
import com.legion.pages.LoginPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ScheduleTest extends TestBase{
	  private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

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
	  
	  
		@Automated(automated = "Automated")
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
	        System.out.println("scheduleWeekScheduledHours: "+scheduleWeekScheduledHours);
	        System.out.println("scheduleWeekBudgetedHours: "+scheduleWeekBudgetedHours);
	        System.out.println("scheduleWeekOtherHours: "+scheduleWeekOtherHours);
	        System.out.println("scheduleWeekWagesBudgetedCount: "+scheduleWeekWagesBudgetedCount);
	        System.out.println("scheduleWeekWagesScheduledCount: "+scheduleWeekWagesScheduledCount);
	        
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
	        

	        if(scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal)/*scheduleWeekScheduledHours == scheduleDaysScheduledHoursTotal*/) {
	        	SimpleUtils.pass("Week Scheduled Hours are matched with Sum of Days Scheduled Hours ("+scheduleWeekScheduledHours+"/"+scheduleDaysScheduledHoursTotal+")");
	        }
	        else {
		        SimpleUtils.assertOnFail("Week Scheduled Hours not matched with Sum of Days Scheduled Hours (" +scheduleWeekScheduledHours+"/"+scheduleDaysScheduledHoursTotal+ ")", scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal), true);
	        }
	        
	        if(scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal)/*scheduleWeekBudgetedHours == scheduleDaysBudgetedHoursTotal*/) {
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
	        LoginPage loginPage = pageFactory.createConsoleLoginPage();
	        loginPage.goToDashboardHome(propertyMap);
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        SchedulePage schedulePage = dashboardPage.goToToday();
	        SimpleUtils.assertOnFail("Today's Schedule not loaded Successfully!",schedulePage.varifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        
	        //Schedule overview should show 5 week's schedule
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        List<String> scheduleOverviewWeeksStatus = schedulePage.getScheduleWeeksStatus();
	        int overviewWeeksStatusCount = scheduleOverviewWeeksStatus.size();
	        for(String overviewWeeksStatus: scheduleOverviewWeeksStatus)
	        {
	        	System.out.println("overviewWeeksStatus: "+overviewWeeksStatus);
	        }
	        //Must have at least "Past Week" schedule published
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.getValue());
	        schedulePage.navigateSalesForecastWeekViewToPastOrFuture(weekViewType.Previous.getValue(), weekCount.One.getValue());
	        SimpleUtils.assertOnFail("Schedule Page: Past week not generated!",schedulePage.isWeekGenerated() , true);
	        SimpleUtils.assertOnFail("Schedule Page: Past week not Published!",schedulePage.isWeekGenerated() , true);
	        
	        
	        //there are at least one week in the future where schedule has not yet been published
	        schedulePage.navigateSalesForecastWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        for(int index = 1; index < weekCount.values().length; index++)
	        {
	        	schedulePage.navigateSalesForecastWeekViewToPastOrFuture(weekViewType.Next.getValue(), weekCount.One.getValue());
	        	if(! schedulePage.isWeekGenerated())
	        	{
	        		//schedulePage.generateSchedule();
	        		//break;
	        	}
	        }
	    }
	    
	    @Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@TestName(description = "LEG-4977: Republish Button is missing for finalized week (Oct 07- Oct 13)")
	    @Test(dataProvider = "browsers")
	    public void shouldRepublishButtonDisplyedForFinalizedWeek(String browser, String version, String os, String pageobject)
	            throws Exception
	    {
			SimpleUtils.pass("Login to leginTech Successfully");
			SimpleUtils.pass("Successfully opened the Schedule app");
			SimpleUtils.pass("Select Oct 07 - Oct 13 which is finalized week present in Schedule Overview");
			SimpleUtils.fail("Republish button is missing",false); 
			
	    }
	    
	    
	   
}
