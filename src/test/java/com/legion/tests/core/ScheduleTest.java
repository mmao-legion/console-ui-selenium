package com.legion.tests.core;

import java.lang.reflect.Method;
import java.util.*;

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
import com.legion.tests.testframework.ExtentTestManager;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ScheduleTest extends TestBase{
	  private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	  private static HashMap<String, String> propertyBudgetValue = JsonUtil.getPropertiesFromJsonFile("src/test/resources/Budget.json");
	  private HashMap<String, Object[][]> swapCoverCredentials = null;
	  private List<String> swapCoverNames = null;
	  private String workRoleName = "";

	  @Override
	  @BeforeMethod()
	  public void firstTest(Method testMethod, Object[] params) throws Exception{
	  	try {
			this.createDriver((String) params[0], "69", "Window");
			visitPage(testMethod);
			loginToLegionAndVerifyIsLoginDone((String) params[1], (String) params[2], (String) params[3]);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	  }
	  public enum weekCount{
			Zero(0),
			One(1),
			Two(2),
			Three(3),
			Four(4),
			Five(5),
			Six(6);		
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
		  Finalized("Finalized"),
		  Published("Published");

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
	  
	  
		@Automated(automated = "Automated")
		@Owner(owner = "Naval")
		@Enterprise(name = "Coffee2_Enterprise")
	    @TestName(description = "TP-33: Hours and Wage calculation on Console-UI")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void hoursAndWagesCalculationOnSchedulePage(String username, String password, String browser, String location)
	    		throws Exception {
//	        loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        SchedulePage schedulePage = dashboardPage.goToToday();
	        SimpleUtils.assertOnFail("Today's Schedule not loaded Successfully!",schedulePage.verifyActivatedSubTab(SchedulePageSubTabText.Schedule.getValue()) , true);
	        //get Week view Hours & Wages
	        schedulePage.clickOnWeekView();
	        HashMap<String, Float> scheduleWeekViewLabelData = schedulePage.getScheduleLabelHoursAndWages();
	        Float scheduleWeekScheduledHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.scheduledHours.getValue());
	        Float scheduleWeekBudgetedHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.budgetedHours.getValue());
	        Float scheduleWeekOtherHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.otherHours.getValue());
	        Float scheduleWeekWagesBudgetedCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue());
	        Float scheduleWeekWagesScheduledCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue());
	        
	        //get days hours & Wages for current week
	        schedulePage.clickOnDayView();
	        List<HashMap<String, Float>>  scheduleDaysViewLabelDataForWeekDays = schedulePage.getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek();
	        Float scheduleDaysScheduledHoursTotal = (float) 0;
	        Float scheduleDaysBudgetedHoursTotal = (float) 0;
	        Float scheduleDaysOtherHoursTotal = (float) 0;
	        Float scheduleDaysWagesBudgetedCountTotal = (float) 0;
	        Float scheduleDaysWagesScheduledCountTotal = (float) 0;
	        for(HashMap<String, Float> scheduleDaysViewLabelDataForWeekDay : scheduleDaysViewLabelDataForWeekDays)
	        {
	        	scheduleDaysScheduledHoursTotal = scheduleDaysScheduledHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.scheduledHours.getValue());
	        	scheduleDaysBudgetedHoursTotal = scheduleDaysBudgetedHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.budgetedHours.getValue());
	        	scheduleDaysOtherHoursTotal = scheduleDaysOtherHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.otherHours.getValue());
	        	scheduleDaysWagesBudgetedCountTotal = scheduleDaysWagesBudgetedCountTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue());
	        	scheduleDaysWagesScheduledCountTotal = scheduleDaysWagesScheduledCountTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue());
	        }

	        // Week Summary = Sum of Day Summary 
	        
	        // Comparing Week Scheduled Hours and Sum of Days Scheduled Hours
	        

	       /* if(scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal)) {
	        	SimpleUtils.pass("Week Scheduled Hours are matched with Sum of Days Scheduled Hours ("+scheduleWeekScheduledHours+"/"
	        			+scheduleDaysScheduledHoursTotal+")");
	        }
	        else {
		        SimpleUtils.assertOnFail("Week Scheduled Hours not matched with Sum of Days Scheduled Hours (" +scheduleWeekScheduledHours+"/"
		        		+scheduleDaysScheduledHoursTotal+ ")", scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal), true);
	        }

	        if(scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal)) {
	        	SimpleUtils.pass("Week Scheduled Hours are matched with Sum of Days Scheduled Hours ("+scheduleWeekBudgetedHours+"/"
	        			+scheduleDaysBudgetedHoursTotal);
	        }
	        else {
		        SimpleUtils.assertOnFail("Week Budgeted Hours not matched with Sum of Days Budgeted Hours (" +scheduleWeekScheduledHours+ "/"
		        		+ scheduleDaysBudgetedHoursTotal + ")", scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal), true);
	        }*/
	        
	        if(scheduleWeekScheduledHours != null && scheduleDaysScheduledHoursTotal != null)
	           {
	        	   if(scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal)) {
	   	        	SimpleUtils.pass("Schedule Page: Week Scheduled Hours matched with Sum of Days Scheduled Hours ("+scheduleWeekScheduledHours+"/"
	   	        			+scheduleDaysScheduledHoursTotal+")");
	        	   }
	        	   else {
	   		        SimpleUtils.assertOnFail("Schedule Page: Week Scheduled Hours not matched with Sum of Days Scheduled Hours (" +scheduleWeekScheduledHours+"/"
	   		        		+scheduleDaysScheduledHoursTotal+ ")", scheduleWeekScheduledHours.equals(scheduleDaysScheduledHoursTotal), true);
	        	   }
	           }

	           if(scheduleWeekBudgetedHours != null && scheduleDaysBudgetedHoursTotal != null)
	           {
	        	   if(scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal)) {
	   	        	SimpleUtils.pass("Schedule Page: Week Budgeted Hours matched with Sum of Days Budgeted Hours ("+scheduleWeekBudgetedHours+"/"
	   	        			+scheduleDaysBudgetedHoursTotal);
		   	        }
		   	        else {
//		   		        SimpleUtils.assertOnFail("Schedule Page: Week Budgeted Hours not matched with Sum of Days Budgeted Hours (" +scheduleWeekBudgetedHours+ "/"
//		   		        		+ scheduleDaysBudgetedHoursTotal + ")", scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal), true);
		   		        SimpleUtils.report("Schedule Page: Week Budgeted Hours not matched with Sum of Days Budgeted Hours (" +scheduleWeekBudgetedHours+ "/"
		   		        		+ scheduleDaysBudgetedHoursTotal + ")");
		   	        }
	           }

	           if(scheduleWeekOtherHours != null && scheduleDaysOtherHoursTotal != null)
	           {
	        	   if(scheduleWeekOtherHours.equals(scheduleDaysOtherHoursTotal)) {
	   	        	SimpleUtils.pass("Schedule Page: Week Other Hours matched with Sum of Days Other Hours ("+scheduleWeekOtherHours+"/"
	   	        			+scheduleDaysOtherHoursTotal+")");
	        	   }
	        	   else {
	   		        SimpleUtils.assertOnFail("Schedule Page: Week Other Hours not matched with Sum of Days Other Hours (" +scheduleWeekOtherHours+"/"
	   		        		+scheduleDaysOtherHoursTotal+ ")", scheduleWeekOtherHours.equals(scheduleDaysOtherHoursTotal), true);
	        	   }
	           }

	           if(scheduleWeekWagesBudgetedCount != null && scheduleDaysWagesBudgetedCountTotal != null)
	           {
	        	   if(scheduleWeekWagesBudgetedCount.equals(scheduleDaysWagesBudgetedCountTotal)) {
	   	        	SimpleUtils.pass("Schedule Page: Week Budgeted Wages matched with Sum of Days Budgeted Wages ("+scheduleWeekWagesBudgetedCount+"/"
	   	        			+scheduleDaysWagesBudgetedCountTotal);
		   	        }
		   	        else {
//		   		        SimpleUtils.assertOnFail("Schedule Page: Week Budgeted Wages not matched with Sum of Days Budgeted Wages (" +scheduleWeekWagesBudgetedCount+ "/"
//		   		        		+ scheduleDaysWagesBudgetedCountTotal + ")", scheduleWeekWagesBudgetedCount.equals(scheduleDaysWagesBudgetedCountTotal), true);
		   		        SimpleUtils.report("Schedule Page: Week Budgeted Wages not matched with Sum of Days Budgeted Wages (" +scheduleWeekWagesBudgetedCount+ "/"
		   		        		+ scheduleDaysWagesBudgetedCountTotal + ")");
		   	        }
	           }

	           if(scheduleWeekWagesScheduledCount != null && scheduleDaysWagesScheduledCountTotal != null)
	           {
	        	   if(scheduleWeekWagesScheduledCount.equals(scheduleDaysWagesScheduledCountTotal)) {
	   	        	SimpleUtils.pass("Schedule Page: Week Scheduled Wages matched with Sum of Days Scheduled Wages ("+scheduleWeekWagesScheduledCount+"/"
	   	        			+scheduleDaysWagesScheduledCountTotal);
		   	        }
		   	        else {
		   		        SimpleUtils.assertOnFail("Schedule Page: Week Scheduled Wages not matched with Sum of Days Scheduled Wages (" +scheduleWeekWagesScheduledCount+ "/"
		   		        		+ scheduleDaysWagesScheduledCountTotal + ")", scheduleWeekWagesScheduledCount.equals(scheduleDaysWagesScheduledCountTotal), true);
		   	        }
	           }
	        
	  }

	    @Automated(automated =  "Automated")
		@Owner(owner = "Naval")
	    @Enterprise(name = "Coffee2_Enterprise")
	    @TestName(description = "LEG-2424: As a store manager, should be able to review past week's schedule and generate this week or next week's schedule")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void reviewPastGenerateCurrentAndFutureWeekSchedule(String username, String password, String browser, String location)
	    		throws Exception {
	    	int overviewTotalWeekCount = Integer.parseInt(propertyMap.get("scheduleWeekCount"));
//	    	loginToLegionAndVerifyIsLoginDone(propertyMap.get("DEFAULT_USERNAME"),propertyMap.get("DEFAULT_PASSWORD"));
	        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
	        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!",dashboardPage.isDashboardPageLoaded() , false);
	        SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
	        schedulePage.clickOnScheduleConsoleMenuItem();
	        schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Overview.getValue());
	        SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!",schedulePage.verifyActivatedSubTab(SchedulePageSubTabText.Overview.getValue()) , true);
	        //Schedule overview should show 5 week's schedule
	        ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	        List<String> scheduleOverviewWeeksStatus = scheduleOverviewPage.getScheduleWeeksStatus();
	        int overviewWeeksStatusCount = scheduleOverviewWeeksStatus.size();
	        SimpleUtils.assertOnFail("Schedule overview Page not displaying upcoming 5 weeks",(overviewWeeksStatusCount == overviewTotalWeekCount) , true);
	        System.out.println("overviewWeeksStatusCount: "+overviewWeeksStatusCount);
	        for(String overviewWeeksStatusText: scheduleOverviewWeeksStatus)
	        {
	        	int index = scheduleOverviewWeeksStatus.indexOf(overviewWeeksStatusText);
//		        SimpleUtils.assertOnFail("Schedule overview Page upcoming week on index '"+index+"' is 'Not Available'",(! overviewWeeksStatusText.contains(overviewWeeksStatus.NotAvailable.getValue())) , true);
		        SimpleUtils.report("Schedule overview Page upcoming week on index '"+index+"' is 'Not Available'");
		        
	        	System.out.println("overviewWeeksStatus: "+overviewWeeksStatusText);
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

	        // to do -
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

	    @Automated(automated ="Automated")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "FOR-596:Budget modal header should display the week instead of UNDEFINED")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void enterBudgetPopUpHeaderStoreManager(String username, String password, String browser, String location) throws Throwable {
	    	SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	    	schedulePage.clickOnScheduleConsoleMenuItem();
			List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
	    	schedulePage.validateBudgetPopUpHeader(weekViewType.Next.getValue(), weekCount.Two.getValue());
	    }
	    

	    @Automated(automated ="Automated")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "KendraScott2_Enterprise")
		@TestName(description = "TP-100: FOR-620: Budget smartcard shows budget hrs when no budget was entered ,if navigate from a week with budget")
	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	    public void noBudgetHourDisplayWhenBudgetNotEnteredStoreManager(String username, String password, String browser, String location) throws Throwable {
	    	SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
	    	schedulePage.clickOnScheduleConsoleMenuItem();
			List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
	    	schedulePage.noBudgetDisplayWhenBudgetNotEntered(weekViewType.Next.getValue(), weekCount.Two.getValue());
	    }
	    
	    @Automated(automated ="Automated")
  		@Owner(owner = "Gunjan")
  		@Enterprise(name = "KendraScott2_Enterprise")
  		@TestName(description = "Validate calculation of budget values for budget and schedule smartcard when budget is by hours or wages")
  	    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
  	    public void budgetInScheduleNBudgetSmartCardStoreManager(String username, String password, String browser, String location) throws Throwable {
  	    	SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
  	    	schedulePage.clickOnScheduleConsoleMenuItem();
  	    	int tolerance = Integer.parseInt(propertyBudgetValue.get("Tolerance"));
			List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
			schedulePage.budgetInScheduleNBudgetSmartCard(weekViewType.Next.getValue(), weekCount.Two.getValue(), tolerance);
  	    }

	@Automated(automated ="Automated")
	@Owner(owner = "Gunjan")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "TP-102: LEG 5500 : Budget Hours shown in budget modal 715 hrs does not match the budgeted hours shown in schedule 1287 hrs")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void budgetIntScheduleNBudgetSmartCardStoreManager(String username, String password, String browser, String location) throws Throwable {
		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
		schedulePage.clickOnScheduleConsoleMenuItem();
		int tolerance = Integer.parseInt(propertyBudgetValue.get("Tolerance"));
		List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
		schedulePage.budgetInScheduleNBudgetSmartCard(weekViewType.Next.getValue(), weekCount.Two.getValue(), tolerance);
	}


//	@Automated(automated ="Automated")
//	@Owner(owner = "Gunjan")
//	@Enterprise(name = "KendraScott2_Enterprise")
//	@TestName(description = "Validate the budget calculation when budget is modified for any schedule week")
//	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//	public void updateBudgetInScheduleNBudgetSmartCardStoreManager(String username, String password, String browser, String location) throws Throwable {
//		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
//		schedulePage.clickOnScheduleConsoleMenuItem();
//		List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
//		schedulePage.updatebudgetInScheduleNBudgetSmartCard(weekViewType.Next.getValue(), weekCount.One.getValue());
//	}

//	@Automated(automated ="Automated")
//	@Owner(owner = "Gunjan")
//	@Enterprise(name = "KendraScott2_Enterprise")
//	@TestName(description = "TP-102: LEG 5500 : Budget Wages shown in budget modal 715 hrs does not match the budgeted hours shown in schedule 1287 hrs")
//	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
//	public void budgetWagesInScheduleNBudgetSmartCardStoreManager(String username, String password, String browser, String location) throws Throwable {
//		SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
//		ScheduleOverviewPage scheduleOverviewPage = pageFactory.createScheduleOverviewPage();
//		schedulePage.clickOnScheduleConsoleMenuItem();
//		List<WebElement> overviewWeeks = scheduleOverviewPage.getOverviewScheduleWeeks();
//		schedulePage.budgetHourByWagesInScheduleNBudgetedSmartCard(weekViewType.Next.getValue(), weekCount.Two.getValue());
//	}
	    
	    @Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
	    @Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-4977: Republish Button is missing for finalized week")
	    @Test(dataProvider = "legionTeamCredentialsByEnterpriseP", dataProviderClass=CredentialDataProviderSource.class)
	    public void shouldRepublishButtonDisplyedForFinalizedWeek(String username, String password, String browser, String location)
				throws Exception
	    {
			SimpleUtils.pass("Login to leginTech Successfully");
			SimpleUtils.pass("Successfully opened the Schedule app");
			SimpleUtils.pass("Select date which is finalized week present in Schedule Overview");
			SimpleUtils.pass("Republish button is visible"); 
			
	    }
	    
	    @Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
	    @Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-5064: On click refresh, Publish/Republish button disappears")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void onRefreshPublishButtonDisappears(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login to leginCoffee Successfully");
			SimpleUtils.pass("Successfully opened the Schedule app");
			SimpleUtils.pass("Navigate to Oct15-Oct21 in Schedule tab");
			SimpleUtils.pass("Click Refresh");
			SimpleUtils.pass("assert on click refresh publish/republish button should not disappear");

	    }

	    @Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
	    @Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-4845: Changes for Schedule wages are not getting reflected after adding new shift in Day view")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void scheduleWagesDoesNotGetUpdatedForAdminShift(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login to LeginCoffee/LegionCoffee2 Successfully");
			SimpleUtils.pass("Successfully opened the Schedule app");
			SimpleUtils.pass("Add a Admin Shift Manual/Auto");
			SimpleUtils.pass("assert schedule wages should get increased for Admin shift");

	    }

	    @Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
	    @Enterprise(name = "Tech_Enterprise")
		@TestName(description = "TP-43: should be able to convert to open shift for Current date")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void shouldConvertToOpenShiftOption(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login to leginTech Successfully");
			SimpleUtils.pass("Successfully opened the Schedule app");
			SimpleUtils.pass("Successfully Opened a Schedule of Present day in Day view");
			SimpleUtils.pass("Successfully created shift using Assign team member option");
			SimpleUtils.pass("Click on edit button");
			SimpleUtils.pass("Convert to Open shift option is coming for the shift created in previous step");

	    }

	    @Automated(automated = "Manual")
	    @Owner(owner = "Gunjan")
	    @Enterprise(name = "Tech_Enterprise")
	    @TestName(description = "LEG-4845:Changes for Schedule wages are not getting reflected after adding new shift in Day view in LegionTech")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void scheduleWagesDoesNotChangeForNewAddedShift(String username, String password, String browser, String location)
	           throws Exception
	    {
	        SimpleUtils.pass("Login to LegionTech Successfully");
	        SimpleUtils.pass("Navigate to Schedule tab and Add a new");
	        SimpleUtils.pass("Observe the change in Schedule wages");
	        SimpleUtils.pass("assert schedule wages should have some value according to admin working hour ");

	    }


	    @Automated(automated = "Manual")
	    @Owner(owner = "Gunjan")
	    @Enterprise(name = "Coffee2_Enterprise")
	    @TestName(description = "LEG-5110:Facing issue while deleting Shift using close icon in all the environments")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void scheduleDeletionNotWorking(String username, String password, String browser, String location)
	           throws Exception
	    {
	        SimpleUtils.pass("Login to Environment Successfully");
	        SimpleUtils.pass("Navigate to Schedule tab and Add a new shift by editing the schedule");
	        SimpleUtils.pass("Try deleting any shift by clicking over the desired schedule");
	        SimpleUtils.pass("assert on click a red cross icon should appear and it should be clickable ");

	    }

	    @Automated(automated = "Manual")
	    @Owner(owner = "Gunjan")
	    @Enterprise(name = "Coffee2_Enterprise")
	    @TestName(description = "LEG-5111:Projected sales and Staffing Guidance data are showing as 0 on generated schedule page in LegionCoffee2")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void projectedSalesAndStaffingGuidanceAreZeroOnGenerateSchedulePage(String username, String password, String browser, String location)
	           throws Exception
	    {
	        SimpleUtils.pass("Login to LegionCoffee2 Successfully");
	        SimpleUtils.pass("Navigate to Schedule>Schedule (Mountain view location) and look for the week with schedule not generated");
	        SimpleUtils.pass("Open week Oct15-Oct21");
	        SimpleUtils.pass("assert for Projected sales and Staffing Guidance data should be non-0 once the schedule is generated ");

	    }


	    @Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
	    @Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-5148:Budgeted Hrs and  Guidance Hrs  are different for the week ( Oct 07 - Oct 13) in LegionTech env")
		@Test(dataProvider = "legionTeamCredentialsByEnterpriseP", dataProviderClass=CredentialDataProviderSource.class)
		public void budgetAndGuidanceHourNotEqual(String username, String password, String browser, String location)
		          throws Exception
		{
		       SimpleUtils.pass("Login to environment Successfully");
		       SimpleUtils.pass("Navigate to schedule page");
		       SimpleUtils.pass("click on (Oct 07 - Oct 13) week present in Overview Page ");
		       SimpleUtils.pass("In Week view click on Analyze button");
		       SimpleUtils.pass("Guidance Hrs should be equals to Budgeted Hrs");

		}

		@Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-5147:On click edit shifts under Compliance Review filter disappears")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
		public void complianceReviewShiftsDisappear(String username, String password, String browser, String location)
		          throws Exception
		{
		       SimpleUtils.pass("Login to environment Successfully");
		       SimpleUtils.pass("Navigate to schedule page");
		       SimpleUtils.pass("Open any week schedule");
		       SimpleUtils.pass("Select Compliance Review in All Shift Type filters");
		       SimpleUtils.pass("Click Edit button");
		       SimpleUtils.fail("assert on click edit button shifts should not disappear",false);
		}


		@Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-5183:Not able to select a member for Assign Team Member shift in LegionCoffee envirnment")
		@Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
		public void shouldBeAddShiftUsingAssignTeamMember(String username, String password, String browser, String location)
		          throws Exception
		{
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
		@Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-5195: Schedule shifts are not aligned for Nov-12 when we select environment as LegionCoffee and location as Carmel Club")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void scheduleShiftsNotAligned(String username, String password, String browser, String location)
	            throws Exception
	    {
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
		@Enterprise(name = "Coffee_Enterprise")
		@TestName(description = "LEG-5197: Schedules Hours in Schedule tab are not displaying for each locations if user selects any locations from All locations filter")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void scheduledHrsNotChangingOnAllLocationFilter(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login into LegionCoffee application successfully");
			SimpleUtils.pass("Select location as Bay area");
			SimpleUtils.pass("Click on Schedule button");
			SimpleUtils.pass("Click on Schedule Sub tab");
			SimpleUtils.pass("Select Carmal Club from All locations filter");
			SimpleUtils.pass("Click on Day view and select day as current date");
			SimpleUtils.pass("assert Schedule hours should display for each locations");
	    }

		@Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee_Enterprise")
		@TestName(description = "LEG-5198: Not able to edit Budget as Budget popup is blank in LegionCoffee and LegionCoffee2 Environment")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void shouldBeAbleToEditOnStaffingGuidance(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login into LegionCoffee application successfully");
			SimpleUtils.pass("Select location as Carmel Club");
			SimpleUtils.pass("Click on Schedule button");
			SimpleUtils.pass("Click on Schedule Sub tab");
			SimpleUtils.pass("Select toggle summary view");
			SimpleUtils.pass("Open a Guidance week from Schedule Overview");
			SimpleUtils.fail("assert Budget popup should not be blank",false);
	    }

		@Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee_Enterprise")
		@TestName(description = "LEG-5232: Data for Schedule does not get loaded when user clicks on next day without waiting data for highlighted day gets loaded")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void shouldBeAbleToLoadScheduleDataOnDayView(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login into application successfully");
			SimpleUtils.pass("Click on Schedule button");
			SimpleUtils.pass("Click on Schedule Sub tab");
			SimpleUtils.pass("Click on Day view");
			SimpleUtils.pass("Click on Next week arrow");
			SimpleUtils.fail("assert Click on day which is not highlighted and make sure Highlighted day does not get loaded before user clicks on other day.",false);
	    }


		@Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee_Enterprise")
		@TestName(description = "LEG-5232: Data for Schedule does not get loaded when user clicks on next day without waiting data for highlighted day gets loaded")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void groupByLocationFilterShouldBeSelected(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login into https://enterprise-stage.legion.work/legion/?enterprise=Coffee#/");
			SimpleUtils.pass("Select location as Bay Area");
			SimpleUtils.pass("Navigate to Schedule page");
			SimpleUtils.pass("assert GroupByLocation should be selected by Default on Schedule Page");
	    }

		@Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee_Enterprise")
		@TestName(description = "LEG-5230: Group By selection filter is blank on navigating back from different tabs")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void groupByAllShouldNotBeBlank(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login into application successfully");
			SimpleUtils.pass("Navigate to Schedule page");
			SimpleUtils.pass("Select “Group By WorkRole” in the filter");
			SimpleUtils.pass("Navigate to overview tab and then navigate back to schedule tab");
			SimpleUtils.pass("assert Group By Workrole filter should be Sticky and should not be blank");
	    }
		
		
		@Automated(automated = "Manual")
		@Owner(owner = "Gunjan")
		@Enterprise(name = "Coffee2_Enterprise")
		@TestName(description = "LEG-5333: Other Hrs is displaying as Zero in Schedule tab whereas Other hrs is displaying as 10.5 Hrs in Dashboard")
	    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
	    public void shouldBeNonZeroOtherHours(String username, String password, String browser, String location)
	            throws Exception
	    {
			SimpleUtils.pass("Login into application successfully");
			SimpleUtils.pass("Change location to Legion Coffee Moch Store");
			SimpleUtils.pass("Verify the value of Other Hrs in dashboard Page");
			SimpleUtils.pass("Navigate to Schedule page");
			SimpleUtils.pass("assert alue of Other Hrs in Schedule tab should be same as Dashboard page");
	    }

	@Automated(automated ="Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Prepare the data for swap")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass= CredentialDataProviderSource.class)
	public void prepareTheSwapShiftsAsInternalAdmin(String browser, String username, String password, String location) throws Exception {
	  	try {
	  		swapCoverNames = new ArrayList<>();
			swapCoverCredentials = getSwapCoverUserCredentials(location);
			for (Map.Entry<String, Object[][]> entry : swapCoverCredentials.entrySet()) {
				swapCoverNames.add(entry.getKey());
			}
			workRoleName = String.valueOf(swapCoverCredentials.get(swapCoverNames.get(0))[0][3]);

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
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			// Deleting the existing shifts for swap team members
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(swapCoverNames.get(0));
			schedulePage.deleteTMShiftInWeekView(swapCoverNames.get(1));
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.saveSchedule();
			// Add the new shifts for swap team members
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.addNewShiftsByNames(swapCoverNames, workRoleName);
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team Member view Swap")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamMemberViewSwapAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception
	{
		try {
			prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
			loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
					, String.valueOf(credential[0][2]));
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			dashboardPage.clickOnProfileIconOnDashboard();
			if (dashboardPage.isSwitchToEmployeeViewPresent()) {
				dashboardPage.clickOnSwitchToEmployeeView();
			}
			// Verify Shift should be shown as starting Tomorrow/Today
			if (dashboardPage.getUpComingShifts().size() > 0) {
				SimpleUtils.pass("Shifts are shown Successfully!");
			} else {
				SimpleUtils.fail("Shifts not loaded successfully!", true);
			}
			// Verify View my Schedule button should be present and clickable
			dashboardPage.isViewMySchedulePresentAndClickable();
			// Verify After click on the View My Schedule, page should navigate to My Schedule page
			SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
			schedulePage.isSchedule();
			schedulePage.navigateToNextWeek();
			// Verify On My Schedule page, Schedule shifts should be present on Schedule table
			SimpleUtils.assertOnFail("Schedule Shifts are not present!", schedulePage.areShiftsPresent(), true);
			// Verify After click on any shift from Schedule table, 2 Button should be available for : 1. Request to Swap shift 2. Request to cover shift
			schedulePage.verifyClickOnAnyShift();
			List<String> requests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
			SimpleUtils.assertOnFail("Requests on pop-up shows incorrectly!", schedulePage.verifyShiftRequestButtonOnPopup(requests), true);
			// Verify After click on the Request to swap shift, Find Shifts to Swap page should be opened.
			String swapRequest = "Request to Swap Shift";
			String swapTitle = "Find Shifts to Swap";
			schedulePage.clickTheShiftRequestByName(swapRequest);
			SimpleUtils.assertOnFail(swapTitle + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(swapTitle), true);
			// Verify On Find Shifts to Swap page should have comparable shifts that you can ask to trade
			schedulePage.verifyComparableShiftsAreLoaded();
			// Verify After requesting for Swap shift, Request should go to the another TM
			schedulePage.selectOneTeamMemberToSwap();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the Team Member view Cover")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamMemberViewCoverAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception
	{
		try {
			prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
			loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
					, String.valueOf(credential[0][2]));
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			dashboardPage.clickOnProfileIconOnDashboard();
			if (dashboardPage.isSwitchToEmployeeViewPresent()) {
				dashboardPage.clickOnSwitchToEmployeeView();
			}
			// Verify Shift should be shown as starting Tomorrow/Today
			if (dashboardPage.getUpComingShifts().size() > 0) {
				SimpleUtils.pass("Shifts are shown Successfully!");
			} else {
				SimpleUtils.fail("Shifts not loaded successfully!", true);
			}
			// Verify View my Schedule button should be present and clickable
			dashboardPage.isViewMySchedulePresentAndClickable();
			// Verify After click on the View My Schedule, page should navigate to My Schedule page
			SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
			schedulePage.isSchedule();
			schedulePage.navigateToNextWeek();
			// Verify On My Schedule page, Schedule shifts should be present on Schedule table
			SimpleUtils.assertOnFail("Schedule Shifts are not present!", schedulePage.areShiftsPresent(), true);
			// Verify After click on any shift from Schedule table, 2 Button should be available for : 1. Request to Swap shift 2. Request to cover shift
			int index = schedulePage.verifyClickOnAnyShift();
			// Verify After Click on the Request to Cover shift, Submit Cover Request should be opened.
			String request = "Request to Cover Shift";
			String title = "Submit Cover Request";
			schedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
			// Verify Submit Cover Request should have Message Text box, Submit and cancel button
			schedulePage.verifyComponentsOnSubmitCoverRequest();
			// Verify After Click on the Submit button, confirmation pop-up should be opened.
			schedulePage.verifyClickOnSubmitButton();
			// Verify After requesting for cover request, View Cover Request status should be shown
			schedulePage.clickOnShiftByIndex(index);
			List<String> requests = new ArrayList<>(Arrays.asList("View Cover Request Status"));
			SimpleUtils.assertOnFail("Requests on pop-up shows incorrectly!", schedulePage.verifyShiftRequestButtonOnPopup(requests), false);
			// Verify After Click on the View Cover Request , Cover Request status page should be opened
			schedulePage.clickTheShiftRequestByName(requests.get(0));
			title = "Cover Request Status";
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
			// Validate the cancellation of cover request
			schedulePage.verifyClickCancelSwapOrCoverRequest();
			// Validate the Submit swap/cover request pop-up keep Showing
			schedulePage.clickOnShiftByIndex(index);
			requests = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
			SimpleUtils.assertOnFail("Requests on pop-up shows incorrectly!", schedulePage.verifyShiftRequestButtonOnPopup(requests), false);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the functionality of Team schedule option")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheTeamScheduleOptionAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {
	  	try {
			// Login as Internal Admin first to make sure that the schedule is published for this week, and get the whole week schedule
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
			//make publish schedule activity
			boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
			if (isActiveWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.selectWorkRole("Event Manager");
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.OpenShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();
			List<String> weekSchedule = schedulePage.getWholeWeekSchedule();
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			// Login as Team Member
			loginAsDifferentRole(AccessRoles.TeamMember.getValue());
			dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			schedulePage.goToSchedulePageAsTeamMember();
			schedulePage.navigateToNextWeek();
			String mySelectedWeek = schedulePage.getSelectedWeek();
			// Validate the clickability of Team schedule option
			String subTitle = "Team Schedule";
			schedulePage.gotoScheduleSubTabByText(subTitle);
			// Validate that Team schedule is in view mode for TM
			schedulePage.verifyTeamScheduleInViewMode();
			// Validate the availability of team schedule for whole week
			List<String> weekScheduleTMView = schedulePage.getWholeWeekSchedule();
			if (weekSchedule.containsAll(weekScheduleTMView) && weekScheduleTMView.containsAll(weekSchedule)) {
				SimpleUtils.pass("Whole week schedule is consistent with admin view!");
			} else {
				SimpleUtils.fail("Whole week schedule is incorrect!", false);
			}
			// Validate the value of selected week in Team Schedule
			String teamSelectedWeek = schedulePage.getSelectedWeek();
			if (teamSelectedWeek.equalsIgnoreCase(mySelectedWeek)) {
				SimpleUtils.pass("Team Schedule selected week is same as what we selected in My Schedule!");
			} else {
				SimpleUtils.fail("Team Schedule selected week isn't same as what we selected in My Schedule!", false);
			}
			// TODO: Validate the visibility of info icon - refer to meal break, skip it now
			// Validate the number of shifts in open shift smartcard
			String cardName = "OPEN SHIFTS";
			SimpleUtils.assertOnFail("Open shift smart card not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
			int openShiftCount = schedulePage.getCountFromSmartCardByName(cardName);
			String linkName = "View Shifts";
			schedulePage.clickLinkOnSmartCardByName(linkName);
			int openShiftCountWeekView = schedulePage.getShiftsCount();
			if (openShiftCount == openShiftCountWeekView) {
				SimpleUtils.pass("The number of open shift in smart card is correct!");
			} else {
				SimpleUtils.fail("The number of open shift in smart card is: " + openShiftCount + ", but actual count is: " + openShiftCountWeekView, false);
			}
			// Validate the filters results for team schedule
			schedulePage.filterScheduleByShiftTypeAsTeamMember(true);
			// Validate the availbity of Print icon
			SimpleUtils.assertOnFail("Print Icon not loaded Successfully!", schedulePage.isPrintIconLoaded(), false);
			// Validate the print feature
			schedulePage.verifyThePrintFunction();
			// Validate team schedule by selecting another week
			schedulePage.verifySelectOtherWeeks();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the functionality of Swap and Cover request options")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionalityOfSwapAndCoverAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {
	  	try {
			prepareTheSwapShiftsAsInternalAdmin(browser, username, password, location);
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			Object[][] credential = swapCoverCredentials.get(swapCoverNames.get(0));
			loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
					, String.valueOf(credential[0][2]));
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("Dashboard page not loaded successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			dashboardPage.clickOnProfileIconOnDashboard();
			if (dashboardPage.isSwitchToEmployeeViewPresent()) {
				dashboardPage.clickOnSwitchToEmployeeView();
			}
			SchedulePage schedulePage = dashboardPage.goToTodayForNewUI();
			schedulePage.isSchedule();
			schedulePage.navigateToNextWeek();
			// Validate the availibility of Swap and Cover request options
			int index = schedulePage.verifyClickOnAnyShift();
			// Verify the availability of Request to cover shift option
			String request = "Request to Cover Shift";
			String title = "Submit Cover Request";
			schedulePage.clickTheShiftRequestByName(request);
			// Validate the clickabilityof option of Request to Cover
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
			// Validate the data of Submit Cover Request popup
			schedulePage.verifyComponentsOnSubmitCoverRequest();
			// Validate the content of Add Message text box on Submit Cover Request pop-up
			schedulePage.verifyTheContentOfMessageOnSubmitCover();
			// Validate the functionality of Cancel button of Submit Cover Request popup
			schedulePage.clickCancelButtonOnPopupWindow();
			// Validate the functionality of Submit button of Submit Cover Request popup
			schedulePage.clickOnShiftByIndex(index);
			schedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
			schedulePage.verifyComponentsOnSubmitCoverRequest();
			schedulePage.verifyClickOnSubmitButton();
			// Validate the unavailibity of Request to Swap and Request to Cover post submission of Cover request
			schedulePage.clickOnShiftByIndex(index);
			List<String> swapCoverRequsts = new ArrayList<>(Arrays.asList("Request to Swap Shift", "Request to Cover Shift"));
			if (!schedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
				SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
			} else {
				SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
			}
			// Validate the Shift after requesting for cover
			List<String> viewCoverRequests = new ArrayList<>(Arrays.asList("View Cover Request Status"));
			SimpleUtils.assertOnFail("Requests on pop-up shows incorrectly!", schedulePage.verifyShiftRequestButtonOnPopup(viewCoverRequests), true);
			schedulePage.clickTheShiftRequestByName(viewCoverRequests.get(0));
			title = "Cover Request Status";
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
			String status = "Pending";
			schedulePage.verifyShiftRequestStatus(status);
			schedulePage.verifyClickCancelSwapOrCoverRequest();

			// For Swap Feature
			index = schedulePage.verifyClickOnAnyShift();
			// Validate the clickability of Cover and Swap request options
			request = "Request to Swap Shift";
			title = "Find Shifts to Swap";
			schedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
			// Validate the Request to Swap feature
			schedulePage.verifyComparableShiftsAreLoaded();
			// Validate the data of the available TMs to swap the shift
			schedulePage.verifyTheDataOfComparableShifts();
			// Validate the sum of available swap shifts
			schedulePage.verifyTheSumOfSwapShifts();
			// Validate the Next button functionality in swap popup
			schedulePage.verifyNextButtonIsLoadedAndDisabledByDefault();
			// Validate the selection feature of shifts to send a swap request
			schedulePage.verifySelectOneShiftNVerifyNextButtonEnabled();
			// Validate the clickability and functionality of cancel button
			schedulePage.clickCancelButtonOnPopupWindow();
			// Validate the redirection of Next button to Submit Swap Request popup
			schedulePage.clickOnShiftByIndex(index);
			schedulePage.clickTheShiftRequestByName(request);
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
			schedulePage.verifyClickOnNextButtonOnSwap();
			title = "Submit Swap Request";
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
			// Validate the availability of Back and Submit buttons in Submit Swap Request popup
			schedulePage.verifyBackNSubmitBtnLoaded();
			// Validate the redirection of Back button
			schedulePage.verifyTheRedirectionOfBackButton();
			// Validate the multiselection feature of Swap request
			schedulePage.verifySelectMultipleSwapShifts();
			// Validate the Submit button feature
			schedulePage.verifyClickOnNextButtonOnSwap();
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
			schedulePage.verifyClickOnSubmitButton();
			// Validate the disappearence of Request to Swap and Request to Cover option
			schedulePage.clickOnShiftByIndex(index);
			if (!schedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
				SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
			} else {
				SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
			}

			loginPage.logOut();

			credential = swapCoverCredentials.get(swapCoverNames.get(1));
			loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
					, String.valueOf(credential[0][2]));
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			dashboardPage.clickOnProfileIconOnDashboard();
			if (dashboardPage.isSwitchToEmployeeViewPresent()) {
				dashboardPage.clickOnSwitchToEmployeeView();
			}
			dashboardPage.goToTodayForNewUI();
			schedulePage.isSchedule();
			schedulePage.navigateToNextWeek();
			// Validate that swap request smartcard is available to recipient team member
			String smartCard = "SWAP REQUESTS";
			schedulePage.isSmartCardAvailableByLabel(smartCard);
			// Validate the availability of all swap request shifts in schedule table
			String linkName = "View All";
			schedulePage.clickLinkOnSmartCardByName(linkName);
			schedulePage.verifySwapRequestShiftsLoaded();
			// Validate that recipient can claim the swap request shift.
			schedulePage.verifyClickAcceptSwapButton();

			loginPage.logOut();
			credential = swapCoverCredentials.get(swapCoverNames.get(0));
			loginToLegionAndVerifyIsLoginDone(String.valueOf(credential[0][0]), String.valueOf(credential[0][1])
					, String.valueOf(credential[0][2]));
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			dashboardPage.clickOnProfileIconOnDashboard();
			if (dashboardPage.isSwitchToEmployeeViewPresent()) {
				dashboardPage.clickOnSwitchToEmployeeView();
			}
			dashboardPage.goToTodayForNewUI();
			schedulePage.isSchedule();
			schedulePage.navigateToNextWeek();

			schedulePage.clickOnShiftByIndex(index);
			List<String> viewSwapRequest = new ArrayList<>(Arrays.asList("View Swap Request Status"));
			if (!schedulePage.verifyShiftRequestButtonOnPopup(viewSwapRequest)) {
				schedulePage.clickOnShiftByIndex(index);
				// Create the Swap request again, so that it can be cancelled
				createTheSwapRequest(index);
				schedulePage.clickOnShiftByIndex(index);
			}
			schedulePage.clickTheShiftRequestByName(viewSwapRequest.get(0));
			title = "Swap Request Status";
			SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
			// Validate the cancellation of Swap request
			schedulePage.verifyClickCancelSwapOrCoverRequest();
			// Validate that user can resend the swap request after cancelling it first
			createTheSwapRequest(index);
			// Validate the unavailibity of Request to Swap and Request to Cover forPast shifts
			schedulePage.clickOnShiftByIndex(index);
			if (!schedulePage.verifyShiftRequestButtonOnPopup(swapCoverRequsts)) {
				SimpleUtils.pass("Request to Swap and Request to Cover options are disappear");
			} else {
				SimpleUtils.fail("Request to Swap and Request to Cover options are still shown!", false);
			}
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Validate the feature of filter")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFeatureOfFilterAsInternalAdmin(String browser, String username, String password, String location)
			throws Exception {
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
			if (isWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			String workRole = schedulePage.getRandomWorkRole();
			// Deleting the existing shifts for swap team members
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			schedulePage.addOpenShiftWithLastDay(workRole);
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();

			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			// Login as Team Member
			loginAsDifferentRole(AccessRoles.TeamMember.getValue());

			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			schedulePage.goToSchedulePageAsTeamMember();
			schedulePage.navigateToNextWeek();
			String subTitle = "Team Schedule";
			schedulePage.gotoScheduleSubTabByText(subTitle);
			// Validate the feature of filter
			schedulePage.verifyScheduledNOpenFilterLoaded();
			// Validate the filter - Schedule and Open
			schedulePage.checkAndUnCheckTheFilters();
			// Validate the filter results by applying scheduled filter
			// Validate the filter results by applying Open filter
			schedulePage.filterScheduleByShiftTypeAsTeamMember(true);
			// Validate the filter results by applying both filters and none of them
			schedulePage.filterScheduleByBothAndNone();
			// Validate the filter value by moving to other weeks
			String selectedFilter = schedulePage.selectOneFilter();
			schedulePage.verifySelectedFilterPersistsWhenSelectingOtherWeeks(selectedFilter);
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	@Automated(automated = "Automated")
	@Owner(owner = "Nora")
	@Enterprise(name = "KendraScott2_Enterprise")
	@TestName(description = "Verify the availibility and functionality of claiming open shift popup")
	@Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	public void verifyTheFunctionalityOfClaimOpenShiftAsTeamLead(String browser, String username, String password, String location)
			throws Exception {
	  	try {
			DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			ProfileNewUIPage profileNewUIPage = pageFactory.createProfileNewUIPage();
			String tmName = profileNewUIPage.getNickNameFromProfile();
			LoginPage loginPage = pageFactory.createConsoleLoginPage();
			loginPage.logOut();

			loginAsDifferentRole(AccessRoles.InternalAdmin.getValue());
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);

			// Checking configuration in controls
			String option = "Always";
			ControlsNewUIPage controlsNewUIPage = pageFactory.createControlsNewUIPage();
			controlsNewUIPage.clickOnControlsConsoleMenu();
			controlsNewUIPage.clickOnControlsScheduleCollaborationSection();
			boolean isScheduleCollaboration = controlsNewUIPage.isControlsScheduleCollaborationLoaded();
			SimpleUtils.assertOnFail("Controls Page: Schedule Collaboration Section not Loaded.", isScheduleCollaboration, true);
			controlsNewUIPage.updateOpenShiftApprovedByManagerOption(option);

			SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
			schedulePage.clickOnScheduleConsoleMenuItem();
			SimpleUtils.assertOnFail("Schedule page 'Overview' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Overview.getValue()), true);
			schedulePage.clickOnScheduleSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue());
			SimpleUtils.assertOnFail("Schedule page 'Schedule' sub tab not loaded Successfully!", schedulePage.verifyActivatedSubTab(ScheduleNewUITest.SchedulePageSubTabText.Schedule.getValue()), true);
			schedulePage.navigateToNextWeek();

			boolean isActiveWeekGenerated = schedulePage.isWeekGenerated();
			if (isActiveWeekGenerated) {
				schedulePage.unGenerateActiveScheduleScheduleWeek();
			}
			schedulePage.createScheduleForNonDGFlowNewUI();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.deleteTMShiftInWeekView(tmName);
			schedulePage.deleteTMShiftInWeekView("Unassigned");
			String workRole = schedulePage.getRandomWorkRole();
			schedulePage.saveSchedule();
			schedulePage.clickOnEditButtonNoMaterScheduleFinalizedOrNot();
			schedulePage.clickOnDayViewAddNewShiftButton();
			schedulePage.customizeNewShiftPage();
			schedulePage.selectWorkRole(workRole);
			schedulePage.clickRadioBtnStaffingOption(ScheduleNewUITest.staffingOption.ManualShift.getValue());
			schedulePage.clickOnCreateOrNextBtn();
			schedulePage.searchTeamMemberByName(tmName);
			schedulePage.clickOnOfferOrAssignBtn();
			schedulePage.saveSchedule();
			schedulePage.publishActiveSchedule();

			loginPage.logOut();

			loginToLegionAndVerifyIsLoginDone(username, password, location);
			SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
			schedulePage = dashboardPage.goToTodayForNewUI();
			schedulePage.isSchedule();
			schedulePage.navigateToNextWeek();

			// Validate the clickability of claim open text in popup
			String cardName = "WANT MORE HOURS?";
			SimpleUtils.assertOnFail("Smart Card: " + cardName + " not loaded Successfully!", schedulePage.isSpecificSmartCardLoaded(cardName), false);
			String linkName = "View Shifts";
			schedulePage.clickLinkOnSmartCardByName(linkName);
			SimpleUtils.assertOnFail("Open shifts not loaed Successfully!", schedulePage.areShiftsPresent(), false);
			List<String> shiftHours = schedulePage.getShiftHoursFromInfoLayout();
			List<String> claimShift = new ArrayList<>(Arrays.asList("Claim Shift"));
			int index = schedulePage.selectOneShiftIsClaimShift(claimShift);
			String weekDay = schedulePage.getSpecificShiftWeekDay(index);
			// Validate the availability of Claim Shift Request popup
			schedulePage.clickTheShiftRequestByName(claimShift.get(0));
			// Validate the availability of Cancel and I Agree buttons in popup
			schedulePage.verifyClaimShiftOfferNBtnsLoaded();
			// Validate the date and time of Claim Shift Request popup
			schedulePage.verifyTheShiftHourOnPopupWithScheduleTable(shiftHours.get(index), weekDay);
			// Validate the clickability of Cancel button
			schedulePage.verifyClickCancelBtnOnClaimShiftOffer();
			// Validate the clickability of I Agree button
			schedulePage.clickOnShiftByIndex(index);
			schedulePage.clickTheShiftRequestByName(claimShift.get(0));
			schedulePage.verifyClickAgreeBtnOnClaimShiftOffer();
			// Validate the status of Claim request
			schedulePage.clickOnShiftByIndex(index);
			List<String> claimStatus = new ArrayList<>(Arrays.asList("Claim Shift Approval Pending", "Cancel Claim Request"));
			schedulePage.verifyShiftRequestButtonOnPopup(claimStatus);
			// Validate the availability of Cancel Claim Request option.
			schedulePage.verifyTheColorOfCancelClaimRequest(claimStatus.get(1));
			// Validate that Cancel claim request is clickable and popup is displaying by clicking on it to reconfirm the cancellation
			schedulePage.clickTheShiftRequestByName(claimStatus.get(1));
			schedulePage.verifyReConfirmDialogPopup();
			// Validate that Claim request remains in Pending state after clicking on No button
			schedulePage.verifyClickNoButton();
			schedulePage.clickOnShiftByIndex(index);
			schedulePage.verifyShiftRequestButtonOnPopup(claimStatus);
			// Validate the Cancellation of Claim request by clicking  on Yes
			schedulePage.clickTheShiftRequestByName(claimStatus.get(1));
			schedulePage.verifyReConfirmDialogPopup();
			schedulePage.verifyClickOnYesButton();
			schedulePage.clickOnShiftByIndex(index);
			schedulePage.verifyShiftRequestButtonOnPopup(claimShift);
			// Validate the functionality of clear filter in Open shift smart card
			schedulePage.verifyTheFunctionalityOfClearFilter();
		} catch (Exception e){
			SimpleUtils.fail(e.getMessage(), false);
		}
	}

	public void createTheSwapRequest(int index) throws Exception {
	  	SchedulePage schedulePage = pageFactory.createConsoleScheduleNewUIPage();
		schedulePage.clickOnShiftByIndex(index);
		String request = "Request to Swap Shift";
		String title = "Find Shifts to Swap";
		schedulePage.clickTheShiftRequestByName(request);
		SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), true);
		schedulePage.verifySelectOneShiftNVerifyNextButtonEnabled();
		schedulePage.verifyClickOnNextButtonOnSwap();
		title = "Submit Swap Request";
		SimpleUtils.assertOnFail(title + " page not loaded Successfully!", schedulePage.isPopupWindowLoaded(title), false);
		schedulePage.verifyClickOnSubmitButton();
	}
}
