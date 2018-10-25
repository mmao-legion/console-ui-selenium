package com.legion.tests.core;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.legion.pages.LocationSelectorPage;
import com.legion.pages.SchedulePage;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;

public class ScheduleRoleBasedTest extends TestBase{
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
	  
	  
	  /*@BeforeMethod()
      public void scheduleInitialSteps(Method testMethod, Object[] params) throws Exception{
            initialize();
            initialSteps(testMethod);    
      }*/
	  
	    @Override
	    @BeforeMethod
	    public  void  firstTest (Method method, Object[] params) throws Exception
	    {
	        String classname = getClass().getSimpleName();
	        String methodName = method.getName();
	        System.out.println("Running "+ classname + "." + methodName + "  before test");
	        String paramsList = Arrays.asList(params).toString();
	        System.out.println((String)params[0]+","+(String)params[1]+","+(String)params[2]+","+(String)params[3]);
	        this.createDriver((String)params[0],"68","Linux");
	        visitPage(method);
	        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2]);
	        //changeLocationTest(genericData.get(2));
            navigateToSchedulePage();
	    }
	  
	  // ToDo - 
	  @Automated(automated ="Automated")
	  @Owner(owner = "Naval")
	  @TestName(description = "Login as Team Member, navigate & verify Schedule page")
	  @Enterprise(name = "Coffee_Enterprise")
	  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	  public void scheduleTestAsTeamMember(String browser, String username,  String  password, String location)
	  {
		  System.out.println("Under scheduleTestAsTeamMember");
		  SimpleUtils.assertOnFail("Schedule Page: Schedule is not Published for current week.",
				  schedulePage.isCurrentScheduleWeekPublished(), false);
		  List<HashMap<String, Float>>  scheduleDaysViewLabelDataForWeekDays = getDaysDataofCurrentWeek();
		  HashMap<String, Float> scheduleWeekViewLabelData = getCurrentWeekData();
		  SimpleUtils.assertOnFail("Schedule Page: Wages are loaded for Team Member in week view.",
				  ! iswagesLoadedInWeekView(scheduleWeekViewLabelData), false);
		  comparingWeekScheduledHoursAndSumOfDaysScheduledHours(scheduleWeekViewLabelData, scheduleDaysViewLabelDataForWeekDays);
	  }

	  
	  @Automated(automated ="Automated")
	  @Owner(owner = "Naval")
	  @Enterprise(name = "Coffee2_Enterprise")
	  @TestName(description = "Login as Team Lead, navigate & verify Schedule page")
	  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	  public void scheduleTestAsTeamLead(String browser, String username,  String  password, String location)
	  {
		  System.out.println("scheduleTestAsTeamLead Called ");
		  System.out.println("browser: "+browser + "username: "+username +"password: "+password +"location: "+location);
		  SimpleUtils.assertOnFail("Schedule Page: Schedule is not Published for current week.",
				  schedulePage.isCurrentScheduleWeekPublished(), false);
		  HashMap<String, Float> scheduleWeekViewLabelData = getCurrentWeekData();
		  List<HashMap<String, Float>>  scheduleDaysViewLabelDataForWeekDays = getDaysDataofCurrentWeek();
		  SimpleUtils.assertOnFail("Schedule Page: Wages are loaded for Team Lead in week view.",
				  ! iswagesLoadedInWeekView(scheduleWeekViewLabelData), false);
		  comparingWeekScheduledHoursAndSumOfDaysScheduledHours(scheduleWeekViewLabelData, scheduleDaysViewLabelDataForWeekDays);
		  
	  }
	  
	  @Automated(automated ="Automated")
	  @Owner(owner = "Naval")
	  @Enterprise(name = "Coffeerprise")
	  @TestName(description = "Login as Store Manager, navigate & verify Schedule page")
	  @Test
	  public void scheduleTestAsStoreManager()
	  {
		  HashMap<String, Float> scheduleWeekViewLabelData = getCurrentWeekData();
		  List<HashMap<String, Float>>  scheduleDaysViewLabelDataForWeekDays = getDaysDataofCurrentWeek();
		  SimpleUtils.assertOnFail("Schedule Page: Wages are not loaded for Store Manager in week view.",
				  iswagesLoadedInWeekView(scheduleWeekViewLabelData), false);
		  comparingWeekScheduledHoursAndSumOfDaysScheduledHours(scheduleWeekViewLabelData, scheduleDaysViewLabelDataForWeekDays);
		  
		
	  }
	  
	  
	  @Automated(automated ="Automated")
	  @Owner(owner = "Naval")
	  @TestName(description = "Login as Team Member, navigate & verify Schedule page")
	  @Enterprise(name = "Coffee_Enterprise")
	  @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass=CredentialDataProviderSource.class)
	  public void scheduleTestAsCoffee2_Enterprise(String browser, String username,  String  password, String location)
	  {
		  System.out.println("Under scheduleTestAsTeamMember");
		  SimpleUtils.assertOnFail("Schedule Page: Schedule is not Published for current week.",
				  schedulePage.isCurrentScheduleWeekPublished(), false);
		  List<HashMap<String, Float>>  scheduleDaysViewLabelDataForWeekDays = getDaysDataofCurrentWeek();
		  HashMap<String, Float> scheduleWeekViewLabelData = getCurrentWeekData();
		  SimpleUtils.assertOnFail("Schedule Page: Wages are loaded for Team Member in week view.",
				  ! iswagesLoadedInWeekView(scheduleWeekViewLabelData), false);
		  comparingWeekScheduledHoursAndSumOfDaysScheduledHours(scheduleWeekViewLabelData, scheduleDaysViewLabelDataForWeekDays);
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
			  SimpleUtils.fail("Unable to load Current Week Data!", true);
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
	   
	   
	   /*public void initialSteps(Method testMethod) throws Exception{
		   if(schedulePage == null)
			   schedulePage = pageFactory.createConsoleSchedulePage();
		   HashMap<String, ArrayList<String>> userCredentials = SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(propertyMap.get("ENTERPRISE"));
           
		   for(Entry<String, ArrayList<String>> entry : userCredentials.entrySet())
             {
                 if(testMethod.getName().contains(entry.getKey())){
                	 ArrayList<String> genericData = entry.getValue();
                     loginToLegion(genericData.get(0), genericData.get(1));
                     changeLocationTest(genericData.get(2));
                     navigateToSchedulePage();
                     break;
                 }
                 
             }
       }*/
	   
	   public boolean iswagesLoadedInWeekView(HashMap<String, Float> scheduleWeekViewLabelData)
	   {
	       Float scheduleWeekWagesBudgetedCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesBudgetedCount.getValue());
	       Float scheduleWeekWagesScheduledCount = scheduleWeekViewLabelData.get(scheduleHoursAndWagesData.wagesScheduledCount.getValue());
		   if(scheduleWeekWagesBudgetedCount != null && scheduleWeekWagesScheduledCount != null)
			   return true;
		   return false;
	   }

}
