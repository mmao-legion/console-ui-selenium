package com.legion.tests.core;

import static com.legion.utils.MyThreadLocal.getEnterprise;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class BartenderScheduleTest extends TestBase{
	  //private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");
	  //private static HashMap<String, String> enterpriseCoffee2Credentials = JsonUtil.getPropertiesFromJsonFile("src/test/resources/enterpriseCoffee2Credentials.json");	
	  private static HashMap<String, String> legionUsersCredentialsMethodsMapping = JsonUtil.getPropertiesFromJsonFile("src/test/resources/legionUsersCredentialsMethodsMapping.json");	
	  
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
	  
	  SchedulePage schedulePage = null;
	  
	  
	  @BeforeMethod()
      public void scheduleInitialSteps(Method testMethod, Object[] params) throws Exception{
            initialize();
            initialSteps(testMethod);    
      }
	  
	  // ToDo - 
	  @Automated(automated ="Automated")
	  @Owner(owner = "Naval")
	  @TestName(description = "Login as Bartender, navigate & verify Schedule page")
	  @Test/*(dataProvider = "legionTeamCredentials")*/
	  public void scheduleTestAsTeamMember(/*String username, String password, String location, String accessRole*/)
	  {
		  //firstTest( username,  password,  location);
		  SimpleUtils.assertOnFail("Schedule Page: Schedule is not Published for current week.",
				  schedulePage.isCurrentScheduleWeekPublished(), false);
	  }

	  
	  @Automated(automated ="Automated")
	  @Owner(owner = "Naval")
	  @TestName(description = "Login as Bartender, navigate & verify Schedule page")
	  @Test/*(dataProvider = "legionTeamCredentials")*/
	  public void scheduleTestAsTeamLead(/*String username, String password, String location, String accessRole*/)
	  {
		  SimpleUtils.assertOnFail("Schedule Page: Schedule is not Published for current week.",
				  schedulePage.isCurrentScheduleWeekPublished(), false);
		  //comparingWeekScheduledHoursAndSumOfDaysScheduledHours(scheduleWeekViewLabelData, scheduleDaysViewLabelDataForWeekDays);
	  }
	  
	  @Automated(automated ="Automated")
	  @Owner(owner = "Naval")
	  @TestName(description = "Login as Bartender, navigate & verify Schedule page")
	  @Test/*(dataProvider = "legionTeamCredentials")*/
	  public void scheduleTestAsStoreManager(/*String username, String password, String location, String accessRole*/)
	  {
		  HashMap<String, Float> scheduleWeekViewLabelData = getCurrentWeekData();
		  List<HashMap<String, Float>>  scheduleDaysViewLabelDataForWeekDays = getDaysDataofCurrentWeek();
		  comparingWeekScheduledHoursAndSumOfDaysScheduledHours(scheduleWeekViewLabelData, scheduleDaysViewLabelDataForWeekDays);
		  
		
	  }

	  public void loginToLegion(String username, String password)
	  {
		  loginToLegionAndVerifyIsLoginDone(username, password);
	  }
	  
	  public void navigateToSchedulePage()
	  {
	      try {
	    	schedulePage = pageFactory.createConsoleSchedulePage();
	    	schedulePage.clickOnScheduleConsoleMenuItem();
			schedulePage.clickOnScheduleSubTab(SchedulePageSubTabText.Schedule.value);
		} catch (Exception e) {
			SimpleUtils.fail("Unable to load Schedule Sub Tab", false);
		}
	  }
	  
	  
	  public void changeLocationTest(String location) { 
			LocationSelectorPage locationSelectorPage = pageFactory.createLocationSelectorPage();
			locationSelectorPage.changeLocation(location);
			SimpleUtils.assertOnFail("Dashboard Page: Location not changed!",locationSelectorPage.isLocationSelected(location), false);
	  }
	  
	  public HashMap<String, Float> getCurrentWeekData() {
		  HashMap<String, Float> scheduleWeekViewLabelData = new HashMap<String, Float>();
		  try {
			  	schedulePage.clickOnWeekView();
		        scheduleWeekViewLabelData = schedulePage.getScheduleLabelHoursAndWagges();
		  }
		  catch(Exception e){
			  SimpleUtils.fail("Unable to load Current Week Data!"+e.getMessage(), true);
		  }
		  return scheduleWeekViewLabelData;
		  	
	  }
	  
	  public List<HashMap<String, Float>> getDaysDataofCurrentWeek()
	  {
		  List<HashMap<String, Float>>  scheduleDaysViewLabelDataForWeekDays = new ArrayList<HashMap<String, Float>>();
		  try {
			  schedulePage.clickOnDayView();
			  scheduleDaysViewLabelDataForWeekDays = schedulePage.getScheduleLabelHoursAndWagesDataForEveryDayInCurrentWeek();
		  } 
		  catch (Exception e) {
			SimpleUtils.fail("Unable to get Current Week data", false);
		}
		  return scheduleDaysViewLabelDataForWeekDays;
	  }
	    
	   public void comparingWeekScheduledHoursAndSumOfDaysScheduledHours(HashMap<String, Float> scheduleWeekViewLabelData, List<HashMap<String, Float>> scheduleDaysViewLabelDataForWeekDays) {
		   // Variables for Day View Data
		   Float scheduleDaysScheduledHoursTotal = (float) 0;
		   Float scheduleDaysBudgetedHoursTotal = (float) 0;
		   Float scheduleDaysOtherHoursTotal = (float) 0;
		   Float scheduleDaysWagesBudgetedCountTotal = (float) 0;
		   Float scheduleDaysWagesScheduledCountTotal = (float) 0;
		   
		   // Variables for Week View Data
		   Float scheduleWeekScheduledHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.scheduledHours.getValue());
	       Float scheduleWeekBudgetedHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.budgetedHours.getValue());
	       Float scheduleWeekOtherHours = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.otherHours.getValue());
	       Float scheduleWeekWagesBudgetedCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue());
	       Float scheduleWeekWagesScheduledCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue());
	       
		   for(HashMap<String, Float> scheduleDaysViewLabelDataForWeekDay : scheduleDaysViewLabelDataForWeekDays)
		   {
			   if(scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.scheduledHours.getValue()) != null)
				   scheduleDaysScheduledHoursTotal = scheduleDaysScheduledHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.scheduledHours.getValue());
		       	
			   if(scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.budgetedHours.getValue()) != null)
				   scheduleDaysBudgetedHoursTotal = scheduleDaysBudgetedHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.budgetedHours.getValue());
		       	
			   if(scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.otherHours.getValue()) != null)
				   scheduleDaysOtherHoursTotal = scheduleDaysOtherHoursTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.otherHours.getValue());
			   
			   if(scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue()) != null)
				   scheduleDaysWagesBudgetedCountTotal = scheduleDaysWagesBudgetedCountTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue());
			   
			   if(scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue()) != null)
				   scheduleDaysWagesScheduledCountTotal = scheduleDaysWagesScheduledCountTotal + scheduleDaysViewLabelDataForWeekDay.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue());
		   }
		   
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
	   		        SimpleUtils.assertOnFail("Schedule Page: Week Budgeted Hours not matched with Sum of Days Budgeted Hours (" +scheduleWeekBudgetedHours+ "/" 
	   		        		+ scheduleDaysBudgetedHoursTotal + ")", scheduleWeekBudgetedHours.equals(scheduleDaysBudgetedHoursTotal), true);
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
	   		        SimpleUtils.assertOnFail("Schedule Page: Week Budgeted Wages not matched with Sum of Days Budgeted Wages (" +scheduleWeekWagesBudgetedCount+ "/" 
	   		        		+ scheduleDaysWagesBudgetedCountTotal + ")", scheduleWeekWagesBudgetedCount.equals(scheduleDaysWagesBudgetedCountTotal), true);
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
	   
	   
	   public void initialSteps(Method testMethod) throws Exception{
           for(Map.Entry<String, String> entry : legionUsersCredentialsMethodsMapping.entrySet())
             {
                 ArrayList<String> genericData = SimpleUtils.getUserCredentialsAndLocation(entry.getValue());
                 if(testMethod.getName().contains(entry.getKey())){
                     loginToLegion(genericData.get(0), genericData.get(1));
                     changeLocationTest(genericData.get(2));
                     navigateToSchedulePage();
                     break;
                 }
                 
             }
       }

}
